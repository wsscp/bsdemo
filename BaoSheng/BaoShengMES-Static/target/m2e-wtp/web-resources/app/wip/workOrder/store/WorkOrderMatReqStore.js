Ext.define('bsmes.store.WorkOrderMatReqStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.WorkOrderMat',
	sorters:[{}],
	autoLoad : false,
	proxy : {
		type: 'rest',
		url : 'workOrder/matList'
	}
});