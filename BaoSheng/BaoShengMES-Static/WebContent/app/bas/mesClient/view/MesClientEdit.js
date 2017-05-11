Ext.define('bsmes.view.MesClientEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.mesClientedit',
	title: Oit.msg.bas.mesClient.addForm.title,
	iconCls: 'feed-add',
	formItems: [
    {
		fieldLabel: Oit.msg.bas.mesClient.clientMac,
        name: 'clientMac',
        allowBlank:false
    },{
    	fieldLabel: Oit.msg.bas.mesClient.clientIp,
    	name: 'clientIp'
    },{
		fieldLabel: Oit.msg.bas.mesClient.clientName,
        name: 'clientName'
    },{
		fieldLabel: Oit.msg.bas.mesClient.printNum,
		name:'printNum'
	}]
});
