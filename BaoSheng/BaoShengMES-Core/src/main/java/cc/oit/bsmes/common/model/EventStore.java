package cc.oit.bsmes.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventStore  implements Serializable { 
	private static final long serialVersionUID = 875233181837355698L;
	private String resourceId;
	private String name;
	private String startDate;
	private String endDate;
	private String customerOrderNo;
	private String halfProductCode;
	private String productCode;	
	private String productSpec;	
	private Double taskLength;
	private String contractNo;	
	private String halfProductCodeColor; 
	private String workOrderNo; 
	private String equipCode;
	private int level;
	private String percentDone;
	private double unfinishedLength;
	private double finishedLength;
	private double usedStockLength;
	private String outPut;
}
