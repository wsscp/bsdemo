package cc.oit.bsmes.bas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.model.Amoeba;
import cc.oit.bsmes.bas.service.AmoebaService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;

import com.alibaba.fastjson.JSONObject;

/**
 * 阿米巴
 * @author 王国华
 * @date  2017-01-06 13:52:41
 */
@Service
public class AmoebaServiceImpl extends BaseServiceImpl<Amoeba> implements AmoebaService {

	@Override
	@Transactional(readOnly = false)
	public void importAmoebaData(Sheet sheet, JSONObject result) throws Exception {
		List<Amoeba> amoebaList = new ArrayList<Amoeba>();
		List<Amoeba> amoebaList2 = new ArrayList<Amoeba>(); // 更新使用
		int maxRow = sheet.getLastRowNum() + 1;
		String col00 = null;
		int endCols = 0;
		for (int i = 0; i < maxRow; i++) {
			
			Row row = sheet.getRow(i);
			if(row != null)
			{
				StringBuffer str1 = new StringBuffer();
				for(int j = 0 ; j < row.getLastCellNum() ; j++)
				{
					str1.append(j + ":");
					str1.append(getCellValue(row, j)+"; ");
				}
				System.out.println(str1.toString());
			}
			if(row == null)
			{
				System.err.println(i);				
			}
			else if(row.getCell(1)!=null){
				if("截面".equals(getCellValue(row,0)))
				{
					if("合计生产费用".equals(getCellValue(row,17)))
					{
						endCols = 17;
					}
					else if("合计生产费用".equals(getCellValue(row,21)))
					{
						endCols = 21;
					}
				}
				else {
					if(StringUtils.isEmpty(getCellValue(row, 1)))
					{
						continue;
					}

					if(StringUtils.isNotEmpty(getCellValue(row, 0)))
					{
						col00 = getCellValue(row, 0);
					}
					Amoeba amoeba = new Amoeba();
					amoeba.setCol00(col00);
					for(int j = 1 ; j < endCols;j++){
						if(j < 10)
						{
							BeanUtils.setProperty(amoeba, "col0"+j, getCellValue(row, j));
						}
						else {
							BeanUtils.setProperty(amoeba, "col"+j, getCellValue(row, j));
						}
					}
					// 合计生产费用
					amoeba.setCol30(getCellValue(row, endCols));
					Amoeba findParams = new Amoeba();
					// 截面
					findParams.setCol00(amoeba.getCol00());
					// 类型
					findParams.setCol01(amoeba.getCol01());
					// 绝缘料col07
					findParams.setCol07(amoeba.getCol07());
					List<Amoeba> listCurData = this.getByObj(findParams);
					if(listCurData == null || listCurData.size() == 0)
					{
						amoebaList.add(amoeba);
					}
					else {
						amoeba.setId(listCurData.get(0).getId());
						amoebaList2.add(amoeba);
					}
				}
			}
		}
		if(amoebaList.size()>0|| amoebaList2.size()>0){
			insert(amoebaList);
			update(amoebaList2);
			result.put("success", true);
			result.put("message", "成功导入数据，新增：" + amoebaList.size() + "条，修改：" + amoebaList2.size() + "条，请确认！");
		}else{
			result.put("message", "没有导入数据，请确认！");
		}
		
	}


	public String getCellValue(Row row,int col)
	{
		String value = "";
		if(row.getCell(col)!=null)
		{
			Cell cell = row.getCell(col);
			if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			{
				return cell.getNumericCellValue()+"";
			}
			else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA)
			{
				try {
                     value = String.valueOf(new BigDecimal(cell.getNumericCellValue()).setScale(2,java.math.BigDecimal.ROUND_HALF_UP));
                } catch (IllegalStateException e) {
                     value = String.valueOf(cell.getRichStringCellValue());
                }
				return value;
			}
			else {
				return cell.getRichStringCellValue().toString();
			}
		}
		else{
			return "";
		}
	}
}
