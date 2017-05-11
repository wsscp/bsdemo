package cc.oit.bsmes.fac.service.impl;

import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.fac.dao.EquipMaintainStateDAO;
import cc.oit.bsmes.fac.model.EquipMaintainState;
import cc.oit.bsmes.fac.service.EquipMaintainStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EquipMaintainStateServiceImpl extends BaseServiceImpl<EquipMaintainState> implements EquipMaintainStateService {

    @Resource
    private EquipMaintainStateDAO equipMaintainStateDAO;

    @Override
    public EquipMaintainState getUncompletedMaintainSate(String equipCode, String orgCode, MaintainTemplateType maintainTemplateType) {
        EquipMaintainState params = new EquipMaintainState();
        params.setEquipCode(equipCode);
        params.setOrgCode(orgCode);
        params.setCompleted(false);
        params.setMaintainType(maintainTemplateType);

        return equipMaintainStateDAO.getOne(params);
    }

    @Override
    public List<EquipMaintainState> getUncompletedMaintainSates(String orgCode) {
        EquipMaintainState params = new EquipMaintainState();
        params.setOrgCode(orgCode);
        params.setCompleted(false);

        return equipMaintainStateDAO.get(params);
    }
}
