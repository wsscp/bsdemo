Ext.define('bsmes.view.EventTypeEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.eventTypeEdit',
	title: Oit.msg.eve.eventType.eventTypeEdit,
	iconCls: 'feed-add',
	formItems: [{
        fieldLabel:Oit.msg.eve.eventType.code,
        xtype:'displayfield',
        name: 'code'
    },{
        fieldLabel: Oit.msg.eve.eventType.name,
        allowBlank: false, 
        name: 'name'
    },{
        fieldLabel: '是否在终端显示',
        xtype:'radiogroup',
        columns:4,
        vertical:true,
        items:[{boxLabel: '显示', name: 'needShow', inputValue: '1',checked:true},
               {boxLabel: '不显示', name: 'needShow', inputValue: '0'}]
    }] 
});