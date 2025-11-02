package lotto.domain;

import lotto.constant.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {

    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_LOTTO_SIZE.getMessage()); // 추가: 메시지 검증
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_LOTTO_NUMBER.getMessage()); // 추가: 메시지 검증
    }

    // --- 추가된 검증 로직 테스트 ---

    @DisplayName("로또 번호가 1 미만이면 예외가 발생한다.")
    @Test
    void 로또_번호가_1_미만이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(0, 2, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.OUT_OF_RANGE_NUMBER.getMessage());
    }

    @DisplayName("로또 번호가 45 초과면 예외가 발생한다.")
    @Test
    void 로또_번호가_45_초과면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.OUT_OF_RANGE_NUMBER.getMessage());
    }

    // --- 추가 기능 테스트 ---

    @DisplayName("로또에 특정 번호가 포함되어 있으면 true를 반환한다.")
    @Test
    void 로또에_특정_번호가_포함되어_있는지_확인한다() {
        // given
        Lotto lotto = new Lotto(List.of(1, 10, 20, 30, 40, 45));

        // when & then
        assertThat(lotto.contains(10)).isTrue();
        assertThat(lotto.contains(15)).isFalse();
    }

    @DisplayName("로또 번호 리스트를 반환할 수 있어야 한다.")
    @Test
    void 로또_번호_리스트를_반환한다() {
        // given
        List<Integer> expectedNumbers = List.of(1, 2, 3, 4, 5, 6);
        Lotto lotto = new Lotto(expectedNumbers);

        // when
        List<Integer> actualNumbers = lotto.getNumbers();

        // then
        assertThat(actualNumbers).containsExactlyInAnyOrderElementsOf(expectedNumbers);
    }
}