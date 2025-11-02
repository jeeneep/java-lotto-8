package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.constant.LottoConstant;

import java.util.List;

public class LottoMachine {

    public Lotto generate() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                LottoConstant.MIN_NUMBER,
                LottoConstant.MAX_NUMBER,
                LottoConstant.LOTTO_NUMBER_COUNT
        );
        return new Lotto(numbers);
    }
}