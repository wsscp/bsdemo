Ext.define('bsmes.store.CustomerOrderStore',{
    extend : 'Oit.app.data.GridStore',
    model : 'bsmes.model.CustomerOrder',
    sorters : [ {
		property : 'seq',
		direction : 'ASC'
	} ],
    proxy : {
        type: 'rest',
        url : 'customerOrderItem'
    }
});