Ext.define('bsmes.view.CheckEventFlowPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.checkEventFlowPanel',
	layout : 'fit',
	store : null,
	initComponent : function() {
		var me = this;

		me.items = [ {
			xtype : 'form',
			bodyPadding : '12 10 10',
			width : '100%',
			defaultType : 'textfield',
			defaults : {
				labelAlign : 'right'
			},
			bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
			items : [ {
				xtype : 'hform',
				width : '100%',
				defaults : {
					labelAlign : 'left'
				},
				layout : {
					type : 'table',
					columns : 2,
					tableAttrs : {
						style : {
							width : '100%'
						}
					}
				},
				items : [ {
					fieldLabel : 'id',
					name : 'id',
					hidden : true
				}, {
					fieldLabel : 'eventInfoId',
					name : 'eventInfoId',
					hidden : true
				}, {
					fieldLabel : '设备名称',
					xtype : 'displayfield',
					name : 'equipName'
				}, {
					fieldLabel : '报修时间',
					xtype : 'displayfield',
					name : 'createTime',
					renderer : Ext.util.Format.dateRenderer('Y-m-d H:i')
				}, {
					fieldLabel : '型号规格',
					xtype : 'displayfield',
					width : 250,
					name : 'equipModelStandard'
				}, {
					fieldLabel : '开始修理时间',
					xtype : 'displayfield',
					renderer : Ext.util.Format.dateRenderer('Y-m-d H:i'),
					name : 'startRepairTime'
				}, {
					fieldLabel : '报修人',
					xtype : 'displayfield',
					name : 'protectMan'
				}, {
					fieldLabel : '完成修理时间',
					name : 'finishRepairTime',
					xtype : 'displayfield',
					renderer : Ext.util.Format.dateRenderer('Y-m-d H:i')
				}, {
					fieldLabel : '修理人',
					name : 'repairMan',
					xtype : 'displayfield'
				}, {
					xtype : 'radiogroup',
					fieldLabel : '故障类型',
					width : '250px',
					columns : 2,
					vertical : true,
					items : [ {
						boxLabel : '机械',
						name : 'failureModel',
						inputValue : '机械'
					}, {
						boxLabel : '电气',
						name : 'failureModel',
						inputValue : '电气'
					} ]
				},{
					xtype : 'radiogroup',
					fieldLabel : '维修评价',
					width : '250px',
					columns : 3,
					vertical : true,
					items : [ {
						boxLabel : '好',
						name : 'evaluate',
						inputValue : '好'
					}, {
						boxLabel : '中',
						name : 'evaluate',
						inputValue : '中'
					} ,{
						boxLabel : '差',
						name : 'evaluate',
						inputValue : '差'
					} ]
				} ]
			}, {
				xtype : 'textareafield',
				grow : true,
				readOnly : true,
				name : 'equipTroubleDescribetion',
				labelAlign : 'top',
				width : 575,
				height : 100,
				fieldLabel : '设备故障状况描述'
			}, {
				xtype : 'textareafield',
				grow : true,
				readOnly : true,
				name : 'equipTroubleAnalyze',
				labelAlign : 'top',
				width : 575,
				height : 100,
				margin : '20 0 0 0',
				fieldLabel : '故障原因分析'
			}, {
				xtype : 'textareafield',
				grow : true,
				readOnly : true,
				name : 'repairMeasures',
				labelAlign : 'top',
				width : 575,
				height : 100,
				margin : '20 0 0 0',
				fieldLabel : '修理措施'
			}, {
				title : '零件更换',
				xtype : 'fieldset',
				margin : '20 0 20 0',
				width : 575,
				items : [ {
					xtype : 'grid',
					width : 575,
					margin : '0 0 10 0',
					store : me.store,
					columns : [ {
						text : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						text : '新备件编码',
						flex : 0.8,
						dataIndex : 'newSparePartCode'
					}, {
						text : '备件型号规格',
						flex : 1,
						dataIndex : 'sparePartModel'
					}, {
						text : '使用部位',
						flex : 0.7,
						dataIndex : 'useSite'
					}, {
						text : '替换数量',
						flex : 0.7,
						dataIndex : 'quantity'
					}, {
						text : '被替换件编码',
						flex : 1,
						dataIndex : 'oldSparePartCode'
					}, {
						text : '被替换件情况',
						flex : 1,
						dataIndex : 'oldSparePartSituation'
					} ]
				} ]
			} ]
		} ];

		me.callParent(arguments);
	}
});
