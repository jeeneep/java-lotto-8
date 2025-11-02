package lotto.domain;

import java.util.Arrays;

public enum Rank {
    FIRST(6, false, 2_000_000_000, "6개 일치 (2,000,000,000원)"),
    SECOND(5, true, 30_000_000, "5개 일치, 보너스 볼 일치 (30,000,000원)"),
    THIRD(5, false, 1_500_000, "5개 일치 (1,500,000원)"),
    FOURTH(4, false, 50_000, "4개 일치 (50,000원)"),
    FIFTH(3, false, 5_000, "3개 일치 (5,000원)"),
    MISS(0, false, 0, "낙첨");

    private final int matchCount;
    private final boolean matchBonus;
    private final long prizeMoney;
    private final String message;

    Rank(int matchCount, boolean matchBonus, long prizeMoney, String message) {
        this.matchCount = matchCount;
        this.matchBonus = matchBonus;
        this.prizeMoney = prizeMoney;
        this.message = message;
    }

    public long getPrizeMoney() {
        return prizeMoney;
    }

    public String getMessage() {
        return message;
    }

    // 일치 개수와 보너스 일치 여부를 받아 Rank를 반환
    public static Rank valueOf(int matchCount, boolean matchBonus) {
        if (matchCount < FIFTH.matchCount) {
            return MISS; // 3개 미만은 무조건 꽝 (조기 반환)
        }

        if (matchCount == SECOND.matchCount && matchBonus) {
            return SECOND; // 5개 일치 + 보너스 일치 = 2등 (조기 반환)
        }

        // 1, 3, 4, 5등을 찾는 로직
        return Arrays.stream(values())
                .filter(rank -> rank.matchCount == matchCount)
                .filter(rank -> !rank.matchBonus) // 보너스 불일치 조건 (2등 제외)
                .filter(rank -> rank != MISS)
                .findFirst()
                .orElse(MISS);
    }
}