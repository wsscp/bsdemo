Ext.define("bsmes.view.SysMessageList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.sysMessagelist',
	store : 'SysMessageStore',
	columns : [{
		text : Oit.msg.bas.sysMessage.messageTitle,
		dataIndex : 'messageTitle'
	}, {
		text : Oit.msg.bas.sysMessage.messageContent,
		dataIndex : 'messageContent'
	},{
		text : Oit.msg.bas.sysMessage.hasread,
		dataIndex : 'hasread'
	},{
		text : Oit.msg.bas.sysMessage.messageReceiver,
		dataIndex : 'messageReceiver'
	},{
		text : Oit.msg.bas.sysMessage.receiveTime,
		dataIndex : 'receiveTime',
		xtype:'datecolumn',
		format: 'Y-m-d H:i:s'
	},{
		text : Oit.msg.bas.sysMessage.readTime,
		dataIndex : 'readTime',
		xtype:'datecolumn',
		format: 'Y-m-d H:i:s'
	},{
		text : Oit.msg.bas.sysMessage.orgCode,
		dataIndex : 'orgCode'
	}],
	actioncolumn : [{
		itemId : 'read',
		tooltip : "查看",
		iconCls : 'icon_message',
		xtype : 'radio',
		handler : function(grid, rowIndex) {
			this.findParentByType('sysMessagelist').fireEvent('toRead', grid.getStore().getAt(rowIndex));
		}
    }],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.sysMessage.hasread,
		        xtype:'radiogroup',
                labelAlign:'left',
                labelWidth:60,
                width:180,
                vertical:true,
                items:[ {boxLabel: Oit.msg.bas.sysMessage.yes,  name:'hasread', inputValue: '1'},
                        {boxLabel:Oit.msg.bas.sysMessage.no, name:'hasread', inputValue: '0'}]
		    }]
		}, {
			itemId : 'search'
		}]
	}]
});