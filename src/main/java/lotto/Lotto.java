package lotto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        if (hasDuplicates(numbers)) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
        if (isNotInRange(numbers)) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private boolean hasDuplicates(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        return uniqueNumbers.size() != numbers.size();
    }

    private boolean isNotInRange(List<Integer> numbers){
        for (int number : numbers){
            if (number < 1 || number > 45){
                return true;
            }
        }
        return false;
    }

    // 이 로또가 당첨 번호와 몇 개 일치하는 지 계산
    public int countMatches(Lotto winningLotto) {
        int matchCount = 0;
        for (int number : this.numbers){
            if (winningLotto.contains(number)){
                matchCount++;
            }
        }
        return matchCount;
    }

    public boolean contains(int number){
        return numbers.contains(number);
    }

    // 출력 시 오름차순으로 정렬된 문자열을 반환
    public String toString(){
        List<Integer> sortedNumbers = numbers.stream().sorted().toList();
        return sortedNumbers.toString();
    }
}
