Ext.define('bsmes.store.Events', {
    extend : 'Sch.data.EventStore',
    model : 'bsmes.model.Event',
    autoLoad : true,
    proxy : {
		type: 'ajax',
		url : 'orderTask/event',
		reader: {
            type: 'json'
        },
         timeout :120000
	}
});
