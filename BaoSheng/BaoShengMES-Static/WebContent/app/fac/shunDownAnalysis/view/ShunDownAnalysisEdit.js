Ext.override(Ext.form.DisplayField, { getValue: function () { return this.value; }, setValue: function (v) { this.value = v; this.setRawValue(this.formatValue(v)); return this; }, formatValue: function (v) { if (this.dateFormat && Ext.isDate(v)) { return v.dateFormat(this.dateFormat); } if (this.numberFormat && typeof v == 'number') { return Ext.util.Format.number(v, this.numberFormat); } return v; } }); 
Ext.define('bsmes.view.ShunDownAnalysisEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.shunDownAnalysisEdit',
	title: Oit.msg.fac.shunDownAnalysis.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.fac.shunDownAnalysis.equipCode,
        name: 'equipCode',
        xtype:'displayfield'
    },{
        fieldLabel: Oit.msg.fac.shunDownAnalysis.equipName,
        name: 'equipName',
        xtype:'displayfield'
    },{
    	fieldLabel : Oit.msg.fac.shunDownAnalysis.startTime,
        name: 'startTime',
        xtype: 'datefield',
        readOnly:true,
        disabled:true,
        width:270,
        format : 'Y-m-d H:i:s'
                 
    },{
    	fieldLabel : Oit.msg.fac.shunDownAnalysis.endTime,
        name: 'endTime',
        xtype:'datefield',
        readOnly:true,
        disabled:true,
        width:270,
        format : 'Y-m-d H:i:s'
    },{
    	fieldLabel:Oit.msg.fac.shunDownAnalysis.reason,
		name:'reason',
    	xtype : 'combobox',
    	editable:false,
    	width:270,
    	mode:'remote',
    	displayField:'name',
    	valueField : 'name',
    	triggerAction :'all',
		store:new Ext.data.Store({
			fields:['code','name'],
			autoLoad:false,
			proxy:{
			  type: 'rest',
			  url:'shunDownAnalysis/statusReason'
			}
		})
    }] 
});