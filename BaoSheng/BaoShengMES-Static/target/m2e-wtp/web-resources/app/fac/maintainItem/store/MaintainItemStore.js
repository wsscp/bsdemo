Ext.define('bsmes.store.MaintainItemStore',{
	extend : 'Oit.app.data.GridStore',
	model:'bsmes.model.MaintainItem',
	sorters : [ {
		property : 'id',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'maintainItem',
        extraParams : Oit.url.urlParams()
    }
});