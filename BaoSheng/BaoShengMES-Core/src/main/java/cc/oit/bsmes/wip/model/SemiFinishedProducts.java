package cc.oit.bsmes.wip.model;



import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class SemiFinishedProducts extends Base {

	private static final long serialVersionUID = -794182168275063469L;
	
	private String matCode;
	private String matName;
	private String contractNo;
	private String locationName;
	private String userName;
	private String shiftName;
	private String productCode;
	private String productType;
	private String productSpec;
	private String taskLength;
	private String processName;
	private String wireCoil;
	private String isUsed;
	private Date finishDate;
    private String reportId; // 报工ID
    private String useStatus; // 半成品使用状态
	
}
