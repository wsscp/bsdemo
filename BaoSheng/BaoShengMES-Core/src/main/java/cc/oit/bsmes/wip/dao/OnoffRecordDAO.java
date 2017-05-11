package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.OnoffRecord;

import java.util.List;

public interface OnoffRecordDAO extends BaseDAO<OnoffRecord> {

	/**
	 * 
	 * <p>查询当前终端工作员工</p> 
	 * @author JinHanyun
	 * @date 2014-2-25 上午11:14:58
	 * @param clientMac
	 * @return
	 * @see
	 */
	public List<OnoffRecord> findByMesClientMac(String clientMac);


    public List<OnoffRecord> getHavePermissionRecord(String clientMac,String equipCode);
	/**
	 * 
	 * <p>查询当前刷卡用户</p> 
	 * @author JinHanyun
	 * @date 2014-2-25 下午2:24:54
	 * @param userCode
	 * @param mac
	 * @return
	 * @see
	 */
	public OnoffRecord findbyUserCodeAndMac(String userCode,String mac);

    /**
     * 验证用户是否刷卡，且该用户是否有权限操作详情和报工
     * @param userCode
     * @param mac
     * @param equipCode
     * @return
     */
    int validUserPermission(String userCode,String mac,String equipCode);

    List<OnoffRecord> getOnWorkUserRecord(String equipCode,String orgCode);

    List<OnoffRecord> getOnWorkUserByUserCode(String equipCode);

	public int checkIFCreditCard(String userCode, String equipCode);

	public String checkIfDB(String userCode);

	public String checkIfUsed(String equipCode);
}
