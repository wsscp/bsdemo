package cc.oit.bsmes.pro.model;

import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.model.Base;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_EQIP_LIST_WIP")
public class EquipListWip extends EquipList{

	
	private static final long serialVersionUID = -9183165960447638796L;
	
	private String equipCode; // 辅助设备CODE
	private String processId; // 流程ID
	private EquipType type; // 设备类型
	private Integer setUpTime;  // 前置时间
	private Integer shutDownTime = 0; //后置时间
	private Double equipCapacity; // 设备能力
    private Boolean isDefault; // 默认生产线
	@Transient
	@JSONField(serialize=false)
	private List<ProcessReceipt> processReceiptList;

	@Transient
	private String equipName; // 设备名称
	@Transient
	private String equipAlias; // 设备别名
	
	private Double fxpj; // 设备能力
	private Double sxpj; // 设备能力
	private Double fxzdzpl; // 设备能力
	private Double sxzdzpl; // 设备能力

    @Transient
    public long getSetUpTimeMilSec() {
        if(setUpTime!=null){
            return (long) (setUpTime * 1000);
        }else{
            return 0;
        }
    }

    @Transient
    public long getShutDownTimeMilSec() {
        if(shutDownTime!=null){
            return (long) (shutDownTime * 1000);
        }else{
            return 0;
        }
    }


}
