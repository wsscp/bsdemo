package cc.oit.bsmes.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logA("a");
		logA("a");
		logA("a");
		logA("a");
		System.out.println("完成");
	}

	public static void logA(String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			File file = new File("D:\\a.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter writer = new FileWriter(file, true);
			writer.write(content + "\r\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logB(String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			File file = new File("D:\\b.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter writer = new FileWriter(file, true);
			writer.write(content + "\r\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
