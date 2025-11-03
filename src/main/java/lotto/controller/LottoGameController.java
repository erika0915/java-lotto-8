package lotto.controller;

import lotto.domain.Lotto;
import lotto.dto.LottoResponse;
import lotto.service.LottoIssuer;
import lotto.service.LottoStatisticCalculator;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoGameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoIssuer lottoIssuer;
    private final LottoStatisticCalculator lottoStatisticCalculator;

    public LottoGameController(){
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.lottoIssuer = new LottoIssuer();
        this.lottoStatisticCalculator = new LottoStatisticCalculator();
    }

    public void run(){
        // 로또 구입
        int purchaseAmount = inputView.readPurchaseAmount();
        List<Lotto> lottos = lottoIssuer.issueLotto(purchaseAmount);
        outputView.printPurchasedLotto(lottos);

        // 당첨 번호 입력
        List<Integer> winningNumbers = inputView.readWinningNumbers();
        Lotto winningLotto = new Lotto(winningNumbers);

        // 보너스 번호 입력
        int bonusNumber = inputView.readBonusNumber(winningNumbers);

        // 통계 계산
        LottoResponse response = lottoStatisticCalculator.calculate(lottos, winningLotto, bonusNumber);

        // 결과 출력
        outputView.printStatistics(response);
        outputView.printProfitRate(response.getProfitRate());
    }
}
