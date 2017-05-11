Ext.define('bsmes.controller.ProductSOPController', {
	extend : 'Oit.app.controller.GridController',
	view : 'productSOPList',
	views : [ 'ProductSOPList'],
	stores : [ 'ProductSOPStore' ],
    exportUrl:'productSOP/export/产品SOP计算结果'
});