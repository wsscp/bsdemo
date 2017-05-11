Ext.define('bsmes.store.ProcessQcStore',{
	extend:'Ext.data.Store',
	model : 'bsmes.model.ProcessQc',
	sorters : [ {
		property : 'checkItemName',
		direction : 'ASC'
	}],
	proxy : {
		type:'rest',
		url : 'processBz/queryProcessQc',
	    reader: {
	            type: 'json',
	            root: 'rows'
        	}
	}
});