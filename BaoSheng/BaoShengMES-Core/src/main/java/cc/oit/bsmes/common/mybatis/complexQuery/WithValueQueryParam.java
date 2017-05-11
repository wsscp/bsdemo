package cc.oit.bsmes.common.mybatis.complexQuery;

import lombok.Getter;

/**
 * Created by 羽霓 on 2014/6/27.
 */
public class WithValueQueryParam extends CustomQueryParam {

    @Getter
    private Object value;

    @Getter
    private String operator;

    public WithValueQueryParam(String property, String operator, Object value) {
        super.property = property;
        this.operator = operator;
        this.value = value;
    }

}
