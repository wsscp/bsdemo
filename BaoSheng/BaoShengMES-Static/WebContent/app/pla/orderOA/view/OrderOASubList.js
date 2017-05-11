Ext.define('bsmes.view.OrderOASubList', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.orderOASubList',
			columnLines : true,
			width : document.body.scrollWidth - 60,
			border : 1,
			columns : [{
						text : Oit.msg.pla.orderOA.processName,
						dataIndex : 'processName',
						flex : 1.6,
						minWidth : 80,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : Oit.msg.pla.orderOA.equipCode,
						dataIndex : 'equipName',
						flex : 3,
						minWidth : 150,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : Oit.msg.pla.orderOA.halfProductCode,
						dataIndex : 'halfProductCode',
						flex : 4,
						minWidth : 200,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : Oit.msg.pla.orderOA.eachLength,
						dataIndex : 'length',
						flex : 1.2,
						minWidth : 60,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : Oit.msg.pla.orderOA.finishedLength,
						dataIndex : 'finishedLength',
						flex : 1.2,
						minWidth : 60,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : Oit.msg.pla.orderOA.procesStatus,
						dataIndex : 'status',
						flex : 1.2,
						minWidth : 60,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : Oit.msg.pla.orderOA.userdLockLength,
						dataIndex : 'usedLockLength',
						hidden : true,
						flex : 1,
						minWidth : 50,
						menuDisabled : true,
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : Oit.msg.pla.orderOA.planStartDate,
						dataIndex : 'latestStartDate',
						xtype : 'datecolumn',
						format : 'm-d H:i:s',
						flex : 2,
						minWidth : 100,
						menuDisabled : true
					}, {
						text : Oit.msg.pla.orderOA.planFinishDate,
						dataIndex : 'latestFinishDate',
						xtype : 'datecolumn',
						format : 'm-d H:i:s',
						flex : 2,
						minWidth : 100,
						menuDisabled : true
					}, {
						text : Oit.msg.pla.orderOA.planWorkHours,
						dataIndex : 'planWorkHours',
						flex : 1.7,
						minWidth : 85,
						menuDisabled : true,
						renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
							console.log(value)
							if (value && value != 0) {
								var hour = parseInt(value / 3600);
								var minute;
								var second;
								if (hour > 0) {
									var temp = parseInt(value % 3600);
									if(temp != 0){
										if(temp >60){
											minute = parseInt(temp / 60);
											second = parseInt(temp % 60)
											value = hour + '时' + minute + '分' + second + '秒';
										}else{
											minute = 0;
											second = temp;
											value = hour + '时' + minute + '分' + second + '秒';
										}
									}else{
										value = hour + '时';
									}
								}else{
									var temp = parseInt(value % 3600);
									if(temp >60){
										minute = parseInt(temp / 60);
										second = parseInt(temp % 60)
										value = minute + '分' + second + '秒';
									}else{
										second = temp;
										value = second + '秒';
									}
								}
							}
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}],
			actioncolumn : [{
						itemId : 'startIdleEquip'
					}],
			initComponent : function() {
				var me = this;

				var defaultActionColumnItems = {
					'startIdleEquip' : {
						tooltip : Oit.msg.pla.orderOA.setIdleEquip,
						iconCls : 'icon_edit',
						handler : function(grid, rowIndex) {
							me.fireEvent('toOpenSetWindow', grid.getStore().getAt(rowIndex), grid);
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
									width : 30 * me.actioncolumn.length,
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