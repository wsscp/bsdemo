Ext.define('bsmes.view.MaintainTemplateAdd',{
	extend: 'Oit.app.view.form.EditForm',
	alias : 'widget.maintainTemplateAdd',
	title: Oit.msg.fac.maintainTemplate.addForm.title,
	formItems: [{
		fieldLabel : Oit.msg.fac.maintainTemplate.model + '<font color=red>*</font>',
		name : 'model',
        xtype: 'combo',
        editable:false,
        mode:'remote',
        displayField:'name',
        valueField : 'code',
        triggerAction :'all',
        blankText:'设备类型不能为空',
        store:new Ext.data.Store({
            fields:[{name:'code'},{name:'name'}],
            autoLoad:false,
            proxy:{
                type: 'rest',
                url:'equipInformation/equipModel'
            }
        })
    },{
    	fieldLabel : '设备类型',
    	name : 'eqipCategory',
        xtype:'combobox',
        queryMode: 'local',
        displayField:'eqipCategory',
        valueField: 'eqipCategory',
        store : new Ext.data.Store({
        	fields:['eqipCategory'],
        	autoLoad:true,
        	data : [{eqipCategory : 'A'},
        	        {eqipCategory : 'B'},
        	        {eqipCategory : 'C'}]
        })
    },{
		fieldLabel : Oit.msg.fac.maintainTemplate.type,
        xtype : 'fieldcontainer',
        layout: {
            type: 'table',
            columns: 2
        },
        defaultType: 'radiofield',
        items: [
          { boxLabel: '点检', name: 'type', width: 100, inputValue: 'DAILY', checked: true, 
        	  listeners : {
	              change : function(field, newValue) {
	                  Ext.ComponentQuery.query('maintainTemplateAdd #triggerType')[0].setDisabled(newValue);
	                  Ext.ComponentQuery.query('maintainTemplateAdd #triggerTypeH')[0].setDisabled(newValue);
	              }
        	  }
          }, 
          { boxLabel: '月检', name: 'type', inputValue: 'MONTHLY' }, 
          { boxLabel: '一级保养', name: 'type', inputValue: 'FIRST_CLASS' }, 
          { boxLabel: '二级保养', name: 'type', inputValue: 'SECOND_CLASS' }, 
          { boxLabel: '大修', name: 'type', inputValue: 'OVERHAUL' }
        ]
    }, {
        fieldLabel : Oit.msg.fac.maintainTemplate.triggerType,
        xtype : 'fieldcontainer',
        layout: 'hbox',
        itemId : 'triggerType',
        disabled : true,
        items: [{
        	xtype: 'checkboxgroup',
        	items: [ {boxLabel: '自然时间', name: 'triggerType', inputValue: 'NATURE', checked: true} ]
        },{
            fieldLabel : Oit.msg.fac.maintainTemplate.triggerCycle_m,
            name : 'triggerCycle',
            xtype: 'numberfield',
            margin: '0 0 0 56',
            width: 168,
            minValue: 0,
            value: 1
    	}]
    }, {
        xtype : 'fieldcontainer',
        layout: 'hbox',
        margin: '0 0 0 105',
        itemId : 'triggerTypeH',
        disabled : true,
        items: [{
        	xtype: 'checkboxgroup',
        	items: [ {boxLabel: '设备运行时间', name: 'triggerTypeH', inputValue: 'RUNTIME', checked: true} ]
        },{
            fieldLabel : Oit.msg.fac.maintainTemplate.triggerCycle_h,
            name : 'triggerCycleH',
            xtype: 'numberfield',
            margin: '0 0 0 30',
            width: 168,
            minValue: 0,
            value: 500
    	}]
    } ,{
		fieldLabel : Oit.msg.fac.maintainTemplate.describe,
		name : 'describe',
		xtype: 'textarea',
        width: 400
	}
 ]
});