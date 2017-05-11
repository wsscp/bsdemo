Ext.define('bsmes.view.ProcessQcEdit', {
			extend : 'Ext.window.Window',
			alias : 'widget.processQcEdit',
			width : 650,
			layout : 'fit',
			title : '产品QA检测项维护',
			iconCls : 'feed-edit',
			modal : true,
			plain : true,
			initComponent : function(){
				var me = this;
				Ext.apply(me, {
							buttons : [{
										itemId : 'ok',
										text : Oit.btn.ok
									}, {
										itemId : 'cancel',
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});
				me.callParent(arguments);
			},
			items : [{
						xtype : 'form',
						bodyPadding : '12 10 10',
						defaults : {
							labelAlign : 'right',
							width : 650
						},
						items : [{
									xtype : 'container',
									layout : 'hbox',
									defaultType : 'textfield',
									defaults : {
										labelWidth : 100,
										width : 300
									},
									margin : '0 0 10 0',
									items : [{
												fieldLabel : '检测项CODE',
												name : 'checkItemCode',
												xtype : 'displayfield'
											}, {
												fieldLabel : '检测项名称',
												margin : '0 0 0 10',
												name : 'checkItemName'
											}]
								}, {
									xtype : 'container',
									layout : 'hbox',
									defaultType : 'textfield',
									defaults : {
										labelWidth : 100,
										width : 300
									},
									margin : '0 0 10 0',
									items : [{
												fieldLabel : '检测频率',
												name : 'frequence'
											}, {
												fieldLabel : '参数目标值',
												margin : '0 0 0 10',
												name : 'itemTargetValue'
											}]
								}, {
									xtype : 'container',
									layout : 'hbox',
									defaultType : 'textfield',
									defaults : {
										labelWidth : 100,
										width : 300
									},
									margin : '0 0 10 0',
									items : [{
												fieldLabel : '参数上限',
												name : 'itemMaxValue'
											}, {
												fieldLabel : '参数下限',
												margin : '0 0 0 10',
												name : 'itemMinValue'
											}]
								}, {
									xtype : 'container',
									layout : 'hbox',
									defaultType : 'textfield',
									defaults : {
										labelWidth : 100,
										width : 300
									},
									margin : '0 0 10 0',
									items : [{
												fieldLabel : '参数数据类型',
												xtype : 'radiogroup',
												columns : 4,
												items : [{
															boxLabel : '字符',
															name : 'dataType',
															inputValue : 'STRING'
														}, {
															boxLabel : '数字',
															name : 'dataType',
															inputValue : 'DOUBLE'
														}, {
															boxLabel : '布尔',
															name : 'dataType',
															inputValue : 'BOOLEAN'
														}]
											}, {
												fieldLabel : '参数单位',
												margin : '0 0 0 10',
												name : 'dataUnit'
											}]
								}, {
									xtype : 'container',
									layout : 'hbox',
									defaultType : 'textfield',
									defaults : {
										labelWidth : 100,
										width : 300
									},
									margin : '0 0 10 0',
									items : [{
												fieldLabel : '是否有附件',
												xtype : 'radiogroup',
												columns : 4,
												items : [{
															boxLabel : '是',
															name : 'hasPic',
															inputValue : '1'
														}, {
															boxLabel : '否',
															name : 'hasPic',
															inputValue : '0'
														}]
											}, {
												fieldLabel : '是否在终端显示',
												xtype : 'radiogroup',
												margin : '0 0 0 10',
												columns : 4,
												items : [{
															boxLabel : '是',
															name : 'needShow',
															inputValue : '1'
														}, {
															boxLabel : '否',
															name : 'needShow',
															inputValue : '0'
														}]
											}]
								}, {
									xtype : 'container',
									layout : 'hbox',
									defaultType : 'textfield',
									defaults : {
										labelWidth : 100,
										width : 300
									},
									margin : '0 0 10 0',
									items : [{
												fieldLabel : '是否重点显示',
												xtype : 'radiogroup',
												columns : 4,
												items : [{
															boxLabel : '是',
															name : 'emphShow',
															inputValue : '1'
														}, {
															boxLabel : '否',
															name : 'emphShow',
															inputValue : '0'
														}]
											}]
								},
								// {
								// xtype : 'container',
								// layout : 'hbox',
								// defaultType : 'textfield',
								// defaults : {
								// labelWidth : 100,
								// width : 300
								// ,
								// },
								// margin : '0 0 10 0',
								// items : [{
								// fieldLabel : '是否要首检',
								// xtype : 'radiogroup',
								// columns : 4,
								// items : [{
								// boxLabel : '是',
								// name : 'needFirstCheck',
								// inputValue : '1'
								// }, {
								// boxLabel : '否',
								// name : 'needFirstCheck',
								// inputValue : '0'
								// }]
								// }, {
								// fieldLabel : '是否要中检',
								// xtype : 'radiogroup',
								// margin : '0 0 0 10',
								// columns : 4,
								// items : [{
								// boxLabel : '是',
								// name : 'needMiddleCheck',
								// inputValue : '1'
								// }, {
								// boxLabel : '否',
								// name : 'needMiddleCheck',
								// inputValue : '0'
								// }]
								// }]
								// }, {
								// xtype : 'container',
								// layout : 'hbox',
								// defaults : {
								// labelWidth : 100,
								// width : 300
								// ,
								// },
								// margin : '0 0 10 0',
								// items : [{
								// fieldLabel : '是否要上车检',
								// xtype : 'radiogroup',
								// columns : 4,
								// items : [{
								// boxLabel : '是',
								// name : 'needInCheck',
								// inputValue : '1'
								// }, {
								// boxLabel : '否',
								// name : 'needInCheck',
								// inputValue : '0'
								// }]
								// }, {
								// fieldLabel : '是否要下车检',
								// xtype : 'radiogroup',
								// margin : '0 0 0 10',
								// columns : 4,
								// items : [{
								// boxLabel : '是',
								// name : 'needOutCheck',
								// inputValue : '1'
								// }, {
								// boxLabel : '否',
								// name : 'needOutCheck',
								// inputValue : '0'
								// }]
								// }]
								// },
								{
									xtype : 'container',
									layout : 'hbox',
									defaultType : 'textfield',
									defaults : {
										labelWidth : 100,
										width : 300
									},
									margin : '0 0 10 0',
									items : [{
												fieldLabel : '是否需要数采',
												xtype : 'radiogroup',
												columns : 4,
												items : [{
															boxLabel : '是',
															name : 'needDa',
															inputValue : 'true'
														}, {
															boxLabel : '否',
															name : 'needDa',
															inputValue : 'false',
															checked : true
														}]
											}, {
												fieldLabel : '是否需要下发',
												xtype : 'radiogroup',
												margin : '0 0 0 10',
												columns : 4,
												items : [{
															boxLabel : '是',
															name : 'needIs',
															inputValue : 'true'
														}, {
															boxLabel : '否',
															name : 'needIs',
															inputValue : 'false',
															checked : true
														}]
											}]
								}, {
									xtype : 'container',
									layout : 'hbox',
									defaultType : 'textfield',
									defaults : {
										labelWidth : 100,
										width : 300
									},
									margin : '0 0 10 0',
									items : [{
												fieldLabel : '超差是否报警',
												xtype : 'radiogroup',
												columns : 4,
												items : [{
															boxLabel : '是',
															name : 'needAlarm',
															inputValue : '1'
														}, {
															boxLabel : '否',
															name : 'needAlarm',
															inputValue : '0'
														}]
											}, {
												fieldLabel : '值域',
												margin : '0 0 0 10',
												name : 'valueDomain'
											}]
								}]
					}]
		}
);