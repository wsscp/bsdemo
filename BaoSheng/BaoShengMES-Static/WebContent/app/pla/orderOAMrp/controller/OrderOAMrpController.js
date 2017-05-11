Ext.define('bsmes.controller.OrderOAMrpController', {
			extend : 'Oit.app.controller.GridController',
			view : 'orderOAMrpList',
			views : ['OrderOAMrpList'],
			stores : ['OrderOAMrpStore'],
			exportUrl : 'orderOAMrp/export/物料需求计划'
		});