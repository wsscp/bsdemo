Ext.define('bsmes.store.EmployeeStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.Employee',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'employee'
	}
});