Ext.define("bsmes.controller.RawMaterialInvTransController",{
	extend : 'Oit.app.controller.GridController',
	view : 'rawMaterialInvTransList',
	views : [ 'RawMaterialInvTransList'],
	stores : [ 'RawMaterialInvTransStore','RawMaterialInvTransSubStore'],
	exportUrl : 'rawMaterialInvTrans/export/原材库存事物',
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