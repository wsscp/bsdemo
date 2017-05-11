Ext.define("bsmes.store.EventTraceStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.EventTrace',
		proxy : {
			type: 'rest',
			url : 'eventTrace'
		},
		sorters : [{
			property : 'code',
			direction : 'ASC'
		}]
});