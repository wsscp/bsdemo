package cc.oit.bsmes.interfacePLM.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.Jiantu;
/**
 * 
 * 简图
 * <p style="display:none">modifyRecord</p>
 * @author rongyidong
 * @date 2014-9-29 上午11:28:26
 * @since
 * @version
 */
public interface JiantuService extends BaseService<Jiantu> {
	
	public List<Jiantu> getJiantuByMpartId(String mpartId);
	
	public List<Jiantu> getAllMaterialJiantu();
	
	public void upload(File file,String designNo, String type,String halfProNo) throws IOException;
	
	public List<Jiantu> getJiantuByMatName(String name);
	
	public void insertJiantu(Jiantu param);
}
