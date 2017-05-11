package cc.oit.bsmes.pla.schedule.matcher.candidateHandler;

import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import org.springframework.stereotype.Component;

/**
 * Created by 羽霓 on 2014/4/14.
 */
@Component
public class SizeCandidatesHandler extends DefaultCandidatesHandler {

    @Override
    public String getMapKey() {
        return "size";
    }

    @Override
    public String getCandidatesKey(CustomerOrderItemProDec order) {
        Mat mat = order.getProcessOut().getMat();
        return mat == null?"":mat.getSize();
    }

}
