Ext.define('bsmes.controller.SemiFinishedProductsController', {
	extend : 'Oit.app.controller.GridController',
	view : 'semiFinishedProductsView',
	views : [ 'SemiFinishedProductsView'],
	stores : [ 'SemiFinishedProductsStore'],
	constructor : function(){
		var me = this;
		// 初始化refs
		me.refs = me.refs || [];

		me.callParent(arguments);
	}	
});