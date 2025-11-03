package lotto.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static lotto.validation.InputValidator.*;
import static org.assertj.core.api.Assertions.*;

public class InputValidatorTest {

    @DisplayName("구입 금액이 숫자가 아니면 예외 발생")
    @Test
    void validatePurchaseAmountNotNumeric(){
        assertThatThrownBy(()-> validatePurchaseAmount("1000j"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 숫자여야 합니다.");
    }

    @DisplayName("구입 금액이 0 또는 음수면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1000"})
    void validatePurchaseAmount(String input){
        assertThatThrownBy(()-> validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 0보다 커야 합니다.");
    }

    @DisplayName("구입 금액이 1000원 단위가 아니면 예외 발생")
    @Test
    void validatePurchaseAmountNotMultipleOf1000(){
        assertThatThrownBy(()-> validatePurchaseAmount("1500"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 0보다 커야 합니다.");
    }

    @DisplayName("당첨 번호 개수가 6개가 아니면 예외 발생")
    @Test
    void validateWinningNumbersCount(){
        assertThatThrownBy(()-> validateWinningNumbers("1,2,3,4,5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 6개여야 합니다.");
    }

    @DisplayName("당첨 번호 중복 시 예외 발생")
    @Test
    void validateWinningNumbersDuplicate(){
        assertThatThrownBy(()-> validateWinningNumbers("1,2,3,4,5,5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 중복될 수 없습니다.");
    }

    @DisplayName("정상 당첨 번호는 검증 통과")
    @Test
    void validateWinningNumbersSuccess(){
        List<Integer> numbers = validateWinningNumbers("1,2,3,4,5,6");
        assertThat(numbers).containsExactly(1,2,3,4,5,6);
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외 발생")
    @Test
    void validateBonusNumberDuplicate(){
        List<Integer> winningNumbers = List.of(1,2,3,4,5,6);
        String bonusInput = "6";
        assertThatThrownBy(()-> validateBonusNumber(bonusInput, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }
}
