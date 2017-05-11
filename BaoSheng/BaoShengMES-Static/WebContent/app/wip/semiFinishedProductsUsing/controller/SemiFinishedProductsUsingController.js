Ext.define('bsmes.controller.SemiFinishedProductsUsingController', {
	extend : 'Oit.app.controller.GridController',
	view : 'semiFinishedProductsUsingView',
	views : [ 'SemiFinishedProductsUsingView'],
	stores : [ 'SemiFinishedProductsUsingStore'],
	constructor : function(){
		var me = this;
		// 初始化refs
		me.refs = me.refs || [];

		me.callParent(arguments);
	}	
});