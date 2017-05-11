package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.Attachment;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.service.BaseService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 附件信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiw
 * @date 2014-9-29 上午10:46:55
 * @since
 * @version
 */
public interface AttachmentService extends BaseService<Attachment> {

	public Attachment getByProductIDAndLocation(String productId, String location);

	public void delete(String refId);

	public void upload(File file, InterfaceDataType module, String refType, String refId) throws IOException;

	/**
	 * 下载服务器文件
	 * 
	 * @param outputStream 输出流
	 * @param uid 文件数据主键
	 */
	public void downLoad(OutputStream outputStream, String uid) throws IOException;

	/**
	 * 下载服务器文件
	 * 
	 * @param outputStream 输出流
	 * @param refId 数据关联主键
	 */
	public void downLoadOne(OutputStream outputStream, String refId) throws IOException;

	public void uploadUrl(File file, InterfaceDataType module, String refType, String refId, String location);

	public void uploadByByte(byte[] content, InterfaceDataType module, String refType, String refId) throws IOException;

	void upload(MultipartFile importFile, InterfaceDataType module,
			String refType, String refId) throws IOException;

}
