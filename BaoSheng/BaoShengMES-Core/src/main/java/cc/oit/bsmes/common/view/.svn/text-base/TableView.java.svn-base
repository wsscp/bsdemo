package cc.oit.bsmes.common.view;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TableView implements Serializable {

    private static final long serialVersionUID = -3635908615839995480L;
    private List<?> rows;
	
	private Integer total;
	
	public void setRows(List<?> rows) {
		this.rows = rows;
		if (total == null) {
			total = rows.size();
		}
	}

}
