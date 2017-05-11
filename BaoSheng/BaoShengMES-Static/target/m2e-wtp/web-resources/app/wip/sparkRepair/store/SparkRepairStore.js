Ext.define("bsmes.store.SparkRepairStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.SparkRepair',
		proxy : {
			type: 'rest',
			url : 'sparkRepair'
		},
		sorters : [ {
			property : 'createTime',
			direction : 'DESC'
		} ]
});