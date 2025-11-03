package lotto.domain;

import lotto.constant.LottoConstant;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class LottoResults {
    private final Map<Rank, Integer> results;

    public LottoResults(Map<Rank, Integer> results) {
        this.results = results;
    }

    public Map<Rank, Integer> getResults() {
        return results;
    }

    // 총 상금 계산
    private long calculateTotalPrize() {
        return results.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
    }

    // 수익률 계산 및 포맷팅
    public String calculateRateOfReturn(int purchaseAmount) {
        long totalPrize = calculateTotalPrize();
        double rate = (double) totalPrize / purchaseAmount * 100;

        return String.format("%." + LottoConstant.ROUNDING_SCALE + "f", rate);
    }

    // 출력 포맷팅 로직
    public String getFormattedResults() {
        return results.entrySet().stream()
                .filter(entry -> entry.getKey() != Rank.MISS)
                .sorted(Comparator.comparing(entry -> entry.getKey().getPrizeMoney()))
                .map(entry -> formatRankOutput(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining());
    }

    private String formatRankOutput(Rank rank, int count) {
        return rank.getMessage() + " - " + count + "개\n";
    }
}