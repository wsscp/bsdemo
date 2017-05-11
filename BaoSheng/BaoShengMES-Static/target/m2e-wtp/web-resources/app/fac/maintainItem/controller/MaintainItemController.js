Ext.define('bsmes.controller.MaintainItemController',{
	extend : 'Oit.app.controller.GridController',
	view : 'maintainItemList',
	editview : 'maintainItemEdit',
	addview : 'maintainItemAdd',
	views : ['MaintainItemList', 'MaintainItemEdit' ,'MaintainItemAdd'],
	stores : ['MaintainItemStore']
});