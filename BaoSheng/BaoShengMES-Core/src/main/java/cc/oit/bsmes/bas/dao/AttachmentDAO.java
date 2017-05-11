package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.Attachment;
import cc.oit.bsmes.common.dao.BaseDAO;

public interface AttachmentDAO extends BaseDAO<Attachment> {

	Attachment getByProductIDAndLocation(String productId, String location);
	
	public void deleteOne(Attachment param);

}
