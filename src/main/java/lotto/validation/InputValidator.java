package lotto.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputValidator {

    private static final int LOTTO_PRICE = 1000;
    private static final int LOTTO_NUMBER_MIN = 1;
    private static final int LOTTO_NUMBER_MAX = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

    public InputValidator(){}

    // 구입 금액 검증
    public static int validatePurchaseAmount(String input){
        int amount = validateNumeric(input, "[ERROR] 구입 금액은 숫자여야 합니다.");
        validatePositive(amount);
        validateMultipleOfLottoPrice(amount);
        return amount;
    }

    private static int validateNumeric(String input, String errorMessage){
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e){
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static void validatePositive(int amount){
        if (amount <= 0){
            throw new IllegalArgumentException("[ERROR] 구입 금액은 0보다 커야 합니다.");
        }
    }

    private static void validateMultipleOfLottoPrice(int amount){
        if (amount % LOTTO_PRICE != 0){
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

    // 당첨 번호 검증
    public static List<Integer> validateWinningNumbers(String input){
        String[] numberStrings = input.split(",");
        validateWinningNumberCount(numberStrings);

        List<Integer> numbers = parseNumbers(numberStrings);
        validateNumbersRange(numbers);
        validateNoDuplicates(numbers);

        return numbers;
    }

    private static void validateWinningNumberCount(String[] numberString){
        if (numberString.length != LOTTO_NUMBER_COUNT){
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개여야 합니다.");
        }
    }

    private static List<Integer> parseNumbers(String[] numberStrings){
        try{
            List<Integer> numbers = new ArrayList<>();
            for (String s : numberStrings){
                numbers.add(Integer.parseInt(s.trim()));
            }
            return numbers;
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자여야 합니다.");
        }
    }

    private static void validateNumbersRange(List<Integer> numbers){
        for (int number : numbers){
            validateNumberRange(number);
        }
    }
    private static void validateNoDuplicates(List<Integer> numbers){
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 중복될 수 없습니다.");
        }
    }

    private static void validateNumberRange(int number){
        if (number < LOTTO_NUMBER_MIN || number > LOTTO_NUMBER_MAX){
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    // 보너스 번호 검증
    public static int validateBonusNumber(String input, List<Integer> winningNumbers){
        int bonusNumber = validateNumeric(input, "[ERROR] 보너스 번호는 숫자여야 합니다.");
        validateNumberRange(bonusNumber);
        validateBonusNotInWinningNumbers(bonusNumber, winningNumbers);
        return bonusNumber;
    }

    private static void validateBonusNotInWinningNumbers(int bonusNumber, List<Integer> winningNumbers){
        if (winningNumbers.contains(bonusNumber)){
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
