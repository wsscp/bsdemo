Ext.define('bsmes.view.ChangeEquipUserCVWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.changeEquipUserCVWindow',
			width : 500,
			height : 350,
			layout : 'fit',
			modal : true,
			title : Oit.msg.wip.title.valid,
			initComponent : function() {
				var me = this;
				me.items = [{
							xtype : 'form',
							bodyPadding : '50 0 0 30',
							defaultType : 'textfield',
							url : 'validBZZPermissions',
							method : 'POST',
							fieldDefaults : { // 设置field的样式
								msgTarget : 'side', // 错误信息提示位置
								labelWidth : 100, // 设置label的宽度
								labelCls : 'x-boxlabel-size-20',
								fieldCls : 'x-panel-body-default',
								height : 30,
								width : 330
							},
							items : [{
										xtype : 'textfield',
										fieldLabel : Oit.msg.wip.workOrder.userCode,
										name : 'userCode',
										margin : '30 30 30 30',
										allowBlank : false,
										plugins : {
											ptype : 'virtualKeyBoard'
										},
										listeners : {
											specialKey : function(field, e) {
												if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
													Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
												}
											}
										}
									}, {
										xtype : 'textfield',
										inputType : 'password',
										fieldLabel : Oit.msg.wip.workOrder.password,
										name : 'password',
										margin : '30 30 30 30',
										plugins : {
											ptype : 'virtualKeyBoard'
										},
										allowBlank : false
									}, {
										xtype : 'hiddenfield',
										name : 'equipCode'
									}, {
										xtype : 'hiddenfield',
										name : 'workOrderNo'
									}]
						}];

				Ext.apply(me, {
							buttons : [{
								itemId : 'ok',
								text : Oit.btn.ok,
								listeners : {
									click : function() {
										var form = me.down('form').getForm();
										var workOrderNo = form.findField('workOrderNo').getValue();
										if (form.isValid()) {
											var userCode = form.findField('userCode').getValue();
											var password = $.md5(form.findField('password').getValue());
											form.findField('password').setValue(password);
											form.submit({
														success : function(form1, action) {
															me.close();
															var win = Ext.create('bsmes.view.ChangeWorkOrderEquipWin',
																	{
																		workOrderNo : workOrderNo
																	});
															win.show();
														},
														failure : function(form, action) {
															var result = Ext.decode(action.response.responseText);
															Ext.Msg.alert(Oit.msg.PROMPT, result.message);
														}
													})
										}
									}
								}
							}, '->', {
								itemId : 'cancel',
								text : Oit.btn.cancel,
								scope : me,
								handler : me.close
							}]
						});

				this.callParent(arguments);
			}
		});