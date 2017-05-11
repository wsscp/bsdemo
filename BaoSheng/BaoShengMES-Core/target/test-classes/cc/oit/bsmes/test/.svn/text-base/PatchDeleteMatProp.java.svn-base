package cc.oit.bsmes.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.model.MatProp;
import cc.oit.bsmes.inv.model.TempletDetail;
import cc.oit.bsmes.inv.service.MatPropService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.inv.service.TempletDetailService;
import cc.oit.bsmes.junit.BaseTest;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 删除物料表多余的属性 T_INV_MAT、T_INV_MAT_PROP、T_INV_TEMPLET_DETAIL
 */
public class PatchDeleteMatProp extends BaseTest {
	@Resource
	private MatService matService;
	@Resource
	private MatPropService matPropService;
	@Resource
	private TempletDetailService templetDetailService;

	private int limit = 100000;
	
	/**
	 * bean初始化后执行的方法
	 * */
	@PostConstruct
	public void initMethod() {
	}

	/**
	 * 执行函数
	 * 
	 * @throws IOException
	 * */
	@Test
	@Rollback(false)
	public void process() throws IOException {
		// 写入中文字符时解决中文乱码问题
		File f = new File("D:/PatchDeleteMatProp_2.txt");
		if (!f.exists()) {// 判断文件是否真正存在,如果不存在,创建一个;
			f.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(f, true);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);

		int count = 0;
		List<Mat> matArray = matService.getAll();
		Multimap<String, MatProp> matPropArrayMap = ArrayListMultimap.create();
		matLoop : for (Mat mat : matArray) {
			// 测试过滤，先测一条
			// if (!"CZ50100050006-5*1.5-10-001".equals(mat.getMatCode())) {
			// continue;
			// }
			
			TempletDetail findParams = new TempletDetail();
			findParams.setTempletId(mat.getTempletId());
			List<TempletDetail> templetDetailArray = templetDetailService.findByObj(findParams);
			for(TempletDetail templetDetail : templetDetailArray){
				MatProp matProp = new MatProp();
				matProp.setMatId(mat.getId());
				matProp.setTempletDetailId(templetDetail.getId());
				List<MatProp> matPropArray = matPropService.findByObj(matProp);
				System.out.println(matPropArray.size());
				bw.write(matPropArray.size() + "\r\n");
			}

			

//			System.out.println("matPropArray.size():"+ matPropArray.size());
//			// 根据属性分组，一个属性应该只有一个，旧的删除
//			matPropArrayMap.clear();
//			for (MatProp matProp : matPropArray) {
//				matPropArrayMap.put(matProp.getTempletDetailId(), matProp);
//			}
//
//			// 遍历处理每一个属性，大于1就处理
//			Iterator<String> matPropKeys = matPropArrayMap.keySet().iterator();
//			while (matPropKeys.hasNext()) {
//				String templetDetailId = (String) matPropKeys.next();
//				Collection<MatProp> matPropCollection = matPropArrayMap.get(templetDetailId);
//				if (matPropCollection.size() > 1) {
//					// 循环一次获取保留的MatProp
//					MatProp matProp = null;
//					Date createTime = null;
//					for (MatProp MatPropLoop : matPropCollection) {
//						if (null != createTime && createTime.before(MatPropLoop.getCreateTime())) {
//							createTime = MatPropLoop.getCreateTime();
//							matProp = MatPropLoop;
//						}
//						if (null == matProp || null == createTime) {
//							createTime = MatPropLoop.getCreateTime();
//							matProp = MatPropLoop;
//						}
//					}
//
//					// 这次循环做删除了，呵呵呵
//					if (null != matProp) {
//						for (MatProp matPropLoop : matPropCollection) {
//							if(count > limit){
//								break matLoop;
//							}
//							if (matPropLoop.getId().equals(matProp.getId())) {
//								continue;
//							}
//							matPropService.delete(matPropLoop);
//							count++;
//							String log = "删除了物料：" + mat.getMatName() + "[" + mat.getMatCode() + "]的属性名ID："
//									+ matPropLoop.getTempletDetailId() + "，值：" + matPropLoop.getPropTargetValue();
//							System.out.println(log);
//							bw.write(log + "\r\n");
//						}
//					}
//				}
//			}
		}

		// 注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
		bw.close();
		osw.close();
		fos.close();
	}
}
