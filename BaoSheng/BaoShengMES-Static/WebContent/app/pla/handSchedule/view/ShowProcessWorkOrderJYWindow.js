/**
 * 工序选择生成生产单
 */
Ext.define('bsmes.view.ShowProcessWorkOrderJYWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.showProcessWorkOrderJYWindow',
			width : document.body.scrollWidth,
			height : document.body.scrollHeight,
			titleAlign : 'center',
			modal : true,
			plain : true,
			autoScroll : true,
			bodyStyle : 'overflow-x:hidden;',
			allDatas : null, // 页面加载所需要数据，open之前已经全部查询好了
			workOrderRecord : null, // 父页面记录
			workFlowInfoMap : {}, // 生产单流程记录
			
			ymOrderGrid:{
				id : 'orderTable',
			    datas : [],
			    columns : [{dataIndex : 'CONTRACTNO',text : '合同号', renderer : function(value, record){
					    var reg = /[a-zA-Z]/g;
						return (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length - 5) : value.replace(reg, "")) + '[' + record.OPERATOR + ']';
					}}, 
					{dataIndex : 'CUSTPRODUCTTYPE',text : '客户型号规格', renderer : function(value, record){
					    return value + ' ' + record.CUSTPRODUCTSPEC;
					}}, 
					{dataIndex : 'CONTRACTLENGTH', text : '合同长度'},
					{dataIndex : 'TASKLENGTH',text : '实际投产长度', renderer : function(value, record){
					    if (record.isGroup)
						    return '';
						return value + '*' + record.NUMBEROFWIRES;
					}}, 
					{dataIndex : 'OUTATTRDESC',text : '导体结构', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.conductorStruct) == "undefined" ? '' : json.conductorStruct;
					}}, 
					{dataIndex : 'OUTATTRDESC',text : '材料', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.material) == "undefined" ? '' : json.material;
					}}, 
					{dataIndex : 'OUTATTRDESC',text : '搭盖率', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.coverRate) == "undefined" ? '' : json.coverRate;
					}}
				]
			}, // 云母绕包的订单列表
			jchhOrderGrid:{
				id : 'orderTable',
			    datas : [],
			    columns : [{dataIndex : 'CONTRACTNO',text : '合同号', renderer : function(value, record){
					    var reg = /[a-zA-Z]/g;
						return (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length - 5) : value.replace(reg, "")) + '[' + record.OPERATOR + ']';
					}}, 
					{dataIndex : 'CUSTPRODUCTTYPE',text : '客户型号规格', renderer : function(value, record){
					    return value + ' ' + record.CUSTPRODUCTSPEC;
					}}, 
					{dataIndex : 'CONTRACTLENGTH', text : '合同长度', renderer : function(value, record){
					    var json = Ext.decode(record.OUTATTRDESC);
					    var splitLengthRole = typeof(json.splitLengthRole) == "undefined" ? '' : json.splitLengthRole;
						if (splitLengthRole != '')
							value += '<br/>' + splitLengthRole;
						return value;
					}},
					{dataIndex : 'TASKLENGTH',text : '实际投产长度', renderer : function(value, record){
					    if (record.isGroup)
							return '';
						var json = Ext.decode(record.OUTATTRDESC);
						var splitLengthRoleWithYuliang = typeof(json.splitLengthRoleWithYuliang) == "undefined" ? '' : json.splitLengthRoleWithYuliang;
						if (splitLengthRoleWithYuliang != '')
							value += '<br/>' + splitLengthRoleWithYuliang;
						return value;
					}}, 
					{dataIndex : 'OUTATTRDESC',text : '导体结构', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.conductorStruct) == "undefined" ? '' : json.conductorStruct;
					}}
				]
			}, // 挤出和火花的订单列表
			jcOutGrid:{
				id : 'outTable',
			    datas : [],
			    columns : [{dataIndex : 'OUTATTRDESC',text : '模芯/模套', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.moldCoreSleeve) == "undefined" ? '' : json.moldCoreSleeve;
					}}, 
					{dataIndex : 'OUTATTRDESC',text : '材料', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.material) == "undefined" ? '' : json.material;
					}}, 
					{dataIndex : 'OUTATTRDESC', text : '线芯结构', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.wiresStructure) == "undefined" ? '' : json.wiresStructure;
					}},
					{dataIndex : 'COLOR',text : '颜色'},
					{dataIndex : 'TASKLENGTH',text : '长度', renderer : function(value, record){
					    if (record.SPLITLENGTHROLEWITHYULIANG && record.SPLITLENGTHROLEWITHYULIANG != '')
							value += '<br/>' + record.SPLITLENGTHROLEWITHYULIANG;
						return value;
					}},
					{dataIndex : 'OUTATTRDESC',text : '标称厚度', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.standardPly) == "undefined" ? '' : json.standardPly;
					}},
					{dataIndex : 'OUTATTRDESC',text : '最大值', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.standardMaxPly) == "undefined" ? '' : json.standardMaxPly;
					}},
					{dataIndex : 'OUTATTRDESC',text : '最小值', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.standardMinPly) == "undefined" ? '' : json.standardMinPly;
					}},
					{dataIndex : 'OUTATTRDESC',text : '外径', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.outsideValue) == "undefined" ? '' : json.outsideValue;
					}}
				]
			}, // 右侧半成品(除火花)列表
			hhOutGrid:{
				id : 'outTable',
			    datas : [],
			    columns : [{dataIndex : 'OUTATTRDESC',text : '材料', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.material) == "undefined" ? '' : json.material;
					}}, 
					{dataIndex : 'OUTATTRDESC', text : '线芯结构', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.wiresStructure) == "undefined" ? '' : json.wiresStructure;
					}},
					{dataIndex : 'COLOR',text : '颜色'},
					{dataIndex : 'TASKLENGTH',text : '分盘要求', renderer : function(value, record){
					    if (record.SPLITLENGTHROLEWITHYULIANG && record.SPLITLENGTHROLEWITHYULIANG != '')
							value += '<br/>' + record.SPLITLENGTHROLEWITHYULIANG;
						return value;
					}},
					{dataIndex : 'WIRECOIL',text : '盘具'},
					{dataIndex : 'OUTATTRDESC',text : '标称厚度', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.standardPly) == "undefined" ? '' : json.standardPly;
					}},
					{dataIndex : 'OUTATTRDESC',text : '最大值', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.standardMaxPly) == "undefined" ? '' : json.standardMaxPly;
					}},
					{dataIndex : 'OUTATTRDESC',text : '最小值', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.standardMinPly) == "undefined" ? '' : json.standardMinPly;
					}},
					{dataIndex : 'OUTATTRDESC',text : '外径', renderer : function(value, record){
					    var json = Ext.decode(value);
						return value = typeof(json.outsideValue) == "undefined" ? '' : json.outsideValue;
					}}
				]
			}, // 右侧火花半成品列表
			orderGrid: null, // 订单列表
			outGrid: null, // 右侧半成品列表
			inGrid:{
				id : 'inTable',
			    datas : [],  
			    columns : [{dataIndex : 'MATNAME',text : '物料名称'}, 
					{dataIndex : 'INATTRDESC',text : '物料信息描述', renderer : function(value, record){
					    var json = Ext.decode(value);
					    var matDesc = '';
					    if (record.COLOR && record.COLOR != '') // 宽度
							matDesc += '<font color="red">颜色:' + record.COLOR + '</font><br/>'
						if (json.kuandu && json.kuandu != '')  // 宽度
							matDesc += '宽度:' + json.kuandu + '<br/>'
						if (json.houdu && json.houdu != '')  // 厚度
							matDesc += '厚度:' + json.houdu + '<br/>'
						if (json.caizhi && json.caizhi != '')  // 材质
							matDesc += '材质:' + json.caizhi + '<br/>'
						if (json.chicun && json.chicun != '')  // 尺寸
							matDesc += '尺寸:' + json.chicun + '<br/>'
						if (json.guige && json.guige != '')  // 规格
							matDesc += '规格:' + json.guige + '<br/>'
						if (json.dansizhijing && json.dansizhijing != '')  // 单丝直径
							matDesc += '单丝直径:' + json.dansizhijing + '<br/>'
						return matDesc;
					}}, 
					{dataIndex : 'QUANTITY', text : '单位用量', renderer : function(value, record){
					    value = Number(value).toFixed(3) + ' ' + record.UNIT;
						return value;
					}},
					{dataIndex : 'UNFINISHEDLENGTH',text : '定额总量', renderer : function(value, record){
					    value = (Number(value) * Number(record.QUANTITY) / 1000).toFixed(3) + ' ' + record.UNIT;
						return value;
					}},
					{dataIndex : 'INATTRDESC',text : '盘具要求/库位:库存', renderer : function(value, record){
						var json = Ext.decode(value);
						var disk = typeof(json.disk) == "undefined" ? '' : json.disk;
						var wireCoil = typeof(json.wireCoil) == "undefined" ? '' : json.wireCoil;
						disk = (!record.DISK || record.DISK == '') ? (disk == '' ? [] : disk.split(",")) : record.DISK.split(",");
						var res = (wireCoil == '' ? '' : ('<font color="red">盘具：</font>' + wireCoil)) + '<br/>';
						if (disk.length > 0) {
							res = res + '<font color="red">库存：</font>';
						}
						for (var i = 0; i < disk.length; i++) {
							if (disk.length > 1) {
								res = res + (i + 1) + '、' + disk[i].split(';')[0] + "<br/>";
							} else {
								res = res + disk[i].split(';')[0];
							}
						}
						return res;
					}
				}]
			}, // 左下物料需求列表

			initComponent : function() {
				var me = this;
				me.workFlowInfoMap = {};
				Ext.apply(me, {
					id : uuid(), // 给自己获取一个UUID，必须的
					title : '<div style="float:left;">' + me.title + '</div><span style="font-size: 16px;margin-left: -120px;">宝 胜 特 种 电 缆 有 限 公 司 绝 缘 工 序 生 产 单</span>',
					html : me.getContentHtml(), // 初始化整个窗口的html
					buttons : ['->', {
						itemId : 'cancel',
						text : '关 闭',
						scope : me,
						handler : me.close
					}]
				});
				
				this.callParent(arguments);
			},
			
			// 监听：window窗口加载完成后处理数据并渲染
			listeners : {
			    afterrender : function(window,  eOpts){
			    	// 在渲染好的整体页面上处理数据并加工渲染
			    	window.loadAllGridData(); 
			    }
			},
			
			// 1、初始化整个窗口的html；2、绑定事件
			getContentHtml : function(){
			    var me = this;
				var workOrder = me.workOrderRecord;
				// 1、初始化默认的列表对象: 订单列表和半成品列表
				var processCode = workOrder.processCode;
			    me.orderGrid = (processCode == 'wrapping_ymd') ? me.ymOrderGrid : me.jchhOrderGrid; 
			    me.outGrid = (processCode == 'Respool') ? me.hhOutGrid : me.jcOutGrid;
					
			    var e = new Date();
				
				// 2、是否急件、是否出口、是否陈线
				var jjckcx = (workOrder.isDispatch ? '急件，' : '') + (workOrder.isAbroad ? '出口，' : '') + (workOrder.isOldLine ? '陈线，' : '');
				jjckcx = ((jjckcx.substring(jjckcx.length - 1) == '，') ? jjckcx.substring(0, jjckcx.length - 1) : jjckcx);
				jjckcx = '<b style="color:red;margin-left: 10px;">' + jjckcx + '</b>';
				// 3、注意事项
				var noticeHtml = '<b>1、请严格对照相应工艺文件生产，平均值不小于标称值；</b><br/>' +
						'<b>2、最薄点厚度应不小于标称值的90%-0.1mm；</b><br/>' +
						'<b>3、按生产工艺、材料定额组织生产，保证产品质量；</b><br/>' +
						'<b>4、严格按照顺序生产，确保每根绝缘线芯长度，做好分段标识，不得私自分头；</b><br/>' +
						'<b>5、认真填写相关记录，按时间完成生产工作；</b><br/>' +
						'<b>6、请按生产进度要求组织生产，保证电缆按时交货。</b>';
				
				// 4、通过方法获取主要的几块区域的html
				var orderTable = me.getTable(me.orderGrid);
				var outTable = me.getTable(me.outGrid);
				var inTable = me.getTable(me.inGrid);
				var orderFieldSet = me.getFieldSet('orderTable', '订单信息', orderTable, false);
				var outFieldSet = me.getFieldSet('outTable', '制造半成品信息', outTable, true);
				var inFieldSet = me.getFieldSet('inTable', '物料需求信息', inTable, true);
				var noticeFieldset = me.getFieldSet('noticeFieldset', '注意事项', noticeHtml, true);
				var userComment = me.getFieldSet('', '备注', '<textarea id="userComment" readonly="true">' + workOrder.userComment + '</textarea>', false);
				var specialReqSplit = me.getFieldSet('', '特殊分盘要求', '<textarea readonly="true">' + workOrder.specialReqSplit + '</textarea>', false);
				
				// 5、整体布局拼接
				// 5.1、topDiv：最上层按钮及工序选择
				// 5.2、一个大的fieldset包含了一个2行2列的大table：leftTd1、rightTd1、leftTd2、rightTd2
				// 5.3、每个单元格基本都是一个n行1列的table
				var topDiv = '<div style="padding: 0px 0px 0px 3px;" id="workOrderFlowBox"></div>';
				var leftTd1 = '<table class="mesTableLayout" width="100%">' +
						'<tr>' +
						  '<td>' +
						    '<ul>' +
							  '<li style="width: 70%;">' +
							    '<span class="label">制单人：</span>' + workOrder.docMakerUserCode + 
						      '</li>' +
							  '<li style="width: 30%;text-align:right;">' +
								'<button class="btn-primary hasEvent" id="lookUpAttachButton">查看附件</button>' +
							  '</li>' +
							'</ul>' +
					      '</td>' +
						'</tr>' +
						'<tr><td><span class="label">工序名称：</span><b>' + workOrder.processName + '</b></td></tr>' +
						'<tr><td><span class="label"">机台：</span>' + workOrder.equipName + '</td></tr>' +
						'</table>';
				var rightTd1 = '<table class="mesTableLayout" width="100%">' +
								'<tr><td><span class="label">接收人：</span>' + workOrder.receiverUserCode + '</td></tr>' +
								'<tr><td>' + jjckcx + '</td></tr>' +
								'<tr><td><span class="label">要求完成日期：</span>' + ((workOrder.requireFinishDate instanceof Date) ? Ext.util.Format.date(workOrder.requireFinishDate, "Y-m-d") : Ext.util.Format.date(new Date(workOrder.requireFinishDate), "Y-m-d")) + '</td></tr>' +
								'</table>';
				var leftTd2 = '<table class="mesTableLayout" width="100%">' +
						'<tr><td>' + orderFieldSet + '</td></tr>' +
						'<tr><td>' + inFieldSet + '</td></tr>' +
						'<tr><td>' + noticeFieldset + '</td></tr>' +
						'</table>';
				var rightTd2 = '<table class="mesTableLayout" width="100%">' +
						'<tr><td>' + outFieldSet + '</td></tr>' +
						'<tr><td>' + userComment + '</td></tr>' +
						'<tr><td>' + specialReqSplit + '</td></tr>' +
						'</table>';		
				var contentHtml =  topDiv + 
					'<fieldset class="mesFieldSet" style="padding: 0px;">' +
						'<table class="mesTableLayout" width="100%">' +
						  '<tr>' +
						    '<td width="50%" valign="top">' + leftTd1 + '</td>' +
						    '<td width="50%" valign="top">' + rightTd1 + '</td>' +
						  '</tr>' +
						  '<tr>' +
						    '<td width="50%" valign="top">' + leftTd2 + '</td>' +
						    '<td width="50%" valign="top">' + rightTd2 + '</td>' +
						  '</tr>' +
						'</table>' +
					'</fieldset>';
				
				// 6、移除页面的按钮事件(不移除多次打开窗口会重复绑定事件)
				$(document, '#' + me.id).off("click", 'button.hasEvent');
				
				// 7、重新绑定页面的按钮事件
				$(document, '#' + me.id).on("click", 'button.hasEvent', function(){
				    var id = $(this).attr('id');
				    var name = $(this).attr('name');
					$(this).attr('disabled', true);
					if(id == 'lookUpAttachButton'){ // 查看附件
						lookUpAttachFileWindow('', workOrder.workOrderNo, '');
					}else if(name == 'workOrderButton'){ // 绑定查看生产单按钮
						me.showWorkOrderDetail($(this).attr('workOrderNo'));
					}
					$(this).attr('disabled', false);
				});
				
				var e1 = new Date();
				console.log('  getContentHtml--拼接html并动态绑定事件用时:' + Math.round(e1 - e))
				
				return contentHtml;
			},
			

			/**
			 * 初始化方法： 加载所有明细信息
			 * 
			 * @param orderItemIdArray 订单产品ID
			 * @param processCode 工序编码
			 * @param processName 工序名称
			 */
			loadAllGridData : function() {
				var me = this;
				var processCode = me.workOrderRecord.processCode;
				var result = me.allDatas;
				var groupObj = {}; // {型号规格: {长度:XXX, ITEMID:XX, idDetail : [XXX, XXX]}}
				me.drawingWorkOrderFlow(result.workOrderArray); // 渲染生产单流程
				me.drawingOrderGrid(processCode, result.orderItemList, groupObj); // 对返回数据进行变更，主要处理已下发长度的变化
				me.drawingInOutGridData(processCode, result, groupObj);

				// 备注高度设定
				var $userComment = $('#userComment', '#' + me.id)
				$userComment.height($userComment[0].scrollHeight);
			},

			/**
			 * @description private
			 * @description 内部私有方法
			 * @description 渲染生产单流程图
			 */
			drawingWorkOrderFlow : function(workOrderArray) {
				var me = this;
				var html = '<table><tr>';
				Ext.Array.each(workOrderArray, function(workOrder, n) {
					me.workFlowInfoMap[workOrder.workOrderNo] = workOrder; // 生产单号和生产单对象映射map放到页面对象
					
					if (n == 0)
						html += '<td><b>开始</b>&nbsp;&nbsp;<i class="fa fa-angle-right"></i><i class="fa fa-angle-right"></i></td>';
					if (n != 0)
						html += '<td>&nbsp;<i class="fa fa-angle-right"></i><i class="fa fa-angle-right"></i>&nbsp;</td>';
					html += '<td><button name="workOrderButton" ' + ((workOrder.workOrderNo == me.workOrderRecord.workOrderNo)? 'style="color:red;" class="btn-nocss"' : 'class="btn-nocss hasEvent"') + ' workOrderNo="' + workOrder.workOrderNo + '">' + workOrder.workOrderNo + '<br/>' + workOrder.processName + '</button></td>';
					if (workOrder.nextSection == '99') {
						html += '<td><i class="fa fa-angle-right"></i><i class="fa fa-angle-right"></i>&nbsp;&nbsp;<b>结束</b></td>';
					}
				});
				html += '</table></tr>';
				$('#workOrderFlowBox', '#' + me.id).append(html);
			},

			/**
			 * 对返回数据进行变更，主要处理已下发长度的变化 主页面传递
			 * 循环订单产品列表，处理未完成的长度字符串，变更订单产品中的卷字段，变更result中的长度对象
			 * Respool=0;Extrusion-Single=7333 转为 [{name:Respool,
			 * value=0},{name:Extrusion-Single, value:7333}]
			 */
			drawingOrderGrid : function(processCode, resultData, groupObj) {
				var me = this;

				// 1、将PRODEC根据订单ID去重，等于就是获取了生产单包含的订单情况
				var result = {}; // 可以看成就是订单信息
				Ext.Array.each(resultData, function(record, n) {
					var item = result[record.ID]
					if (!item) {
						result[record.ID] = Ext.clone(record);
					}
				});

				// 2、根据去重的结果处理合并的数据：主要得到长度和合并的ID
				for (var i in result) {
					var record = result[i];
					var itemObj = groupObj[record.RELATEORDERIDS];
					if (itemObj) {
						itemObj.totalLength += Math.round(record.TASKLENGTH);
					} else {
						groupObj[record.RELATEORDERIDS] = {
							totalLength : Math.round(record.TASKLENGTH),
							itemId : record.ID
						};
					} // -----------1 END
				}

				// 3、对订单进行处理，该合并的设置为合并，并变更长度
				var data = [];
				for (var id in result) {
					var order = result[id];
					var item = groupObj[order.RELATEORDERIDS];
					if (item.itemId == order.ID) {
						order.TASKLENGTH = item.totalLength; // 设置合并总量
					} else {
						order.isGroup = true;
					}
				}
				// 4、合并后的订单需要修改排序，知道哪些是在一起合的
				for (var key in groupObj) {
					for (var id in result) {
						var order = result[id];
						if (key == order.RELATEORDERIDS) {
							data.push(order);
						}
					}
				}

				// 5.2、将三个表格里面的数据重新赋值并重新加载html
				me.orderGrid.datas = data;
				me.doLayoutTable(me.orderGrid);
				
			},

			/**
			 * 排生产单 : 渲染工序和物料需求的表格
			 * 
			 * @param me 当前controller
			 * @param win 当前弹出框
			 * @param result 根据订单产品ID和工序编码获取的工序列表
			 */
			drawingInOutGridData : function(processCode, result, groupObj) {
				var me = this;
				// data存放工序列表record，matOaData存放物料需求列表record，Map为临时缓存
				var data = [], dateMap = {}, matOaData = [], matOaDataMap = {};

				Ext.Array.each(result.matList, function(record, i) {
					var item = matOaDataMap[record.MATCODE + me.getReplaceColor(record.COLOR) + record.QUANTITY];
					if (item) {
						item.UNFINISHEDLENGTH = item.UNFINISHEDLENGTH + record.UNFINISHEDLENGTH;
					} else {
						matOaDataMap[record.MATCODE + me.getReplaceColor(record.COLOR) + record.QUANTITY] = record;
					}
				});

				// 16.3.2特别修改：针对同一个产品中包含有相同颜色的线，火花部分不允许合并，现将其中稍作处理，添加一个颜色数量来区分是否同一个产品中的
				var colorCountMap = {};
				Ext.Array.each(result.orderItemList, function(record, i) {
					// 颜色计数
					if (colorCountMap[record.ID + record.COLOR]) {
						colorCountMap[record.ID + record.COLOR] += 1;
					} else {
						colorCountMap[record.ID + record.COLOR] = 1
					}
					record.COLORCOUNT = colorCountMap[record.ID + record.COLOR];

					if (record.SPLITLENGTHROLE && record.SPLITLENGTHROLE != '') {
						var specSplitRequire = me.analysisSplitRoleExpression(record.SPLITLENGTHROLE);
						Ext.Array.each(specSplitRequire, function(require, m) {
							var mapData = Ext.clone(record);
							var outyl = me.redundantAmount(record.PRODUCTTYPE, require.length, record.WIRESSTRUCTURE,
									processCode); // 计算余量
							mapData.SPLITLENGTHROLEWITHYULIANG = require.volNum == 1
									? (Number(require.length) + Number(outyl))
									: ((Number(require.length) + Number(outyl)) + '*' + require.volNum);
							mapData.TASKLENGTH = Math.round((Number(require.length) + Number(outyl)) * require.volNum);
							dateMap['random-' + Math.random()] = mapData;
						});
					} else {
						// 合并规则加上relateOrderIds：相关联的订单ID，有调度手动合并
						var relateOrderIds = record.RELATEORDERIDS; // @editor:丁新涛,
						var productSpec = '';
						// record.PRODUCTSPEC.substring(record.PRODUCTSPEC.lastIndexOf('*') + 1, record.PRODUCTSPEC.length);
						var item = dateMap[relateOrderIds + productSpec + me.getReplaceColor(record.COLOR) + record.COLORCOUNT];
						if (item) {
							item.TASKLENGTH += Math.round(record.TASKLENGTH);
						} else {
							dateMap[relateOrderIds + productSpec + me.getReplaceColor(record.COLOR) + record.COLORCOUNT] = record;
						}
					}
				});

				for (var key in dateMap) {
					data.push(dateMap[key]);
				}
				for (var key in matOaDataMap) {
					matOaData.push(matOaDataMap[key]);
				}

		        processCode != 'Respool' ? $('#inTable', '#' + me.id).show() : $('#inTable', '#' + me.id).hide(); // 如果是火花，物料不显示, 不允许编辑订单长度
				if (processCode == 'wrapping_ymd') { // 云母带绕包不显示产出
					$('#outTable', '#' + me.id).hide();
					$('#noticeFieldset', '#' + me.id).hide();
				} else {
					$('#outTable', '#' + me.id).show();
					$('#noticeFieldset', '#' + me.id).show();
				}
				// 5.2、将投入产出两个表格里面的数据重新赋值并重新加载html
				me.outGrid.datas = data;
				me.inGrid.datas = matOaData;
				me.doLayoutTable(me.outGrid);
				me.doLayoutTable(me.inGrid);
				
			},
			
			// 获取一个FieldSet的html
			getFieldSet : function(id, title, content, hidden) {
				var me = this;
				var html = '<fieldset';
				if(id != ''){
					html += ' id="' + id + '"';
				}
					html += ' style="display: ' + (hidden ? 'none' : 'block') + ';" class="mesFieldSet">';
				if (title != '') {
					html += '<legend>' + title + '</legend>';
				}
				html += content + '</fieldset>';
				return html;
			},

			// 获取一个table的html
			getTable : function(grid) {
				var me = this;
				var html = '';
				if(grid.toolBar && grid.toolBar != ''){
					html += grid.toolBar;
				}
				html += '<table class="mesTable" cellPadding="0" width="100%">';
				html += '<thead>';
				html += '<tr>';
				for (var i in grid.columns) {
					if(grid.columns[i].xtype && grid.columns[i].xtype == 'checkbox'){
						html += '<th><input type="checkbox" class="checkAll"></th>';
					}else{
						html += '<th>' + grid.columns[i].text + '</th>';
					}
				}
				html += '</tr>';
				html += '</thead>';
				html += '<tbody>';

				if (grid.datas && grid.datas.length > 0) {
					for (var i in grid.datas) {
						html += '<tr>';
						var data = grid.datas[i];
						if(!data.id){
							data.id = uuid();
						}
						for (var j in grid.columns) {
							if(grid.columns[j].xtype && grid.columns[j].xtype == 'checkbox'){
								html += '<td><input type="checkbox" id="' + data.id + '"></td>';
							}else{
								var cell = (data[grid.columns[j].dataIndex] ? data[grid.columns[j].dataIndex] : '');
								if(grid.columns[j].renderer){
									cell = grid.columns[j].renderer(cell, grid.datas[i])
								}
								html += '<td>' + cell + '</td>';
							}
						}
						html += '</tr>';
					}
				}
				html += '</tbody>';
				html += '</table>';
				return html;
			},
			
			// 重新渲染制定的table
			doLayoutTable : function(grid) {
				var me = this;
				var html = '';
				if(grid.toolBar && grid.toolBar != ''){
					html += grid.toolBar;
				}
				html += '<table class="mesTable" cellPadding="0" width="100%">';
				html += '<thead>';
				html += '<tr>';
				for (var i in grid.columns) {
					if(grid.columns[i].xtype && grid.columns[i].xtype == 'checkbox'){
						html += '<th><input type="checkbox" class="checkAll"></th>';
					}else{
						html += '<th>' + grid.columns[i].text + '</th>';
					}
				}
				html += '</tr>';
				html += '</thead>';
				html += '<tbody>';

				if (grid.datas && grid.datas.length > 0) {
					for (var i in grid.datas) {
						html += '<tr>';
						var data = grid.datas[i];
						if(!data.id){
							data.id = uuid();
						}
						for (var j in grid.columns) {
							if(grid.columns[j].xtype && grid.columns[j].xtype == 'checkbox'){
								html += '<td><input type="checkbox" id="' + data.id + '"></td>';
							}else{
							var cell = (data[grid.columns[j].dataIndex] ? data[grid.columns[j].dataIndex] : '');
								if(grid.columns[j].renderer){
									cell = grid.columns[j].renderer(cell, grid.datas[i])
								}
								html += '<td>' + cell + '</td>';
							}
						}
						html += '</tr>';
					}
				}
				html += '</tbody>';
				html += '</table>';
				$('#' + grid.id + ' table', '#' + me.id).remove();
				$('#' + grid.id + ' div.toolBar', '#' + me.id).remove();
				$('#' + grid.id, '#' + me.id).append(html);
			},


			getReplaceColor : function(color) {
				if (color) {
					return color.replace('双色', '').replace('色', '');
				} else {
					return '';
				}
			},

			/**
			 * 解析表达式，返回一个数组解析式
			 * 
			 * @description 前：1500*1+1000*2+500*3
			 * @description 后：[{length: 1500, volNum: 1}, {length: 1000, volNum:
			 *              2}, {length: 500, volNum: 3}]
			 */
			analysisSplitRoleExpression : function(splitLengthRole) {
				var specSplitRequire = [];
				if (splitLengthRole && splitLengthRole != '') {
					Ext.Array.each(splitLengthRole.split('+'), function(roleItem, i) {
								if (roleItem.split('*').length > 1) {
									specSplitRequire.push({
												length : roleItem.split('*')[0],
												volNum : roleItem.split('*')[1]
											});
								} else {
									specSplitRequire.push({
												length : roleItem,
												volNum : 1
											});
								}
							});
					return specSplitRequire;
				} else {
					return [];
				}
			},

			/**
			 * 余量计算 四舍五入，取整数
			 */
			redundantAmount : function(productType, length, wiresStructure, processCode) {
				var yuliang = 0;
				if (processCode && processCode == 'wrapping_ymd') {
					yuliang = length * 0.05;
				} else {
					if (productType.indexOf('DJ') > 0) {
						if (length > 500) {
							yuliang = length * 0.02;
						} else {
							yuliang = 10;
						}
					} else {
						if (wiresStructure == 'A') {
							if (length > 1000) {
								yuliang = length * 0.01;
							} else {
								yuliang = 10;
							}
						} else if (wiresStructure == 'B') {
							if (length > 600) {
								yuliang = length * 0.015;
							} else {
								yuliang = 10;
							}
						} else if (wiresStructure == 'C') {
							if (length > 500) {
								yuliang = length * 0.02;
							} else {
								yuliang = 10;
							}
						}
					}
				}
				return Math.round(yuliang.toFixed());
			},
			
			// 查看明细生产单
			showWorkOrderDetail : function(workOrderNo) {
				var me = this;
				var workOrderRecord = me.workFlowInfoMap[workOrderNo];;
				var workOrderSection = workOrderRecord.workOrderSection; // 生产单所属工段
				// 1:绝缘工段;2:成缆工段;3:护套工段
				var	processSection = (workOrderSection == '1' ? '绝缘' : (workOrderSection == '2' ? '成缆' : '护套'));
				
				Ext.Msg.wait('数据查询中，请稍后...', '提示');
				Ext.Ajax.request({
					url : 'handSchedule/showWorkOrderDetail?workOrderNo=' + workOrderRecord.workOrderNo + '&processSection=' + processSection,
					success : function(response) {
						Ext.Msg.hide(); // 隐藏进度条
						var result = Ext.decode(response.responseText);
						
						if (workOrderSection == '1') {
							win = Ext.create('bsmes.view.ShowProcessWorkOrderJYWindow', {
										title : '生产单：' + workOrderRecord.workOrderNo + '-' + workOrderRecord.processName,
										workOrderRecord : workOrderRecord,
										allDatas : result
									});
						} else if (workOrderSection == '2') {
							win = Ext.create('bsmes.view.ShowProcessWorkOrderCLWindow', {
										title : '生产单：' + workOrderRecord.workOrderNo + '-' + workOrderRecord.processName,
										workOrderRecord : workOrderRecord,
										allDatas : result
									});
						} else if (workOrderSection == '3') {
							win = Ext.create('bsmes.view.ShowProcessWorkOrderHTWindow', {
										title : '生产单：' + workOrderRecord.workOrderNo + '-' + workOrderRecord.processName,
										workOrderRecord : workOrderRecord,
										allDatas : result
									});
						}
						if (win) {
							win.show();
						}
					},
					failure : function(response, action) {
						Ext.Msg.hide(); // 隐藏进度条
						Ext.Msg.alert(Oit.msg.WARN, '查询数据失败！');
					}
				});
			}
				
		});
