Ext.define('bsmes.view.SysMessageRead', {
	extend: 'Ext.window.Window',
	alias: 'widget.sysMessageread',
	title: Oit.msg.bas.sysMessage.readForm.title,
	iconCls: 'feed-add',
	width: 500,
	layout: 'fit',
	modal: true,
	plain: true,
	closable: false,
    initComponent: function() {
		var me = this;
		Ext.apply(me, {
			buttons: [{
				itemId: 'ok',
				text:Oit.btn.ok
			}]
		});
		
		me.items = [{
			xtype: 'form',
			bodyPadding: '12 10 10',
		    items: [{
		        fieldLabel: Oit.msg.bas.sysMessage.messageTitle,
		        name: 'messageTitle',
		        xtype:'displayfield',
		        readOnly:true
		    },{
		        fieldLabel: Oit.msg.bas.sysMessage.messageContent,
		        name: 'messageContent',
		        xtype:'displayfield',
		        readOnly:true
		    },{
		        fieldLabel: Oit.msg.bas.sysMessage.messageReceiver,
		        name: 'messageReceiver',
		        xtype:'displayfield',
		        readOnly:true
		    }]
		}],
		this.callParent(arguments);
	} 
});