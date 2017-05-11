Ext.define('bsmes.store.WorkOrderProductStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.WorkOrderProduct',
	sorters:[{}],
	autoLoad : false,
	proxy : {
		type: 'rest',
		url : 'workOrder/productList'
	}
});