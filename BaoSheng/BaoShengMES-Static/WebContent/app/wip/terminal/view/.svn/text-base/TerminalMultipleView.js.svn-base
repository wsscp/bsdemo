//显示当前终端登录员工
var userViewArray = new Array();
var workUsers = Ext.get("workUsers").getAttribute("value");
if (workUsers != null && workUsers != '') {
	var array = workUsers.split(",");
	Ext.Array.each(array, function(userName, i) {
				userViewArray.push({
							xtype : 'label',
							text : userName,
							margin : '10 10 0 20'
						});
			});
}
Ext.define("bsmes.view.TerminalMultipleView", {
			extend : 'Ext.panel.Panel',
			alias : 'widget.terminalMultipleView',
			requires : ['bsmes.view.EquipView', 'bsmes.view.ReportView'],
			layout : 'fit',
			overflowY : 'auto',
			equipInfos : {}, // 页面设备缓存，报功用
			initComponent : function() {
				var me = this;
				var maxClient = Ext.get('maxClient').getAttribute("value");
				var leftClients = new Array();
				var rightClients = new Array();
				for (var i = 0; i < maxClient; i++) {
					if (i % 2 == 0) {
						leftClients.push({
							xtype : 'equipView',
							equipStatus : Ext.fly('equip' + i).getAttribute('status'),
							equipCode : Ext.fly('equip' + i).getAttribute('equipCode'),
							equipName : Ext.fly('equip' + i).getAttribute('equipName'),
							equipAlias : Ext.fly('equip' + i).getAttribute('equipAlias'),
							equipId : Ext.fly('equip' + i).getAttribute('eqipId'),
							section : Ext.fly('equip' + i).getAttribute('section')
								// id:'equip'+i
							});
					} else {
						rightClients.push({
							xtype : 'equipView',
							equipStatus : Ext.fly('equip' + i).getAttribute('status'),
							equipCode : Ext.fly('equip' + i).getAttribute('equipCode'),
							equipName : Ext.fly('equip' + i).getAttribute('equipName'),
							equipAlias : Ext.fly('equip' + i).getAttribute('equipAlias'),
							equipId : Ext.fly('equip' + i).getAttribute('eqipId'),
							section : Ext.fly('equip' + i).getAttribute('section')
								//id:'equip'+i
							});
					}
				}
				if (maxClient % 2 != 0) {
					rightClients.push({
						xtype : 'equipView',
						equipStatus : null,
						equipCode : null,
						equipName : null,
						equipId : null
							// id:'equip'+maxClient
						});
				}

				me.items = [{
							layout : 'hbox',
							height : document.body.scrollHeight,
							xtype : 'container',
							overflowY : 'auto',
							items : [{
										id : 'leftContainer',
										layout : 'vbox',
										border : '0',
										defaults : {
											border : 0
										},
										flex : 3,
										items : leftClients
									}, {
										xtype : 'component',
										width : 1
									}, {
										id : 'rightContainer',
										layout : 'vbox',
										border : 0,
										defaults : {
											border : 0
										},
										flex : 3,
										items : rightClients
									}, {
										xtype : 'component',
										width : 1
									}, {
										xytpe : 'panel',
										layout : 'vbox',
										//border: 0,
										flex : 1,
										items : [{
													height : document.body.scrollHeight - 10,
													width : (document.body.scrollWidth - 3) / 7,
													id : 'loginUserList',
													dockedItems : [{
																xtype : 'toolbar',
																dock : 'top',
																layout : 'vbox',
																items : [{
																			xtype : 'button',
																			text : Oit.btn.close,
																			itemId : 'closewindow',
																			width : (document.body.scrollWidth - 3) / 7 - 20,
																			height : 60
																		}, {
																			xtype : 'button',
																			text : Oit.msg.wip.terminal.btn.sign,
																			itemId : 'sign',
																			margin : '10 0',
																			width : (document.body.scrollWidth - 3) / 7 - 20,
																			height : 60
																		}/*
																			 * , {
																			 * xtype:
																			 * 'button',
																			 * text:
																			 * '处理暂停<br>生产单',
																			 * itemId:
																			 * 'startWorkOrderWin',
																			 * width:(document.body.scrollWidth-3)/7 -
																			 * 20,
																			 * margin:
																			 * '0 0
																			 * 10
																			 * 0',
																			 * height:60 }
																			 */, {
																			xtype : 'button',
																			text : Oit.msg.wip.terminal.btn.handleAlarm,
																			itemId : 'handleEquipAlarm',
																			width : (document.body.scrollWidth - 3) / 7 - 20,
																			margin : '10 0',
																			height : 60
																		}, {
																			xtype : 'button',
																			itemId : 'multiReportDetail',
																			text : '报工信息',
																			height : 60,
																			width : (document.body.scrollWidth - 3) / 7 - 20
																		}]
															}],
													layout : 'vbox',
													items : userViewArray
												}]
									}]
						}]
				me.callParent(arguments);
			}
		});