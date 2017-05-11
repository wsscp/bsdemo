Ext.define('bsmes.view.PriorityCenter',{
	extend:'Ext.panel.Panel',
	height: 466,
	frame : true,
	layout:'vbox',
	align:'center',
	alias:'widget.centerView',
	border:0,
	bodyPadding: "200 13 200 13",
	items:[{items:[{
					xtype:'button',
					itemId:'right',
					text:Oit.msg.pla.customerOrderItem.button.right
				}]
			},{
				bodyPadding: "10 0 0 0",
				items:[{
					xtype:'button',
					itemId:'left',
					text:Oit.msg.pla.customerOrderItem.button.left
				}]
			}]
});