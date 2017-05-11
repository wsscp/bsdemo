
Ext.define('bsmes.model.MaterialMng', {
	extend : 'Ext.data.Model',
	fields : ['id', 'workOrderId',{
		name : 'workOrderNo',
		type : 'string'
	},{
		name : 'equipName',
		type : 'string'
	},{
		name : 'userName',
		type : 'string'
	},{
		name : 'quantity',
		type : 'string'
	},{
		name : 'matCode',
		type : 'string'
	}, {
		name : 'matName',
		type : 'string'
	}, {
		name : 'yaoLiaoQuantity',
		type : 'string'
	},{
		name : 'faLiaoQuantity',
		type : 'string'
	}, {
		name : 'unit',
		type : 'string'
	},{
		name : 'createTime',
		type : 'date'
	} ,{
		name : 'status',
		type : 'string'
	}, {
		name : 'typeSpec',
		type : 'string'
	},{
		name : 'planAmount',
		type : 'string'
	},{
		name : 'jiTaiQuantity',
		type : 'string'
	},{
		name : 'color',
		type : 'string'
	},{
		name : 'inAttrDesc',
		type : 'string'
	},{
		name : 'buLiaoQuantity',
		type : 'string'
	},{
		name : 'faYaoLiaoQuantity',
		type : 'string'
	},{
		name : 'faBuLiaoQuantity',
		type : 'string'
	}]
});
