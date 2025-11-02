package lotto.constant;

public final class LottoConstant {
    // 로또 번호 관련 상수
    public static final int LOTTO_NUMBER_COUNT = 6;
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 45;

    // 로또 구입 관련 상수
    public static final int LOTTO_PRICE = 1000;

    // 수익률 관련 상수 (소수점 첫째 자리까지 출력)
    public static final int ROUNDING_SCALE = 1;

    private LottoConstant() {
        // 인스턴스화 방지
    }
}