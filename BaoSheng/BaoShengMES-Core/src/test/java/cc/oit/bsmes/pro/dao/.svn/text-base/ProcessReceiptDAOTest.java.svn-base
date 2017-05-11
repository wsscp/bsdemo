package cc.oit.bsmes.pro.dao;


import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessQcValue;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.model.ProductProcess;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class ProcessReceiptDAOTest extends BaseTest {

	@Resource
	private ProductProcessDAO productProcessDAO;
	@Resource
	private ProcessReceiptDAO processReceiptDAO;
	@Resource
	private EquipListDAO equipListDAO;
    @Resource
    private ProcessQcValueDAO processQcValueDAO;
	
	@Test
	public void test() {
        boolean flag = true;
		iterator:for(int i = 0;i<10;i++){
            if(flag){
                for(int j=11;j<20;j++){
                    if(j % 2 ==0){
                        break iterator;
                    }
                    System.out.println("i="+i+",j="+j);
                }
            }
            System.out.println("i==="+i*5);
        }
		
	}
	
	
	@Test
	public void test2() {
		
		List<EquipList> equipList = equipListDAO.getAll();
		int i = 1;
		for(EquipList eq:equipList){
			ProductProcess pro = productProcessDAO.getById(eq.getProcessId());
			ProcessReceipt pr = new ProcessReceipt();
			pr.setReceiptCode(pro.getProcessCode());
			pr.setReceiptName(pro.getProcessName());
			pr.setReceiptTargetValue(i+"");
			pr.setSubReceiptCode(pro.getProcessCode());
			pr.setSubReceiptName(pro.getProcessName());
			pr.setEqipListId(eq.getId());
			processReceiptDAO.insert(pr);
			i++;
		}
	}

    @Test
    public void testfindByProductCode(){
        ProcessQcValue findParams = new ProcessQcValue();
        findParams.setProductCode("%500%");
        List<ProcessQcValue> list = processQcValueDAO.find(findParams);
        System.out.println(list.size());
    }

}
