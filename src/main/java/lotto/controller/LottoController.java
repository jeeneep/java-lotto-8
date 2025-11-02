package lotto.controller;

import lotto.constant.ErrorMessage;
import lotto.constant.LottoConstant;
import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.lottoService = new LottoService();
    }

    public void run() {
        try {
            // 구입 금액 입력 및 로또 발행
            int purchaseAmount = readValidPurchaseAmount();
            List<Lotto> purchasedLottos = purchaseAndPrintLottos(purchaseAmount);

            // 당첨 번호 및 보너스 번호 입력
            WinningLotto winningLotto = readValidWinningLotto();

            // 결과 계산 및 출력
            Map<Rank, Integer> results = lottoService.calculateResults(purchasedLottos, winningLotto);
            printResults(results, purchaseAmount);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // --- 구입 금액 입력 및 로또 발행 ---

    private int readValidPurchaseAmount() {
        while (true) {
            try {
                String input = inputView.readPurchaseAmount();
                int amount = parseInputToInteger(input);
                validateAmountUnit(amount);
                return amount;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int parseInputToInteger(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_A_NUMBER.getMessage());
        }
    }

    private void validateAmountUnit(int amount) {
        if (amount < LottoConstant.LOTTO_PRICE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PURCHASE_AMOUNT.getMessage());
        }
        if (amount % LottoConstant.LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PURCHASE_AMOUNT.getMessage());
        }
    }

    private List<Lotto> purchaseAndPrintLottos(int purchaseAmount) {
        List<Lotto> purchasedLottos = lottoService.purchaseLottos(purchaseAmount);
        outputView.printPurchaseCount(purchasedLottos.size());
        outputView.printPurchasedLottos(purchasedLottos);
        return purchasedLottos;
    }

    // --- 당첨 번호 및 보너스 번호 입력 ---

    private WinningLotto readValidWinningLotto() {
        Lotto winningNumbers = readWinningNumbersWithRetry();
        int bonusNumber = readBonusNumberWithRetry();

        return new WinningLotto(winningNumbers, bonusNumber);
    }

    private Lotto readWinningNumbersWithRetry() {
        while (true) {
            try {
                String input = inputView.readWinningNumbers();
                List<Integer> numbers = parseWinningNumbers(input);
                return new Lotto(numbers); // Lotto 생성자에서 유효성 검증
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readBonusNumberWithRetry() {
        while (true) {
            try {
                String input = inputView.readBonusNumber();
                int number = parseInputToInteger(input);
                validateBonusRange(number);
                return number;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Integer> parseWinningNumbers(String input) {
        try {
            String[] tokens = input.split(",");
            return Arrays.stream(tokens)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_A_NUMBER.getMessage());
        }
    }

    private void validateBonusRange(int number) {
        if (number < LottoConstant.MIN_NUMBER || number > LottoConstant.MAX_NUMBER) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_RANGE_NUMBER.getMessage());
        }
    }

    // --- 결과 출력 ---

    private void printResults(Map<Rank, Integer> results, int purchaseAmount) {
        outputView.printLottoResult(results);
        String rateOfReturn = lottoService.calculateRateOfReturn(results, purchaseAmount);
        outputView.printRateOfReturn(rateOfReturn);
    }
}