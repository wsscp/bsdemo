Ext.define('bsmes.store.WorkOrderAdjustStore', {
	extend : 'Ext.data.Store',
	model : 'bsmes.model.WorkOrder',
	//fields: ['name', 'id'],
	sorters:[{}],
	/*sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],*/
	proxy : {
		type: 'rest',
		url : 'workOrder/workOrderAdjustList'
	}
});