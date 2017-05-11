Ext.define('bsmes.view.AddEmployeeResume', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.addEmployeeResume',
	formItems: [{
		xtype : 'hiddenfield',
    	name : 'userCode'
	},{
		xtype : 'datefield',
		anchor: '40%',
		labelWidth : 60,
		fieldLabel: '更新时间',
		name: 'recordDate',
		format: 'Y-m-d'
	},{
		xtype : 'textareafield',
		fieldLabel: '履历明细',
		name : 'recordDetail',
        labelWidth : 60,
        grow      : true,
        autoWidth: true,
        labelAlign : 'top'
	}],
	initComponent : function() {
		var me = this;
		this.callParent(arguments);
		if(me.title=='查看履历'){
			var ok = Ext.ComponentQuery.query('#ok');
			ok[ok.length-1].setVisible(false);
			console.log(ok);
		}
	}
});