Ext.define('bsmes.store.ZoneStore', {
    extend : 'Ext.data.Store',
    model : 'bsmes.model.Zone',
    autoLoad : true,
    proxy: {
    	type: 'ajax',
    	url : '/bsmes/pla/orderOA/zone',
    	reader: {
             type: 'json'
         }
    }
});
