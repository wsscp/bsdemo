Ext.define('bsmes.store.DailyCheckStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.DailyCheck',
    autoLoad:false,
	proxy : {
		type:'rest',
		url : 'dailyCheck/' +  Ext.fly('equipInfo').getAttribute('code')+'/'+Ext.fly('orderInfo').getAttribute('num')
	}
});