package cc.oit.bsmes.interfaceWWIs.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * sql 中的数据
 * Created by zdp on 14-3-17.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INT_SPARK_HISTORY")
public class SparkHistory extends Base {
 
	private static final long serialVersionUID = -6366642790178446327L;
	private String tagName;
    private Double length;
 
}
