package cc.oit.bsmes.fac.model;

import cc.oit.bsmes.common.model.Base;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



import javax.persistence.Table;



/**
 * 设备信息
 * 
 * @author leiwei
 * @version 2013-12-24 下午2:23:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_EVE_SPARE_PARTS")
public class SparePart extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String repairedRecordId; // 维修记录ID
	private String eventInfoId; // 事件ID
	private String sparePartModel; // 备件型号规格
	private String useSite; // 使用部位
	private String newSparePartCode; //新备件编码
	private String oldSparePartSituation;//旧备件情况
	private String oldSparePartCode;// 旧备件编码
	private String quantity;// 替换数量

	
}
