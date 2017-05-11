var workShiftItems = [];
var workShifts = Ext.decode(Ext.fly("workShift").getAttribute('workShifts'));
for(var i=0;i<workShifts.length;i++){
	var boxLabel = workShifts[i].shiftName;    
	var index = workShifts[i].id;
    workShiftItems.push({  
                boxLabel : boxLabel,    
                name : 'rb',
                inputValue: index
            	});
}

Ext.define('bsmes.view.EqipCalendarShiftAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.eqipCalendarShiftadd',
	title: Oit.msg.bas.eqipCalendarShift.addForm.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.bas.eqipCalendarShift.equipCode,
        name: 'equipCode',
        xtype:'combobox',
        queryMode: 'local',
        allowBlank:false,
        displayField:   'code',
        store:new Ext.data.Store({
        	fields:['code'],
        	autoLoad:true,
        	proxy:{
        		type: 'rest',
        		url:'eqipCalendarShift/getEquipCode'
        	}
        }),
        listeners: {
        	'select' : function(value){
        		var eqipCode = value.lastValue;
        		Ext.Ajax.request({
        			async: false,
        			url:'eqipCalendarShift/getEqipName/'+eqipCode+'/',
        			method:'GET',
        			success:function(response){
        				var data = Ext.decode(response.responseText);
        				Ext.getCmp('name').setValue(data);
        			}
        		});
        	}
        }
    },{
        fieldLabel: Oit.msg.bas.eqipCalendarShift.name,
        name: 'name',
        xtype:'displayfield',
        id : 'name'
    },{
        fieldLabel: Oit.msg.bas.eqipCalendarShift.dateOfWork,
        name: 'dateOfWork',
        xtype: 'datefield',
        format:'Y-m-d'
    },{
        fieldLabel: Oit.msg.bas.eqipCalendarShift.workShiftId,
        xtype: 'checkboxgroup',
        columns: 1,
        id:'itemsId',
        items: workShiftItems
    }]
});
