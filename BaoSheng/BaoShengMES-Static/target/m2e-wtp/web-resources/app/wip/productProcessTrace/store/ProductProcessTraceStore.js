Ext.define("bsmes.store.ProductProcessTraceStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.ProductProcessTrace',
		proxy : {
			type: 'rest',
			url : 'productProcessTrace'
		},
		sorters : [{
			property : 'batchNo',
			direction : 'ASC'
		}]
});