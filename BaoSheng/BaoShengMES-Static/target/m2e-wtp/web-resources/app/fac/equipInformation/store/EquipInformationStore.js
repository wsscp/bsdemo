Ext.define('bsmes.store.EquipInformationStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.EquipInformation',
	sorters : [ {
		property : 'code',
		direction : 'ASC'
	} ],
	autoLoad : false,
	proxy : {
		type: 'rest',
		url : 'equipInformation'
	}
});