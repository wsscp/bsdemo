Ext.define('bsmes.model.ImportProduct', {
	extend : 'Ext.data.Model',
	fields : [ 'id', 'seriesName', 'productStatus', 'mpartStatus',
			'insertMpartStatus', 'processStatus', 'scxStatus','createUserCode',
			'modifyUserCode',{
				name : 'createTime',
				type : 'date'
			}, {
				name : 'modifyTime',
				type : 'date'
			}, 'location'
			]
});