package cc.oit.bsmes.fac.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;

/**
 * 维护记录项
 * @author chanedi
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_MAINTAIN_RECORD_ITEM")
public class MaintainRecordItem extends Base {

	private static final long serialVersionUID = -1442539735L;

	private Boolean isPassed; //是否通过检查
	private String remarks; //备注
	private String recordId; //点检表
	private Double value; //测量值
    private String describe; //项目说明

}