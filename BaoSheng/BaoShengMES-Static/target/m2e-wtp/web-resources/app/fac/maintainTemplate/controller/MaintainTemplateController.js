Ext.define('bsmes.controller.MaintainTemplateController',{
	extend : 'Oit.app.controller.GridController',
	view : 'maintainTemplateList',
	editview : 'maintainTemplateEdit',
	addview : 'maintainTemplateAdd',
	views : ['MaintainTemplateList', 'MaintainTemplateEdit' ,'MaintainTemplateAdd'],
	stores : ['MaintainTemplateStore'],
	/**
	 * @private 重写新增弹出方法
	 */ 
	doAdd: function(){
		var me = this;
		//var record = Ext.create(me.getGrid().getStore().model);
		// 设置个初始值
		//record.getData().triggerCycle = 1;
		//record.getData().triggerCycleH = 500;
		//console.log();
		
		
		me.getAddView().show();
		//me.getAddForm().loadRecord(record);
	},
	/**
	 * @private 重写修改弹出方法
	 */ 
    doEdit: function(data) {
        var me = this;
        me.callParent(arguments);
        var type = me.getEditForm().getRecord().get('type');
        if (type == 'DAILY') {
            var c = Ext.ComponentQuery.query('maintainTemplateEdit #triggerCycle')[0];
            c.setDisabled(true);
            c.hide();
            var t = Ext.ComponentQuery.query('maintainTemplateEdit #triggerType')[0];
            t.setDisabled(true);
            t.hide();
        }
    },
    /**
	 * @private 重写新增方法
	 */ 
	onFormAdd: function(btn) {
		var me = this;
		var form = me.getAddForm(); 
		form.updateRecord();
		if (form.isValid()) {
			Ext.Ajax.request({
	            url: 'maintainTemplate/save',
	            method: 'POST',
	            params: form.getValues(),
	            success: function (response) {
	            	me.getAddView().close();
	            	me.getGrid().getStore().load();
	            }
	        });
        }
	},

});