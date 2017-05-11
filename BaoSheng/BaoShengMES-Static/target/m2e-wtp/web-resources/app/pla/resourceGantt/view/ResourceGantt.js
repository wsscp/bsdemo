Ext.Loader.setConfig({
			enabled : true,
			disableCaching : true
		});
var stratDate = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
var endDate = Sch.util.Date.add(new Date(), Sch.util.Date.WEEK, 1);
Ext.define("bsmes.view.ResourceGantt", {
			extend : "Sch.panel.SchedulerTree",
			alias : 'widget.resourceGantt',
			id : 'resourceGanttId',
			rowHeight : 25,
			eventBorderWidth : 1,
			rowLines : true,
			eventStore : 'EventStore',
			resourceStore : 'WorkTaskStore',
			viewPreset : 'hourAndDayOit',
			readOnly : true,
			tipCfg : {
				autoHide : false
			},
			startDate : stratDate,
			trackHeaderOver : true,
			useArrows : true,
			multiSelect : true,
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			endDate : endDate,
			columnLines : true,
			columns : [{
						xtype : 'treecolumn',
						text : '工序信息',
						flex : 4,
						minWidth : 200,
						dataIndex : 'Name',
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
								var order = Ext.getCmp('resourceGanttId');
								var record = order.eventStore.findRecord('resourceId', val, 0, false, true, false);
								if (record == null) {
									return;
								}
								order.getSchedulingView().scrollEventIntoView(record, true);
							}
						}
					}],
			// eventRenderer : function(flight, resource, meta) {
			// if (resource.data.leaf) {
			// meta.cls = 'leaf';
			// return flight.get('Name');
			// } else {
			// meta.cls = 'group';
			// return '&nbsp;';
			// }
			// },
			tooltipTpl : new Ext.XTemplate('<dl class="tip">',
					'<dt  >开始时间 :{[Ext.Date.format(values.startDate, "Y-m-d G:i")]}</dt>',
					'<dt  >结束时间 :{[Ext.Date.format(values.endDate, "Y-m-d G:i")]}</dt>',
					'<dt  >产出半成品 :{[values.name]}</dt>', '</dl>').compile(),
			tbar : ['过滤条件:', {
						xtype : 'triggerfield',
						emptyText : '输入后请回车...',
						enableKeyEvents : true,
						triggerCls : 'x-form-clear-trigger',
						onTriggerClick : function() {
							this.setValue('');
							var resourceStore = Ext.ComponentQuery.query('resourceGantt')[0].view.getStore();
							resourceStore.clearTreeFilter();
						},
						listeners : {
							keyup : function(text, e, eOpts) {
								if (e.getKey() == Ext.EventObject.ENTER) {
									if (text.getValue() != '' && text.getValue() != undefined) {
										var resourceStore = Ext.ComponentQuery.query('resourceGantt')[0].view
												.getStore();
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
										var resourceStore = Ext.ComponentQuery.query('resourceGantt')[0].view
												.getStore();
										resourceStore.clearTreeFilter();
									}
								} else {
									if (text.getValue() == '' || text.getValue() == ' ' || text.getValue() == undefined) {
										var resourceStore = Ext.ComponentQuery.query('resourceGantt')[0].view
												.getStore();
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
							Ext.ComponentQuery.query('resourceGantt')[0].expandAll();
						}
					}, {
						text : '',
						iconCls : 'collapseAll',
						handler : function() {
							var tree = Ext.ComponentQuery.query('resourceGantt')[0];
							var roonodes = tree.getRootNode().childNodes; // 获取主节点
							for (var i = 0; i < roonodes.length; i++) { // 从节点中取出子节点依次遍历
								var rootnode = roonodes[i];
								if (rootnode.childNodes.length > 0) { // 判断子节点下是否存在子节点
									for (var j = 0; j < rootnode.childNodes.length; j++) { // 从节点中取出子节点依次遍历
										var rootnodej = rootnode.childNodes[j];
										rootnodej.collapse(true)
									}
								}
							}
							// Ext.ComponentQuery.query('resourceGantt')[0].collapseAll();
						}
					}, '计划使用日期 从：', {
						xtype : 'datefield',
						name : 'fromDate',
						width : 110,
						value : stratDate,
						id : 'resourceGantqueryFromDate',
						format : 'Y-m-d'
					}, '到:', {
						xtype : 'datefield',
						name : 'toDate',
						id : 'resourceGantqueryToDate',
						value : endDate,
						width : 110,
						format : 'Y-m-d'
					}, {
						text : '按时间查询',
						iconCls : 'goSsearch',
						handler : function() {
							var startDate = Ext.getCmp("resourceGantqueryFromDate").getValue();
							if (startDate == null) {
								Ext.MessageBox.alert("提示", "请输入开始日期!");
								return;
							}
							endDate = Ext.getCmp("resourceGantqueryToDate").getValue();
							if (endDate == null) {
								Ext.MessageBox.alert("提示", "请输入截止日期!");
								return;
							}
							if (endDate < startDate) {
								Ext.MessageBox.alert("提示", "开始日期必须小于截止日期!");
								return;
							}
							endDate = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate(), 23, 59, 59);
							stratDate = startDate;
							Ext.ComponentQuery.query('resourceGantt')[0].getResourceStore().load({
										params : {
											"fromDate" : startDate,
											"toDate" : endDate
										}
									});
							Ext.ComponentQuery.query('resourceGantt')[0].getEventStore().load({
										params : {
											"fromDate" : startDate,
											"toDate" : endDate
										}
									});
							Ext.ComponentQuery.query('resourceGantt')[0].switchViewPreset('hourAndDayOit', startDate,
									endDate);
						}
					}, {
						text : '放大',
						iconCls : 'zoomIn',
						handler : function() {
							var o = Ext.ComponentQuery.query('resourceGantt')[0].zoomIn();
							if (!o) {
								Ext.MessageBox.alert("提示", "视图不能再放大了 !");
							}
						}
					}, {
						text : '缩小',
						iconCls : 'zoomOut',
						handler : function() {
							var o = Ext.ComponentQuery.query('resourceGantt')[0].zoomOut();
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
							Ext.ComponentQuery.query('resourceGantt')[0].switchViewPreset('minuteAndHour', stratDate,
									endDate);
						}
					}, {
						text : '按小时',
						toggleGroup : 'presets',
						enableToggle : true,
						iconCls : 'icon-calendar',
						handler : function() {
							Ext.ComponentQuery.query('resourceGantt')[0].switchViewPreset('hourAndDay', stratDate,
									endDate);
						}
					}, {
						text : '按天',
						toggleGroup : 'presets',
						enableToggle : true,
						iconCls : 'icon-calendar',
						handler : function() {
							Ext.ComponentQuery.query('resourceGantt')[0].switchViewPreset('hourAndDayOit', stratDate,
									endDate);
						}
					}, {
						text : '按周',
						toggleGroup : 'presets',
						enableToggle : true,
						iconCls : 'icon-calendar',
						handler : function() {
							Ext.ComponentQuery.query('resourceGantt')[0].switchViewPreset('dayAndWeek', stratDate,
									endDate);
						}
					}]
		});
