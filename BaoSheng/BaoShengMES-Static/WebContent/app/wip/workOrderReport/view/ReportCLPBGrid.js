Ext.define("bsmes.view.ReportCLPBGrid", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.reportCLPBGrid',
			store : 'ReportCLPBStore',
			forceFit : false,
			defaultEditingPlugin : false,
			columns : [{
						flex : 1.6,
						minWidth : 80,
						text : '统计时间',
						dataIndex : 'reportDate',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '订单号',
						dataIndex : 'contractNo',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '生产单号',
						dataIndex : 'workOrderNo',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '机台',
						dataIndex : 'equipCode',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '班次',
						dataIndex : 'shiftId',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '型号规格',
						dataIndex : 'productType',
						renderer : function(value, metaData, record) {
							value = value + ' ' + record.get('productSpec');
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : '成缆外径mm',
						columns : [{
									flex : 1.6,
									minWidth : 80,
									text : '上',
									dataIndex : 'clOuterdiameterUp',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}, {
									flex : 1.6,
									minWidth : 80,
									text : '下',
									dataIndex : 'clOuterdiameterDown',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}]
					}, {
						text : '屏蔽外径mm',
						columns : [{
									flex : 1.6,
									minWidth : 80,
									text : '上',
									dataIndex : 'pbOuterdiameterUp',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}, {
									flex : 1.6,
									minWidth : 80,
									text : '下',
									dataIndex : 'pbOuterdiameterDown',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}]
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '节距',
						dataIndex : 'piceRange',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '屏蔽材料',
						dataIndex : 'pbMat',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '绕包质量',
						dataIndex : 'rbQuality',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '搭盖率%',
						dataIndex : 'coverLevel',
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;padding:5px 5px 5px 5px;";
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '产品自检',
						dataIndex : 'ownerTesting',
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;padding:5px 5px 5px 5px;";
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '长度',
						dataIndex : 'finishedLength',
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;padding:5px 5px 5px 5px;";
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '工时',
						dataIndex : 'workHours',
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;padding:5px 5px 5px 5px;";
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '挡班',
						dataIndex : 'dbWorker',
						renderer : function(value, metaData, record) {
							metaData.style = "white-space:normal;padding:5px 5px 5px 5px;";
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
							xtype : 'hform',
							items : [{
										fieldLabel : '生产日期',
										name : 'reportDate',
										xtype : 'datefield',
										itemId : 'exportReportDate',
										format : 'Y-m-d'
									}, {
										fieldLabel : '班次',
										name : 'shiftId',
										xtype : 'combobox',
										labelWidth : 30,
										margin : '0 0 0 50',
										itemId : 'shiftId',
										displayField : 'shiftName',
										valueField : 'id',
										store : Ext.create('Ext.data.Store', {
													fields : ['shiftName', 'id'],
													proxy : {
														type : 'rest',
														url : 'getWorkShiftCombo'
													}
												})
									}]
						}, {
							itemId : 'search'
						}, {
							itemId : 'exportReport',
							margin : '10 0 0 10',
							text : '导出生产记录',
							scope : this,
							handler : function() {
								var queryDate = Ext.ComponentQuery.query('reportCLPBGrid [itemId=exportReportDate]')[0]
										.getRawValue();
								var shiftId = Ext.ComponentQuery.query('reportCLPBGrid [itemId=shiftId]')[0].getValue();
								if (queryDate == '') {
									Ext.Msg.alert(Oit.msg.wip.title.fail, '请选择生产日期！');
								} else {
									var url = 'workOrderReportExport/clpb/' + queryDate;
									if (shiftId == null) {
										url = url + '/成缆屏蔽生产记录/-1';
									} else {
										url = url + '/成缆屏蔽生产记录/' + shiftId;
									}
									window.location.href = url;
								}
							}
						}]
			}]

		});