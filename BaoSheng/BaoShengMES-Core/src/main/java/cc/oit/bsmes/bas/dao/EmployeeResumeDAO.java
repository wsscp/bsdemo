package cc.oit.bsmes.bas.dao;


import java.util.List;

import cc.oit.bsmes.bas.model.EmployeeResume;
import cc.oit.bsmes.common.dao.BaseDAO;

/**
 * 
 */
public interface EmployeeResumeDAO extends BaseDAO<EmployeeResume>{

	List<EmployeeResume> getUserResumeInfo(String userCode);

}
