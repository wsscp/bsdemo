package cc.oit.bsmes.interfacePLM.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.interfacePLM.dao.PrcvDAO;
import cc.oit.bsmes.interfacePLM.dao.ProcessDAO;
import cc.oit.bsmes.interfacePLM.dao.ProcessProcessObjofDAO;
import cc.oit.bsmes.interfacePLM.model.Prcv;
import cc.oit.bsmes.interfacePLM.model.Process;
import cc.oit.bsmes.interfacePLM.model.ProcessProcessObjof;
import cc.oit.bsmes.junit.BaseTest;

@Component
public class AddPrcvXmlTask extends BaseTest {

	@Resource
	private PrcvDAO prcvDAO;

	@Resource
	private ProcessDAO processDAO;

	@Resource
	private ProcessProcessObjofDAO processProcessObjofDAO;

	@Test
	@Rollback(false)
	public void addPrcvXmlFile() throws IOException {
		//查找到所有需要生产工艺文件的工艺路线
		List<Prcv> lists = prcvDAO.getAllMesPrcv();
		if (lists == null || lists.size() == 0) {
			System.out.println("没有找到记录");
			return;
		}
		for (Prcv nPrcv : lists) {
			String prcvNo=null;
			try {
				int count=nPrcv.getFname().indexOf("-");
				prcvNo = nPrcv.getFname().substring(0, count)+"-001";
			} catch (Exception e) {
				System.out.println(nPrcv.getNo()+"-"+nPrcv.getFname());
				continue;
			}
			
			//复制工艺文件
			copyXmlFile(nPrcv, prcvNo);
		}
	}
	
	@Test
	@Rollback(false)
	public void addSinglePrcvXmlFile() throws IOException {
		String[] tempStrArray = {};
		List<String> listStrings = Arrays.asList(tempStrArray);
		for (String string : listStrings) {
			List<Prcv> lists = prcvDAO.getSingleMesPrcv(string);
			if (lists == null || lists.size() == 0) {
				System.out.println("没有找到记录");
				return;
			}
			for (Prcv nPrcv : lists) {
				String prcvNo=null;
				try {
					int count=nPrcv.getFname().indexOf("-");
					prcvNo = nPrcv.getFname().substring(0, count)+"-001";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(nPrcv.getNo()+"-"+nPrcv.getFname());
					continue;
				}
			/*	if (!prcvNo.endsWith("-001")) {
					prcvNo = prcvNo + "-001";
					 System.out.println(prcvNo); 
				}*/
				copyXmlFile(nPrcv, prcvNo);
			}
		}
	}
	
	public void addByPrcv(Prcv prcv) throws IOException{
		int count=prcv.getFname().indexOf("-");
		String prcvNo = prcv.getFname().substring(0, count)+"-001";
		copyXmlFile(prcv, prcvNo);
	}

	public void copyXmlFile(Prcv nPrcv, String prcvNo) throws IOException {
		//查询复制的旧工艺路线信息是否存在，如果存在获得本地文件位置
		List<Prcv> prcvs = prcvDAO.checkPrcvByNo(prcvNo);
		if (prcvs == null || prcvs.size() == 0) {
			System.out.println("---------------------------");
			System.out.println("新工艺路线:" + nPrcv.getNo());
			System.out.println("模板工艺路线:" + prcvNo);
			System.out.println("模板" + prcvNo + "工艺路线无法找到！");
			System.out.println("---------------------------");
			return;
		}
		Prcv oPrcv = prcvs.get(0);
		String oLocation = oPrcv.getLocation();
		String oldFileLocation = "\\\\192.167.36.94" + oLocation;

		InputStream in = null;
		try {
			in = new FileInputStream(oldFileLocation);
		} catch (FileNotFoundException e) {
			System.out.println("---------------------------");
			System.out.println("新工艺路线:" + nPrcv.getNo());
			System.out.println("模板工艺路线:" + prcvNo);
			System.out.println("找不到原工艺路线文件"+oldFileLocation);
			System.out.println("---------------------------");
			return;
		}
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(reader);
		String nPrcvId = nPrcv.getId();
		String nFileName = nPrcvId + ".sfm";
		String nLocation = oLocation.substring(0, oLocation.indexOf("\\", 8)+1) + nFileName;
		String nFname = nPrcv.getNo() + ".sfm";
		String fileDir = "\\\\192.167.36.94" + nLocation;
		File file = new File(fileDir);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("---------------------------");
				System.out.println("新工艺路线:" + nPrcv.getNo());
				System.out.println("模板工艺路线:" + prcvNo);
				System.out.println("找不到文件路径" + fileDir);
				System.out.println("---------------------------");
				return;
			}
		}
		OutputStream out = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		BufferedWriter bw = new BufferedWriter(writer);
		PrintWriter pw = new PrintWriter(bw);
		String oldPrcvId = prcvDAO.checkPrcvByNo(prcvNo).get(0).getId();
		List<Process> oldLists = processDAO.getProcessByPrcvId(oldPrcvId);
		List<Process> newLists = processDAO.getProcessByPrcvId(nPrcvId);
		List<ProcessProcessObjof> oldppoLists = processProcessObjofDAO
				.getByPrcvId(oldPrcvId);
		List<ProcessProcessObjof> newppoLists = processProcessObjofDAO
				.getByPrcvId(nPrcvId);
		Map<String, String> param = new HashMap<String, String>();
		List<String> ngnos = new ArrayList<String>();
		param.put("location", nLocation);
		param.put("fname", nFname);
		param.put("nPrcvId", nPrcvId);
		int onum = oldLists.size();
		int nnum = newLists.size();
		if (onum != nnum) {
			System.out.println("新工艺路线:" + nPrcv.getNo());
			System.out.println("模板工艺路线:" + prcvNo);
			System.out.println("新旧工艺路线不符");
			System.out.println("----------------------------------");
			return;
		}
		for (Process np : newLists) {
			String npgno = String.valueOf(np.getGno());
			ngnos.add(npgno);
		}
		for (Process op : oldLists) {
			String opGno = String.valueOf(op.getGno());
			String opId = op.getId();
			if (!ngnos.contains(opGno)) {
				System.out.println("---------------------------");
				System.out.println("新工艺路线:" + nPrcv.getNo());
				System.out.println("模板工艺路线:" + prcvNo);
				System.out.println("模板工艺下的工序" + opGno + "找不到新工艺路线下对应的工序代号");
				System.out.println("---------------------------");
				return;
			}
			for (Process np : newLists) {
				String npGno = String.valueOf(np.getGno());
				String npId = np.getId();
				if (opGno.equals(npGno)) {
					param.put(opId, npId);
					break;
				}

			}

		}
		for (ProcessProcessObjof oppo : oldppoLists) {
			String oppoId = oppo.getId();
			String oitemid1 = oppo.getItemid1();
			String oitemid2 = oppo.getItemid2();
			if (param.get(oitemid1) == null || param.get(oitemid1).equals("")
					|| param.get(oitemid2) == null
					|| param.get(oitemid2).equals("")) {	
				System.out.println("---------------------------");
				System.out.println("新工艺路线:" + nPrcv.getNo());
				System.out.println("模板工艺路线:" + prcvNo);
				System.out.println("新的工艺路线" + nPrcv.getNo() + "中" + "工序id"
						+ oitemid1 + "或工序id" + oitemid2 + "找不到对应新的工序id");
				System.out.println("---------------------------");
				return;
			}
			for (ProcessProcessObjof nppo : newppoLists) {
				String nppoId = nppo.getId();
				String nitemid1 = nppo.getItemid1();
				String nitemid2 = nppo.getItemid2();

				if (param.get(oitemid1).equals(nitemid1)
						&& param.get(oitemid2).equals(nitemid2)) {
					param.put(oppoId, nppoId);
					break;
				}
			}
		}

		String data;
		StringBuffer buf = new StringBuffer();
		while ((data = br.readLine()) != null) {
			Pattern p = Pattern.compile("WK\\w+");
			Matcher m = p.matcher(data);
			if (m.find()) {
				String key = m.group().substring(3);
				String value = param.get(key);
				if (value == null || value.equals("")) {
					System.out.println("---------------------------");
					System.out.println("新工艺路线:" + nPrcv.getNo());
					System.out.println("模板工艺路线:" + prcvNo);
					System.out.println("新工艺路线" + nPrcv.getNo()
							+ "的配置文件找不到对应替换的id!" + key);
					System.out.println("---------------------------");
					file.delete();
					return;
				}
				m.appendReplacement(buf, "WK_" + value);
				m.appendTail(buf);
				pw.println(buf);
				buf.delete(0, buf.length());
				continue;
			}
			/* System.out.println(data); */
			pw.println(data);
		}
		br.close();
		pw.close();
		InputStream in2 = new FileInputStream(file);
		String fileSize = String.valueOf(in2.available());
		param.put("fsize", fileSize);
		in2.close();
		prcvDAO.updateLoNameFsize(param);
	}

	
	
}
