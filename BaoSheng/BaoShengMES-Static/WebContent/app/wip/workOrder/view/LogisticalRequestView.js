Ext.define("bsmes.view.LogisticalRequestView", {
	extend : 'Ext.window.Window',
	alias : 'widget.logisticalRequestView',
	title : Oit.msg.wip.btn.logisticalRequest,
	width : 650,
	height : 400,
//	layout : {
//		type : 'vbox',
//		padding : 5
//	},
	modal : true,
	plain : true,
	requires:[
	          'bsmes.view.WorkOrderProductList',
	          'bsmes.view.WorkOrderToolsReqList',
	          'bsmes.view.WorkOrderMatReqList'
	          ],
	buttons : [ {
		text : Oit.btn.close,
		handler: function() {
			this.up('.window').close();
	    }
	} ],
	items : [ 
		{
		xtype : 'form',
		layout : 'vbox',
		width : '100%',
		labelAlign : 'right',
		// bodyPadding : 5,
		defaults : {
			xtype : 'panel',
			width : '100%',
			layout : 'hbox',
			defaults : {
				labelAlign : 'right',
				bodyPadding : '5 10 10',
				xtype : 'displayfield'
			}
		},
		items : [ {
			items : [ {
				fieldLabel : Oit.msg.wip.workOrder.workOrderNO,
				name : 'workOrderNo'
			}, {
				fieldLabel : Oit.msg.wip.workOrder.orderLength,
				name : 'orderLength'
			} ]
		}, {
			height : 5
		}, {
			items : [ {
				fieldLabel : Oit.msg.wip.workOrder.processName,
				name : 'processName'
			}, {
				fieldLabel : Oit.msg.wip.workOrder.equipName,
				name : 'equipName'
			} ]
		} ]
	},
	{
		xtype : 'tabpanel',
		items : [ {
			xtype : 'workOrderProductList',
			height : 225,
			title : '产品清单'
		}, {
			xtype:'workOrderToolsReqList',
			height : 225,
			title : '工装清单'
		}, {
			xtype : 'workOrderMatReqList',
			height : 225,
			title : '物料需求清单'
		} ]
	}/*,
	{
		region : 'north',
		width : 300,
		height : 60,
		xtype : 'workOrderProductList'
	}, {
		region : 'center',
		width : '100%',
		height : 180,
		xtype:'workOrderToolsReqList'
	},{
		region : 'south',
		width : 300,
		height : 50,
		xtype : 'workOrderMatReqList'
	}*/ ]
});
