Ext.define('bsmes.store.UserStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.User',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'userCode',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'user'
	}
});