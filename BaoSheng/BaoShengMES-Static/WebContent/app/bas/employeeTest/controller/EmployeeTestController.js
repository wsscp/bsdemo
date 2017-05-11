Ext.define('bsmes.controller.EmployeeTestController', {
			extend : 'Oit.app.controller.GridController',
	view : 'employeeList',
	editview : 'employeeedit',
	addview  : 'employeeAdd',
	detailview  : 'employeeDetail',
	views : [ 'EmployeeList', 'EmployeeEdit' ,'EmployeeAdd', 'EmployeeDetail' ],
	stores : [ 'EmployeeStore' ]
});