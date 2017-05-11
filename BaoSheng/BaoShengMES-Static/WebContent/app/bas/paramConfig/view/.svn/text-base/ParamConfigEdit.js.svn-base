Ext.define('bsmes.view.ParamConfigEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.paramConfigEdit',
	title: Oit.msg.bas.param.editForm.title,
	/*iconCls: 'feed-add',*/
	formItems: [{
        fieldLabel: Oit.msg.bas.param.code,
        name: 'code',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.bas.param.name,
        name: 'name',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.bas.param.value,
        name: 'value',
    },{
        fieldLabel: Oit.msg.bas.param.type,
        name: 'type',
    },{
        fieldLabel: Oit.msg.bas.param.description,
        name: 'description',
    },{
        fieldLabel: Oit.msg.bas.param.status,
        xtype:'radiogroup',
        columns:4,
        vertical:true,
        items:[{boxLabel: Oit.msg.bas.param.normal, name: 'status', inputValue: '1',id:'status-2000'},
               {boxLabel: Oit.msg.bas.param.freeze, name: 'status', inputValue: '0',id:'status-2001'}]
    }] 
});

