package cc.oit.bsmes.generator.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 羽霓 on 2014/6/10.
 */
@Data
public class Table {

    private StringBuffer modelName;
    private String name;
    private String comment;
    private Map<String, Column> columns = new HashMap<String, Column>();

    public void addColumn(Column column) {
        columns.put(column.getName(), column);
    }

    public Column getColumn(String columnName) {
        return columns.get(columnName);
    }

    public String getModelName() {
        if (modelName == null) {
            modelName = new StringBuffer();
            String[] splits = name.split("_");
            for (int i = 2; i < splits.length ; i++) {
                modelName.append(StringUtils.capitalize(splits[i].toLowerCase()));
            }
        }
        return modelName.toString();
    }

}
