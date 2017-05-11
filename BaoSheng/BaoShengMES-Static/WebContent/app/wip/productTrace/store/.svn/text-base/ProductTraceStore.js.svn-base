Ext.define("bsmes.store.ProductTraceStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.ProductTrace',
		proxy : {
			type: 'rest',
			url : 'productTrace'
		},
		sorters : [{
			property : 'createTime',
			direction : 'DESC'
		}]
});