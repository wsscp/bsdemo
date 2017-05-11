Ext.define('bsmes.store.EventInformationStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.EventInformation',
	sorters : [ {
		property : 'eventTitle',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'eventInformation'
	}
});