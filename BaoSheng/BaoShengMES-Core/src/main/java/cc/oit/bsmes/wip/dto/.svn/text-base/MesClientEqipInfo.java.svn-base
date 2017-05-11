package cc.oit.bsmes.wip.dto;

import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.pla.model.Product;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class MesClientEqipInfo {

	private String mesClientId;
    private String eqipId;
    private String equipCode;
    private String equipName;
    private String equipAlias;//设备别名
    private EquipStatus status; //设备状态
    private double planLength;//计划长度
    private double qualifiedLength;//合格长度
    private int toDoTaskNum;//待生产任务数

    private String contracts;
    private int discNo; // 1、2、3

    private String workOrderNo;//当前任务
    private double remainQLength; // 剩余合格长度
    private double remainQLengthExceptDisc; // 排除当前盘的剩余合格长度
    private double currentDiscQLength;//当前盘合格长度
    private double currentDiscLength;//当前盘生产长度
    private double currentReportLength;//当前可报工长度
    

    private Set<Product> products;

    private String currentProcess;
    private String currentProduct;

    private Double sumReportLength;

    private String productColor;

    private String productSpec;
    private String productType;
    private String wiresStructure;

    private String needFirstCheck; // 是否要首检
    private String needMiddleCheck; // 是否要中检
    private String needInCheck; // 是否要上车检
    private String needOutCheck; // 是否要下车检
    private Double contractLength; // 生产单长度/投产长度
    private Date releaseDate;
    private Date requireFinishDate;
    private String section;
    
    private String energyConsumptio; //本班能耗
    
}