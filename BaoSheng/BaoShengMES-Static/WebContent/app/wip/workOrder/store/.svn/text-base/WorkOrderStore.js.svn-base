Ext.define('bsmes.store.WorkOrderStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.WorkOrder',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'preStartTime',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'workOrder'
	}
});