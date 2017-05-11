Ext.define('bsmes.view.ResourcesEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.resourcesEdit',
	title: Oit.msg.bas.resources.addForm.title,
	iconCls: 'feed-add',
	formItems: [
    {
		fieldLabel: Oit.msg.bas.resources.name,
        name: 'name'
    },
    {
    	fieldLabel: Oit.msg.bas.resources.uri,
    	name: 'uri'
    },
    {
		fieldLabel: Oit.msg.bas.resources.type,
        name: 'typeName',
        xtype:'displayfield'
    },{
    	 fieldLabel: Oit.msg.bas.resources.description,
    	 name: 'description'
    }] 
});
