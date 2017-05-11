Ext.define('bsmes.store.WorkOrderToolsReqStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.WorkOrderTools',
	sorters:[{}],
	autoLoad : false,
	proxy : {
		type: 'rest',
		url : 'workOrder/toolsList'
	}
});