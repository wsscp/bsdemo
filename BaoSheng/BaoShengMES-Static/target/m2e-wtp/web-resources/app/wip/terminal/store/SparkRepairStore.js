Ext.define("bsmes.store.SparkRepairStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.SparkRepair',
		proxy : {
			type: 'rest',
			url : '../sparkRepair/findList/'+Ext.fly('equipInfo').getAttribute('code')
		},
		autoLoad: false,
		sorters : []
});