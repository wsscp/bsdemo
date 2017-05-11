Ext.define('bsmes.view.MaintainRecordItemEdit',{
	extend: 'Oit.app.view.form.EditForm',
	alias : 'widget.maintainRecordItemEdit',
	title: Oit.msg.fac.maintainRecordItem.editForm.title,
	formItems: [{
		fieldLabel : Oit.msg.fac.maintainRecordItem.isPassed,
		name : 'isPassed',
        xtype : 'fieldcontainer',
        defaultType: 'radiofield',
        defaults: {
            name: 'isPassed'
        },
        items: [{
            boxLabel  : '是',
            inputValue: 'true'
        }, {
            boxLabel  : '否',
            inputValue: 'false'
        }]
	} ,{
		fieldLabel : Oit.msg.fac.maintainRecordItem.remarks,
		name : 'remarks',
        xtype: 'displayfield'
	} ,{
		fieldLabel : Oit.msg.fac.maintainRecordItem.value,
		name : 'value',
		xtype: 'numberfield'
	} ]
});