package cc.oit.bsmes.wip.model;

import lombok.Data;

/**
 * 生产单生产要求信息
 * Created by JIN on 2015/7/7.
 */
@Data
public class OutAttrDesc {
    private String guidePly;        //指导厚度
    private String standardPly;     //标准厚度
    private String standardMaxPly;  //标称厚度最大值
    private String standardMinPly;  //标称厚度最小值
    private String outsideValue;    //标准外径
    private String outsideMaxValue; //最大外径
    private String outsideMinValue; //最小外径
    private String moldCoreSleeve;  //模芯模套
    private String wiresStructure;  //线芯结构
    private String material;        //物料
    private String conductorStruct; //导体结构
    private String splitLengthRoleWithYuliang; //特殊分段
}
