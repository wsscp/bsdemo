Ext.define("bsmes.controller.ProductStatusTraceController",{
	extend : 'Oit.app.controller.GridController',
	view : 'productStatusTraceList',
	views : [ 'ProductStatusTraceList'],
	stores : [ 'ProductStatusTraceStore'],
	exportUrl : 'productStatusTrace/export/生产状态追溯',
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