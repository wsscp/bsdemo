Ext.define('bsmes.controller.AmoebaController', {
	extend : 'Oit.app.controller.GridController',
	view : 'amoebaList',
	addview  : 'amoebaAdd',
	editview  : 'amoebaEdit',
	amoebaImportWindow:'amoebaImportWindow',
	views : [ 'AmoebaList','AmoebaAdd','AmoebaEdit','AmoebaImportWindow'],
	stores : [ 'AmoebaStore' ],
	constructor : function(){
		var me = this;
		// 初始化refs
		me.refs = me.refs || [];

		me.refs.push({
			ref : 'amoebaImportWindow',
			selector : 'amoebaImportWindow',
			autoCreate : true,
			xtype : 'amoebaImportWindow'
		});
		me.refs.push({
			ref : 'AmoebaList',
			selector : 'AmoebaList',
			autoCreate : true,
			xtype : 'AmoebaList'
		});
		me.callParent(arguments);
	},
	init : function(){
		var me = this;
		if (!me.view) {
			Ext.Error.raise("A view configuration must be specified!");
		}
		me.control(me.view + ' button[itemId=importData]',{
			click : me.showImportAmoeba
		});
		me.control(me.view + ' button[itemId=search]', {
			click : me.onSearch
		});
		me.control(me.view + ' button[itemId=add]',{
			click : me.doAdd
		});
		me.control(me.view + ' button[itemId=remove]', {
			click : function(){me.onRemove()}
		});
		me.control(me.editview + ' button[itemId=ok]', {
			click : me.onFormSave
		})
		me.control(me.amoebaImportWindow + ' button[itemId=ok]', {
			click : me.importAmoebaData
		});
	},
	showImportAmoeba:function(){
    	var me=this;
    	var win=me.getAmoebaImportWindow();
    	win.show();
	},
    /**
	 * 设备维护计划导入
	 */
    importAmoebaData : function() {
		var me = this;
		var win = me.getAmoebaImportWindow();
		var form = win.down('form');
		if (form.isValid()) {
			form.submit({
				waitMsg : '正在导入阿米巴基础数据,请耐心等待...',
				success : function(form, action) {
					var result = Ext.decode(action.response.responseText);
					Ext.Msg.alert(Oit.msg.PROMPT, result.message);
					me.getAmoebaList().getStore().load();
					win.close();
				},
				failure : function(form, action) {
					var result = Ext.decode(action.response.responseText);
					Ext.Msg.alert(Oit.msg.WARN, result.message);
					win.close();
				}
			});
		}

	},

	doAdd: function() {
		var me = this;
		me.getEditView().show();
	},
	
	doEdit: function(data) {
		var me = this;
		me.getEditView().show();
		me.getEditForm().loadRecord(data);
	},
	onFormAdd: function(btn) {
		var me = this;
		var exist = true;
		var form = me.getAddForm(); 
		var termsCode = form.getValues().termsCode;
		var code = form.getValues().code;
		form.updateRecord();
//		Ext.Ajax.request({
//			async: false,
//			url:'amoeba/checkDicCodeUnique/'+encodeURI(encodeURI(termsCode))+'/'+code+'/',
//			method:'GET',
//			success:function(response){
//				if(response.responseText=="true"){
//					exist = false;
//					Ext.Msg.alert("错误信息","该数据重复，请重新输入");
//				}
//			}
//		});
		if (form.isValid()&&exist) {
			var store = me.getGrid().getStore();
			// 同步到服务器
			store.insert(0, form.getRecord());
			store.sync();
			// 关闭窗口
			me.getAddView().close();
        }
	},
	onFormSave : function(btn) {
		var me = this;
		var form = me.getEditForm();
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getGrid().getStore();
			// 同步到服务器
			store.sync();
			// 关闭窗口
			me.getEditView().close();
		}
	}
	
});