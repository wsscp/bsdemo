Ext.define('bsmes.store.RightStore',{
	extend:'Oit.app.data.GridStore',
    model:'bsmes.model.SetPriority',
    autoLoad : false,
	sorters : [ {
		property : 'seq',
		direction : 'ASC'
	} ],
	proxy:{
		type: 'rest',
		url:'customerOrderItem/listCustomerOrder/1',
		reader: {
			type: 'json',
			root: 'rows'
		}
	}
});