package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.dto.LottoResponse;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoStatisticCalculator {

    private static final int LOTTO_PRICE = 1000;

    public LottoResponse calculate(List<Lotto> userLottos, Lotto winningLotto, int bonusNumber){
        Map<Rank, Integer> statistics = initializeStatisticsMap();
        long totalPrize = 0;

        for (Lotto userLotto : userLottos) {
            Rank rank = determineRank(userLotto, winningLotto, bonusNumber);
            statistics.put(rank, statistics.get(rank) + 1);
            totalPrize += rank.getPrizeMoney();
        }
        long totalSpent = (long) userLottos.size() * LOTTO_PRICE;
        double profitRate = calculateProfitRate(totalPrize, totalSpent);

        return new LottoResponse(statistics, profitRate);
    }

    private Map<Rank, Integer> initializeStatisticsMap(){
        Map<Rank, Integer> statistics = new EnumMap<>(Rank.class);
        for (Rank rank : Rank.values()){
            statistics.put(rank, 0);
        }
        return statistics;
    }

    private Rank determineRank(Lotto userLotto, Lotto winningLotto, int bonusNumber){
        int matchCount = userLotto.countMatches(winningLotto);
        boolean hasBonus = userLotto.contains(bonusNumber);
        return Rank.valueOf(matchCount, hasBonus);
    }

    private double calculateProfitRate(long totalPrize, long totalSpent){
        if (totalSpent == 0){
            return 0.0;
        }
        return ((double) totalPrize / totalSpent) * 100.0;
    }
}
