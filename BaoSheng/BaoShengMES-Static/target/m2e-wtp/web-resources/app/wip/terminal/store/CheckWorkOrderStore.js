Ext.define('bsmes.store.CheckWorkOrderStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.WorkOrder',
	proxy : {
		url : 'terminal/recentOrders'
	},
	autoLoad : false
});