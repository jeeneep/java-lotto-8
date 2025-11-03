package lotto.service;

import lotto.constant.LottoConstant;
import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.domain.LottoResults;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LottoService {

    private final LottoMachine lottoMachine;

    public LottoService() {
        this.lottoMachine = new LottoMachine();
    }

    private int calculateLottoCount(int purchaseAmount) {
        return purchaseAmount / LottoConstant.LOTTO_PRICE;
    }

    // 로또 발행
    public List<Lotto> purchaseLottos(int purchaseAmount) {
        int lottoCount = calculateLottoCount(purchaseAmount);

        List<Lotto> purchasedLottos = new ArrayList<>();
        for (int i = 0; i < lottoCount; i++) {
            purchasedLottos.add(lottoMachine.generate());
        }

        return purchasedLottos;
    }

    // --- 통계 및 수익률 계산 ---

    // 당첨 통계 집계하여 LottoResults 객체를 반환
    public LottoResults calculateResults(List<Lotto> purchasedLottos, WinningLotto winningLotto) {
        Map<Rank, Integer> resultsMap = initializeResultsMap();

        purchasedLottos.stream()
                .map(winningLotto::match)
                .filter(rank -> rank != Rank.MISS)
                .forEach(rank -> resultsMap.put(rank, resultsMap.get(rank) + 1));

        return new LottoResults(resultsMap);
    }

    // 통계 맵 초기화
    private Map<Rank, Integer> initializeResultsMap() {
        return Arrays.stream(Rank.values())
                .filter(rank -> rank != Rank.MISS)
                .collect(Collectors.toMap(
                        rank -> rank,
                        rank -> 0
                ));
    }

}