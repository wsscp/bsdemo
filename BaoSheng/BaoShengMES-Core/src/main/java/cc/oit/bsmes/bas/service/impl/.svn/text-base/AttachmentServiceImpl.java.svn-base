package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.AttachmentDAO;
import cc.oit.bsmes.bas.model.Attachment;
import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.constants.PropKeyConstants;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.WebContextUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment> implements AttachmentService {
	@Resource
	private AttachmentDAO attachmentDAO;
	private static final int BUF_SIZE = 8192;

	@Override
	public Attachment getByProductIDAndLocation(String productId, String location) {
		return attachmentDAO.getByProductIDAndLocation(productId, location);
	}

	@Override
	public void delete(String refId) {
		Attachment findParams = new Attachment();
		findParams.setRefId(refId);
		List<Attachment> attachments = attachmentDAO.get(findParams);
		if (attachments == null || attachments.size() == 0) {
			return;
		}
		for (Attachment attachment : attachments) {
			String path = attachment.getDownloadPath();
			// + "/" + attachment.getRealFileName();
			new File(path).delete();
			attachmentDAO.delete(attachment.getId());
		}
	}

	@Override
	public void upload(File file, InterfaceDataType module, String refType, String refId) throws IOException {
		String attachmentPath = null;
		try {
			attachmentPath = WebContextUtils.getPropValue(PropKeyConstants.ATTACHMENT_PATH);
		} catch (Exception e) {
			attachmentPath = "D:/";
		}
		DateFormat df = new SimpleDateFormat(DateUtils.DATE_SHORT_FORMAT);
		String dirPath = attachmentPath + "/" + module.name() + "/" + df.format(new Date());
		File dir = new File(dirPath);
		dir.mkdirs();
		String realFileName = UUID.randomUUID().toString();
		File uploadFile = new File(dirPath, realFileName);
		final OutputStream outputStream = new FileOutputStream(uploadFile);
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			bufferedOutputStream = new BufferedOutputStream(outputStream);

			final byte temp[] = new byte[BUF_SIZE];
			int readBytes = 0;
			while ((readBytes = bufferedInputStream.read(temp)) != -1) {
				bufferedOutputStream.write(temp, 0, readBytes);
			}
			bufferedOutputStream.flush();

		} finally {
			if (bufferedOutputStream != null) {
				bufferedOutputStream.close();
			}
			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
		}

		String fileName = file.getName();
		String[] split = fileName.split("\\.");
		String contentType = split[split.length - 1];

		Attachment attachment = new Attachment();
		attachment.setFileName(fileName);
		attachment.setRealFileName(realFileName);
		attachment.setOwnerModule(module);
		attachment.setContentType(contentType);
		attachment.setContentLength(file.length());
		// attachment.setDownloadPath(uploadFile.getParentFile().getAbsolutePath());
		attachment.setDownloadPath(uploadFile.getAbsolutePath());
		attachment.setRefId(refId);
		attachment.setSubType(refType);
		attachmentDAO.insert(attachment);
	}
	
	@Override
	public void upload(MultipartFile importFile, InterfaceDataType module, String refType, String refId) throws IOException {
		String attachmentPath = null;
		try {
			attachmentPath = WebContextUtils.getPropValue(PropKeyConstants.ATTACHMENT_PATH);
		} catch (Exception e) {
			attachmentPath = "D:/";
		}
		DateFormat df = new SimpleDateFormat(DateUtils.DATE_SHORT_FORMAT);
		String dirPath = attachmentPath + "/" + module.name() + "/" + df.format(new Date());
		File dir = new File(dirPath);
		dir.mkdirs();
		
		String fileName = importFile.getOriginalFilename();
		String[] split = fileName.split("\\.");
		String contentType = split[split.length - 1];
		
		String realFileName = UUID.randomUUID().toString() + "." + contentType;
		File uploadFile = new File(dirPath, realFileName);
		final OutputStream outputStream = new FileOutputStream(uploadFile);
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(importFile.getInputStream());
			bufferedOutputStream = new BufferedOutputStream(outputStream);

			final byte temp[] = new byte[BUF_SIZE];
			int readBytes = 0;
			while ((readBytes = bufferedInputStream.read(temp)) != -1) {
				bufferedOutputStream.write(temp, 0, readBytes);
			}
			bufferedOutputStream.flush();

		} finally {
			if (bufferedOutputStream != null) {
				bufferedOutputStream.close();
			}
			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
		}

		Attachment attachment = new Attachment();
		attachment.setFileName(fileName);
		attachment.setRealFileName(realFileName);
		attachment.setOwnerModule(module);
		attachment.setContentType(contentType);
		attachment.setContentLength(importFile.getSize());
		// attachment.setDownloadPath(uploadFile.getParentFile().getAbsolutePath());
		attachment.setDownloadPath(uploadFile.getAbsolutePath());
		attachment.setRefId(refId);
		attachment.setSubType(refType);
		attachmentDAO.insert(attachment);
	}


	/**
	 * 下载服务器文件
	 * 
	 * @param outputStream 输出流
	 * @param uid 文件数据主键
	 */

	@Override
	public void downLoad(OutputStream outputStream, String uid) throws IOException {
		Attachment attachment = this.getById(uid);
		if (attachment == null) {
			return;
		}
		this.downLoad(outputStream, this.getById(uid));
	}

	/**
	 * 下载服务器文件
	 * 
	 * @param outputStream 输出流
	 * @param refId 数据关联主键
	 */
	@Override
	public void downLoadOne(OutputStream outputStream, String refId) throws IOException {
		Attachment findParams = new Attachment();
		findParams.setRefId(refId);
		List<Attachment> attachments = attachmentDAO.get(findParams);
		if (attachments == null || attachments.size() == 0) {
			return;
		}
		Attachment attachment = attachments.get(0);
		this.downLoad(outputStream, attachment);
	}

	/**
	 * 下载服务器文件
	 * 
	 * @param outputStream 输出流
	 * @param attachment 文件数据对象
	 */

	private void downLoad(OutputStream outputStream, Attachment attachment) throws IOException {
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			// String path = attachment.getDownloadPath() + "/" +
			// attachment.getRealFileName();
			String path = attachment.getDownloadPath();
			bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			final byte temp[] = new byte[BUF_SIZE];
			int readBytes = 0;
			while ((readBytes = bufferedInputStream.read(temp)) != -1) {
				bufferedOutputStream.write(temp, 0, readBytes);
			}
			bufferedOutputStream.flush();
		} finally {
			if (bufferedOutputStream != null) {
				bufferedOutputStream.close();
			}
			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
		}
	}

	public void uploadUrl(File file, InterfaceDataType module, String refType, String refId, String location) {
		String fileName = file.getName();
		//String realFileName = UUID.randomUUID().toString();
		String[] split = fileName.split("\\.");
		String contentType = split[split.length - 1];
		Attachment attachment = new Attachment();
		attachment.setFileName(fileName);
		if (attachmentDAO.getOne(attachment) != null) {
			attachmentDAO.delete(attachmentDAO.getOne(attachment).getId());
		}
		attachment.setRealFileName(fileName);
		attachment.setOwnerModule(module);
		attachment.setContentType(contentType);
		attachment.setContentLength(file.length());
		attachment.setDownloadPath(location);
		attachment.setRefId(refId);
		attachment.setSubType(refType);
		attachmentDAO.insert(attachment);
	}

	public void uploadByByte(byte[] content, InterfaceDataType module, String refType, String refId) throws IOException {
		String attachmentPath = null;
		try {
			attachmentPath = WebContextUtils.getPropValue(PropKeyConstants.ATTACHMENT_PATH);
		} catch (Exception e) {
			attachmentPath = "D:/";
		}
		// DateFormat df = new SimpleDateFormat(DateUtils.DATE_SHORT_FORMAT);
		String dirPath = attachmentPath + "/" + module.name();
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String realFileName = refId + refType + module.name();
		File uploadFile = new File(dirPath, realFileName);
		if (uploadFile.exists()) {
			uploadFile.delete();
			Attachment param = new Attachment();
			param.setRefId(refId);
			param.setSubType(refType);
			param.setOwnerModule(module);
			attachmentDAO.deleteOne(param);
			uploadFile.createNewFile();
		}
		final OutputStream outputStream = new FileOutputStream(uploadFile);
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(content));
			bufferedOutputStream = new BufferedOutputStream(outputStream);

			final byte temp[] = new byte[BUF_SIZE];
			int readBytes = 0;
			while ((readBytes = bufferedInputStream.read(temp)) != -1) {
				bufferedOutputStream.write(temp, 0, readBytes);
			}
			bufferedOutputStream.flush();

		} finally {
			if (bufferedOutputStream != null) {
				bufferedOutputStream.close();
			}
			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
		}

		Attachment attachment = new Attachment();
		attachment.setFileName(realFileName);
		attachment.setRealFileName(realFileName);
		attachment.setOwnerModule(module);
		attachment.setContentLength(uploadFile.length());
		// attachment.setDownloadPath(uploadFile.getParentFile().getAbsolutePath());
		attachment.setDownloadPath(uploadFile.getAbsolutePath());
		attachment.setContentType("jpg");
		attachment.setRefId(refId);
		attachment.setSubType(refType);
		attachmentDAO.insert(attachment);

	}

}
