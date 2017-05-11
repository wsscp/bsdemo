Ext.define("bsmes.store.DailyCheckStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.DailyCheck',
		proxy : {
			type: 'rest',
			url : 'dailyCheck'
		},
		sorters : [{}]
});