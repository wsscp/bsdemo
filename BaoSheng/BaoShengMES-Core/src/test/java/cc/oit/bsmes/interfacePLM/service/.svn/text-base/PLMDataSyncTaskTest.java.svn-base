package cc.oit.bsmes.interfacePLM.service;

import cc.oit.bsmes.common.constants.*;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.interfacePLM.dao.EquipDAO;
import cc.oit.bsmes.interfacePLM.model.*;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.*;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.model.WorkOrder;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by JinHy on 2014/10/8 0008.
 */
public class PLMDataSyncTaskTest extends BaseTest {

    @Resource private ScxService scxService;
    @Resource private MachService machService;
    @Resource private EquipService equipService;
    @Resource private BZProcessService bzProcessService;
    @Resource
    private ProductProcessService productProcessService;
    @Resource
    private ProcessInOutService processInOutService;

    @Resource
    private WorkOrderDAO workOrderDAO;
    @Resource
    private MaterialRequirementPlanService materialRequirementPlanService;

    @Resource private PLMPrcvAsyncTask pLMPrcvAsyncTask;

    @Resource private PLMProductTask plmProductTask;
    @Resource private CanShukuTask canShukuTask;


    @Resource private BZProcessTask bzProcessTask;
    @Resource private EquipUpdateTask equipUpdateTask;

    @Resource private  MatService matService;

    @Test
    @Rollback(false)
    public void scxUpdateTest(){
        JobParams params =  new JobParams();
        params.setOrgCode("1");
        equipUpdateTask.process(params);
    }


    @Test
    @Rollback(false)
    public void testBZProcessTask(){
        JobParams params =  new JobParams();
        params.setOrgCode("1");
        bzProcessTask.process(params);
    }


    @Test
    @Rollback(false)
    public void productProcessEquipAsyncTask(){

//        scxService.syncScxData();
        JobParams params =  new JobParams();
        params.setOrgCode("bstl01");
        //plmProductTask.process(params);
        canShukuTask.process(params);
        //productProcessEquipAsyncTask.process(params);

       // margeYzProcess("01_C9CE906C22904B11AEB34367B28BD8D0");
    }



    private void margeYzProcess(String  productCraftsId) {
        List<ProductProcess> productProcesses = productProcessService.getByProductCraftsId(productCraftsId);
        ProcessInOut out = null;
        ProductProcess lastProcess = null;
        boolean isYZ  = false;
        List<ProductProcess> list = new ArrayList<ProductProcess>();
        for(ProductProcess productProcess : productProcesses){
            if(productProcess.getNextProcessId().equals(WebConstants.ROOT_ID)){
                ProcessInOut processInOut = new ProcessInOut();
                processInOut.setProductProcessId(productProcess.getId());

            }

            if(productProcess.getSameProductLine()) {
                if(productProcess.getProcessName().contains("印字")) {
                    out = processInOutService.getOutByProcessId(productProcess.getId());
                    isYZ = true;
                    list.add(productProcess);


                    continue;
                }
            }

            if(out != null){
                out.setProductProcessId(productProcess.getId());
                processInOutService.update(out);
                productProcess.setProcessName(productProcess.getProcessName()+"-印字");
                out = null;
            }

            if(isYZ){
                if(lastProcess == null){
                    productProcess.setFullPath(productProcess.getId()+";");
                    productProcess.setNextProcessId(WebConstants.ROOT_ID);
                }else{
                    productProcess.setFullPath(lastProcess.getFullPath()+productProcess.getId()+";");
                    productProcess.setNextProcessId(lastProcess.getId());
                }

                productProcessService.update(productProcess);
                lastProcess = productProcess;
            }
        }

        productProcessService.delete(list);

    }


    @Test
    @Rollback(false)
    public void loadMatSection(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("Y101001005012", "0.75");
        map.put("Y101001005024","1.5");
        map.put("Y101001005006","1.0");
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()){
            String matCode  = it.next();
            String matSection = map.get(matCode);
            ProcessInOut findParams = new ProcessInOut();
            findParams.setMatCode(matCode);
            Map<String,Mat> matMap = StaticDataCache.getMatMap();
            List<ProcessInOut> inOuts = processInOutService.getByObj(findParams);
            for(ProcessInOut inOut:inOuts){
                ProcessInOut out = processInOutService.getOutByProcessId(inOut.getProductProcessId());
                Mat mat = matMap.get(out.getMatCode());
                mat.setMatSection(matSection);
                matService.update(mat);

                findParams.setMatCode(out.getMatCode());
                findParams.setInOrOut(InOrOut.IN);
                List<ProcessInOut> inOuts1 = processInOutService.getByObj(findParams);
                for(ProcessInOut inOut1:inOuts1){
                    ProcessInOut out1 = processInOutService.getOutByProcessId(inOut1.getProductProcessId());
                    Mat mat1 = matMap.get(out1.getMatCode());
                    mat1.setMatSection(matSection);
                    matService.update(mat1);

                }
            }
        }
    }

    @Test
    @Rollback(false)
    public void auditWorkOrder(){
        List<WorkOrder> workOrderList = workOrderDAO.getAll();
        for(WorkOrder wo : workOrderList){
            ProductProcess process = StaticDataCache.getProcessByProcessId(wo.getProcessId());
            //根据工序ID查询出工序所有投入的物料
            List<ProcessInOut> inList = StaticDataCache.getByProcessId(process.getId());

            //计算物料需求
            List<MaterialRequirementPlan> mrpResultList = calculatorMRP(process,inList,wo,new BigDecimal(wo.getOrderLength()));
            materialRequirementPlanService.insert(mrpResultList);

            //计算工装夹具需求 (已写入存储过程)

        }
    }

    private List<MaterialRequirementPlan> calculatorMRP(ProductProcess process,List<ProcessInOut> inList,WorkOrder wo,BigDecimal length){
        List<MaterialRequirementPlan> result = new ArrayList<MaterialRequirementPlan>();
        for(ProcessInOut in:inList){
            // @edit DingXintao <p>只应该存入原材料数据，即T_INV_MAT 中类型为 MATERIALS的数据，其他半成品不需要存入在计算表中</p>
            if(!MatType.MATERIALS.equals(in.getMat().getMatType())){
                continue;
            }
            BigDecimal totalCount =  length.multiply(new BigDecimal(in.getQuantity())).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
            MaterialRequirementPlan mrp = new MaterialRequirementPlan();
            mrp.setOrgCode(wo.getOrgCode());
            mrp.setMatCode(in.getMatCode());
            mrp.setProcessCode(process.getProcessCode());
            mrp.setPlanDate(wo.getPreStartTime());
            mrp.setStatus(MaterialStatus.UNAUDITED);
            mrp.setQuantity(totalCount.doubleValue());
            mrp.setWorkOrderId(wo.getId());
            mrp.setUnit(in.getUnit());
            mrp.setEquipCode(wo.getEquipCode());
            result.add(mrp);
        }
        return result;
    }
}
