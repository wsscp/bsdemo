Ext.define('bsmes.store.EventTypeDescStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.EventTypeDescModel',
	autoLoad : false,
	sorters : [ {
		property : 'code',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'eventType/eventTypeDesc'
	}
});