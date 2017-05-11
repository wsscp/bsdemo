package cc.oit.bsmes.interfacePLM.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.Jpgfile;
/**
 * 
 *单元操作图
 * @author rongyidong
 * @date 
 * @since
 * @version
 */
public interface JpgfileService extends BaseService<Jpgfile> {
	
	public List<Jpgfile> getByJpgfileId(String jpgfileId);
	
	public String upLoadClImage(File file) throws IOException;
}
