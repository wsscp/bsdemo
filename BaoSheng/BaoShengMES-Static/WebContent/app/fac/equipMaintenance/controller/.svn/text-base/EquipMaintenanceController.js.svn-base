Ext.define('bsmes.controller.EquipMaintenanceController', {
	extend : 'Oit.app.controller.GridController',
	view : 'equipMaintenanceList',
	views : [ 'EquipMaintenanceList'],
	stores : [ 'EquipMaintenanceStore' ],
	exportUrl : 'equipMaintenance/export/设备维修统计',
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