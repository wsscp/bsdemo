Ext.define('bsmes.store.EquipProcessTraceStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.EquipProcessTrace',
	//fields: ['name', 'id'],
	sorters:[{}],
	/*sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],*/
	proxy : {
		type: 'rest',
		url : 'equipProcessTrace',
		extraParams : {
			equipCode: '',
			contractNo: '',
			batchNum: '',
			realStartTime: '',
			realEndTime: ''
		}
	}
});