/**
 * 修改生产单的设备
 */
Ext.define('bsmes.view.ChangeWorkOrderEquipWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.changeWorkOrderEquipWin',
	title : '调整生产线',
	width : document.body.scrollWidth - 150,
	height : document.body.scrollHeight - 100,
	autoScroll : true,
	layout : 'fit',
	modal : true,
	plain : true,
	workOrderNo : null,

	initComponent : function() {
		var me = this;
		me.items = [{
					xtype : 'form',
					bodyPadding : '20 10',
					width : document.body.scrollWidth - 150,
					height : document.body.scrollHeight - 100,
					autoScroll : true,
					items : [{
								// fieldLabel : '选择设备',
								name : 'equipList',
								xtype : 'checkboxgroup',
								columns : 2,
								items : []
							}]
				}];

		var store = Ext.create('Ext.data.Store', {
					fields : [{
								name : 'EQUIPNAME',
								convert : function(value, record) {
									if (record.get('EQUIPALIAS') != '') {
										return record.get('EQUIPALIAS') + '-' + value + '[' + record.get('EQUIPCODE')
												+ ']';
									}
									return value + '[' + record.get('EQUIPCODE') + ']';
								}
							}, 'EQUIPCODE', 'EQUIPALIAS', 'CODE'],
					proxy : {
						type : 'rest',
						url : 'getEquipForChangeOrderEquip',
						extraParams : {
							workOrderNo : me.workOrderNo
						}
					}
				});
		store.load({
					callback : function(records, options, success) {
						var checkgroup = Ext.ComponentQuery.query('changeWorkOrderEquipWin checkboxgroup')[0];;
						for (var i = 0; i < store.getCount(); i++) {
							var record = store.getAt(i);
							checkgroup.items.add(new Ext.form.Checkbox({
										boxLabel : record.get('EQUIPNAME'),
										inputValue : record.get('EQUIPCODE'),
										checked : (record.get('CODE') != ''),
										listeners : {
											change : function(my, newValue, oldValue, eOpts) {
												Ext.Ajax.request({
															url : '../../pla/handSchedule/getTaskStatueInEquip',
															params : {
																workOrderNo : me.workOrderNo
															},
															success : function(response) {
																var result = Ext.decode(response.responseText);
																if (result.length != 0) {
																	for (var i = 0; i < result.length; i++) {
																		if (my.inputValue == result[i].equipCode) {
																			Ext.Msg.alert('警告', '该生产单已在选取设备上生产中，请勿取消');
																			my.setValue(true);
																			break;
																		}
																	}
																}
															}
														});
											}
										}
									}));
						}
						checkgroup.doLayout();
					}
				});

		Ext.apply(me, {
					buttons : [{
						itemId : 'ok',
						text : Oit.btn.ok,
						handler : function() {
							Ext.Msg.wait('数据处理中，请稍后...', '提示');
							var equipList = this.up('window').down('form checkboxgroup[name="equipList"]').getChecked();
							var equipName = [], equipCode = [];
							Ext.Array.each(equipList, function(item) {
										equipName.push(item.boxLabel);
										equipCode.push(item.inputValue);
									});

							Ext.Ajax.request({
										url : 'changeWorkEquipSub',
										method : 'POST',
										params : {
											equipNameArrayStr : equipName.join(),
											equipCodeArrayStr : equipCode.join(),
											workOrderNo : me.workOrderNo
										},
										success : function(response) {
											Ext.Msg.hide(); // 隐藏进度条
											var section = Ext.fly('processInfo').getAttribute('section');
											var changeOrderGridStore;
											if (section == "绝缘") {
												changeOrderGridStore = Ext.ComponentQuery.query('#recentOrderGrid')[0]
														.getStore();
											} else {
												changeOrderGridStore = Ext.ComponentQuery.query('#recentOrderGrid')[0]
														.getStore();
											}
											changeOrderGridStore.load({
														params : {
															equipCode : Ext.fly('equipInfo').getAttribute('code'),
															status : 'TO_DO',
															section : section
														}
													});
											me.close();
											Ext.Msg.alert(Oit.msg.PROMPT, '保存成功！');
										},
										failure : function(response, action) {
											Ext.Msg.hide(); // 隐藏进度条
											Ext.Msg.alert(Oit.msg.PROMPT, '保存失败！');
										}
									});

						}
					}, {
						itemId : 'cancel',
						text : Oit.btn.cancel,
						scope : me,
						handler : me.close
					}]
				});

		this.callParent(arguments);
	}
});
