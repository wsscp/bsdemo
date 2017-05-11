Ext.define('bsmes.store.MaintainRecordItemStore',{
	extend : 'Oit.app.data.GridStore',
	model:'bsmes.model.MaintainRecordItem',
	sorters : [ {
		property : 'id',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'maintainRecordItem',
        extraParams : Oit.url.urlParams()
	}
});