package lotto.domain;

import lotto.constant.ErrorMessage;

public class WinningLotto {
    private final Lotto winningNumbers;
    private final int bonusNumber;

    public WinningLotto(Lotto winningNumbers, int bonusNumber) {
        validateBonusNumber(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    // 보너스 번호가 당첨 번호 6개와 중복되는지 검증
    private void validateBonusNumber(Lotto winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_IN_WINNING_NUMBERS.getMessage());
        }
    }

    // 구매한 로또와 비교하여 Rank(등수)를 반환
    public Rank match(Lotto purchasedLotto) {
        int matchCount = countMatchingNumbers(purchasedLotto);
        boolean matchBonus = purchasedLotto.contains(this.bonusNumber);

        return Rank.valueOf(matchCount, matchBonus);
    }

    // 구매 로또의 번호와 당첨 번호가 몇 개 일치하는지 계산
    private int countMatchingNumbers(Lotto purchasedLotto) {
        return (int) purchasedLotto.getNumbers().stream()
                .filter(winningNumbers.getNumbers()::contains)
                .count();
    }
}