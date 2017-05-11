Ext.define("bsmes.view.UserWorkHoursGrid", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.userWorkHoursGrid',
	store : 'UserWorkHoursStore',
	id : 'userWorkHoursGrid',
	forceFit : false,
	// currentMonth: new Date(),
	hasPaging : false,
	columnLines : true,
	defaultEditingPlugin : false,
	initComponent : function() {
		var me = this;
		var isshow29 = false;
		var isshow30 = false;
		var isshow31 = false;
		var lastMonth = Ext.Date.add(new Date(), Ext.Date.MONTH, -1);
		var dayOfLastMonth = Ext.Date.getDaysInMonth(lastMonth);
		if (dayOfLastMonth == 28) {
			isshow29 = true;
			isshow30 = true;
			isshow31 = true;
			// console.log(this.down('#29日'));
		}
		if (dayOfLastMonth == 29) {
			isshow30 = true;
			isshow31 = true;
			// console.log(this.down('#29日'));
		}
		if (dayOfLastMonth == 30) {
			isshow31 = true;
		}
		var columns = [];
		columns.push({
					flex : 1.2,
					minWidth : 60,
					sortable : false,
					menuDisabled : true,
					text : '姓名',
					dataIndex : 'USERNAME'
				}, {
					flex : 1.5,
					minWidth : 75,
					sortable : false,
					menuDisabled : true,
					text : '类型',
					dataIndex : 'HOURSTYPE'
				});
		for (var i = 26; i <= 28; i++) {
			columns.push({
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						day : i + '',
						text : i + '日',
						dataIndex : i + '日'
					});
		}
		columns.push({
					flex : 1,
					minWidth : 50,
					sortable : false,
					menuDisabled : true,
					day : '29',
					id : '29',
					text : '29日',
					hidden : isshow29,
					dataIndex : '29日'
				});
		columns.push({
					flex : 1,
					minWidth : 50,
					sortable : false,
					menuDisabled : true,
					day : '30',
					hidden : isshow30,
					text : '30日',
					id : '30',
					dataIndex : '30日'
				});
		columns.push({
					flex : 1,
					minWidth : 50,
					sortable : false,
					menuDisabled : true,
					day : '31',
					hidden : isshow31,
					id : '31',
					text : '31日',
					dataIndex : '31日'
				});
		for (var i = 1; i <= 25; i++) {
			columns.push({
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						day : i < 10 ? ('0' + i) : (i + ''),
						text : i + '日',
						dataIndex : i + '日'
					});
		}
		columns.push({
					flex : 1,
					minWidth : 50,
					sortable : false,
					menuDisabled : true,
					text : '合计',
					dataIndex : '合计',
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						value = Number(value).toFixed(1);
						metaData.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}
				}, {
					flex : 1,
					minWidth : 50,
					sortable : false,
					menuDisabled : true,
					text : '含系数',
					dataIndex : '含系数',
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						value = Number(value).toFixed(1);
						metaData.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}
				});
		var dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			items : [{
						title : '查询条件',
						xtype : 'fieldset',
						collapsible : true,
						width : '100%',
						items : [{
									xtype : 'form',
									width : '100%',
									layout : 'hbox',
									buttonAlign : 'left',
									labelAlign : 'right',
									bodyPadding : 5,
									items : [{
												fieldLabel : '查询年月',
												xtype : 'datefield',
												itemId : 'yearMonth',
												name : 'yearMonth',
												format : 'Y-m',
												margin : '0 30 0 0',
												value : new Date(),
												labelWidth : 60,
												firstLoad : true,
												width : 230,
												listeners : {
													change : function(field, newValue, oldValue, eOpts) {
														var newMonth = field.getValue();
														var lastMonth = Ext.Date.add(newMonth, Ext.Date.MONTH, -1);
														var dayOfLastMonth = Ext.Date.getDaysInMonth(lastMonth);
														if (dayOfLastMonth == 28) {
															field.up('userWorkHoursGrid').down('#29').setVisible(false);
															field.up('userWorkHoursGrid').down('#30').setVisible(false);
															field.up('userWorkHoursGrid').down('#31').setVisible(false);
														}
														if (dayOfLastMonth == 29) {
															field.up('userWorkHoursGrid').down('#29').setVisible(true);
															field.up('userWorkHoursGrid').down('#30').setVisible(false);
															field.up('userWorkHoursGrid').down('#31').setVisible(false);
														}
														if (dayOfLastMonth == 30) {
															field.up('userWorkHoursGrid').down('#29').setVisible(true);
															field.up('userWorkHoursGrid').down('#30').setVisible(true);
															field.up('userWorkHoursGrid').down('#31').setVisible(false);
														}
														if (dayOfLastMonth == 31) {
															field.up('userWorkHoursGrid').down('#29').setVisible(true);
															field.up('userWorkHoursGrid').down('#30').setVisible(true);
															field.up('userWorkHoursGrid').down('#31').setVisible(true);
														}

														// field.up('userWorkHoursGrid').getStore().load();
													}
												}
											}, {
												fieldLabel : '姓名',
												xtype : 'combobox',
												margin : '0 30 0 0',
												// hideTrigger: true,
												multiSelect : true,
												name : 'userName',
												displayField : 'USER_NAME',
												valueField : 'USER_NAME',
												labelWidth : 50,
												width : 230,
												store : new Ext.data.Store({
															fields : ['USER_NAME', 'USER_NAME'],
															proxy : {
																autoLoad : true,
																type : 'rest',
																url : 'userWorkHours/userName'
															}
														}),
												listeners : {
													beforequery : function(e) {
														var combo = e.combo;
														combo.collapse();
														if (!e.forceAll) {
															var value = e.query;
															var regExp = new RegExp("^.*" + value + ".*$", 'i');
															if (value != null && value != '') {
																combo.store.filterBy(function(record, id) {
																			var text = record.get('USER_NAME');
																			return regExp.test(text);
																		});
															} else {
																combo.store.clearFilter();
															}
															combo.onLoad();
															combo.expand();
															return false;
														}
													}
												}
											}/*
												 * ,{ fieldLabel : '类型', xtype : 'combobox', name : 'typeName',
												 * displayField:'typeName', valueField: 'typeName', store: new
												 * Ext.data.Store({ fields: ['typeName','typeName'], autoLoad: true,
												 * data: [{typeName:'生产工时',typeName:'生产工时'},
												 * {typeName:'生产辅助',typeName:'生产辅助'}, {typeName:'加班工时',typeName:'加班工时'},
												 * {typeName:'辅助工时',typeName:'辅助工时'}] }), labelWidth : 50, width : 230, }
												 */],
									buttons : [{
												itemId : 'search',
												text : '查找'
											}, {
												itemId : 'reset',
												text : '重置',
												handler : function() {
													this.up('grid').down('form').getForm().reset();
												}
											}, {
												itemId : 'export',
												text : '导出'
											}]
								}]
					}]
		}];
		// 设置默认查询时间
		me.columns = columns;
		me.dockedItems = dockedItems;
		this.callParent(arguments);

		/*
		 * me.down('form').form.findField('yearMonth').setValue(Ext.util.Format.date(new Date(), "Y-m"));
		 * me.getStore().on('load', function(store, records, successful, eOpts) { //me.mergeCells(me, [1]); });
		 */
	},

	/**
	 * ruantao1989 合并单元格
	 * 
	 * @param {} grid 要合并单元格的grid对象
	 * @param {} cols 要合并哪几列 例如 [1,2,4]
	 */
	mergeCells : function(grid, cols) {
		// ==>ExtJs4.2的<tbody>改到上层<table>的lastChild . <tbody>是各个<tr>的集合
		var arrayTr = document.getElementById(grid.getId() + "-body").firstChild.firstChild.lastChild
				.getElementsByTagName('tr');

		var trCount = arrayTr.length; // <tr>总行数
		var arrayTd;
		var td;

		// ==>显示层将目标格的样式改为.display='none';
		var merge = function(rowspanObj, removeObjs)// 定义合并函数
		{
			if (0 != rowspanObj.rowspan) {
				arrayTd = arrayTr[rowspanObj.tr].getElementsByTagName("td"); // 合并行
				td = arrayTd[rowspanObj.td - 1];
				td.rowSpan = rowspanObj.rowspan;
				td.vAlign = "middle";
				var height = td.clientHeight
				if (removeObjs.length > 0) {
					td.style.paddingTop = height / 3;
					// $(td).css("padding-top", height / 3);
					// var showIndex = Math.ceil(removeObjs.length/2);
				}

				// 隐身被合并的单元格
				Ext.each(removeObjs, function(obj) {
							arrayTd = arrayTr[obj.tr].getElementsByTagName("td");
							arrayTd[obj.td - 1].style.display = 'none';
						});
			}
		};
		// ==>显示层将目标格的样式改为.display='none';

		var rowspanObj = {}; // 要进行跨列操作的td对象{tr:1,td:2,rowspan:5}
		var removeObjs = []; // 要进行删除的td对象[{tr:2,td:2},{tr:3,td:2}]
		var col;
		// ==>逐列靠表内具体数值去合并各个<tr> (表内数值一样则合并)
		Ext.each(cols, function(colIndex) {
					var rowspan = 1;
					var divHtml = null;// 单元格内的数值
					for (var i = 0; i < trCount; i++)// ==>从第一行数据0开始
					{
						// ==>一个arrayTr[i]是一整行的所有数据, 一个arrayTd是 <td xxxx
						// ><div>具体数值</div></td> ,
						arrayTd = arrayTr[i].getElementsByTagName("td");
						var cold = 0;
						// Ext.each(arrayTd,function(Td){
						// //获取RowNumber列和check列
						// if(Td.getAttribute("class").indexOf("x-grid-cell-special")
						// != -1)
						// cold++;
						// });
						col = colIndex + cold;// 跳过RowNumber列和check列

						if (!divHtml) {
							divHtml = arrayTd[col - 1].innerHTML;
							divHtml = $(divHtml).text(); // ==>拿到真正数值,相比Ext4.1多了一层<div>
							rowspanObj = {
								tr : i,
								td : col,
								rowspan : rowspan
							}
						} else {
							var cellText = arrayTd[col - 1].innerHTML;
							cellText = $(cellText).text();// ==>拿到真正数值

							var addf = function() {
								rowspanObj["rowspan"] = rowspanObj["rowspan"] + 1;
								removeObjs.push({
											tr : i,
											td : col
										});
								if (i == trCount - 1) {
									merge(rowspanObj, removeObjs);// 执行合并函数
								}
							};
							var mergef = function() {
								merge(rowspanObj, removeObjs);// 执行合并函数
								divHtml = cellText;
								rowspanObj = {
									tr : i,
									td : col,
									rowspan : rowspan
								}
								removeObjs = [];
							};

							if (cellText == divHtml) {
								if (colIndex != cols[0]) {
									var leftDisplay = arrayTd[col - 2].style.display;// 判断左边单元格值是否已display
									if (leftDisplay == 'none') {
										addf();
									} else {
										mergef();
									}
								} else {
									addf();
								}
							} else {
								mergef();
							}
						}
					}
				});
	},

	listeners : {
		cellclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
			var me = this;
			var showcolumns = []
			Ext.Array.each(this.columns, function(columns, i) {
						if (columns.isVisible())
							showcolumns.push(columns)
					})
			var day = showcolumns[cellIndex].day;
			var userCode = record.get('USERCODE');
			var yearmonth = this.down('form').form.findField('yearMonth').getValue();
			if (Number(day) > 26 && cellIndex < 5) {
				yearmonth = Ext.util.Format.date(Ext.Date.add(yearmonth, Ext.Date.MONTH, -1), "Y-m")
			} else {
				yearmonth = Ext.util.Format.date(yearmonth, "Y-m")
			}

			var win = Ext.create('Ext.window.Window', {
						title : ' 工时明细',
						width : document.body.scrollWidth - 100,
						height : document.body.scrollHeight - 100,
						modal : true,
						plain : true,
						items : [{
							xtype : 'grid',
							collapsible : false,
							animCollapse : false,
							height : document.body.scrollHeight - 140,
							store : 'WorkHoursDetailStore',
							columns : [{
										text : '生产单号',
										dataIndex : 'workOrderNo',
										minWidth : 70,
										flex : 1.4,
										renderer : function(value, metaData, record) {
											metaData.tdAttr = 'data-qtip="' + value + '"';
											value = value.substring(value.length - 6);
											return value;
										}
									}, {
										text : '合同号',
										dataIndex : 'contractNo',
										minWidth : 70,
										flex : 1.4,
										renderer : function(value, metaData, record) {
											var reg = /[a-zA-Z]/g;
											value = (value.replace(reg, "").length > 5 ? value.replace(reg, "")
													.substring(value.replace(reg, "").length - 5) : value.replace(reg,
													""));
											metaData.tdAttr = 'data-qtip="' + value + '"';
											return value;
										}
									}, {
										text : '经办人',
										dataIndex : 'operator',
										minWidth : 55,
										flex : 1.1
									}, {
										text : '客户型号规格',
										dataIndex : 'custProductType',
										minWidth : 200,
										flex : 4,
										renderer : function(value, metaData, record) {
											value = value + ' ' + record.get('custProductSpec');
											metaData.tdAttr = 'data-qtip="' + value + '"';
											return value;
										}
									}, {
										text : '工序',
										dataIndex : 'processName',
										minWidth : 70,
										flex : 1.4
									}, {
										text : '机台',
										dataIndex : 'equipName',
										minWidth : 200,
										flex : 4
									}, {
										text : '班次',
										dataIndex : 'shiftName',
										minWidth : 50,
										flex : 1
									}, {
										text : '报工长度',
										dataIndex : 'finishedLength',
										minWidth : 60,
										flex : 1.2
									}, {
										text : '定额',
										dataIndex : 'quota',
										minWidth : 50,
										flex : 1
									}, {
										text : '系数',
										dataIndex : 'coefficient',
										minWidth : 50,
										flex : 1
									}, {
										text : '工时',
										dataIndex : 'productWorkHours',
										minWidth : 50,
										flex : 1
									}]
						}],
						buttons : [{
									text : Oit.btn.cancel,
									handler : function() {
										this.up('window').close();
									}
								}]
					});
			win.down('grid').getStore().load({
						params : {
							userCode : userCode,
							reportDate : yearmonth + '-' + day
						}
					})
			win.show();

		}
	}
});