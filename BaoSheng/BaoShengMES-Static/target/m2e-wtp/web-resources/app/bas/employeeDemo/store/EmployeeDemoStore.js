Ext.define('bsmes.store.EmployeeDemoStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.EmployeeDemo',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'employeeDemo'//请求数据的地址，EmployeeDemoController里面注解设置的地址
	}
});