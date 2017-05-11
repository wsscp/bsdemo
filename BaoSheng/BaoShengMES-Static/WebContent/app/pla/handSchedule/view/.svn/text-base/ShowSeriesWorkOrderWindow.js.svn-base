Ext.define("bsmes.view.ShowSeriesWorkOrderWindow", {
			extend : 'Ext.window.Window',
			alias : 'widget.showSeriesWorkOrderWindow',
			width : document.body.scrollWidth,
			height : document.body.scrollHeight,
			preWorkOrderNo : null,
			modal : true,
			layout : 'fit',
			title : '层级查看',
			initComponent : function() {
				var me = this;
				me.items = [{
					xtype : 'grid',
					extend : 'Oit.app.view.Grid',
					defaultEditingPlugin : false,
					store : new Ext.data.Store({
								model : 'bsmes.model.WorkOrder',
								proxy : {
									type : 'rest',
									url : 'handSchedule/getSeriesWorkOrder'
								}
							}),
					columnLines : true,
					selType : 'checkboxmodel',
					selModel : {
						mode : "SINGLE" // "SINGLE"/"SIMPLE"/"MULTI"
					},
					viewConfig : {
//						stripeRows : false,
//						enableTextSelection : true,
						getRowClass : function(record, rowIndex, rowParams, store) {
							var className = '';
							if (record.get('workOrderNo') == me.preWorkOrderNo) {
								className += 'x-grid-record-red-color ';
							}
//							if (rowIndex % 2 != 0) { // 添加默认间隔样式
//								className += 'x-grid-row-alt ';
//							}
							return className;
						}
					},
					columns : [{
								text : '生产单号',
								dataIndex : 'workOrderNo',
								flex : 1.8,
								minWidth : 90,
								menuDisabled : true,
								renderer : function(value, metaData, record) {
									var isDispatch = record.get('isDispatch');
									metaData.tdAttr = 'data-qtip="' + value + (isDispatch ? '(急)' : '') + '"';
									value = value.substring(value.length - 6) + (isDispatch ? '<font color="red">(急)</font>' : '');
									return value;
								}
							}, {
								text : '合同号 - 客户型号规格 - 合同长度',
								dataIndex : 'contractNo',
								menuDisabled : true,
								sortable : false,
								flex : 5,
								minWidth : 250,
								renderer : function(value, metaData, record) {
									var machine = value.split(",");
									var res = '';
									for (var i = 0; i < machine.length; i++) {
										res = res + machine[i].split(';')[0] + "<br/>";
									}
									metaData.tdAttr = 'data-qtip="' + res + '"';
									return res;
								}
							}, {
								text : '生产线',
								dataIndex : 'equipName',
								flex : 3,
								minWidth : 150,
								menuDisabled : true,
								renderer : function(value, metaData, record) {
									var machine = value.split(",");
									var res = '', res1 = '';
									for (var i = 0; i < machine.length; i++) {
										res = res + '<a style="color:blue;cursor:pointer;" onclick="intoMachine(\'' + machine[i] + '\')">' + machine[i]
												+ '</a>' + '<br/>';
										res1 = res1 + machine[i] + '<br/>';
									}
									metaData.tdAttr = 'data-qtip="' + res1 + '"';
									return res;
								}
							}, {
								text : '工序',
								dataIndex : 'processName',
								flex : 1.6,
								minWidth : 80,
								menuDisabled : true,
								renderer : function(value, metaData, record) {
									metaData.tdAttr = 'data-qtip="' + value + '"';
									return value;
								}
							}, {
								text : '特殊分<br/>盘要求',
								dataIndex : 'specialReqSplit',
								flex : 1.3,
								minWidth : 65,
								sortable : false,
								menuDisabled : true,
								renderer : function(value, metaData, record) {
									// metaData.style =
									// "white-space:normal;word-break:break-all;padding:5px
									// 5px 5px 5px;";
									metaData.tdAttr = 'data-qtip="' + value + '"';
									return value;
								}
							}, {
								text : '总任<br/>务数',
								dataIndex : 'orderLength',
								flex : 1.2,
								minWidth : 60,
								menuDisabled : true,
								renderer : function(value, metaData, record) {
									metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
									metaData.tdAttr = 'data-qtip="' + value + '"';
									return value;
								}
							}, {
								text : '下发<br/>日期',
								dataIndex : 'releaseDate',
								xtype : 'datecolumn',
								flex : 1.2,
								minWidth : 60,
								menuDisabled : true,
								format : 'm-d'
							}, {
								text : '要求完<br/>成日期',
								dataIndex : 'requireFinishDate',
								xtype : 'datecolumn',
								format : 'm-d',
								flex : 1.2,
								minWidth : 60,
								menuDisabled : true
							}, {
								text : '实际完<br/>成日期',
								dataIndex : 'realEndTime',
								xtype : 'datecolumn',
								format : 'm-d',
								flex : 1.2,
								minWidth : 60,
								menuDisabled : true,
								hidden : true
							}, {
								text : '完成<br/>进度',
								dataIndex : 'percent',
								flex : 1,
								minWidth : 50,
								menuDisabled : true,
								sortable : false,
								renderer : function(value, metaData, record) {
									metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
									var html = '<a style="color:blue;cursor:pointer;" onclick="showPercentDetail(\'' + record.get('workOrderNo')
											+ '\', \'\', \'\')">' + value + '%</a>';
									metaData.tdAttr = 'data-qtip="' + value + '%"';
									return html;
								}
							}, {
								text : '附件',
								dataIndex : 'workOrderNo',
								flex : 1,
								minWidth : 50,
								renderer : function(value, metaData, record, rowIndex) {
									var nofile = (record.get('orderfilenum') == 0);
									var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"'
											+ (nofile ? '' : 'onclick="lookUpAttachFileWindow(\'\', \'' + value + '\', \'\')"') + '>附件</a>';
									return html;
								},
								sortable : false,
								menuDisabled : true
							}, {
								text : '备注&技术要求',
								dataIndex : 'userComment',
								flex : 5,
								minWidth : 250,
								menuDisabled : true,
								renderer : function(value, metaData, record, rowIndex) {
									var svalue = value.replace(/\n/g, "<br/>");
									metaData.tdAttr = 'data-qtip="' + svalue + '"';
									return '<a style="color:blue;cursor:pointer;" onclick="showremarks(\'' + svalue + '\')">' + value + '</a>';
								}
							}, {
								text : '状态',
								dataIndex : 'status',
								flex : 1.2,
								minWidth : 60,
								menuDisabled : true,
								renderer : function(value) {
									switch (value) {
										case 'TO_AUDIT' :
											return Oit.msg.pla.handSchedule.statusName.toAudit;
										case 'TO_DO' :
											return Oit.msg.pla.handSchedule.statusName.toDo;
										case 'IN_PROGRESS' :
											return Oit.msg.pla.handSchedule.statusName.inProgress;
										case 'FINISHED' :
											return Oit.msg.pla.handSchedule.statusName.finished;
										case 'PAUSE' :
											return Oit.msg.pla.handSchedule.statusName.pause;
										case 'CANCELED' :
											return Oit.msg.pla.handSchedule.statusName.canceled;
										default :
											return '';
									}
								}
							}],
					dockedItems : [{
								xtype : 'toolbar',
								dock : 'top',
								items : [{
											xtype : 'button',
											text : '详情',
											iconCls : 'icon_detail',
											itemId : 'showWorkOrderDetail'
										}, '->', {
											xtype : 'panel',
											html : '<div style="width:256px;height:19px;">'
													+ '<span style="display:block;float:right;color:red;">当前生产单</span></div>'
										}]
							}]
				}];

				Ext.apply(me, {
							buttons : ['->', {
										itemId : 'cancel',
										text : '关 闭',
										scope : me,
										handler : me.close
									}]
						});
				this.callParent(arguments);
			}
		});