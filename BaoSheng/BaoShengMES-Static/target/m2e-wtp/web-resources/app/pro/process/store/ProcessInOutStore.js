Ext.define('bsmes.store.ProcessInOutStore',{
	extend:'Ext.data.Store',
	model : 'bsmes.model.ProcessInOut',
	sorters : [{}],
	proxy : {
		type:'rest',
//		url : 'process/queryProcessEquipList',
		api: {
		    create  : undefined,
		    read    : 'process/inOutBz/queryProcessInOutListBz',
		    update  : 'process/inOutBz/updateProcessInOutBz',
		    destroy : undefined
		},
	    reader: {
	            type: 'json',
	            root: 'rows'
        	}
	}
});