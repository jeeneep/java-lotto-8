package lotto.view;


import lotto.domain.Lotto;
import lotto.domain.LottoResults;

import java.util.List;


public class OutputView {

    public void printPurchaseCount(int count) {
        System.out.println("\n" + count + "개를 구매했습니다.");
    }

    public void printPurchasedLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            System.out.println(lotto.toString());
        }
    }

    public void printLottoResult(LottoResults results) {
        System.out.println("\n당첨 통계");
        System.out.println("---");

        String formattedResult = results.getFormattedResults();
        System.out.print(formattedResult);
    }

    public void printRateOfReturn(String rate) {
        System.out.println("총 수익률은 " + rate + "%입니다.");
    }
}