package cc.oit.bsmes.common.constants;

/**
 * 
 * 单位
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-17 上午11:20:48
 * @since
 * @version
 */
public enum UnitType {
	TON("吨"), KG("千克/千米"), KM("千米"), M("米"), G("克");

	private String unit;

	private UnitType(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return unit;
	}

	public static UnitType getUnitType(String unit) {
		if ("TON".equalsIgnoreCase(unit)) {
			return UnitType.TON;
		} else if ("KG".equalsIgnoreCase(unit)) {
			return UnitType.KG;
		} else if ("KM".equalsIgnoreCase(unit)) {
			return UnitType.KM;
		} else if ("M".equalsIgnoreCase(unit)) {
			return UnitType.M;
		} else {
			return null;
		}
	}
}
