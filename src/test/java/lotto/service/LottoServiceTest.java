package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoServiceTest {

    private final LottoService lottoService = new LottoService();

    @DisplayName("로또 구입 금액에 따라 정확한 개수의 로또를 발행해야 한다.")
    @Test
    void purchaseLottos_shouldIssueCorrectCount() {
        // given
        int purchaseAmount = 14000;
        int expectedCount = 14;

        // when
        List<Lotto> purchasedLottos = lottoService.purchaseLottos(purchaseAmount);

        // then
        assertThat(purchasedLottos).hasSize(expectedCount);
    }

    @DisplayName("당첨 결과에 따라 통계 맵을 정확하게 계산해야 한다.")
    @Test
    void calculateResults_shouldCountCorrectly() {
        // given: 당첨 1, 2, 3, 4, 5, 6, 보너스 7
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        WinningLotto winningLotto = new WinningLotto(winningNumbers, 7);

        // 5등 1개 (1, 2, 3, 8, 9, 10)
        // 4등 1개 (1, 2, 3, 4, 9, 10)
        // 2등 1개 (1, 2, 3, 4, 5, 7)
        // 꽝 1개 (10, 11, 12, 13, 14, 15)
        List<Lotto> purchasedLottos = List.of(
                new Lotto(List.of(1, 2, 3, 8, 9, 10)),
                new Lotto(List.of(1, 2, 3, 4, 9, 10)),
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                new Lotto(List.of(10, 11, 12, 13, 14, 15))
        );

        // when
        Map<Rank, Integer> results = lottoService.calculateResults(purchasedLottos, winningLotto);

        // then
        assertThat(results.get(Rank.FIRST)).isEqualTo(0);
        assertThat(results.get(Rank.SECOND)).isEqualTo(1);
        assertThat(results.get(Rank.THIRD)).isEqualTo(0);
        assertThat(results.get(Rank.FOURTH)).isEqualTo(1);
        assertThat(results.get(Rank.FIFTH)).isEqualTo(1);
    }

    @DisplayName("총 상금과 구입 금액에 따라 수익률을 소수점 첫째 자리까지 정확히 계산해야 한다.")
    @Test
    void calculateRateOfReturn_shouldBeAccurate() {
        // given: 구입 금액 8000원 (로또 8개)
        int purchaseAmount = 8000;

        // 5등 1개 (5000원), 4등 0개, 3등 0개, 2등 0개, 1등 0개
        Map<Rank, Integer> results = Map.of(
                Rank.FIRST, 0,
                Rank.SECOND, 0,
                Rank.THIRD, 0,
                Rank.FOURTH, 0,
                Rank.FIFTH, 1
        );

        // 총 상금: 5000원
        // 수익률: (5000 / 8000) * 100 = 62.5%

        // when
        String rateOfReturn = lottoService.calculateRateOfReturn(results, purchaseAmount);

        // then
        assertThat(rateOfReturn).isEqualTo("62.5");
    }

    @DisplayName("총 상금에 따라 수익률을 반올림하여 소수점 첫째 자리까지 정확히 계산해야 한다.")
    @Test
    void calculateRateOfReturn_shouldHandleRounding() {
        // given: 구입 금액 10000원
        int purchaseAmount = 10000;

        // 5등 1개 (5000원)
        Map<Rank, Integer> results = Map.of(
                Rank.FIRST, 0,
                Rank.SECOND, 0,
                Rank.THIRD, 0,
                Rank.FOURTH, 0,
                Rank.FIFTH, 1
        );
        // 총 상금: 5000원
        // 수익률: (5000 / 10000) * 100 = 50.0%
        assertThat(lottoService.calculateRateOfReturn(results, purchaseAmount)).isEqualTo("50.0");

        // 5등 2개 (10000원)
        results = Map.of(
                Rank.FIRST, 0,
                Rank.SECOND, 0,
                Rank.THIRD, 0,
                Rank.FOURTH, 0,
                Rank.FIFTH, 2
        );
        // 총 상금: 10000원
        // 수익률: (10000 / 10000) * 100 = 100.0%
        assertThat(lottoService.calculateRateOfReturn(results, purchaseAmount)).isEqualTo("100.0");

        // 상금 1500000원 (3등 1개)
        results = Map.of(
                Rank.FIRST, 0,
                Rank.SECOND, 0,
                Rank.THIRD, 1,
                Rank.FOURTH, 0,
                Rank.FIFTH, 0
        );
        // 수익률: (1500000 / 10000) * 100 = 15000.0%
        assertThat(lottoService.calculateRateOfReturn(results, purchaseAmount)).isEqualTo("15000.0");
    }
}