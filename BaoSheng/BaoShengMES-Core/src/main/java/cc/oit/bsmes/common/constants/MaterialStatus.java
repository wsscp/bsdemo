package cc.oit.bsmes.common.constants;

import cc.oit.bsmes.pla.model.MaterialRequirementPlan;

/**
 * 
 * 物料状态
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-1-17 上午11:14:18
 * @since
 * @version
 */
public enum MaterialStatus {
	UNAUDITED("未使用"),FINISHED("已使用"),MAT_UN_DOWN("未要料"),MAT_DOWN("已要料"),MAT_GETED("已发料"),MAT_BORROW("已补料");
	
	private String materialType;
	
	private MaterialStatus(String materialType){
		this.materialType=materialType;
	}
	
	@Override
	public String toString() {
		return materialType;
	}
	
public static void main(String arg[])
{
	
	MaterialStatus[] tc = MaterialStatus.values();
	
	for(MaterialStatus termsCodeType : tc){
		MaterialRequirementPlan dataDic = new MaterialRequirementPlan();
		dataDic.setStatusCode(termsCodeType.name());
		dataDic.setStatusCode(termsCodeType.toString());
		System.out.println(termsCodeType.name());
		System.out.println(termsCodeType.toString());
	}
	
	}
}
