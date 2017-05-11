Ext.define('bsmes.store.LeftStore',{
	extend:'Oit.app.data.GridStore',
    model:'bsmes.model.SetPriority',
    autoLoad : false,
	sorters : [ {
		property : 'importance',
		direction : 'ASC'
	}],
	proxy:{
		type: 'rest',
		url:'customerOrderItem/listCustomerOrder',
	    reader: {
             type: 'json',
             root: 'rows'
         	}
	}
});