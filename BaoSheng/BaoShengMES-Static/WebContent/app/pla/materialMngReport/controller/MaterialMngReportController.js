Ext.define('bsmes.controller.MaterialMngReportController', {
	extend : 'Oit.app.controller.GridController',
	view : 'materialMngReportList',
	views : [ 'MaterialMngReportList'],
	stores : [ 'MaterialMngReportStore'],
	init : function(){
		var me = this;
		me.refs.push({
			ref : 'materialMngReportList',
			selector : me.materialMngReportList,
			autoCreate : true,
			xtype : me.materialMngReportList
		});
        
    }
});