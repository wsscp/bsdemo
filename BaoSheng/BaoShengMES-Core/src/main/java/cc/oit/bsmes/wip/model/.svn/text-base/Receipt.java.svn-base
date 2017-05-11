package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.constants.ReceiptStatus;
import cc.oit.bsmes.common.constants.ReceiptType;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_RECEIPT")
public class Receipt extends Base{
	
	private static final long serialVersionUID = 4379432718926563292L;
	private String workOrderId;
    private String receiptId;
    private String receiptCode;
    private String receiptName;
    private String equipCode;
    private ReceiptStatus status;
    private String orgCode;
    private Date confirmTime;
    private Date issuedTime;
    private String receiptTargetValue;
    private String receiptMaxValue;
    private String receiptMinValue;
    private Boolean needAlarm;
    private ReceiptType type;
    @Transient
    private String workOrderNo;
    @Transient
    private String productCode;

    /**
     * 更新频率，秒 例如 ：3 表示 3秒钟更新一下数据
     */
    private double frequence;
    /**
     * 采集值在内存中的最后更新时间
     */
    @Transient
    private Date lastDate;

   /**
     * 采集对象的加载时间
     */
    @Transient
    private Date lastLoadDate;
    /**
     * 最后访问时间
     */
    @Transient
    private Date accessDate;
    @Transient
    private String processId;
    @Transient
    private String tagName;
    //数据采集值
    @Transient
    private String daValue;
    @Transient
    private String dataUnit;
}