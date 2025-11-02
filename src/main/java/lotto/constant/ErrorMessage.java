package lotto.constant;

public enum ErrorMessage {
    // 공통 에러 접두사
    ERROR_PREFIX("[ERROR] "),

    // Lotto 유효성 검증 에러
    INVALID_LOTTO_SIZE("로또 번호는 " + LottoConstant.LOTTO_NUMBER_COUNT + "개여야 합니다."),
    DUPLICATE_LOTTO_NUMBER("로또 번호에 중복된 숫자가 있습니다."),
    OUT_OF_RANGE_NUMBER("로또 번호는 " + LottoConstant.MIN_NUMBER + "부터 " + LottoConstant.MAX_NUMBER + " 사이의 숫자여야 합니다."),

    // 구입 금액 유효성 검증 에러
    INVALID_PURCHASE_AMOUNT("구입 금액은 " + LottoConstant.LOTTO_PRICE + "원 단위여야 합니다."),
    NOT_A_NUMBER("입력은 숫자여야 합니다."),

    // 보너스 번호 유효성 검증 에러
    BONUS_NUMBER_IN_WINNING_NUMBERS("보너스 번호가 당첨 번호와 중복됩니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX.message + message;
    }
}