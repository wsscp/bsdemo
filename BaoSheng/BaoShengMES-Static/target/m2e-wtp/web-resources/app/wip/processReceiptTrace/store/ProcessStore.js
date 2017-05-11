Ext.define('bsmes.store.ProcessStore',{
	extend:'Ext.data.Store',
	model : 'bsmes.model.Process',
	sorters : [{} ],
	proxy : {
		type: 'rest',
		url : 'process'
	}
});