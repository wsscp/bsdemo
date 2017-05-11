Ext.define("bsmes.view.RealEquipStatusChartList", {
			extend : 'Ext.panel.Panel',
			alias : 'widget.realEquipStatusChartList',
			autoScroll : true,
			initComponent : function() {
				var me = this;
				var tabTitle = Oit.msg.wip.realTimeEquipmentStatus.timeDiagram;
				var code = Ext.fly('equipInfo').getAttribute('code'); // 设备编码
				var equipAlias = Ext.fly('equipInfo').getAttribute('equipAlias'); // 设备别名
				var name = Ext.fly('equipInfo').getAttribute('equipName'); // 设备名称
				var status = ''; // 设备状态
				var product = ''; // 产品名称合同号
				Ext.Ajax.request({
							url : 'workOrder/getProductByEquipCode?equipCode=' + code,
							async : false,
							success : function(response) {
								var responseText = Ext.decode(response.responseText);
								product = responseText.product;
								status = responseText.status;
							}
						});

				switch (status) {
					case 'IN_PROGRESS' :
						status = '加工中';
						break;
					case 'IN_DEBUG' :
						status = '调试';
						break;
					case 'IDLE' :
						status = '待机';
						break;
					case 'CLOSED' :
						status = '关机';
						break;
					case 'ERROR' :
						status = '故障';
						break;
					case 'IN_MAINTAIN' :
						status = '保养';
						break;
					default :
						status = '待机';
				};

				var tabPanel = new Ext.TabPanel({
							activeTab : 0,
							tabPosition : 'top',
							items : [{
										xtype : 'equipmentStatusChartColumn'
									}, {
										xtype : 'equipmentStatusChartLine'
									}, {
										xtype : 'equipmentStatusChartYield'
									},{
										xtype : 'equipmentStatusChartOEE'
									}, {
										title : Oit.msg.wip.realTimeEquipmentStatus.equipCraftsMonitor,
										autoScroll : true,
										itemId : 'processReceiptTraceView',
										items : Ext.create('bsmes.view.ProcessReceiptTraceView')
									}
							// ,{
							// title:'设备实时加工信息',
							// items:Ext.create('bsmes.view.EquipRealTimeInfoView')
							// }
							],
							listeners : {
								'beforetabchange' : function(tab, newTab, currentTab) {
									if (newTab.title == '设备实时加工信息') {
										$('#openNewWinBtn').hide();
									} else {
										$('#openNewWinBtn').show();
									}

									var url = '/bsmes/wip/statusChart/' + Ext.fly('equipInfo').getAttribute('code')
											+ '.action?title=' + newTab.title;
									$('#openNewWinBtn').attr('href', url);
								},
								'tabchange' : function(tab, newTab, currentTab) {
									if (newTab.itemId == 'processReceiptTraceView') { // 设备工艺参数监控：第一次默认显示R_Length的图标
										var grid = Ext.ComponentQuery.query('#processReceiptGrid')[0];
										for (var i = 0; i < grid.getStore().getCount(); i++) {
											var row = grid.getStore().getAt(i);
											if (row.get('receiptCode') == 'R_Length') {
												grid.getSelectionModel().select(row);
												grid.getController().itemClickTraceLive(grid, row);
												break;
											}
										}
									}
								}
							}
						})
				me.items = [{
					xtype : 'panel',
					layout : 'vbox',
					items : [{
						xtype : 'form',
						layout : 'hbox',
						margin : '5 0 5 0',
						defaults : {
							labelAlign : 'right',
							xtype : 'displayfield',
							labelWidth : 80
						},
						items : [{
									fieldLabel : '设备名称',
									labelWidth : 60,
									id : 'equipNameAll',
									value : (equipAlias == '' ? '' : (equipAlias + '-')) + name + '[' + code + ']'
								}, {
									fieldLabel : Oit.msg.wip.processReceiptTrace.status,
									value : status
								}, {
									fieldLabel : Oit.msg.wip.processReceiptTrace.productCode,
									value : product
								}, {
									xtype : 'button',
									text : '全屏显示',
									id : 'openNewWinBtn',
									href : '/bsmes/wip/statusChart/' + Ext.fly('equipInfo').getAttribute('code')
											+ '.action?title=' + tabTitle
								}]
					}, tabPanel]
				}];
				me.callParent(arguments);
			}
		});
