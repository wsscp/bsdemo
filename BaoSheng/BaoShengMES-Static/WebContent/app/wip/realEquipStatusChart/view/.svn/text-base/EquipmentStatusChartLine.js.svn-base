Ext.define("bsmes.view.EquipmentStatusChartLine", {
			extend : 'Ext.panel.Panel',
			alias : 'widget.equipmentStatusChartLine',
			title : Oit.msg.wip.realTimeEquipmentStatus.oEETradeAnalysis,
			chartPanelId : '', // 定义一个随机id
			lineHighChar : null, // 线图
			initComponent : function() {
				var me = this;
				me.chartPanelId = me.uuid(); // 定义一个随机id

				me.items = [{
							xtype : 'panel',
							id : me.chartPanelId,
							width : document.body.scrollWidth,
							height : document.body.scrollHeight - 120
						}];

				me.tbar = [{
							xtype : 'form',
							layout : 'column',
							defaults : {
								labelAlign : 'right',
								margin : '0 5 0 0'
							},
							items : [{
										fieldLabel : Oit.msg.wip.realTimeEquipmentStatus.startTime,
										xtype : 'datefield',
										name : 'startDate',
										value : Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.DAY, -7),
												"Y-m-d"),

										editable : false,
										format : 'Y-m-d'
									}, {
										fieldLabel : Oit.msg.wip.realTimeEquipmentStatus.endTime,
										xtype : 'datefield',
										name : 'endDate',
										value : Ext.util.Format.date(new Date(), "Y-m-d"),

										editable : false,
										format : 'Y-m-d'
									}, {
										xtype : 'radiogroup',
										vertical : true,
										name : 'type',
										width : 150,
										items : [{
													boxLabel : '天',
													name : 'type',
													inputValue : 'DAY',
													checked : true
												}, {
													boxLabel : '周',
													name : 'type',
													inputValue : 'WEEK'
												}, {
													boxLabel : '月',
													name : 'type',
													inputValue : 'MONTH'
												}]
									}]
						}/*, {
							text : Oit.btn.search,
							iconCls : 'icon_search',
							scope : me,
							handler : me.search
						}, {
							text : Oit.btn.reset,
							iconCls : 'icon_reset',
							handler : function(e) {
								this.up("equipmentStatusChartColumn").down('form').getForm().reset();
							}
						}*/];
				me.callParent(arguments);
			},

			listeners : {
				afterlayout : function(container, component, eOpts) {
					// console.log('--afterlayout--')
					// var startDate = endDate = Ext.util.Format.date(new Date(), "Y-m-d");
					// 1、刷新线图
					this.search();

					// this.freshLineHighChar(startDate + ' 00:00:00', endDate + ' 23:59:59', 'DAY');
				},
				afterrender : function(component, eOpts) {
					// console.log('--afterrender--')
				}
			},

			// 页面查询按钮
			search : function() {
				var me = this;
				var form = me.down('form').getForm();
				var startDate = form.findField('startDate').getRawValue() + ' 00:00:00';
				var endDate = form.findField('endDate').getRawValue() + ' 23:59:59';
				var type = form.findField('type').getValue();
				if (startDate > endDate) {
					Ext.Msg.alert('提示', '开始时间不能大于结束时间');
					return;
				}
				// console.log(type)

				// 1、刷新线图
				me.freshLineHighChar(startDate, endDate, type.type);
			},

			// 刷新加工时间：线图
			freshLineHighChar : function(startTime, endTime, type) {
				var me = this;
				Ext.Ajax.request({
							url : 'realEquipStatusChart/equipHistoryLine',
							method : 'GET',
							params : {
								equipCode : Ext.fly('equipInfo').getAttribute('code'),
								startTime : startTime,
								endTime : endTime,
								type : type
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								console.log(result);
								if (me.lineHighChar) {
									var series = me.lineHighChar.series;
									Ext.Array.each(series, function(serie, i) {
												Ext.Array.each(result, function(data, j) {
															if (serie.name == data.name) {
																serie.setData(data.data);
																return false;
															}
														});
											});
									// me.lineHighChar.series[0].setData(result[0].data);
									// console.log(111)
									// console.log(me.lineHighChar)
									// console.log(series)
									// .setData(result);
								} else {
									me.createLineHighChar(result);
								}
							}
						});
			},

			createLineHighChar : function(seriesData) {
				var me = this;
				me.lineHighChar = $('#' + me.chartPanelId).highcharts({
							title : {
								text : Oit.msg.wip.realTimeEquipmentStatus.equipEffectiveUse,
								x : -20
								// center
							},
							// subtitle : {
							// text : 'Source: WorldClimate.com',
							// x : -20
							// },
							xAxis : {
								type : 'category'
							},
							yAxis : {
								title : {
									text : Oit.msg.wip.realTimeEquipmentStatus.processTime
								},
								plotLines : [{
											value : 0,
											width : 1,
											color : '#808080'
										}]
							},
							tooltip : {
								valueSuffix : '小时'
							},
							legend : {
								layout : 'vertical',
								align : 'right',
								verticalAlign : 'middle',
								borderWidth : 0
							},
							credits : {
								enabled : false
								// 去除水印
							},
							exporting : {
								enabled : false
								// 去除导出
							},
							series : seriesData
						}).highcharts();
			},

			// 获取uuid
			uuid : function() {
				var s = [];
				var hexDigits = "0123456789abcdef";
				for (var i = 0; i < 36; i++) {
					s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
				}
				s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
				s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
				s[8] = s[13] = s[18] = s[23] = "-";

				var uuid = s.join("");
				return uuid;
			}
		});
