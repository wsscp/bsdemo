package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.ParmsMapping;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by JIN on 2015/5/7.
 */
public class ParmsMappingDAOTest extends BaseTest{
    @Resource
    private ParmsMappingDAO parmsMappingDAO;

    @Test
    @Rollback(false)
    public void distinctData(){

        List<Map<String,Object>> result = parmsMappingDAO.getParmsMapping();
        for (Map<String, Object> stringStringMap : result) {
            if(Integer.parseInt(String.valueOf(stringStringMap.get("NUM"))) > 1){
                ParmsMapping findParams = new ParmsMapping();
                findParams.setEquipCode(String.valueOf(stringStringMap.get("EQUIP_CODE")));
                findParams.setParmCode(String.valueOf(stringStringMap.get("PARM_CODE")));
                List<ParmsMapping> list = parmsMappingDAO.get(findParams);
                parmsMappingDAO.delete(list.get(0).getId());
            }
        }
    }
}
