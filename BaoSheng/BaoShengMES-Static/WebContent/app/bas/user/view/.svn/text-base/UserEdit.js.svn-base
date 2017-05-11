Ext.define('bsmes.view.UserEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.useredit',
	title: Oit.msg.bas.user.editForm.title,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel: Oit.msg.bas.user.userCode,
        name: 'userCode',
        xtype:'displayfield',
        readOnly:true
    },{
        fieldLabel: Oit.msg.bas.employee.name,
        name: 'name',
        xtype:'displayfield',
        readOnly:true
    },{
        fieldLabel: Oit.msg.bas.user.password,
        name: 'password',
        allowBlank: false,
        inputType: 'password'
    },{
        fieldLabel: Oit.msg.bas.user.status,
        xtype:'radiogroup',
        items:[{boxLabel: Oit.msg.bas.user.normal, name: 'status', inputValue: '1',checked:true},
               {boxLabel: Oit.msg.bas.user.freeze, name: 'status', inputValue: '0'}]
    }] 
});