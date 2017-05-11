Ext.define('bsmes.store.RightStore',{
	extend:'Oit.app.data.GridStore',
    model:'bsmes.model.WorkOrder',
    autoLoad : false,
	sorters : [ {
		property : 'seq',
		direction : 'ASC'
	} ],
	proxy:{
		type: 'rest',
		url : 'workOrder/seqWorkOrder'
	}
});