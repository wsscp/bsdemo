Ext.define("bsmes.view.RealTimeEquipmentStatusList", {
	extend : 'Ext.panel.Panel',
	alias : 'widget.realTimeEquipmentStatusList',
	requires : ['bsmes.view.EquipmentStatusImage'],
	padding : '10 0 10 10',
	autoScroll : true,
	equipMapCache : null, // 存放设备缓存
	total : 0, // 设备数量
	statusCount : null, // 状态数量
	intervalTime : null, // 自动刷新时间
	initComponent : function() {
		var me = this;
		// 1、定义固定数据：{rowsArray：整个面板的容器, status: 查询状态}
		var rowsArray = [], status = "IN_PROGRESS,CLOSED,IDLE,IN_DEBUG,IN_MAINTAIN,ERROR";

		// 2、后台请求数据
		me.getEquipInfoStatusData(status, false);

		// 3、渲染整个页面
		me.drawComponentTitle(rowsArray); // 渲染头部
		me.drawComponentContent(rowsArray); // 渲染主题

		// 4、给items赋值
		me.items = rowsArray;
		me.callParent(arguments);
	},
	constructor : function() {
		var me = this;
		me.callParent(arguments);
		me.startAutoRefresh();

	},

	startAutoRefresh : function() {
		var me = this;
		me.intervalTime = setInterval(function() {
					me.autoRefresh.apply(me);
				}, 1500000);

	},

	stopAutoRefresh : function() {
		var me = this;
		clearInterval(me.intervalTime);
	},

	/**
	 * 自动刷新：刷新设备状态信息
	 */
	autoRefresh : function() {
		var me = this;
		var checkbox = me.query('checkboxgroup[name="equipStatus"]')[0];
		var status = checkbox.getValue().equipStatus.join(',');
		// 1、刷新数据：更新状态
		me.getEquipInfoStatusData(status, true);
	},

	/**
	 * 渲染面板：渲染头部，checkbox和查询按钮
	 * 
	 * @param rowsArray 面板items内容(一个数组对象)
	 */
	drawComponentTitle : function(rowsArray) {
		var me = this;
		var checkBoxArray = [];
		var statusArray = ('IN_PROGRESS,CLOSED,IDLE,IN_DEBUG,IN_MAINTAIN,ERROR').split(',');
		var statusStrArray = (Oit.msg.wip.realTimeEquipmentStatus.status.process + ',' + Oit.msg.wip.realTimeEquipmentStatus.status.closed
				+ ',' + Oit.msg.wip.realTimeEquipmentStatus.status.idle + ',' + Oit.msg.wip.realTimeEquipmentStatus.status.debug + ','
				+ Oit.msg.wip.realTimeEquipmentStatus.status.maint + ',' + Oit.msg.wip.realTimeEquipmentStatus.status.error).split(',');

		for (var i = 0; i < statusArray.length; i++) {
			var count = (me.statusCount[statusArray[i]] ? me.statusCount[statusArray[i]] : 0);
			checkBoxArray.push({
						boxLabel : '<img style="height:15px;" src="/bsstatic/icons/' + statusArray[i]
								+ '-min.png"/>&nbsp;<span style="display:block;margin-top:-19px;margin-left:17px;">' + statusStrArray[i]
								+ '(' + count + '/' + me.total + ')</span>',
						name : 'equipStatus',
						count : count,
						margin : '0 20 0 0',
						inputValue : statusArray[i],
						checked : true
					});
		}

		rowsArray.push({
					xtype : 'panel',
					layout : 'column',
					items : [{
								xtype : 'checkboxgroup',
								name : 'equipStatus',
								fieldLabel : Oit.msg.wip.realTimeEquipmentStatus.equipCurrentStatus,
								labelWidth : 60,
								layout : 'column',
								items : checkBoxArray
							}, {
								xtype : 'button',
								text : '<i style="margin-right: 10px;" class="fa fa-search"></i>'
										+ Oit.msg.wip.realTimeEquipmentStatus.search,
								margin : '0 20 0 0',
								handler : function() {
									me.stopAutoRefresh()
									Ext.Msg.wait('处理中，请稍后...', '提示');
									me.autoRefresh();
									Ext.Msg.hide(); // 隐藏进度条
									me.startAutoRefresh();
								}
							}, {
								xtype : 'button',
								text : '<i style="margin-right: 10px;" class="fa fa-cog"></i>' + Oit.msg.wip.realTimeEquipmentStatus.resert,
								handler : function() {
									var checkbox = me.query('checkboxgroup[name="equipStatus"]')[0];
									var items = checkbox.items.items;
									for (var i = 0; i < items.length; i++) {
										items[i].setValue(true);
									}
								}
							}]
				});
	},

	/**
	 * 渲染面板: 主题部分
	 * 
	 * @param rowsArray 面板items内容(一个数组对象)
	 */
	drawComponentContent : function(rowsArray) {
		var me = this;
		// 1、定义固定数据：{rows\columns:默认设备现场布局为5行\9列}
		var rows = 5, columns = 9;

		// 2、创建每行、每列、每个item：item组放入列，列组放入行，行组最终放入me
		for (var i = rows; i > 0; i--) {
			var columnArray = [];
			for (var j = 1; j <= columns; j++) {
				var getKey = (i < 10 ? ('0' + i) : i) + '-' + (j < 10 ? ('0' + j) : j);
				var equipArray = me.equipMapCache[getKey];
				var itemArray = [];
				if (equipArray) {
					for (var e = 0; e < equipArray.length; e++) {
						var itemPanel = me.createItemPanel(equipArray[e]);
						itemArray.push(itemPanel);
					}
				}
				var columnFieldSet = me.createColumnFieldSet(j, itemArray);
				columnArray.push(columnFieldSet);
			}

			var rowsFieldSet = me.createRowsFieldSet(i, columnArray);
			rowsArray.push(rowsFieldSet);
		}
	},

	/**
	 * 创建行组件
	 * 
	 * @param i 第几行
	 * @param columnArray 行数组对象
	 */
	createRowsFieldSet : function(i, columnArray) {
		var rwidth = document.body.scrollWidth - 40; // 一行的宽度
		return Ext.create('Ext.form.FieldSet', {
					title : i,
					width : rwidth,
					style : 'border:5px solid #B5B8C8;',
					padding : '0 0 5 5',
					layout : 'column',
					items : columnArray
				});
	},

	/**
	 * 创建列组件
	 * 
	 * @param j 第几列
	 * @param itemArray 列数组对象
	 */
	createColumnFieldSet : function(j, itemArray) {
		var cwidth = Math.floor((document.body.scrollWidth - 40 - 110) / 9); // 每一列的宽度
		return Ext.create('Ext.form.FieldSet', {
					title : j,
					padding : '0 5 0 5',
					width : cwidth,
					style : {
						textAlign : 'center'
					},
					margin : (j == 1 ? '' : '0 0 0 10'),
					items : itemArray
				});
	},

	/**
	 * 创建item组件
	 * 
	 * @param equip 设备对象
	 */
	createItemPanel : function(equip) {
		var me = this;
		var iwidth = Math.floor((document.body.scrollWidth - 40 - 110) / 9) - 20; // 每一列的宽度
		return {
			xtype : 'button',
			name : 'equipStatusBox',
			margin : '0 0 5 0',
			width : iwidth,
			style : {
				borderWidth : 0,
				//borderColor : 'red',
				//borderStyle : 'solid',
				textAlign : 'center',
				course : 'pointer',
				background : 'rgba(0,0,0,0.1)'
				//backgroundImage : '-moz-linear-gradient(center top , #88c6bc 0%, #59aca9 100%)'
			},
			itemId : equip.section,
			status : equip.status,
			text : '<img src="/bsstatic/icons/' + equip.status + '-min.png"><br/><font style="color : #000;font-weight: normal;">'
					+ equip.code + '<br/>' + equip.equipAlias + '</font>',
			listeners : {
				click : function(e, a) {
					me.doSearchItem(equip.code, equip.name);
				},
				mouseover : function(button, e, eOpts) {

				},
				mouseout : function(button, e, eOpts) {

				}
			}
		}
	},

	/**
	 * 请求后台获取设备状态信息
	 * 
	 * @param status 查询状态
	 * @param isfresh 是否刷新
	 */
	getEquipInfoStatusData : function(status, isfresh) {
		var me = this;
		me.equipMapCache = {};
		// 同步请求获取设备状态信息
		Ext.Ajax.request({
					url : 'realTimeEquipmentStatus/realSearch',
					method : 'GET',
					async : false,
					params : {
						status : status
					},
					success : function(response) {
						if (response.responseText) {
							var result = Ext.decode(response.responseText);
							if (isfresh) {
								me.freshEquipInfo(result)
							} else {
								me.initEquipInfoCache(result)
							}
						}
					}
				});
	},

	/**
	 * 组建初始化：处理数据查询结果
	 * 
	 * @param result 查询结果
	 */
	initEquipInfoCache : function(result) {
		var me = this;
		me.statusCount = {};
		var equipInfos = result.equipInfos;
		for (i in equipInfos) {
			var section = equipInfos[i].section;
			var mapKey = section.substring(0, section.lastIndexOf("-"));
			if (me.equipMapCache[mapKey]) {
				me.equipMapCache[mapKey].push(equipInfos[i]);
			} else {
				me.equipMapCache[mapKey] = [equipInfos[i]];
			}

			if (me.statusCount[equipInfos[i].status] >= 0) {
				me.statusCount[equipInfos[i].status] = me.statusCount[equipInfos[i].status] + 1;
			} else {
				me.statusCount[equipInfos[i].status] = 0;
			}
		}
		me.total = result.totals;
	},

	/**
	 * 刷新数据：处理数据查询结果
	 * 
	 * @param result 查询结果
	 */
	freshEquipInfo : function(result) {
		var me = this;
		me.statusCount = {};
		// 1、循环设备信息，更新对应的设备状态
		var equipInfos = result.equipInfos;
		for (i in equipInfos) {
			var equip = equipInfos[i];
			var item = me.query('#' + equip.section);
			if (item && item.length > 0) { // 设备存在，更新
				var equipComponent = item[0];
				if (equipComponent.status != equip.status) { // 设备状态不同，更新
					equipComponent.status = equip.status;
					equipComponent.update('<img src="/bsstatic/icons/' + equip.status + '-min.png"><br/>' + equip.code + '<br/>'
							+ equip.equipAlias);
				}
			} else { // 设备不存在，更新到对应的区域上

			}

			// 2、更新不同状态的设备数量
			if (me.statusCount[equipInfos[i].status] >= 0) {
				me.statusCount[equipInfos[i].status] += 1;
			} else {
				me.statusCount[equipInfos[i].status] = 0;
			}
		}

		// 3、将设备数量值更新到显示checkbox
		var checkbox = me.query('checkboxgroup[name="equipStatus"]')[0];
		var items = checkbox.items.items;
		for (var i = 0; i < items.length; i++) {
			var count = me.statusCount[items[i].inputValue];
			if (count && items[i].count != count) {
				items[i].count = count;
				var boxLabel = items[i].boxLabel;
				items[i].getEl().down('.x-form-cb-label').update(boxLabel.replace(/\(\d+\/\d+\)/g, '(' + count + '/' + me.total + ')'));
			}
		}

		// 暂时注释掉，十分影响效率
		// 4、判断是否显示或者隐藏 
		//		var status = checkbox.getValue().equipStatus;
		//		var equipStatusBoxArray = me.query('panel[name="equipStatusBox"]');
		//		for (i in equipStatusBoxArray) {
		//			var equipStatusBox = equipStatusBoxArray[i];
		//			if (me.arrayContains(status, equipStatusBox.status)) { // 状态在勾选里面，显示
		//				if (!equipStatusBox.isVisible()) {
		//					equipStatusBox.setVisible(true);
		//				}
		//			} else { // 状态不在勾选里面，影藏
		//				if (equipStatusBox.isVisible()) {
		//					equipStatusBox.setVisible(false);
		//				}
		//			}
		//		}
	},

	/**
	 * 查看设备明细信息：图标利用率
	 */
	doSearchItem : function(equipCode, equipName) {
		var me = this;
		parent.openTab(Oit.msg.wip.realTimeEquipmentStatus.equipStatusMointorHistory, 'wip/realEquipStatusChart.action?code=' + equipCode
						+ '&name=' + equipName);
	},

	/**
	 * 判断数组中包含element元素
	 * 
	 * @param array 源数组
	 * @param element 可为数组或者单个
	 */
	arrayContains : function(array, element) {
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
});