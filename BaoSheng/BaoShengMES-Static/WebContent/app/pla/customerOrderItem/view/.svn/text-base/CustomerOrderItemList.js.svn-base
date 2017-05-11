Ext.define('bsmes.view.CustomerOrderItemList', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.customerOrderItemList',
			columnLines : true,
			autoHeight : true,
			viewConfig : {
				getRowClass : function(record, rowIndex, rowParams, store) {
					var status = record.get(("status")); // 状态
					var customerOaDate = record.get("customerOaDate"); // 客户交货期
					var subOaDate = record.get(("subOaDate")); // 上次计算计划完成时间
					var lastOa = record.get(("lastOa")); // 上次计算计划完成时间
					if (status == 'FINISHED') { // 已完成：绿色
						return 'x-grid-record-green-color';
					} else if (status == 'CANCELED') { // 已完成：灰色
						return 'x-grid-record-grey-color';
					} else if (customerOaDate != null && customerOaDate < new Date()) { // 超客户交期：红色
						return 'x-grid-record-red-color';
					} else if (record.get("seq") > 0) { // 优先：紫色
						className = 'x-grid-record-purple-color';
					} else if (subOaDate != null && lastOa != null && subOaDate > lastOa) { // 超上次OA计算时间：橘黄色
						className = 'x-grid-record-orange-color';
					}
				}
			},
			columns : [{
						text : Oit.msg.pla.customerOrderItem.productTypeSpec,
						dataIndex : 'salesOrderItem.custProductType',
						sortable : false,
						menuDisabled : true,
						flex : 2,
						renderer : function(value, metaData, record) {
							value += ' ' + record.get('salesOrderItem.custProductSpec');
							return value;
						}
					}, {
						text : Oit.msg.pla.customerOrderItem.contractLength,
						dataIndex : 'contractLength',
						sortable : false,
						menuDisabled : true,
						flex : 0.8
					}, {
						text : Oit.msg.pla.customerOrderItem.contractAmount,
						dataIndex : 'salesOrderItem.contractAmount',
						sortable : false,
						menuDisabled : true,
						flex : 0.8
					}, {
						text : Oit.msg.pla.customerOrderItem.itemRemarks,
						dataIndex : 'remarks',
						sortable : false,
						menuDisabled : true,
						flex : 2.5,
						renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
							var processRequire = '技术要求:'
									+ (record.get('processRequire') == '' ? '无' : record.get('processRequire'));
							var remarks = '备注:' + (value == '' ? '无' : value);
							var showStr = processRequire + ';<br/>' + remarks;
							metaData.tdAttr = 'data-qtip="' + showStr + '"';
							return showStr;
						}

					}, {
						text : Oit.msg.pla.customerOrderItem.itemStatus,
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
					}, {
						text : Oit.msg.pla.customerOrderItem.customerOaDate,
						dataIndex : 'customerOaDate',
						xtype : 'datecolumn',
						format : 'Y-m-d',
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.planStartDate,
						dataIndex : 'planStartDate',
						xtype : 'datecolumn',
						format : 'Y-m-d',
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.planFinishDate,
						dataIndex : 'planFinishDate',
						xtype : 'datecolumn',
						format : 'Y-m-d',
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.wiresStructure,
						dataIndex : 'salesOrderItem.wiresStructure',
						hidden : true,
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.releaseDate,
						dataIndex : 'releaseDate',
						xtype : 'datecolumn',
						format : 'Y-m-d',
						hidden : true,
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.productDate,
						dataIndex : 'productDate',
						xtype : 'datecolumn',
						format : 'Y-m-d',
						hidden : true,
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.wiresLength,
						dataIndex : 'salesOrderItem.wiresLength',
						hidden : true,
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.productCode,
						dataIndex : 'salesOrderItem.productCode',
						hidden : true,
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.orderLength,
						dataIndex : 'orderLength',
						sortable : false,
						menuDisabled : true,
						hidden : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.customerOaDate,
						dataIndex : 'subOaDate',
						xtype : 'datecolumn',
						format : 'Y-m-d',
						hidden : true,
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.productType,
						dataIndex : 'salesOrderItem.productType',
						sortable : false,
						menuDisabled : true,
						hidden : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.productSpec,
						dataIndex : 'salesOrderItem.productSpec',
						sortable : false,
						menuDisabled : true,
						hidden : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.numberOfWires,
						dataIndex : 'salesOrderItem.numberOfWires',
						hidden : true,
						sortable : false,
						menuDisabled : true,
						flex : 1
					}, {
						text : Oit.msg.pla.customerOrderItem.section,
						dataIndex : 'salesOrderItem.section',
						hidden : true,
						sortable : false,
						menuDisabled : true,
						flex : 1
					}],
			actioncolumn : [{
				itemId : 'craftDetail'
					// }, '', {
					// itemId : 'itemSplitToDec'
					// }, '', {
					// itemId : 'setSubOaDate'
				}, ''/*
						 * , { itemId : 'itemSplit' }
						 */
			],
			initComponent : function() {
				var me = this;

				var defaultActionColumnItems = {
					'setSubOaDate' : {
						tooltip : Oit.msg.pla.customerOrderItem.button.setDelivery,
						iconCls : 'icon_deadline',
						handler : function(grid, rowIndex) {
							me.fireEvent('toOpenSetSubOaDateWindow', grid.getStore().getAt(rowIndex), grid);
						}
					},
					'itemSplit' : {
						tooltip : Oit.msg.pla.customerOrderItem.button.itemSplit,
						iconCls : 'icon_split',
						handler : function(grid, rowIndex) {
							var record = grid.getStore().getAt(rowIndex);
							if (record.data.status == 'TO_DO') {
								me.fireEvent('toSplitItem', grid, rowIndex);
							} else {
								Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.customerOrderItem.msg.splitMsg);
							}
						}
					},
					'itemSplitToDec' : {
						tooltip : Oit.msg.pla.customerOrderItem.button.orderItemSpilt,
						iconCls : 'icon_split_roll',
						handler : function(grid, rowIndex) {
							var record = grid.getStore().getAt(rowIndex);
							if (record.data.status == 'TO_DO') {
								me.fireEvent('toSplitItemToDec', grid, rowIndex);
								// orderItemSpilt(record);
							} else {
								Ext.Msg.alert(Oit.msg.WARN, Oit.msg.pla.customerOrderItem.msg.splitMsg);
							}
						}
					},
					'craftDetail' : {
						tooltip : Oit.msg.pla.customerOrderItem.button.craftDetail,
						iconCls : 'icon_detail',
						handler : function(grid, rowIndex) {
							var record = grid.getStore().getAt(rowIndex);
							console.log(grid)
							console.log(me)
							me.fireEvent('toCraftDetail', record);
						}
					}
				};
				/**
				 * @cfg {Object/Object[]} actioncolumn 表格中操作类的按钮
				 */
				if (me.actioncolumn) {
					Ext.each(me.actioncolumn, function(item) {
								Ext.applyIf(item, defaultActionColumnItems[item.itemId]);
							});
					if (me.columns[0].xtype != 'actioncolumn') {
						me.columns.unshift({
									menuDisabled : true,
									sortable : false,
									xtype : 'actioncolumn',
									width : 20 * me.actioncolumn.length,
									items : me.actioncolumn
								});
					}
				}

				me.callParent(arguments); // ------------call父类--------------

				// 更新render
				var fields = me.store.model.prototype.fields.items;
				Ext.each(fields, function(field) {
							var length = me.columns.length;
							for (i = 0; i < length; i++) {
								if (me.columns[i].dataIndex == field.name && !me.columns[i].renderer) {
									me.columns[i].renderer = field.renderer;
									break;
								}
							}
						});
			}
		});
