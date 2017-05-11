Ext.define("bsmes.controller.QualityTraceController",{
	extend : 'Oit.app.controller.GridController',
	view : 'qualityTraceList',
	views : [ 'QualityTraceList'],
	stores : [ 'QualityTraceStore'],
	exportUrl : 'qualityTrace/export/质量追溯',
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