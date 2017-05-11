Ext.define("bsmes.view.EquipInfoList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.equipInfoList',
			defaultEditingPlugin : false,
			store : 'EquipInfoStore',
			forceFit : false,
			columns : [{
						text : Oit.msg.fac.equipInformation.name,
						flex : 6,
						minWidth : 300,
						dataIndex : 'name',
						renderer : function(value, metaData, record) {
							if (record.get('equipAlias') == '') {
								return value + '[' + record.get('code') + ']';
							} else {
								return record.get('equipAlias') + '-' + value + '[' + record.get('code') + ']';
							}
							metaData.tdAttr = 'data-qtip="' + res1 + '"';
							return res;
						}
					}, {
						text : Oit.msg.fac.equipInformation.maintainer,
						width : 100,
						dataIndex : 'maintainer'
					}, {
						text : Oit.msg.fac.equipInformation.model,
						width : 100,
						dataIndex : 'model'
					}, {
						text : Oit.msg.fac.equipInformation.type,
						dataIndex : 'type',
						width : 150,
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
						text : Oit.msg.fac.equipInformation.maintainDate,
						width : 150,
						dataIndex : 'maintainDate'
					}, {
						text : Oit.msg.fac.equipInformation.nextMaintainDate,
						width : 200,
						dataIndex : 'nextMaintainDate',
						xtype : 'datecolumn',
						format : 'Y年m月d日',
						filter : {
							type : 'date'
						}
					}, {
						text : Oit.msg.fac.equipInformation.maintainDateFirst,
						width : 200,
						dataIndex : 'maintainDateFirst'
					}, {
						text : Oit.msg.fac.equipInformation.nextMaintainDateFirst,
						width : 150,
						dataIndex : 'nextMaintainDateFirstStr'
					}, {
						text : Oit.msg.fac.equipInformation.maintainDateSecond,
						width : 200,
						dataIndex : 'maintainDateSecond'
					}, {
						text : Oit.msg.fac.equipInformation.nextMaintainDateSecond,
						width : 150,
						dataIndex : 'nextMaintainDateSecondStr'
					}, {
						text : Oit.msg.fac.equipInformation.maintainDateOverhaul,
						width : 200,
						dataIndex : 'maintainDateOverhaul'
					}, {
						text : Oit.msg.fac.equipInformation.nextMaintainDateOverhaul,
						width : 200,
						dataIndex : 'nextMaintainDateOverhaulStr'
					}],
			actioncolumn : [{
				itemId : 'repair',
				icon : '/bsstatic/icons/hard-hat.png',
				tooltip : Oit.msg.fac.equipInformation.btn.repair,
				handler : function(grid, rowIndex) {
					var row = grid.getStore().getAt(rowIndex);
					Oit.app.controller.GridController.openSubWindow('maintainRecord.action?equipCode=' + row.get('code'),
							Oit.msg.fac.equipInformation.btn.repair);
				}
			}],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
					title : '查询条件',
					xtype : 'fieldset',
					collapsible : true,
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
								fieldLabel : Oit.msg.fac.equipInformation.name,
								xtype : 'combobox',
								name : 'code',
								width : 500,
								editable : false,
								mode : 'remote',
								displayField : 'name',
								valueField : 'ecode',
								defaultValue : '',
								triggerAction : 'all',
								store : new Ext.data.Store({
											fields : ['code', {
														name : 'name',
														type : 'string',
														convert : function(value, record) {
															if (record.get('equipAlias') == '') {
																return value + '[' + record.get('code') + ']';
															} else {
																return record.get('equipAlias') + '-' + value + '[' + record.get('code')
																		+ ']';
															}
														}
													}, 'equipAlias', 'ecode'],
											proxy : {
												type : 'rest',
												url : 'equipInfo/getEquipLine'
											}
										}),
								listeners : {
									beforequery : function(e) {
										equipEmpty = true;
										var combo = e.combo;
										combo.collapse();
										if (!e.forceAll) {
											equipEmpty = false;
											var value = e.query;
											if (value != null && value != '') {
												combo.store.filterBy(function(record, id) {
															var text = record.get('name');
															return (text.indexOf(value) != -1);
														});
											} else {
												combo.store.clearFilter();
											}
											combo.onLoad();
											combo.expand();
											return false;
										}
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
								}, {
									text : Oit.btn.export,
									itemId : 'export'
								}]

					}]
				}]
			}]

		});
