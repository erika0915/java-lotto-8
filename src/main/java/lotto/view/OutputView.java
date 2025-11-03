package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.dto.LottoResponse;

import java.util.List;
import java.util.Map;

public class OutputView {

    public void printPurchasedLotto(List<Lotto> lottos){
        System.out.println();
        System.out.println(lottos.size() + "개를 구매했습니다.");

        for (Lotto lotto : lottos){
            System.out.println(lotto.toString());
        }
    }

    public void printStatistics(LottoResponse response){
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");

        List<Rank> ranksToDisplay = List.of(
                Rank.FIFTH, Rank.FOURTH, Rank.THIRD, Rank.SECOND, Rank.FIRST
        );

        Map<Rank, Integer> statistics = response.getStatistics();

        for (Rank rank : ranksToDisplay){
            System.out.printf("%s (%,d원) - %d개\n",
                    rank.getDescription(),
                    rank.getPrizeMoney(),
                    statistics.getOrDefault(rank, 0));
        }
    }

    public void printProfitRate(double profitRate){
        System.out.printf("총 수익률은 %,.1f%%입니다.\n", profitRate);
    }
}
