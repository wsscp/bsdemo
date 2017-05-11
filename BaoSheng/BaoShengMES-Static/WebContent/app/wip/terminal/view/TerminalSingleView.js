Ext.define('bsmes.view.TerminalSingleView', {
	extend : 'Ext.container.Container',
	alias : 'widget.terminalSingleView',
	layout : 'vbox',
	initComponent : function() {
		var me = this;
		var btnReportText;
		var btnBack;
		var btnDebugText;
		if (Ext.fly('orderInfo').getAttribute('status') == 'TO_DO') {
			btnReportText = Oit.msg.wip.terminal.btn.receive;
		} else {
			btnReportText = Oit.msg.wip.terminal.btn.report;
		}
		if (Ext.fly('single').getAttribute('single') == 'true') {
			btnBack = {
				xtype : 'button',
				itemId : 'sign',
				text : Oit.msg.wip.terminal.btn.sign
			};
		} else {
			btnBack = {
				xtype : 'button',
				itemId : 'back',
				text : Oit.msg.wip.terminal.btn.back,
				href : '../terminal.action',
				hrefTarget : '_self'
			};
		}

		//判断接受按钮是否显示
		var disableReceiveBtn = (btnReportText == Oit.msg.wip.terminal.btn.report && Ext.fly('equipInfo').getAttribute('status') != 'IN_PROGRESS')
				|| (btnReportText == Oit.msg.wip.terminal.btn.receive && Ext.fly('equipInfo').getAttribute('isFeedCompleted') == 'false');

		var toolBar = {};
		var issuedParamIshidden = true;
		var changeBtnIshidden = true;
		if (Ext.fly('orderInfo').getAttribute('status') == 'IN_PROGRESS') {
			changeBtnIshidden = false;
			btnDebugText = Oit.msg.wip.terminal.btn.debug;
			if (Ext.fly('equipInfo').getAttribute('status') == 'IN_DEBUG') {
				btnDebugText = Oit.msg.wip.terminal.btn.product;
				issuedParamIshidden = false;
			} else {
				btnDebugText = Oit.msg.wip.terminal.btn.debug;
				issuedParamIshidden = true;
			}
		} else {
			changeBtnIshidden = true;
			issuedParamIshidden = true;
		}

		toolBar = {
			xtype : 'toolbar',
			width : '100%',
			height : 60,
			items : [{
						xtype : 'container',
						contentEl : 'equipInfo'
					}, '->', {
						xtype : 'button',
						text : Oit.msg.wip.terminal.btn.QADataEntry,
						itemId : 'QADataEntry',
						woNo : Ext.fly('orderInfo').getAttribute('num'),
						//hidden:Ext.fly('equipInfo').getAttribute('status') != 'IN_PROGRESS' && Ext.fly('equipInfo').getAttribute('status') != 'IN_DEBUG',
						margin : '0 20 0 0'
					}, {
						xtype : 'button',
						itemId : 'changeEquipStatus',
						text : btnDebugText,
						hidden : changeBtnIshidden,
						margin : '0 20 0 0'
					}, {
						xtype : 'button',
						itemId : 'issuedParam',
						text : Oit.msg.wip.terminal.btn.issuedParam,
						hidden : issuedParamIshidden,
						margin : '0 20 0 0'
					}, {
						xtype : 'button',
						itemId : 'check',
						text : Oit.msg.wip.terminal.btn.dailyMaintain,
						hidden : /^LINE-VIRTUAL/.test(Ext.fly('equipInfo').getAttribute('code')),
						margin : '0 20 0 0',
						equipCode : Ext.fly('equipInfo').getAttribute('code')
					}, btnBack, {
						xtype : 'button',
						text : Oit.btn.close,
						itemId : 'closewindow'
					}]
		}

		me.items = [toolBar, {
			xtype : 'form',
			itemId : 'equipForm' + Ext.fly('equipInfo').getAttribute('equipId'),
			width : '100%',
			height : 150,
			layout : {
				type : 'table',
				columns : 4,
				tableAttrs : {
					style : {
						width : '100%'
					}
				}
			},
			defaultType : 'displayfield',
			defaults : {
				labelWidth : 130
			},
			items : [{
						fieldLabel : Oit.msg.wip.terminal.processCode,
						name : 'currentProcess'
					}, {
						fieldLabel : Oit.msg.wip.terminal.productType,
						name : 'productType'
					}, {
						fieldLabel : Oit.msg.wip.terminal.productSpec,
						name : 'productSpec'
					}, {
						fieldLabel : Oit.msg.wip.terminal.planLength,
						name : 'contractLength'
					}, {
						fieldLabel : Oit.msg.wip.terminal.productLength,
						name : 'planLength'
					}, {
						fieldLabel : Oit.msg.wip.terminal.remainQLength,
						name : 'remainQLength'
					}, {
						fieldLabel : Oit.msg.wip.terminal.currentDiscLength,
						name : 'currentDiscLength'
					}, {
						fieldLabel : '可报工长度',
						name : 'currentReportLength'
					}, {
						fieldLabel : Oit.msg.wip.terminal.productColor,
						name : 'productColor'
					},

					{
						xtype : 'hiddenfield',
						name : 'needInCheck',
						id : 'needInCheck'
					}, {
						xtype : 'hiddenfield',
						name : 'needMiddleCheck',
						id : 'needMiddleCheck'
					}
			//{
			//    fieldLabel: Oit.msg.wip.terminal.productCode,
			//    name: 'currentProduct'
			//}
			],
			header : {
				xtype : 'toolbar',
				height : 60,
				items : [
						"<span style='font-size:20px;line-height: 24px;color:#ffffff;font-weight: bold;'>"
								+ Oit.msg.wip.terminal.orderInfoTitle + '(' + Ext.fly('orderInfo').getAttribute('num') + ')' + "</span>",
						'->', {
							xtype : 'button',
							itemId : 'receive',
							text : btnReportText,
							equipCode : Ext.fly('equipInfo').getAttribute('code'),
							hidden : disableReceiveBtn,
							margin : '0 20 0 0'
						}, {
							xtype : 'button',
							text : Oit.msg.wip.terminal.btn.reportDetail,
							itemId : 'reportDetail',
							equipCode : Ext.fly('equipInfo').getAttribute('code'),
							margin : '0 20 0 0'
						}, {
							xtype : 'button',
							text : Oit.msg.wip.terminal.btn.querySpark,
							itemId : 'sparkRepairDetail',
							equipCode : Ext.fly('equipInfo').getAttribute('code'),
							margin : '0 20 0 0'
						}, {
							xtype : 'button',
							text : Oit.msg.wip.terminal.btn.triggerAlarm,
							itemId : 'triggerEquipAlarm',
							margin : '0 20 0 0'
						}, {
							xtype : 'button',
							text : Oit.msg.wip.terminal.btn.handleAlarm,
							itemId : 'handleEquipAlarm',
							margin : '0 20 0 0'
						}]
			}
		}, {
			xtype : 'panel',
			width : '100%',
			itemId : 'orderDetailPanel',
			height : document.body.scrollHeight - 210,
			header : {
				xtype : 'toolbar',
				height : 60,
				items : [
						"<span style='font-size:20px;line-height: 24px;color:#ffffff;font-weight: bold;'>"
								+ Oit.msg.wip.terminal.orderDetailTitle + "</span>", {
							xtype : 'button',
							itemId : 'emphInfoShow',
							text : Oit.msg.wip.terminal.btn.emphInfoShow
						}, {
							xtype : 'button',
							itemId : 'toDayMatPlan',
							text : Oit.msg.wip.terminal.btn.toDayMatPlan
						}, '->', {
							xtype : 'button',
							itemId : 'change',
							text : Oit.msg.wip.terminal.btn.change,
							equipCode : Ext.fly('equipInfo').getAttribute('code')
						}]

			},
			tbar : [Oit.msg.wip.terminal.workOrderNo + ':' + Ext.fly('orderDetail').getAttribute('num'), '-', {
						xtype : 'button',
						itemId : 'pause',
						text : Oit.msg.wip.btn.pause
					}, '->', Ext.fly('orderDetail').getAttribute('status')],
			bbar : [{
						xtype : 'panel',
						width : '100%',
						height : 20,
						bodyStyle : 'font-size: 20px;',
						html : "当前在线用户：" + Ext.fly('workUsers').getHTML()
					}],
			items : [{
						xtype : 'tabpanel',
						width : '100%',
						height : document.body.scrollHeight - 60 - 120 - 60 - 60 - 16 - 30,
						items : [{
									xtype : 'materialGrid',
									woNo : Ext.fly('orderInfo').getAttribute('num')
								}, {
									xtype : 'processReceiptGrid'
								}, {
									xtype : 'processQcGrid'
								}, {
									xtype : 'agreementGrid'
								}, {
									xtype : 'dailyCheckGrid'
								}]
					}]
		}]

		me.callParent(arguments); // ------------call父类--------------
	}
});
