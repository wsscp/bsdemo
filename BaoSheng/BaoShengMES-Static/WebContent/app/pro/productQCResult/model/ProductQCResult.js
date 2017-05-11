Ext.define('bsmes.model.ProductQCResult',{
	extend:'Ext.data.Model',
	fields : [	'id',
	          	'conclusion',
	          	'sampleBarcode',
	          	'productCode',
	          	{name : 'createTime',type : 'date'}
	          ]
});