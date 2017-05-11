Ext.define('bsmes.controller.EquipProcessTraceController', {
	extend : 'Oit.app.controller.GridController',
	view : 'equipProcessTraceList',
	views : [ 'EquipProcessTraceList'],
	stores : [ 'EquipProcessTraceStore'],
	exportUrl : 'equipProcessTrace/export/设备加工追溯',
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