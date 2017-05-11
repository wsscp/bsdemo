/**
 * 主列表：订单产品列表
 */

Ext.define("bsmes.view.HandScheduleGrid", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.handScheduleGrid',
	store : 'HandScheduleStore',
	defaultEditingPlugin : false,
	columnLines : true,
	isHidden : Ext.fly('roleList').getAttribute('roleList').indexOf("JY_DD")>-1?false:true,
	selType : 'checkboxmodel',
	selModel : {
		mode : "SIMPLE" // "SINGLE"/"SIMPLE"/"MULTI"
		// checkOnly : true
	},
	viewConfig : {
		enableTextSelection : true,
		getRowClass : function(record, rowIndex, rowParams, store) {
			var specialFlag = record.get("SPECIALFLAG"); // 特殊状态[0:厂外计划; 1:计划已报; 2:手工单; 3:成品现货]
			var status = record.get("STATUS"); // 特殊状态[TO_DO:未开始; IN_PROGRESS:生产中; FINISHED:已完成; CANCELED:已取消]
			if (specialFlag == '0') { // 0:厂外计划; 橘黄色
				return 'x-grid-record-orange-color ';
			} else if (specialFlag == '1') { // 1:计划已报; 绿色
				return 'x-grid-record-green-color ';
			} else if (specialFlag == '2') { // 2:手工单; 紫色
				return 'x-grid-record-purple-color ';
			} else if (specialFlag == '3') { // 3:成品现货; 青色
				return 'x-grid-record-cyan-color ';
			} else if (status == 'IN_PROGRESS') { // IN_PROGRESS:生产中; 蓝色
				return 'x-grid-record-blue-color ';
			} else if (status == 'FINISHED') { // FINISHED:已完成; 绿色
				return 'x-grid-record-green-color ';
			} else if (status == 'CANCELED') { // CANCELED:已取消; 灰色
				return 'x-grid-record-grey-color ';
			}
		}
	},
	
	initComponent : function() {
		var me = this;
		var columns = [{
			text : '客户<br/>合同号',
			dataIndex : 'CONTRACTNO',
			flex : 1.5,
			minWidth : 55,
			sortable : false,
			renderer : function(value, metaData, record) {
				var reg = /[a-zA-Z]/g;
				value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length
						- 5) : value.replace(reg, ""));
				// + '[' + record.get('OPERATOR') + ']';
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				// metaData.tdAttr = 'data-qtip="' + value + '"';
				// value = '<a
				// style="width:16px;height:16px;display:block;float:left;"
				// class="icon_remove"></a>&nbsp;&nbsp;' + value;				
				if((record.get('TIMECOUNT') <= record.get('REMINDTIME'))&&(record.get('STATUS') == 'TO_DO')){
					value = '<font color=red>(新)</font>' + value;
				}
				return value;
			}
		}, {
			text : '经办人',
			dataIndex : 'OPERATOR',
			flex : 1.1,
			minWidth : 55,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				// metaData.tdAttr = 'data-qtip="' + value + '"';
				return value;
			}
		}, {
			text : Oit.msg.pla.customerOrderItem.customerCompany,
			dataIndex : 'CUSTOMERCOMPANY',
			flex : 2.4,
			minWidth : 120,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				// metaData.tdAttr = 'data-qtip="' + value + '"';
				return value;
			}
		}, {
			// text : '产品型号规格',
			// dataIndex : 'PRODUCTTYPE',
			// flex : 2.4,
			// minWidth : 120,
			// hidden : true,
			// renderer : function(value, metaData, record,
			// rowIndex, columnIndex, store, view) {
			// var me = this;
			// value = value + ' ' + record.get('PRODUCTSPEC');
			// metaData.tdAttr = 'data-qtip="' + value + '"';
			// return value;
			// },
			// menuDisabled : true
			// }, {
			text : '产品型号规格<br/>客户型号规格',
			dataIndex : 'CUSTPRODUCTTYPE',
			flex : 3,
			minWidth : 150,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				var show = record.get('PRODUCTTYPE') + ' ' + record.get('PRODUCTSPEC');
				show += '<br/>' + value + ' ' + record.get('CUSTPRODUCTSPEC');
				// metaData.style =
				// "white-space:normal;word-break:break-all;padding:5px
				// 5px 5px 5px;";
				metaData.tdAttr = 'data-qtip="' + show + '"';
				return show;
			},
			menuDisabled : true
		}, {
			text : '合同<br/>长度',
			dataIndex : 'CONTRACTLENGTH',
			flex : 1.1,
			minWidth : 55,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				// metaData.tdAttr = 'data-qtip="' + value + '"';
				return value;
			}
		}, {
			text : '合同<br/>金额',
			dataIndex : 'CONTRACTAMOUNT',
			flex : 1.1,
			minWidth : 55,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				// metaData.tdAttr = 'data-qtip="' + value + '"';
				return value;
			}
		}, {
			text : 'ERP编码',
			dataIndex : 'ERPBM',
			flex : 1.3,
			minWidth : 65,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				// metaData.tdAttr = 'data-qtip="' + value + '"';
				return value;
			},
			sortable : false,
			menuDisabled : true
		}, {
			text : '下达<br/>时间',
			dataIndex : 'RELEASEDATE',
			flex : 1.1,
			minWidth : 55,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				// metaData.tdAttr = 'data-qtip="' + value + '"';
				value = value.substring(value.indexOf('-') + 1).replace(' ', '<br/>');
				return value;
			}
		}, {
			text : '交货期',
			dataIndex : 'CUSTOMEROADATE',
			flex : 1.1,
			minWidth : 55,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				// value = value.substring(value.indexOf('-') + 1);
				value = value.substring(value.indexOf('-') + 1, value.lastIndexOf(':')).replace(' ', '<br/>');
				// metaData.tdAttr = 'data-qtip="' + value + '"';
				return value;
			}
		}, {
			text : '预计完<br/>成时间',
			dataIndex : 'OADATE',
			flex : 1.1,
			minWidth : 55,
			// hidden : true,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = value.substring(value.indexOf('-') + 1, value.lastIndexOf(':')).replace(' ', '<br/>');
//				var show = '';
//				if (value != '') {
//					show = Ext.util.Format.date(value, "m-d<br/>H:i")
//					// value = value.substring(value.indexOf('-') +
//					// 1, value.lastIndexOf(':'));
//				}
				// metaData.tdAttr = 'data-qtip="' + show + '"';
				return value;
			}
		}, {
			text : '附件',
			dataIndex : 'ID',
			flex : 0.9,
			minWidth : 45,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				var nofile = (record.get('ORDERFILENUM') == 0);
				var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"'
						+ (nofile ? '' : 'onclick="lookUpAttachFileWindow(\'' + value + '\', \'\', \'\')"') + '>附件</a>';
				return html;
			}
		},{
			text : '内部工</br>艺附件',
			dataIndex : 'TECHNIQUENUM',
			flex : 0.8,
			minWidth : 55,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				var nofile = (value == 0);
				var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"'
						+ (nofile ? '' : 'onclick="lookUpAttachFileWindow(\'' + record.get('SALESORDERITEMID') + '\', \'TECHNIQUENUM\', \''+record.get('CONTRACTNO')+'\')"') + '>附件</a>';
				return html;
			}
		}, {
			text : '备注&技术要求',
			dataIndex : 'ID',
			flex : 5,
			minWidth : 250,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				var processRequire = '技术要求:' + (record.get('PROCESSREQUIRE') == '' ? '无' : record.get('PROCESSREQUIRE'));
				var remarks = '备注:' + (record.get('REMARKS') == '' ? '无' : record.get('REMARKS'));
				var showStr = processRequire + ';<br/>' + remarks;
				// metaData.style =
				// "white-space:normal;word-break:break-all;padding:5px
				// 5px 5px 5px;";
				metaData.tdAttr = 'data-qtip="' + showStr + '"';
				return showStr;
			}
		}, {
			text : '订单<br/>状态',
			dataIndex : 'STATUS',
			flex : 1.2,
			minWidth : 60,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				if (value == 'TO_DO') {
					return '未开始';
				} else if (value == 'IN_PROGRESS') {
					return '生产中';
				} else if (value == 'FINISHED') {
					return '已完成';
				} else if (value == 'CANCELED') {
					return '已取消';
				} else {
					return '订单状态不明确'
				}
			}
		}, {
			text : '下发进度',
			dataIndex : 'SENDDOWNPERCENT',
			flex : 1.8,
			minWidth : 90,
			sortable : false,
			menuDisabled : true,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				var specialFlag = record.get('SPECIALFLAG'); // 0:厂外计划;1:计划已报;2:手工单;3:成品现货
				var specialOperateTime = record.get('SPECIALOPERATETIME');
				if (specialFlag == '0') {
					return '外计划<br/>' + Ext.util.Format.date(specialOperateTime, "m-d H:i");
				} else if (specialFlag == '2') {
					return '手工单<br/>' + Ext.util.Format.date(specialOperateTime, "m-d H:i");
				} else if (specialFlag == '3') {
					return '<a style="color:red;cursor:pointer;" onclick = "showSpecialFlag(\''+record.get('SALESORDERITEMID')+'\')">成品现货</a><br/>' + Ext.util.Format.date(specialOperateTime, "m-d H:i");
				}
				// metaData.style =
				// "white-space:normal;word-break:break-all;padding:5px
				// 5px 5px 5px;";
				if (record.get('FINISHJY') == '1') {
					metaData.tdAttr = 'data-qtip="已全部下发"';
					return '<a style="color:blue;cursor:pointer;" onclick="showPercentDetail(\'\', \'' + record.get('ID')
							+ '\', \'JY\')">已全部下发</a>';
				}

				var sendDownPercent = value.split(','), finSend = [], hafSend = [], send = [], showStr, showStrQtip, contractLength = record
						.get('CONTRACTLENGTH');
				if (sendDownPercent && sendDownPercent.length == 4) {

					if (record.get('HASYUNMU') > 0) {
						if (sendDownPercent[0] < contractLength) {
							hafSend.push('云母(' + sendDownPercent[0] + ')');

						} else {
							finSend.push('云母');
						}
						if (sendDownPercent[0] > 0) {
							send.push('云母');
						}
					}

					if (sendDownPercent[1] < contractLength) {
						hafSend.push('挤出(' + sendDownPercent[1] + ')');

					} else {
						finSend.push('挤出');
					}
					if (sendDownPercent[1] > 0) {
						send.push('挤出');
					}

					if (sendDownPercent[2] < contractLength) {
						hafSend.push('火花(' + sendDownPercent[2] + ')');

					} else {
						finSend.push('火花');
					}
					if (sendDownPercent[2] > 0) {
						send.push('火花');
					}

					if (record.get('PRODUCTTYPE').indexOf('YJ') >= 0) {
						if (sendDownPercent[3] < contractLength) {
							hafSend.push('蒸线(' + sendDownPercent[3] + ')');
						} else {
							finSend.push('蒸线');
						}
						if (sendDownPercent[3] > 0) {
							send.push('蒸线');
						}
					}
				}
				if (hafSend.length == 0) {
					showStr = '<a style="color:blue;cursor:pointer;" onclick="showPercentDetail(\'\', \''
							+ record.get('ID') + '\', \'JY\')">已全部下发</a>';
					showStrQtip = '已全部下发';
				} else if (send.length == 0) {
					showStr = '<font style="color: red">未下发</font>';
					showStrQtip = '未下发';
				} else if (finSend.length == 0) {
					showStr = '<a style="color:blue;cursor:pointer;" onclick="showPercentDetail(\'\', \''
							+ record.get('ID') + '\', \'JY\')"><font style="color: red;">' + hafSend.join() + '</font></a>';
					showStrQtip = hafSend.join();
				} else {
					showStr = '<a style="color:blue;cursor:pointer;" onclick="showPercentDetail(\'\', \''
							+ record.get('ID') + '\', \'JY\')">' + finSend.join() + '已下;<br/><font style="color: red;">'
							+ hafSend.join() + '</font></a>';
					showStrQtip = finSend.join() + '已下,' + hafSend.join();
				}
				if (record.get('STATUS') == 'CANCELED') {
					showStr = '订单已取消';
					showStrQtip = '订单已取消';
				}

				metaData.tdAttr = 'data-qtip="' + showStrQtip + '"';
				return showStr;
			}
		}, {
			text : '修改',
			dataIndex : 'CRAFTSCNAME',
			flex : 1.3,
			minWidth : 65,
			sortable : false,
			menuDisabled : true,
			hidden : me.isHidden,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
				value = '<a style="color:blue;cursor:pointer;" onclick="getInstanceProcessGrid(\'' + record.get('CRAFTSID')
						+ '\',\'' + record.get('CRAFTSCODE') + '\',\'' + record.get('SALESORDERITEMID') + '\',true)">'
						+ value + '</a>';
				var html = '<a style="color:';
				/* if(record.get('PARENTCRAFTSID') && record.get('PARENTCRAFTSID') != ''){
				html += 'blue;cursor:pointer;" 'onclick="changeCrafts(\'' + rowIndex + '\', \'' + record.get('PRODUCTTYPE')
						+ '\', \'' + record.get('PRODUCTSPEC') + '\', \'' + record.get('PRODUCTCODE') + '\', \''
						+ record.get('ID') + '\', \'' + record.get('PARENTCRAFTSID') + '\')"';
				 }else{
				 html += 'grey;"';
				 }*/
				html += 'grey;">工艺</a> / <br/>';
				return html + value;
			}
		}];
		
		var dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			items : [{
				title : '查询条件',
				xtype : 'fieldset',
				collapsible : true,
				width : '99.3%',
				items : [{
					xtype : 'form',
					layout : 'column',
					defaults : {
						width : 300,
						padding : 1,
						labelAlign : 'right'
					},
					items : [{
								fieldLabel : '客户合同号',
								xtype : 'combobox',
								name : 'contractNo',
								displayField : 'contractNo',
								valueField : 'contractNo',
								multiSelect : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['contractNo'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getcontractNo'
											},
											sorters : [{
														property : 'contractNo',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '经办人',
								xtype : 'combobox',
								name : 'operator',
								displayField : 'operator',
								valueField : 'operator',
								multiSelect : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['operator'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getoperator'
											},
											sorters : [{
														property : 'operator',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '单位',
								name : 'customerCompany',
								xtype : 'combobox',
								displayField : 'customerCompany',
								valueField : 'customerCompany',
								multiSelect : true,
								hidden : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											fields : ['customerCompany'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getcustomerCompany'
											},
											sorters : [{
														property : 'customerCompany',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '客户型号',
								xtype : 'combobox',
								name : 'custProductType',
								displayField : 'custProductType',
								valueField : 'custProductType',
								multiSelect : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['custProductType'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getcustproductType'
											},
											sorters : [{
														property : 'custProductType',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '产品型号',
								xtype : 'combobox',
								name : 'productType',
								displayField : 'productType',
								valueField : 'productType',
								multiSelect : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['productType'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getproductType'
											},
											sorters : [{
														property : 'productType',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '产品规格',
								name : 'productSpec',
								xtype : 'combobox',
								displayField : 'productSpec',
								valueField : 'productSpec',
								multiSelect : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['productSpec'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getproductSpec'
											},
											sorters : [{
														property : 'productSpec',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '线芯结构',
								xtype : 'combobox',
								name : 'wiresStructure',
								displayField : 'wiresStructure',
								valueField : 'wiresStructure',
								multiSelect : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['wiresStructure'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getwiresStructure'
											},
											sorters : [{
														property : 'wiresStructure',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '线芯数',
								xtype : 'combobox',
								name : 'numberOfWires',
								displayField : 'numberOfWires',
								valueField : 'numberOfWires',
								multiSelect : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['numberOfWires'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getnumberOfWires'
											},
											sorters : [{
														property : 'numberOfWires',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '截面',
								xtype : 'combobox',
								name : 'section',
								displayField : 'section',
								valueField : 'section',
								multiSelect : true,
								queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
								minChars : 3, // 最少几个字开始查询
								triggerAction : 'all', // 请设置为”all”,否则默认
								// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
								store : new Ext.data.Store({
											autoLoad : false,
											fields : ['section'],
											proxy : {
												type : 'rest',
												url : 'handSchedule/getSection'
											},
											sorters : [{
														property : 'section',
														direction : 'ASC'
													}],
											listeners : {
												beforeload : function(store, operation, eOpts) {
													var form = Ext.ComponentQuery.query('handScheduleGrid toolbar form')[0];
													var param = form.getForm().getValues();
													store.getProxy().extraParams = param;
												}
											}
										}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										combo.collapse(); // 折叠
										if (!e.forceAll) { // 模糊查询走的方法
											var value = e.query;
											if (value != null && value != '') {
												combo.getStore().load({
															params : {
																'query' : value
															}
														});
											} else {
												combo.getStore().load();
											}
											combo.expand(); // 展开
											return false;
										} else { // 点击下拉框
											combo.firstExpand++;
										}
									},
									expand : function(e) {
										if (e.firstExpand > 1) {
											e.getStore().load();
										}
									}
								}
							}, {
								fieldLabel : '开始日期',
								xtype : 'datefield',
								name : 'createDate',
								format : 'Y-m-d',
								value : Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -5), "Y-m-d")
							}, {
								fieldLabel : '是否云母绕包',
								xtype : 'checkbox',
								name : 'isYunMu',
								checked : false
							}, {
								// fieldLabel : '是否全部下发',
								// xtype : 'checkbox',
								// name : 'finishJy',
								// checked : false
								// }, {
								fieldLabel : Oit.msg.pla.orderOA.orderItemStatus,
								xtype : 'checkboxgroup',
								width : 500,
								columns : 5,
								vertical : true,
								items : [{
											boxLabel : '未开始',
											name : 'status',
											inputValue : 'TO_DO',
											checked : true
										}, {
											boxLabel : '生产中',
											name : 'status',
											inputValue : 'IN_PROGRESS',
											checked : true
										}, {
											boxLabel : '已完成',
											name : 'status',
											inputValue : 'FINISHED'
										}, {
											boxLabel : '已取消',
											name : 'status',
											inputValue : 'CANCELED'
										}]
							}],
					buttons : [{
								text : '手动导入',
								hidden : true,
								handler : function(e) {
									Ext.Ajax.request({
												url : 'handSchedule/importMySelf',
												success : function(response) {
													Ext.Msg.alert(Oit.msg.PROMPT, '生成完毕');
												}
											})
								}
							}, {
								itemId : 'search'
							}, {
								itemId : 'reset',
								handler : function(e) {
									this.up("form").getForm().reset();
								}
							}, {
								itemId : 'searchNoCrafts',
								hidden : true,
								text : '查询无工艺产品'
							}, {
								itemId : 'saveTemp',
								text : '保存下发清单',
								hidden : me.isHidden
							}, {
								itemId : 'searchTemp',
								hidden : me.isHidden,
								text : '查询下发清单'
							}, {
								itemId : 'clearTemp',
								text : '清空下发清单',
								hidden : me.isHidden
							}, {
								itemId : 'reportPlan',
								text : '报计划',
								hidden : me.isHidden
							}, {
								itemId : 'outPlan',
								text : '外计划',
								hidden : me.isHidden
							}, {
								itemId : 'exportOutPlan',
								text : '导出外计划',
								hidden : me.isHidden
							}, {
								itemId : 'changeOrder2PaperJY',
								text : '转手工单',
								hidden : me.isHidden
							}, {
								itemId : 'stockOrder',
								text : '成品现货',
								hidden : me.isHidden
							}, '->', {
								xtype : 'panel',
								html : '<div style="background-color:#DFEAF2;width:350px;height:19px;">'
										+ '<span class="x-grid-record-grey-color" style="display:block;float:right;">已取消</span>' // 灰色
										+ '<span class="x-grid-record-green-color" style="display:block;float:right;">已完成,</span>' // 绿色
										+ '<span class="x-grid-record-blue-color" style="display:block;float:right;">生产中,</span>' // 蓝色
										+ '<span class="x-grid-record-cyan-color" style="display:block;float:right;">成品现货,</span>' // 青色
										+ '<span class="x-grid-record-purple-color" style="display:block;float:right;">手工单,</span>' // 紫色
										+ '<span class="x-grid-record-green-color" style="display:block;float:right;">计划已报,</span>' // 绿色
										+ '<span class="x-grid-record-orange-color" style="display:block;float:right;">外计划,</span>' // 橘黄色
										+ '</div>'
							}]
				}]
			}]
		}];
		me.dockedItems = dockedItems;
		me.columns = columns;
		me.callParent(arguments);
	}
	
});

/**
 * 显示原材料库存 订单id
 */
function showMaterialsInventoryWin(orderItemIds) {
	var win = Ext.create('bsmes.view.InventoryGridWindow', {
				orderItemIds : orderItemIds,
				section : '绝缘'
			});
	win.show();
};
