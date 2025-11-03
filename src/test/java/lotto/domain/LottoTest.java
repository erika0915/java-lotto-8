package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {

    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void createLottoByOverSize(){
        assertThatThrownBy(()-> new Lotto(List.of(1,2,3,4,5,6,7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 로또 번호는 6개여야 합니다.");
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void createLottoByDuplicatedNumbers(){
        assertThatThrownBy(()-> new Lotto(List.of(1,2,3,4,5,5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 로또 번호는 중복될 수 없습니다.");
    }

    @DisplayName("로또 번호가 1-45 범위를 벗어나면 예외가 발생한다. (46)")
    @Test
    void createLottoByOutOfRangeOver(){
        assertThatThrownBy(()-> new Lotto(List.of(1,2,3,4,5,46)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    @DisplayName("로또 번호가 1-45 범위를 벗어나면 예외가 발생한다. (0)")
    @Test
    void createLottoByOutOfRangeUnder(){
        assertThatThrownBy(()-> new Lotto(List.of(1,2,3,4,5,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    @DisplayName("정상적인 로또 번호로 객체를 생성할 수 있다.")
    @Test
    void createLottoSuccess(){
        List<Integer> numbers = List.of(1,2,3,4,5,6);
        Lotto lotto = new Lotto(numbers);
        assertThat(lotto).isNotNull();
    }

    @DisplayName("당첨 번호와 일치하는 개수를 정확히 반환한다.")
    @Test
    void countMatches(){
        Lotto userLotto = new Lotto(List.of(1,2,3,4,5,6));
        Lotto winningLottos = new Lotto(List.of(1,2,3,10,11,12));
        int matches = userLotto.countMatches(winningLottos);
        assertThat(matches).isEqualTo(3);
    }

    @DisplayName("보너스 번호 포함 여부를 정확히 반환한다.")
    @Test
    void containBonus(){
        Lotto userLotto = new Lotto(List.of(1,2,3,4,5,7));
        int bonusNumber = 7;
        int nonBonusNumber = 8;

        boolean hasBonus = userLotto.contains(bonusNumber);
        boolean hasNonBonus = userLotto.contains(nonBonusNumber);

        assertThat(hasBonus).isTrue();
        assertThat(hasNonBonus).isFalse();
    }
}
