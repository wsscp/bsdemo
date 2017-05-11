// 成缆工段下发生成生产单
// 页面包含：订单信息、半制品产出信息、物料需求信息
// 按钮功能：
// －－1、双击订单信息列表可以进行编辑功能：修改颜色／收盘要求／成缆绕包要求
// 注：余量方法：不合并生产，默认每盘余量5米
//
// 页面逻辑：
// 1、获取页面需要的数据：成缆工段包含的工序、此些工序所需要的投入产出信息、设备信息、接受人信息
// 2、页面渲染顺序
// －－a、渲染工序选择checkbox；
// －－b、封装订单／产出／投入数据；
// －－c、勾选所有的checkbox；
// －－d、获取对应工序的两个个grid的store数据和设备要求数据
Ext.define('bsmes.view.ProcessWorkOrderCLWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.processWorkOrderCLWindow',
	width : document.body.scrollWidth,
	height : document.body.scrollHeight,
	title : '<a style="font-size: 16px;">宝 胜 特 种 电 缆 有 限 公 司 成 缆 工 序 生 产 单</a>',
	titleAlign : 'center',
	modal : true,
	plain : true,
	autoScroll : true,
	allDatas : null, // 页面加载所需要数据，open之前已经全部查询好了
	orderData : null, // 主表单数据
	orderIdProductTypeMap : null, // 订单id和产品类型的映射
	preWorkOrderNo : null, // 上一道工序生产单号
	inDataCacheMap : {}, // 投入物料数据map，用于提高查询效率
	outDataCacheMap : {}, // 产出半成品数据map，用于提高查询效率
	completeCusOrderItemIds : [], // 已经完成了的产品型号规格
	sectionEquipArray : [], // 工段的所有设备
	materialsInventory : {}, // 原材料库存信息
	workFlowInfoMap : {}, // 生产单流程记录
	equipCombobox : null, // 设备对象
	jumpProcessesMergedArray : [], // 跳过的工序描述json对象
	orderGrid:{
		id : 'orderTable',
	    datas : [],
	    edit : function(grid, record){
			grid.editRecord(record);
		},
	    columns : [{dataIndex : 'contractNo',text : '合同号', renderer : function(value, record){
			    var reg = /[a-zA-Z]/g;
				return (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length - 5) : value.replace(reg, "")) + '[' + record.operator + ']';
			}}, 
			{dataIndex : 'custProductType',text : '客户型号规格', renderer : function(value, record){
			    return value + ' ' + record.custProductSpec;
			}}, 
			{dataIndex : 'contractLength', text : '合同长度', renderer : function(value, record){
			    if (record.splitLengthRole && record.splitLengthRole != '') {
					value += '<br/>' + record.splitLengthRole;
				}
				return value;
			}}, 
			{dataIndex : 'color',text : '颜色字码',edit : true},
			{dataIndex : 'unFinishedLength',text : '成缆长度', renderer : function(value, record){
			    if (record.splitLengthRoleWithYuliang && record.splitLengthRoleWithYuliang != '') {
					value += '<br/>' + record.splitLengthRoleWithYuliang;
				}
				return value;
			}, edit : true}, 
			{dataIndex : 'conductorStruct',text : '导体结构'},
			{dataIndex : 'standardPly',text : '标称厚度'},
			{dataIndex : 'standardMaxPly',text : '最大值'},
			{dataIndex : 'standardMinPly',text : '最小值'},
			{dataIndex : 'outsideValue',text : '外径'},
			{dataIndex : 'wireCoil',text : '收盘要求',edit : true},
			{dataIndex : 'outMatDesc',text : '成缆/绕包要求(双击可修改)', maxWidth : (document.body.scrollWidth/3), edit : true}
		]
//		,
//		toolBar : '<div class="toolBar" style="padding: 0px 0px 4px 0px;">' +
//			'<input type="text" id="color" value="" placeholder="颜色">' +
//			'<input type="text" id="outMatDesc" value="" style="margin-left: 5px;width: 300px;" placeholder="成缆/绕包要求">' +
//			'<button class="btn-primary hasEvent" id="" style="margin-left: 5px;">确定</button>' +
//		'</div>'
	}, // 云母绕包的订单列表
	inGrid:{
		id : 'inTable',
	    datas : [],
	    columns : [{dataIndex : 'matName',text : '物料名称'}, 
			{dataIndex : 'color',text : '物料信息描述', renderer : function(value, record){
			    var matDesc = '';
				if (value && value != '')
					matDesc += '<font color="red">颜色:' + value + '</font><br/>'
				if (record.kuandu && record.kuandu != '')  // 宽度
					matDesc += '宽度:' + record.kuandu + '<br/>'
				if (record.houdu && record.houdu != '')  // 厚度
					matDesc += '厚度:' + record.houdu + '<br/>'
				if (record.caizhi && record.caizhi != '')  // 材质
					matDesc += '材质:' + record.caizhi + '<br/>'
				if (record.chicun && record.chicun != '')  // 尺寸
					matDesc += '尺寸:' + record.chicun + '<br/>'
				if (record.guige && record.guige != '')  // 规格
					matDesc += '规格:' + record.guige + '<br/>'
				if (record.dansizhijing && record.dansizhijing != '')  // 单丝直径
					matDesc += '单丝直径:' + record.dansizhijing + '<br/>'
				return matDesc;
			}}, 
			{dataIndex : 'quantity', text : '单位用量', renderer : function(value, record){
			    value = Number(value).toFixed(3) + ' ' + record.unit;
				return value;
			}},
			{dataIndex : 'unFinishedLength',text : '定额总量', renderer : function(value, record){
			    value = (Number(value) * Number(record.quantity) / 1000).toFixed(3) + ' ' + record.unit;
				return value;
			}},
			{dataIndex : 'disk',text : '盘具要求/库位:库存', renderer : function(value, record){
				var disk = (value == '' ? [] : value.split(","));
				var res = ((!record.wireCoil || record.wireCoil == '') ? '' : ('<font color="red">盘具：</font>' + record.wireCoil)) + '<br/>';
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

		// 清除页面缓存，ext管理容器会保留页面缓存内容
		me.inDataCacheMap = {}; // 投入物料数据map，用于提高查询效率
		me.outDataCacheMap = {}; // 产出半成品数据map，用于提高查询效率
		me.completeCusOrderItemIds = []; // 已经完成了的产品型号规格
		me.sectionEquipArray = []; // 工段的所有设备
		me.materialsInventory = {}; // 原材料库存信息
		me.workFlowInfoMap = {}; // 生产单流程信息
				
		Ext.apply(me, {
			id : uuid(), // 给自己获取一个UUID，必须的
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
	    	window.drawingProcessComponent(); 
	    }
	},
	
	// 1、初始化整个窗口的html；2、绑定事件
	getContentHtml : function(){
	    var me = this;
	    
	    var e = new Date();
				
		// 2、是否急件、是否出口、是否陈线
		var jjckcx = '<label><input type="checkbox" id="isDispatch" value="1" style="margin-left: 10px;vertical-align:middle;"><b style="color:red;vertical-align:middle;">急件</b></label>' +
				'<label><input type="checkbox" id="isAbroad" value="1" style="margin-left: 30px;vertical-align:middle;"><b style="color:red;vertical-align:middle;">出口</b></label>';
		// 3、注意事项
		var noticeHtml = '<b>1、成缆最外层绞向为右向，相邻层绞向相反；</b><br/>' +
				'<b>2、绝缘线芯采用数字标志时，由内层到外层从1开始按自然顺序顺时针方向排列；</b><br/>' +
				'<b>3、绿/黄双色绝缘线芯应放置在缆芯最外层；</b><br/>' +
				'<b>4、成缆绕包的重叠率为带宽的15%-25%；</b><br/>' +
				'<b>5、产品加工时应严格遵守机台的操作规程；</b><br/>' +
				'<b>6、产品加工结束后，应挂上制造卡，注明型号、规格、制造长度、制造日期、制造人。</b>';
		
		// 4、通过方法获取主要的几块区域的html
		var orderFieldSet = me.getFieldSet({id : 'orderTable'}, '订单信息', '', false);
		var inFieldSet = me.getFieldSet({id : 'inTable'}, '物料需求信息', '', false);
		var userComment = me.getFieldSet(null, '备注', '<textarea id="userComment"></textarea>', false);
		var noticeFieldset = me.getFieldSet({id : 'noticeFieldset'}, '注意事项', noticeHtml, false);
		
		// 5、整体布局拼接
		// 5.1、topDiv：最上层按钮及工序选择
		// 5.2、一个大的fieldset包含了一个2行2列的大table：leftTd1、rightTd1、leftTd2、rightTd2
		// 5.3、每个单元格基本都是一个n行1列的table
		var topDiv = '<div id="workOrderFlowBox" style="padding: 5px 0px 4px 3px;"><button class="btn-primary hasEvent" style="float:left;margin-top:8px;" id="jumpProcess">跳过工序</button></div>' +
				'<div id="chooseProcess" style="padding: 0px 0px 4px 0px;"></div>';
		var leftTd1 = '<table class="mesTableLayout" width="100%">' +
				'<tr>' +
				  '<td>' +
				    '<ul>' +
					  '<li style="width: 70%;">' +
					    '<span class="label">制单人：</span>' + Ext.fly('userSessionKeyName').getAttribute('userName') + 
					    '<input type="hidden" id="docMakerUserCode" value="' + Ext.fly('userSessionKeyName').getAttribute('userName') + '">' +
				      '</li>' +
					  '<li style="width: 30%;text-align:right;">' +
						'<button class="btn-primary hasEvent" id="lookUpAttachButton">查看附件</button>' +
					  '</li>' +
					'</ul>' +
			      '</td>' +
				'</tr>' +
				'<tr><td><span class="label">工序名称：</span><b id="processName"></b><input type="hidden" id="processCode"></td></tr>' +
				'<tr><td><span class="label" style="float: left;">选择机台：</span><span id="equipCodesSpan" style="float: left;"></span></td></tr>' +
				'</table>';
		var rightTd1 = '<ul>' +
					'<li style="width: 70%;">' +
						'<table class="mesTableLayout" width="100%">' +
						'<tr><td><span class="label">接收人：</span><input type="text" id="receiverUserCode"></td></tr>' +
						'<tr><td>' + jjckcx + '</td></tr>' +
						'<tr><td><span class="label" style="float: left;">要求完成日期：</span><span id="requireFinishDateSpan" style="float: left;"></span></td></tr>' +
						'</table>' +
					'</li>' +
					'<li style="width: 30%;text-align:right;">' +
						'<button id="saveButton" class="btn-primary big  hasEvent">保存生产单</button>' +
						'<button id="issueButton" class="btn-primary big  hasEvent">下发生产单</button>' +
						'<b id="alerttext" style="display: none;margin-right: 4px;">下发成功</b>' +
					'</li>' +
				'</ul>';
		var leftTd2 = inFieldSet;
		var rightTd2 = '<table class="mesTableLayout" width="100%">' +
				'<tr><td>' + userComment + '</td></tr>' +
				'<tr><td>' + noticeFieldset + '</td></tr>' +
				'</table>';		
		var contentHtml =  topDiv + 
			'<fieldset class="mesFieldSet" style="padding: 0px;">' +
				'<table class="mesTableLayout" width="100%">' +
				  '<tr>' +
				    '<td width="50%" valign="top">' + leftTd1 + '</td>' +
				    '<td width="50%" valign="top">' + rightTd1 + '</td>' +
				  '</tr>' +
				  '<tr>' +
				    '<td width="100%" colspan=2>' + orderFieldSet + '</td>' +
				  '</tr>' +
				  '<tr>' +
				    '<td width="50%" valign="top">' + leftTd2 + '</td>' +
				    '<td width="50%" valign="top">' + rightTd2 + '</td>' +
				  '</tr>' +
				'</table>' +
			'</fieldset>';
		
		// 6、移除页面的按钮事件(不移除多次打开窗口会重复绑定事件)
		$(document, '#' + me.id).off("change", 'div#chooseProcess input[type="checkbox"].hasEvent');
		$(document, '#' + me.id).off("click", 'button.hasEvent')
		$(document, '#' + me.id).off("change", 'input[type="checkbox"].checkAll')
		
		// 7、重新绑定页面的按钮事件
		$(document, '#' + me.id).on("change", 'div#chooseProcess input[type="checkbox"].hasEvent', function(){
			var name = $(this).attr('name');
			var processChecks = $(this).closest('fieldset').find('input[type="checkbox"][name!="checkAll"]'); 
			if(name == 'checkAll'){ // 工序选择所有
				processChecks.prop('checked', $(this).prop('checked'));
			    if(!$(this).prop('checked')){
			    	processChecks.filter(":first").prop('checked', true).change();
			    }else{
			        processChecks.filter(":last").change();
			    }
			}else{
				var processChecked = processChecks.filter(":checked");
				me.changeProcessCheckbox(processChecked);
			}
		});
		$(document, '#' + me.id).on("click", 'button.hasEvent', function(){
			var id = $(this).attr('id');
			var name = $(this).attr('name');
			$(this).attr('disabled', true);
			if(id == 'saveButton'){ // 绑定保存生产单事件
				me.saveWorkOrder();
			}else if(id == 'issueButton'){ // 绑定下发生产单事件
				me.auditWorkOrder();
			}else if(id == 'lookUpAttachButton'){ // 查看附件
				me.lookUpAttachFileWindow();
			}else if(name == 'workOrderButton'){ // 绑定查看生产单按钮
				me.showWorkOrderDetail($(this).attr('workOrderNo'));
			}else if(id == "jumpProcess"){
			    me.jumpProcess();
			}
			$(this).attr('disabled', false);
		});
		$(document, '#' + me.id).on("change", 'input[type="checkbox"].checkAll', function(){
			var checked = $(this).prop('checked');
			$(this).closest('table').find('input[type="checkbox"][class!="checkAll"]').each(function(){
				$(this).prop('checked', checked);
			});
		});
		
		var e1 = new Date();
		console.log('  getContentHtml--拼接html并动态绑定事件用时:' + Math.round(e1 - e))
		
		return contentHtml;
	},

	/**
	 * 渲染工序选择控件，渲染页面按钮
	 */
	drawingProcessComponent : function() {
		var me = this;
		
		var e = new Date();

		// 1、所有的后台数据allDatas
		var result = me.allDatas; 
		
		// 2、更新保存下发按钮状态，所有工序设备放入缓存
		me.changeButtonStatus(true, false, false);
		new Ext.form.DateField({ renderTo: 'requireFinishDateSpan', itemId: 'requireFinishDate', xtype: 'datefield', format: 'Y-m-d', value: new Date(), allowBlank: false}); 
		me.sectionEquipArray = result.equipArray; 
		
		var e1 = new Date();
		console.log('drawingProcessComponent--更新按钮状态changeButtonStatus用时:' + Math.round(e1 - e))
			
		// 3、渲染生产单流程
		me.drawingWorkOrderFlow(result.workOrderArray); 
		
		var e2 = new Date();
		console.log('drawingProcessComponent--渲染生产单流程drawingWorkOrderFlow用时:' + Math.round(e2 - e1))

		// 4、设置备注和其他的信息：是否出口
		me.setUserCommentAndOther(result.receivers);

		var e3 = new Date();
		console.log('drawingProcessComponent--设置备注和其他的信息是否出口用时:' + Math.round(e3 - e2))

		// 5、请求的投入产出数据集处理放入页面缓存
		me.initInOutDataToCacheMap(result.proDecList, result.materialsInventory); 

		var e4 = new Date();
		console.log('drawingProcessComponent--请求的投入产出数据集处理放入页面缓存用时:' + Math.round(e4 - e3))

		// 6、合并订单长度
		me.initOrderGroup(); 

		var e5 = new Date();
		console.log('drawingProcessComponent--合并订单长度initOrderGroup用时:' + Math.round(e5 - e4))
		
		// 7、渲染页面checkbox工序选择组件
		me.drawingCheckboxGroup(result.processesMergedArrayStr, result.processList);

		var e6 = new Date();
		console.log('drawingProcessComponent--渲染页面checkbox工序选择组件用时:' + Math.round(e6 - e5))
		
		// 8、变更订单产品对象，将已经完成成缆下发的订单产品去除
		me.changeOrderData();

		var e7 = new Date();
		console.log('drawingProcessComponent--变更订单产品对象，将已经完成成缆下发的订单产品去除用时:' + Math.round(e7 - e6))

		// 9、全选工序选择框:用于最后促发change事件，预设全选不能促发
		me.initGroupAllChecked();

		var e8 = new Date();
		console.log('drawingProcessComponent--选中所有选择框并处理响应显示数据用时:' + Math.round(e8 - e7))
		console.log('总用时:' + Math.round(e8 - e))
	},

	/**
	 * 设置备注和其他的信息：是否出口/接收人
	 * 
	 * @param receivers 接收人
	 */
	setUserCommentAndOther : function(receivers) {
		
		var me = this;
		var remarksMap = {}, jishuyaoqiuMap = {}, processRequire = '', remarks = '', reg = /[a-zA-Z]/g;
		Ext.Array.each(me.orderData, function(order, n) {
			remarksMap[order.contractNo] = order.remarks;
			if (jishuyaoqiuMap[order.contractNo]) {
				jishuyaoqiuMap[order.contractNo].push(order.contractLength + ':' + ((!order.processRequire || order.processRequire == '') ? '无' : order.processRequire));
			} else {
				jishuyaoqiuMap[order.contractNo] = [order.contractLength + ':' + ((!order.processRequire || order.processRequire == '') ? '无' : order.processRequire)];
			}
		});
		for (conNo in remarksMap) {
			remarks = remarks + (conNo.replace(reg, "").length > 5 ? conNo.replace(reg, "").substring(conNo.replace(reg, "").length - 5) : conNo.replace(reg, "")) + ':' + '技术要求:[' + jishuyaoqiuMap[conNo]
					+ ']; \r\n      备注:' + remarksMap[conNo] + '\n';
		}
		// 判断是否是出口线
		var $userComment = $('#userComment', '#' + me.id)
		$userComment.val(remarks);
		$('#isAbroad', '#' + me.id).prop('checked', me.workOrderInfo.isAbroad);
		$('#isDispatch', '#' + me.id).prop('checked', me.workOrderInfo.isDispatch);
		$('#receiverUserCode', '#' + me.id).val(receivers.length > 0 ? receivers[0].receiverUserCode : '');
		$userComment.height($userComment[0].scrollHeight);
	},

	/**
	 * @description private 方法内私有方法
	 * @description 改变button的显示影藏状态
	 * @param saveStatus 保存按钮是否显示
	 * @param issueStatus 下发按钮是否显示
	 * @param alertStatus 提示信息按钮是否显示
	 */
	changeButtonStatus : function(saveStatus, issueStatus, alertStatus) {
		var me = this;
		var saveButton = $('button#saveButton', '#' + me.id); // 保存按钮
		var issueButton = $('button#issueButton', '#' + me.id); // 下发按钮
		var alerttext = $('button#alerttext', '#' + me.id); // 提示文字按钮
		if(saveStatus)
			saveButton.show();
		else
			saveButton.hide();
		if(issueStatus)
			issueButton.show();
		else
			issueButton.hide();
		if(alertStatus)
			alerttext.show();
		else
			alerttext.hide();
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
			html += '<td><button name="workOrderButton" class="btn-nocss hasEvent"' + 'workOrderNo="' + workOrder.workOrderNo + '">' + workOrder.workOrderNo + '<br/>' + workOrder.processName + '</button></td>';
			if (workOrder.nextSection == '99') {
				html += '<td><i class="fa fa-angle-right"></i><i class="fa fa-angle-right"></i>&nbsp;&nbsp;<b>结束</b></td>';
			}
		});
		html += '</table></tr>';
		$('#workOrderFlowBox', '#' + me.id).append(html);
	},

	/**
	 * @description 页面初始化方法
	 * @description 处理返回的结果集，转换成map对象，提高页面查询效率
	 * @param inOutDataArray 投入产出信息
	 * @param materialsInventory 原材料库存
	 */
	initInOutDataToCacheMap : function(inOutDataArray, materialsInventory) {
		var me = this;

		// 库存信息保存
		for (i in materialsInventory) {
			me.materialsInventory[materialsInventory[i].MATERIALCODE] = materialsInventory[i]; // 原材料库存
		}
		var outDataTmp, color, kuandu, houdu, caizhi, chicun, guige, firkey, seckey, inFirDataTmp, inSecDataTmp;
		Ext.Array.each(inOutDataArray, function(inOutData, n) {
					if (inOutData.inOrOut == 'OUT') {
						outDataTmp = me.outDataCacheMap[inOutData.orderItemId];
						if (outDataTmp) {
							outDataTmp.push(inOutData);
						} else {
							me.outDataCacheMap[inOutData.orderItemId] = [inOutData]
						}
					} else if (inOutData.inOrOut == 'IN') {
						// @date: 2016/09/26 特殊处理投入单位用量，对于对层的单位用量技术部是定义的多层单位用量，前台拆分到1层需要除以层数
						me.divideQuantity(inOutData);
						
						var inventory = me.materialsInventory[inOutData.matCode];
						if (inventory) {
							// console.log(inventory.MATERIALCODE + '-----' + inOutData.matCode);
							// inOutData.matType = 'MATERIALS'; // 表识为原材料
							inOutData.disk = inventory.DESCRIPTION;
							if(inventory.DISKNUMBER != null){
								inventory.DISKNUMBER.replace(/(^\s*)|(\s*$)/g,'');
								if(inventory.DISKNUMBER.length > 0){
									inOutData.wireCoil = inventory.DISKNUMBER;
								}
							}
						}
						
						color = typeof(inOutData.color) == "undefined" ? '' : inOutData.color;
						kuandu = typeof(inOutData.kuandu) == "undefined" ? '' : inOutData.kuandu;
						houdu = typeof(inOutData.houdu) == "undefined" ? '' : inOutData.houdu;
						caizhi = typeof(inOutData.caizhi) == "undefined" ? '' : inOutData.caizhi;
						chicun = typeof(inOutData.chicun) == "undefined" ? '' : inOutData.chicun;
						guige = typeof(inOutData.guige) == "undefined" ? '' : inOutData.guige;

						// var productSpec =
						// inOutData.productSpec.substring(inOutData.productSpec.lastIndexOf('*')
						// + 1, inOutData.productSpec.length);
						firkey = inOutData.processCode + inOutData.seq + inOutData.productType + inOutData.productSpec;
						seckey = inOutData.matCode + color + inOutData.quantity + kuandu + houdu + caizhi + chicun + guige;
						inFirDataTmp = me.inDataCacheMap[firkey];
						if (inFirDataTmp) {
							inSecDataTmp = inFirDataTmp[seckey];
							if (inSecDataTmp) {
								inSecDataTmp.push(inOutData)
							} else {
								inFirDataTmp[seckey] = [inOutData]
							}
						} else {
							inFirDataTmp = {};
							inFirDataTmp[seckey] = [inOutData];
							me.inDataCacheMap[firkey] = inFirDataTmp;
						}
					}
				});
	},
	
	// 平分单位用量
	// @date: 2016/09/26 特殊处理投入单位用量，对于对层的单位用量技术部是定义的多层单位用量，前台拆分到1层需要除以层数
	divideQuantity : function(inData){
		if(inData.matType != 'MATERIALS' || inData.quantity == ''){
			return;
		}
		var processName = inData.processName;
		// var cablingPattern = /(?=成缆)\d(?=层)/g;
		var num = 0;
		if(processName.indexOf('二层') >= 0){
			num = 2;
		}else if(processName.indexOf('三层') >= 0){
			num = 3;
		}else if(processName.indexOf('四层') >= 0){
			num = 4;
		}else if(processName.indexOf('五层') >= 0){
			num = 5;
		}else if(processName.indexOf('六层') >= 0){
			num = 6;
		}else if(processName.indexOf('七层') >= 0){
			num = 7;
		}
		if(num != 0){
			inData.quantity = Number(inData.quantity) / num;
		}
	},
	

	/**
	 * @description 页面初始化方法
	 * @description 渲染checkbox工序选择组
	 * @param processesMergedArrayStr 生产单已经使用过的工序组合json字符串对象
	 * @param sourceProcessList 工序对象集合
	 */
	drawingCheckboxGroup : function(processesMergedArrayStr, sourceProcessList) {
		var me = this;
		var processPanel = [];
		// processesMergedArrayStr
		// 记录成缆已经下发过的工序: 格式示例：
		// [[{"ZC-KVVRP-450/750V 4*1.5":[{name: '绕包二层_2',code
		// :'wrapping-50'},{name: '编织',code :'Braiding-60'}]}],
		// [{"ZC-KVVRP-450/750V 4*1.5":[{name: '成缆',code :'Cabling-40'},{name:
		// '绕包二层_1',code :'wrapping-50'}]}]]
		var processesMergedTimeArray = [];
		if (processesMergedArrayStr && processesMergedArrayStr != '') {
			processesMergedTimeArray = eval('([' + processesMergedArrayStr + '])');
		}

		me.processList = sourceProcessList; // 将成缆工段信息保存到页面供save后重新初始化工序信息使用
		for (var orderId in sourceProcessList) {
			var processList = sourceProcessList[orderId];
			var items = [];
			Ext.Array.each(processList, function(record, n) {
				var isSetCheck = me.hasProcessUsed(orderId, processesMergedTimeArray, record); // 是否放置Checkbox组件
				if (isSetCheck) {
					var checkbox = me.createProcessCheckbox(orderId, record);
					items.push(checkbox);
				}
			});

			if (items.length == 0) { // 工序都下发完了就不显示了
				Ext.Array.each(me.orderData, function(order, i) {
					if ((order.productType + order.productSpec) == me.orderIdProductTypeMap[orderId]) {
						me.completeCusOrderItemIds.push(order.id)
					}
				});
				continue;
			}
			var attr = {productTypeSpec : me.orderIdProductTypeMap[orderId]};
			var legend = '<label><input type="checkbox" class="hasEvent" name="checkAll" id="' + me.orderIdProductTypeMap[orderId] + 
				'" style="vertical-align:middle;">' +
				'<b style="vertical-align:middle;">工序[' + me.orderIdProductTypeMap[orderId] + ']</b>';
			var checkboxGroup = me.getFieldSet(attr, legend, items.join(''), false);
			processPanel.push(checkboxGroup);
		}
		
		$('#chooseProcess', '#' + me.id).append(processPanel.join(''));
	},

	/**
	 * 修改页面订单产品数据：将已经完成成缆下发的产品去除
	 */
	changeOrderData : function() {
		var me = this;
		if (me.completeCusOrderItemIds.length > 0) {
			var newOrderData = [];
			Ext.Array.each(me.orderData, function(order, i) { // 遍历工序组中选中的工序
				if (!arrayContains(me.completeCusOrderItemIds, order.id)) {
					newOrderData.push(order);
				}
			});
			me.orderData = newOrderData;
		}
	},

	/**
	 * @description private
	 * @description 内部私有方法
	 * @description 创建一个工序的checkbox组建
	 * @param orderId: 工序组对应的订单ID，用来当作一个name组
	 * @param toSetProcess: 当前要判断的工序对象
	 */
	createProcessCheckbox : function(orderId, toSetProcess) {
		var me = this;
		var html = '<label>' +
			'<input class="hasEvent" type="checkbox" name="' + orderId + '" text="' + toSetProcess.NAME + '" ' +
			'cableFaceName="' + (toSetProcess.cableFaceName ? toSetProcess.cableFaceName : '') + '" cableFaceValue="' + (toSetProcess.cableFaceValue ? toSetProcess.cableFaceValue : '') + '" ' +
			'cableOrderName="' + (toSetProcess.cableOrderName ? toSetProcess.cableOrderName : '') + '" cableOrderValue="' + (toSetProcess.cableOrderValue ? toSetProcess.cableOrderValue : '') + '" ' +
			'wringDistName="' + (toSetProcess.wringDistName ? toSetProcess.wringDistName : '') + '" wringDistValue="' + (toSetProcess.wringDistValue ? toSetProcess.wringDistValue : '') + '" ' +
			'coverRateName="' + (toSetProcess.coverRateName ? toSetProcess.coverRateName : '') + '" coverRateValue="' + (toSetProcess.coverRateValue ? toSetProcess.coverRateValue : '') + '" ' +
			'braidDensityName="' + (toSetProcess.braidDensityName ? toSetProcess.braidDensityName : '') + '" braidDensityValue="' + (toSetProcess.braidDensityValue ? toSetProcess.braidDensityValue : '') + '" ' +
			'value="' + toSetProcess.CODE + '" seq="' + toSetProcess.SEQ + '" id="'  + (orderId + toSetProcess.CODE + toSetProcess.SEQ + toSetProcess.NAME) + '" ' +
			'section="' + toSetProcess.SECTION + '" ' + 'nextSection="' + toSetProcess.NEXTSECTION + '" ' +
			'style="vertical-align:middle;">' +
			'<b style="vertical-align:middle;margin-right: 15px;">' + toSetProcess.NAME + '</b></label>';
		return html;
	},

	/**
	 * @description private
	 * @description 内部私有方法
	 * @description 判断当前工序是否已经使用过了
	 * @param orderId: 订单ID
	 * @param processesMergedTimeArray: 使用过的工序列表json对象
	 * @param toSetProcess: 当前要判断的工序对象
	 */
	hasProcessUsed : function(orderId, processesMergedTimeArray, toSetProcess) {
		var me = this;
		var isSetCheck = true; // 是否放置Checkbox组件
		Ext.Array.each(processesMergedTimeArray, function(processesMergedArray, m) {
			// processesMergedArray 每一次生产单的工序使用组合
			Ext.Array.each(processesMergedArray, function(craftsProcessArray, n) {
				for (var productType in craftsProcessArray) { // 工艺：工序列表
					var processArray = craftsProcessArray[productType];
					Ext.Array.each(processArray, function(process, i) { // 工序列表
						if (me.orderIdProductTypeMap[orderId] == productType && process.name == toSetProcess.NAME
								&& process.code == (toSetProcess.CODE + toSetProcess.SEQ)) {
							isSetCheck = false;
							return false;
						}
					});
					if (!isSetCheck) {
						break;
					}
				}
				if (!isSetCheck) {
					return false;
				}
			});
			if (!isSetCheck) {
				return false;
			}
		});
		return isSetCheck;
	},

	/**
	 * @description private
	 * @description 内部私有方法
	 * @description 在刚初始化页面的时候要更新所有工序关联的物料
	 */
	initGroupAllChecked : function() {
		var me = this;
		// 遍历工序选择checkbox组
		me.getChecksGroup().each(function(n){
			var checks = $(this).find('input[type="checkbox"]'); 
			checks.each(function(m, check){
				$(check).prop('checked', true);
			    if (m == checks.length - 1) 
				    $(check).change();
			});
		});
	},

	/**
	 * @description private
	 * @description 内部私有方法
	 * @description 操作checkbox时候改变物料信息
	 * @param changeMatOaArray [{ isAddItem : false, inDataMap : inDataMap
	 *            }]:是新增还是删除，关联的物料信息
	 */
	changeMatOaByChangeCheckbox : function() {
		var me = this;

		// 从缓存map中取出所有选中的checkbox对应的投入，计算其和：a、遍历checkedbox;b、缓存中取出box对应的投入集合遍历;c、每个投入物料类型计算一个总和
		// tmpDataMap: 计算后的结果，map形式，用于合并长度
		// tmpDataArray: 计算后的结果，数组形式，最后load到matStore中去
		// inDataTmp: 临时放置indata投入对象
		// inFirDataTmp: 第一次从缓存map中根据型号/规格/SEQ/工序取出来的对象
		// processCodeSeq: changedProcessBox的inputValue(processCode-seq)
		// orderItemId: changedProcessBox中存放的此组box所对应的订单ID(其中之一)
		var tmpDataMap = {}, tmpDataArray = [], inDataTmp, inFirDataTmp;
		// 遍历所有工序组列表
		me.getChecksGroup().each(function(i, group){
			me.getCheckeds($(group)).each(function(n, check){
				// inDataCacheMap的key是processCode + seq + productType + productSpec
				inFirDataTmp = me.inDataCacheMap[$(check).val() + $(check).attr('seq') + $(group).attr('productTypeSpec')];
				for (var secKey in inFirDataTmp) {
					inDataTmp = null; // 存放一条
					Ext.Array.each(inFirDataTmp[secKey], function(inData, m) {
						if (n > 0 && inData.matName.indexOf('半成品') >= 0) { // 除了第一个工序，其他工序的半成品不要显示
							return true;
						}
						if (inDataTmp) {
							inDataTmp.unFinishedLength = Math.round(inDataTmp.unFinishedLength
									+ inData.unFinishedLength)
						} else {
							inDataTmp = Ext.clone(inData);
						}
					});
					if (inDataTmp) {
						if (tmpDataMap[secKey]) {
							tmpDataMap[secKey].unFinishedLength = Math.round(tmpDataMap[secKey].unFinishedLength
									+ inDataTmp.unFinishedLength)
						} else {
							tmpDataMap[secKey] = Ext.clone(inDataTmp);
						}
					}
				}
			});
		});	

		// 结果集合由map形式转换成数组形式
		for (key in tmpDataMap) {
			tmpDataArray.push(tmpDataMap[key]);
		}
		// 投入表单load数据
		me.inGrid.datas = tmpDataArray;
		me.doLayoutTable(me.inGrid);
	},

	/**
	 * 在未完成的订单基础上处理同产品型号的合并生产 1、先循环订单列表获得要合并的订单，并封装到一个map对象 2、循环订单列表变更合并的订单的信息
	 * 3、在循环中首先获取合并的对象，然后变更其及子result中的长度，再再其中变更他包含的合并的订单的数据（这样做为了不影响其他不合并的订单的信息）
	 */
	initOrderGroup : function() {
		var me = this, outDataTmp, firInDataMap;

		Ext.Array.each(me.orderData, function(order, i) {
			var unFinishedLength = Number(order.orderLength), splitLengthRoleWithYuliang, wireCoilCount;
			if (order.splitLengthRole && order.splitLengthRole != '') {
				// 此处添加增加手动分段要求，添加余量
				var specSplitRequire = me.analysisSplitRoleExpression(order.splitLengthRole), yuliang = 0, wireCoilCount = 0, splitLengthRoleWithYuliang = ''; // 带余量的规则
				Ext.Array.each(specSplitRequire, function(require, m) {
							var itemyl = 5; // 每段放5米余量
							yuliang += itemyl * Number(require.volNum); // 计算余量
							wireCoilCount += Number(require.volNum); // 计算盘数
							if (m != 0) {
								splitLengthRoleWithYuliang += '+';
							}
							splitLengthRoleWithYuliang += (Number(require.length) + Number(itemyl))
									+ (require.volNum == 1 ? '' : ('*' + require.volNum));
						});
				unFinishedLength += yuliang;
				order.wireCoilCount = wireCoilCount;
			} else {
				unFinishedLength += 5;
			}
			order.splitLengthRoleWithYuliang = splitLengthRoleWithYuliang;
			order.unFinishedLength = unFinishedLength; // 设置合并总量
			order.wireCoil = '无要求';
			
			// 更新投入的对应的合并长度
			for (var firKey in me.inDataCacheMap) {
				firInDataMap = me.inDataCacheMap[firKey];
				for (var secKey in firInDataMap) {
					Ext.Array.each(firInDataMap[secKey], function(inData, n) {
								if (order.id == inData.orderItemId) {
									// 紧跟着火花配套的成缆工序 并且是半成品信息，不加余量
									if (inData.preProcessCode != 'Respool' || inData.matName.indexOf('半成品') < 0) {
										inData.unFinishedLength = unFinishedLength; // 余量暂固定5M
									}
								}
							});
				}
			}

			// 更新产出的的对应的合并长度和产出的颜色
			outDataTmp = me.outDataCacheMap[order.id];
			if (outDataTmp) {
				for (var key in outDataTmp) {
					outDataTmp[key].wireCoil = order.wireCoil;
					outDataTmp[key].color = order.color;
					outDataTmp[key].operator = order.operator;
					outDataTmp[key].conductorStruct = order.conductorStruct;
					outDataTmp[key].splitLengthRole = order.splitLengthRole;
					outDataTmp[key].splitLengthRoleWithYuliang = order.splitLengthRoleWithYuliang;
					outDataTmp[key].wireCoilCount = order.wireCoilCount;
					outDataTmp[key].unFinishedLength = unFinishedLength; // 余量暂固定5M
					if (!order.standardPly || order.standardPly == '' || !order.standardMaxPly || order.standardMaxPly == ''
							|| !order.standardMinPly || order.standardMinPly == '') { // 赋值一次就勾了
						order.standardPly = outDataTmp[key].standardPly;
						order.standardMaxPly = outDataTmp[key].standardMaxPly;
						order.standardMinPly = outDataTmp[key].standardMinPly;
					}
				}
			}
		})
	},

	/**
	 * @description 根据工序改变该工序对应的订单的外径和标称厚度值
	 * @param process 需要改变的对应工序
	 * @param orderItemId 工序对应的订单id的其中之一：用来获取产品型号规格来匹配更新
	 */
	changeOrderDataByCheckbox : function(processCodeSeq, productTypeSpec) {
		var me = this;
		// order.outsideValue = record.outsideValue;
		// 显示页面对应工序的参数值：标称厚度/外径
		Ext.Array.each(me.orderData, function(order, i) {
			Ext.Array.each(me.outDataCacheMap[order.id], function(record, m) {
				if ((record.processCode + record.seq + record.productType + record.productSpec) 
				    == (processCodeSeq + productTypeSpec)) {
					order.outsideValue = record.outsideValue;
					return false;
				}
			});
		});
	},

	/**
	 * @description private 方法中包含的私有方法
	 * @description 根据工序改变该工序对应的所有相关订单的产出的成缆要求信息
	 * @param process 需要改变的对应工序
	 * @param orderItemId 工序对应的订单id的其中之一：用来获取产品型号规格来匹配更新
	 */
	changeOutMatDescByCheckbox : function(showDesc, productTypeSpec) {
		var me = this;
		for (var firKey in me.outDataCacheMap) {
			Ext.Array.each(me.outDataCacheMap[firKey], function(outData, j) {
				if (productTypeSpec == (outData.productType + outData.productSpec)) {
					outData.outMatDesc = showDesc;
				}
			});
		}
	},

	/**
	 * @description private
	 * @description 方法中包含的私有方法
	 * @description 根据工序改变该工序对应的所有相关订单的成缆要求信息
	 * @param process 需要改变的对应工序
	 * @param orderItemId 工序对应的订单id的其中之一：用来获取产品型号规格来匹配更新
	 */
	changeOrderMatDescByCheckbox : function(showDesc, productTypeSpec) {
		var me = this;
		Ext.Array.each(me.orderData, function(order, i) {
			if (productTypeSpec == (order.productType + order.productSpec)) {
				order.outMatDesc = showDesc;
			}
		});
	},

	/**
	 * 改变工序选择所产生的事件
	 */
	changeProcessCheckbox : function(processChecked) {
		var me = this;
		var inDataMap = {}, inInsertData = [], inDeleteData = [];
		// 1、改变button的显示影藏状态
		me.changeButtonStatus(true, false, false);
		// 2、计算每个工序的使用次数
		var text = [], usedCount = 1, pNAME = '', preCode = '', tmpStr = '', 
		    productTypeSpec = $(processChecked).closest('fieldset').attr('productTypeSpec');
		Ext.Array.each(processChecked, function(process, n) {
			var processName = $(process).attr('text');
			var processCode = $(process).val();
			var processCodeSeq = processCode + $(process).attr('seq');
			if (n == 0) {
				$('b#processName', '#' + me.id).text(processName);
				$('#processCode', '#' + me.id).val(processCode);
			}
			var mat = []; // 每道工序所要投的物料
			pNAME = processName.substring(0, 2);
			if (preCode == processCodeSeq) { // 如果上一次编码跟此次的是一样的说明多次包
				usedCount++;
			}

			// 从投入缓存map中获取投入物料信息
			// matInfo: 物料信息/名称（描述）
			// matDesc: 物料描述
			// processInData: 缓存中获取的工序投入物料列表{描述key:[投入物料list],
			// 描述key:[投入物料list]}
			var processInData = me.inDataCacheMap[processCodeSeq + productTypeSpec];
			for (var secKey in processInData) {
				Ext.Array.each(processInData[secKey], function(inData, m) {
					if (inData.matName.indexOf('半成品') < 0) { // 不显示半成品投入物料
						var matDesc = '';
						matDesc += (inData.kuandu ? ('宽度:' + inData.kuandu + ';') : ''); // 宽度
						matDesc += (inData.houdu ? ('厚度:' + inData.houdu + ';') : ''); // 厚度
						matDesc += (inData.caizhi ? ('材质:' + inData.caizhi + ';') : ''); // 材质
						matDesc += (inData.chicun ? ('尺寸:' + inData.chicun + ';') : ''); // 尺寸
						matDesc += (inData.guige ? ('规格:' + inData.guige + ';') : ''); // 规格
						matDesc += (inData.dansizhijing ? ('单丝直径:' + inData.dansizhijing + ';') : ''); // 单丝直径
						var matInfo = inData.matName + (matDesc != '' ? ('(' + matDesc + ')') : '');
						mat.push(matInfo);
					}
					return false; // 只要一次就可以了，因为同一类型的投入物料只要取一个，map中包含了多个类型
				});
			}

			tmpStr = '使用[{0}]{1}{2}层';
			if (preCode == processCodeSeq) {
				var tmp = text[text.length - 1];
				if (mat.length == 0) {
					tmpStr = '{0}{1}层';
				}
				if (tmp.indexOf('，') >= 0) {
					tmpStr = tmpStr + tmp.substring(tmp.indexOf('，'), tmp.length);
				}
				
				tmpStr = me.addExpandProperty(tmpStr, $(process));
				
				text.pop(); // 去掉数组最后的一个text
				if (mat.length == 0) {
					text.push(tmpStr.format(pNAME, usedCount)); // 添加新的值
				} else {
					text.push(tmpStr.format(mat, pNAME, usedCount)); // 添加新的值
				}

			} else {
				usedCount = 1; // 重置
				// 说明是一道独立的，可以产生一句话
				var marStr = mat + '';
				if (marStr.indexOf('填充') < 0 && pNAME.indexOf('编织') < 0) { // 包含填充的工序无层概念
					if (mat.length == 0) {
						tmpStr = '{0}';
					}
					
					tmpStr = me.addExpandProperty(tmpStr, $(process));
					
					if (mat.length == 0) {
						text.push(tmpStr.format(pNAME));
					} else {
						text.push(tmpStr.format(mat, pNAME, usedCount));
					}
				} else {
					tmpStr = '使用[{0}]{1}';
					if (mat.length == 0) {
						tmpStr = '{0}';
					}
					
					tmpStr = me.addExpandProperty(tmpStr, $(process));

					if (mat.length == 0) {
						text.push(tmpStr.format(pNAME));
					} else {
						text.push(tmpStr.format(mat, pNAME));
					}
				}
			}
			preCode = processCodeSeq;

			if (n == processChecked.length - 1) { // 最后一个的时候处理订单信息上面的外径数据
				me.changeOrderDataByCheckbox(processCodeSeq, productTypeSpec);
			}

		});
		var showDesc = me.outMatDescShowText(text);
		// 根据工序改变该工序对应的所有相关订单的产出的成缆要求信息
		me.changeOutMatDescByCheckbox(showDesc, productTypeSpec); // 保存生产单的时候prodec表中才能保存该信息
		// 根据工序改变该工序对应的所有相关订单的成缆要求信息
		me.changeOrderMatDescByCheckbox(showDesc, productTypeSpec); // 界面显示
		me.changeMatOaByChangeCheckbox();

		me.getEquip(); // 显示可用设备
		me.orderGrid.datas = me.orderData;
		me.doLayoutTable(me.orderGrid);
	},
	
	addExpandProperty : function(tmpStr, process){
	    var me = this;
	    if (process.attr('cableOrderValue') != '') {
			tmpStr = tmpStr + '，' + process.attr('cableOrderName') + '：' + process.attr('cableOrderValue');
		}
		if (process.attr('cableFaceValue') != '') {
			tmpStr = tmpStr + '，' + process.attr('cableFaceName') + '：' + process.attr('cableFaceValue');
		}
		if (process.attr('wringDistValue') != '') {
			tmpStr = tmpStr + '，' + process.attr('wringDistName') + '：' + process.attr('wringDistValue');
		}
		if (process.attr('coverRateValue') != '') {
			tmpStr = tmpStr + '，' + process.attr('coverRateName') + '：' + process.attr('coverRateValue');
		}
		if (process.attr('braidDensityValue') != '') {
			tmpStr = tmpStr + '，' + process.attr('braidDensityName') + '：' + process.attr('braidDensityValue');
		}
		return tmpStr;
	},

	// 显示成缆要求文字描述
	outMatDescShowText : function(text) {
		var showDesc = '';
		for (var i in text) {
			showDesc += Math.round(Number(i) + 1) + '、' + text[i] + '<br/>'
		}
		return showDesc;
	},
	/**
	 * @description 保存生产单
	 */
	saveWorkOrder : function(but) {
		var me = this;

		var processesMergedArray = []; // 提交工艺工序使用情况
		var subResult = me.generateSubResult(processesMergedArray); // 生成提交数据，当前所选工艺包括的投入产出
		var requireFinishDate = Ext.ComponentQuery.query('#requireFinishDate')[0];
		var equipCodes = me.equipCombobox;
		if ($('#docMakerUserCode', '#' + me.id).val() == '' || $('#receiverUserCode', '#' + me.id).val() == '' 
		    || !requireFinishDate.validate() || !equipCodes.validate()) {
			Ext.Msg.alert(Oit.msg.PROMPT, '请填写完表单内容！');
			return;
		}
		if (subResult.length == 0) {
			Ext.Msg.alert(Oit.msg.PROMPT, '该工序任务已经全部下发完成！');
			return;
		}
		
//		var a = [];
//		var b = me.generateSubResult(a);
//		for (var i in b) {
//				console.log(b[i].inOrOut + '--' + b[i].matName + '--' + b[i].unFinishedLength + '--' + b[i].processId + '--'
//						+ b[i].wireCoil + '--' + b[i].outsideValue + '--' + b[i].outMatDesc + '--' + b[i].splitLengthRole + '--'
//						+ b[i].splitLengthRoleWithYuliang)
//		}
//		return;
		

		/**
		 * @param docMakerUserCode 制单人
		 * @param receiverUserCode 接受人
		 * @param requireFinishDate 完成日期
		 * @param equipCodes 设备编码数组
		 * @param equipName 设备名称
		 * @param processName 工序名称
		 * @param processCode 工序编码
		 * @param userComment 备注
		 * @param processJsonData
		 *            工序Json对象，投入产出：转换对象CustomerOrderItemProDec、MaterialRequirementPlan保存
		 * @param orderTaskLengthJsonData 该订单产品该生产单所下发的长度
		 * @param preWorkOrderNo 上一道生产单的生产单号
		 * @param processesMergedArray 成缆工段生产单保存当前生产单所用工序的Json字符串
		 * @param workOrderSection 生产单所属工段 1:绝缘工段 2:成缆工段 3:护套工段
		 * @param nextSection 生产单下一个加工工段 1:绝缘工段 2:成缆工段 3:护套工段
		 * @param cusOrderItemIds 当前生产单中下发的所有客户生产订单明细IDs
		 */
		Ext.Msg.wait('数据处理中，请稍后...', '提示');
		Ext.Ajax.request({
			url : 'handSchedule/mergeCustomerOrderItem',
			params : {
				docMakerUserCode : $('#docMakerUserCode', '#' + me.id).val(),
				receiverUserCode : $('#receiverUserCode', '#' + me.id).val(),
				requireFinishDate : requireFinishDate.rawValue + ' 00:00:00',
				equipCodes : equipCodes.getValue(),
				equipName : equipCodes.getDisplayValue(),
				processName : $('b#processName', '#' + me.id).text(),
				processCode : $('#processCode', '#' + me.id).val(),
				userComment : $('#userComment', '#' + me.id).val(),
				processJsonData : Ext.encode(subResult),
				preWorkOrderNo : me.preWorkOrderNo,
				processesMergedArray : (me.jumpProcessesMergedArray.length > 0 ? (Ext.encode(me.jumpProcessesMergedArray)+',') : '') + Ext.encode(processesMergedArray),
				orderTaskLengthJsonData : Ext.encode(me.getOrderTaskLengthJsonData()),
				workOrderSection : me.getSection(),
				nextSection : me.getNextSection(),
				completeCusOrderItemIds : me.getCompleteCusOrderItemIds(),
				cusOrderItemIds : me.getCusOrderItemIds(),
				isDispatch : $('#isDispatch', '#' + me.id).prop('checked'),
				isAbroad : $('#isAbroad', '#' + me.id).prop('checked')
			},
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.PROMPT, '保存成功！');

				var result = Ext.decode(response.responseText); // 返回结果
				me.jumpProcessesMergedArray = []; // 清理跳过对象
				me.afterSaveWorkOrder(but, result); // 保存提交后需要进行的操作
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.PROMPT, '保存失败！');
			}
		});
	},

	// 获取当前工段
	getSection : function(){
		var me = this;
		var tmp = {}, section = []; // tmp:map去重
		var checksGroup = me.getChecksGroup(); // 获取所有工序组列表
		checksGroup.each(function(n, group){
			var lastChecked = $(group).find('input[type="checkbox"][name!="checkAll"]:checked:last'); // 获取最后一个
			var s = lastChecked.attr('section');
			if(!tmp[s]){
				tmp[s] = true;
				section.push(s);
			}
		});
		return section.join();
	},
	
	// 获取下一个工段:  @description 获取生产单提交参数nextSection: 生产单下一个加工工段 1:绝缘工段 2:成缆工段 3:护套工段
	getNextSection : function() {
		var me = this;
		
		var tmp = {}, nextSection = [];
		var checksGroup = me.getChecksGroup(); // 获取所有工序组列表
		checksGroup.each(function(n, group){
			var productTypeSpec = $(group).attr('productTypeSpec');
			var lastChecked = $(group).find('input[type="checkbox"][name!="checkAll"]:checked:last'); // 获取最后一个
			var ns = lastChecked.attr('nextSection');
			if(!tmp[ns]){
				tmp[ns] = true;
				nextSection.push(ns);
			}
		});
		return nextSection.join();
	},
	
	// 保存时获取已经完成当前工段的订单ID
	getCompleteCusOrderItemIds : function(){
		var me = this;
		var completeCusOrderItemIds = [];
		var checksGroup = me.getChecksGroup(); // 获取所有工序组列表
		checksGroup.each(function(n, group){
			var productTypeSpec = $(group).attr('productTypeSpec');
			var lastCheck = $(group).find('input[type="checkbox"][name!="checkAll"]:last'); // 获取最后一个
		    if (lastCheck.prop('checked')){ // 最后一个选中的话
				Ext.Array.each(me.orderData, function(order, i) {
					if ((order.productType + order.productSpec) == productTypeSpec) {
						completeCusOrderItemIds.push(order.id);
						me.completeCusOrderItemIds.push(order.id);
					}
				});
		    }
		});
		return completeCusOrderItemIds.join();
	},

	/**
	 * @description private
	 * @description 方法中包含的私有方法
	 * @description 获取生产单提交参数cusOrderItemIds: 当前生产单中下发的所有客户生产订单明细IDs
	 */
	getCusOrderItemIds : function() {
		var me = this, idArray = [];
		Ext.Array.each(me.orderData, function(order, n) {
					idArray.push(order.id);
				});
		return idArray.join(',');
	},

	/**
	 * @description private
	 * @description 方法中包含的私有方法
	 * @description 获取生产单提交参数orderTaskLengthJsonData: 该订单产品该生产单所下发的长度
	 */
	getOrderTaskLengthJsonData : function() {
		var me = this, orderTaskLengthJsonData = [];
		Ext.Array.each(me.orderData, function(order, n) {
			orderTaskLengthJsonData.push({
				cusOrderItemId : order.id,
				orderTaskLength : order.orderLength,
				splitLengthRole : order.splitLengthRole
			});
		});
		return orderTaskLengthJsonData;
	},

	/**
	 * 保存提交后需要进行的操作 1、保存按钮隐藏，下发按钮显示； 2、生产单页面赋值，供下发功能使用和下一级生产单生成使用；
	 * 3、改变当前所选的工序状态，已经选择了的移除； 4、默认设置每一组group的第一项为选中； 5、刷新父页面； 刷新主页面列表store
	 * 
	 * @param button 请求button对象
	 * @param result 请求返回结果
	 */
	afterSaveWorkOrder : function(button, result) {
		var me = this;
		me.changeButtonStatus(false, true, false); // 1、 改变button的显示影藏状态

		// 2、生产单页面赋值，供下发功能使用和下一级生产单生成使用；
		me.preWorkOrderNo = result.workOrderNo; // 将页面的上一次生产单号放入页面，以便下次保存使用

		// 3、保存后改变父窗口选择客户订单列表，已经下发的去掉，勾选没有下发的
		me.changeParentChooseWindow();

		// 4、刷新父页面
		Ext.ComponentQuery.query('handSchedule2Grid')[0].getStore().load();
	},

	/**
	 * 保存后改变父窗口选择客户订单列表 已经下发的去掉，勾选没有下发的
	 */
	changeParentChooseWindow : function() {
		var me = this;
		var chooseOrderWin = Ext.ComponentQuery.query('chooseCustomerOrderDecWindow')[0];
		if (chooseOrderWin) {
			var chooseOrderGrid = chooseOrderWin.down('grid');
			var chooseOrderStore = chooseOrderGrid.getStore();
			var selection = chooseOrderGrid.getSelectionModel().getSelection();

			var newData = [];
			for (var i = 0; i < chooseOrderStore.getCount(); i++) {
				var record = chooseOrderStore.getAt(i);
				var select = false;
				Ext.Array.each(selection, function(srecord, i) {
							if (record.get('id') == srecord.get('id')) {
								select = true;
								return false;
							}
						});
				if (!select) {
					newData.push(record.data);
				}
			}

			if (newData.length == 0) {
				chooseOrderWin.close();
			} else {
				chooseOrderStore.loadData(newData, false);
				chooseOrderGrid.getSelectionModel().selectAll();
			}
		}
	},

	/**
	 * 下发生产单
	 */
	auditWorkOrder : function() {
		var me = this;
		Ext.Msg.wait('数据处理中，请稍后...', '提示');
		Ext.Ajax.request({
			url : 'handSchedule/updateWorkerOrderStatus',
			params : {
				workOrderNo : me.preWorkOrderNo,
				status : 'TO_DO'
			},
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				me.changeButtonStatus(false, false, true); // 1、改变button的显示影藏状态

				// 2、改变当前所选的工序状态，已经选择了的移除
				me.getChecksGroup().each(function(n, group){
					var productTypeSpec = $(group).attr('productTypeSpec');
					if(me.getCheckes($(group)).length > 0){
						me.getCheckeds($(group)).each(function(m, check){
						    $(check).next('label:first').remove();
						});
						me.getCheckeds($(group)).remove(); // 下发完了的就去掉
					}
					if(me.getCheckes($(group)).length == 0){
					    Ext.Array.each(me.orderData, function(order, i) {
							if ((order.productType + order.productSpec) == productTypeSpec) {
								me.completeCusOrderItemIds.push(order.id);
							}
						});
						$(this).hide();
					}
				});

				// 3处理订单产品对象，对保存后已经完成此道工段下发的订单产品过滤显示
				me.changeOrderData();

				// 4、剩下的check全部选中
				me.initGroupAllChecked();
				Ext.Msg.alert(Oit.msg.PROMPT, '下发成功！');

				// 5、刷新父页面：再刷一次，因为页面显示已下发，前面刷新还未下发，所以前面刷新看不到
				Ext.ComponentQuery.query('handSchedule2Grid')[0].getStore().load();
				if (!me.hasProcessCheck()) {
					me.close();
				}
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.PROMPT, '下发失败！');
			}
		});
	},

	/**
	 * 生成提交数据，当前所选工艺包括的投入产出
	 */
	generateSubResult : function(processesMergedArray) {
		var me = this;
		var subResult = []
		
		// 获取所有工序组列表
		me.getChecksGroup().each(function(n){
			var checkeds = me.getCheckeds($(this));
			var productTypeSpec = $(this).attr('productTypeSpec');
			
			var processArray = [];
			checkeds.each(function(m, checked){
				var $checked = $(checked);
				
				// 投入表
				var processInDataMap = me.inDataCacheMap[$checked.val() + $checked.attr('seq') + productTypeSpec];
				for (var secKey in processInDataMap) {
					Ext.Array.each(processInDataMap[secKey], function(inData, j) {
						if (m > 0 && inData.matName.indexOf('半成品') >= 0) { // 过滤一下多余的半成品物料：投入取第一个的半成品物料
							return true;
						}
						subResult.push(inData);
					});
				}
				// 产出表，取最后一道工序的产出就可以了
				if (m == (checkeds.length - 1)) {
					for (var firKey in me.outDataCacheMap) {
						Ext.Array.each(me.outDataCacheMap[firKey], function(outData, j) {
							if ((outData.processCode + outData.seq + outData.productType + outData.productSpec)
									== ($checked.val() + $checked.attr('seq') + productTypeSpec)) {
								subResult.push(outData);
							}
						});
					}
				}
				processArray.push({
							name : $checked.attr('text'),
							code : $checked.val() + $checked.attr('seq')
						});
			});
			
			if(processArray.length > 0){
				var craftsProcesses = {};
				craftsProcesses[productTypeSpec] = processArray;
				processesMergedArray.push(craftsProcesses);
			}
		});
				
		return subResult;
	},

	/**
	 * 排生产单: 显示工序使用可选设备
	 * 
	 * @param win 当前弹出框
	 * @param processCode 工序编码
	 */
	getEquip : function() {
		var me = this;
		var e = new Date();
		
		var processCodeArray = me.getCheckedProcessCode(); // 获取选中的工序的工序编码
		if (processCodeArray.length == 0) {
			return;
		}
		if (me.specialOutsideValue()) {
			processCodeArray.push('Cabling');
		}

		var equipData = [], defaultEquip;
		Ext.Array.each(me.sectionEquipArray, function(equip, i) {
			if (arrayContains(processCodeArray, equip.processCode)) {
				equipData.push(equip);
				if(!defaultEquip)
		   		    defaultEquip = equip.code;
			}
		});
				
		// 2、使用extjs的combobox控件渲染到要选择机台
		if($('#equipCodesSpan', '#' + me.id).children().length > 0){
			var equipCodes = me.equipCombobox;
			var store = equipCodes.getStore();
			store.loadData(equipData);
			equipCodes.setValue(defaultEquip);
		}else{
			me.equipCombobox = new Ext.form.field.ComboBox({  
		        renderTo : 'equipCodesSpan',//节点的id  
		        itemId : 'equipCodes',
				store : Ext.create('Ext.data.Store', {
					fields : ['code', 'name'],
					data : equipData
				}),
				multiSelect : true,
				displayField : 'name',
				valueField : 'code',
				width : 450,
			    allowBlank : false,
			    value : defaultEquip
		    }); 
		}
		
		var e1 = new Date();
		console.log('  getEquip--使用extjs的combobox控件渲染到要选择机台用时:' + Math.round(e1 - e))

	},
	
	// 获取工序选择checkbox组
	getChecksGroup : function(){
	    var me = this;
	    return $('div#chooseProcess fieldset:not(:hidden)', '#' + me.id); // 获取所有工序组列表
	},
	
	// 获取工序选择checkbox组下面选中的
	getCheckes : function(checksGroup){
	    var me = this;
	    return checksGroup.find('input[type="checkbox"][name!="checkAll"]');
	},
	
	// 获取工序选择checkbox组下面选中的
	getCheckeds : function(checksGroup){
	    var me = this;
	    return checksGroup.find('input[type="checkbox"][name!="checkAll"]:checked');
	},
	
	// 判断工序是否还有选择按钮
	hasProcessCheck : function(){
	    return $('div#chooseProcess input[type="checkbox"][name!="checkAll"]').length > 0;
	},
	
	// 获取工序选择checkbox选中的工序编码
	getCheckedProcessCode : function(checksGroup){
	    var me = this;
	    var processCode = [];
	    me.getChecksGroup().each(function(n, group){
	    	me.getCheckeds($(group)).each(function(m, check){
	    		var code = $(check).val();
				if (!arrayContains(processCode, code)) {
					processCode.push(code);
				}
	    	});
	    });
	    return processCode;
	},
	
	// 获取当前的合同号 ： 查看附件的/将合同号去重传值
	getContractNo : function(){
		var me = this;
		var conNoMap = {}, contractNo = [];
		Ext.Array.each(me.orderData, function(order, n) {
			if (!conNoMap[order.contractNo]) {
				contractNo.push(order.contractNo)
				conNoMap[order.contractNo] = order.contractNo;
			}
		});
		return contractNo;
	},
	
	// 查看附件
	lookUpAttachFileWindow : function(){
		var me = this;
		lookUpAttachFileWindow('', '', me.getContractNo().join());
	},
	
	/**
	 * 跳过该工序
	 */
	jumpProcess : function(btn) {
		var me = this;
		Ext.MessageBox.confirm('确认', '确认跳过选择的工序？', function(btn) {
			if (btn == 'yes') {
				// 1、定义：{checkAll: 判断是否全部选择, completeType: 完成的型号规格}
				var checkAll = true; // 判断是否全部选择
				var completeType = []; // 完成的型号规格
				var section = me.getSection(); // 当前工段
				var nextSection = me.getNextSection(); // 下一个工段
				var completeCusOrderItemIds = me.getCompleteCusOrderItemIds(); // 已经完成成缆工段的订单id
				
				// 2、遍历工序选择checkbox组
				me.getChecksGroup().each(function(n, group){
					var productTypeSpec = $(group).attr('productTypeSpec');
					var processArray = [];
					// 3、选中的工序放入工序组
					me.getCheckeds($(group)).each(function(m, check){
					    processArray.push({
							name : $(check).attr('text'),
							code : $(check).val() + $(check).attr('seq')
						});
					});
					// 4、工序组放入对象，供保存用
					if(processArray.length > 0){
						var craftsProcesses = {};
						craftsProcesses[productTypeSpec] = processArray;
						me.jumpProcessesMergedArray.push(craftsProcesses);
					}
				});
				
				// 5、删除选中的工序
				me.getChecksGroup().each(function(n){
					me.getCheckeds($(this)).each(function(m, check){
						$(check).next('label:first').remove();
						$(check).remove();
					});
				});

				// 6、最后的处理：如果都没有全部跳过，选中剩下的
				me.changeOrderData(); // 已经全部完成成缆工段的订单去掉
				me.initGroupAllChecked(); // 剩下的全部选中
				me.changeParentChooseWindow(); // 变更父页面选择的订单
				// 7、如果需要修改上道生产单信息，请求后台
				if(section != nextSection){
					Ext.Ajax.request({
						url : 'handSchedule/updateNextSection?workOrderNo=' + me.preWorkOrderNo
								+ '&nextSection=' + nextSection + '&completeCusOrderItemIds=' + completeCusOrderItemIds,
						success : function(response) {
							// 刷新父页面
							if(Ext.ComponentQuery.query('handScheduleGrid2')[0]){
								Ext.ComponentQuery.query('handScheduleGrid2')[0].getStore().load();
							}
							if(nextSection.indexOf(',') == -1){ // 模糊：断定下一个工段section是3，不是2,3
								me.close();
							}
						}
					});									
				}
			}
		});
	},
	
	// 查看附件
	editRecord : function(record){
		var me = this;
		var outMatDesc = record.outMatDesc ? record.outMatDesc.replace(/<br\/>/g, "\r\n") : ''; 
		var win = Ext.create('Ext.window.Window', {
		    title: '编辑',
		    width : document.body.scrollWidth / 2,
			height : document.body.scrollHeight / 2,
			modal : true,
			plain : true,
			autoScroll : true,
		    layout : 'anchor',
		    items: [{
				xtype : 'form',
				bodyPadding : 10,
				defaults : { anchor : '100%' },
				defaultType : 'textfield',
				items : [{ fieldLabel : '颜色字码', name : 'color', value : record.color
				}, { fieldLabel : '成缆长度', name : 'unFinishedLength', value : record.unFinishedLength
				}, { hidden: true, name : 'oldLength', value : record.unFinishedLength
				}, { fieldLabel : '收盘要求', xtype : 'combobox',
					store : Ext.create('Ext.data.Store', {
						fields : ['name'],
						data : [{ "name" : "外盘" }, { "name" : "盘中" }, { "name" : "盘底" }, { "name" : "无要求" }]
					}),
					queryMode : 'local', name : 'wireCoil', displayField : 'name', valueField : 'name', value : record.wireCoil
				},{ xtype : 'textarea', fieldLabel : '成缆/绕包要求', name : 'outMatDesc', value : outMatDesc,
					style : 'overflow-y:visible;'
				}]
			}],
			buttons : ['->', { text : '确 定', handler : function(){ 
				var form = win.down('form').getForm();
				me.updateOrderTask(record, form, win); 
			}
			},{ text : '关 闭', handler : function(){ win.close(); }
			}]
		}).show();
	},
	
	
	// 获取一个FieldSet的html
	getFieldSet : function(attrs, title, content, hidden) {
		var me = this;
		var html = '<fieldset ';
		if(attrs && attrs != ''){
		    for(var a in attrs){
		    	html += a + '="' + attrs[a] + '"';
		    }
		}
		html += ' style="display: ' + (hidden ? 'none' : 'block') + ';" class="mesFieldSet">';
		if (title != '') {
			html += '<legend>' + title + '</legend>';
		}
		html += content + '</fieldset>';
		return html;
	},

	// 重新渲染制定的table
	doLayoutTable : function(grid) {
		var me = this;
		var html = '';
		// 1、如果有工具栏，加载工具栏
		if(grid.toolBar && grid.toolBar != ''){
			html += grid.toolBar;
		}
		// 2、加载table的表头
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

		// 3、遍历data数据，生成table表主体
		Ext.Array.each(grid.datas, function(record, n) {
			// 3.1、每行数据需要唯一id，没有就补充
			record.id = ((record.id && record.id != '') ? record.id : uuid());
			html += '<tr id="' + record.id + '">';
			// 3.2、添加编辑事件
			if(grid.edit){
				$(document, '#' + me.id).off("dblclick", '#' + record.id);
				$(document, '#' + me.id).on("dblclick", '#' + record.id, function(){
					grid.edit(me, record);
				}); 
			}
			// 3.2、遍历列：获取每个单元格元素值
			Ext.Array.each(grid.columns, function(column, m) {
				// 3.3、添加style和class
				var style = '', cls = '';
				// 3.3.1、最大宽度maxWidth
				if(column.maxWidth){
					style += 'max-width:' + column.maxWidth + 'px;';
				}
				// 3.3.2、最小宽度minWidth
				if(column.minWidth){
					style += 'min-width:' + column.minWidth + 'px;';
				}
				// 3.3.3、添加编辑样式
				cls += column.edit ? 'edit ' : '';
				html += '<td style="' + style + '" class="' + cls +'">';
				// 3.4、添加单元格元素:checkbox
				if(column.xtype && column.xtype == 'checkbox'){
					html += '<input type="checkbox" id="' + record.id + '"></td>';
				}else{
					// 3.4.1、正常值
					var cell = (record[column.dataIndex] ? record[column.dataIndex] : '');
					// 3.4.2、返回renderer事件
					if(column.renderer){
						cell = column.renderer(cell, record);
					}
					html += cell + '</td>';
				}
			});
			html += '</tr>';
		});
		
		html += '</tbody>';
		html += '</table>';
		$('#' + grid.id + ' table', '#' + me.id).remove();
		$('#' + grid.id + ' div.toolBar', '#' + me.id).remove();
		$('#' + grid.id, '#' + me.id).append(html);
	},

	/**
	 * 解析表达式，返回一个数组解析式
	 * 
	 * @description 前：1500*1+1000*2+500*3
	 * @description 后：[{length: 1500, volNum: 1}, {length: 1000, volNum: 2},
	 *              {length: 500, volNum: 3}]
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
	//a*b+c  a*b  a+c
	t : function(k){
		var a=0,b=0,c=0,d=0;
		if(k.indexOf('*')>0){
			var str = k.split('*');
			a = Number(str[0]);
			if(str[1].indexOf('+')>0){
				var str1 = str[1].split('+');
				b = Number(str1[0]);
				c = Number(str1[1]);
				d = a*b+c;
				return d;
			}else{
				b = Number(str[1]);
				c = a*b;
				return c;
			}
		}else if(k.indexOf('+')>0){
			var str = k.split('+');
			a = Number(str[0]);
			b = Number(str[1]);
			c = a+b;
			return c;
		}else{
			return k;
		}
	},
	
	
	// 编辑更新任务
	updateOrderTask : function(record, form, win){
		var me = this;
		var outMatDesc = form.findField('outMatDesc').getValue();
		var newLength = me.t(form.findField('unFinishedLength').getValue());
		var oldLength = form.findField('oldLength').getValue();
		outMatDesc = outMatDesc.replace(/\n/g, "<br/>"); 
		// 1、修改订单信息
		record.wireCoil = form.findField('wireCoil').getValue();
		record.color = form.findField('color').getValue();
		record.unFinishedLength = newLength;
		record.outMatDesc = outMatDesc;
		// 2、修改产出信息
		Ext.Array.each(me.outDataCacheMap[record.id], function(outData, j) {
			outData.wireCoil = record.wireCoil;
			outData.outMatDesc = record.outMatDesc;
			outData.color = record.color;
			outData.unFinishedLength = record.unFinishedLength;
		});
		// 3、重新update订单列表
		me.doLayoutTable(me.orderGrid);
		// 4、如果长度变了
		if(newLength != oldLength){
			// 4.1、更新投入长度
			for(var firKey in me.inDataCacheMap){
				var inFirDataTmp = me.inDataCacheMap[firKey];
				for (var secKey in inFirDataTmp) {
					Ext.Array.each(inFirDataTmp[secKey], function(inData, m) {
						if (record.id == inData.orderItemId) {
							inData.unFinishedLength = newLength;
						}
					});
				}
			}
			// 4.2、更新投入物料显示长度
			Ext.Array.each(me.inGrid.datas, function(inData, m) {
					if (record.id == inData.orderItemId) {
						inData.unFinishedLength = newLength;
					}
			});
			// 6、重新update物料需求
			me.doLayoutTable(me.inGrid);
		}
		win.close();
	},

	/**
	 * 特殊要求，外径大于的工序，设备添加成缆的设备
	 */
	specialOutsideValue : function() {
		var me = this;
		var specialOutsideValue = false;
		Ext.Array.each(me.orderData, function(order, n) {
					if (Number(order.outsideValue) > 10) {
						specialOutsideValue = true;
						return false;
					}
				});
		// Ext.Array.each(me.sectionEquipArray, function(sectionEquip, n) {
		// console.log(sectionEquip.processCode +'---'+sectionEquip.code +'---'+
		// sectionEquip.name)
		// });

		return specialOutsideValue;
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
