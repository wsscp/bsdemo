Ext.define('bsmes.store.OrderOAEventStore', {
    extend : 'Sch.data.EventStore',
    model : 'bsmes.model.EventModel',
    autoLoad : true,
    proxy: {
    	type: 'ajax',
    	url : '/bsmes/pla/orderOA/getEvent',
    	reader: {
             type: 'json'
         }
    }
});
