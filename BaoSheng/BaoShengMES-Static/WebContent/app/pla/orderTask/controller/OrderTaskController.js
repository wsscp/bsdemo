Ext.define('bsmes.controller.OrderTaskController', {
	extend : 'Oit.app.controller.GridController',
	view : 'orderTaskList',
	views : [ 'OrderTaskList' ],
	stores : [ 'OrderTaskStore' ],
	constructor: function() {
		var me = this;
		// 初始化refs
		me.refs = me.refs || [];

		me.callParent(arguments);
	},
	init: function() {
		var me = this;
		if (!me.view) {
			Ext.Error.raise("A view configuration must be specified!");
		}
		
	}
	
});