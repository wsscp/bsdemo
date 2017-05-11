Ext.define('bsmes.store.RoleStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.Role',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'role'
	}
});