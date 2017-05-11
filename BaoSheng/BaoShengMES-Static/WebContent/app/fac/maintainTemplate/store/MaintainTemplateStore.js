Ext.define('bsmes.store.MaintainTemplateStore',{
	extend : 'Oit.app.data.GridStore',
	model:'bsmes.model.MaintainTemplate',
	sorters : [ {
		property : 'id',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'maintainTemplate'
	}
});