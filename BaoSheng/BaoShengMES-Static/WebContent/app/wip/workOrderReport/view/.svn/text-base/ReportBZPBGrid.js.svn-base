Ext.define("bsmes.view.ReportBZPBGrid", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.reportBZPBGrid',
			store : 'ReportBZPBStore',
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
						dataIndex : 'shiftName',
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
						flex : 1.6,
						minWidth : 80,
						text : '外层方向',
						dataIndex : 'outerPosition',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '包带方向',
						dataIndex : 'beltPosition',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '铜丝覆盖率或铜带重叠率',
						dataIndex : 'cuCoverLevel',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : '制造长度',
						columns : [{
									flex : 1.6,
									minWidth : 80,
									text : '计划长度格',
									dataIndex : 'planLength',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}, {
									flex : 1.6,
									minWidth : 80,
									text : '实际长度',
									dataIndex : 'realLength',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}]
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '检验',
						dataIndex : 'testing',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '质量',
						dataIndex : 'quality',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '工时',
						dataIndex : 'workHours',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '挡班',
						dataIndex : 'dbWorker',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '副挡班',
						dataIndex : 'fdbWorker',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						flex : 1.6,
						minWidth : 80,
						text : '辅助工',
						dataIndex : 'fzgWorker',
						renderer : function(value, metaData, record) {
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : '原材料消耗情况',
						columns : [{
									flex : 1.6,
									minWidth : 80,
									text : '种类',
									dataIndex : 'kind',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}, {
									flex : 1.6,
									minWidth : 80,
									text : '上班存盘',
									dataIndex : 'preLeave',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}, {
									flex : 1.6,
									minWidth : 80,
									text : '本班领用',
									dataIndex : 'thisTake',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}, {
									flex : 1.6,
									minWidth : 80,
									text : '本班退用',
									dataIndex : 'thisBack',
									renderer : function(value, metaData, record) {
										metaData.tdAttr = 'data-qtip="' + value + '"';
										return value;
									}
								}]
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
								var queryDate = Ext.ComponentQuery.query('reportBZPBGrid [itemId=exportReportDate]')[0]
										.getRawValue();
								var shiftId = Ext.ComponentQuery.query('reportBZPBGrid [itemId=shiftId]')[0].getValue();

								if (queryDate == '') {
									Ext.Msg.alert(Oit.msg.wip.title.fail, '请选择生产日期！');
								} else {
									var url = 'workOrderReportExport/bzpb/' + queryDate;
									if (shiftId == null) {
										url = url + '/编织屏蔽生产记录/-1';
									} else {
										url = url + '/编织屏蔽生产记录/' + shiftId;
									}
									window.location.href = url;
								}
							}
						}]
			}]

		});