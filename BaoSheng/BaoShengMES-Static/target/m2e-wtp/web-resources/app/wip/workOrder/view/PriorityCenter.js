Ext.define('bsmes.view.PriorityCenter',{
	extend:'Ext.panel.Panel',
	height: '100%', 
	frame : true,
	layout:'vbox',
	align:'center',
	alias:'widget.centerView',
	border:0,
	bodyPadding: "200 5 200 5",
	items:[{items:[{
					xtype:'button',
					itemId:'right',
					text:Oit.msg.wip.btn.right
				}]
			},{
				bodyPadding: "10 0 0 0",
				items:[{
					xtype:'button',
					itemId:'left',
					text:Oit.msg.wip.btn.left
				}]
			}]
});