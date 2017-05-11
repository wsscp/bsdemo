//Employee
Ext.define('bsmes.model.WorkOrderProduct', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'productCode',
	    'productName',
	    'productType',
	    'productSpec',
	    'usedStock',
	    'craftsCode',
		'orgCode'
	 ]
});