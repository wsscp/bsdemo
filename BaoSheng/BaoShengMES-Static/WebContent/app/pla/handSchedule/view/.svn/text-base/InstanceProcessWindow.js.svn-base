/**
 * 实例工序查看
 */
Ext.define('bsmes.view.InstanceProcessWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.instanceProcessWindow',
	title : Oit.msg.pla.customerOrderItem.button.craftDetail,
	width : document.body.scrollWidth, // * 0.95,
	height : document.body.scrollHeight, // * 0.9,
	modal : true,
	plain : true,
	// layout : 'fit',
	layout : "border",
	spencialParam : {}, // 特殊工艺变更需要提交的参数

	initComponent : function() {
		var me = this;
		var store = Ext.create('Ext.data.TreeStore', {
					root : {
						// text : me.spencialParam.craftsCode,
						expanded : true,
						children : me.spencialParam.processArray
					}
				});

		me.items = [{
					region : "west",
					collapsible : false,
					split : true,
					layout : "fit",
					width : '20%',
					xtype : 'tree',
					store : store,
					rootVisible : false,
					listeners : {
						afterrender : function(self) {
							// console.log(this.getRootNode())
							// console.log(this.getRootNode().findChild())
							var currentNode = self.getRootNode().getChildAt(0);
							// console.log(currentNode.get('id'))
							if (currentNode) {
								self.getSelectionModel().select(currentNode);
								me.treeItemclick(currentNode.get('id'));
							}
						},
						itemclick : function(self, record, item, index, e, eOpts) {
							me.treeItemclick(record.get('id'));
						}
					}
				}, {
					region : "center",
					split : true,
					layout : "fit",
					width : '80%',
					xtype : 'panel',
					layout : 'accordion',
					items : [me.getInOutGrid(), me.getQcGrid()]
				}];

		Ext.apply(me, {
					buttons : ['->', {
								text : Oit.btn.close,
								scope : me,
								handler : me.close
							}]
				});

		this.callParent(arguments);
	},

	// 工序树选择事件
	treeItemclick : function(processId) {
		var me = this;
		me.spencialParam.processId = processId;
		if (processId != 'root') {
			var processInOutGrid = me.down('#inOutGrid');
			processInOutGrid.down('form').getForm().setValues({
						processId : processId
					});
			processInOutGrid.getStore().load();

			var processQcGrid = me.down('#qcGrid');

			processQcGrid.down('form').getForm().setValues({
						processId : processId,
						checkType1 : 'needInCheck'
					});

			// processQcGrid.getStore().getProxy().url = '../pro/processWip/qc/getQcByProcessId';
			processQcGrid.getStore().load();
		}
	},

	getInOutGrid : function() {
		var me = this;
		var grid = Ext.create('Ext.grid.Panel', {
					xtype : 'grid',
					title : '投入产出',
					columnLines : true,
					itemId : 'inOutGrid',
					store : 'ProcessInOutStore',
					columns : [{
								text : '物料编码',
								dataIndex : 'matCode',
								// minWidth : 175,
								hidden : true,
								flex : 3.5,
								renderer : function(value, metaData, record) {
									metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
									metaData.tdAttr = 'data-qtip="' + value + '"';
									return value;
								}
							}, {
								text : '物料名称<br/>物料编码',
								dataIndex : 'matName',
								// minWidth : 150,
								flex : 3.5,
								renderer : function(value, metaData, record) {
									metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
									metaData.tdAttr = 'data-qtip="' + value + '"';
									return value + '<br/>' + record.get('matCode');
								}
							}, {
								text : '投入/产出',
								dataIndex : 'inOrOut',
								// minWidth : 75,
								flex : 1.5,
								renderer : function(value, record) {
									if (value == 'IN') {
										return '投入';
									} else if (value == 'OUT') {
										return '产出';
									}
									return '';
								}
							}, {
								text : '单位投入量',
								dataIndex : 'quantity',
								// minWidth : 100,
								flex : 2,
								renderer : function(value, metaData, record) {
									return value + ' ' + record.get('unit');
								}
							}, {
								text : '颜色',
								dataIndex : 'color',
								// minWidth : 50,
								flex : 1
							}, {
								text : '物料描述',
								dataIndex : 'remark',
								// minWidth : 300,
								flex : 6,
								renderer : function(value, metaData, record) {
									metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
									metaData.tdAttr = 'data-qtip="' + value + '"';
									return value;
								}
							}, {
								text : '单位投入用量计算公式',
								hidden : true,
								dataIndex : 'quantityFormula',
								flex : 4
							}, {
								text : '用量单位',
								hidden : true,
								dataIndex : 'unit',
								flex : 1.8
							}, {
								text : '用法',
								hidden : true,
								dataIndex : 'useMethod',
								flex : 2.4
							}],
					tbar : [{
								text : '添加投入产出',
								handler : function() {
									me.showAddWin('INOUT', this);
								}
							}, {
								text : '修改投入产出',
								handler : function() {
									me.showEditWin('INOUT', this);
								}
							}, {
								text : '物料属性',
								handler : function() {
									me.showPropWin('PROP');
								}
							}, {
								xtype : 'form',
								items : [{
											xtype : 'hiddenfield',
											name : 'processId',
											value : me.spencialParam.processId
										}]
							}]
				});
		grid.getStore().on('beforeload', function(store, options) {
					var searchForm = grid.down('form');
					if (searchForm) {
						var params = searchForm.form.getValues();
						if (options.params) {
							Ext.apply(options.params, params);
						} else {
							options.params = params
						}
					}
				});
		return grid;
	},

	getQcGrid : function() {
		var me = this;
		var grid = Ext.create('Ext.grid.Panel', {
					xtype : 'grid',
					title : '质检参数',
					columnLines : true,
					itemId : 'qcGrid',
					store : 'ProcessQcStore',
					columns : [{
								text : '检测项编码',
								dataIndex : 'checkItemCode',
								minWidth : 100,
								hidden : true,
								flex : 2
							}, {
								text : '检测项名称<br/>检测项CODE',
								dataIndex : 'checkItemName',
								minWidth : 150,
								flex : 3,
								renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
									return value + '<br/>' + record.get('checkItemCode');
								}
							}, {
								text : '检测频率',
								dataIndex : 'frequence',
								minWidth : 100,
								flex : 1
							}, {
								text : '目标值',
								dataIndex : 'itemTargetValue',
								minWidth : 50,
								flex : 1
							}, {
								text : '上限',
								dataIndex : 'itemMaxValue',
								minWidth : 50,
								flex : 1
							}, {
								text : '上上限',
								dataIndex : 'itemUpValue',
								hidden : true,
								minWidth : 50,
								flex : 1
							}, {
								text : '下限',
								dataIndex : 'itemMinValue',
								minWidth : 50,
								flex : 1
							}, {
								text : '下下限',
								dataIndex : 'itemDownValue',
								hidden : true,
								minWidth : 50,
								flex : 1
							}, {
								text : '数据类型',
								dataIndex : 'dataType',
								minWidth : 50,
								flex : 1,
								renderer : function(value) {
									if (value == 'STRING') {
										return '字符';
									} else if (value == 'DOUBLE') {
										return '数字';
									} else if (value == 'BOOLEAN') {
										return '布尔';
									} else {
										return '';
									}
								}
							}, {
								text : '参数单位',
								dataIndex : 'dataUnit',
								minWidth : 50,
								flex : 1
							}, {
								text : '是否有附件',
								dataIndex : 'hasPic',
								minWidth : 50,
								hidden : true,
								flex : 1,
								renderer : function(value) {
									if (value == '1') {
										return '是';
									} else if (value == '0') {
										return '否';
									} else {
										return '';
									}
								}
							}, {
								text : '是否在<br/>终端显示',
								dataIndex : 'needShow',
								minWidth : 50,
								flex : 1,
								renderer : function(value) {
									if (value == '1') {
										return '是';
									} else if (value == '0') {
										return '否';
									} else {
										return '';
									}
								}

							}, {
								text : '是否重点显示',
								dataIndex : 'emphShow',
								minWidth : 50,
								hidden : true,
								flex : 1,
								renderer : function(value) {
									if (value == '1') {
										return '是';
									} else if (value == '0') {
										return '否';
									} else {
										return '';
									}
								}
							}, {
								text : '是否需要数采',
								dataIndex : 'needDa',
								minWidth : 50,
								hidden : true,
								flex : 1,
								renderer : function(value) {
									if (value == true) {
										return '是';
									} else {
										return '否';
									}
								}
							}, {
								text : '是否需要下发',
								dataIndex : 'needIs',
								minWidth : 50,
								hidden : true,
								flex : 1,
								renderer : function(value) {
									if (value == true) {
										return '是';
									} else {
										return '否';
									}
								}
							}, {
								text : '超差是否报警',
								dataIndex : 'needAlarm',
								minWidth : 50,
								hidden : true,
								flex : 1,
								renderer : function(value) {
									if (value == '1') {
										return '是';
									} else if (value == '0') {
										return '否';
									} else {
										return '';
									}
								}
							}, {
								text : '值域',
								dataIndex : 'valueDomain',
								minWidth : 50,
								flex : 1
							}],
					// 查询栏
					tbar : [{
								text : '新增质量参数',
								handler : function() {
									me.showAddWin('QC', this);
								}
							}, {
								text : '修改质量参数',
								handler : function() {
									me.showEditWin('QC', this);
								}
							}, {
								xtype : 'form',
								items : [{
											xtype : 'radiogroup',
											id : 'checkTypeValue',
											width : 280,
											items : [{
														boxLabel : '上车检',
														name : 'checkType',
														inputValue : 'needInCheck',
														checked : true
													}, {
														boxLabel : '中检',
														name : 'checkType',
														inputValue : 'needMiddleCheck'
													}, {
														boxLabel : '下车检',
														name : 'checkType',
														inputValue : 'needOutCheck'
													}, {
														boxLabel : '全部',
														name : 'checkType',
														inputValue : '',
														hidden : true
														// checked :
													// true
												}],
											listeners : {
												change : function(obj, newValue, oldValue, eOpts) {
													var store = this.up('grid').getStore();
													store.load({
																params : {
																	processId : me.spencialParam.processId,
																	checkType1 : newValue.checkType
																}
															});
												}
											}
										}, {
											xtype : 'hiddenfield',
											name : 'processId',
											value : me.spencialParam.processId
										}]
							}]
				});
		grid.getStore().on('beforeload', function(store, options) {
					// var checkType = grid.down('radiogroup').getValue().checkType;
		         	var checkType = Ext.getCmp('checkTypeValue').getValue()['checkType'];
					var params = {
						processId : me.spencialParam.processId,
						checkType1 : checkType
					}
					options.params = params;
				});
		return grid;
	},

	showPropWin : function() {
		var me = this;
		var grid = me.down('#inOutGrid');
		var selection = grid.getSelectionModel().getSelection();
		if (selection && selection.length == 1) {
			var record = selection[0];
			var win = me.getMatPropWin(record.get('id')); // processInOut的ID
			if (win)
				win.show();

			// 特殊工艺变更要保存变更记录：提交数据
			me.spencialParam.matCode = record.get('matCode');
			me.spencialParam.matName = record.get('matName');
			me.spencialParam.inOrOut = record.get('inOrOut');
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条记录！');
		}
	},

	getMatPropWin : function(id) {
		var me = this;
		var win = Ext.create('Ext.window.Window', {
					title : ' 物料属性',
					width : document.body.scrollWidth - 300,
					height : document.body.scrollHeight - 200,
					modal : true,
					plain : true,
					layout : 'fit',
					items : [{
								xtype : 'grid',
								columnLines : true,
								store : 'ProcessMatPropStore',
								columns : [{
											text : '属性名称',
											dataIndex : 'propName',
											// minWidth : 100,
											flex : 2
										}, {
											text : '属性值',
											dataIndex : 'propTargetValue',
											// minWidth : 100,
											flex : 2
										}],
								// 查询栏
								tbar : [{
											text : '新增属性',
											hidden : true,
											handler : function() {
												me.showAddWin('PROP', this);
											}
										}, {
											text : '修改属性',
											handler : function() {
												me.showEditWin('PROP', this);
											}
										}]
							}],
					buttons : ['->', {
								text : Oit.btn.close,
								handler : function() {
									this.up('window').close();
								}
							}]
				});
		win.down('grid').getStore().load({
					params : {
						inOutId : id
					}
				})
		return win;
	},

	showAddWin : function(type, button, param) {
		var me = this;
		var win = null;
		var grid = button.up('grid');
		var record = Ext.create(grid.getStore().model);
		if (type == 'INOUT') {
			win = me.getInOutFormWin({
						title : '添加投入产出',
						grid : grid,
						type : 'ADD'
					});
		} else if (type == 'QC') {
			win = me.getQcFormWin({
						title : '添加质量参数',
						grid : grid,
						type : 'ADD'
					});
			// 保存上/下车检类型字段
			var checkType = grid.down('radiogroup').getValue().checkType;
			record.set(checkType , '1');
		} else if (type == 'PROP') {
			win = me.getPropFormWin({
						title : '添加物料属性',
						grid : grid,
						type : 'ADD'
					});
		}
		if (win)
			win.show();
		var form = win.down('form');
		form.loadRecord(record);
	},

	showEditWin : function(type, button) {
		var me = this;
		var win = null;
		var grid = button.up('grid');
		var selection = grid.getSelectionModel().getSelection();
		if (selection && selection.length == 1) {
			var record = selection[0];
			if (type == 'INOUT') {
				win = me.getInOutFormWin({
							title : '修改投入产出',
							grid : grid
						});
			} else if (type == 'QC') {
				win = me.getQcFormWin({
							title : '修改质量参数',
							grid : grid
						});
			} else if (type == 'PROP') {
				win = me.getPropFormWin({
							title : '修改物料属性',
							grid : grid
						});
			}
			if (win)
				win.show();
			var form = win.down('form');
			form.loadRecord(record);
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条记录！');
		}
	},

	getInOutFormWin : function(option) {
		var me = this;
		return Ext.create('Ext.window.Window', {
					width : document.body.scrollWidth / 1.5,
					layout : 'fit',
					title : option.title,
					iconCls : 'icon_detail',
					modal : true,
					plain : true,
					grid : option.grid,
					items : [{
						xtype : 'form',
						bodyPadding : '12 10 10',
						defaultType : 'textfield',
						defaults : {
							labelAlign : 'right',
							width : document.body.scrollWidth / 1.5 - 50,
							labelWidth : 150
						},
						items : [{
							name : 'matCode',
							fieldLabel : '物料编码',
							emptyText : '请选择原材料',

							xtype : 'combobox',
							displayField : 'showMatName',
							valueField : 'matCode',
							store : Ext.create('Ext.data.Store', {
										proxy : {
											type : 'rest',
											url : 'materialRequirementPlan/getDescByMatName?matName='
										},
										autoLoad : true,
										fields : [{
													name : 'matCode',
													mapping : 'MATCODE'
												}, {
													name : 'matName',
													mapping : 'MATNAME'
												}, {
													name : 'matDesc',
													mapping : 'MATDESC'
												}, {
													name : 'showMatName',
													convert : function(value, record) {
														if (record.get('MATDESC') != '') {
															return record.get('matName') + '[' + record.get('matDesc')
																	+ ']';
														}
														return value;
													}
												}],
										sorters : [{
													property : 'matCode',
													direction : 'ASC'
												}]
									}),
							listeners : {
								change : function(combox, newValue, oldValue, eOpts) {
									var me = this;
									if (combox.displayTplData[0]) {
										var form = me.up('form').getForm();
										form.findField('matName').setValue(combox.displayTplData[0].matName);
									}
								},
								beforequery : function(e) {
									var combo = e.combo;
									if (!e.forceAll) {
										var value = e.query;
										if (value != null && value != '') {
											combo.store.getProxy().url = 'materialRequirementPlan/getDescByMatName?matName='
													+ value
											combo.store.load();
										} else {
											combo.store.clearFilter();
										}
										combo.expand();
										return false;
									}
								}
							}
						}, {
							name : 'matName',
							xtype : 'hiddenfield'
						}, {
							fieldLabel : '投入/产出',
							xtype : 'radiogroup',
							columns : 2,
							items : [{
										boxLabel : '投入',
										name : 'inOrOut',
										inputValue : 'IN',
										checked : true
									}, {
										boxLabel : '产出',
										name : 'inOrOut',
										inputValue : 'OUT'
									}]
						}, {
							fieldLabel : '单位投入量',
							xtype : 'numberfield',
							name : 'quantity'
						}, {
							fieldLabel : '单位投入用量计算公式',
							name : 'quantityFormula'
						}, {
							fieldLabel : '用量单位',
							xtype : 'radiogroup',
							columns : 4,
							items : [{
										boxLabel : Oit.unitType.ton,
										name : 'unit',
										inputValue : 'TON',
										checked : true
									}, {
										boxLabel : Oit.unitType.kg,
										name : 'unit',
										inputValue : 'KG'
									}, {
										boxLabel : Oit.unitType.km,
										name : 'unit',
										inputValue : 'KM'
									}, {
										boxLabel : Oit.unitType.m,
										name : 'unit',
										inputValue : 'M'
									}]
						}, {
							fieldLabel : '用法',
							name : 'useMethod'
						}]
					}],
					buttons : [{
								text : Oit.btn.ok,
								handler : function() {
									me.updateForm(this.up('window'), option.type);
								}
							}, {
								text : Oit.btn.cancel,
								handler : function() {
									this.up('window').close();
								}
							}]
				});

	},

	getQcFormWin : function(option) {
		var me = this;
		return Ext.create('Ext.window.Window', {
					width : 642,
					layout : 'fit',
					title : option.title,
					iconCls : 'icon_detail',
					modal : true,
					plain : true,
					grid : option.grid,
					items : [{
								xtype : 'form',
								bodyPadding : '12 10 10',
								defaults : {
									labelAlign : 'right',
									width : 642
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
														name : 'checkItemCode'
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
											hidden : true,
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
													}]
										}, {
											xtype : 'container',
											layout : 'hbox',
											hidden : true,
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
													}, {
														fieldLabel : '超差是否报警',
														xtype : 'radiogroup',
														margin : '0 0 0 10',
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
													}]
										}, {
											xtype : 'container',
											layout : 'hbox',
											defaultType : 'textfield',
											defaults : {
												labelWidth : 100,
												width : 300
											},
											hidden : true,
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
														fieldLabel : '是否在终端显示',
														xtype : 'radiogroup',
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
													}, {
														fieldLabel : '值域',
														margin : '0 0 0 10',
														name : 'valueDomain'
													}]
										}]
							}],
					buttons : [{
								text : Oit.btn.ok,
								handler : function() {
									me.updateForm(this.up('window'), option.type);
								}
							}, {
								text : Oit.btn.cancel,
								handler : function() {
									this.up('window').close();
								}
							}]

				});
	},

	getPropFormWin : function(option) {
		var me = this;
		return Ext.create('Ext.window.Window', {
					width : 550,
					layout : 'fit',
					title : option.title,
					iconCls : 'icon_detail',
					modal : true,
					plain : true,
					grid : option.grid,
					items : [{
								xtype : 'form',
								bodyPadding : '12 10 10',
								defaultType : 'textfield',
								defaults : {
									labelAlign : 'right',
									labelWidth : 150
								},
								items : [{
											fieldLabel : '属性名称',
											width : 450,
											name : 'propName'
										}, {
											fieldLabel : '属性值',
											width : 450,
											name : 'propTargetValue'
										}]
							}],
					buttons : [{
								text : Oit.btn.ok,
								handler : function() {
									me.updateForm(this.up('window'), option.type);
								}
							}, {
								text : Oit.btn.cancel,
								handler : function() {
									this.up('window').close();
								}
							}]
				});

	},

	updateForm : function(win, type) {
		var me = this;

		var store = win.grid.getStore();
		var form = win.down('form');

		console.log(win)
		console.log(type)
		console.log(form)

		form.updateRecord();
		var record = form.getRecord();

		record.set('salesOrderItemId', me.spencialParam.salesOrderItemId);
		record.set('productProcessId', me.spencialParam.processId);
		record.set('processId', me.spencialParam.processId);
		if (me.spencialParam.matCode)
			record.set('matCode', me.spencialParam.matCode);
		if (me.spencialParam.matName)
			record.set('matName', me.spencialParam.matName);
		if (me.spencialParam.inOrOut)
			record.set('inOrOut', me.spencialParam.inOrOut);

		if (type == 'ADD')
			store.insert(0, record);
		store.sync();
		win.close();
	}
});