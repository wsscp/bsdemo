Ext.define('bsmes.controller.ProcessTraceReportController',{
	extend : 'Ext.app.Controller',
	view : 'processTraceReportList',
	views : [ 'ProcessTraceReportList','ProcessReoprtGrid'],
	stores : [ 'ProcessTraceReportStore'],
	constructor: function () {
        var me = this;
        // 初始化refs
        me.refs = me.refs || [];
        me.refs.push({
            ref: 'searchForm',
            selector: me.view + " form"
        });
        me.refs.push({
        	ref: 'processTraceReportGrid',
        	selector: "#processTraceReportGrid"
        });
        me.callParent(arguments);
	},
	init: function() {
		var me = this;
		if (!me.view) {
			Ext.Error.raise("A view configuration must be specified!");
		};
		// 初始化工具栏
		me.control(me.view + ' button[itemId=search]', {
			click: me.onSearch
		});

	},
	onLaunch : function() {
		var me = this;
		// 绑定searchForm record
		if (me.getSearchForm()) {
			var record = Ext.create("bsmes.model.ProcessTraceReport");
			me.getSearchForm().loadRecord(record);
		}
		//
		if(me.getProcessTraceReportGrid()){
			me.getProcessTraceReportGrid().setController(me);
		}		
	},
	onSearch: function() {
		var me = this;
		var form = me.getSearchForm();
		form.updateRecord();
		var processTraceReportStore = Ext.getCmp('processReoprtGridId').getStore();
		var data=form.getRecord().getData();
		processTraceReportStore.on('beforeload',function(store, options){
			var params={contractNo:data.contractNo,productCode:data.productCode,processCode:data.processCode,equipCode:data.equipCode};
			Ext.apply(store.proxy.extraParams,params);
		});
		processTraceReportStore.loadPage(1, {
		    params: form.getRecord().getData()
		});
	}
});