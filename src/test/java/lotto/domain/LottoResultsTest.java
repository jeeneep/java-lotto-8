package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultsTest {

    @DisplayName("총 상금 계산 기능이 정확해야 한다.")
    @Test
    void calculateTotalPrize_shouldBeAccurate() {
        // given: 5등 1개 (5000원), 4등 2개 (100000원), 2등 1개 (30,000,000원)
        long expectedPrize = 5000L + (50000L * 2) + 30_000_000L; // 30,105,000원
        Map<Rank, Integer> resultsMap = Map.of(
                Rank.SECOND, 1,
                Rank.FOURTH, 2,
                Rank.FIFTH, 1
        );
        LottoResults results = new LottoResults(resultsMap);

        // when
        long actualPrize = results.calculateTotalPrize();

        // then
        assertThat(actualPrize).isEqualTo(expectedPrize);
    }

    @DisplayName("수익률을 소수점 첫째 자리까지 정확히 계산해야 한다.")
    @ParameterizedTest
    @CsvSource(value = {"8000, 1, 62.5", "10000, 2, 100.0", "10000, 300, 15000.0"}) // 5000원, 10000원, 150만원
    void calculateRateOfReturn_shouldBeAccurate(int purchaseAmount, int fifthCount, String expectedRate) {
        // given: 5등만 있는 시나리오
        Map<Rank, Integer> resultsMap = Map.of(Rank.FIFTH, fifthCount);
        LottoResults results = new LottoResults(resultsMap);

        // when
        String actualRate = results.calculateRateOfReturn(purchaseAmount);

        // then
        assertThat(actualRate).isEqualTo(expectedRate);
    }

    @DisplayName("출력 포맷은 5등부터 1등 순서(상금 오름차순)로 정렬되어야 한다.")
    @Test
    void getFormattedResults_shouldBeSortedByPrizeMoney() {
        // given: 2, 4, 5등이 섞여 있는 통계
        Map<Rank, Integer> resultsMap = Map.of(
                Rank.SECOND, 1,
                Rank.FOURTH, 2,
                Rank.FIFTH, 3
        );
        LottoResults results = new LottoResults(resultsMap);

        // when
        String formatted = results.getFormattedResults();

        // then: 5등 -> 4등 -> 2등 순서인지 확인
        assertThat(formatted).startsWith("3개 일치 (5,000원) - 3개\n");
        assertThat(formatted).contains("4개 일치 (50,000원) - 2개\n");
        assertThat(formatted).contains("5개 일치, 보너스 볼 일치 (30,000,000원) - 1개\n");
    }
}