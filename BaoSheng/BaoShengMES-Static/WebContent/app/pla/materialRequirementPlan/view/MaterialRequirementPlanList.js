Ext.define("bsmes.view.MaterialRequirementPlanList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.materialRequirementPlanList',
			store : 'MaterialRequirementPlanStore',
			defaultEditingPlugin : false,
					columns : [{
						text : '生产单号',
						sortable : false,
						flex : 2,
						dataIndex : 'workOrderNo'
					}, {
						text : Oit.msg.pla.materialRequirementPlan.processName,
						sortable : false,
						flex : 2,
						dataIndex : 'processName'
					}, {
						text : Oit.msg.pla.materialRequirementPlan.planDate,
						dataIndex : 'planDate',
						flex : 2,
						renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}, 
//					{
//						text : Oit.msg.pla.materialRequirementPlan.matCode,
//						flex : 1,
//						dataIndex : 'matCode',
//					},
					{
						text : Oit.msg.pla.materialRequirementPlan.matName,
						flex : 3,
						dataIndex : 'matName'
					}, 
//					{
//						text : Oit.msg.pla.materialRequirementPlan.matSpec,
//						width : 110,
//						dataIndex : 'matSpec'
//					}, {
//						text : Oit.msg.pla.materialRequirementPlan.matSize,
//						width : 110,
//						dataIndex : 'matSize'
//					},
					{
						text : Oit.msg.pla.materialRequirementPlan.color,
						flex : 1,
						dataIndex : 'color'
					},
//					{
//						text : Oit.msg.pla.materialRequirementPlan.equipCode,
//						flex : 1,
//						dataIndex : 'equipCode',
//						renderer : function(value) {
//							var machine = value.split(",");
//							var res = '';
//							for (var i = 0; i < machine.length; i++) {
//								res = res + machine[i] + "<br/>";
//							}
//							return res;
//						}
//					},
					{
						text : Oit.msg.pla.materialRequirementPlan.quantity,
						flex : 1,
						dataIndex : 'weight',
//						renderer : function(value, metaData, record) {
//							if (value != '' && record.get('unFinishedLength') != '') {
//								return Math.round(value * record.get('unFinishedLength'));
//							} else {
//								return value
//							}
//						}
					}, {
						text : Oit.msg.pla.materialRequirementPlan.status,
						flex : 1,
						dataIndex : 'statusName'
					}],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
					title : '查询条件',
					xtype : 'fieldset',
					width : '100%',
					items : [{
						id : 'materialRequirementPlanSearchForm',
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
								labelAlign : 'right',
								width : 350
							}
						},
						items : [{
							items : [{
										fieldLabel : Oit.msg.pla.materialRequirementPlan.planDate,
										xtype : 'datefield',
										name : 'planDate',
										width : 300,
										format : 'Y-m-d'
									}, {
										fieldLabel : Oit.msg.pla.materialRequirementPlan.matName,
										xtype : 'combobox',
										name : 'matName',
										displayField : 'matName',
										width : 300,
										valueField : 'matName',
										store : new Ext.data.Store({
													fields : ['matName'],
													autoLoad : false,
													proxy : {
														type : 'rest',
														url : 'materialRequirementPlan/mat',
														extraParams : {
															all : true
															// 显示全部选项
														}
													}
												}),
										listeners : {
											beforequery : function(e) {
												opeaEmpty = true;
												var combo = e.combo;
												combo.collapse();
												if (!e.forceAll) {
													opeaEmpty = false;
													var value = e.query;
													if (value != null && value != '') {
														combo.store.filterBy(function(record, id) {
																	var text = record.get('matName');
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
									}, {

										fieldLabel : Oit.msg.pla.materialRequirementPlan.status,
										name : 'statusCode',
										xtype : 'combobox',
										labelWidth : 85,
										queryMode : 'local',
										editable : false,
										displayField : 'statusName',
										valueField : 'statusCode',
										width : 300,
										store : new Ext.data.Store({
													fields : ['statusName', 'statusCode'],
													autoLoad : true,
													proxy : {
														type : 'rest',
														url : 'materialRequirementPlan/getStatus?needALL=true'
													}
												})
									}]
						}, {
							height : 5
						}, {
							items : [{
										fieldLabel : Oit.msg.pla.materialRequirementPlan.processName,
										xtype : 'combobox',
										name : 'processCode',
										editable : false,
										displayField : 'processName',
										valueField : 'processCode',
										width : 300,
										mode : 'remote',
										minChars : 1,
										store : new Ext.data.Store({
													fields : ['processName', 'processCode'],
													autoLoad : true,
													proxy : {
														type : 'rest',
														url : 'materialRequirementPlan/process'
													}
												})
									}, 
//									{
//										fieldLabel : Oit.msg.pla.materialRequirementPlan.equipCode,
//										xtype : 'combobox',
//										name : 'equipCode',
//										displayField : 'name',
//										valueField : 'code',
//										mode : 'remote',
//										width : 420,
//										store : new Ext.data.Store({
//													extend : 'Oit.app.data.GridStore',
//													fields : ['code', {
//														name : 'name',
//														type : 'string',
//														convert : function(value, record) {
//															if (record.get('equipAlias') == '') {
//																return value + '[' + record.get('code') + ']';
//															} else {
//																return record.get('equipAlias') + '-' + value + '['
//																		+ record.get('code') + ']';
//															}
//
//														}
//													}, 'equipAlias'],
//													sorters : [{
//																property : 'code',
//																direction : 'ASC'
//															}], // 排序
//													proxy : {
//														type : 'rest',
//														url : '../fac/equipInfo/getEquipLine'
//													}
//												}),
//										listeners : {
//											beforequery : function(e) {
//												opeaEmpty = true;
//												var combo = e.combo;
//												combo.collapse();
//												if (!e.forceAll) {
//													opeaEmpty = false;
//													var value = e.query;
//													if (value != null && value != '') {
//														combo.store.filterBy(function(record, id) {
//																	var text = record.get('name');
//																	return (text.indexOf(value) != -1);
//																});
//													} else {
//														combo.store.clearFilter();
//													}
//													combo.onLoad();
//													combo.expand();
//													return false;
//												}
//											}
//										}
//									},
									{
										name : 'workOrderNo',
										fieldLabel : '生产单号',
										width : 300,
										xtype : 'combobox',
										displayField : 'workOrderNo',
										valueField : 'workOrderNo',
										queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
										minChars : 3, // 最少几个字开始查询
										triggerAction : 'all', // 请设置为”all”,否则默认
										// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
										typeAhead : true, // 是否延迟查询
										typeAheadDelay : 1000, // 延迟时间
										store : new Ext.data.Store({
													fields : ['workOrderNo'],
													proxy : {
														type : 'rest',
														url : 'materialRequirementPlan/workorderNo'
													}	
												}),
										listeners : {
											'beforequery' : function(e, eOpts) {
												var combo = e.combo;
												if (e.query) {
													combo.getStore().load({
																params : {
																	query : e.query
																}
															});
													combo.expand(); // 展开
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
									itemId : 'export',
									text : Oit.btn.export
								}, {
									itemId : 'summarize',
									text : '汇总'
									}]
							}]
					}]
				}]
		});
