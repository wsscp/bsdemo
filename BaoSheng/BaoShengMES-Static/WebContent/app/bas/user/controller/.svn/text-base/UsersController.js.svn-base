Ext.define('bsmes.controller.UsersController', {
	extend : 'Oit.app.controller.GridController',
	view : 'userlist',
	editview : 'useredit',
	importUserInfoWindow : 'importUserInfoWindow',
	addview  : 'useradd',
	addEmployeeResume : 'addEmployeeResume',
	views : [ 'UserList', 'UserEdit' ,'UserAdd' ,'ImportUserInfoWindow','AddEmployeeResume'],
	stores : [ 'UserStore'],
	constructor : function() {
		var me = this;

		// 初始化refs
		me.refs = me.refs || [];
		me.refs.push({
			ref : 'importUserInfoWindow',
			selector : me.importUserInfoWindow,
			autoCreate : true,
			xtype : me.importUserInfoWindow
		});
		me.refs.push({
			ref : 'addEmployeeResume',
			selector : me.addEmployeeResume,
			autoCreate : true,
			xtype : me.addEmployeeResume
		});
		me.callParent(arguments);
	},
	init : function(){
		var me = this;
        me.control(me.view + ' button[itemId=add]', {
            click: me.onAdd
        });
        me.control(me.view + ' button[itemId=remove]', {
            click: me.onRemove
        });
        // 初始化编辑表单按钮
        me.control(me.addview + ' button[itemId=ok]', {
            click: me.onFormAdd
        })
        me.control(me.editview + ' button[itemId=ok]', {
            click: me.onFormSave
        });
        // 初始化工具栏
        me.control(me.view + ' button[itemId=search]', {
            click: me.onSearch
        });
        
        me.control(me.view + ' button[itemId=ImportUserInfo]', {
			click : me.importUserInfo
		});
        
        me.control(me.importUserInfoWindow + ' button[itemId=ok]', {
			click : me.importUserInfoSub
		});
        
        me.control(me.addEmployeeResume + ' button[itemId=ok]', {
			click : me.addEmployeeResumeFun
		});
    },
    importUserInfo : function() {
		var me = this;
		me.getImportUserInfoWindow().show();
	},
	importUserInfoSub : function(){
		var me = this;
		var win = me.getImportUserInfoWindow();
		var form = win.down('form');
		if (form.isValid()) {
			form.submit({
					waitMsg : '正在上传简历,请耐心等待...',
					success : function(form, action) {
						var result = Ext.decode(action.response.responseText);
						Ext.Msg.alert(Oit.msg.PROMPT, result.message);
						win.close();
					},
					failure : function(form, action) {
						var result = Ext.decode(action.response.responseText);
						Ext.Msg.alert(Oit.msg.WARN, result.message);
					}
			});
		}
	},
    onFormAdd: function(btn) {
        var me = this;
        var form = me.getAddForm();
        form.updateRecord();
        if (form.isValid()) {
            var record = form.getRecord();
            if(!Ext.isEmpty(record.get('password'))){
                record.set('password', $.md5(record.get('password')));
            }
            var store = me.getGrid().getStore();
            // 同步到服务器
            store.insert(0, record);
            store.sync();
            // 关闭窗口
            me.getAddView().close();
        }
    },
    /**
     * @private
     */
    onFormSave: function(btn) {
        var me = this;
        var form = me.getEditForm();
        form.updateRecord();
        if (form.isValid()) {
            var record = form.getRecord();
            if(!Ext.isEmpty(record.get('password'))){
                record.set('password', $.md5(record.get('password')));
            }
            var store = me.getGrid().getStore();
            // 同步到服务器
            store.sync();
            // 关闭窗口
            me.getEditView().close();
        }
    },
    doEdit: function(record) {
        var me = this;
        me.getEditView().show();
        record.set('password','');
        me.getEditForm().loadRecord(record);
    },
    addEmployeeResumeFun : function(){
    	var me = this;
    	var win = me.getAddEmployeeResume();
    	var form = win.down('form');
    	form.submit({
    		url : 'user/addEmployeeResume',
    		success : function(form, action) {
				var result = Ext.decode(action.response.responseText);
				Ext.Msg.alert(Oit.msg.PROMPT, result.message);
				win.close();
				var grid = Ext.ComponentQuery.query('checkResume grid')[0];
				grid.getStore().reload()
			},
			failure : function(form, action) {
				var result = Ext.decode(action.response.responseText);
				Ext.Msg.alert(Oit.msg.WARN, result.message);
			}
    	})
    }
});