Ext.define('bsmes.store.EventTypeStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.EventType',
	sorters : [ {
		property : 'name',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'eventType'
	}
});