package cc.oit.bsmes.pla.schedule.matcher.candidateHandler;

import cc.oit.bsmes.pla.schedule.model.Strategy;
import org.springframework.stereotype.Component;

/**
 * Created by 羽霓 on 2014/4/14.
 */
@Component
public class SameWOMatCodeCandidatesHandler extends MatCodeCandidatesHandler {

    public SameWOMatCodeCandidatesHandler() {
        strategy = new Strategy();
        strategy.setNewWorkerOrder(false);
        strategy.setIncludeSetUpTime(false);
        strategy.setIncludeShutDownTime(false);
        strategy.setLatest(false);
        strategy.setNotCombine(false);
    }

}
