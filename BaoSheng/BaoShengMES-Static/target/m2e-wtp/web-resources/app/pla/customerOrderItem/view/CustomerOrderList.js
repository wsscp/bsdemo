Ext.define('bsmes.view.CustomerOrderList', {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.customerOrderList',
	store : 'CustomerOrderStore',
	plugins : [{
				ptype : 'rowexpander',
				rowBodyTpl : ['<div id="{id}">', '</div>']
			}],
	defaultEditingPlugin : false,
	selType : 'rowmodel',
	viewConfig : {
		getRowClass : function(record, rowIndex, rowParams, store) {
			var status = record.get(("status")); // 状态
			var customerOaDate = record.get("customerOaDate"); // 客户交货期
			var oaDate = record.get(("oaDate")); // 上次计算计划完成时间
			var lastOa = record.get(("lastOa")); // 上次计算计划完成时间
			if (status == 'FINISHED') { // 已完成：绿色
				return 'x-grid-record-green-color';
			} else if (status == 'CANCELED') { // 已完成：灰色
				return 'x-grid-record-grey-color';
			} else if (customerOaDate != null && customerOaDate < new Date()) { // 超客户交期：红色
				return 'x-grid-record-red-color';
			} else if (record.get("seq") > 0) { // 优先：紫色
				className = 'x-grid-record-purple-color';
			} else if (oaDate != null && lastOa != null && oaDate > lastOa) { // 超上次OA计算时间：橘黄色
				className = 'x-grid-record-orange-color';
			}
		}
	},
	columns : [{
				text : Oit.msg.pla.customerOrderItem.customerContractNO,
				dataIndex : 'contractNo',
				sortable : false,
				menuDisabled : true,
				flex : 1.5
			}, {
				text : Oit.msg.pla.customerOrderItem.customerCompany,
				dataIndex : 'customerCompany',
				sortable : false,
				menuDisabled : true,
				flex : 2
			}, {
				text : Oit.msg.pla.customerOrderItem.operator,
				dataIndex : 'operator',
				sortable : false,
				menuDisabled : true,
				flex : 1
			}, {
				text : Oit.msg.pla.customerOrderItem.customerOaDate,
				dataIndex : 'customerOaDate',
				xtype : 'datecolumn',
				format : 'Y-m-d',
				sortable : false,
				menuDisabled : true,
				flex : 1
			}, {
				text : Oit.msg.pla.customerOrderItem.attach,
				dataIndex : 'orderFileNum',
				sortable : false,
				menuDisabled : true,
				flex : 0.5,
				renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
					var nofile = (value == 0);
					var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"'
							+ (nofile ? '' : 'onclick="lookUpAttachFileWindow(\'' + record.get('contractNo') + '\')"')
							+ '>附件</a>';
					return html;
				}
			}, {
				text : Oit.msg.pla.customerOrderItem.oaDate,
				dataIndex : 'oaDate',
				xtype : 'datecolumn',
				hidden : true,
				format : 'Y-m-d',
				flex : 1
			}, {
				text : Oit.msg.pla.customerOrderItem.planStartDate,
				dataIndex : 'planStartDate',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i',
				sortable : false,
				menuDisabled : true,
				flex : 1
			}, {
				text : Oit.msg.pla.customerOrderItem.planFinishDate,
				dataIndex : 'planFinishDate',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i',
				sortable : false,
				menuDisabled : true,
				flex : 1
			}, {
				text : Oit.msg.pla.customerOrderItem.lastOa,
				dataIndex : 'lastOa',
				xtype : 'datecolumn',
				format : 'Y-m-d H:i',
				hidden : true,
				sortable : false,
				menuDisabled : true,
				flex : 1
			}, {
				text : Oit.msg.pla.customerOrderItem.fixedOa,
				dataIndex : 'fixedOaText',
				hidden : true,
				sortable : false,
				menuDisabled : true,
				flex : 1
			}, {
				text : Oit.msg.pla.customerOrderItem.orderStatus,
				dataIndex : 'status',
				sortable : false,
				menuDisabled : true,
				flex : 1,
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
			}],
	dockedItems : [{
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
				defaultType : 'textfield',
				items : [{
							fieldLabel : Oit.msg.pla.customerOrderItem.customerContractNO,
							name : 'contractNo'
						}, {
							fieldLabel : Oit.msg.pla.customerOrderItem.customerCompany,
							name : 'customerCompany'
						}, {
							fieldLabel : Oit.msg.pla.customerOrderItem.operator,
							name : 'operator'
						}, {
							fieldLabel : Oit.msg.pla.customerOrderItem.custProductType,
							name : 'custProductType'
						}, {
							fieldLabel : Oit.msg.pla.customerOrderItem.custProductSpec,
							name : 'custProductSpec'
						}, {
							fieldLabel : Oit.msg.pla.customerOrderItem.planDate,
							xtype : 'datefield',
							format : 'Y-m-d',
							name : 'startDate'
						}, {
							fieldLabel : Oit.msg.pla.customerOrderItem.end,
							xtype : 'datefield',
							format : 'Y-m-d',
							name : 'endDate'
						}, {
							fieldLabel : '是否超交期',
							xtype : 'checkboxgroup',
							vertical : true,
							items : [{
										boxLabel : '超交期',
										name : 'exceedOa',
										inputValue : 'PASS',
										checked : true
									}, {
										boxLabel : '未超交期',
										name : 'exceedOa',
										inputValue : 'NOPASS',
										checked : true
									}]
						}, {
							fieldLabel : Oit.msg.pla.customerOrderItem.orderStatus,
							xtype : 'checkboxgroup',
							width : 600,
							columns : 5,
							vertical : true,
							items : [{
										boxLabel : '新增待审核',
										name : 'orderStatus',
										inputValue : 'NEW_TO_EXAMINE'
									}, {
										boxLabel : '未开始',
										name : 'orderStatus',
										inputValue : 'TO_DO',
										checked : true
									}, {
										boxLabel : '生产中',
										name : 'orderStatus',
										inputValue : 'IN_PROGRESS'
									}, {
										boxLabel : '已取消',
										name : 'orderStatus',
										inputValue : 'CANCELED'
									}, {
										boxLabel : '已完成',
										name : 'orderStatus',
										inputValue : 'FINISHED'
									}

							]
						}

				],
				buttons : [{
							itemId : 'search'
						}, {
							itemId : 'reset'
						}, {
							itemId : 'importOrder',
							text : Oit.msg.pla.customerOrderItem.button.importOrder
						}, {
							itemId : 'setPriority',
							text : Oit.msg.pla.customerOrderItem.button.setPriority
						}, {
							itemId : 'setDelivery',
							text : Oit.msg.pla.customerOrderItem.button.setDelivery
						}, {
							itemId : 'confirmOrderDelivery',
							text : Oit.msg.pla.customerOrderItem.button.confirmOrderDelivery
						}, {
							itemId : 'export',
							text : Oit.btn.export
						}, '->', {
							xtype : 'panel',
							html : '<div style="background-color:#DFEAF2;width:370px;height:19px;">'
									+ '<span class="x-grid-record-grey-color" style="display:block;float:right;">已取消</span>' // 灰色
									+ '<span class="x-grid-record-green-color" style="display:block;float:right;">已完成,</span>' // 绿色
									+ '<span class="x-grid-record-red-color" style="display:block;float:right;color:#FF0000;">超客户指定交期,</span>'
									+ '<span class="x-grid-record-orange-color" style="display:block;float:right;color:#FAA500;">超上次计划完工日期,</span>'
									+ '<span class="x-grid-record-purple-color" style="display:block;float:right;color:#008000;">优先,</span>'
									+ '<span style="display:block;float:right;">正常,</span></div>'
						}]
			}]
		}]
	}],
	initComponent : function() {
		var me = this;
		this.callParent(arguments);
		me.view.on('expandBody', function(rowNode, record, expandRow, eOpts) {
					var renderId = record.get('id');
					var url = "customerOrderItem/findOrderItemInfo/" + renderId;
					var subGrid = Ext.create("bsmes.view.CustomerOrderItemList", {
								renderTo : renderId,
								store : Ext.create('bsmes.store.CustomerOrderItemStore', {
											customerOaDate : record.get('customerOaDate')
										})

							});
					var subStore = subGrid.getStore();
					subStore.getProxy().url = url;
					subStore.reload();

					subGrid.getEl().swallowEvent(['mousedown', 'mouseup', 'click', 'contextmenu', 'dblclick',
							'mousemove']);

					subGrid.on('toOpenSetSubOaDateWindow', me.doOpenSetSubOaDateWindow, me);
					subGrid.on('toSplitItem', me.doSplitItem, me);
					subGrid.on('toSplitItemToDec', me.doSplitItemToDec, me);
					subGrid.on('toCraftDetail', me.doCraftDetail, me);

				});
		me.view.on('collapsebody', function(rowNode, record, expandRow, eOpts) {
					var parent = document.getElementById(record.get('id'));
					var child = parent.firstChild;
					while (child) {
						child.parentNode.removeChild(child);
						child = child.nextSibling;
					}
				});
	},
	doOpenSetSubOaDateWindow : function(data, grid) {
		var window = Ext.create('bsmes.view.SetSubOaDateWindow', {
					parentGrid : grid,
					parentOaDate : grid.getStore().customerOaDate
				});
		var form = window.items.items[0];
		form.loadRecord(data);
		window.show();
	},
	doSplitItem : function(grid, rowIndex) {
		var data = grid.getStore().getAt(rowIndex);
		if (data.get('orderLength') < data.get('salesOrderItem.idealMinLength') * 2) {
			Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.customerOrderItem.msg.orderLengthIsTooShort);
			return;
		}
		var window = Ext.create('bsmes.view.ItemSplitWindow', {
					parentGrid : grid,
					idealMinLength : data.get('salesOrderItem.idealMinLength'),
					maxLength : data.get('orderLength') - data.get('salesOrderItem.idealMinLength'),
					orderLength : data.get('orderLength'),
					contractAmount : data.get('salesOrderItem.contractAmount')
				});
		var form = window.items.items[0];
		form.loadRecord(data);
		window.show();
	},
	doSplitItemToDec : function(grid, rowIndex) {
		var window = Ext.create('bsmes.view.SplitOrderWindow', {
					record : grid.getStore().getAt(rowIndex)
				});
		window.show();
	},
	doCraftDetail : function(record) {
		Ext.Msg.wait(Oit.msg.LOADING, Oit.msg.PROMPT);
		Ext.Ajax.request({
			url : 'customerOrderItem/findCraftProcess',
			method : 'GET',
			params : {
				'craftsId' : record.get('craftsId')
			},
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				// map.put("crafts", productCraftsWip);
				// map.put("processArray", jsonArray);
				var result = Ext.decode(response.responseText);
				if (record.get('craftsId') && record.get('craftsId') != '') {
					var window = Ext.create('bsmes.view.CraftDetailWindow', {
						title : Oit.msg.pla.customerOrderItem.button.craftDetail + '(' + result.crafts.craftsCode + ')',
						processArray : result.processArray
					});

					window.show();
				} else {
					Ext.Msg.alert('警告', '没有绑定工艺，无法查看');
				}
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
			}
		});
	}
});

/**
 * @param result 提交的结果集
 * @param length 卷长度
 * @param id 卷Id
 * @param disabled 是否可操作
 * @returns {___anonymous8670_10546}
 */
function formfieldObject(result, length, id, disabled) {
	return {
		xtype : 'panel',
		layout : 'hbox',
		bodyPadding : '5 5 5 5',
		height : 30,
		items : [{
					xtype : 'numberfield',
					fieldLabel : Oit.msg.pla.customerOrderItem.itemDecLength,
					name : 'length',
					value : length,
					disabled : disabled,
					oldValue : length
				}, {
					xytpe : 'hiddenfield',
					name : 'id',
					value : id
				}, {
					xtype : 'component',
					width : 10
				}, {
					xtype : 'button',
					text : Oit.btn.remove,
					disabled : disabled,
					handler : function(e) {
						var panel = Ext.ComponentQuery.query("#splitVolumePanel")[0];
						var fieldArray = this.up('panel').items.items;
						console.log(fieldArray);
						if (id) {
							var subLength = fieldArray[0].getValue();// 卷长度
							var itemDecId = fieldArray[1].value; // 卷ID
							result.push({
										length : subLength,
										id : itemDecId,
										idDelete : true
									});
						}
						panel.remove(this.up('panel'));
					}
				}]
	};
}

// 查看附件
function lookUpAttachFileWindow(contractNo) {
	Ext.Msg.wait(Oit.msg.LOADING, Oit.msg.PROMPT);
	// 1、请求后台查询
	Ext.Ajax.request({
				url : 'handSchedule/lookUpAttachFile?contractNo=' + contractNo,
				success : function(response) {
					Ext.Msg.hide(); // 隐藏进度条
					var data = Ext.decode(response.responseText);
					// 2、数据处理，按合同号归类
					var gridDataMap = {};
					Ext.Array.each(data, function(record, i) {
								if (gridDataMap[record.contractNo]) {
									gridDataMap[record.contractNo].push(record);
								} else {
									gridDataMap[record.contractNo] = [record];

								}
							});

					if (isEmptyObject(gridDataMap)) {
						Ext.Msg.alert(Oit.msg.WARN, '没有附件');
						return;
					}
					// 3、弹出窗口显示附件列表
					var wint = Ext.create('bsmes.view.LookUpAttachFileWindow', {
								gridDataMap : gridDataMap
							});
					wint.show();
				}
			});
}

// 判断指定参数是否是一个空对象
function isEmptyObject(obj) {
	var name;
	for (name in obj) {
		return false;
	}
	return true;
}