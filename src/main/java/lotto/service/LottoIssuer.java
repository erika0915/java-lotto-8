package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.Lotto;

import java.util.ArrayList;
import java.util.List;

public class LottoIssuer {

    private static final int LOTTO_PRICE = 1000;
    private static final int LOTTO_NUMBER_MIN = 1;
    private static final int LOTTO_NUMBER_MAX = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

    public List<Lotto> issueLotto(int purchaseAmount){
        int numberOfLottos = calculateNumberOfLottos(purchaseAmount);
        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < numberOfLottos; i++) {
            lottos.add(createLotto());
        }
        return lottos;
    }

    public int calculateNumberOfLottos(int purchaseAmount){
        return purchaseAmount/LOTTO_PRICE;
    }

    private Lotto createLotto(){
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                LOTTO_NUMBER_MIN,
                LOTTO_NUMBER_MAX,
                LOTTO_NUMBER_COUNT
        );
        return new Lotto(numbers);
    }
}
