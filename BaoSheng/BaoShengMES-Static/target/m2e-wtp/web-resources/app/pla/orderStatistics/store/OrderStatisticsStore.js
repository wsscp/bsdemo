Ext.define('bsmes.store.OrderStatisticsStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.OrderStatistics',
	autoLoad : false,
	sorters : [{}],
	proxy : {
		type: 'rest',
		url : 'orderStatistics'
	}
});