Ext.define('bsmes.view.PropConfigEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.propConfigEdit',
	title: Oit.msg.bas.prop.editForm.title,
	/*iconCls: 'feed-add',*/
	formItems: [{
        fieldLabel: Oit.msg.bas.prop.keyK,
        name: 'keyK',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.bas.prop.valueV,
        name: 'valueV'
    },{
        fieldLabel: Oit.msg.bas.prop.description,
        name: 'description'
    },{
        fieldLabel: Oit.msg.bas.prop.status,
        xtype:'radiogroup',
        columns:4,
        vertical:true,
        items:[{boxLabel: Oit.msg.bas.prop.normal, name: 'status', inputValue: '1',id:'status-2000'},
               {boxLabel: Oit.msg.bas.prop.freeze, name: 'status', inputValue: '0',id:'status-2001'}]
    }] 
});

