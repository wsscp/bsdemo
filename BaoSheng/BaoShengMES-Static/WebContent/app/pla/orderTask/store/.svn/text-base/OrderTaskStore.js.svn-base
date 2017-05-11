Ext.define('bsmes.store.OrderTaskStore', {
	extend: 'Sch.data.ResourceTreeStore',
	model : 'bsmes.model.OrderTask',
	proxy: {
		type: 'ajax',
		url : 'orderTask/order',
		reader: {
	        type: 'json'
	    },
         timeout :120000
	}
});
