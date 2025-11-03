package lotto.dto;

import lotto.domain.Rank;

import java.util.Map;

public class LottoResponse {

    private final Map<Rank, Integer> statistics;
    private final double profitRate;

    public LottoResponse(Map<Rank, Integer> statistics, double profitRate) {
        this.statistics = statistics;
        this.profitRate = profitRate;
    }

    public Map<Rank, Integer> getStatistics() {
        return statistics;
    }

    public double getProfitRate() {
        return profitRate;
    }
}
