Ext.define('bsmes.controller.WeekCalendarShiftController', {
	extend : 'Oit.app.controller.GridController',
	view : 'weekCalendarShiftlist',
	editview : 'weekCalendarShiftedit',
	addview  : 'weekCalendarShiftadd',
	views : [ 'WeekCalendarShiftList','WeekCalendarShiftAdd','WeekCalendarShiftEdit'],
	stores : [ 'WeekCalendarShiftStore' ],
	onFormAdd: function(btn) {
		var me = this;
		var form = me.getAddForm(); 
		var weekCalendarId = form.getValues().weekCalendarId;
		if(weekCalendarId=="周一"){
			weekCalendarId="1";
		}else if(weekCalendarId=="周二"){
			weekCalendarId="2";
		}else if(weekCalendarId=="周三"){
			weekCalendarId="3";
		}else if(weekCalendarId=="周四"){
			weekCalendarId="4";
		}else if(weekCalendarId=="周五"){
			weekCalendarId="5";
		}else if(weekCalendarId=="周六"){
			weekCalendarId="6";
		}else if(weekCalendarId=="周日"){
			weekCalendarId="7";
		}
		var workShiftId = form.getValues().workShiftId;
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getGrid().getStore();
			var valid = true;
			
			Ext.Ajax.request({
				async: false,
				url:'/bsmes/bas/weekCalendarShift/checkUnique/'+encodeURI(encodeURI(workShiftId))+"/"+weekCalendarId+'/',
				method:'GET',
				success:function(response){
					var data = Ext.decode(response.responseText);
					if(data.exists){
						valid = false;
						Ext.Msg.alert("错误信息","该工作日历和班次情况已经录入,请重新输入!");
					}
				}
			});
			var str = "";
//			if(form.getValues().shiftStartTime.m.toString().length==1){
//				str = "0"+form.getValues().shiftStartTime.m.toString();
//			}else{
//				str = form.getValues().shiftStartTime.m.toString();
//			}
//			var shiftStartTime = form.getValues().shiftStartTime.h+""+str;
			var shiftStartTime = form.getValues().shiftStartTime.replace(":","");
			var shiftEndTime=form.getValues().shiftEndTime.replace(":","");
			Ext.Ajax.request({
				async: false,
				url:'/bsmes/bas/weekCalendarShift/timeValid/'+shiftStartTime+'/'+shiftEndTime+'/'+encodeURI(encodeURI(form.getValues().workShiftId))+"/"+encodeURI(encodeURI(form.getValues().weekCalendarId))+'/',
				method:'GET',
				success:function(response){
					if(response.responseText=="true"){
						valid = false;
						Ext.Msg.alert("错误信息","时间有冲突,请核对!");
					}
				}
			});
			if(valid){
				store.insert(0, form.getRecord());
				store.sync();
				me.getAddView().close();
			}
        }
	},
	doAdd: function() {
		var me = this;
		var record = Ext.create(me.getGrid().getStore().model);
		me.getAddView().show();
		me.getAddForm().loadRecord(record);
	}
});