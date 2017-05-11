Ext.define('bsmes.store.CraftsStore',{
	extend:'Oit.app.data.GridStore',
	model : 'bsmes.model.Crafts',
	sorters : [{
		property : 'startDate',
		direction : 'DESC'
	}],
	proxy : {
		type: 'rest',
		url : 'craftsBz'
	}
});