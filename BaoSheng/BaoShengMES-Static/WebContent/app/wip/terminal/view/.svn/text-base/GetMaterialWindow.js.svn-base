Ext.define("bsmes.view.GetMaterialWindow", {
			extend : 'Ext.window.Window',
			alias : 'widget.getMaterialWindow',
			id : 'getMaterialWindow',
			layout : 'fit',
			//width : document.body.scrollWidth - 300,
			//height : document.body.scrollHeight - 100,
			modal : true,
			plain : true,
			materialMap : new Ext.util.HashMap(),
			planLengthMap : new Ext.util.HashMap(),
			title : '要料清单',
			workOrderNo : null,
			userCode : null,
			userName : null,
			groupMap : null,
			initComponent : function() {
				var me = this;
				var rowItems = [{
										fieldLabel : '<font size="4.5px">生产单号</font>',
										xtype : 'displayfield',
										//labelWidth : 80,
										//margin : '10 0 0 0',
										name : 'workOrderNo',
										value : me.workOrderNo
									}, {
										fieldLabel : '<font size="4.5px">要料人</font>',
										xtype : 'displayfield',
										//labelWidth : 60,
										//margin : '10 0 0 0',
										name : me.userCode,
										value : me.userName
									}];
				me.materialMap.clear();
				me.planLengthMap.clear();
				Ext.Array.each(me.groupMap, function(record, i) {
							me.materialMap.add(record.matCode, record.name);
							me.planLengthMap.add(record.matCode, record.planAmount);
							var json = Ext.decode(record.inAttrDesc);
							var matDesc = '';
							var a = false;
							if (json.kuandu) { // 宽度
								matDesc += '宽度:' + json.kuandu + '<br/>';
								a = true;
							}
							if (json.color) { // 宽度
								matDesc += '<font color="red">颜色:' + json.color + '</font><br/>'
								a = true;
							}
							if (json.houdu) { // 厚度
								matDesc += '厚度:' + json.houdu + '<br/>';
								a = true;
							}
							if (json.caizhi) { // 材质
								matDesc += '材质:' + json.caizhi + '<br/>';
								a = true;
							}
							if (json.chicun) { // 尺寸
								matDesc += '尺寸:' + json.chicun + '<br/>';
								a = true;
							}
							if (json.guige) { // 规格
								matDesc += '规格:' + json.guige + '<br/>';
								a = true;
							}
							if (json.dansizhijing) { // 单丝直径
								matDesc += '单丝直径:' + json.dansizhijing + '<br/>';
								a = true;
							}
							if (a) {
								var name = record.name + '(' + matDesc + ')' + '(KG)';
							} else {
								var name = record.name + '(KG)';
							}
							me.materialMap.add(record.matCode, name);
							var matItems = {
								xtype : 'textfield',
								fieldLabel : name,
								id : record.matCode,
								name : record.matCode,
								//labelWidth : 200,
								// height : 30,
								emptyText : '计划用料：' + record.planAmount.substr(0, record.planAmount.indexOf(".") + 3),
								//readOnly : false,
								//margin : '5 0 5 10',
								plugins : {
									ptype : 'virtualKeyBoard'
								}
							};
							rowItems.push(matItems);
						});
				me.items = [{
							xtype : 'form',
							frame : true,
							//width : 340,
//							bodyPadding : 5,
							padding : 20,
							fieldDefaults : {
								labelAlign : 'left',
								//labelWidth : 90,
								anchor : '100%'
							},
							items : rowItems
						}];

				Ext.apply(me, {
							buttons : [{
								itemId : 'ok',
								text : Oit.btn.ok,
								handler : function() {
									var form = Ext.ComponentQuery.query('#getMaterialWindow')[0].down('form').getForm();
									var isAllNull = true;
									for (var i in form.getValues()) {
										if (form.findField(i).getValue() != '') {
											isAllNull = false;
										}
									}
									if (isAllNull) {
										Ext.Msg.alert(Oit.msg.PROMPT, '要料量均为空，请填写要料量！');
										return;
									}
									var matMap = me.materialMap.map;
									var matNameArr = new Array();
									var myIndex = 0;
									var isSubmit = false;
									var data = [];
									for (var key in matMap) {
										var inputValue = form.findField(key).getValue();
										if (inputValue == '') {
											continue;
										}
										if (Number(inputValue) > Number(me.planLengthMap.get(key))) {
											matNameArr[myIndex] = me.materialMap.get(key);
											data.push({
												matCode : key,
												userCode : me.userCode,
												equipCode : Ext.fly('equipInfo').getAttribute('code'),
												workOrderNo : me.workOrderNo,
												yaoLiaoQuantity : inputValue,
												planAmount : me.planLengthMap.get(key)
													//								jiTaiQuantity : inputValueJT
												});
										} else {
											data.push({
												matCode : key,
												userCode : me.userCode,
												equipCode : Ext.fly('equipInfo').getAttribute('code'),
												workOrderNo : me.workOrderNo,
												yaoLiaoQuantity : inputValue,
												planAmount : me.planLengthMap.get(key)
													//								jiTaiQuantity : inputValueJT
												});
										}
									}
									if (matNameArr.length > 0) {
										Ext.Msg.show({
													title : '警告',
													msg : '物料:' + matNameArr.join(',') + '的要料量超出计划用量,是否继续要料?',
													buttons : Ext.Msg.YESNO,
													fn : function callback(btn, text) {
														if (btn == "yes") {
															form.submit({
																		url : 'saveSuppMaterialInfo',
																		params : {
																			yaoLiaoInfo : Ext.encode(data),
																			workOrderNo : me.workOrderNo
																		},
																		success : function(form, action) {
																			Ext.Msg.alert(Oit.msg.PROMPT, '要料成功');
																			me.close();
																			Ext.ComponentQuery.query('#recentOrderGrid')[0].getStore()
																					.reload();
																		},
																		failure : function(form, action) {
																			Ext.Msg.alert(Oit.msg.PROMPT, '要料失败');
																		}
																	});
														}
													}
												})
									} else {
										isSubmit = true;
									}
									if (isSubmit) {
										form.submit({
													url : 'saveSuppMaterialInfo',
													params : {
														workOrderNo : me.workOrderNo,
														yaoLiaoInfo : Ext.encode(data)
													},
													success : function(form, action) {
														Ext.Msg.alert(Oit.msg.PROMPT, '要料成功');
														me.close();
														Ext.ComponentQuery.query('#recentOrderGrid')[0].getStore().reload();
													},
													failure : function(form, action) {
														Ext.Msg.alert(Oit.msg.PROMPT, '要料失败');
													}
												});
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
