Ext.define('bsmes.model.OrderOperateModel', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'workOrderNo',
	    'equipCode',
	    'operateReason',
	    'operateType',
	    'orgCode',
	    'oldFlag',
	    'createUserCode',
	    'modifyUserCode',
	    { name : 'createTime', type : 'date' },
	    { name : 'modifyTime', type : 'date' },
	    'outProductColor',
	    'productType',
	    'productSpec'
	 ]
});