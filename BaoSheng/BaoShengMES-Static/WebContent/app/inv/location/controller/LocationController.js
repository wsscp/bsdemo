Ext.define('bsmes.controller.LocationController', {
	extend : 'Oit.app.controller.GridController',
	view : 'locationList',
	addview : 'locationadd',
	editview : 'locationedit',
	views : [ 'LocationList', 'LocationEdit', 'LocationAdd' ],
	stores : [ 'LocationStore' ],
	init : function() {
		var me = this;
		me.control(me.view + ' button[itemId=add]', {
			click : me.onAdd
		});
		me.control(me.view + ' button[itemId=remove]', {
			click : me.onRemove
		});
		// 初始化编辑表单按钮
		me.control(me.addview + ' button[itemId=ok]', {
			click : me.onFormAdd
		})
		me.control(me.editview + ' button[itemId=ok]', {
			click : me.onFormSave
		});
		// 初始化工具栏
		me.control(me.view + ' button[itemId=search]', {
			click : me.onSearch
		});
	},
	onFormAdd : function(btn) {
		var me = this;
		var form = me.getAddForm();
		form.updateRecord();
		var result=me.doCheckLocation(form.getRecord());
		if (result && form.isValid()) {
			var record = form.getRecord();
				Ext.Ajax.request({
					url : 'location/insert',
					method : "POST",
					params : {
						location : Ext.encode(record.data)
					},
					success : function(response) {
						me.getGrid().getStore().reload();
					},
					failure : function(response, options) {
						Ext.MessageBox.alert('失败', '错误编号：' + response.status);
					}
				});
				me.getAddView().close();
		}else{
			Ext.Msg.alert('提示','该工序库位重复');
		}
	},
	onFormSave : function(btn) {
		var me = this;
		var form = me.getEditForm();
		form.updateRecord();
		var result=me.doCheckLocation(form.getRecord());
		if (result && form.isValid()) {
			var record = form.getRecord();
			Ext.Ajax.request({
				url : 'location/update',
				method : "POST",
				params : {
					location : Ext.encode(record.data)
				},
				success : function(response) {
					me.getGrid().getStore().reload();
				},
				failure : function(response, options) {
					Ext.MessageBox.alert('失败', '错误编号：' + response.status);
				}
			});
			me.getEditView().close();
		}else{
			Ext.Msg.alert('提示','该工序库位重复');
		}
	},
	doCheckLocation:function(record){
		var result=true;
		Ext.Ajax.request({
			url : '/bsmes/inv/location/check',
			async: false,
			method:'GET',
			params : {
				processCode:record.data.processCode,
				locationX:record.data.locationX,
				locationY:record.data.locationY
			},
			success:function(response,opts){
				var data = Ext.decode(response.responseText);
				if(data=='1'){
					result=false;
				}
			}
		});
		return result;
	}
});