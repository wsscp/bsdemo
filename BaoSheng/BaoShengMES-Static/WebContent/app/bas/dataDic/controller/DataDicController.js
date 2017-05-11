Ext.define('bsmes.controller.DataDicController', {
	extend : 'Oit.app.controller.GridController',
	view : 'dataDicList',
	addview  : 'dataDicAdd',
	editview  : 'dataDicEdit',
	views : [ 'DataDicList','DataDicAdd','DataDicEdit'],
	stores : [ 'DataDicStore' ],
	doEdit: function(data) {
		var me = this;
		if (me.newFormToEdit) {
			me.getEditView().show();
			if(data.data.status=='false'){
				Ext.getCmp("status-2001").setValue(true);
			}else{
				Ext.getCmp("status-2000").setValue(true);
			}
			me.getEditForm().loadRecord(data);
		} else {
			me.getGrid().editingPlugin.startEdit(data, 0);
		}
	},
	onFormAdd: function(btn) {
		var me = this;
		var exist = true;
		var form = me.getAddForm(); 
		var termsCode = form.getValues().termsCode;
		var code = form.getValues().code;
		form.updateRecord();
		Ext.Ajax.request({
			async: false,
			url:'dataDic/checkDicCodeUnique/'+encodeURI(encodeURI(termsCode))+'/'+code+'/',
			method:'GET',
			success:function(response){
				if(response.responseText=="true"){
					exist = false;
					Ext.Msg.alert("错误信息","该数据重复，请重新输入");
				}
			}
		});
		if (form.isValid()&&exist) {
			var store = me.getGrid().getStore();
			// 同步到服务器
			store.insert(0, form.getRecord());
			store.sync();
			// 关闭窗口
			me.getAddView().close();
        }
	}
	
});