package lotto.view;

import java.util.Comparator;
import lotto.domain.Lotto;
import lotto.domain.Rank;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    public void printPurchaseCount(int count) {
        System.out.println("\n" + count + "개를 구매했습니다.");
    }

    public void printPurchasedLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            System.out.println(lotto.toString());
        }
    }

    public void printLottoResult(Map<Rank, Integer> results) {
        System.out.println("\n당첨 통계");
        System.out.println("---");

        String formattedResult = results.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().getPrizeMoney()))
                .map(entry -> formatRankOutput(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining());

        System.out.print(formattedResult);
    }

    private String formatRankOutput(Rank rank, int count) {
        return rank.getMessage() + " - " + count + "개\n";
    }

    public void printRateOfReturn(String rate) {
        System.out.println("총 수익률은 " + rate + "%입니다.");
    }
}