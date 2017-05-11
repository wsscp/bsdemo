package cc.oit.bsmes.interfacePLM.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.Mach;

/**
 * Created by JinHy on 2014/9/26 0026.
 */
public interface MachService extends BaseService<Mach> {
    void syncMachData();
}
