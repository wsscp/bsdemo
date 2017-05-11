Ext.define('bsmes.store.CraftsSLStore',{
	extend:'Oit.app.data.GridStore',
	model : 'bsmes.model.CraftsSL',
	sorters : [{
		property : 'craftsCode',
		direction : 'ASC'
	}],
	proxy : {
		type: 'rest',
		url : 'craftsSL'
	}
});