Ext.define('bsmes.store.LeftStore',{
	extend:'Oit.app.data.GridStore',
    model:'bsmes.model.WorkOrder',
    autoLoad : false,
	sorters : [ {}],
	proxy:{
		type: 'rest',
		url : 'workOrder/disorderWorkOrder'
	}
});