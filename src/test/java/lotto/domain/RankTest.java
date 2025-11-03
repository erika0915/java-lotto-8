package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RankTest {

    @DisplayName("일치 개수와 보너스 여부에 따라 정확한 Rank Enum을 반환한다.")
    @ParameterizedTest(name = "{0}개 일치, 보너스 {1} -> {2}")
    @CsvSource({
            "6, true, FIRST",   // 6개 일치 (보너스 상관없이 1등)
            "6, false, FIRST",  // 6개 일치 (보너스 상관없이 1등)
            "5, true, SECOND",  // 5개 일치 + 보너스
            "5, false, THIRD",  // 5개 일치
            "4, true, FOURTH",  // 4개 일치 (보너스 상관없이 4등)
            "4, false, FOURTH", // 4개 일치 (보너스 상관없이 4등)
            "3, true, FIFTH",   // 3개 일치 (보너스 상관없이 5등)
            "3, false, FIFTH"    // 3개 일치 (보너스 상관없이 5등)
    })
    void valueOf_WinningRanks(int matchCount, boolean bonusMatch, Rank expectedRank) {
        // when
        Rank rank = Rank.valueOf(matchCount, bonusMatch);
        // then
        assertThat(rank).isEqualTo(expectedRank);
    }

    @DisplayName("2개 이하 일치 시 보너스 여부와 관계없이 꽝(MISS)을 반환한다.")
    @ParameterizedTest(name = "{0}개 일치, 보너스 {1} -> MISS")
    // 꽝(MISS)이 되는 조건
    @CsvSource({
            "2, true",
            "2, false",
            "1, true",
            "1, false",
            "0, true",
            "0, false"
    })
    void valueOf_MissRanks(int matchCount, boolean bonusMatch) {
        // when
        Rank rank = Rank.valueOf(matchCount, bonusMatch);
        // then
        assertThat(rank).isEqualTo(Rank.MISS);
    }

    @DisplayName("각 Rank가 정확한 당첨금을 반환하는지 확인한다.")
    @Test
    void getPrizeMoney() {
        assertThat(Rank.FIRST.getPrizeMoney()).isEqualTo(2_000_000_000);
        assertThat(Rank.SECOND.getPrizeMoney()).isEqualTo(30_000_000);
        assertThat(Rank.THIRD.getPrizeMoney()).isEqualTo(1_500_000);
        assertThat(Rank.FOURTH.getPrizeMoney()).isEqualTo(50_000);
        assertThat(Rank.FIFTH.getPrizeMoney()).isEqualTo(5_000);
        assertThat(Rank.MISS.getPrizeMoney()).isEqualTo(0);
    }
}
