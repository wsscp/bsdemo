Ext.define('bsmes.model.CraftsSL', {
	extend : 'Ext.data.Model',
	fields : [ 'id', 'craftsCode', 'craftsName', 'craftsVersion', 'productName',
	           {name : 'startDate', type : 'date'},
	           {name : 'endDate', type : 'date'}, 
	           'productCode', 'isDefault','isDefaultText' ]
});