Ext.define('bsmes.controller.ProductQCResultController', {
	extend : 'Oit.app.controller.GridController',
	view : 'productQCResultList',
	views : [ 'ProductQCResultList'],
	//stores : [ 'ProductQCResultStore' ],
	exportUrl:'productQCResult/export/QC报告'
});