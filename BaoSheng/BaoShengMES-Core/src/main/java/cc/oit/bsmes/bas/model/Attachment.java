package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
/**
 * 
 * 附件信息表
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-9-29 上午10:39:52
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_ATTACHMENT")
public class Attachment extends Base {
	private static final long serialVersionUID = 7023442634442979122L;
	
	private String fileName;   //文件名
	private String realFileName; //物理文件名
	private InterfaceDataType ownerModule;  //引用数据refType 模块
	private String contentType;  //文件类型
	private Long contentLength; //文件大小（K）
	private String downloadPath;  //文件下载路径
	private String refId;		  //引用数据ID
	private String subType;		  //引用数据 附件分类适用于一条主记录对应多个不同的附件，而且使用方式不一致的情况
}
