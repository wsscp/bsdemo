package cc.oit.bsmes.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.pro.dao.EquipListDAO;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProductProcessService;

@Service
public class EquipListServiceImpl extends BaseServiceImpl<EquipList> implements EquipListService {

	private static final int MAX_PARENT_TABLE_CELL_INDEX = 1;

	@Resource
	private EquipListDAO equipListDAO;
	@Resource
	private ProductProcessService productProcessService;

	@Override
	public List<EquipList> getToolsByProcessId(String processId) {
		EquipList findParams = new EquipList();
		findParams.setType(EquipType.TOOLS);
		findParams.setProcessId(processId);
		return equipListDAO.get(findParams);
	}

	@Override
	public EquipList getByProcessIdAndEquipCode(String processId, String equipCode) {
		EquipList findParams = new EquipList();
		findParams.setProcessId(processId);
		findParams.setEquipCode(equipCode);
		List<EquipList> result = equipListDAO.get(findParams);
		return (!CollectionUtils.isEmpty(result)) ? result.get(0) : null;
	}

	@Override
	public List<EquipList> getByProcessId(String processId) {
		EquipList findParams = new EquipList();
		findParams.setProcessId(processId);
		return equipListDAO.getByProcessId(findParams);
	}

	@Override
	public List<EquipList> getByProcessId(String processId, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return getByProcessId(processId);
	}

	@Override
	public List<EquipList> getIdleEquip(String processId) {
		EquipList findParams = new EquipList();
		findParams.setProcessId(processId);
		findParams.setIsDefault(false);
		findParams.setType(EquipType.PRODUCT_LINE);
		return equipListDAO.get(findParams);
	}

	@Override
	@Transactional(readOnly = false)
	public void importEquipList(Sheet sheet, String orgCode) {
		int maxRow = sheet.getRows();

		String craftCode = null;
		String processId = null;
		for (int i = 1; i < maxRow; i++) {
			Cell[] cells = sheet.getRow(i);
			if (isEmptyRow(cells)) {
				break;
			}

			if (!JxlUtils.getRealContents(cells[0]).isEmpty()) {
				craftCode = JxlUtils.getRealContents(cells[0]);
			}
			if (!JxlUtils.getRealContents(cells[1]).isEmpty()) {
				processId = JxlUtils.getRealContents(cells[1]);
			}

			EquipList equipList = new EquipList();
			equipList.setProcessId(processId);
			equipList.setShutDownTime(0);
			equipList.setType(EquipType.PRODUCT_LINE);

			for (int j = MAX_PARENT_TABLE_CELL_INDEX + 1; j < cells.length; j++) {
				Cell cell = cells[j];
				setProperty(equipList, cell, j);
			}
			equipListDAO.insert(equipList);
		}
	}

	private void setProperty(EquipList equipList, Cell cell, int index) {
		String contents = JxlUtils.getRealContents(cell);
		switch (index) {
		case 2:
			equipList.setEquipCode(contents);
			break;
		case 3:
			equipList.setIsDefault(contents.equals("是"));
			break;
		case 4:
			equipList.setEquipCapacity(Double.parseDouble(contents) / 60);
			break;
		case 5:
			equipList.setSetUpTime(Integer.parseInt(contents) * 60);
			break;
		default:
			break;
		}
	}

	private boolean isEmptyRow(Cell[] cells) {
		Cell cell = cells[MAX_PARENT_TABLE_CELL_INDEX + 1];
		return JxlUtils.getRealContents(cell).isEmpty();
	}

	@Override
	public List<EquipList> getByEquipCode(String equipCode) {
		return equipListDAO.getByEquipCode(equipCode);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertBatch(List<EquipList> list) {
		equipListDAO.insertBatch(list);
	}
}
