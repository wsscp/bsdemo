Ext.define('bsmes.store.MaterialMngReportStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.MaterialMngReport',
	sorters : [{}],
	proxy : {
		url : 'materialMngReport'
	}
});