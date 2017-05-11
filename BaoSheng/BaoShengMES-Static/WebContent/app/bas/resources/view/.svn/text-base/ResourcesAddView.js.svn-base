Ext.define('bsmes.view.ResourcesAddView', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.resourcesAddView',
	title: Oit.msg.bas.resources.addForm.title,
	iconCls: 'feed-add',
	formItems: [
    {
    	fieldLabel: Oit.msg.bas.resources.parentRes,
        name: 'parentId',
        xtype: 'hiddenfield'
    },
    {
    	fieldLabel: Oit.msg.bas.resources.parentRes,
    	name: 'parentName',
    	xtype: 'textfield',
    	readOnly:true
    },
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
        name: 'type',
        xtype:'combobox',
        model:'local',
        displayField:'type',
        allowBlank : false,
        emptyText : '请选择', 
        blankText:'请选择',
        editable: false,
        store:new Ext.data.Store({
        	fields:['type','type'],
        	data : [
        	        {"type":"MENU", "type":"菜单"},
        	        {"type":"BUTTON", "type":"按钮"}
        	    ]
        })
    },{
    	 fieldLabel: Oit.msg.bas.resources.description,
    	 name: 'description'
    }] 
});
