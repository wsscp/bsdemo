Ext.define('bsmes.model.ProductSOP',{
	extend : 'Ext.data.Model',
	fields : [{name : 'id',type : 'string'},
	          {name : 'productType',type : 'string'},
	          {name : 'productSpec',type : 'string'},
	          {name : 'productCode',type : 'string'},
	          {name : 'earliestFinishDate',type : 'date'}]
});