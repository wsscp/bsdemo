package cc.oit.bsmes.wip.service.impl;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.constants.SectionType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.exception.InconsistentException;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.inv.model.InventoryDetail;
import cc.oit.bsmes.ord.dto.LengthConstraints;
import cc.oit.bsmes.ord.dto.LengthConstraints.LengthConstraint;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.InvOaUseLog;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.InvOaUseLogService;
import cc.oit.bsmes.wip.dao.SectionDAO;
import cc.oit.bsmes.wip.dto.SectionLength;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.Section;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.SectionService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.*;

@Service
public class SectionServiceImpl extends BaseServiceImpl<Section> implements
		SectionService {
	
	private static final double PIAN_YI_LIANG = 5; // TODO chanedi

	private static final String processPathRegex = "[^" + BusinessConstants.PROCESS_PATH_SPLITS + "]+" + BusinessConstants.PROCESS_PATH_SPLITS + "$";
	private Map<String, LengthConstraints> lengthConstraintsMap = new HashMap<String, LengthConstraints>(); // <customerOrderItemId, LengthConstraints>
	
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private SectionDAO sectionDAO;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
    private DataAcquisitionService dataAcquisitionService;
    @Resource
    private InvOaUseLogService invOaUseLogService;

    private LengthConstraints getLengthConstraints(String customerOrderItemId) {
		LengthConstraints lengthConstraints = lengthConstraintsMap.get(customerOrderItemId);
		if (lengthConstraints == null) {
			lengthConstraints = salesOrderItemService.getByCustomerOrderItemId(customerOrderItemId).getLengthConstraints();
			lengthConstraintsMap.put(customerOrderItemId, lengthConstraints);
		}
		return lengthConstraints;
	}
	
	@Override
	public List<? extends SectionLength> getMatchedSections(List<? extends SectionLength> sortedSections,
			Map<String, List<Double>> completedLengths, CustomerOrderItemProDec customerOrderItemProDec, boolean forStock) {
		CustomerOrderItemDec itemDec = customerOrderItemDecService.getById(customerOrderItemProDec.getOrderItemDecId());
		// 获取要求长度 TODO 完成生产时的更新！
		Double requireLength = customerOrderItemProDec.getUnFinishedLength();
		
		List<SectionLength> matchSections = new ArrayList<SectionLength>();
		while (!CollectionUtils.isEmpty(sortedSections)) {
			// 获取最佳长度
			while (!CollectionUtils.isEmpty(sortedSections)) {
				int lastIndex = sortedSections.size() - 1;
				SectionLength longest = sortedSections.get(lastIndex);
				if (longest.getLength() > requireLength + PIAN_YI_LIANG) {
					// 太长
					sortedSections.remove(lastIndex);
					continue;
				} else if (longest.getLength() >= requireLength - PIAN_YI_LIANG) {
					// 合适
					matchSection(matchSections, longest, completedLengths, customerOrderItemProDec, forStock);
					customerOrderItemProDec.setUnFinishedLength(0.0);
					return matchSections;
				} else {
					break;
				}
			}
			
			// 获取最短可冲抵库存
			while (!CollectionUtils.isEmpty(sortedSections)) {
				// 获取最短库存
				SectionLength shortest = sortedSections.get(0);
				// 库存i长度小于剩余要求长度的1/2
				if (shortest.getLength() < requireLength / 2) {
					if (isGoodLength(shortest.getLength(), itemDec.getOrderItemId(), completedLengths, customerOrderItemProDec.getProcessPath(), customerOrderItemProDec.getParrelCount())) {
						// 冲抵
						matchSection(matchSections, shortest, completedLengths, customerOrderItemProDec, forStock);
						requireLength = requireLength - shortest.getLength();
                        sortedSections.remove(0);
						break; // 回到获取最佳长度
					} else {
						// 不可冲抵，删除
						sortedSections.remove(0);
						continue;
					}
				} else {
					if (isGoodLength(requireLength - shortest.getLength(), itemDec.getOrderItemId(), completedLengths, customerOrderItemProDec.getProcessPath(), 1)) {
						// 冲抵
						matchSection(matchSections, shortest, completedLengths, customerOrderItemProDec, forStock);
					}
					return matchSections;
				}
			}
		}
        return matchSections;
	}

    @Override
    public List<Section> getMatchedSections(Section section,List<CustomerOrderItemProDec> proDecs,Map<String,
            CustomerOrderItemDec> itemDecMap,String reportId,User user) {
        List<Section> matchedSections = new ArrayList<Section>();
        Map<String,Map<String,List<Double>>> completedMap = new HashMap<String, Map<String, List<Double>>>();
        double sectionLength  = section.getLength();
        for(CustomerOrderItemProDec proDec:proDecs){
            Double requireLength = proDec.getUnFinishedLength();
            if(requireLength == 0.0){
                continue;
            }
            CustomerOrderItemDec itemDec = itemDecMap.get(proDec.getOrderItemDecId());

            Map<String,List<Double>> completedLengths = completedMap.get(proDec.getOrderItemDecId());
            if(completedLengths == null){
                completedLengths = getCompletedLengths(proDec.getOrderItemDecId());
                completedMap.put(proDec.getOrderItemDecId(),completedLengths);
            }

            if(itemDec == null){
                itemDec =  customerOrderItemDecService.getById(proDec.getOrderItemDecId());
                itemDecMap.put(proDec.getOrderItemDecId(),itemDec);
            }

            //合适
            if(sectionLength >= requireLength - PIAN_YI_LIANG && sectionLength <= requireLength + PIAN_YI_LIANG){
                if(isGoodLength(sectionLength, itemDec.getOrderItemId(), completedLengths, proDec.getProcessPath(), 1)){
                    section.setOrderItemProDecId(proDec.getId());
                    section.setReportId(reportId);
                    proDec.setModifyUserCode(user.getUserCode());
                    bindProDec(sectionLength, proDec, requireLength, completedLengths);
                    proDec.setUnFinishedLength(0.0);
                    break;
                }
                continue;
            }

            if(sectionLength < requireLength - PIAN_YI_LIANG){
                //计算当sectionLength 是否是goodLength 如果不是跳过本次循环
                if (isGoodLength(sectionLength, itemDec.getOrderItemId(), completedLengths, proDec.getProcessPath(), 1)) {
                    section.setOrderItemProDecId(proDec.getId());
                    section.setReportId(reportId);
                    proDec.setModifyUserCode(user.getUserCode());
                    bindProDec(sectionLength, proDec, requireLength, completedLengths);
                    break;
                }
                continue;
            }

            if(sectionLength > requireLength + PIAN_YI_LIANG){
                //计算出允许最大的浪费长度
                double wasteLength = getWasteLength(user, sectionLength);
                //表示浪费的长度在允许范围之内
                if((sectionLength - requireLength) <= wasteLength){
                    section.setOrderItemProDecId(proDec.getId());
                    section.setReportId(reportId);
                    proDec.setModifyUserCode(user.getUserCode());
                    bindProDec(sectionLength, proDec, requireLength, completedLengths);
                    proDec.setUnFinishedLength(0.0);
                    break;
                }else{
                    Section newSection  = new Section();
                    newSection.setReportId(reportId);
                    newSection.setOrderItemProDecId(proDec.getId());
                    newSection.setWorkOrderNo(section.getWorkOrderNo());
                    newSection.setProcessPath(section.getProcessPath());
                    newSection.setProductLength(section.getProductLength() - (sectionLength - requireLength));
                    newSection.setSectionLength(requireLength);
                    newSection.setGoodLength(requireLength);
                    newSection.setSectionType(SectionType.NORMAL);
                    newSection.setOrgCode(user.getOrgCode());
                    newSection.setSectionLocal(section.getSectionLocal() - (sectionLength - requireLength));
                    newSection.setId(UUID.randomUUID().toString());
                    newSection.setCreateUserCode(user.getUserCode());
                    newSection.setModifyUserCode(user.getUserCode());
                    proDec.setModifyUserCode(user.getUserCode());
                    bindProDec(requireLength, proDec, requireLength, completedLengths);

                    insert(newSection);
                    matchedSections.add(newSection);

                    section.setSectionLength(sectionLength - requireLength);
                    section.setGoodLength(sectionLength - requireLength);

                    sectionLength = sectionLength - requireLength;
                }
            }
        }

        section.setModifyUserCode(user.getUserCode());
        update(section);
        matchedSections.add(section);
        return matchedSections;
    }

    @Override
    public double calculateWasteLength(Section section,List<CustomerOrderItemProDec> proDecs,Map<String,
            CustomerOrderItemDec> itemDecMap){
        Map<String,Map<String,List<Double>>> completedMap = new HashMap<String, Map<String, List<Double>>>();
        User user = SessionUtils.getUser();
        double sectionLength  = section.getLength();
        for(CustomerOrderItemProDec  proDec : proDecs){
            Double requireLength = proDec.getUnFinishedLength();
            if(requireLength == 0.0){
                continue;
            }
            CustomerOrderItemDec itemDec = itemDecMap.get(proDec.getOrderItemDecId());
            Map<String,List<Double>> completedLengths = completedMap.get(proDec.getOrderItemDecId());
            if(completedLengths == null){
                completedLengths = getCompletedLengths(proDec.getOrderItemDecId());
                completedMap.put(proDec.getOrderItemDecId(),completedLengths);
            }

            if(itemDec == null){
                itemDec =  customerOrderItemDecService.getById(proDec.getOrderItemDecId());
                itemDecMap.put(proDec.getOrderItemDecId(),itemDec);
            }

            //合适
            if(sectionLength >= requireLength - PIAN_YI_LIANG && sectionLength <= requireLength + PIAN_YI_LIANG){
                if(isGoodLength(sectionLength, itemDec.getOrderItemId(), completedLengths, proDec.getProcessPath(), 1)){
                    section.setOrderItemProDecId(proDec.getId());
                    proDec.setUnFinishedLength(0.0);
                    break;
                }
                continue;
            }

            if(sectionLength < requireLength - PIAN_YI_LIANG){
                //计算当sectionLength 是否是goodLength 如果不是跳过本次循环
                if (isGoodLength(sectionLength, itemDec.getOrderItemId(), completedLengths, proDec.getProcessPath(), 1)) {
                    section.setOrderItemProDecId(proDec.getId());
                    proDec.setUnFinishedLength(requireLength - sectionLength);
                    break;
                }
                continue;
            }

            if(sectionLength > requireLength + PIAN_YI_LIANG){
                //计算出允许最大的浪费长度
                double wasteLength = getWasteLength(user, sectionLength);
                //表示浪费的长度在允许范围之内
                if((sectionLength - requireLength) <= wasteLength){
                    section.setOrderItemProDecId(proDec.getId());
                    proDec.setUnFinishedLength(0.0);
                    break;
                }else{
                    proDec.setUnFinishedLength(0.0);
                    section.setSectionLength(sectionLength - requireLength);
                    section.setGoodLength(sectionLength - requireLength);
                    sectionLength = sectionLength - requireLength;
                }
            }
        }

        return (StringUtils.isBlank(section.getOrderItemProDecId()))?sectionLength:0.0;
    }

    private double getWasteLength(User user, double sectionLength) {
        String wastePercent = WebContextUtils.getSysParamValue(WebConstants.WASTE_PERCENT, user.getOrgCode());
        if(StringUtils.isBlank(wastePercent)){
            throw new MESException("");
        }
        double watePercentLength = (Double.parseDouble(wastePercent) * sectionLength);

        String wasteLength_s = WebContextUtils.getSysParamValue(WebConstants.WASTE_LENGTH,user.getOrgCode());
        if(StringUtils.isBlank(wasteLength_s)){
            throw new MESException("");
        }
        double wasteLength = Double.parseDouble(wasteLength_s);
        return wasteLength > watePercentLength ? watePercentLength:wasteLength;
    }

    private void bindProDec(double sectionLength, CustomerOrderItemProDec proDec, Double requireLength, Map<String, List<Double>> completedLengths) {
        List<Double> processLengths = completedLengths.get(proDec.getProcessPath());
        if (processLengths == null) {
            processLengths = new ArrayList<Double>();
            completedLengths.put(proDec.getProcessPath(), processLengths);
        }
        processLengths.add(sectionLength);
        double unFinishedLength = (requireLength - sectionLength) < PIAN_YI_LIANG ? 0 : requireLength - sectionLength;
        proDec.setUnFinishedLength(unFinishedLength);
        proDec.setFinishedLength((proDec.getFinishedLength() == null?0.0:proDec.getFinishedLength())+sectionLength);
        customerOrderItemProDecService.update(proDec);
    }

    /**
	 * 冲抵长度
	 * @author chanedi
	 * @date 2014年1月6日 下午6:20:43
	 * @param matchSections
	 * @param sectionLength
	 * @param completedLengths
	 * @param cProDec
	 * @param forStock
	 * @see
	 */
	private void matchSection(List<SectionLength> matchSections, SectionLength sectionLength, Map<String, List<Double>> completedLengths, CustomerOrderItemProDec cProDec, boolean forStock) {
		matchSections.add(sectionLength);
		
		List<Double> processLengths = completedLengths.get(cProDec.getProcessPath());
		if (processLengths == null) {
			processLengths = new ArrayList<Double>();
			completedLengths.put(cProDec.getProcessPath(), processLengths);
		}
		processLengths.add(sectionLength.getLength());
		if (forStock) {
			cProDec.setUnFinishedLength(cProDec.getUnFinishedLength() - sectionLength.getLength());
            cProDec.setUsedStockLength(cProDec.getUsedStockLength() + sectionLength.getLength());
		} else {
			cProDec.setUnFinishedLength(cProDec.getFinishedLength() + sectionLength.getLength());
		}
	}


	@Override
	public boolean isGoodLength(double lengthToCheck, String customerOrderItemId,
                                Map<String, List<Double>> completedLengths, String processPath, int parrelCount) {
		LengthConstraints lengthConstraints = getLengthConstraints(customerOrderItemId);
		for (LengthConstraint lengthConstraint : lengthConstraints) {
			// 判断是否符合约束所指长度。
			if (lengthToCheck >= lengthConstraint.getMaxLength()) {
				continue;
			}
			if (lengthConstraint.getMinLength() != null && lengthToCheck <= lengthConstraint.getMinLength()) {
				continue;
			}
			
			int quantity = 1;
			// 根据processPath依次获取父路径并获取符合长度范围的已冲抵长度
			while (processPath.contains(BusinessConstants.PROCESS_PATH_SPLITS)) {
				List<Double> processLengths = completedLengths.get(processPath);
                if(processLengths != null){
                    for (Double length : processLengths) {
                        if (length < lengthConstraint.getMaxLength() && (lengthConstraint.getMinLength() == null || length > lengthConstraint.getMinLength())) {
                            quantity++;
                        }
                    }
                }
                if (!processPath.endsWith(BusinessConstants.PROCESS_PATH_SPLITS)) {
                    throw new InconsistentException();
                }
				processPath = processPath.replaceFirst(processPathRegex, "");
			}
			
			if (quantity > lengthConstraint.getMaxQuantity() * parrelCount) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public List<Section> getByOrderItemIdAndWoNo(String customerOrderItemId,
			String workOrderNo) {
		return sectionDAO.getByOrderItemIdAndWoNo(customerOrderItemId, workOrderNo);
	}

	@Override
	public Map<String, List<Double>> getCompletedLengths(
			String orderItemDecId) {
		Map<String,List<Double>> completedLengths = new HashMap<String, List<Double>>();
		CustomerOrderItemDec itemDec = customerOrderItemDecService.getById(orderItemDecId);
		//为空表示未分解过
		if(itemDec == null || itemDec.getCusOrderItemProDesList() == null) return completedLengths;

        CustomerOrderItemProDec proDecFindParams = new CustomerOrderItemProDec();
        proDecFindParams.setOrderItemDecId(itemDec.getId());
        proDecFindParams.setUsedStockLength(null);
        proDecFindParams.setFinishedLength(null);
        List<CustomerOrderItemProDec> proDecs = customerOrderItemProDecService.getByObj(proDecFindParams);

        for(CustomerOrderItemProDec proDec:proDecs){
			String processFullPath =  proDec.getProcessPath();
			List<Double> lengths = completedLengths.get(processFullPath);
			if(lengths == null) {
				lengths = new ArrayList<Double>();
				completedLengths.put(processFullPath, lengths);
			}
			
			// 库存冲抵部分
			List<InvOaUseLog> inventoryUseLogs = invOaUseLogService.getByRefId(proDec.getId());
			//inventoryUseLogs 为空表示未冲抵
			if(inventoryUseLogs != null){
				for(InvOaUseLog useLog:inventoryUseLogs){
					InventoryDetail detail = useLog.getInventoryDetail();
					lengths.add(detail.getLength());
				}
			}
			
			// 生产部分
			Section findParams = new Section();
			findParams.setOrderItemProDecId(proDec.getId());
			List<Section> sections = sectionDAO.get(findParams);
			for (Section section : sections) {
				lengths.add(section.getGoodLength());
			}
		}
		
		return completedLengths;
	}

	@Override
	public List<Section> getByWoNo(String workOrderNo) {
        List<Section> list = sectionDAO.getByWoNo(workOrderNo);
        Section nextSection = null;
        for(int i=list.size() -1; i>=0; i--){
            Section section = list.get(i);
            if(StringUtils.isNotBlank(section.getReportId())){
                if(nextSection == null){
                    section.setRowSpanSize(1);
                }else{
                    if(section.getReportId().equals(nextSection.getReportId())){
                        section.setRowSpanSize(nextSection.getRowSpanSize() + 1);
                        nextSection.setRowSpanSize(1);
                    }else{
                        section.setRowSpanSize(1);
                    }
                }
            }else{
                section.setRowSpanSize(1);
            }
            nextSection = section;
        }
		return list;
	}

    @Override
    public List<Section> getToReport(String equipCode, boolean loadCurrent) {
        WorkOrder wo = workOrderService.getCurrentByEquipCode(equipCode);
        List<Section> toReport = sectionDAO.getToReport(wo.getWorkOrderNo());
        if (!loadCurrent) {
            return toReport;
        }
        Section current = getCurrentSection(equipCode, wo);
        toReport.add(current);
        return toReport;
    }

    private Section getCurrentSection(String equipCode, WorkOrder wo) {
        CustomerOrderItemDec item = customerOrderItemDecService.getCurrentByWoNo(wo.getWorkOrderNo());
        EquipInfo mainEquip = StaticDataCache.getMainEquipInfo(equipCode);
        double currentLength=0;
        if(mainEquip!=null)
        {
          currentLength = dataAcquisitionService.getLength(mainEquip.getCode());
        }
        
        Section current = new Section();
        current.setSectionLength(currentLength);
        current.setSectionType(SectionType.NORMAL);
        LengthConstraints lengthConstraints = getLengthConstraints(item.getOrderItemId());
        for (LengthConstraint lengthConstraint : lengthConstraints) {
            if (currentLength < lengthConstraint.getMaxLength()) {
                current.setGoodLength(0.0);
            }
        }
        if (current.getGoodLength() == null) {
            current.setGoodLength(currentLength);
        }
        return current;
    }

    @Override
    public void insertCurrentSection(String equipCode, Report report, Section section) {
        WorkOrder wo = workOrderService.getCurrentByEquipCode(equipCode);
        Section current = section != null ? section : getCurrentSection(equipCode, wo);
        current.setReportId(report.getId());
        current.setOrgCode(report.getOrgCode());
        CustomerOrderItemProDec item = customerOrderItemProDecService.getCurrentByWoNo(wo.getWorkOrderNo());
        current.setOrderItemProDecId(item.getId());
        current.setProcessPath(item.getProcessPath());
        sectionDAO.insert(current);
    }


    @Override
    public Section getOne(Section findParams) {
        return sectionDAO.getOne(findParams);
    }

    @Override
    public Section getLastSection(String woNo) {
        return sectionDAO.getLastByWoNo(woNo);
    }

    @Override
    public List<Section> getByWoNoAndNotReport(String woNo,double sumReportLength) {
        return sectionDAO.getByWoNoAndNotReport(woNo,sumReportLength);
    }

    @Override
    public List<Section> getPrintSectionInfo(String reportId) {
        return sectionDAO.getPrintSectionInfo(reportId);
    }

    @Override
    public List<Section> getByReportIdForInWarehouse(String reportId) {
        return sectionDAO.getByReportIdForInWarehouse(reportId);
    }
}
