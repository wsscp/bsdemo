Ext.define('bsmes.store.StoreManageStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.StoreManage',
	sorters : [ {
		property : 'userName',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'storeManage'
	}
});