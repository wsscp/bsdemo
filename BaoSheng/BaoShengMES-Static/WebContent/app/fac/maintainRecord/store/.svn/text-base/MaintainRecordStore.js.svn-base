Ext.define('bsmes.store.MaintainRecordStore',{
	extend : 'Oit.app.data.GridStore',
	model:'bsmes.model.MaintainRecord',
	sorters : [ {
		property : 'id',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'maintainRecord',
        extraParams : Oit.url.urlParams()
	}
});