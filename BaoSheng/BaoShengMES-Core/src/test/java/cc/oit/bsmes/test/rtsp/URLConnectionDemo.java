package cc.oit.bsmes.test.rtsp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class URLConnectionDemo {
	public static void main(String[] args) throws Exception {
		URL url = new URL("rtsp://admin:admin@172.17.4.77/cam/realmonitor?channel=1&subtype=0");
		URLConnection uc = url.openConnection();
		String fileName = uc.getHeaderField(6);
		fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename=") + 9), "UTF-8");
		System.out.println("文件名为：" + fileName);
		System.out.println("文件大小：" + (uc.getContentLength() / 1024) + "KB");
		String path = "D:" + File.separator + fileName;
		FileOutputStream os = new FileOutputStream(path);
		InputStream is = uc.getInputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = is.read(b)) != -1) {
			os.write(b, 0, len);
		}
		os.close();
		is.close();
		System.out.println("下载成功,文件保存在：" + path);
	}
}