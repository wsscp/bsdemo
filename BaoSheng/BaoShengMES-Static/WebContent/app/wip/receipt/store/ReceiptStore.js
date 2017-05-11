Ext.define("bsmes.store.ReceiptStore",{
		extend: 'Oit.app.data.GridStore',
		model : 'bsmes.model.Receipt',
		proxy : {
			type: 'rest',
			url : 'receipt'
		},
		sorters : [{}]
});