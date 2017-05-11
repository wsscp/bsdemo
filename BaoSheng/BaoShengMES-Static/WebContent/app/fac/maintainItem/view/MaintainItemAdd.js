Ext.define('bsmes.view.MaintainItemAdd',{
	extend: 'Oit.app.view.form.EditForm',
	alias : 'widget.maintainItemAdd',
	title: Oit.msg.fac.maintainItem.addForm.title,
	formItems: [{
		fieldLabel : Oit.msg.fac.maintainItem.describe,
		name : 'describe',
		xtype: 'textarea',
        width : 400
	} ]
});