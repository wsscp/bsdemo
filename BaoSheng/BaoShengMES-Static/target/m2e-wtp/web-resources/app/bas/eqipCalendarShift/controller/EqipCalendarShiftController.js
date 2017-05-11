Ext.define('bsmes.controller.EqipCalendarShiftController', {
	extend : 'Oit.app.controller.GridController',
	view : 'eqipCalendarShiftView',
	editview : 'eqipCalendarShiftedit',
	addview  : 'eqipCalendarShiftadd',
	views : [ 'EqipCalendarShiftList','EqipCalendarShiftAdd','EqipCalendarShiftEdit'],
	stores : [ 'EqipCalendarShiftStore' ],
	onFormAdd: function(btn) {
		var me = this;
		var form = me.getAddForm();
		var equipCode = form.getValues().equipCode;
		var dateOfWork = form.getValues().dateOfWork;
		var workShiftId = form.getValues().rb;
		if(workShiftId==null || workShiftId=='undefined' || workShiftId==''){
			Ext.Msg.alert('提示','请输入班次信息!');
			return ;
		}
		var result = true;
		form.updateRecord();
		var exist = true;
		Ext.Ajax.request({
			async: false,
			url:'eqipCalendarShift/checkExist/'+equipCode+'/',
			method:'GET',
			success:function(response){
				if(response.responseText=="true"){
					exist = false;
					result = false;
					Ext.Msg.alert("错误信息","该设备不存在,请重新输入.");
				}
			}
		});
		
		if(exist){
			var workShiftValid = true;
			Ext.Ajax.request({
				async: false,
				url:'eqipCalendarShift/checkUnique/'+equipCode+'/'+dateOfWork+'/',
				method:'GET',
				success:function(response){
					if(response.responseText=="true"){
						workShiftValid = false;
						result = false;
						Ext.Msg.alert("错误信息","录入的数据已存在!");
					}
				}
			});
			if(workShiftValid){
				Ext.Ajax.request({
					async: false,
					url:'eqipCalendarShift/workShiftValid/'+workShiftId,
					method:'GET',
					success:function(response){
						if(response.responseText=="true"){
							workShiftValid = false;
							result = false;
							Ext.Msg.alert("错误信息","班次选择有冲突,请重新选择.");
						}
					}
				});
			}
		}
		
		if (form.isValid()&&result) {
			Ext.Ajax.request({
				async: false,
				url:'eqipCalendarShift/saveForm/'+equipCode+'/'+workShiftId.toString()+'/'+dateOfWork,
				method:'GET',
				success:function(response){
					me.onSearch();
				}
			});
			
			// 关闭窗口
			me.getAddView().close();
        }
	},
	doEdit: function(data) {
		var me = this;
		if (me.newFormToEdit) {
			me.getEditView().show();
			var array = new Array();
			array = data.data.workShiftId.split(',');
			data.data.rb = [];
			var workShifts = Ext.decode(Ext.fly("workShift").getAttribute('workShifts'));
			for(var i=0;i<workShifts.length;i++){
				for(var j=0;j<array.length;j++){
					if(workShifts[i].shiftName.substr(0,4)==array[j]){
						data.data.rb.push(workShifts[i].id);
					}
				}
			}
			me.getEditForm().loadRecord(data);
		} else {
			me.getGrid().editingPlugin.startEdit(data, 0);
		}
	},
	onFormSave: function(btn) {
		var me = this;
		var form = me.getEditForm(); 
		var equipCode = form.getValues().equipCode;
		var dateOfWork = form.getValues().dateOfWork;
		var workShiftId = form.getValues().rb;
		var result = true;
		if(workShiftId==null || workShiftId=='undefined' || workShiftId==''){
			Ext.Msg.alert('提示','请输入班次信息!');
			return ;
		}
		form.updateRecord();
		
		var exist = true;
		Ext.Ajax.request({
			async: false,
			url:'eqipCalendarShift/checkExist/'+equipCode+'/',
			method:'GET',
			success:function(response){
				if(response.responseText=="true"){
					exist = false;
					result = false;
					Ext.Msg.alert("错误信息","该设备不存在,请重新输入.");
				}
			}
		});
		
		if(exist){
			Ext.Ajax.request({
				async: false,
				url:'eqipCalendarShift/workShiftValid/'+workShiftId,
				method:'GET',
				success:function(response){
					if(response.responseText=="true"){
						workShiftValid = false;
						result = false;
						Ext.Msg.alert("错误信息","班次选择有冲突,请重新选择.");
					}
				}
			});
		}
		
		if (form.isValid()&&result) {
			var object = form.getValues();
			var equipCode = form.getValues().equipCode;
			var dateOfWork = form.getValues().dateOfWork;
			var workShiftId = form.getValues().rb;
			var id = form.getValues().id;
			if(workShiftId==null || workShiftId=='undefined' || workShiftId==''){
				Ext.Msg.alert('提示','请输入班次信息!');
				return ;
			}
			Ext.Ajax.request({
				async: false,
				url:'eqipCalendarShift/updateForm/'+equipCode+'/'+workShiftId.toString()+'/'+dateOfWork+'/'+id,
				method:'GET',
				success:function(response){
					me.onSearch();
				}
			});
			// 关闭窗口
			me.getEditView().close();
		}
	},
	doRemove: function(data) {
		var me = this;
		var store = me.getGrid().getStore();
		var eqipCalendarId = data[0].data.id;
		store.remove(data);
		Ext.Ajax.request({
			async: false,
			url:'eqipCalendarShift/deleteRow/'+eqipCalendarId,
			method:'GET',
			success:function(response){
				me.onSearch();
			}
		});
	}
});