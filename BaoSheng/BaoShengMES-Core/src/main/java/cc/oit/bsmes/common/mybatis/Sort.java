package cc.oit.bsmes.common.mybatis;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jijy on 2014/7/4.
 */
@Data
public class Sort implements Serializable {

    private static final long serialVersionUID = 7026434198845897214L;
    private String property;
    private String column;
    private Direction direction;

    public Sort() {
        super();
        direction = Direction.ASC;
    }

    public Sort(String property) {
        this();
        this.property = property;
    }

    public Sort(String property, Direction direction) {
        this();
        this.property = property;
        this.direction = direction;
    }

    public static enum Direction {
        ASC, DESC;
    }

}
