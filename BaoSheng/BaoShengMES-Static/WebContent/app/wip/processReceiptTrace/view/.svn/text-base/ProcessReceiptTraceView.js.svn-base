Ext.define("bsmes.view.ProcessReceiptTraceView", {
	extend : 'Ext.container.Container',
	alias : 'widget.processReceiptTraceView',
	height : '100%',
	layout : 'vbox',
	padding : 5,
	defaults : {
		width : '100%',
		height : '200'
	},
	controller : {},
	items : [{
				xtype : 'form',
				layout : 'hbox',
				margin : '5 0 5 0',
				defaults : {
					labelAlign : 'right',
					xtype : 'displayfield',
					labelWidth : 80
				},
				items : [{
							fieldLabel : Oit.msg.wip.processReceiptTrace.equipCode,
							name : 'equipCode',
							id : 'equipCodeComb',
							xtype : 'combobox',
							displayField : 'name',
							valueField : 'ecode',
							minChars : 1,
							width : 380,
							labelWidth : 50,
							store : Ext.create('bsmes.store.ProcessReceiptEquipStore'),
							listeners : {
								'beforequery' : function(queryPlan, eOpts) {
									var me = this;
									var url = '../fac/equipInfo/getEquipLine';
									if (queryPlan.query) {
										me.getStore().getProxy().url = url + "?codeOrName=" + queryPlan.query + '';
									}
								}
							}
						}, {
							fieldLabel : Oit.msg.wip.processReceiptTrace.status,
							hidden : true,
							xtype : 'displayfield',
							id : 'statusId',
							name : 'status'
						},
						/**
						 * @date 2015/6/24
						 * @author DingXintao
						 * @description 修改工艺参数报表，屏蔽QC { fieldLabel :
						 *              Oit.msg.wip.processReceiptTrace.processCode,
						 *              name : 'processCode', xtype :
						 *              'combobox', id :
						 *              'processReceiptTrace_process',
						 *              displayField : 'processName', valueField :
						 *              'processCode', minChars : 1, width :
						 *              360, store :
						 *              Ext.create('bsmes.store.ProcessStore'),
						 *              editable : false, margin : '0 0 0 20',
						 *              listeners : { 'beforequery' :
						 *              function(queryPlan, eOpts) { var me =
						 *              this; var equipCodeComb =
						 *              me.findParentByType("form").queryById("equipCodeComb");
						 *              var equipCode =
						 *              equipCodeComb.getValue(); var url =
						 *              'processReceiptTrace/process';
						 * 
						 * if (equipCode) { url = url + "/" + equipCode; } else {
						 * url = url + "/-1"; }
						 * 
						 * //me.getStore().getProxy().url = url; if
						 * (queryPlan.query) { me.getStore().getProxy().url =
						 * url + "/" + queryPlan.query + '/'; } else {
						 * me.getStore().getProxy().url = url + "/-1"; } },
						 * 'expand' : function(field, eOpts) { var me = this;
						 * me.clearValue(); var equipCodeComb =
						 * me.findParentByType("form").queryById("equipCodeComb");
						 * var equipCode = equipCodeComb.getValue(); var url =
						 * 'processReceiptTrace/process';
						 * 
						 * if (equipCode) { url = url + "/" + equipCode; } else {
						 * url = url + "/-1"; } me.getStore().getProxy().url =
						 * url + "/-1"; me.getStore().reload(); } } },
						 */
						{
							fieldLabel : Oit.msg.wip.processReceiptTrace.productCode,
							hidden : true,
							xtype : 'displayfield',
							id : 'productCodeId',
							name : 'productCode'
						}]
			}, {
				id : 'centerPanel',
				xtype : 'panel',
				height : "100",
				layout : 'hbox',
				autoScroll : true,
				overflowY : 'auto',
				forceFit : true,
				items : [{
							xtype : 'grid',
							itemId : 'processReceiptGrid',
							// title :
							// Oit.msg.wip.processReceiptTrace.processReceipt,
							width : "19.9%",
							minwidth : 200,
							height : document.body.scrollHeight - 70,
							autoScroll : true,
							overflowY : 'auto',
							forceFit : true,
							controller : {},
							store : 'ProcessReceiptTraceStore',
							columns : [{
										text : "工艺参数名称",
										dataIndex : 'receiptName',
										flex : 2.4
									}, {
										text : "值",
										flex : 1.6,
										dataIndex : 'daValue'
									}],
							tbar : [{
										itemId : 'search',
										text : Oit.btn.search
									}, {
										itemId : 'reset',
										text : '重置',
										handler : function(e) {
											this.up("form").getForm().reset();
										}
									}, {
										xtype : 'button',
										text : '刷新',
										handler : function() {
											Ext.ComponentQuery.query('#processReceiptGrid')[0].getStore().load();
										}
									}, {
										itemId : 'historySearch',
										text : '历史数据曲线图'
									}, {
										itemId : 'realSearch',
										hidden : true,
										text : '实时数据曲线图'
									}],
							setController : function(controller) {
								var me = this;
								me.controller = controller;
							},
							getController : function() {
								var me = this;
								return me.controller;
							}
						}, {
							xtype : 'panel',
							id : 'realReceiptChart_id1',
							width : "80%",
							height : document.body.scrollHeight - 70,
							layout : {
								align : 'middle',
								pack : 'center',
								type : 'hbox'
							},
							items : [{
										xtype : 'displayfield',
										value : '<font style="font-size: 16px;margin:0 auto;">实 时 数 据 曲 线 图：请选择一个设备</font>'
									}]
						}]
			}]
});
