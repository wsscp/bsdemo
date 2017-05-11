Ext.define('bsmes.store.ProcessReceiptTraceStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.ProcessReceiptTrace',
	autoLoad : false,
	remoteSort : false,
	sorters:[{}],
	proxy : {
		type: 'rest',
		url : 'processReceiptTrace'
	}
});