package cc.oit.bsmes.common.constants;

public enum ProcessCode {
    KAIZHUANG_PT("铠装配套"),
    JIANYAN("检验"),
    RESPOOL("复绕");
    //JACKET_EXTRUSION("护套-挤出"),
    //EXTRUSION_DUAL("挤出-双层"),
    //EXTRUSION_SINGLE("挤出-单层");
/*  EXTRUSION_DUAL("挤出-双层"),
    TAPEWRAP("绕包"),
    IRRADITION("辐照"),
	RESPOOL("复绕"),
    PRINT("印字"),
    STRIPE("印条纹"),
    TWISTING("扭绞"),
    CABLING("成缆"),
    MATCHING_RESPOOLING("配套"),
	BRAIDING("编织"),
    JACKET_EXTRUSION("护套-挤出"),
    JACKET_TAPEWRAP("护套-绕包"),
    SHAOJIE("烧结"),
    JIANYAN("检验"),
    KAIZHUANG("铠装"),
    KAIZHUANG_PT("铠装配套"),
    FENPING("分屏"),
    ZONGPING("总屏"),
    PINGBI("屏蔽"),
    JIAOLIAN("交联");*/
	
	private String name;

	private ProcessCode(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}