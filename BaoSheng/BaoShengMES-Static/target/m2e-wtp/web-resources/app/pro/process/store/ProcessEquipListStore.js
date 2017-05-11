Ext.define('bsmes.store.ProcessEquipListStore',{
	extend:'Ext.data.Store',
	model : 'bsmes.model.ProcessEquipList',
	sorters : [{}],
	proxy : {
		type:'rest',
		api: {
		    create  : undefined,
		    read    : 'process/eqipListBz/queryProcessEquipListBz',
		    update  : 'process/eqipListBz/updateProcessEquipListBz',
		    destroy : undefined
		},
	    reader: {
	            type: 'json',
	            root: 'rows'
        	}
	}
});