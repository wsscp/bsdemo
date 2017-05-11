package cc.oit.bsmes.wwalmdb.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "v_AlarmHistory")
public class AlarmHistory {

	//	private Date eventStamp;     //报警事件的日期与时间
	private String alarmState;   //报警状态：UNACK、UNACK_RTN、ACK、ACK_RTN
	private String tagName;		 //生成报警的对象的名称
	private String description;  //报警的描述字符串。可以缺省为对象描述(或InTouch HMI中的注释)。或是已确认的记录的确认注释
	private String area;         //报警的"区域"或"组"的名称
	private String type;		 //报警类型，例如Hi、HiHi、ROC、PV.HiAlarm
	private String value;        //报警时报警变量的值
	private String checkValue;   //报警时报警限的值
	private Integer priority;    //报警优先级
	private String category; // 报警类或报警类别。例如"值"、"偏差"、"变化率"、"过程"、"批次"、"系统"，等等。
	private String provider;	 //报警的供应器：节点/InTouch,或GalaxyName
	private String operator;     //操作员的姓名
	private String domainName;   //域的名称
	private String userFullName; //操作员用户的全名
	private String unAckDuration;//最近一次报警转换（报警或子状态）与确认之间的时间
	private Float user1; 		 //用户自定义数字1
	private Float user2; 		 //用户自定义数字2
	private String user3;		 //用户自定义字符串字段
	private Date eventStampUTC;  //报警事件的UTC日期/时间
	private Integer milliSec;    //事件标签秒值的小数部分，以0.1毫秒为增量
	private String operatorNode; //操作员在其中确认报警的节点名称

    public Date getEventStampUTC(){
        if(eventStampUTC == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(eventStampUTC);
        calendar.add(Calendar.HOUR,8);
        return calendar.getTime();
    }

	private String equipCode; // 操作员在其中确认报警的节点名称
	private String tagNameDec; // 标签/属性名称
	
}
