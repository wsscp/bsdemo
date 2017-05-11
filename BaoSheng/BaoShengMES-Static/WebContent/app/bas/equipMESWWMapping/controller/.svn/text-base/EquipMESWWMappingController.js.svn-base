Ext.define('bsmes.controller.EquipMESWWMappingController', {
	extend : 'Oit.app.controller.GridController',
	view : 'equipMESWWMappingList',
	addview  : 'equipMESWWMappingAdd',
	editview  : 'equipMESWWMappingEdit',
	views : [ 'EquipMESWWMappingList','EquipMESWWMappingAdd','EquipMESWWMappingEdit'],
	stores : [ 'EquipMESWWMappingStore','ParmCodeStore'],
	onFormAdd: function(btn) {
		var me = this;
		var form = me.getAddForm(); 
		var tagName = form.getValues().tagName;
		var equipCode = form.getValues().equipCode;
		var parmCode = form.getValues().parmCode;
		form.updateRecord();
		var result = true;
		var exits = true;
		Ext.Ajax.request({
			async: false,
			url:'equipMESWWMapping/checkExist/'+tagName+'/',
			method:'GET',
			success:function(response){
				if(response.responseText=="true"){
					exits = false;
					result = false;
					Ext.Msg.alert("错误信息","该标签名已存在,请重新输入.");
				}
			}
		});
		if(exits){
			Ext.Ajax.request({
				async: false,
				url:'equipMESWWMapping/checkExist/'+equipCode+'/'+parmCode+'/',
				method:'GET',
				success:function(response){
					if(response.responseText=="true"){
						result = false;
						Ext.Msg.alert("错误信息","录入数据重复.");
					}
				}
			});
		}
		
		
		if (form.isValid()&&result) {
			var store = me.getGrid().getStore();
			// 同步到服务器
			store.insert(0, form.getRecord());
			store.sync();
			// 关闭窗口
			me.getAddView().close();
        }
	}
});