package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 
 * 刷卡记录
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2014-2-24 下午5:50:29
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_ONOFF_RECORD")
public class OnoffRecord extends Base{

	private static final long serialVersionUID = 7156931186834695628L;

	private String userCode;
    private Date onTime;
    private Date offTime;
    private String exceptionType;
    private String clientName;
    private String orgCode;
    private String shiftId;
    private String equipCodes;
    private String userName;
    @Transient
    private String certificate;


}