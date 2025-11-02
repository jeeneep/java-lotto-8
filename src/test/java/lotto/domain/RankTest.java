package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @DisplayName("일치하는 개수와 보너스 번호 일치 여부에 따라 올바른 Rank를 반환해야 한다.")
    @ParameterizedTest
    // matchCount, matchBonus, expectedRankName
    @CsvSource(value = {
            "6, false, FIRST",
            "5, true, SECOND",
            "5, false, THIRD",
            "4, false, FOURTH",
            "3, false, FIFTH",
            "2, true, MISS"
    })
    void shouldReturnCorrectRank(int matchCount, boolean matchBonus, String expectedRankName) {
        Rank actualRank = Rank.valueOf(matchCount, matchBonus);
        assertThat(actualRank).isEqualTo(Rank.valueOf(expectedRankName));
    }
}