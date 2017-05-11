package cc.oit.bsmes.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileOutputStream b = null;
		File file = new File("d:/sdcard/myImage/");
		file.mkdirs();// 创建文件夹
		String fileName = "d:/sdcard/myImage/" + new Date().getTime() + ".jpg";

		try {
			b = new FileOutputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<String> shang = new ArrayList<String>();
		List<String> xia = new ArrayList<String>();
		List<String> zhong = new ArrayList<String>();
		List<String> shou = new ArrayList<String>();

		String csvalue1 = "JCD-06-012=^JCD-06-013=^JCD-06-014=^JCD-06-015=^JCD-06-010=^JCD-06-011=^JCD-13-017=0.5^JCD-11-034=mm^JCD-13-016=1.14^JCD-11-035=mm^JCD-13-019=t^JCD-11-032=mm^JCD-13-018=2.14^JCD-11-033=N/mm2^JCD-13-013=^JCD-06-017=r/min^JCD-11-030=Ω/km^JCD-13-012=0.2^JCD-06-016=^JCD-11-031=kg/km^JCD-13-015=^JCD-06-019=^JCD-13-014=t^JCD-06-018=r/min^JCD-13-011=t^JCD-13-010=t^JCD-11-038=mm^JCD-11-039=N/mm2^JCD-11-036=mm^JCD-11-037=mm^JCD-06-003=^JCD-06-004=^JCD-06-001=^JCD-06-002=^JCD-13-029=^JCD-13-028=^JCD-13-027=^JCD-06-009=^JCD-13-026=^JCD-06-008=^JCD-13-025=^JCD-06-007=^JCD-11-040=kg/km^JCD-13-024=16.34^JCD-06-006=^JCD-11-041=m^JCD-13-023=t^JCD-06-005=^JCD-13-022=≥200%^JCD-13-021=≥12.5^JCD-13-020=10%^JCD-07-014=kg/km^JCD-07-013=mm^JCD-07-012=^JCD-07-011=^JCD-07-010=^JCD-13-035=^JCD-13-034=^JCD-12-022=^JCD-11-012=t^JCD-12-023=^JCD-11-013=t^JCD-12-020=10%^JCD-11-010=t^JCD-12-021=t^JCD-11-011=^JCD-12-026=^JCD-08-001=^JCD-11-016=18-20^JCD-12-027=^JCD-08-002=^JCD-02-005=kg/km^JCD-11-017=左向^JCD-12-024=^JCD-11-014=t^JCD-12-025=^JCD-11-015=0.2^JCD-13-031=^JCD-07-017=^JCD-13-030=^JCD-07-018=kg/km^JCD-07-015=^JCD-13-033=^JCD-02-004=2.57^JCD-11-018=t^JCD-07-016=^JCD-13-032=^JCD-11-019=^JCD-07-001=^JCD-07-003=^JCD-07-002=^JCD-14-006=t^JCD-14-007=t^JCD-14-004=1.14±0.02^JCD-14-005=t^JCD-14-002=t^JCD-14-003=t^JCD-14-001=t^JCD-11-020=1.14^JCD-11-021=0.5^JCD-12-010=0.2^JCD-11-022=2.14^JCD-12-011=18-20^JCD-11-023=t^JCD-14-009=1.14^JCD-12-012=左向^JCD-11-024=10%^JCD-14-008=t^JCD-12-013=t^JCD-07-008=^JCD-11-025=12.5^JCD-03-020=r/min^JCD-12-014=^JCD-07-009=^JCD-11-026=200%^JCD-12-015=1.14^JCD-11-027=t^JCD-12-016=t^JCD-11-028=16.34^JCD-12-017=兰色^JCD-07-004=^JCD-11-029=^JCD-07-005=^JCD-12-018=0.5^JCD-07-006=^JCD-12-019=2.14^JCD-07-007=^JCD-14-012=t^JCD-03-019=r/min^JCD-14-011=2.14^JCD-14-014=t^JCD-14-013=10%^JCD-14-016=^JCD-14-015=^JCD-14-018=^JCD-14-017=^JCD-05-010=^JCD-05-012=^JCD-05-011=^JCD-05-014=^JCD-05-013=^JCD-05-016=^JCD-14-010=0.5^JCD-05-015=^JCD-12-009=t^JCD-05-017=^JCD-12-008=t^JCD-05-018=mm^JCD-12-007=t^JCD-05-019=mm^JCD-12-006=24/0.2铜^JCD-12-005=1.14±0.02^JCD-12-004=6.88^JCD-12-003=t^JCD-03-010=^JCD-12-002=t^JCD-12-001=t^JCD-03-012=^JCD-03-011=Min^JCD-03-014=kg^JCD-03-013=^JCD-03-016=m^JCD-03-015=kg^JCD-03-018=^JCD-03-017=m/kg^JCD-11-009=^JCD-11-008=22^JCD-03-008=Min^JCD-11-007=24/0.2铜^JCD-03-009=^JCD-11-006=1.14±0.02^JCD-11-005=6.88^JCD-11-004=24.5^JCD-11-003=t^JCD-11-002=t^JCD-11-001=t^JCD-05-008=^JCD-05-009=^JCD-05-006=^JCD-05-007=^JCD-03-003=^JCD-03-002=^JCD-03-001=^JCD-03-007=^JCD-03-006=Min^JCD-03-005=^JCD-03-004=^JCD-13-005=1.14±0.02^JCD-13-006=24/0.2铜^JCD-13-007=22^JCD-13-008=^JCD-13-001=t^JCD-13-002=t^JCD-13-003=t^JCD-13-004=24.5^JCD-13-009=t";

		String[] csvalueGroup = csvalue1.split("\\^");
		System.out.println(csvalueGroup.length);
		for (String canshu : csvalueGroup) {

			String[] key_val = canshu.split("="); // 1
			if (key_val.length <= 0) {
				continue; // 无效参数
			}
			String[] parms = key_val[0].split("-");
			if (parms.length != 3) {
				continue; // 无效参数
			}

			if (FIRST_CHECK.equals(parms[1]) || FIRST_CHECK_0.equals(parms[1])) { // 首
				shou.add("首检：" + canshu);
			} else if (IN_CHECK.equals(parms[1]) || IN_CHECK_0.equals(parms[1])) { // 上
				shang.add("上检：" + canshu);
			} else if (OUT_CHECK.equals(parms[1]) || OUT_CHECK_0.equals(parms[1])) { // 下
				xia.add("下检：" + canshu);
			} else if (MIDDLE_CHECK.equals(parms[1]) || MIDDLE_CHECK_0.equals(parms[1])) { // 中
				zhong.add("中检：" + canshu);
			}

		}
		System.out.println("首检长度：" + shou.size());

		System.out.println("上检长度：" + shang.size());

		System.out.println("中检长度：" + zhong.size());

		System.out.println("下检长度：" + xia.size());

		for (String s : shou) {
			System.out.println(s);
		}
		for (String s : shang) {
			System.out.println(s);
		}
		for (String s : zhong) {
			System.out.println(s);
		}
		for (String s : xia) {
			System.out.println(s);
		}

	}

	private static String FIRST_CHECK_0 = "011"; // 首
	private static String IN_CHECK_0 = "012"; // 上
	private static String OUT_CHECK_0 = "013"; // 下
	private static String MIDDLE_CHECK_0 = "014"; // 中

	private static String FIRST_CHECK = "11"; // 首
	private static String IN_CHECK = "12"; // 上
	private static String OUT_CHECK = "13"; // 下
	private static String MIDDLE_CHECK = "14"; // 中

	public static void test(Boolean a) {
		a.valueOf(false);

	}

	public static void Copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}
}

