package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.validation.InputValidator;

import java.util.List;

public class InputView {

    public int readPurchaseAmount(){
        while(true){
            try{
                System.out.println("구입금액을 입력해 주세요.");
                String input = Console.readLine();
                return InputValidator.validatePurchaseAmount(input);
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Integer> readWinningNumbers(){
        while(true){
            try{
                System.out.println();
                System.out.println("당첨 번호를 입력해주세요.");
                String input = Console.readLine();
                return InputValidator.validateWinningNumbers(input);
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public int readBonusNumber(List<Integer> winningNumbers){
        while(true){
            try{
                System.out.println();
                System.out.println("보너스 번호를 입력해주세요.");
                String input = Console.readLine();
                return InputValidator.validateBonusNumber(input, winningNumbers);
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
