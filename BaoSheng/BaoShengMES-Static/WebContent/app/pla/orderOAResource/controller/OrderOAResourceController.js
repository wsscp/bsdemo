Ext.define('bsmes.controller.OrderOAResourceController', {
	extend : 'Ext.app.Controller',
	view : 'orderOAResource',
	views : [ 'OrderOAResource','Toolbar'],
	stores : [ 'OrderOAEventStore','OrderOAResourceStore','ZoneStore'],
	constructor: function () {
	        var me = this;
	        me.callParent(arguments);
				// setInterval(function() {
				// me.refresh.apply(me);
				//	        }, 60000);
	},
	refresh:function(){
		var  searchButton=Ext.getCmp("goSearchButtonid");
		searchButton.fireEvent('click');
	}
});