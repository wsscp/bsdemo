Ext.define('bsmes.store.ShiftRecordStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.ShiftRecord',
	autoLoad: false,
    //sorters:[{"property:"createTime","direction":"DESC"}],
	proxy : {
		type:'rest',
		url : 'shiftRecord',
		extraParams: {
			'equipCode': Ext.fly('equipInfo').getAttribute('code')
		}
	}
});