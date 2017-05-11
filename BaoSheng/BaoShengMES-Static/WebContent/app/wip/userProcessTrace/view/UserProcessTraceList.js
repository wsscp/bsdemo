Ext.define("bsmes.view.UserProcessTraceList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.userProcessTraceList',
	store : 'UserProcessTraceStore',
    collapsible: false,  
    animCollapse: false,
	columns : [
		{text : Oit.msg.wip.userProcessTrace.reportUserCode,dataIndex : 'reportUserCode'}, 
		{text : Oit.msg.wip.userProcessTrace.reportUserName,dataIndex : 'reportUserName'}, 
		{text : Oit.msg.wip.userProcessTrace.workOrderNo,dataIndex : 'workOrderNo'}, 
		{
			text : Oit.msg.wip.userProcessTrace.productCode,
			dataIndex : 'productCode'
			//renderer:function (value, metaData, record, rowIdx, colIdx, store) {
			//	if(value){
			//		metaData.tdAttr = 'data-qtip=" <div style=\'word-break:break-all;word-wrap:break-word;\'>' + value + '</div>"';
			//	}
             //   return value;
			//}
		},
		{text : Oit.msg.wip.userProcessTrace.realStartTime,dataIndex : 'realStartTime'},
		{text : Oit.msg.wip.userProcessTrace.realEndTime,dataIndex : 'realEndTime'},
		{text : Oit.msg.wip.userProcessTrace.equipCode,dataIndex : 'equipCode'},
		{text : Oit.msg.wip.userProcessTrace.orderLength,dataIndex : 'orderLength'},
		{text : Oit.msg.wip.userProcessTrace.usedTime,dataIndex : 'usedTime'}
	],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			title : '查询条件',
			xtype : 'fieldset',
			collapsible : true,
			width : '100%',
			items : [ {
				xtype : 'form',
				width : '100%',
				layout : 'vbox',
				buttonAlign : 'left',
				labelAlign : 'right',
				bodyPadding : 5,
				defaults : {
					xtype : 'panel',
					width : '100%',
					layout : 'hbox',
					defaults : {
						labelAlign : 'right'
					}
				},
				items : [ {
					items : [ {
						fieldLabel : Oit.msg.wip.userProcessTrace.contractNo,
						xtype : 'textfield',
						name : 'contractNo'
					}, {
						fieldLabel : Oit.msg.wip.userProcessTrace.workOrderNo,
						xtype : 'textfield',
						name : 'workOrderNo'
					}, {
						fieldLabel : Oit.msg.wip.userProcessTrace.equipCode,
						xtype : 'textfield',
						name : 'equipCode'
					},{
						fieldLabel : Oit.msg.wip.userProcessTrace.userCode,
						xtype : 'combo',
						name : 'reportUserCode',
						mode : 'remote',
						displayField : 'name',
						valueField:'userCode',
						selectOnFocus : true,
						forceSelection:true,
						hideTrigger : true,
						minChars : 1,
						store:new Ext.data.Store({
							  fields:['userCode', 'name'],
							  proxy:{
								  type: 'rest',
								  url:'qualityTrace/user'
							  }
						}),
						listeners: { 
							'beforequery': function (queryPlan, eOpts) {
								var me=this;
								var url = 'qualityTrace/user/'+queryPlan.query+'/';
								me.getStore().getProxy().url=url;
							}
						}
					}]
				}],
				buttons : [ {
					itemId : 'search',
					text : Oit.btn.search
				}, {
					itemId : 'reset',
					text : '重置',
					handler : function(e) {
						this.up("form").getForm().reset();
					}
				}, {
					itemId : 'exportToXls',
					text : Oit.btn.export
				} ]

			} ]
		} ]
	}]
//	dockedItems : [ {
//		xtype : 'toolbar',
//		dock : 'top',
//		items : [ {
//			id :'userProcessTraceSearchForm',
//			xtype : 'form',
//			width : '100%',
//			layout : 'hbox',
//			buttonAlign : 'left',
//			labelAlign : 'right',
//			bodyPadding : 5,
//			defaults : {
//				labelAlign : 'left',
//				labelWidth : 50
//			},
//			items : [ {
//					fieldLabel : Oit.msg.wip.userProcessTrace.reportUserCode,
//					xtype : 'textfield',
//					name : 'reportUserCode'
//				},{
//					fieldLabel : Oit.msg.wip.userProcessTrace.reportUserName,
//					xtype : 'textfield',
//					name : 'reportUserName'
//				},{
//					fieldLabel : Oit.msg.wip.userProcessTrace.contractNo,
//					xtype : 'textfield',
//					name : 'contractNo'
//				},{
//					fieldLabel : Oit.msg.wip.userProcessTrace.workOrderNo,
//					xtype : 'textfield',
//					name : 'workOrderNo'
//				},{
//					fieldLabel : Oit.msg.wip.userProcessTrace.equipCode,
//					xtype : 'textfield',
//					name : 'equipCode'
//				}],
//			buttons : [ {
//				itemId : 'search',
//				text : Oit.btn.search
//			},{
//				itemId:'reset',
//				text : '重置',
//				handler : function(e) {
//					this.up("form").getForm().reset();
//				}
//			},{
//				itemId:'exportToXls',
//				text : '导出到xls',
//			}]
//		}]
//	} ]
});
