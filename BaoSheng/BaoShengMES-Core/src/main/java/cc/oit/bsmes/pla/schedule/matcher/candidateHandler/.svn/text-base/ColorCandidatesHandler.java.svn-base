package cc.oit.bsmes.pla.schedule.matcher.candidateHandler;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 羽霓 on 2014/4/14.
 */
@Component
public class ColorCandidatesHandler extends DefaultCandidatesHandler {

    @Override
    public String getMapKey() {
        return "color";
    }

    @Override
    public String getCandidatesKey(CustomerOrderItemProDec order) {
        return getColor(order);
    }

    @Override
    public Collection<CustomerOrderItemProDec> getCandidates(Multimap<String, CustomerOrderItemProDec> orders, CustomerOrderItemProDec lastOrder) {
        Collection<CustomerOrderItemProDec> candidateOrders = new ArrayList<CustomerOrderItemProDec>();
        if (orders == null) {
            return candidateOrders;
        }

        //Color color = getColor(lastOrder); // 色值增加
        String color = getColor(lastOrder);
        candidateOrders.addAll(orders.get(color));

        while(true) {
            color = increaseOrder(color);
            if (color == null) {
                break;
            }
            Collection<CustomerOrderItemProDec> collection = orders.get(color);
            candidateOrders.addAll(collection);
        }
        return candidateOrders;
    }

    private String getColor(CustomerOrderItemProDec order) {
        Mat mat = order.getProcessOut().getMat();
        return mat == null?"":mat.getColor();
    }

    private String increaseOrder(String color) {
        List<DataDic> list = StaticDataCache.getByTermsCode(TermsCodeType.DATA_PRODUCT_COLOR.name());
        for(int i = 0;i<list.size();i++){
            DataDic dataDic = list.get(i);
            if(dataDic.getCode().equals(color)){
                if(i++ == list.size()){
                    return null;
                }else{
                    return list.get(i).getCode();
                }
            }
        }
        return null;
    }

}
