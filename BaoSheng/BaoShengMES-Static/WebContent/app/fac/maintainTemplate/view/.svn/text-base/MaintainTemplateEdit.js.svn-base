Ext.define('bsmes.view.MaintainTemplateEdit',{
	extend: 'Oit.app.view.form.EditForm',
	alias : 'widget.maintainTemplateEdit',
	title: Oit.msg.fac.maintainTemplate.editForm.title,
	formItems: [ {
		fieldLabel : Oit.msg.fac.maintainTemplate.model,
		name : 'model',
        xtype : 'displayfield'
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
	}, {
		fieldLabel : Oit.msg.fac.maintainTemplate.type,
		name : 'type',
        xtype : 'displayfield',
        renderer : function(value) {
            if (value == "DAILY") {
                return "点检";
            } else if (value =="MONTHLY") {
                return "月检";
            } else if (value =="FIRST_CLASS") {
                return "一级保养";
            } else if (value =="SECOND_CLASS") {
                return "二级保养";
            } else {
                return "大修";
            }
        }
	}, {
		fieldLabel : Oit.msg.fac.maintainTemplate.triggerType,
		name : 'triggerType',
		itemId : 'triggerType',
        xtype : 'displayfield',
        renderer : function(value) {
            if (value == "NATURE") {
                return "自然时间";
            } else if (value =="RUNTIME") {
                return "设备运行时间";
            }
        }
	}, {
        fieldLabel : Oit.msg.fac.maintainTemplate.triggerCycle_m,
        name : 'triggerCycle',
        itemId : 'triggerCycle',
        xtype: 'numberfield',
        renderer: function(value, metaData, record, row, col, store, gridView){
        	if(record.data.triggerType = 'RUNTIME'){
        		this.setFieldLabel(Oit.msg.fac.maintainTemplate.triggerCycle_h);
        	}
        }
    } ,{
		fieldLabel : Oit.msg.fac.maintainTemplate.describe,
		name : 'describe',
        xtype: 'textarea',
        width: 400
	} ]
});