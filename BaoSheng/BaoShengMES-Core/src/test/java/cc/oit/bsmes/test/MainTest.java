package cc.oit.bsmes.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.util.DateUtils;

public class MainTest {

	public static void main(String[] args) throws IOException {
		String a = "R_WrapH1S,R_WrapH2S";
		System.out.println(UnitType.M.name() + "");

		Date date = DateUtils.convert(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT));
		System.out.println(date);
	}

	public static List<Person> initList() {
		List<Person> personArray = new ArrayList<Person>();
		personArray.add(new Person("xiaoming", 53));
		personArray.add(new Person("zhangsan", 23));
		personArray.add(new Person("lisi", 78));
		personArray.add(new Person("wangwu", 64));
		return personArray;
	}

}
