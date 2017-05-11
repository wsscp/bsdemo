Ext.define('bsmes.view.WeekCalendarShiftAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.weekCalendarShiftadd',
	title: Oit.msg.bas.weekCalendarShift.addForm.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.bas.weekCalendarShift.weekCalendarId,
        name: 'weekCalendarId',
        xtype:'combobox',
        mode: 'local',
        allowBlank:false,
        displayField:'dayName',
        store:new Ext.data.Store({
        	fields:['dayName','dayName'],
        	data : [
        	        {"dayName":"周一", "dayName":"周一"},
        	        {"dayName":"周二", "dayName":"周二"},
        	        {"dayName":"周三", "dayName":"周三"},
        	        {"dayName":"周四", "dayName":"周四"},
        	        {"dayName":"周五", "dayName":"周五"},
        	        {"dayName":"周六", "dayName":"周六"},
        	        {"dayName":"周日", "dayName":"周日"}
        	    ]
        })
    },{
        fieldLabel: Oit.msg.bas.weekCalendarShift.workShiftId,
        name: 'workShiftId',
        xtype:'combobox',
        mode: 'remote',
        allowBlank:false,
        displayField:   'shiftName',
        store:new Ext.data.Store({
        	fields:['shiftName'],
        	autoLoad:true,
        	proxy:{
        		type: 'rest',
        		url:'weekCalendarShift/getWorkShiftName'
        	}
        }),
        listeners: {
        	'select' : function(value){
        		var shiftName = value.lastValue;
        		Ext.Ajax.request({
        			async: false,
        			url:'weekCalendarShift/getStartTimeAndEndTime/'+encodeURI(encodeURI(shiftName))+'/',
        			method:'GET',
        			success:function(response){
        				var data = Ext.decode(response.responseText);
        				var shiftStartTime = data.shiftStartTime;
        				var shiftEndTime = data.shiftEndTime;
        				shiftStartTime = shiftStartTime.substr(0,2)+":"+shiftStartTime.substr(2,2);
        				shiftEndTime = shiftEndTime.substr(0,2)+":"+shiftEndTime.substr(2,2);
        				Ext.getCmp('shiftStartTime').setValue(shiftStartTime);
        				Ext.getCmp('shiftEndTime').setValue(shiftEndTime);
        			}
        		});
        	}
        }
    },{
        fieldLabel: Oit.msg.bas.weekCalendarShift.shiftStartTime,
        name: 'shiftStartTime',
        readOnly: 'true',
        xtype: 'textfield',
        id:'shiftStartTime'
    },{
        fieldLabel: Oit.msg.bas.weekCalendarShift.shiftEndTime,
        name: 'shiftEndTime',
        readOnly: 'true',	
        xtype: 'textfield',
        id:'shiftEndTime'
    }]
});
