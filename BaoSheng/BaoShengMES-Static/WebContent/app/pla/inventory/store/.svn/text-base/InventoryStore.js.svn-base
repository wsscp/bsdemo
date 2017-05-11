Ext.define('bsmes.store.InventoryStore',{
    extend: 'Oit.app.data.GridStore',
    model : 'bsmes.model.Inventory',
    sorters : [ {
		property : 'createTime',
		direction : 'DESC'
	} ],
	proxy:{
        type: 'rest',
        url:'inventory'
    }
});