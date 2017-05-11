Ext.define('bsmes.view.ApplyMaterials', {
			extend : 'Ext.window.Window',
			alias : 'widget.applyMaterials',
			minWidth : 500,
			width : document.body.scrollWidth * 0.5,
			maxHeight : document.body.scrollHeight * 0.8,
			layout : 'fit',
			modal : true,
			plain : true,
			equipCode : null,
			store : null,
			initComponent : function() {
				var me = this;
				Ext.apply(me, {
							buttons : [{
										itemId : 'ok',
										text : '提交',
//										formBind: true, //only enabled once the form is valid
//								        disabled: true,
										handler : function(){
											var form = me.down('form').getForm();
											if(form.isValid()){
												 form.submit({
													url : 'applyMaterials',
								                    success: function(form, action) {
								                       var result = Ext.decode(action.response.responseText);
								                       Ext.Msg.alert('要料成功', result.message);
								                       me.close();
								                    },
								                    failure: function(form, action) {
								                    	var result = Ext.decode(action.response.responseText);
								                        Ext.Msg.alert('要料失败', result.message);
								                    }
									              });
											}
										}
									}, {
										itemId : 'cancel',
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});

				me.items = [{
							xtype : 'form',
							bodyPadding : 10,
							// 将会通过 AJAX 请求提交到此URL
							// url : 'save-form.php',
							// 表单域 Fields 将被竖直排列, 占满整个宽度
							layout : 'form',
							autoScroll : true,
							defaults : {
								//labelAlign:'right',
								msgTarget : 'under',
								anchor : '100%'
							},
							fieldDefaults : { // 设置field的样式
								msgTarget : 'side', // 错误信息提示位置
								labelWidth : 100, // 设置label的宽度
								labelStyle : 'font-size:20px; margin-top:5px;'
							},
							items : [{
								layout : 'form',
								items : [{
									fieldLabel : '机台编码',
									xtype : 'textfield',
									name : 'equipCode',
									fieldCls : 'x-panel-body-default',
									fieldStyle : 'font-size:20px;',
									height : 30,
									readOnly : true,
									value : me.equipCode
								}]
							},{
								layout : 'form',
								items : [{
									fieldLabel : '要料人',
									xtype : 'textfield',
									name : 'userCode',
									fieldCls : 'x-panel-body-default',
									fieldStyle : 'font-size:20px;',
									height : 30,
									allowBlank: false,
									plugins : {
										ptype : 'virtualKeyBoard'
									}
								}]
							},{
								layout : 'column',
								items : [{
									columnWidth : 0.45,
									layout: 'form',
									items : [{
										fieldLabel : '物料名称',
										xtype : 'combobox',
										name : 'material',
										fieldCls : 'x-panel-body-default',
										fieldStyle : 'font-size:20px;',
										height : 30,
										editable : false,
										queryMode: 'local',
									    displayField: 'material',
									    valueField: 'material',
									    store : me.store
									}]
								},{
									columnWidth : 0.4,
									layout: 'form',
									items : [{
										fieldLabel : '要料量(KG)',
										xtype : 'textfield',
										name : 'materialNum',
										allowBlank: false,
										fieldCls : 'x-panel-body-default',
										fieldStyle : 'font-size:20px;',
										height : 30,
										labelWidth : 120,
										plugins : {
											ptype : 'virtualKeyBoard'
										}
									}]
								},{
									columnWidth : 0.075,
									layout: 'form',
									items : [{
										xtype : 'button',
//										text : Oit.btn.add,
										iconCls : 'icon_add',
										width : 35,
										height : 30,
										style:'margin-left:10px;padding:5px 9px 5px;',
										handler : function(){
											var form = this.up('form');
											var item = {
													layout : 'column',
													items : [{
														columnWidth : 0.45,
														layout: 'form',
														items : [{
															fieldLabel : '物料名称',
															xtype : 'combobox',
															name : 'material',
															fieldCls : 'x-panel-body-default',
															fieldStyle : 'font-size:20px;',
															height : 30,
															editable : false,
															queryMode: 'local',
														    displayField: 'material',
														    valueField: 'material',
														    store : me.store
														}]
													},{
														columnWidth : 0.4,
														layout: 'form',
														items : [{
															fieldLabel : '要料量(KG)',
															xtype : 'textfield',
															name : 'materialNum',
															fieldCls : 'x-panel-body-default',
															fieldStyle : 'font-size:20px;',
															height : 30,
															labelWidth : 120,
															allowBlank: false,
															plugins : {
																ptype : 'virtualKeyBoard'
															}
														}]
													}]
												}
											form.add(item);
											form.doLayout();
										}
									}]
								},{
									columnWidth : 0.075,
									layout: 'form',
									items : [{
										xtype : 'button',
										iconCls : 'icon_remove',
										width : 35,
										height : 30,
										style:'margin-left:5px;padding:5px 9px 5px;',
										handler : function(){
											var form = this.up('form');
											var i = form.items.length;
											if(i>3){
												form.remove(--i,true);
												form.doLayout();
											}
										}
									}]
								}]
							}]
						}],

				this.callParent(arguments);
			}
		});
