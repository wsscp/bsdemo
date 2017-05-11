Ext.define('bsmes.store.ProcessReceiptStore',{
	extend:'Ext.data.Store',
	model : 'bsmes.model.ProcessReceipt',
	sorters : [{}],
	proxy : {
		type:'rest',
//		url : 'process/queryProcessEquipList',
		api: {
		    create  : undefined,
		    read    : 'process/receipt/queryProcessReceiptList',
		    update  : 'process/receipt/updateProcessReceipt',
		    destroy : undefined
		},
	    reader: {
	            type: 'json',
	            root: 'rows'
        	}
	}
});