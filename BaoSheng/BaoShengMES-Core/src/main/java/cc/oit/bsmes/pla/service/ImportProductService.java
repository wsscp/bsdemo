package cc.oit.bsmes.pla.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.springframework.web.multipart.MultipartFile;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.pla.model.ImportProduct;

public interface ImportProductService {

	public void importProductDetail(MultipartFile file, StringBuffer log)
			throws IOException;

	public void importMpartDetail(MultipartFile file, StringBuffer log)
			throws IOException;

	public void importInsertMpartObj(MultipartFile file, StringBuffer log)
			throws IOException;

	public void importProcessDetail(MultipartFile file, StringBuffer log)
			throws IOException;

	public void importInsertScx(MultipartFile file, StringBuffer log)
			throws IOException;


	public List<ImportProduct> checkExistsByName(String seriesName);

	public void createFile(StringBuffer log, String userName, String location,
			String importType) throws IOException;

	public void insertNewRecord(Map<String, String> param, String seriesName,
			String userName, String location, String status, String importType,
			String otherStatus) throws ClassNotFoundException;

	public void updateRecordBySeriesName(Map<String, String> param, String seriesName,
			String userName, String status, String importType)
			throws ClassNotFoundException;

	public List<ImportProduct> getSeriesNameAndUserName(List<Sort> sortArray);

	public List<ImportProduct> getUserName(List<Sort> sortArray);

	public void importPrcvXml(StringBuffer log) throws IOException;

	public void createFileForPrcvXml(StringBuffer log, String location)
			throws IOException;

	public void deleteFile(String location, String importType);
	
	

}
