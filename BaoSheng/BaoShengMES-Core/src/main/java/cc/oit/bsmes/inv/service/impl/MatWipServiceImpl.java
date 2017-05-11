package cc.oit.bsmes.inv.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.inv.dao.MatWipDAO;
import cc.oit.bsmes.inv.model.MatWip;
import cc.oit.bsmes.inv.service.MatWipService;

/**
 * @ClassName:   MatWipServiceImpl
 * @Description: TODO(订单物料表实现)
 * @author:      DingXintao
 * @date:        2016年7月5日 上午9:51:41
 */
@Service
public class MatWipServiceImpl extends BaseServiceImpl<MatWip> implements MatWipService {
	@Resource
	private MatWipDAO matWipDAO;

}
