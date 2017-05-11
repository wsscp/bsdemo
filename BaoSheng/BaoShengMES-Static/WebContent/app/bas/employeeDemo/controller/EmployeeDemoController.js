Ext.define('bsmes.controller.EmployeeDemoController', {
			extend : 'Oit.app.controller.GridController',
//	view : 'employeeList',
//	editview : 'employeeedit',
//	addview  : 'employeeAdd',
//	detailview  : 'employeeDetail',
//	views : [ 'EmployeeList', 'EmployeeEdit' ,'EmployeeAdd', 'EmployeeDetail' ],
//	stores : [ 'EmployeeStore' ]
	
	view : 'employeeDemoList',
	views : [ 'EmployeeDemoList'],
	stores : [ 'EmployeeDemoStore' ]
});