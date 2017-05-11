Ext.define('bsmes.store.EquipInfoStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.EquipInfo',
	sorters : [ {
		property : 'code',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'equipInfo',
        extraParams : Oit.url.urlParams()
	}
});