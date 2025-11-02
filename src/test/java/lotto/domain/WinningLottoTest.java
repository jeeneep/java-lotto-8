package lotto.domain;

import lotto.constant.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void createWinningLotto_WithBonusDuplicated() {
        // given
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int duplicatedBonus = 6;

        // when & then
        assertThatThrownBy(() -> new WinningLotto(winningNumbers, duplicatedBonus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.BONUS_NUMBER_IN_WINNING_NUMBERS.getMessage());
    }

    @DisplayName("구매 로또가 당첨 번호와 일치하는 개수 및 보너스 일치 여부에 따라 올바른 Rank를 반환해야 한다.")
    @ParameterizedTest(name = "구매로또: {0}, 예상등수: {1}")
    @MethodSource("provideLottoAndExpectedRank")
    void match_shouldReturnCorrectRank(Lotto purchasedLotto, Rank expectedRank) {
        // given: 당첨 번호 1~6, 보너스 7
        Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;
        WinningLotto winningLotto = new WinningLotto(winningNumbers, bonusNumber);

        // when
        Rank actualRank = winningLotto.match(purchasedLotto);

        // then
        assertThat(actualRank).isEqualTo(expectedRank);
    }

    // 테스트 데이터를 제공하는 static 메서드
    static Stream<Arguments> provideLottoAndExpectedRank() {
        return Stream.of(
                // 1등 (6개 일치)
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 6)), Rank.FIRST),
                // 2등 (5개 일치 + 보너스 7 일치)
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 7)), Rank.SECOND),
                // 3등 (5개 일치, 보너스 8 불일치)
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 8)), Rank.THIRD),
                // 4등 (4개 일치)
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 9, 10)), Rank.FOURTH),
                // 5등 (3개 일치)
                Arguments.of(new Lotto(List.of(1, 2, 3, 9, 10, 11)), Rank.FIFTH),
                // 꽝 (2개 일치)
                Arguments.of(new Lotto(List.of(1, 2, 9, 10, 11, 12)), Rank.MISS)
        );
    }
}