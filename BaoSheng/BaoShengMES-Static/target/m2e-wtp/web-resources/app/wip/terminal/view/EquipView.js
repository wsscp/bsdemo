/**
 * Created by chanedi on 14-3-3.
 */
Ext.define("bsmes.view.EquipView", {
			extend : 'Ext.panel.Panel',
			alias : 'widget.equipView',
			layout : 'vbox',
			height : /* document.body.scrollHeight / 3 */300,
			width : (document.body.scrollWidth - 3) / 7 * 3,
			equipStatus : '',
			equipCode : '',
			equipName : '',
			initComponent : function() {
				var me = this;
				var checkHidden = false;
				var str = me.equipAlias;

				if (me.equipCode != null) {
					me.icon = '/bsstatic/icons/' + me.equipStatus + '.png';
					me.title = me.equipAlias + "&nbsp;(" + me.equipCode + ")";
					
					if(str.substring(0,2) == '交联'){
						checkHidden = true;
					}

					var btnReportText;
					if (me.equipStatus == 'IDLE') {
						btnReportText = Oit.msg.wip.terminal.btn.receive;
					} else {
						btnReportText = Oit.msg.wip.terminal.btn.report;
					}
					me.buttons = [{
								text : Oit.msg.wip.terminal.btn.detail,
								itemId : 'detail',
								equipCode : me.equipCode,
								margin : '0 10 0 0'
							}, {
								text : Oit.msg.wip.terminal.btn.dailyMaintain,
								margin : '0 10 0 0',
								xtype : 'button',
								itemId : 'check',
								equipCode : me.equipCode,
								hidden : checkHidden,
//								hidden : /^LINE-VIRTUAL/.test(me.equipCode),
								equipCode : me.equipCode
							}, {
								xtype : 'button',
								text : Oit.msg.wip.terminal.btn.triggerAlarm,
								itemId : 'triggerEquipAlarm',
								equipCode : me.equipCode,
								margin : '0 10 0 0'
							}/*
								 * { xtype:'button', text:
								 * Oit.msg.wip.terminal.btn.handleAlarm,
								 * itemId:'handleEquipAlarm', equipCode:
								 * me.equipCode, margin:'0 10 0 0' }
								 */, {
								xtype : 'button',
								text : '查看生产单',
								itemId : 'checkOrderItemId',
								equipCode : me.equipCode,
								section : me.section,
								margin : '0 10 0 0'
							}];
				}

				var equipStatusText = "";
				if (me.equipStatus == "CLOSED") {
					equipStatusText = "关机";
				} else if (me.equipStatus == "ERROR") {
					equipStatusText = "故障";
				}

				var formItems = [];
				if (me.equipStatus == null || me.equipStatus == undefined) {
					formItems.push({
								items : [{
											xtype : 'label',
											text : Oit.msg.wip.terminal.noEquipment
										}]
							});
				} else if (me.equipStatus == 'IDLE' || me.equipStatus == 'CLOSED') {
					formItems.push({
								items : [{
											xtype : 'displayfield',
											fieldLabel : Oit.msg.wip.terminal.toDoTaskNum,
											name : 'toDoTaskNum'
										}, {
											xtype : 'displayfield',
											fieldLabel : Oit.msg.wip.terminal.workOrderNo,
											name : 'workOrderNo'
										}]
							});
				} else if (me.equipStatus == 'IN_PROGRESS' || me.equipStatus == 'IN_DEBUG') {
					formItems.push({
								items : [{
											xtype : 'displayfield',
											fieldLabel : Oit.msg.wip.terminal.orderLength,
											name : 'planLength',
											renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
												return Math.round(value);
											}
										}, {
											xtype : 'displayfield',
											fieldLabel : Oit.msg.wip.terminal.discLength,
											name : 'currentDiscLength',
											renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
												return Math.round(value);
											}
										}, {
											xtype : 'displayfield',
											fieldLabel : '采集长度',
											name : 'currentReportLength',
											fieldStyle : {
												color : 'red'
											}
										}, {
											xtype : 'hiddenfield',
											name : 'needMiddleCheck',
											id : 'needMiddleCheck_' + me.id
										}, {
											xtype : 'hiddenfield',
											name : 'equipName',
											value : me.equipName,
											id : 'equipName_' + me.id
										}]
							});
				} else {
					formItems.push({
								items : [{
											xtype : 'label',
											text : equipStatusText
										}]
							});
				}
				me.items = {
					xtype : 'form',
					layout : 'hbox',
					itemId : 'equipForm' + me.equipId,
					padding : '25 10 10 5',
					defaults : {
						xtype : 'panel',
						layout : 'vbox',
						defaults : {
							xtype : 'displayfield',
							labelAlign : 'right',
							labelWidth : 120
						}
					},
					items : formItems
				};
				if (me.equipId != null) {
					me.itemId = 'equipView' + me.equipId;
				}
				me.callParent(arguments); // ------------call父类--------------
			}
		});
