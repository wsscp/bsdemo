package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_USER_CV")
public class Resume extends Base{
	
	private static final long serialVersionUID = -5972008894240878384L;
	
	//用户编码
	@NotNull
	private String userCode;
	
	private String politicalClimate; //政治面貌
	
	private String originPlace; // 籍贯
	
	private String birthPlace; //出生地
	
	private String gender; //性别
	
	private String idNumbers;//身份证号码
	
	private String birthDate; //出生日期
	
	private String entryDate;//入职日期
	
	private String maritalStatus;//婚姻状态
	
	private String accountProperties;//户口性质
	
	private String homeAddress; //家庭住址
	
	private String phoneNumber; //手机号码
	
	private String admissionDate;//入学日期
	
	private String graduationDate; //毕业日期
	
	private String school;//学校
	
	private String isAviationCollege;//是否航空院校
	
	private String education;//学历
	
	private String degree; //学位
	
	private String studyWay;//学习方式
	
	private String remarks;//备注
	@Transient
	private String userName;
}