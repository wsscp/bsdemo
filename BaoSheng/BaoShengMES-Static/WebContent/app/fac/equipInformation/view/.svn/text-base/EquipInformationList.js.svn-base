Ext.define("bsmes.view.EquipInformationList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.equipInformationList',
			defaultEditingPlugin : false,
			id : 'equipInformationList',
			forceFit : false,
			selModel : {
				mode : 'MULTI'
			},
			selType : 'checkboxmodel',
			store : 'EquipInformationStore',
			listeners : {
				afterrender : function(comp, eOpts) {
					var combo = Ext.ComponentQuery.query('equipInformationList combobox')[1];
					var store = combo.getStore();
					store.load({
								callback : function(records, options, success) {
									combo.select(records[0]);
									comp.store.load();
								}
							});
				}
			},
			columns : [{
						text : Oit.msg.fac.equipInformation.name,
						width : 300,
						dataIndex : 'name',
						renderer : function(value, metaData, record) {
							// metaData.style = "white-space:normal;padding:5px 20px 5px 5px;";
							if (record.get('equipAlias') == '') {
								value = value + '[' + record.get('code') + ']';
							} else {
								value = record.get('equipAlias') + '[' + record.get('code') + ']' + '-' + value;
							}
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : Oit.msg.fac.equipInformation.maintainer,
						width : 100,
						hidden : true,
						dataIndex : 'maintainer'
					}, {
						text : Oit.msg.fac.equipInformation.maintainDate,
						width : 150,
						dataIndex : 'maintainDate'
					}, {
						text : Oit.msg.fac.equipInformation.nextMaintainDate,
						width : 150,
						dataIndex : 'nextMaintainDate',
						xtype : 'datecolumn',
						format : 'Y年m月d日',
						filter : {
							type : 'date'
						}
					}, {
						text : Oit.msg.fac.equipInformation.maintainDateFirst,
						width : 150,
						dataIndex : 'maintainDateFirst'
					}, {
						text : Oit.msg.fac.equipInformation.nextMaintainDateFirst,
						width : 150,
						dataIndex : 'nextMaintainDateFirstStr'
					}, {
						text : Oit.msg.fac.equipInformation.maintainDateSecond,
						width : 150,
						dataIndex : 'maintainDateSecond'
					}, {
						text : Oit.msg.fac.equipInformation.nextMaintainDateSecond,
						width : 150,
						dataIndex : 'nextMaintainDateSecondStr'
					}, {
						text : Oit.msg.fac.equipInformation.maintainDateOverhaul,
						width : 150,
						dataIndex : 'maintainDateOverhaul'
					}, {
						text : Oit.msg.fac.equipInformation.nextMaintainDateOverhaul,
						width : 150,
						dataIndex : 'nextMaintainDateOverhaul',
						xtype : 'datecolumn',
						format : 'Y年m月d日',
						filter : {
							type : 'date'
						}
					}, {
						text : Oit.msg.fac.equipInformation.model,
						width : 150,
						dataIndex : 'model'
					}, {
						text : '设备分类',
						width : 150,
						dataIndex : 'equipCategory'
					}, {
						text : '设备价格',
						width : 150,
						dataIndex : 'equipPrice'
					}, {
						text : '设备厂商',
						width : 150,
						dataIndex : 'equipFact'
					}, {
						text : '采购日期',
						width : 150,
						dataIndex : 'equipPurtime',
						xtype : 'datecolumn',
						format : 'Y年m月d日',
						filter : {
							type : 'equipPurtime'
						}
					}, {
						text : Oit.msg.fac.equipInformation.type,
						width : 100,
						dataIndex : 'type',
						renderer : function(value, cellmeta, record) {
							switch (value) {
								case 'MAIN_EQUIPMENT' :
									return '主生产设备';
								case 'ASSIT_EQUIPMENT' :
									return '辅助生产设备';
								case 'TOOLS' :
									return '工装夹具';
								case 'PRODUCT_LINE' :
									return '生产线';
								default :
									return '';
							}
						}
					}, {
						text : Oit.msg.fac.equipInformation.subType,
						width : 100,
						dataIndex : 'subType',
						renderer : function(value, cellmeta, record) {
							switch (value) {
								case 'LINE_DEVICE' :
									return '放线装置';
								case 'VERTICAL_HEAD' :
									return '纵包头';
								case 'AROUND_HEAD' :
									return '绕包头';
								case 'ROTARY_TRACTOR' :
									return '旋转牵引机';
								case 'HUTCHISION_DEVICE' :
									return '记米装置';
								case 'CABLE_MACHINE' :
									return '成缆机';
								default :
									return '';
							}
						}
					}, {
						text : Oit.msg.fac.equipInformation.belongLine,
						width : 100,
						dataIndex : 'belongLine'
					}],
			actioncolumn : ['', {
						itemId : 'edit'
					}/*, '', {
						itemId : 'editMaintainer',
						icon : '/bsstatic/icons/hard-hat--pencil.png',
						tooltip : Oit.msg.fac.equipInformation.btn.maintainerEdit,
						handler : function(grid, rowIndex) {
							Ext.ComponentQuery.query('equipInformationList')[0].fireEvent('toEditMaintainer', grid
											.getStore().getAt(rowIndex));
						},
						isDisabled : function(view, rowIndex, colIndex, item, record) {
							return !record.get('isNeedMaintain');
						}
					}*/],
			tbar : [{
						itemId : 'add'
					}, {
						itemId : 'remove'
					}, {
						itemId : 'export'
					}, {
						itemId : 'importImage',
						text : '上传设备图片'
					}, {
						itemId : 'lookImage',
						text : '设备图片查看',
						handler : function() {
							var grid = Ext.getCmp('equipInformationList');
							var selections = grid.getSelectionModel().getSelection();
							var eqipId = null;
							if (selections.length > 0) {
								for (var i = 0; i < selections.length; i++) {
									var row = selections[i];
									if (row.get('id')) {
										eqipId = row.get('id');
									}
								}
								lookupImage(eqipId);
							} else {
								Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
								return;
							}
						}
					}, {
						itemId : 'equipMaintain',
						text : '设备保养记录',
						handler : function() {
							var grid = Ext.getCmp('equipInformationList');
							var selections = grid.getSelectionModel().getSelection();
							if (selections.length > 0) {
								var row = selections[0];
								var code = row.get('code');
								Oit.app.controller.GridController.openSubWindow('maintainRecord.action?equipCode='
												+ code, Oit.msg.fac.equipInformation.btn.repair);
							} else {
								Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
								return;
							}
						}
					}, {
						itemId : 'equipMaintainCase',
						text : '设备维修记录',
						handler : function() {
							var grid = Ext.getCmp('equipInformationList');
							var selections = grid.getSelectionModel().getSelection();
							if (selections.length > 0) {
								var row = selections[0];
								var code = row.get('code');
								code = code.substring(0, code.indexOf('_'));
								var win = Ext.create('Ext.window.Window', {
											title : '设备维修维护情况',
											height : 400,
											width : 900,
											layout : 'fit',
											items : [{
														xtype : 'equipEventCaseList'
													}]
										});
								var grid = Ext.ComponentQuery.query('#equipEventCaseList')[0];
								var store = grid.getStore();
								store.getProxy().url = "/bsmes/fac/equipMaintenance/queryEquipEvent/" + code;
								store.reload();
								win.show();
							} else {
								Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
								return;
							}
						}
					}/*
						 * ,{ itemId : 'equipTransfer', text : '设备迁移' },{ itemId : 'equipTransferHistory', text :
						 * '设备迁移历史' }
						 */],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
					title : '查询条件',
					xtype : 'fieldset',
					width : '100%',
					items : [{
						xtype : 'form',
						width : '100%',
						layout : 'vbox',
						buttonAlign : 'left',
						labelAlign : 'right',
						bodyPadding : 5,
						defaults : {
							xtype : 'panel',
							width : '100%',
							layout : 'hbox',
							defaults : {
								labelAlign : 'right'
							}
						},
						items : [{
							items : [{
								fieldLabel : '设备名称',
								xtype : 'combobox',
								name : 'code',
								mode : 'remote',
								displayField : 'name',
								valueField : 'code',
								width : 420,
								store : new Ext.data.Store({
											fields : ['code', {
												name : 'name',
												type : 'string',
												convert : function(value, record) {
													if (record.get('equipAlias') == '') {
														return value + '[' + record.get('code') + ']';
													} else {
														return record.get('equipAlias') + '-' + value + '['
																+ record.get('code') + ']';
													}
												}
											}, 'equipAlias'],
											autoLoad : true,
											proxy : {
												type : 'rest',
												url : 'equipInfo/getEquipLine'
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										if (!e.forceAll) {
											var value = e.query;
											combo.store.filterBy(function(record, id) {
														var text = record.get(combo.displayField);
														return (text.indexOf(value) != -1);
													});
											combo.expand();
											return false;
										}
									}
								}
							}, {
								fieldLabel : Oit.msg.fac.equipInformation.type,
								name : 'type',
								xtype : 'combobox',
								editable : false,
								queryMode : 'local',
								displayField : 'name',
								id : 'myCombo',
								valueField : 'code',
								triggerAction : 'all',
								store : new Ext.data.Store({
											fields : [{
														name : 'name'
													}, {
														name : 'code'
													}],
											autoLoad : false,
											proxy : {
												type : 'rest',
												url : 'equipInformation/equipType'
											}
										})
							}, {
								xtype : 'radiogroup',
								fieldLabel : '工序选择',
								columns : 3,
								width : 300,
								labelWidth : 60,
								margin : '0 0 0 30',
								vertical : true,
								items : [{
											boxLabel : '绝缘',
											name : 'section',
											inputValue : '绝缘'
										}, {
											boxLabel : '成缆',
											name : 'section',
											inputValue : '成缆'
										}, {
											boxLabel : '护套',
											name : 'section',
											inputValue : '护套'
										}],
								listeners : {
									change : function(radio, newValue, oldValue, eOpts) {
										var grid = Ext.ComponentQuery.query('equipInformationList')[0];
										var store = grid.getStore();
										if (newValue.section == '' || newValue.section == null) {
											store.load();
											return;
										}
										store.reload({
													params : {
														'section' : newValue.section
													}
												});
									}
								}
							}]
						}],
						buttons : [{
									itemId : 'search',
									text : Oit.btn.search
								}, {
									itemId : 'reset',
									text : '重置',
									handler : function(e) {
										this.up("form").getForm().reset();
									}
								}]

					}]
				}]
			}]
		});
function lookupImage(eqipId) {
	var imageStore = new Oit.app.data.GridStore({
				autoLoad : true,
				autoSync : true,
				fields : ['id', 'imageName', 'code', 'imagePath', 'equipAlias', 'name'],
				proxy : {
					type : 'rest',
					url : 'equipInformation/lookImage?eqipId=' + eqipId,
					reader : {
						type : 'json',
						root : 'rows'
					}
				}
			});
	var imageGrid = new Oit.app.view.Grid({
				store : imageStore,
				id : 'imageGrid',
				width : '100%',
				height : 420,
				columns : [{
					text : '设备名称',
					dataIndex : 'name',
					flex : 2,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;padding:5px 20px 5px 5px;";
						if (record.get('equipAlias') == '') {
							return value + '[' + record.get('code') + ']';
						} else {
							return record.get('equipAlias') + '-' + value.replace('_设备', '') + '['
									+ record.get('code').replace('_EQUIP', '') + ']';
						}
					}
				}, {
					text : '图片名称',
					flex : 2,
					dataIndex : 'imageName'
				}, {
					text : '操作',
					flex : 1,
					dataIndex : 'cz',
					renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
						var me = this;
						console.log(view)
						var html = '<a style="color:blue;cursor:pointer;margin:0 10 0 0" onclick="lookImage(\''
								+ record.get('imagePath')
								+ '\')">查看</a><a style="color:blue;cursor:pointer;"onclick="deleteImage(\''
								+ record.get('id') + '\')">删除</a>';
						return html;
					}
				}]
			});
	getWindow(imageGrid, '查看设备图片').show();
}
function getWindow(grid, title) {
	var win = new Ext.window.Window({
				title : title,
				width : 750,
				height : 420,
				layout : 'border',
				items : [{
							region : 'center',
							items : grid
						}]
			});
	return win;
}
function lookImage(imagePath) {
	var width = document.body.scrollWidth / 2 - 200, height = width * 0.618;
	var win = new Ext.window.Window({
				title : '图片查看',
				width : width,
				overflowY : 'auto',
				height : height,
				items : [{
					xtype : 'panel',
					width : width,
					height : height,
					html : '<img src="equipInformation/showFile?refId=' + imagePath + '" width = "' + width
							+ 'px" height = "' + height + 'px">'
				}],
				buttons : ['->', {
							text : '关闭',
							handler : function() {
								this.up('window').close();
							}
						}]
			});
	win.show();
}
function deleteImage(id) {
	Ext.MessageBox.confirm('警告', '确认删除？', function(btn) {
				if (btn == 'yes') {
					console.log(id)
					Ext.Ajax.request({
								url : 'equipInformation/deleteImage',
								params : {
									id : id
								},
								success : function(response) {
									Ext.ComponentQuery.query('#imageGrid')[0].getStore().reload();
								}
							});
				}
			});
}
