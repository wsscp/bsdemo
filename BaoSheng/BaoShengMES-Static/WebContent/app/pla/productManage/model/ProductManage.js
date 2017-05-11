Ext.define('bsmes.model.ProductManage', {
	extend : 'Ext.data.Model',
	fields : ['contractNo',
	          'operator',
	          'customerCompany',
	          'custProductType',
	          'custProductSpec',
	          'productSpec',
	          'productType',
	          'salesOrderItemId',
	          'contractLength',
	          {name : 'oaDate',type : 'date'},
	          'remarks',
	          'createUserCode',
	          'status','processRequire','orderFileNum','id']
});