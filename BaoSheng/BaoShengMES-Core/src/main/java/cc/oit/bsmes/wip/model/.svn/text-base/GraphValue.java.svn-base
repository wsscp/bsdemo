package cc.oit.bsmes.wip.model;

import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class GraphValue {
	private String name;
	private Double y = 0.0;
	private String color;
	@Transient
	private Double openTime = 0.0; // 开机时间
	@Transient
	private Double totalTime = 0.0; // 总时间
	
}
