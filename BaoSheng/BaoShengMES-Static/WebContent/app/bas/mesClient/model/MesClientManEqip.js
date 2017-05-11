Ext.define('bsmes.model.MesClientManEqip', {
	extend : 'Ext.data.Model',
	fields : [
		'id',
		'mesClientId',
		'eqipId',
		'equipName',
		'mesClientName',
		{
		name : 'createTime',
		type : 'date'
		}]
});
