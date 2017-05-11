package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.Jpgfile;

public interface JpgfileDAO extends BaseDAO<Jpgfile> {

	List<Jpgfile> getByJpgfileId(String jpgfileId);
	
	public void insertJpgFile(Jpgfile jpgfile);

}
