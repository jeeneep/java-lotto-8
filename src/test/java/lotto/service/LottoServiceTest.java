package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoResults; // 추가: LottoResults 임포트
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @DisplayName("당첨 결과에 따라 LottoResults 객체 내부를 정확하게 계산해야 한다.")
    @Test
    void calculateResults_shouldCountCorrectly() {
        // given: 당첨 1, 2, 3, 4, 5, 6, 보너스 7
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        WinningLotto winningLotto = new WinningLotto(winningNumbers, 7);

        // 5등 1개, 4등 1개, 2등 1개, 꽝 1개
        List<Lotto> purchasedLottos = List.of(
                new Lotto(List.of(1, 2, 3, 8, 9, 10)),
                new Lotto(List.of(1, 2, 3, 4, 9, 10)),
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                new Lotto(List.of(10, 11, 12, 13, 14, 15))
        );

        // when
        LottoResults results = lottoService.calculateResults(purchasedLottos, winningLotto);

        // then
        assertThat(results.getResults().get(Rank.FIRST)).isEqualTo(0);
        assertThat(results.getResults().get(Rank.SECOND)).isEqualTo(1);
        assertThat(results.getResults().get(Rank.THIRD)).isEqualTo(0);
        assertThat(results.getResults().get(Rank.FOURTH)).isEqualTo(1);
        assertThat(results.getResults().get(Rank.FIFTH)).isEqualTo(1);
    }

}