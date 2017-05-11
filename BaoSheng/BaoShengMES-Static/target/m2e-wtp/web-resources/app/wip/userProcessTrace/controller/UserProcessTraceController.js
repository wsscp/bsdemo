Ext.define('bsmes.controller.UserProcessTraceController', {
	extend : 'Oit.app.controller.GridController',
	view : 'userProcessTraceList',
	views : [ 'UserProcessTraceList'],
	stores : [ 'UserProcessTraceStore'],
	exportUrl : 'userProcessTrace/export/人员作业追溯',
	init: function() {
		var me = this;
		me.control(me.view + ' button[itemId=exportToXls]', {
            click: me.onExport
        });
		me.control(me.view + ' button[itemId=search]', {
            click: me.onSearch
        });
	},
    onSearch:function(){
        var me = this;
        var store = me.getGrid().getStore();
        var form = me.getSearchForm();
        var findParams = form.getValues();
        store.loadPage(1, {
            params: findParams
        });
    }
});