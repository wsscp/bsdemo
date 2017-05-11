Ext.define('bsmes.view.MaintainItemEdit',{
	extend: 'Oit.app.view.form.EditForm',
	alias : 'widget.maintainItemEdit',
	title: Oit.msg.fac.maintainItem.editForm.title,
	formItems: [{
		fieldLabel : Oit.msg.fac.maintainItem.describe,
		name : 'describe',
        xtype: 'textarea',
        width : 400
	} ]
});