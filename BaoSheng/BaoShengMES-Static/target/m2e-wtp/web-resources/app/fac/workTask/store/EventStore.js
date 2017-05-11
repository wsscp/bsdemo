Ext.define('bsmes.store.EventStore', {
    extend : 'Sch.data.EventStore',
    model : 'bsmes.model.EventModel',
    autoLoad : true,
    autoSync : true,  
    proxy: {
    	type: 'ajax',
    	url : '/bsmes/fac/workTask/getTasks',
    	reader: {
            type: 'json'
        }
    }
});

