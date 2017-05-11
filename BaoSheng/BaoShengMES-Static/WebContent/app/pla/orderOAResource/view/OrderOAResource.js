Ext.Loader.setConfig({
			enabled : true,
			disableCaching : true
		});
var stratDate = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);;
var endDate = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate() + 7, 23, 59, 59);
PerformanceBarTemplate = function() {
	return new Ext.XTemplate('<div class="sch-percent-allocated-bar" style="width:{percentDone};height:100%"></div><span class="sch-percent-allocated-text">{[values.percentDone||0]}</span>');
};

Ext.define("bsmes.view.OrderOAResource", {
	extend : "Sch.panel.SchedulerTree",
	alias : 'widget.orderOAResource',
	id : 'orderOAResourceId',
	resourceStore : 'OrderOAResourceStore',
	eventStore : 'OrderOAEventStore',
	rowHeight : 28,
	infiniteScroll : false,
	eventBorderWidth : 1,
	columnLines : true,
	rowLines : true,
	highlightWeekends : true,
	readOnly : true,
	loadMask : true,
	width : 1360,
	height : 680,
	startDate : stratDate,
	endDate : endDate,
	tipCfg : {
		autoHide : false
	},
	viewPreset : 'hourAndDayOit',
	columns : [{
		xtype : 'treecolumn',
		text : '合同/产品工序/设备信息',
		sortable : false,
		dataIndex : 'Name',
		flex : 3.6,
		minWidth : 180,
		renderer : function(v, meta, r) {
			if (!r.data.leaf) {
				meta.tdCls = 'group-title';
			}
			meta.tdAttr = 'data-qtip="' + v + '"';
			return v;
		},
		listeners : {
			'click' : function(v, record, item, index, e, eOpts) {
				var val = eOpts.data.Id;
				var order = Ext.getCmp('orderOAResourceId');
				order.getSchedulingView().scrollEventIntoView(
						order.eventStore.findRecord('resourceId', val, 0, false, false, true), true, true);
			}
		}
	}, {
		dataIndex : 'percentDone',
		sortable : false,
		text : '进度<br/>(%)',
		flex : 0.8,
		minWidth : 40
	}, {
		dataIndex : 'outPut',
		sortable : false,
		text : '产出',
		flex : 1.8,
		minWidth : 90,
		renderer : function(v, meta, r) {
			meta.tdAttr = 'data-qtip="' + v + '"';
			return v;
		}
	}, {
		dataIndex : 'startDate',
		format : 'm-d H:i',
		xtype : 'datecolumn',
		sortable : false,
		text : '开始时间',
		flex : 1.8,
		minWidth : 90
	}, {
		dataIndex : 'endDate',
		sortable : false,
		xtype : 'datecolumn',
		format : 'm-d H:i',
		text : '结束时间',
		flex : 1.8,
		minWidth : 90
	}],
	eventRenderer : function(item, r, tplData, row) {
		var bgColor;
		var height;
		var marginTop;
		var level = item.get('level');
		if (level == 0) {
			bgColor = 'red';
			height = 15;
			marginTop = 6;
		} else if (level == 1) {
			bgColor = 'yellow';
			height = 15;
			marginTop = 6;
		} else if (level == 2) {
			bgColor = 'purple';
			height = 15;
			marginTop = 6;
		} else if (level == 3) {
			bgColor = 'blue';
			height = 25;
			marginTop = 0;
		}
		tplData.style = "background-color:" + bgColor + ";height:" + height + ";margin-top:" + marginTop + ";";// +
		// "background:
		// url('images/graytexture.png')
		// repeat-x
		// scroll
		// 0 0
		// DarkCyan;";

		// tplData.cls = 'duration';
		return {
			percentDone : item.get('percentDone') + '%'
		};
		// return item.get('percentDone')+'%';
	},
	eventBodyTemplate : new PerformanceBarTemplate(),
	lockedGridConfig : {
		resizeHandles : 'e',
		width : 493,
		resizable : {
			pinned : true
		}
	},
	schedulerConfig : {
		scroll : true,
		columnLines : false,
		flex : 0
	},
	tbar : ['过滤条件:', {
				xtype : 'triggerfield',
				enableKeyEvents : true,
				emptyText : '输入后请回车...',
				width : 180,
				triggerCls : 'x-form-clear-trigger',
				onTriggerClick : function() {
					this.setValue('');
					var resourceStore = Ext.ComponentQuery.query('orderOAResource')[0].view.getStore();
					resourceStore.clearTreeFilter();
				},
				listeners : {
					keyup : function(text, e, eOpts) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							if (text.getValue() != '' && text.getValue() != undefined) {
								var resourceStore = Ext.ComponentQuery.query('orderOAResource')[0].view.getStore();
								var newValue = text.getValue();
								if (newValue) {
									newValue = newValue.replace(/(^\s*)|(\s*$)/g, "");

									var regexps = Ext.Array.map(newValue.split(/\s+/), function(token) {
												return new RegExp(Ext.String.escapeRegex(token), 'i');
											});
									var length = regexps.length;
									resourceStore.filterTreeBy({
												filter : function(resource) {
													var name = resource.get('Name');
													for (var i = 0; i < length; i++) {
														if (regexps[i].test(name)) {
															return true;
														}
													}
													return false;
												},
												checkParents : true
											});
								} else {
									resourceStore.clearTreeFilter();
								}
							} else {
								var resourceStore = Ext.ComponentQuery.query('orderOAResource')[0].view.getStore();
								resourceStore.clearTreeFilter();
							}
						} else {
							if (text.getValue() == '' || text.getValue() == ' ' || text.getValue() == undefined) {
								var resourceStore = Ext.ComponentQuery.query('orderOAResource')[0].view.getStore();
								resourceStore.clearTreeFilter();
								this.setValue('');
							}

						}

					},
					specialkey : function(field, e, t) {
						if (e.keyCode === e.ESC)
							field.reset();
					}
				}
			}, {
				text : '',
				iconCls : 'expandAll',
				handler : function() {
					Ext.ComponentQuery.query('orderOAResource')[0].expandAll();
				}
			}, {
				text : '',
				iconCls : 'collapseAll',
				handler : function() {
					Ext.ComponentQuery.query('orderOAResource')[0].collapseAll();
				}
			}, '计划开工日期 从：', {

				xtype : 'datefield',
				name : 'fromDate',
				width : 110,
				value : stratDate,
				id : 'orderOaResourcequeryFromDate',
				format : 'Y-m-d'
			}, '到:', {

				xtype : 'datefield',
				name : 'toDate',
				id : 'orderOaResourcequeryToDate',
				value : endDate,
				width : 110,
				format : 'Y-m-d'
			}, {
				text : '按时间查询',
				iconCls : 'goSsearch',
				id : 'goSearchButtonid',
				listeners : {
					click : function() {
						// / var startDate=new Date(new Date().getFullYear(),new
						// Date().getMonth(),new Date().getDate()+10,0,0,0) ;
						// / var endDate=Sch.util.Date.add(new Date(),
						// Sch.util.Date.WEEK,17);
						var startDate = Ext.getCmp("orderOaResourcequeryFromDate").getValue();
						if (startDate == null) {
							Ext.MessageBox.alert("提示", "请输入开始日期!");
							return;
						}

						var endDDate = Ext.getCmp("orderOaResourcequeryToDate").getValue();
						if (endDDate == null) {
							Ext.MessageBox.alert("提示", "请输入截止日期!");
							return;
						}
						if (endDDate < startDate) {
							Ext.MessageBox.alert("提示", "开始日期必须小于截止日期!");
							return;
						}
						endDDate = new Date(endDDate.getFullYear(), endDDate.getMonth(), endDDate.getDate(), 23, 59, 59);

						Ext.ComponentQuery.query('orderOAResource')[0].getResourceStore().load({
									params : {
										"fromDate" : startDate,
										"toDate" : endDDate
									}
								});
						Ext.ComponentQuery.query('orderOAResource')[0].getEventStore().load({
									params : {
										"fromDate" : startDate,
										"toDate" : endDDate
									}
								});
						stratDate = startDate;
						endDate = endDDate;
						Ext.ComponentQuery.query('orderOAResource')[0].switchViewPreset('hourAndDayOit', startDate,
								endDDate);
						// Ext.ComponentQuery.query('orderOAResource')[0].getEventStore(
						// ).reload();

					}
				}
				// handler : function () {}
			}, {
				text : '放大',
				iconCls : 'zoomIn',
				handler : function() {

					var o = Ext.ComponentQuery.query('orderOAResource')[0].zoomIn();
					if (!o) {
						Ext.MessageBox.alert("提示", "视图不能再放大了 !");
					}
				}
			}, {
				text : '缩小',
				iconCls : 'zoomOut',
				handler : function() {
					var o = Ext.ComponentQuery.query('orderOAResource')[0].zoomOut();
					if (!o) {
						Ext.MessageBox.alert("提示", "视图不能再缩小了 !");
					}
				}
			}, {
				text : '按分钟',
				toggleGroup : 'presets',
				enableToggle : true,
				iconCls : 'icon-calendar',
				handler : function() {
					Ext.ComponentQuery.query('orderOAResource')[0]
							.switchViewPreset('minuteAndHour', stratDate, endDate);
				}
			}, {
				text : '按小时',
				toggleGroup : 'presets',
				enableToggle : true,
				iconCls : 'icon-calendar',
				handler : function() {
					Ext.ComponentQuery.query('orderOAResource')[0].switchViewPreset('hourAndDay', stratDate, endDate);
				}
			}, {
				text : '按天',
				toggleGroup : 'presets',
				enableToggle : true,
				iconCls : 'icon-calendar',
				handler : function() {
					Ext.ComponentQuery.query('orderOAResource')[0]
							.switchViewPreset('hourAndDayOit', stratDate, endDate);
				}
			}, {
				text : '按周',
				toggleGroup : 'presets',
				enableToggle : true,
				iconCls : 'icon-calendar',
				handler : function() {
					Ext.ComponentQuery.query('orderOAResource')[0].switchViewPreset('dayAndWeek', stratDate, endDate);
				}
			}],
	tooltipTpl : new Ext.XTemplate('<dl class="tip">',
			'<dt  >开始时间 :{[Ext.Date.format(values.startDate, "Y-m-d G:i")]}</dt>',
			'<dt  >结束时间 :{[Ext.Date.format(values.endDate, "Y-m-d G:i")]}</dt>', '<dt  >产出半成品 :{[values.outPut]}</dt>',
			'<dt  >进度(%):{[values.percentDone]}</dt>', '</dl>').compile(),
	plugins : [Ext.create('Sch.plugin.Zones', {
				store : 'ZoneStore'
			})]

});
