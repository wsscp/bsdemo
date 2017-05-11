/**
 * 修改生产单的设备
 */
Ext.define('bsmes.view.ChangeWorkOrderEquipWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.changeWorkOrderEquipWindow',
			title : '调整生产线',
			width : document.body.scrollWidth * 0.6,
			height : document.body.scrollHeight * 0.8,
			autoScroll : true,
			layout : 'fit',
			modal : true,
			plain : true,
			workOrderNo : null,
			orderItemIds : null,
			status : null,

			initComponent : function() {
				var me = this;
				me.items = [{
							xtype : 'form',
							bodyPadding : '20 10',
							autoScroll : true,
							items : [{
										// fieldLabel : '选择设备',
										name : 'equipList',
										xtype : 'checkboxgroup',
										columns : 4,
										items : []
									}]
						}];

				var store = Ext.create('Ext.data.Store', {
							fields : ['EQUIPNAME', 'EQUIPCODE', 'EQUIPALIAS', 'CODE'],
							proxy : {
								type : 'rest',
								url : 'handSchedule/getAllCanChooseEquipByProcessCode',
								extraParams : {
									processCode : me.processCode,
									workOrderNo : me.workOrderNo
								}
							}
						});
				store.load({
							callback : function(records, options, success) {
								var checkgroup = Ext.ComponentQuery.query('changeWorkOrderEquipWindow checkboxgroup')[0];;
								for (var i = 0; i < store.getCount(); i++) {
									var record = store.getAt(i);
									checkgroup.items.add(new Ext.form.Checkbox({
												boxLabel : record.get('EQUIPNAME'),
												inputValue : record.get('EQUIPCODE'),
												checked : (record.get('CODE') != ''),
												listeners : {
													change : function(my, newValue, oldValue, eOpts) {
														Ext.Ajax.request({
																	url : 'handSchedule/getTaskStatueInEquip',
																	params : {
																		workOrderNo : me.workOrderNo
																	},
																	success : function(response) {
																		var result = Ext.decode(response.responseText);
																		if (result.length != 0) {
																			for (var i = 0; i < result.length; i++) {
																				if (my.inputValue == result[i].equipCode) {
																					Ext.Msg.alert('警告', '该生产单已在此设备上已有生产记录！，请勿取消');
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
											Ext.Msg.wait(Oit.msg.LOADING, Oit.msg.PROMPT);
											var equipList = this.up('window').down('form checkboxgroup[name="equipList"]').getChecked();
											var equipName = [], equipCode = [];
											Ext.Array.each(equipList, function(item) {
														equipName.push(item.boxLabel);
														equipCode.push(item.inputValue);
													});

											Ext.Ajax.request({
														url : 'handSchedule/changeWorkEquipSub',
														method : 'POST',
														params : {
															equipNameArrayStr : equipName.join(),
															equipCodeArrayStr : equipCode.join(),
															workOrderNo : me.workOrderNo
														},
														success : function(response) {
															Ext.Msg.hide(); // 隐藏进度条
															Ext.ComponentQuery.query("showWorkOrderGrid")[0].getStore().load();
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
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});

				this.callParent(arguments);

			}

		});
