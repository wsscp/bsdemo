package cc.oit.webservice.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {
	public static void main(String[] args) {

		try {
			new FTPUtil().downFile("/test/1.txt", "", "e:/2.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FTPUtil() {
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private FTPClient ftp;

	/**
	 * 连接ftp远程服务器
	 * 
	 * @param path 当前ftp站点主目录
	 * @param addr 服务器ip地址
	 * @param port ftp站点端口号
	 * @param username 服务器登录用户名
	 * @param password 服务器登录密码
	 * @return
	 * @throws Exception
	 */
	private boolean connect() throws Exception {
		boolean result = false;

		String addr = "192.168.1.157";
		String port = "2100";
		String username = "Dream2";
		String password = "52350299";

		// String addr="192.168.1.62";
		// String port="21";
		// String username="administrator";
		// String password="cimscims";

		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, Integer.valueOf(port));
		ftp.login(username, password);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		// ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
		// ftp.changeWorkingDirectory("hhmdps");
		result = true;
		return result;
	}

	/**
	 * 通过ftp协议读取远程文件，并下载到本地磁盘
	 * 
	 * @param remoteFileName 远程文件名
	 * @param localDir 本地目录名
	 * @throws Exception
	 */
	public void downFile(String path, String remoteFileName, String localDir) throws Exception {
		FTPFile[] fs;
		FileOutputStream os = null;
		try {
			ftp.enterLocalPassiveMode();
			os = new FileOutputStream(localDir);
			fs = ftp.listFiles(remoteFileName);
			
			for(int i=0;i<fs.length;i++){
				if(fs[i].getName().equals(path.substring(path.lastIndexOf("\\")+1))){
					InputStream in = ftp.retrieveFileStream(new String(path.getBytes("GBK"), "iso-8859-1"));
					byte[] bytes = new byte[1024];
					int c;
					while ((c = in.read(bytes)) != -1) {
						os.write(bytes, 0, c);
					}
					in.close();
				}
			}
			os.close();
			ftp.logout();
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			if (null != os) {
				os.close();
			}
		}
	}

	/**
	 * 从共享文件中得到文件并直接返回文件
	 * 
	 * @param remoteFileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public File getFile(String path, String remoteFileName) throws IOException, FileNotFoundException {
		OutputStream out = null;
		try {
			ftp.changeWorkingDirectory(path);

			String[] ftpName = ftp.listNames();
			for (int i = 0; i < ftpName.length; i++) {
				String string = ftpName[i];
				if (remoteFileName.equalsIgnoreCase(string)) {
					remoteFileName = string;
					break;
				}
			}

			FTPFile[] fs = ftp.listFiles(remoteFileName);
			File file = new File(fs[0].getName());

			out = new BufferedOutputStream(new FileOutputStream(file));
			ftp.retrieveFile(remoteFileName, out);
			return file;
		} catch (Exception e) {
			throw new FileNotFoundException(e.getMessage());
		} finally {
			ftp.logout();
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从共享文件中得到文件并直接返回文件
	 * 
	 * @param remoteFileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public File getUncertainedDirFile(String rooPath, String dirPath, String remoteFileName) throws IOException,
			FileNotFoundException {
		String[] paths = dirPath.split("\\\\");
		OutputStream out = null;
		boolean imageFlag = false;
		try {
			ftp.changeWorkingDirectory(rooPath);
			if (paths.length > 0) {
				for (String path : paths) {
					ftp.changeWorkingDirectory(path);
				}
			}
			String[] ftpName = ftp.listNames();
			for (int i = 0; i < ftpName.length; i++) {
				String string = ftpName[i];
				if (remoteFileName.equalsIgnoreCase(string)) {
					remoteFileName = string;
					imageFlag = true;
					break;
				}
			}
			if (imageFlag) {
				FTPFile[] fs = ftp.listFiles(remoteFileName);
				File file = new File(fs[0].getName());
				out = new BufferedOutputStream(new FileOutputStream(file));
				ftp.retrieveFile(remoteFileName, out);
				return file;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileNotFoundException(e.getMessage());
		} finally {
			ftp.logout();
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ftp上传功能，若dir为空表示无需在ftp主目录中新建子目录，反之新建相应的子目录完成文件上传
	 * 
	 * @param dir
	 * @param localFile
	 * @throws Exception
	 */
	public void upload(String dir, File localFile) throws Exception {
		FileInputStream input;
		try {
			if (!dir.equals("")) {
				String[] dirs = dir.split("/");

				ftp.changeWorkingDirectory(dirs[0]);

				for (int i = 1; i < dirs.length; i++) {
					ftp.makeDirectory(dirs[i]);
					ftp.changeWorkingDirectory(dirs[i]);
				}
				input = new FileInputStream(localFile);
				ftp.storeFile(localFile.getName(), input);
				input.close();
				ftp.logout();
			} else {
				input = new FileInputStream(localFile);
				ftp.storeFile(localFile.getName(), input);
				input.close();
				ftp.logout();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyTo(String originalPath, String targetPath) {
		try {
			ftp.changeWorkingDirectory(originalPath);
			String[] fileName = ftp.listNames();
			List<File> fileList = new ArrayList<File>();
			for (int i = 0; i < fileName.length; i++) {
				String remoteFileName = fileName[i];
				File file = new File(remoteFileName);
				OutputStream out = new FileOutputStream(file);
				ftp.retrieveFile(remoteFileName, out);
				out.close();
				fileList.add(file);
			}

			for (File file : fileList) {
				FileInputStream input;
				if (!targetPath.equals("")) {
					String[] originalPathDirs = originalPath.split("/");
					for (int k = 0; k < originalPathDirs.length; k++) {
						ftp.changeToParentDirectory();
					}

					String[] targetPathDirs = targetPath.split("/");
					ftp.changeWorkingDirectory(targetPathDirs[0]);
					for (int j = 1; j < targetPathDirs.length; j++) {
						ftp.makeDirectory(targetPathDirs[j]);
						ftp.changeWorkingDirectory(targetPathDirs[j]);
					}

					input = new FileInputStream(file);
					ftp.storeFile(file.getName(), input);
					input.close();
				} else {
					input = new FileInputStream(file);
					ftp.storeFile(file.getName(), input);
					input.close();
				}
			}
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除ftp远程文件
	 * 
	 * @param url
	 * @throws Exception
	 */
	public void deleteFile(String path, String fileName) {
		if (ftp != null) {
			try {
				ftp.changeWorkingDirectory(path);
				ftp.deleteFile(fileName);
				ftp.logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// public static void deleteDirectory(String dirName) throws Exception {
	// if( ftp!=null ){
	// try {
	// ftp.removeDirectory(dirName);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
}
