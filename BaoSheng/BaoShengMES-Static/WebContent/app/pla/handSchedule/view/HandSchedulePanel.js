/**
 * 主页面
 */

Ext.define("bsmes.view.HandSchedulePanel", {
			extend : 'Ext.panel.Panel',
			autoScroll : true,
			alias : 'widget.handSchedulePanel',
			layout : 'fit',
			items : [{
						xtype : 'handScheduleGrid' // 影藏生产单ID，用于下发生产单
					}],
			tbar : new Ext.Toolbar({
						style : 'background:-moz-linear-gradient(53% 87% 270deg, #FFFFFF, #C0C0C0 68%)',
						id : 'panelButtonId',
						items : [{
									itemId : 'showWorkOrderList',
									text : '查看生产单'
								}, {
									itemId : 'mergeYunMuProduce',
									text : '排云母绕包生产单'
								}, {
									itemId : 'mergeProduce',
									text : '排生产单'
								}, {
									itemId : 'chooseWorkSection',
									fieldLabel : '工段选择',
									labelWidth : 70,
									name : 'processCode',
									xtype : 'radiogroup',
									vertical : true,
									defaults : { // 设置样式
										width : 80
										// 设置label的宽度
									},
									items : [{
												boxLabel : '绝缘',
												name : 'processCode',
												inputValue : 'JY',
												itemId : 'JY_DD',
												checked : true
											}, {
												boxLabel : '成缆',
												name : 'processCode',
												itemId : 'CL_DD',
												inputValue : 'CL'
											}, {
												boxLabel : '护套',
												name : 'processCode',
												itemId : 'HT_DD',
												inputValue : 'HT'
											}],
									listeners : {
										change : function(radoi, newValue, oldValue, eOpts) {
											var me = this;
											//获取用户角色，绝缘调度，成缆调度，护套调度
											var roleList = Ext.fly('roleList').getAttribute('roleList');
											//获取handSchedulePanel界面按钮
											var bts = Ext.ComponentQuery.query('#panelButtonId button');
											var handSchedulePanel = Ext.ComponentQuery.query('handSchedulePanel')[0];
											var removeed = handSchedulePanel.removeAll(true);
											var items = [], component;
											if (newValue.processCode == 'JY') {
												component = Ext.create('bsmes.view.HandScheduleGrid');
												//看用户是否有绝缘调度的权限，没有就将操作按钮隐藏
												if(roleList.indexOf(newValue.processCode)>-1){
													Ext.Array.each(bts, function(btn, index, countriesItSelf) {
													    btn.show();
													});
												}else{
													Ext.Array.each(bts, function(btn, index, countriesItSelf) {
													    btn.hide();
													});
												}
												component.getStore().load();
											} else {
												component = Ext.create('bsmes.view.HandSchedule2Grid');
												if(roleList.indexOf(newValue.processCode)>-1){
													component.down('#changeOrder2Paper').show();
													Ext.Array.each(bts, function(btn, index, countriesItSelf) {
														if(btn.itemId == 'mergeYunMuProduce'){
															btn.hide();
														}else{
															btn.show();
														}
													});
												}else{
													component.down('#changeOrder2Paper').hide();
													Ext.Array.each(bts, function(btn, index, countriesItSelf) {
													    btn.hide();
													});
												}
												component.down('form').getForm().setValues({currentSection : newValue.processCode});
												component.getStore().load();
											}
											items.push(component);
											handSchedulePanel.items.add(items);
											handSchedulePanel.doLayout();
										}
									}
								}, '->', {
									itemId : 'importFinishedProduct',
									text : '导入成品现货'
								},{
									itemId : 'importTechnique',
									text : '导入内部工艺'
								}/*{
									itemId : 'importNewProduct',
									text : '导入新产品'
								}, {
									itemId : 'importNewOrder',
									text : '导入新订单'
								}, {
									itemId : 'saveNoPrcv',
									text : '保存无工艺产品'
								}*/]
					}),
					listeners : {
						afterrender : function(c,d){
							//获取用户角色：绝缘调度，成缆调度，护套调度
							var roleList = Ext.fly('roleList').getAttribute('roleList');
							//初始化界面是绝缘界面，要先看是否有绝缘调度权限
							if(roleList.length ==0 || roleList.indexOf("JY_DD") == -1){
								//没有角色权限，按钮隐藏
								var bts = c.query('#panelButtonId button');
								Ext.Array.each(bts, function(btn, index, countriesItSelf) {
								    btn.hide();
								});
							}
						}
					},
			initComponent : function() {
				var me = this;

				this.callParent(arguments);
			}
		});

/**
 * 修改产品工艺
 * 
 * @author DingXintao
 * @param rowIndex 行号：用于更新列信息
 * @param productCode 产品编码：用于查询产品可用的工艺列表
 * @param orderItemId 订单产品ID：用于更新关联工艺信息
 * @param currentCraftsId 订单产品关联当前工艺ID：用于显示当前关联的工艺信息
 */
function changeCrafts(rowIndex, productType, productSpec, productCode, orderItemId, oldCraftsId) {
	var win = Ext.create('bsmes.view.ChooseCraftsWindow', {
				rowIndex : rowIndex,
				oldCraftsId : oldCraftsId,
				orderItemId : orderItemId
			});
	var grid = win.down('grid');
	var store = grid.getStore();
	store.load({
				params : {
					productCode : productCode,
					productType : productType,
					productSpec : productSpec
				},
				callback : function(records, options, success) {
					for (var i = 0; i < store.getCount(); i++) {
						var record = store.getAt(i);
						if (oldCraftsId == record.get('id')) {
							grid.getSelectionModel().select(i, true);
						}
					}
				}
			});
	win.show();
};

/**
 * 查看实例工艺流程
 * 
 * @author DingXintao
 * @param craftsId 实例工艺ID
 * @param salesOrderItemId 客户订单明细id
 * @param isWip 是否从wip表里面获取
 */
function getInstanceProcessGrid(craftsId, craftsCode, salesOrderItemId, isWip) {
	Ext.Msg.wait(Oit.msg.LOADING, Oit.msg.PROMPT);
	Ext.Ajax.request({
		url : '../pro/processWip',
		method : 'GET',
		params : {
			craftsId : craftsId
		},
		success : function(response) {
			Ext.Msg.hide(); // 隐藏进度条
			var result = Ext.decode(response.responseText);
			var processArray = [], processId = null;
			Ext.Array.each(result, function(record, i) {
				processId = (processId ? processId : record.id);
				processArray.push({text :　record.seq + '-' + record.processName + '[' +record.processCode+ ']', id : record.id, leaf : true});
			});
			
			var win = Ext.create('bsmes.view.InstanceProcessWindow', {
				title : '工艺相关数据查看：' + craftsCode,
				spencialParam : {
					processId : processId, // 给个初始的processId
					processArray : processArray,
					salesOrderItemId : salesOrderItemId
				}
			});
			win.show();
		},
		failure : function(response, action) {
			Ext.Msg.hide(); // 隐藏进度条
		}
	});

//	var win = Ext.create('bsmes.view.InstanceProcessWindow', {
//				spencialParam : {
//					craftsCode : craftsCode,
//					salesOrderItemId : salesOrderItemId
//				}
//			});
//	var grid = win.down('grid');
//	var store = grid.getStore();
//	if (isWip) {
//		store.getProxy().url = '../pro/processWip';
//	} else {
//		store.getProxy().url = '../pro/process/getByCraftsId';
//	}
//	store.load({
//				params : {
//					craftsId : craftsId
//				},
//				callback : function(records, options, success) {
//				}
//			});
//
//	win.show();
};

// 查看附件
function lookUpAttachFileWindow(orderItemId, workOrderNo, contractNo) {
	Ext.Msg.wait('数据查询中，请稍后...', '提示');
	//若workOrderNo值为TECHNIQUENUM，则表示为查看内部附件
	// 1、请求后台查询
	Ext.Ajax.request({
				url : 'handSchedule/lookUpAttachFile?orderItemId=' + orderItemId + '&workOrderNo=' + workOrderNo + '&contractNo=' + contractNo,
				success : function(response) {
					Ext.Msg.hide(); // 隐藏进度条
					var data = Ext.decode(response.responseText);
					// 2、数据处理，按合同号归类
					var gridDataMap = {};
					Ext.Array.each(data, function(record, i) {
								if (gridDataMap[record.contractNo]) {
									gridDataMap[record.contractNo].push(record);
								} else {
									gridDataMap[record.contractNo] = [record];

								}
							});
					
					if(isEmptyObject(gridDataMap)){
						Ext.Msg.alert(Oit.msg.WARN, '没有附件');
						return;
					}
					// 3、弹出窗口显示附件列表
					var wint = Ext.create('bsmes.view.LookUpAttachFileWindow', {
								gridDataMap : gridDataMap,
								workOrderNo : workOrderNo
							});
					wint.show();
				}
			});
}


/**
 * 查看备注说明
 */
function showremarks(value) {
	value = value.replace(/<br\/>/g, "\r\n"); 
	var win = new Ext.Window({
				title : '查看备注说明',
				modal : true,
				plain : true,
				width : Math.round(document.body.scrollWidth / 2),
				height : Math.round(document.body.scrollHeight / 2),
				layout : 'fit',
				items : [{
							xtype : 'textarea',
							padding : '5',
							readOnly : true,
							value : value
						}],
				buttons : ['->', {
							text : '关闭',
							handler : function() {
								this.up('window').close();
							}
						}]
			});
	win.show();
};

/**
 * 弹出设备终端窗口，勿删，通用
 */
function intoMachine(id) {
	var first = id.indexOf("[") + 1;
	var last = id.indexOf("]");
	var length = last - first;
	var equipId = id.substr(first, length);
	Ext.Ajax.request({
				url : 'handSchedule/getEquipInfo',
				params : {
					id : equipId
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					window.open('/bsmes/wip/terminal.action?ip=' + result[0].clientIp + '&mac=' + result[0].clientMac);
				}
			});
};

// 查看[生产单/订单工段]的完成进度，勿删，通用
function showPercentDetail(workOrderNo, orderItemId, section) {
	Ext.Msg.wait('数据查询中，请稍后...', '提示');
	// 1、请求后台查询
	Ext.Ajax.request({
		url : 'handSchedule/getWorkOrderFinishDetail?workOrderNo=' + workOrderNo + 
			'&orderItemId=' + orderItemId + '&section=' + section,
		success : function(response) {
			Ext.Msg.hide(); // 隐藏进度条
			var data = Ext.decode(response.responseText);
			// 2、弹出窗口显示附件列表
			var win = Ext.create('bsmes.view.LookUpProgressWindow', {
						gridDataArray : data
					});
			win.show();
		}
	});
}
//查看成品现货使用情况
function showSpecialFlag(salesOrderItemId){
	Ext.Ajax.request({
		url : 'handSchedule/getFinishedProductById?salesOrderItemId=' + salesOrderItemId,
		success : function(response) {
			var data = Ext.decode(response.responseText);
			var win = new Ext.window.Window({
				title : "成品现货" ,
				height: 200,
			    width: 600,
			    layout: 'fit',
				items : [ {
					xtype : 'grid',
					store:Ext.create('Ext.data.Store',{
						fields : [{name : 'model',type:'string'},{name:'spec',type:'string'},{name:'length',type:'double'},
						          {name:'serialNum',type:'string'},{name:'dish',type:'string'},{name:'region',type:'string'},
						          {name :'qualifying',type:'string'}],
						data : data
					}),
					columns : [{
						dataIndex : 'serialNum',
						flex : 1,
						text : '序号'
					},{
						dataIndex : 'model',
						flex : 1,
						text : '型号'
					},{
						dataIndex : 'spec',
						flex : 1,
						text : '规格'
					},{
						dataIndex : 'dish',
						flex : 1,
						text : '盘具'
					},{
						dataIndex : 'qualifying',
						flex : 1,
						text : '排位'
					},{
						dataIndex : 'region',
						flex : 1,
						text : '区域'
					},{
						dataIndex : 'length',
						flex : 1,
						text : '使用长度'
					}]
				} ]
			}).show();
		}
	});
}

/**
 * 重写字符串以某开头
 */
String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
}

/**
 * js中的string.format
 */
String.prototype.format = function(args) {
	var result = this;
	if (arguments.length > 0) {
		if (arguments.length == 1 && typeof(args) == "object") {
			for (var key in args) {
				if (args[key] != undefined) {
					var reg = new RegExp("({" + key + "})", "g");
					result = result.replace(reg, args[key]);
				}
			}
		} else {
			for (var i = 0; i < arguments.length; i++) {
				if (arguments[i] != undefined) {
					// var reg = new RegExp("({[" + i + "]})",
					// "g");//这个在索引大于9时会有问题，谢谢何以笙箫的指出
					var reg = new RegExp("({)" + i + "(})", "g");
					result = result.replace(reg, arguments[i]);
				}
			}
		}
	}
	return result;
}

/**
 * 判断数组中包含element元素
 * 
 * @param array 源数组
 * @param element 可为数组或者单个
 */
arrayContains = function(array, element) {
	if (Object.prototype.toString.call(element) === '[object Array]') {
		for (var j = 0; j < element.length; j++) {
			var has = false;
			for (var i = 0; i < array.length; i++) {
				if (array[i] == element[j]) {
					has = true;
					break;
				}
			}
			if (!has) {
				return false;
			}
		}
		return true;
	} else {
		for (var i = 0; i < array.length; i++) {
			if (array[i] == element) {
				return true;
			}
		}
		return false;
	}
}

/**
 * 向数组中添加一个数组
 * 
 * @param array 源数组
 * @param element 添加的数组
 * @param filter 过滤的数组
 */
arrayAddAll = function(array, element, filter) {
	if (!(Object.prototype.toString.call(element) === '[object Array]')) {
		return false;
	}
	for (var i = 0; i < element.length; i++) {
		if (Object.prototype.toString.call(filter) === '[object Array]') {
			if (!arrayContains(array, element[i]) && !arrayContains(filter, element[i])) {
				array.push(element[i]);
			}
		} else {
			if (!arrayContains(array, element[i])) {
				array.push(element[i]);
			}
		}
	}
}

/**
 * 删除数组中某个元素
 * 
 * @param array 源数组
 * @param element 可为数组或者单个
 */
arrayRemove = function(array, element) {
	if (Object.prototype.toString.call(element) === '[object Array]') {
		for (var j = 0; j < element.length; j++) {
			var index = -1;
			for (var i = 0; i < array.length; i++) {
				if (array[i] == element[j]) {
					index = i;
					break;
				}
			}
			if (index != -1) {
				array.splice(index, 1);
			}
		}
	} else {
		var index = -1;
		for (var i = 0; i < array.length; i++) {
			if (array[i] == element) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			array.splice(index, 1);
		}
	}
}

/**
 * 替换字符串中的字段.
 * 
 * @param {String} str 模版字符串
 * @param {Object} o json data
 * @param {RegExp} [regexp] 匹配字符串的正则表达式
 */
function StringFormat(str, o, regexp) {
	return str.replace(regexp || /\\?\{([^{}]+)\}/g, function(match, name) {
				return (o[name] === undefined) ? '' : o[name];
			});
}

// 获取uuid			
function uuid(){
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
 
    var uuid = s.join("");
    return uuid;
}

// 判断指定参数是否是一个空对象
function isEmptyObject(obj){
    var name;
    for ( name in obj ) {
        return false;
    }
    return true;
}

