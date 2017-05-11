Ext.define('bsmes.store.OrderOAResourceStore', {
    extend : 'Sch.data.ResourceTreeStore',
    model : 'bsmes.model.OrderOAResourceModel',
    proxy: {
    	type: 'ajax',
    	url : '/bsmes/pla/orderOA/getResource',
    	reader: {
             type: 'json'
         }
    }
});
