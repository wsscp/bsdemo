package cc.oit.bsmes.test;

import java.util.Date;

public class ProcessTime {

	public static Date time;

	public static void main(String[] args) {
	}

	public static void begin1() {
		time = new Date();
	}

	public static void print1(String msg) {
		if (null != time) {
			Date now = new Date();
			System.out.println("[" + msg + "]耗时：" + (now.getTime() - time.getTime()));
		}
		time = new Date();
	}

}
