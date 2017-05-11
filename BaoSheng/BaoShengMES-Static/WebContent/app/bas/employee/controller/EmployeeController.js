Ext.define('bsmes.controller.EmployeeController', {
			extend : 'Oit.app.controller.GridController',
	view : 'employeeList',
	editview : 'employeeedit',
	addview  : 'employeeAdd',
	detailview  : 'employeeDetail',
	views : [ 'EmployeeList', 'EmployeeEdit' ,'EmployeeAdd', 'EmployeeDetail' ],
	stores : [ 'EmployeeStore' ]
});