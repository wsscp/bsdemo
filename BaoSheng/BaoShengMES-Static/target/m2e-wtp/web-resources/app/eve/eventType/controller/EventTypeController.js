Ext.define('bsmes.controller.EventTypeController', {
	extend : 'Oit.app.controller.GridController',
	view : 'eventTypelist',
	views : [ 'EventTypeList','EventTypeAdd','EventTypeEdit', 'EventTypeDescWindow', 'EventTypeDescList', 'EventTypeDescAdd', 'EventTypeDescEdit'],
	stores : [ 'EventTypeStore', 'EventTypeDescStore' ],
	editview : 'eventTypeEdit',
	addview : 'eventTypeAdd',
	eventTypeDescAdd : 'eventTypeDescAdd',
	eventTypeDescEdit : 'eventTypeDescEdit',
	eventTypeDescList: 'eventTypeDescList',
	eventTypeDescWindow: 'eventTypeDescWindow',
	
	constructor: function () {
        var me = this;
        // 初始化refsW
        me.refs = me.refs || [];
        
        // 注册事件类型明细window
        me.refs.push({
			ref : 'eventTypeDescWindow',
			selector : me.eventTypeDescWindow,
			autoCreate : true,
			xtype : me.eventTypeDescWindow
		});
        // 注册事件类型明细grid
        me.refs.push({
        	ref: 'eventTypeDescList', 
			selector: me.eventTypeDescWindow + ' grid'
		});
        
        // 注册事件类型明细新增弹出框
        me.refs.push({
			ref : 'eventTypeDescAdd',
			selector : me.eventTypeDescAdd,
			autoCreate : true,
			xtype : me.eventTypeDescAdd
		});
        me.refs.push({
			ref: 'eventTypeDescAddForm', 
			selector: me.eventTypeDescAdd + ' form'
		});
        
        // 注册事件类型明细修改弹出框
        me.refs.push({
			ref : 'eventTypeDescEdit',
			selector : me.eventTypeDescEdit,
			autoCreate : true,
			xtype : me.eventTypeDescEdit
		});
        me.refs.push({
			ref: 'eventTypeDescEditForm', 
			selector: me.eventTypeDescEdit + ' form'
		});
        
        me.callParent(arguments);
    },
    
    init: function () {
        var me = this;

        // 初始化 添加事件类型明细新增按钮
        me.control(me.view + ' button[itemId=eventTypeDesc]', {
            click: me.openEventTypeDesc
        });
        
        // 初始化 添加事件类型明细新增按钮
        me.control(me.eventTypeDescList + ' button[itemId=addEventTypeDesc]', {
            click: me.addEventTypeDesc
        });
        
        // 初始化 添加事件类型明细修改按钮
        me.control(me.eventTypeDescList + ' button[itemId=editEventTypeDesc]', {
            click: me.editEventTypeDesc
        });
        
        // 初始化 添加事件类型明细新增修改提交按钮
		me.control(me.eventTypeDescAdd + ' button[itemId=ok]', {
			click: me.onEventTypeDescFormAdd
		})
		me.control(me.eventTypeDescEdit + ' button[itemId=ok]', {
			click: me.onEventTypeDescFormEdit
		})
        
        me.callParent(arguments);
    },
    
    // 添加事件类型明细列表弹出
    openEventTypeDesc: function() {
    	var me = this;
		var selection = me.getGrid().getSelectionModel()
				.getSelection();
		if (selection.length == 0) {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		} else {
			var record = selection[0];
			var extatt = record.getData().code;
			var win = me.getEventTypeDescWindow().show();
			var grid = me.getEventTypeDescList();
			grid.down('form').form.findField('extatt').setValue(extatt);
			grid.getStore().load();
		}
	},
    
    // 添加事件类型明细新增按钮
    addEventTypeDesc: function() {
    	var me = this;
    	// 获取事件类型
    	var eventTypeCode = me.getEventTypeDescList().down('form').form.findField('extatt').getValue();
    	
    	var record = Ext.create(me.getEventTypeDescList().getStore().model);
    	record.set('extatt', eventTypeCode);
    	record.set('code', Ext.data.IdGenerator.get('uuid').generate());
		me.getEventTypeDescAdd().show();
		me.getEventTypeDescAddForm().loadRecord(record);
	},
	
	// 添加事件类型明细新增按钮
	editEventTypeDesc: function() {
		var me = this;
		var selection = me.getEventTypeDescList().getSelectionModel()
				.getSelection();
		if (selection.length == 0) {
			Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
			return;
		} else {
			var record = selection[0];
			me.getEventTypeDescEdit().show()
			me.getEventTypeDescEditForm().loadRecord(record);
		}
	},
	
	// 事件类型明细新增和修改
	onEventTypeDescFormAdd: function(){
		var me = this;
		var form = me.getEventTypeDescAddForm(); 
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getEventTypeDescList().getStore();
			
			// 同步到服务器
			store.insert(0, form.getRecord());
			store.sync();
			// 关闭窗口
			me.getEventTypeDescAdd().close();
        }
        
	},
	onEventTypeDescFormEdit: function(){
		var me = this;
		var form = me.getEventTypeDescEditForm(); 
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getEventTypeDescList().getStore();
			// 同步到服务器
			store.sync();
			// 关闭窗口
			me.getEventTypeDescEdit().close();
        }
	}
	
});