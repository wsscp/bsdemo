// 护套工段下发生成生产单
// 页面包含：订单信息、半制品产出信息、物料需求信息
// 按钮功能：
// －－1、双击订单信息列表可以进行编辑功能：修改材料／颜色／厚度／外径／收线盘具／印字要求等信息
// －－2、分盘要求：1、可以作为分段下单功能（总长200000，可以填写10000先下，剩下的以后再下），2、同时可以设置分盘的要求（总长5000，要求每1000一盘，则可填写1000*5）；
// 注：余量方法：不合并生产，默认每盘余量5米
//
// 页面逻辑：
// 1、获取页面需要的数据：护套工段包含的工序、此些工序所需要的投入产出信息、设备信息、接受人信息
// 2、页面渲染顺序
// －－a、渲染工序选择checkbox；
// －－b、封装订单／产出／投入数据；
// －－c、勾选所有的checkbox；
// －－d、获取对应工序的两个个grid的store数据和设备要求数据
Ext.define('bsmes.view.ProcessWorkOrderHTWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.processWorkOrderHTWindow',
	width : document.body.scrollWidth,
	height : document.body.scrollHeight,
	title : '<a style="font-size: 16px;">宝 胜 特 种 电 缆 有 限 公 司 护 套 工 序 生 产 单</a>',
	titleAlign : 'center',
	modal : true,
	plain : true,
	autoScroll : true,
	allDatas : null, // 页面加载所需要数据，open之前已经全部查询好了
	orderData : null, // 主表单数据
	orderIdProductTypeMap : null, // 订单id和产品类型的映射
	preWorkOrderNo : null, // 上一道工序生产单号 [成缆专用]
	inDataCacheMap : {}, // 投入物料数据map，用于提高查询效率
	outDataCacheMap : {}, // 产出半成品数据map，用于提高查询效率
	completeCusOrderItemIds : [], // 已经完成了的产品型号规格
	equipQcCacheMap : {},
	sectionEquipArray : [], // 工段的所有设备
	materialsInventory : {}, // 原材料库存信息
	workFlowInfoMap : {}, // 生产单流程记录
	equipCombobox : null, // 设备对象
	
	executeChangeEvent : true,
	
	orderGrid:{
		id : 'orderTable',
	    datas : [],
	    edit : function(grid, record){
			grid.editRecord(record);
		},
	    columns : [{xtype : 'checkbox'},
	    	{dataIndex : 'contractNo',text : '合同号', renderer : function(value, record){
			    var reg = /[a-zA-Z]/g;
				return (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length - 5) : value.replace(reg, "")) + '[' + record.operator + ']';
			}}, 
			{dataIndex : 'custProductType',text : '客户型号规格', renderer : function(value, record){
			    return value + ' ' + record.custProductSpec;
			}}, 
			{dataIndex : 'unFinishedLength',text : '投产长度', renderer : function(value, record){
			    if (record.splitLengthRoleWithYuliang && record.splitLengthRoleWithYuliang != '') {
					value += '<br/>' + record.splitLengthRoleWithYuliang;
				}
				return value;
			}}, 
			{dataIndex : 'orderLength', text : '交货长度', renderer : function(value, record){
			    if (record.splitLengthRole && record.splitLengthRole != '') {
					value += '<br/>' + record.splitLengthRole;
				}
				return value;
			}}, 
			{dataIndex : 'voltage',text : '电压'},
			{dataIndex : 'material',text : '材料',edit : true},
			{dataIndex : 'color',text : '颜色',edit : true,minWidth:50},
			{dataIndex : 'moldCoreSleeve',text : '模芯模套'},
			{dataIndex : 'standardPly',text : '标称厚度'},
			{dataIndex : 'guidePly',text : '指导厚度'},
			{dataIndex : 'minPly',text : '最薄点'},
			{dataIndex : 'outsideValue',text : '标准外径'},
			{dataIndex : 'outsideMaxValue',text : '最大外径'},
			{dataIndex : 'wireCoil',text : '收线盘具',edit : true},
			{dataIndex : 'outMatDesc',text : '印字要求(双击可修改)', maxWidth : (document.body.scrollWidth/3), edit : true
		}],
		toolBar : '<div class="toolBar" style="padding: 0px 0px 4px 0px;">' +
			'<input type="text" id="splitLengthRole" value="" style="margin-left: 5px;" placeholder="分盘要求,例:500*2+50">' +
			'<button class="btn-primary hasEvent" id="setSplitLengthRole" style="margin-left: 5px;">确定</button>' +
		'</div>'
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
		var noticeHtml = '<b>1、请严格对照相应工艺文件生产，平均值不小于标称值；</b><br/>' +
				'<b>2、认真核对盘卡及任务单，注意印字正确；</b><br/>' +
				'<b>3、按生产工艺、材料定额组织生产，保证产品质量；</b><br/>' +
				'<b>4、严格按照顺序生产，确保每根长度，不得私自分头；</b><br/>' +
				'<b>5、认真填写相关记录，按时间完成生产工作；</b><br/>' +
				'<b>6、请按生产进度要求组织生产，保证电缆按时进站。</b>';
		
		// 4、通过方法获取主要的几块区域的html
		var orderFieldSet = me.getFieldSet({id : 'orderTable'}, '订单信息', '', false);
		var inFieldSet = me.getFieldSet({id : 'inTable'}, '物料需求信息', '', false);
		var userComment = me.getFieldSet(null, '备注', '<textarea id="userComment"></textarea>', false);
		var noticeFieldset = me.getFieldSet({id : 'noticeFieldset'}, '注意事项', noticeHtml, false);
		
		// 5、整体布局拼接
		// 5.1、topDiv：最上层按钮及工序选择
		// 5.2、一个大的fieldset包含了一个2行2列的大table：leftTd1、rightTd1、leftTd2、rightTd2
		// 5.3、每个单元格基本都是一个n行1列的table
		var topDiv = '<div id="workOrderFlowBox" style="padding: 0px 0px 4px 3px;"></div>' +
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
			var processChecked = $(this).closest('fieldset').find('input[type="checkbox"]:checked');
			me.changeProcessCheckbox(processChecked);
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
			}else if(id == 'setSplitLengthRole'){ // 设置分盘要求
				me.setSplitLengthRole();
			}else if(name == 'workOrderButton'){ // 绑定查看生产单按钮
				me.showWorkOrderDetail($(this).attr('workOrderNo'));
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
		//me.equipQcCacheMap = result.equipQc;
		
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
		
		// 8、全选工序选择框:用于最后促发change事件，预设全选不能促发
		me.initGroupChecked();

		var e7 = new Date();
		console.log('drawingProcessComponent--选中所有选择框并处理响应显示数据用时:' + Math.round(e7 - e6))
		console.log('总用时:' + Math.round(e7 - e))
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
			var checkboxGroup = me.getFieldSet(attr, '工序[' + me.orderIdProductTypeMap[orderId] + ']', items.join(''), false);
			processPanel.push(checkboxGroup);
		}
		
		$('#chooseProcess', '#' + me.id).append(processPanel.join(''));
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
		var liasname = (typeof(toSetProcess.ALIASNAME) == "undefined") ? '' : toSetProcess.ALIASNAME;
		var html = '<label><input class="hasEvent" type="checkbox" name="' + orderId + '" text="' + (toSetProcess.NAME + liasname) + '" ' +
			'value="' + toSetProcess.CODE + '" id="'  + (orderId + toSetProcess.CODE + toSetProcess.SEQ) + '" seq="' + toSetProcess.SEQ + '" ' +
			'section="' + toSetProcess.SECTION + '" ' + 'nextSection="' + toSetProcess.NEXTSECTION + '" style="vertical-align:middle;">' +
			'<b style="vertical-align: middle;margin-right: 15px;">' + (toSetProcess.NAME + liasname) + '</b></label>';
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
		var liasname = (typeof(toSetProcess.ALIASNAME) == "undefined") ? '' : toSetProcess.ALIASNAME;
		var isSetCheck = true; // 是否放置Checkbox组件
		Ext.Array.each(processesMergedTimeArray, function(processesMergedArray, n) {
			// processesMergedArray 每一次生产单的工序使用组合
			Ext.Array.each(processesMergedArray, function(craftsProcessArray, m) {
				for (var productType in craftsProcessArray) { // 工艺：工序列表
					var processArray = craftsProcessArray[productType];
					Ext.Array.each(processArray, function(process, j) { // 工序列表
						if (me.orderIdProductTypeMap[orderId] == productType
								&& process.name == (toSetProcess.NAME + liasname)
								&& process.code == (toSetProcess.CODE + '-' + toSetProcess.SEQ)) {
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
				// 印字内容做处理
				if(inOutData.outMatDesc && inOutData.outMatDesc != ''){
					inOutData.outMatDesc = inOutData.outMatDesc.replace('@@', (inOutData.custProductType + ' ' + inOutData.custProductSpec));
				}
				outDataTmp = me.outDataCacheMap[inOutData.orderItemId];
				if (outDataTmp) {
					outDataTmp.push(inOutData);
				} else {
					me.outDataCacheMap[inOutData.orderItemId] = [inOutData]
				}
			} else if (inOutData.inOrOut == 'IN') {
				var inventory = me.materialsInventory[inOutData.matCode];
				if (inventory) {
					// console.log(inventory.MATTYPE + '---' + inventory)
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
	 * 在未完成的订单基础上处理同产品型号的合并生产 1、先循环订单列表获得要合并的订单，并封装到一个map对象 2、循环订单列表变更合并的订单的信息
	 * 3、在循环中首先获取合并的对象，然后变更其及子result中的长度，再再其中变更他包含的合并的订单的数据（这样做为了不影响其他不合并的订单的信息）
	 */
	initOrderGroup : function() {
		var me = this, outDataTmp;
		Ext.Array.each(me.orderData, function(order, i) {
			var unFinishedLength = Number(order.orderLength), splitLengthRoleWithYuliang;
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
			order.wireCoil = '普通盘具';

			// 改变所有投入信息
			for (var firKey in me.inDataCacheMap) {
				firInDataMap = me.inDataCacheMap[firKey];
				for (var secKey in firInDataMap) {
					Ext.Array.each(firInDataMap[secKey], function(inData, n) {
						if (order.id == inData.orderItemId) {
							inData.unFinishedLength = unFinishedLength;
						}
					});
				}
			}

			// 改变所有产出信息
			outDataTmp = me.outDataCacheMap[order.id];
			Ext.Array.each(me.outDataCacheMap[order.id], function(outData, n) {
				outData.color = order.color;
				outData.wireCoil = order.wireCoil;
				outData.operator = order.operator;
				outData.conductorStruct = order.conductorStruct;
				outData.splitLengthRole = order.splitLengthRole;
				outData.splitLengthRoleWithYuliang = order.splitLengthRoleWithYuliang;
				outData.wireCoilCount = order.wireCoilCount;
				outData.unFinishedLength = unFinishedLength;
				// 设置模芯模套：护套前外径有值
				if (outData.frontOutsideValue) {
					me.setMoldCoreSleeve(outData);
				}
			});
		})
	},

	/**
	 * 改变工序选择所产生的事件
	 */
	changeProcessCheckbox : function(processChecked) {
		// inDataMap: 计算后的结果，map形式，用于合并长度
		// inDataArray: 计算后的结果，数组形式，最后load到matStore中去
		// inFirDataTmp: 第一次从缓存map中根据型号/规格/SEQ/工序取出来的对象
		// inDataTmp: 临时放置indata投入对象
		var me = this, inDataMap = {}, inDataArray = [], outMap = {}, outData = [], inFirDataTmp, inDataTmp, processCode;

		// 获取fieldset的工序组的型号规格的属性
		var productTypeSpec = processChecked.closest('fieldset').attr('productTypeSpec');
		
		Ext.Array.each(processChecked, function(process, n) {
			var processName = $(process).attr('text');
			var processCode = $(process).val();
			var seq = $(process).attr('seq');
			orderItemId = $(process).attr('name');
			if (n == 0) {
				$('b#processName', '#' + me.id).text(processName);
				$('#processCode', '#' + me.id).val(processCode);
			}
			
			if (n == (processChecked.length - 1)) {
				// 1、显示页面对应工序的参数值：标称厚度/外径
				Ext.Array.each(me.orderData, function(order, i) {
					Ext.Array.each(me.outDataCacheMap[order.id], function(record, m) {
						if ((record.productType + record.productSpec + record.processCode + record.seq) == (productTypeSpec + processCode + seq)) {
							order.standardPly = record.standardPly;
							order.guidePly = record.guidePly;
							order.minPly = record.minPly;
							order.outsideValue = record.outsideValue;
							order.outsideMinValue = record.outsideMinValue;
							order.outsideMaxValue = record.outsideMaxValue;
							order.outMatDesc = record.outMatDesc;
							order.material = record.material;
							order.moldCoreSleeve = record.moldCoreSleeve;
							return false;
						}
					});
				});
			}
		});	
		
		me.changeMatOaByChangeCheckbox()

		me.getEquip(); // 显示可用设备
		me.orderGrid.datas = me.orderData;
		me.doLayoutTable(me.orderGrid);
		
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
							inDataTmp.unFinishedLength = Math.round(inDataTmp.unFinishedLength + inData.unFinishedLength)
						} else {
							inDataTmp = Ext.clone(inData);
						}
					});
					if (inDataTmp) {
						if (tmpDataMap[secKey]) {
							tmpDataMap[secKey].unFinishedLength = Math.round(tmpDataMap[secKey].unFinishedLength + inDataTmp.unFinishedLength)
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
	 * 保存生产单
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
				processesMergedArray : Ext.encode(processesMergedArray),
				orderTaskLengthJsonData : Ext.encode(me.getOrderTaskLengthJsonData()),
				workOrderSection : me.getSection(),
				nextSection : me.getNextSection(),
				cusOrderItemIds : me.getCusOrderItemIds(),
				completeCusOrderItemIds : me.getCompleteCusOrderItemIds(),
				isDispatch : $('#isDispatch', '#' + me.id).prop('checked'),
				isAbroad : $('#isAbroad', '#' + me.id).prop('checked')
			},
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.PROMPT, '保存成功！');

				var result = Ext.decode(response.responseText); // 返回结果
				me.afterSaveWorkOrder(but, result); // 保存提交后需要进行的操作
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.PROMPT, '保存失败！');
			}
		});
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
		me.changeButtonStatus(false, true, false); // 1、改变button的显示影藏状态

		// 1、生产单页面赋值，供下发功能使用和下一级生产单生成使用；
		me.preWorkOrderNo = result.workOrderNo; // 将页面的上一次生产单号放入页面，以便下次保存使用

		// 2、刷新父页面
		Ext.ComponentQuery.query('handSchedule2Grid')[0].getStore().load();

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
	 * 默认设置每一组group的第一项为选中
	 */
	initGroupChecked : function() {
		var me = this;
		// 遍历工序选择checkbox组
		me.getChecksGroup().each(function(n){
			var checks = $(this).find('input[type="checkbox"]'); 
			checks.each(function(m, check){
				if(m == 0)
					$(check).prop('checked', true).change();
			});
		});
	},

	/**
	 * 下发生产单
	 */
	auditWorkOrder : function(but, win) {
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
					var orderItemId = '';
					if($(this).find('input[type="checkbox"]').length > 0){
						var orderItemId = $(this).find('input[type="checkbox"]:first').attr('name');
						me.getCheckeds($(this)).each(function(m, check){
						    $(check).next('label:first').remove();
						});
						me.getCheckeds($(this)).remove(); // 下发完了的就去掉
					}
				});

				// 3、默认设置每一组group的第一项为选中
				me.initGroupChecked();
				Ext.Msg.alert(Oit.msg.PROMPT, '下发成功！');

				// 4、刷新父页面：再刷一次，因为页面显示已下发，前面刷新还未下发，所以前面刷新看不到
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
		me.getChecksGroup().each(function(n, group){
			var checkeds = me.getCheckeds($(group));
			var productTypeSpec = $(group).attr('productTypeSpec');
			var processArray = [];
			checkeds.each(function(m, check){
				var checked = $(check);
				// 投入表
				var processInDataMap = me.inDataCacheMap[checked.val() + checked.attr('seq') + productTypeSpec];
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
							if ((productTypeSpec + checked.val() + checked.attr('seq')) == (outData.productType + outData.productSpec + outData.processCode + outData.seq)) {
								subResult.push(outData);
							}
						});
					}
				}
				processArray.push({
							name : checked.attr('text'),
							code : checked.val() + '-' + checked.attr('seq')
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
	getCheckeds : function(checksGroup){
	    var me = this;
	    return checksGroup.find('input[type="checkbox"]:checked');
	},
	
	// 判断工序是否还有选择按钮
	hasProcessCheck : function(){
	    return $('div#chooseProcess input[type="checkbox"]').length > 0;
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
	
	// 查看附件
	editRecord : function(record){
		var me = this;
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
				items : [{ fieldLabel : '材料', name : 'material', value : record.material
				}, { fieldLabel : '颜色字码', name : 'color', value : record.color
				}, { fieldLabel : '收线盘具', xtype : 'combobox',
					store : Ext.create('Ext.data.Store', {
						fields : ['name'],
						data : [{ "name" : "普通盘具" }, { "name" : "出口红盘" }, { "name" : "出口胶木板" }, { "name" : "出口熏蒸" }, { "name" : "铁盘" }, { "name" : "铁木盘" }]
					}),
					queryMode : 'local', name : 'wireCoil', displayField : 'name', valueField : 'name', value : record.wireCoil
				},{ xtype : 'textarea', fieldLabel : '印字要求', name : 'outMatDesc', value : record.outMatDesc,
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
	
	// 编辑更新任务
	updateOrderTask : function(record, form, win){
		var me = this;
		record.material = form.findField('material').getValue();																
		record.color = form.findField('color').getValue();
		record.wireCoil = form.findField('wireCoil').getValue();
		record.outMatDesc = form.findField('outMatDesc').getValue();
		
		Ext.Array.each(me.outDataCacheMap[record.id], function(outData, j) {
			outData.material = record.material;
			outData.color = record.color;
			outData.wireCoil = record.wireCoil;
			outData.outMatDesc = record.outMatDesc;
		});
		// 重新update
		me.orderData = me.orderGrid.datas;
		me.doLayoutTable(me.orderGrid);
		win.close();
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

	// 计算模芯模套 (旧删)模芯：A、线芯+0.1；B、线芯+0.15；C、线芯+0.2； 模套：外径+厚度*2
	// (新)模芯: 护套前外径+2.5; 模套:护套前外径+6
	setMoldCoreSleeve : function(outData) {
		var me = this;
		var moldCore = Number(outData.frontOutsideValue) + Number(2.5);
		var moldSleeve = Number(outData.frontOutsideValue) + Number(6);
		outData.moldCoreSleeve = moldCore.toFixed(3) + '/' + moldSleeve.toFixed(3);
	},
	
	// 获取列表选择的记录
	getSelectionGridDatas : function(grid){
	    var me = this;
		var checkedRow = $('#' + grid.id + ' table input[type="checkbox"][class!="checkAll"]:checked', '#' + me.id);
		var selection = [];
		Ext.Array.each(grid.datas, function(record, i) {
			checkedRow.each(function(j){
			    if($(this).attr('id') == record.id){
			    	selection.push(record)
			    }
			});
		});
		return selection;
	},

	/**
	 * 设置-订单分盘要求
	 */
	setSplitLengthRole : function(btn) {
		var me = this;
		var selection = me.getSelectionGridDatas(me.orderGrid);
		if (selection && selection.length == 1) {
			var record = selection[0];

			// 1、获取当前选择的项，将合并订单ID放入数组
			var splitLengthRole = $('#splitLengthRole', '#' + me.id).val();
			if (!me.validateExpression(splitLengthRole, record.contractLength)) { // 验证表达式是否正确
				return;
			}

			var orderId = record.id;
			Ext.Array.each(me.orderData, function(order, n) {
				if (order.id == orderId) {
				    order.splitLengthRole = splitLengthRole; // 获取所有相关的ID（排除选择了的）
				}
			});
			
			me.getChecksGroup().find('input[type="checkbox"]').prop('checked', false);

			// 4、重新处理分组和渲染
			me.initOrderGroup(); // 处理groupObj
			me.initGroupChecked(); // 置为第一个选中
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条订单产品记录！');
		}
	},

	/**
	 * 验证表达式是否正确：格式：1500+1000*2+500*3
	 */
	validateExpression : function(expression, total) {
		var me = this;
		var re = /^([(\d+)|(\d+.\d+)][+*]{1}|[(\d+)|(\d+.\d+)])+$/;
		if (re.test(expression)) {

		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '分盘要求格式错误！例：1500+1000*2+500*3');
			return false;
		}

		var total_ = 0;
		var specSplitRequire = me.analysisSplitRoleExpression(expression);
		Ext.Array.each(specSplitRequire, function(require, m) {
					total_ += Math.round(Number(require.length) * require.volNum);
				});
		if (total_ != total) {
			Ext.Msg.alert(Oit.msg.PROMPT, '设置分盘要求总长度与合同长度不等！合同长度：' + total + '，分盘：' + expression + '=' + total_);
			return false;
		}
		return true;
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
