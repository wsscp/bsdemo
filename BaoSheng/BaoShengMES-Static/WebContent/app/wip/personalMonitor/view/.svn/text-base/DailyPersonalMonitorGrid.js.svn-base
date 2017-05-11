Ext.define("bsmes.view.DailyPersonalMonitorGrid", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.dailyPersonalMonitorGrid',
			itemId : 'dailyPersonalMonitorGrid',
			store : 'DailyMonitorStore',
			forceFit : false,
			defaultEditingPlugin : false,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight - 36,
			columns : [{
						flex : 1.2,
						minWidth : 60,
						sortable : false,
						menuDisabled : true,
						text : '机台',
						dataIndex : 'equipAlias'
					}, {
						flex : 1.5,
						minWidth : 75,
						sortable : false,
						menuDisabled : true,
						text : '班次',
						dataIndex : 'shiftName'
					}, {
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						text : '人员名单',
						dataIndex : 'userName'
					}, {
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						text : '上班时间',
						dataIndex : 'onTime'
					}, {
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						text : '下班时间',
						dataIndex : 'offTime'
					}, {
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						itemId : 'remark',
						text : '&nbsp;&nbsp;&nbsp;&nbsp;备注<br/>(是否正常)',
						dataIndex : 'remark',
						renderer : function(value, metaData, record, row, column) {
							if(record.get('onTime') != '' && record.get('offTime') !=''){
								var onTime = new Date(record.get('onTime')).getTime();
								var offTime = new Date(record.get('offTime')).getTime();
								if((offTime-onTime)/(1000*60*60)>9.5){
									value = '刷下班卡不及时';
								}
							}else if(record.get('onTime') != '' && record.get('offTime') ==''){
								value = '未刷下班卡';
							}else{
								value = '';
							}
							return value;
						}
					}
//					, {
//						flex : 1,
//						minWidth : 50,
//						sortable : false,
//						menuDisabled : true,
//						itemId : 'shutDownReason',
//						text : '停机原因',
//						dataIndex : 'shutDownReason'
//			}
					],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
							title : '查询条件',
							xtype : 'fieldset',
							collapsible : true,
							width : '100%',
							items : [{
										xtype : 'hform',
										width : '100%',
//										layout : 'vbox',
										buttonAlign : 'left',
										labelAlign : 'right',
										bodyPadding : 5,
										items : [{
													fieldLabel : '查询日期',
													xtype : 'datefield',
													name : 'yearMonthDay',
													id : 'yearMonthDay',
													format : 'Y-m-d',
													labelWidth : 60,
													firstLoad : true,
													width : 250
												},{
													fieldLabel: "机台",
											        name: 'code',
											        xtype:'combobox',
											        allowBlank:false,
											        labelWidth : 45,
											        width : 250,
											        displayField:'equipAlias',
											        valueField: 'code',
											        store:new Ext.data.Store({
											        	fields:['code','equipAlias'],
											        	autoLoad:false,
											        	proxy:{
											        		type: 'rest',
											        		url:''
											        	},
											        	sorters : [{
															property : 'code',
															direction : 'ASC'
														}]
											        }),
											        listeners:{
														expand:function(){
															var date = Ext.util.Format.date(Ext.getCmp('yearMonthDay').getValue(),'Y-m-d');
															console.log(date);
															var store = Ext.ComponentQuery.query('dailyPersonalMonitorGrid combobox')[0].getStore();
															store.proxy.url = 'personalMonitor/getDailyEquipCode?yearMonthDay='+date;
															store.load();
														}
											        }
												},{
													fieldLabel: "员工",
											        name: 'userCode',
											        xtype:'combobox',
											        labelWidth : 45,
											        width : 250,
											        allowBlank:false,
											        displayField:'userName',
											        valueField: 'userCode',
											        store:new Ext.data.Store({
											        	fields:['userCode','userName'],
											        	autoLoad:false,
											        	proxy:{
											        		type: 'rest',
											        		url:''
											        	},
											        	sorters : [{
															property : 'userCode',
															direction : 'ASC'
														}]
											        }),
											        listeners:{
														expand:function(){
															var date = Ext.util.Format.date(Ext.getCmp('yearMonthDay').getValue(),'Y-m-d');
															console.log(date);
															var store = Ext.ComponentQuery.query('dailyPersonalMonitorGrid combobox')[1].getStore();
															store.proxy.url = 'personalMonitor/getDailyUserCode?yearMonthDay='+date;
															store.load();
														}
											        }
												},{
													fieldLabel: "班次",
											        name: 'shiftId',
											        xtype:'combobox',
											        labelWidth : 45,
											        width : 250,
											        allowBlank:false,
											        displayField:'shiftName',
											        valueField: 'shiftId',
											        store:new Ext.data.Store({
											        	fields:['shiftId','shiftName'],
											        	data : [{'shiftId':'1','shiftName':'早班'},
											        	        {'shiftId':'2','shiftName':'中班'},
											        	        {'shiftId':'3','shiftName':'晚班'}],
											        	sorters : [{
															property : 'shiftId',
															direction : 'ASC'
														}]
											        })
												}],
										buttons : [{
													itemId : 'searchDailyReport',
													text : '查找'
												}/*,{
													itemId : 'creditCard',
													text : '人员考勤报表'
												}*/]
									}]
						}]
			}],		
			initComponent : function() {
				var me = this;

				this.callParent(arguments);
				
				// 设置默认查询时间
				me.down('form').form.findField('yearMonthDay').setValue(Ext.Date.add(new Date(),Ext.Date.DAY,-1),"Y-m-d");
				me.getStore().on('load', function(store, records, successful, eOpts) {
					//若以班次查询就不要合并单元格了
							var form = Ext.ComponentQuery.query("dailyPersonalMonitorGrid hform")[0];
							var shiftId = form.getValues().shiftId;
							if(shiftId == null || shiftId == ''){
								me.mergeCells(me, [1]);
								me.mergeCells(me, [2]);
							}
						});

			},
			/**
			 * 合并单元格
			 * 
			 * @param {}
			 *            grid 要合并单元格的grid对象
			 * @param {}
			 *            cols 要合并哪几列 例如 [1,2,4]
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
			}
});