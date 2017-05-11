Ext.define('bsmes.store.AgreementStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.Agreement',
    autoLoad:false,
	proxy : {
		type:'rest',
		url : 'agreement/' + Ext.fly('orderDetail').getAttribute('num')
	}
});