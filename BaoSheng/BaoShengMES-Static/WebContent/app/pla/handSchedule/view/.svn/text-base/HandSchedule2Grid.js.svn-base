/**
 * 主列表：订单产品列表
 */

Ext.define("bsmes.view.HandSchedule2Grid", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.handSchedule2Grid',
	store : 'HandSchedule2Store',
	defaultEditingPlugin : false,
	columnLines : true,
	selType : 'checkboxmodel',
	selModel : {
		mode : "SINGLE", // "SINGLE"/"SIMPLE"/"MULTI"
		checkOnly : true
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
				minWidth : 300,
				renderer : function(value, metaData, record) {
					// 说明：成缆和护套对于2,3这种情况显示不一样：成缆显示成缆还没结束的/护套显示成缆已经下完的
					// 获取当前工序
					var processSection = Ext.ComponentQuery.query('handSchedulePanel #chooseWorkSection')[0].getValue().processCode; // 页面工段选择
				    var isCL = (processSection == 'CL'); // 判断是否成缆工段
				    var completeCusOrderItemIds = record.get('completeCusOrderItemIds'); // 成缆已经完成成缆工段的id
					completeCusOrderItemIds = (completeCusOrderItemIds == '' ? [] :completeCusOrderItemIds.split(','));
					var auditedCusOrderItemIds = record.get('auditedCusOrderItemIds'); // 已经流到下一工序的id
					auditedCusOrderItemIds = (auditedCusOrderItemIds == '' ? [] :auditedCusOrderItemIds.split(','));
					
					var showInfo = '';
					var showInfo2 = '';
					for (var i = 0; i < value.split(",").length; i++) {
						var cppl = value.split(",")[i].split(';')[0]; // 显示的型号规格和长度
						var coid = value.split(",")[i].split(';')[1]; // 订单ID
						if(isCL && (arrayContains(completeCusOrderItemIds, coid) || arrayContains(auditedCusOrderItemIds, coid))){ // 
							continue; // 成缆: [已经完成的/已经下发的]，不显示
						}else if(!isCL && ((completeCusOrderItemIds.length > 0 && !arrayContains(completeCusOrderItemIds, coid)) 
							|| arrayContains(auditedCusOrderItemIds, coid))){
							continue; // 护套: [不在已经完成的/已经下发的]，不显示
						}
						showInfo2 += cppl + "<br/>";
						if(cppl.indexOf('(盘具') > 0)
						{
							cppl = cppl.replace('(盘具','<font = color="red">(盘具');
							cppl += '</font>';
						}
						showInfo += cppl + "<br/>";
					}
					metaData.tdAttr = 'data-qtip="' + showInfo2 + '"';
					return showInfo;
				}
			}, {
				text : '机台',
				dataIndex : 'equipName',
				flex : 3,
				minWidth : 150,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					var machine = value.split(",");
					var res = '', show = '';
					for (var i = 0; i < machine.length; i++) {
						res = res + '<a style="color:blue;cursor:pointer;" onclick="intoMachine(\'' + machine[i]
								+ '\')">' + machine[i] + '</a>' + "<br/>";
						show = show + machine[i] + '<br/>';
					}
					metaData.tdAttr = 'data-qtip="' + show + '"';
					return res;
				}
			}, {
				text : '工序',
				dataIndex : 'processName',
				flex : 1.8,
				minWidth : 90,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					metaData.tdAttr = 'data-qtip="' + value + '"';
					return value;
				}
			}, {
				text : '生产单<br/>总任务数',
				dataIndex : 'orderLength',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					return value; // 不处理，免除产生歧义！！！
					if(record.get('nextSection').indexOf(',') == -1) // 正常情况直接返回orderLength
						return value;
					
					// 说明：成缆和护套对于2,3这种情况显示不一样：成缆显示成缆还没结束的/护套显示成缆已经下完的
					// 获取当前工序
					var processSection = Ext.ComponentQuery.query('handSchedulePanel #chooseWorkSection')[0].getValue().processCode; // 页面工段选择
				    var isCL = (processSection == 'CL'); // 判断是否成缆工段
				    var completeCusOrderItemIds = record.get('completeCusOrderItemIds'); // 成缆已经完成成缆工段的id
					completeCusOrderItemIds = (completeCusOrderItemIds == '' ? [] :completeCusOrderItemIds.split(','));
					var auditedCusOrderItemIds = record.get('auditedCusOrderItemIds'); // 已经流到下一工序的id
					auditedCusOrderItemIds = (auditedCusOrderItemIds == '' ? [] :auditedCusOrderItemIds.split(','));
					
					var showInfo = 0;
					for (var i = 0; i < record.get('contractNo').split(",").length; i++) {
						var cppl = record.get('contractNo').split(",")[i].split(';')[0]; // 显示的型号规格和长度
						var coid = record.get('contractNo').split(",")[i].split(';')[1]; // 订单ID
						var length = cppl.substring(cppl.lastIndexOf(' ') + 1);
						if(isCL && (arrayContains(completeCusOrderItemIds, coid) || arrayContains(auditedCusOrderItemIds, coid))){ // 
							continue; // 成缆: [已经完成的/已经下发的]，不显示
						}else if(!isCL && ((completeCusOrderItemIds.length > 0 && !arrayContains(completeCusOrderItemIds, coid)) 
							|| arrayContains(auditedCusOrderItemIds, coid))){
							continue; // 护套: [不在已经完成的/已经下发的]，不显示
						}
						showInfo += Math.round(Math.round(Number(showInfo) + Number(length)) + 5);
					}
					metaData.tdAttr = 'data-qtip="' + showInfo + '"';
					return showInfo;
				}
			}, {
				text : '下发<br/>日期',
				dataIndex : 'releaseDate',
				xtype : 'datecolumn',
				format : 'm-d',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true
			}, {
				text : '要求完<br/>成日期',
				dataIndex : 'requireFinishDate',
				xtype : 'datecolumn',
				format : 'm-d',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true
			}, {
				text : '完成<br/>进度',
				dataIndex : 'percent',
				flex : 1,
				minWidth : 50,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					value = (Number(value) * 100).toFixed(0);
					var html = '<a style="color:blue;cursor:pointer;" onclick="showPercentDetail(\''
							+ record.get('workOrderNo') + '\', \'\', \'\')">' + value + '%</a>';
					metaData.tdAttr = 'data-qtip="' + value + '%"';
					return html;
				}
//			}, {
//				text : '是否<br/>急件',
//				dataIndex : 'isDispatch',
//				flex : 1,
//				minWidth : 50,
//				menuDisabled : true,
//				renderer : function(value) {
//					switch (value) {
//						case true :
//							return '是';
//						default :
//							return '否';
//					}
//				}
			}, {
				text : '附件',
				dataIndex : 'workOrderNo',
				flex : 1,
				minWidth : 50,
				renderer : function(value, metaData, record, rowIndex) {
					var nofile = (record.get('orderfilenum') == 0);
					var html = '<a style="color:' + (nofile ? 'grey' : 'blue;cursor:pointer;') + '"' + (nofile ? '' : 'onclick="lookUpAttachFileWindow(\'\', \'' + value + '\', \'\')"') + '>附件</a>';
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
					//value.replace(/\n|\r|(\r\n)|(\u0085)|(\u2028)|(\u2029)/g, "<br>") // 去除字符串中换行符
					//metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
					metaData.tdAttr = 'data-qtip="' + svalue + '"';
					return '<a style="color:blue;cursor:pointer;" onclick="showremarks(\'' + svalue + '\')">' + value + '</a>';
				}
			}, {
				text : '状态',
				dataIndex : 'status',
				flex : 1.2,
				minWidth : 60,
				menuDisabled : true,
				renderer : function(value, metaData, record) {
					switch (value) {
						case 'TO_AUDIT' :
							return Oit.msg.pla.handSchedule.statusName.toAudit;
						case 'TO_DO' :
							return Oit.msg.pla.handSchedule.statusName.toDo;
						case 'IN_PROGRESS' :
							return Oit.msg.pla.handSchedule.statusName.inProgress;
						case 'CANCELED' :
							return Oit.msg.pla.handSchedule.statusName.canceled;
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
			title : '查询条件',
			xtype : 'fieldset',
			collapsible : true,
			width : '99.3%',
			items : [{
				xtype : 'form',
				layout : 'column',
				defaults : {
					width : 300,
					padding : 1,
					labelAlign : 'right'
				},
				items : [{
							fieldLabel : '合同号',
							xtype : 'combobox',
							name : 'contractNo',
							displayField : 'contractNo',
							valueField : 'contractNo',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										autoLoad : false,
										fields : ['contractNo'],
										proxy : {
											type : 'rest',
											url : 'handSchedule/getWorkOrderContractNo'
										},
										sorters : [{
													property : 'contractNo',
													direction : 'ASC'
												}],
										listeners : {
										    beforeload : function(store, operation, eOpts){
										    	var form = Ext.ComponentQuery.query('handSchedule2Grid toolbar form')[0];
										    	var param = form.getForm().getValues();
										    	store.getProxy().extraParams = param;
										    }
										}
									}),
							listeners : {
										beforequery : function(e) {
											var combo = e.combo;
											combo.collapse(); // 折叠
											if (!e.forceAll) { // 模糊查询走的方法
												var value = e.query;
												if(value != null && value != ''){
													combo.getStore().load({ params : { 'query' : value } });
												}else{
													combo.getStore().load();
												}
												combo.expand(); // 展开
												return false;
											}else{ // 点击下拉框
												combo.firstExpand ++;
											}
										},
										expand : function(e) {
											if (e.firstExpand > 1) {
												e.getStore().load();
											}
										}
									}
						}, {
							fieldLabel : '客户型号',
							xtype : 'combobox',
							name : 'custProductType',
							displayField : 'custProductType',
							valueField : 'custProductType',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										autoLoad : false,
										fields : ['custProductType'],
										proxy : {
											type : 'rest',
											url : 'handSchedule/getWorkOrderCustproductType'
										},
										sorters : [{
													property : 'custProductType',
													direction : 'ASC'
												}],
										listeners : {
										    beforeload : function(store, operation, eOpts){
										    	var form = Ext.ComponentQuery.query('handSchedule2Grid toolbar form')[0];
										    	var param = form.getForm().getValues();
										    	store.getProxy().extraParams = param;
										    }
										}
									}),
							listeners : {
										beforequery : function(e) {
											var combo = e.combo;
											combo.collapse(); // 折叠
											if (!e.forceAll) { // 模糊查询走的方法
												var value = e.query;
												if(value != null && value != ''){
													combo.getStore().load({ params : { 'query' : value } });
												}else{
													combo.getStore().load();
												}
												combo.expand(); // 展开
												return false;
											}else{ // 点击下拉框
												combo.firstExpand ++;
											}
										},
										expand : function(e) {
											if (e.firstExpand > 1) {
												e.getStore().load();
											}
										}
									}
						}, {
							fieldLabel : '产品规格',
							name : 'productSpec',
							xtype : 'combobox',
							displayField : 'productSpec',
							valueField : 'productSpec',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							minChars : 3, // 最少几个字开始查询
							triggerAction : 'all', // 请设置为”all”,否则默认
							// 为”query”的情况下，你选择某个值后，再此下拉时，只出现匹配选项，如果设为”all”的话，每次下拉均显示全部选项
							typeAhead : true, // 是否延迟查询
							typeAheadDelay : 1000, // 延迟时间
							firstExpand : 0, // 自定义，用来阻止初次点击下拉框store执行两遍的问题
							store : new Ext.data.Store({
										autoLoad : false,
										fields : ['productSpec'],
										proxy : {
											type : 'rest',
											url : 'handSchedule/getWorkOrderProductSpec'
										},
										sorters : [{
													property : 'productSpec',
													direction : 'ASC'
												}],
										listeners : {
										    beforeload : function(store, operation, eOpts){
										    	var form = Ext.ComponentQuery.query('handSchedule2Grid toolbar form')[0];
										    	var param = form.getForm().getValues();
										    	store.getProxy().extraParams = param;
										    }
										}
									}),
							listeners : {
										beforequery : function(e) {
											var combo = e.combo;
											combo.collapse(); // 折叠
											if (!e.forceAll) { // 模糊查询走的方法
												var value = e.query;
												if(value != null && value != ''){
													combo.getStore().load({ params : { 'query' : value } });
												}else{
													combo.getStore().load();
												}
												combo.expand(); // 展开
												return false;
											}else{ // 点击下拉框
												combo.firstExpand ++;
											}
										},
										expand : function(e) {
											if (e.firstExpand > 1) {
												e.getStore().load();
											}
										}
									}
						}, {
							fieldLabel : '生产单号',
							xtype : 'textfield',
							name : 'workOrderNo'
						}, {
							xtype : 'container',
							width : 302,
							layout : 'hbox',
							items : [{
										fieldLabel : '下发日期',
										xtype : 'datefield',
										width : 200,
										name : 'releaseDate',
										labelAlign : 'right',
										fieldStyle : 'font-size:11px;',
										format : 'Y-m-d',
										value : Ext.util.Format.date(Ext.Date.add(new Date(), Ext.Date.MONTH, -2), "Y-m-d")
									}, {
										xtype : 'text',
										text : '-',
										width : 5
									}, {
										xtype : 'datefield',
										width : 95,
										name : 'releaseDateEnd',
										fieldStyle : 'font-size:11px;',
										format : 'Y-m-d',
										value : Ext.util.Format.date(new Date(), "Y-m-d")
									}]
						}, {
							fieldLabel : '工序',
							name : 'processCode',
							xtype : 'combobox',
							editable : false,
							displayField : 'name',
							valueField : 'code',
							multiSelect : true,
							queryMode : 'remote', // 使用typeAhead时queryMode必须为remote
							store : new Ext.data.Store({
										//autoLoad : false,
										fields : ['name', 'code'],
										proxy : {
											type : 'rest',
											url : '../pro/processInfo/getAllProcess'
										}
									})
						}, {
							fieldLabel : '是否为急件',
							xtype : 'checkbox',
							name : 'isDispatch',
							checked : false
						}, {
							fieldLabel : "生产单状态",
							xtype : 'checkboxgroup',
							width : 550,
							columns : 6,
							vertical : true,
							items : [{
										boxLabel : '待下发',
										name : 'status',
										inputValue : 'TO_AUDIT'
									}, {
										boxLabel : '已下发',
										name : 'status',
										inputValue : 'TO_DO',
										checked : true
									}, {
										boxLabel : '生产中',
										name : 'status',
										inputValue : 'IN_PROGRESS',
										checked : true
									}, {
										boxLabel : '已完成',
										name : 'status',
										inputValue : 'FINISHED',
										checked : true
									}, {
										boxLabel : '已暂停',
										name : 'status',
										inputValue : 'PAUSE'
									}, {
										boxLabel : '已取消',
										name : 'status',
										inputValue : 'CANCELED'
									}]
						}, {
							xtype : 'hiddenfield',
							name : 'currentSection'
						}],
				buttons : [{
							itemId : 'search'
						}, {
							itemId : 'reset',
							handler : function(e) {
								var form = this.up("form").getForm();
								form.reset();
								var section = Ext.ComponentQuery.query('handSchedulePanel #chooseWorkSection')[0].getValue().processCode;
								form.setValues({currentSection : section})
							}
						}, {
							itemId : 'changeOrder2Paper',
							text : '转手工单'
						}, '->']
			}]
		}]
	}]
});
