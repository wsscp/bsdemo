Ext.define("bsmes.controller.EventTraceController",{
	extend : 'Oit.app.controller.GridController',
	view : 'eventTraceList',
	views : [ 'EventTraceList'],
	stores : [ 'EventTraceStore'],
	exportUrl : 'eventTrace/export/事件追溯',
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
})