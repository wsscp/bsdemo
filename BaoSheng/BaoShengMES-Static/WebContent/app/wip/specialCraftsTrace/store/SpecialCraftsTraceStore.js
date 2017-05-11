Ext.define("bsmes.store.SpecialCraftsTraceStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.SpecialCraftsTrace',
		proxy : {
			type: 'rest',
			url : 'specialCraftsTrace'
		},
		sorters : [{
			property : 'modifyTime',
			direction : 'DESC'
		}]
});