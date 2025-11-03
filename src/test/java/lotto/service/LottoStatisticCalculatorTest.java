package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.dto.LottoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

public class LottoStatisticCalculatorTest {

    private LottoStatisticCalculator lottoStatisticCalculator;

    @BeforeEach
    void setUp(){
        lottoStatisticCalculator = new LottoStatisticCalculator();
    }

    @DisplayName("ApplicationTest 예시(5등 1개)일 때 통계 및 수익률 62.5% 반환")
    @Test
    void calculate_ApplicationTest_Example() {
        // given
        // 1. ApplicationTest에 제공된 고정 랜덤값 8개 (총 8000원 투자)
        List<Lotto> userLottos = List.of(
                new Lotto(List.of(8, 21, 23, 41, 42, 43)),
                new Lotto(List.of(3, 5, 11, 16, 32, 38)),
                new Lotto(List.of(7, 11, 16, 35, 36, 44)),
                new Lotto(List.of(1, 8, 11, 31, 41, 42)),
                new Lotto(List.of(13, 14, 16, 38, 42, 45)),
                new Lotto(List.of(7, 11, 30, 40, 42, 43)),
                new Lotto(List.of(2, 13, 22, 32, 38, 45)),
                new Lotto(List.of(1, 3, 5, 14, 22, 45))
        );

        // 2. 당첨 번호와 보너스 번호
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        // when
        LottoResponse result = lottoStatisticCalculator.calculate(userLottos, winningLotto, bonusNumber);

        // then
        // 1. 통계 맵 검증
        Map<Rank, Integer> statistics = result.getStatistics();
        assertThat(statistics.get(Rank.FIFTH)).isEqualTo(1); // 5등 1개
        assertThat(statistics.get(Rank.FOURTH)).isEqualTo(0);
        assertThat(statistics.get(Rank.THIRD)).isEqualTo(0);
        assertThat(statistics.get(Rank.SECOND)).isEqualTo(0);
        assertThat(statistics.get(Rank.FIRST)).isEqualTo(0);
        assertThat(statistics.get(Rank.MISS)).isEqualTo(7); // 꽝 7개

        // 2. 수익률 검증 (5000 / 8000 * 100 = 62.5)
        assertThat(result.getProfitRate()).isEqualTo(62.5);
    }

    @DisplayName("다양한 등수가 포함된 복잡한 통계 및 수익률을 정확히 계산한다.")
    @Test
    void calculate_ComplexExample() {
        // given
        // 총 7장 (7000원 투자)
        List<Lotto> userLottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),   // 1등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),   // 2등 (보너스 7)
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),   // 3등
                new Lotto(List.of(1, 2, 3, 4, 8, 9)),   // 4등
                new Lotto(List.of(1, 2, 3, 8, 9, 10)),  // 5등
                new Lotto(List.of(10, 11, 12, 13, 14, 15)), // 꽝
                new Lotto(List.of(4, 5, 6, 20, 21, 22))  // 5등 (3개 일치)
        );

        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        // when
        LottoResponse result = lottoStatisticCalculator.calculate(userLottos, winningLotto, bonusNumber);

        // then
        // 1. 통계 맵 검증
        Map<Rank, Integer> statistics = result.getStatistics();
        assertThat(statistics.get(Rank.FIRST)).isEqualTo(1);
        assertThat(statistics.get(Rank.SECOND)).isEqualTo(1);
        assertThat(statistics.get(Rank.THIRD)).isEqualTo(1);
        assertThat(statistics.get(Rank.FOURTH)).isEqualTo(1);
        assertThat(statistics.get(Rank.FIFTH)).isEqualTo(2); // 5등은 2개
        assertThat(statistics.get(Rank.MISS)).isEqualTo(1);

        // 2. 수익률 검증
        // 총 당첨금: 2,000,000,000 + 30,000,000 + 1,500,000 + 50,000 + (2 * 5,000) = 2,031,560,000원
        // 총 투자금: 7,000원
        // 수익률 = (2031560000 / 7000) * 100 = 29022285.714...
        double expectedProfitRate = (double) 2_031_560_000L / 7_000 * 100;

        // 소수점 오차를 감안하여 0.001 범위 내에서 비교
        assertThat(result.getProfitRate()).isCloseTo(expectedProfitRate, within(0.001));
    }

    @DisplayName("모두 꽝일 경우 수익률 0.0% 반환")
    @Test
    void calculate_AllMiss() {
        // given
        List<Lotto> userLottos = List.of(
                new Lotto(List.of(10, 11, 12, 13, 14, 15)),
                new Lotto(List.of(20, 21, 22, 23, 24, 25))
        ); // 총 2장 (2000원 투자)

        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        // when
        LottoResponse result = lottoStatisticCalculator.calculate(userLottos, winningLotto, bonusNumber);

        // then
        assertThat(result.getStatistics().get(Rank.MISS)).isEqualTo(2);
        assertThat(result.getProfitRate()).isEqualTo(0.0);
    }
}
