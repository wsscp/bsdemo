Ext.define('bsmes.controller.StoreManageController', {
	extend : 'Oit.app.controller.GridController',
	view : 'storeManageView',
	importScrapWindow : 'importScrapWindow',
	views : [ 'StoreManageView','ImportScrapWindow','StoreManageAdd'],
	stores : [ 'StoreManageStore'],
	constructor : function(){
		var me = this;
		
		// 初始化refs
		me.refs = me.refs || [];
		
		me.refs.push({
			ref : 'storeManageView',
			selector : '#storeManageView'
		});
		me.refs.push({
			ref: 'importScrapWindow', 
			selector: me.importScrapWindow, 
			autoCreate: true, 
			xtype: me.importScrapWindow
		});
		me.callParent(arguments);
	},
	init : function(){
		var me = this;
		
		me.control(me.view + ' button[itemId=importScrapWin]',{
			click : me.openImportScrapWin
		});
		me.control(me.importScrapWindow + ' button[itemId=ok]',{
			click : me.importScrapSub
		});
//		me.control(me.view + ' button[itemId=add]', {
//            click: me.onAdd
//        });
		me.callParent(arguments);
    },
    openImportScrapWin : function(){
    	var me = this;
    	var win = me.getImportScrapWindow();
    	win.show();
    },
    importScrapSub : function(){
    	var me = this;
    	var grid = me.getStoreManageView();
    	var win = me.getImportScrapWindow();
    	var form = win.down('form');
    	if (form.isValid()) {
    		form.submit({
    			waitMsg : '正在导入废料明细,请耐心等待...',
    			success : function(form, action) {
    				var result = Ext.decode(action.response.responseText);
    				console.log(result)
					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
					grid.getStore().reload();
					win.close();
    			},
    			failure : function(form, action) {
    				var result = Ext.decode(action.response.responseText);
					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
    			}
    		});
    	}
    }
});