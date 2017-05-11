// 绝缘工段下发生成生产单
// 页面包含：订单信息、半制品产出信息、物料需求信息
// 按钮功能：
// －－1、调整工序顺序：工序位置错误调整（使用少，常用于技术部工序顺序做错了）；
// －－2、跳过该工序：跳过工序不下发该工序的生产单（使用少，常用于挤出单层和火花之间的辐照工序，辐照不做就跳过）；
// －－3、放余量后合并：选择多个订单分别对长度添加余量后合并长度（两个订单长度分别1000和500，余量固定为5，使用该功能后总长度为1510=1005+505）；
// －－4、合并后放余量：选择多个订单合并长度后添加余量（两个订单长度分别1000和500，余量固定为5，使用该功能后总长度为1505=(1000+500)
// + 5）；
// －－5、单个拆分：针对合并余量使用，单个脱离合并的订单组（三个订单合并，选择一个单个拆分，其它两个仍然保持合并）；
// －－6、关联拆分：针对合并余量使用，关联的合并组全部拆开还原（三个订单合并，选择一个关联拆分，其它两个也全部拆开了）；
// －－7、分盘要求：1、可以作为分段下单功能（总长200000，可以填写10000先下，剩下的以后再下），2、同时可以设置分盘的要求（总长5000，要求每1000一盘，则可填写1000*5）；
// －－8、批量设置：选择半成品信息，对一些工序数据进行批量修改（使用少，基本数据错误调度就会提出，或等技术部更改，或下手工单）；
// 注：余量方法：根据工序，型号，长度、导体结构来获取余量
//
// 页面逻辑：
// 1、获取页面需要的数据：绝缘工段包含的工序、此些工序所需要的投入产出信息、设备信息、接受人信息
// 2、页面渲染顺序
// －－a、渲染工序选择radio；--
// －－b、封装订单／产出／投入数据；
// －－c、选择一个radio；
// －－d、获取对应工序的三个grid的store数据和设备要求数据
Ext.define('bsmes.view.ProcessWorkOrderJYWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.processWorkOrderJYWindow',
	width : document.body.scrollWidth,
	height : document.body.scrollHeight,
	padding : 5,
	title : '<a style="font-size: 16px;">宝 胜 特 种 电 缆 有 限 公 司 绝 缘 工 序 生 产 单</a>',
	titleAlign : 'center',
	modal : true,
	plain : true,
	autoScroll : true,
	savedWorkOrderNo : null, // 此次保存的生产单号
	preWorkOrderNo : null, // 上一道工序生产单号
	allDatas : null, // 页面加载所需要数据，open之前已经全部查询好了
	orderData : null, // 订单产品数据
	groupObj : {}, // {型号规格: {长度:XXX, // ITEMID:XX,idDetail : [XXX,
	// XXX]}}
	inDataCacheMap : {}, // 投入物料数据map，用于提高查询效率
	outDataCacheMap : {}, // 产出半成品数据map，用于提高查询效率
	//boolenSavedOldLine : null,
	sectionEquipArray : [], // 工段的所有设备
	materialsInventory : {}, // 原材料库存信息
	equipCombobox : null, // 设备选择下拉框，用来销毁用
	jumpProcessesMergedArray : [], // 跳过的工序描述json对象
	
	ymOrderGrid:{
		id : 'orderTable',
	    datas : [],
	    columns : [{xtype : 'checkbox'},
	    	{dataIndex : 'contractNo',text : '合同号', renderer : function(value, record){
			    var reg = /[a-zA-Z]/g;
				return (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length - 5) : value.replace(reg, "")) + '[' + record.operator + ']';
			}}, 
			{dataIndex : 'custProductType',text : '客户型号规格', renderer : function(value, record){
			    return value + ' ' + record.custProductSpec;
			}}, 
			{dataIndex : 'contractLength', text : '合同长度'},
			{dataIndex : 'groupLength',text : '实际投产长度', renderer : function(value, record){
			    if (record.isGroup) {
				    return '';
				}
				return value + '*' + record.numberOfWires;
			}}, 
			{dataIndex : 'conductorStruct',text : '导体结构'},
			{dataIndex : 'material',text : '材料'},
			{dataIndex : 'coverRate',text : '搭盖率'
		}],
		toolBar : '<div class="toolBar" style="padding: 0px 0px 4px 0px;">' +
			'<button class="btn-primary hasEvent" id="mergeOrderItem0">放余量后合并</button>' +
			'<button class="btn-primary hasEvent" id="mergeOrderItem1" style="margin-left: 5px;">合并后放余量</button>' +
			'<button class="btn-primary hasEvent" id="splitOrderItemAll" style="margin-left: 5px;">拆分</button>' +
			'<input type="text" id="splitLengthRole" value="" style="margin-left: 5px;" placeholder="分盘要求,例:500*2+50">' +
			'<button class="btn-primary hasEvent" id="setSplitLengthRole" style="margin-left: 5px;">确定</button>' +
		'</div>'
	}, // 云母绕包的订单列表
	jchhOrderGrid:{
		id : 'orderTable',
	    datas : [],
	    columns : [{xtype : 'checkbox'},
			{dataIndex : 'contractNo',text : '合同号', renderer : function(value, record){
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
			{dataIndex : 'leftOrderLength', text : '剩余未下发长度', renderer : function(value, record){
				console.log(value);
			    if (record.splitLengthRole && record.splitLengthRole != '') {
					value += '<br/>' + record.splitLengthRole;
				}
				return value;
			}},
			{dataIndex : 'groupLength',text : '实际投产长度', renderer : function(value, record){
			    if (record.isGroup) {
					return '';
				}
				if (record.splitLengthRoleWithYuliang && record.splitLengthRoleWithYuliang != '') {
					value += '<br/>' + record.splitLengthRoleWithYuliang;
				}
				return value;
			}}, 
			{dataIndex : 'conductorStruct',text : '导体结构'
		}],
		toolBar : '<div class="toolBar" style="padding: 0px 0px 4px 0px;">' +
			'<button class="btn-primary hasEvent" id="mergeOrderItem0">放余量后合并</button>' +
			'<button class="btn-primary hasEvent" id="mergeOrderItem1" style="margin-left: 5px;">合并后放余量</button>' +
			'<button class="btn-primary hasEvent" id="splitOrderItemAll" style="margin-left: 5px;">拆分</button>' +
			'<input type="text" id="splitLengthRole" value="" style="margin-left: 5px;" placeholder="分盘要求,例:500*2+50">' +
			'<button class="btn-primary hasEvent" id="setSplitLengthRole" style="margin-left: 5px;">确定</button>' +
		'</div>'
	}, // 挤出和火花的订单列表
	jcOutGrid:{
		id : 'outTable',
	    datas : [],
	    columns : [{dataIndex : 'moldCoreSleeve',text : '模芯/模套'}, 
			{dataIndex : 'material',text : '材料'}, 
			{dataIndex : 'wiresStructure', text : '线芯结构'},
			{dataIndex : 'color',text : '颜色'},
			{dataIndex : 'unFinishedLength',text : '长度', renderer : function(value, record){
			    if (record.splitLengthRoleWithYuliang && record.splitLengthRoleWithYuliang != '' && value != record.splitLengthRoleWithYuliang) {
						value += '<br/>' + record.splitLengthRoleWithYuliang;
				}
				return value;
			}},
			{dataIndex : 'standardPly',text : '标称厚度'},
			{dataIndex : 'standardMaxPly',text : '最大值'},
			{dataIndex : 'standardMinPly',text : '最小值'},
			{dataIndex : 'outsideValue',text : '外径'
		}]
	}, // 右侧半成品(除火花)列表
	hhOutGrid:{
		id : 'outTable',
	    datas : [],
	    columns : [{xtype : 'checkbox'},
			{dataIndex : 'material',text : '材料'}, 
			{dataIndex : 'wiresStructure', text : '线芯结构'},
			{dataIndex : 'color',text : '颜色'},
			{dataIndex : 'unFinishedLength',text : '分盘要求', renderer : function(value, record){
			    if (record.splitLengthRole && record.splitLengthRole != '') {
					value += '<br/>' + record.splitLengthRole;
				}
				return value;
			}},
			{dataIndex : 'wireCoil',text : '盘具'},
			{dataIndex : 'standardPly',text : '标称厚度'},
			{dataIndex : 'standardMaxPly',text : '最大值'},
			{dataIndex : 'standardMinPly',text : '最小值'},
			{dataIndex : 'outsideValue',text : '外径'
		}],
		toolBar : '<div class="toolBar" style="padding: 0px 0px 4px 0px;">' +
			'<select id="wireCoil" style="margin-left: 0px;">' +
				'<option value="">请选择盘具</option>' +
			    '<option value="宽630">宽630</option>' +
			    '<option value="窄630">窄630</option>' +
			    '<option value="500型">500型</option>' +
			    '<option value="500束">500束</option>' +
			    '<option value="400型">400型</option>' +
			'</select>' +
			'<button class="btn-primary hasEvent" id="setOutParamer" style="margin-left: 5px;">批量设置</button>' +
		'</div>'
	}, // 右侧火花半成品列表
	orderGrid: null, // 订单列表
	outGrid: null, // 右侧半成品列表
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
		me.savedWorkOrderNo = null, // 此次保存的生产单号
		me.preWorkOrderNo = null, // 上一道工序生产单号
		me.inDataCacheMap = {}; // 投入物料数据map，用于提高查询效率
		me.outDataCacheMap = {}; // 产出半成品数据map，用于提高查询效率
		me.sectionEquipArray = []; // 工段的所有设备
		me.materialsInventory = {}; // 原材料库存信息
		me.groupObj = {}; // {型号规格: {长度:XXX, // ITEMID:XX,idDetail : [XXX, XXX]}}
		
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
	    	window.drawingComponent(); 
	    }
	},
	
	// 1、初始化整个窗口的html；2、绑定事件
	getContentHtml : function(){
	    var me = this;
	    
	    var e = new Date();
				
	    // 1、初始化默认的列表对象: 订单列表和半成品列表
	    me.orderGrid = me.jchhOrderGrid; 
	    me.outGrid = me.jcOutGrid;
		
		// 2、是否急件、是否出口、是否陈线
		var jjckcx = '<label><input type="checkbox" id="isDispatch" value="1" style="margin-left: 10px;vertical-align:middle;"><b style="color:red;vertical-align:middle;">急件</b></label>' +
				'<label><input type="checkbox" id="isAbroad" value="1" style="margin-left: 30px;vertical-align:middle;"><b style="color:red;vertical-align:middle;">出口</b></label>' +
				'<label><input type="checkbox" id="isHaved" value="1" style="margin-left: 30px;vertical-align:middle;"><b style="color:red;vertical-align:middle;">陈线</b></label>'
		// 3、注意事项
		var noticeHtml = '<b>1、请严格对照相应工艺文件生产，平均值不小于标称值；</b><br/>' +
				'<b>2、最薄点厚度应不小于标称值的90%-0.1mm；</b><br/>' +
				'<b>3、按生产工艺、材料定额组织生产，保证产品质量；</b><br/>' +
				'<b>4、严格按照顺序生产，确保每根绝缘线芯长度，做好分段标识，不得私自分头；</b><br/>' +
				'<b>5、认真填写相关记录，按时间完成生产工作；</b><br/>' +
				'<b>6、请按生产进度要求组织生产，保证电缆按时交货。</b>';
		
		// 4、通过方法获取主要的几块区域的html
		var orderFieldSet = me.getFieldSet({id : 'orderTable'}, '订单信息', '', false);
		var outFieldSet = me.getFieldSet({id : 'outTable'}, '制造半成品信息', '', true);
		var inFieldSet = me.getFieldSet({id : 'inTable'}, '物料需求信息', '', true);
		var noticeFieldset = me.getFieldSet({id : 'noticeFieldset'}, '注意事项', noticeHtml, true);
		var userComment = me.getFieldSet(null, '备注', '<textarea id="userComment"></textarea>', false);
		var specialReqSplit = me.getFieldSet(null, '特殊分盘要求', '<textarea id="specialReqSplit">无特殊分盘要求</textarea>', false);
		
		// 5、整体布局拼接
		// 5.1、topDiv：最上层按钮及工序选择
		// 5.2、一个大的fieldset包含了一个2行2列的大table：leftTd1、rightTd1、leftTd2、rightTd2
		// 5.3、每个单元格基本都是一个n行1列的table
		var topDiv = '<div style="padding: 0px 0px 4px 3px;">' +
				'<button class="btn-primary hasEvent" id="changeProcessSeq">调整工序顺序</button>' +
				'<button class="btn-primary hasEvent" style="margin-left: 5px;" id="jumpProcess">跳过该工序</button>' +
				'<span id="chooseProcess"></span>' +
			'</div>';
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
				'<tr><td><span class="label">工序名称：</span><b id="processName"></b></td></tr>' +
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
		$(document, '#' + me.id).off("change", 'span#chooseProcess input[name="chooseProcess"]');
		$(document, '#' + me.id).off("click", 'button.hasEvent')
		$(document, '#' + me.id).off("change", 'input[type="checkbox"].checkAll')
		
		// 7、重新绑定页面的按钮事件
		$(document, '#' + me.id).on("change", 'span#chooseProcess input[name="chooseProcess"]', function(){
			me.changeProcess($(this).val(), $(this).attr('id'));
		});
		$(document, '#' + me.id).on("click", 'button.hasEvent', function(){
			var id = $(this).attr('id');
			$(this).attr('disabled', true);
			if(id == 'saveButton'){ // 绑定保存生产单事件
				me.saveWorkOrder();
			}else if(id == 'issueButton'){ // 绑定下发生产单事件
				me.auditWorkOrder();
			}else if(id == 'mergeOrderItem0'){ // 绑定放余量后合并
				me.mergeOrderItem(0);
			}else if(id == 'mergeOrderItem1'){ // 绑定合并后放余量
				me.mergeOrderItem(1);
			}else if(id == 'splitOrderItemAll'){ // 绑定全部拆分
				me.splitOrderItemAll();
			}else if(id == 'setSplitLengthRole'){ // 设备特殊分盘
				me.setSplitLengthRole();
			}else if(id == 'setOutParamer'){ // 设备特殊分盘
			    me.setOutParamer();
			}else if(id == 'lookUpAttachButton'){ // 查看附件
				me.lookUpAttachFileWindow();
			}else if(id == 'jumpProcess'){
			    me.jumpProcess();
			}else if(id == 'changeProcessSeq'){
			    me.changeProcessSeqShowWin();
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
	
	 // 工序去重
	changeSameProcess : function(result){
		var me = this;
		var processMap = {}; 
		var processArray = []; 
		Ext.Array.each(result.processList, function(process, n) {
			var key =  process.CODE + process.NAME;
			if(!processMap[key]){
				processMap[key] = key;
				processArray.push(process);
			}
		});
		result.processList = processArray
	},
	
	/**
	 * 渲染工序选择控件，渲染页面按钮
	 */
	drawingComponent : function() {
		var me = this;
		var e = new Date();
		
		// 1、所有的后台数据allDatas
		var result = me.allDatas; 
		me.changeSameProcess(result); // 工序去重
		
		
		// 1.1、使用extjs的日期控件渲染到要求完成时间
		new Ext.form.DateField({ renderTo: 'requireFinishDateSpan', itemId: 'requireFinishDate', xtype: 'datefield', format: 'Y-m-d', value: new Date(), allowBlank: false}); 
	    
	    var e1 = new Date();
		console.log('drawingComponent--使用extjs的日期控件渲染到要求完成时间用时:' + Math.round(e1 - e))
		
		// 2、绝缘下单情况的对象设置-------------新方法，难理解---------------
		me.workOrderFlowObject.init(me.orderData, result.allWorkOrder, result.processList);
		console.log(me.workOrderFlowObject)
		
		// 7、渲染页面checkbox工序选择组件
		me.setJumpProcess(result.processesMergedArrayStr);
		
		var e2 = new Date();
		console.log('drawingComponent--绝缘下单情况的对象设置用时:' + Math.round(e2 - e1))

		// 3、改变button的显示影藏状态
		me.changeButtonStatus(true, false, false);

		// 4、设置备注和其他的信息：是否出口
		me.setUserCommentAndOther(result.receivers); 

		var e3 = new Date();
		console.log('drawingComponent--设置button和备注是否出口显示影藏用时:' + Math.round(e3 - e2))

		// 5、请求的投入产出数据集处理放入页面缓存
		me.initInOutDataToCacheMap(result.proDecList, result.materialsInventory);

		var e4 = new Date();
		console.log('drawingComponent--投入产出数据集处理放入页面缓存用时:' + Math.round(e4 - e3))

		// 6、渲染页面radioGroup工序选择组件
		me.setChooseProcessRadioGroup(result.processList, true);

		var e5 = new Date();
		console.log('drawingComponent--渲染工序选择框框用时:' + Math.round(e5 - e4))

		// 7、所有工序设备放入缓存；工序选择radio并触发事件
		me.sectionEquipArray = result.equipArray;
		me.initRadioGroupChecked();
		
		var e6 = new Date();
		console.log('drawingComponent--工序选择radio并触发事件用时:' + Math.round(e6 - e5))
		console.log('drawingComponent--总用时:' + Math.round(e6 - e))
	},
	
	
	// 向页面添加工序选择按钮
	setChooseProcessRadioGroup : function(processs, isInit){
		var me = this;
		var html = '';
		var section = 1;
		var nextSection = 1;
		for(var i in processs){
			section = (i == 0 ? processs[i].SECTION : section);
			nextSection = (i == (processs.length - 1) ? processs[i].NEXTSECTION : nextSection);
			html += '<label><input type="radio" ' +
					'name="chooseProcess" ' +
					'value="' + processs[i].CODE + '" ' +
					'id="' + processs[i].NAME + '" ' +
					'section="' + processs[i].SECTION + '" ' +
					'nextSection="' + ((me.hasSteamLineProcess() && i == (processs.length - 1)) ? processs[i].SECTION : processs[i].NEXTSECTION) + '" ' +
					'style="margin-left: 25px;vertical-align: text-bottom;"><b style="vertical-align:middle;">' + processs[i].NAME + '</b></label>';
		}
		if(isInit && me.hasSteamLineProcess()){ // 蒸线
			html += '<label><input type="radio" name="chooseProcess" value="Steam-Line" id="蒸线" ' + 
				'" section="' + section + '" ' + 'nextSection="' + nextSection + 
				'" style="margin-left: 25px;vertical-align: text-bottom;">' +
				'<b style="vertical-align:middle;">蒸线</b></label>';
		}
		$('#chooseProcess', '#' + me.id).children().remove();
		$('#chooseProcess', '#' + me.id).append(html);
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
				// 3.3.3、添加编辑样式和编辑事件
				if(column.edit){
					cls += 'edit ';
					$(document, '#' + me.id).off("dblclick", '#' + record.id);
					$(document, '#' + me.id).on("dblclick", '#' + record.id, function(){
						column.edit(me, record);
					}); 
				}
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
	 * @description 页面初始化方法
	 * @description 处理返回的结果集，转换成map对象，提高页面查询效率
	 * @param inOutDataArray 投入产出信息
	 * @param materialsInventory 原材料库存
	 */
	initInOutDataToCacheMap : function(inOutDataArray, materialsInventory) {
		var me = this;

		// 库存信息保存
		for (i in materialsInventory) {
			me.materialsInventory[materialsInventory[i].MATERIALCODE + materialsInventory[i].COLOR] = materialsInventory[i]; // 原材料库存
		}
		var outDataTmp, color, kuandu, houdu, caizhi, chicun, guige, dansizhijing, firkey, seckey, inFirDataTmp, inSecDataTmp;
		// 16.3.2特别修改：针对同一个产品中包含有相同颜色的线，火花部分不允许合并，现将其中稍作处理，添加一个颜色数量来区分是否同一个产品中的
		var colorCountMap = {}; // 16.3.2
		Ext.Array.each(inOutDataArray, function(inOutData, n) {
					if (inOutData.inOrOut == 'OUT') {
						// 16.3.2 start
						var countKey = inOutData.processCode + inOutData.orderItemId + inOutData.color;
						if (colorCountMap[countKey]) {
							colorCountMap[countKey] += 1;
						} else {
							colorCountMap[countKey] = 1
						}
						inOutData.colorCount = colorCountMap[countKey];
						// 16.3.2 end
						outDataTmp = me.outDataCacheMap[inOutData.orderItemId];
						if (outDataTmp) {
							outDataTmp.push(inOutData);
						} else {
							me.outDataCacheMap[inOutData.orderItemId] = [inOutData]
						}
					} else if (inOutData.inOrOut == 'IN') {
						var inventory = me.materialsInventory[inOutData.matCode + inOutData.color];
						if (inventory) {
							// inOutData.matType = 'MATERIALS'; // 表识为原材料
							inOutData.disk = inventory.DESCRIPTION;
						}
						color = typeof(inOutData.color) == "undefined" ? '' : inOutData.color;
						kuandu = typeof(inOutData.kuandu) == "undefined" ? '' : inOutData.kuandu;
						houdu = typeof(inOutData.houdu) == "undefined" ? '' : inOutData.houdu;
						caizhi = typeof(inOutData.caizhi) == "undefined" ? '' : inOutData.caizhi;
						chicun = typeof(inOutData.chicun) == "undefined" ? '' : inOutData.chicun;
						guige = typeof(inOutData.guige) == "undefined" ? '' : inOutData.guige;
						dansizhijing = typeof(inOutData.dansizhijing) == "undefined" ? '' : inOutData.dansizhijing;

						firkey = inOutData.processCode;
						seckey = inOutData.matName + color + inOutData.quantity + kuandu + houdu + caizhi + chicun + guige + dansizhijing;
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

		// 蒸线直接从火花拷贝一份
		if (me.hasSteamLineProcess()) {
			for (orderItemId in me.outDataCacheMap) {
				var outArray = me.outDataCacheMap[orderItemId];
				for (i in outArray) {
					var out = outArray[i];
					if (out.processCode == 'Respool') {
						if (me.isSteamLine(out.productType)) { // 判断是否需要蒸线
							var steamLineOut = Ext.clone(out);
							steamLineOut.processCode = 'Steam-Line';
							steamLineOut.processName = '蒸线';
							steamLineOut.processId = 'Steam-Line-UUID-' + out.color;
							steamLineOut.matName = '蒸线半成品';
							steamLineOut.wireCoil = '宽630';
							outArray.push(steamLineOut);

							var steamLineIn = Ext.clone(out);
							steamLineIn.inOrOut = 'IN';
							steamLineIn.processCode = 'Steam-Line';
							steamLineIn.processName = '蒸线';
							steamLineIn.wireCoil = '宽630';
							steamLineIn.processId = 'Steam-Line-UUID-' + out.color;
							color = typeof(steamLineIn.color) == "undefined" ? '' : steamLineIn.color;
							kuandu = typeof(steamLineIn.kuandu) == "undefined" ? '' : steamLineIn.kuandu;
							houdu = typeof(steamLineIn.houdu) == "undefined" ? '' : steamLineIn.houdu;
							caizhi = typeof(steamLineIn.caizhi) == "undefined" ? '' : steamLineIn.caizhi;
							chicun = typeof(steamLineIn.chicun) == "undefined" ? '' : steamLineIn.chicun;
							guige = typeof(steamLineIn.guige) == "undefined" ? '' : steamLineIn.guige;
							firkey = steamLineIn.processCode;
							seckey = steamLineIn.matName + color + steamLineIn.quantity + kuandu + houdu + caizhi + chicun + guige;
							inFirDataTmp = me.inDataCacheMap[firkey];
							if (inFirDataTmp) {
								inSecDataTmp = inFirDataTmp[seckey];
								if (inSecDataTmp) {
									inSecDataTmp.push(steamLineIn)
								} else {
									inFirDataTmp[seckey] = [steamLineIn]
								}
							} else {
								inFirDataTmp = {};
								inFirDataTmp[seckey] = [steamLineIn];
								me.inDataCacheMap[firkey] = inFirDataTmp;
							}
						}
					}
				}
			}
		}

	},
	
	/**
	 * 初始化按钮选中项，获取上一道生产单
	 */
	initRadioGroupChecked : function(isJump) {
		var me = this;
		var processs = me.getProcessRadio();
		var processCode = me.getProcessCode();
		var next = processCode ? false : true;
		// 判断每道工序下发是否完成
		processs.each(function(i){
		    var process = $(this);
		    var processFinished = me.workOrderFlowObject.processFinished(process.val());
		    if(!process.prop('disabled') && 
		    	(processFinished || arrayContains(me.workOrderFlowObject.jumpProcess, process.val()))){ 
		    	// 工序完成/工序跳过的:设置disabled
		    	process.attr('disabled', true);
		    }
		    if(processCode && processCode == process.val()){ // 如果工序选择不为空，那就执行下一个
		    	next = true;
		    	return true;
		    }
		    if(next && !processFinished){ // 下一个工序，并且还没完成，选择并触发事件，跳出循环
		    	process.prop('checked', true).change();
		        return false;
		    }
		});
	},
	
	/**
	 * 监听工序变更方法： 改变工序变更页面
	 * 
	 * @param processCode 工序编码
	 * @param processName 工序名称
	 */
	changeProcess : function(processCode, processName) {
		var me = this;
		var e = new Date();

		// 1、分段变更订单长度
		me.changeOrderLength(processCode);

		var e1 = new Date();
		console.log('  changeProcess--分段变更订单长度changeOrderLength用时:' + Math.round(e1 - e))

		// 2、改变工序名；改变button的显示影藏状态
		$('b#processName', '#' + me.id).text(processName);
//		if (me.boolenSavedOldLine && processCode == 'Extrusion-Single') {
//			me.changeButtonStatus(false, false, false);
//		} else {
			me.changeButtonStatus(true, false, false);
//		}
//				if (process.inputValue == 'Respool') {
//					var isHavedHidden = me.query('#isHaved')[0]
//					isHavedHidden.setVisible(false);
//				}
//				if (process.inputValue == 'Extrusion-Single') {
//					var isHavedHidden = me.query('#isHaved')[0]
//					isHavedHidden.setVisible(true);
//				}
		
		var e2 = new Date();
		console.log('  changeProcess--改变工序名；改变button的显示影藏状态用时:' + Math.round(e2 - e1))

		// 3、对返回数据进行变更，主要处理已下发长度的变化
		me.groupObj = {};
		me.initResultData(processCode); 
		
		var e3 = new Date();
		console.log('  changeProcess--获取合并生产数据initResultData用时:' + Math.round(e3 - e2))

		// 4、更新三个grid的数据长度，封装data并重新渲染三个table
		me.initAndloadGridData(processCode);

		var e4 = new Date();
		console.log('  changeProcess--处理数据(长度等信息)渲染三个table列表用时:' + Math.round(e4 - e3))

		me.getEquip(processCode);

		var e5 = new Date();
		console.log('  changeProcess--获取设备信息用时:' + Math.round(e5 - e4))
	},
	
	/**
	 * 下发前：根据workOrderFlowObject类更新到orderData 更新订单长度：订单太长分批下载订单
	 * 根据查出来的全面下单情况，更新订单长度
	 */
	changeOrderLength : function(processCode) {
		var me = this;
		processCode = ((processCode && processCode != '') ? processCode : me.getProcessCode());
		var workOrder = me.workOrderFlowObject.getLastWorkOrder(processCode); // 上一道生产单
		me.preWorkOrderNo = workOrder ? workOrder.WORKORDERNO : '';
		Ext.Array.each(me.orderData, function(order, i) {
			// 根据下单情况重新设置订单长度orderLength
			var newOrderLength = workOrder ? workOrder.ITEMLENGTHINFO[order.id] : me.workOrderFlowObject.getUnSenteddLength(order.id, processCode);
			var splitLengthRole = workOrder ? workOrder.SPLITLENGTHROLE[order.id] : '';

			order.orderLength = newOrderLength; // 这次下发的长度
			order.leftOrderLength = newOrderLength; // 这次之外剩余未下发的长度
			order.splitLengthRole = splitLengthRole;

			me.updateOutDataField(order.id, {
				unFinishedLength : newOrderLength,
				splitLengthRole : splitLengthRole
			}, processCode);
			me.updateInDataField(order.id, {
				unFinishedLength : newOrderLength,
				splitLengthRole : splitLengthRole
			}, processCode);
		});
	},
	
	/**
	 * 对返回数据进行变更，主要处理已下发长度的变化 主页面传递 循环订单产品列表，处理未完成的长度字符串
	 */
	initResultData : function(processCode) {
		var me = this;

		Ext.Array.each(me.orderData, function(order, i) {
			if (order.splitLengthRole && order.splitLengthRole != '') { // 如果有分盘规则，
				// 特殊处理，跳过
				// me.groupObj[order.relateOrderIds.join()] = itemObj;
				// me.specSplitRequire[]
				return true;
			}

			var yuliang = me.redundantAmount(order.productType, order.orderLength, order.wiresStructure, processCode); // 计算余量
			/**
			 * 以下代码为封装合并产品的对象
			 */
			// 规格型号一模一样的合并一起，记录ID -----------1 START
			var itemObj = me.groupObj[order.relateOrderIds.join()];
			if (itemObj) {
				itemObj.totalLength += order.orderLength;
				itemObj.totalYuliang += yuliang; // 总余量，用于合并策略0的
				itemObj.lenDetail.push(order.orderLength);
				itemObj.idDetail.push(order.id);
			} else {
				itemObj = {
					totalLength : Math.round(order.orderLength), // 余量暂固定5M
					totalYuliang : yuliang, // 总余量，用于合并策略0的
					itemId : order.id,
					lenDetail : [order.orderLength],
					idDetail : [order.id]
				};
				me.groupObj[order.relateOrderIds.join()] = itemObj;
			} // -----------1 END
		});
	},
	
	/**
	 * 排生产单 : 渲染工序和物料需求的表格
	 * 
	 * @param processCode 当前选择工序编码
	 * @param strategy 策略：{0:'正常长度叠加就可以', 1:'合并后放余量'}
	 */
	initAndloadGridData : function(processCode) {
		var me = this;

		// 1、设置余量
		Ext.Array.each(me.orderData, function(order, n) {
			// 1.1、设置订单的长度、余量、是否分组等
			// 注：合并余量放置规则strategy，默认0，每根单独放余量，当合并规则变为1则使用合并后放余量规则
			var yuliang = me.calculatorOrderYuliang(order, processCode);

			// 1.2、更新产出字段：保存时候使用；设置产出的长度，材料，搭盖率、盘具、 盘数等
			if (processCode == 'wrapping_ymd') {
				var unFinishedLength = Math.round((order.orderLength + yuliang) * order.numberOfWires);
				me.updateOutDataField(order.id, {
					unFinishedLength : unFinishedLength
				}, processCode);
				me.updateInDataField(order.id, {
					unFinishedLength : unFinishedLength
				}, processCode);

				// 1.21、云母给订单赋物料、搭盖率属性
				Ext.Array.each(me.outDataCacheMap[order.id], function(outData, m) {
					if (processCode == outData.processCode) {
						order.material = outData.material;
						order.coverRate = outData.coverRate;
						return false;
					}
				});
			}

			// 1.3、线芯结构处理(主要针对云母绕包：和多个线芯结构的产品，订单上要放在一起查看)--
			var wiresMap = {}, wiresArray = [], ymname = '';
			Ext.Array.each(me.outDataCacheMap[order.id], function(outData, m) {
				if (processCode == outData.processCode && outData.wiresStructure) {
					if (!wiresMap[outData.wiresStructure]) {
						wiresMap[outData.wiresStructure] = outData.wiresStructure;
						wiresArray.push(outData.wiresStructure.split('<br/>')[0]);
						if (ymname == '' && outData.wiresStructure.split('<br/>')[1]) {
							ymname = outData.wiresStructure.split('<br/>')[1]
						}
					}
				}
			});
			order.conductorStruct = order.wiresStructure + ' ' + wiresArray.join() + (ymname == '' ? '' : ('<br/>' + ymname));

			// 1.4、产出需要更新的地方
			var upJson = {
				operator : order.operator,
				conductorStruct : order.conductorStruct,
				wiresStructure2 : order.wiresStructure,
				strategy : order.strategy,
				yuliang : (order.strategy == 1 ? 0 : yuliang),
				splitLengthRole : order.splitLengthRole,
				splitLengthRoleWithYuliang : order.splitLengthRoleWithYuliang,
				wireCoilCount : order.wireCoilCount
			};
			if (processCode == 'Respool') {
				upJson['wireCoil'] = '宽630';
			}
			me.updateOutDataField(order.id, upJson, processCode);

			// 1.5、设置投入字段：保存时候使用；长度，余量等
			me.updateInDataField(order.id, {
				strategy : order.strategy,
				yuliang : (order.strategy == 1 ? 0 : yuliang)
			}, processCode)
		});

		// 2、封装半成品列表的显示信息，主要计算长度，余量等，用map处理
		// data存放工序列表record，matOaData存放物料需求列表record，Map为临时缓存
		var outData = [], outDataMap = {}, outDataTmp, inData = [], inDataMap = {}, inFirDataTmp = me.inDataCacheMap[processCode];
		/**
		 * @author DingXintao
		 * @date 2015-08-24
		 * @description 余量分开放,不同芯数合并带来的余量放置问题 1、先对合并后产出放置余量，然后再设置投入的用量
		 */
		for (var id in me.outDataCacheMap) {
			outDataTmp = me.outDataCacheMap[id];
			if (outDataTmp) {
				Ext.Array.each(outDataTmp, function(data, m) {
					if (processCode == data.processCode) {
						if (data.splitLengthRole && data.splitLengthRole != '') {
							var specSplitRequire = me.analysisSplitRoleExpression(data.splitLengthRole);

							Ext.Array.each(specSplitRequire, function(require, m) {
								var mapData = Ext.clone(data);
								var outyl = me.redundantAmount(data.productType, require.length, data.wiresStructure2, processCode); // 计算余量
								mapData.splitLengthRoleWithYuliang = require.volNum == 1 ? (Number(require.length) + Number(outyl)) : ((Number(require.length) + Number(outyl)) + '*' + require.volNum);
								mapData.unFinishedLength = Math.round((Number(require.length) + Number(outyl)) * require.volNum);
								outDataMap['random-' + Math.random()] = mapData;
							});
						} else {
							// 合并规则加上relateOrderIds：相关联的订单ID，有调度手动合并
							var relateOrderIds = data.relateOrderIds; // @editor:丁新涛,
							// @date:2015-08-18
							var productSpec = '';
							// data.productSpec.substring(data.productSpec.lastIndexOf('*')
							// + 1, data.productSpec.length);
							var seckey = relateOrderIds + productSpec + me.getReplaceColor(data.color) + data.colorCount;
							if (outDataMap[seckey]) {
								outDataMap[seckey].unFinishedLength += data.unFinishedLength;
								outDataMap[seckey].totalYuliang += data.yuliang; // 总余量，用于合并策略0的
							} else {
								outDataMap[seckey] = Ext.clone(data);
								outDataMap[seckey].totalYuliang = outDataMap[seckey].yuliang; // 总余量，用于合并策略0的
							}
						}
					}
				});
			}
		}

		// 3、根据实际情况重新更新产出和投入的余量：一个3芯100和2芯50，最后一个1芯100米的余量可能跟订单合并150米的余量是不等的，必须要具体到任务单上面
		for (var key in outDataMap) {
			// 计算合并后的余量，然后设置到其中一个产出上面，再更新到对应的投入上
			var out = outDataMap[key];
			if (out.splitLengthRole && out.splitLengthRole != '') {
			} else {
				if (out.strategy == 1) { // 合并后放余量要重新计算余量，并且设置到产出
					var yuliang = me.redundantAmount(out.productType, out.unFinishedLength, out.wiresStructure2, processCode); // 计算余量
					out.unFinishedLength = Math.round(out.unFinishedLength + yuliang);
					// @更新具体的投入产出的余量
					me.updateOutDataField(out.orderItemId, {
						yuliang : yuliang
					}, processCode);
					me.updateInDataField(out.orderItemId, {
						yuliang : yuliang
					}, processCode, {
						discrColor : out.discrColor
					});
				} else { // 简单合并长度只需要把长度合并
					out.unFinishedLength = Math.round(out.unFinishedLength + out.totalYuliang);
				}
			}
			outData.push(out);
		}

		// 4、封装物料需求列表的显示信息，主要计算长度，余量等，用map处理
		if (inFirDataTmp) {
			for (var secKey in inFirDataTmp) {
				Ext.Array.each(inFirDataTmp[secKey], function(data, m) {
					if (inDataMap[secKey]) {
						inDataMap[secKey].unFinishedLength += Math.round(data.unFinishedLength + data.yuliang)
					} else {
						inDataMap[secKey] = Ext.clone(data);
						inDataMap[secKey].unFinishedLength += data.yuliang;
					}
				});
			}
		}
		for (var key in inDataMap) {
			inData.push(inDataMap[key]);
		}

		// 5、根据工序选择制定table，并load数据
		// 5.1、选择table
		me.orderGrid = processCode == 'wrapping_ymd' ? me.ymOrderGrid : me.jchhOrderGrid;
		me.outGrid = (processCode == 'Respool' || processCode == 'Steam-Line') ? me.hhOutGrid : me.jcOutGrid;
        processCode != 'Respool' ? $('#inTable', '#' + me.id).show() : $('#inTable', '#' + me.id).hide(); // 如果是火花，物料不显示, 不允许编辑订单长度

		if (processCode == 'wrapping_ymd') { // 云母带绕包不显示产出
			$('#outTable', '#' + me.id).hide();
			$('#noticeFieldset', '#' + me.id).hide();
		} else {
			$('#outTable', '#' + me.id).show();
			$('#noticeFieldset', '#' + me.id).show();
		}
		// 5.2、将三个表格里面的数据重新赋值并重新加载html
		me.orderGrid.datas = me.orderData;
		me.outGrid.datas = outData;
		me.inGrid.datas = inData;
		me.doLayoutTable(me.orderGrid);
		me.doLayoutTable(me.outGrid);
		me.doLayoutTable(me.inGrid);
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
	
	/**
	 * 计算订单的余量
	 * 
	 * @param 订单
	 */
	calculatorOrderYuliang : function(order, processCode) {
		var me = this, yuliang = 0, isGroup = false, groupLength = 0, wireCoilCount = 1, splitLengthRoleWithYuliang = '';
		// 1、手工配置分盘1500*1+1000*2+500*3，余量计算
		// 2、根据合并情况：先放再合：余量各算各；先合再放：余量放队长上
		if (order.splitLengthRole && order.splitLengthRole != '') {
			var specSplitRequire = me.analysisSplitRoleExpression(order.splitLengthRole);
			wireCoilCount = 0;
			splitLengthRoleWithYuliang = ''; // 带余量的规则
			Ext.Array.each(specSplitRequire, function(require, m) {
				var itemyl = me.redundantAmount(order.productType, require.length, order.wiresStructure, processCode);
				yuliang += itemyl * Number(require.volNum); // 计算余量
				wireCoilCount += Number(require.volNum); // 计算盘数
				if (m != 0) {
					splitLengthRoleWithYuliang += '+';
				}
				splitLengthRoleWithYuliang += (Number(require.length) + Number(itemyl)) + (require.volNum == 1 ? '' : ('*' + require.volNum));
			});

			groupLength = Math.round(order.orderLength + yuliang); // ①
			isGroup = false;
		} else {
			yuliang = me.redundantAmount(order.productType, order.orderLength, order.wiresStructure, processCode); // 计算余量
			var item = me.groupObj[order.relateOrderIds.join()];
			if (item.itemId == order.id) {
				if (order.strategy == 1) { // 1:'合并后放余量',故余量重设，切显示总长度设置发生变化：①②
					yuliang = me.redundantAmount(order.productType, item.totalLength, order.wiresStructure, processCode); // 计算余量
					groupLength = Math.round(item.totalLength + yuliang); // ①
				} else {
					groupLength = Math.round(item.totalLength + item.totalYuliang); // ②
				}
				isGroup = false;
			} else {
				if (order.strategy == 1) // 合并后放余量,队员全部清零，因为余量都在队长身上
					yuliang = 0;
				isGroup = true;
			}
		}
		order.wireCoilCount = wireCoilCount;
		order.groupLength = groupLength; // ①
		order.splitLengthRoleWithYuliang = splitLengthRoleWithYuliang; // ①
		order.yuliang = yuliang; // 当前订单所添加的余量
		order.isGroup = isGroup;
		return yuliang;
	},
	
	// -----------------------
	// 关于下单产品的生产单流程
	// 描述产品绝缘下单情况的对象
	// -----------------------
	workOrderFlowObject : {
		workOrder : {}, // 用来初始化对象：key:上一道生产单号; value:[生产单]
		processList : [], // 工序
		orderData : [], // 订单产品
		jumpProcess : [], // 跳过的工序

		workOrderArray : [], // 树形结构的生产单数组对象
		orderProcessLength : {}, // {orderItemId:[{processCode :长度},
		// {processCode :长度}]}

		init : function(orderData, initWorkOrder, processList) {
			var me = this;
			me.clearCache();
			me.initOrderProcessLength(Ext.clone(orderData)); // 初始化orderProcessLength
			me.initProcessList(Ext.clone(processList));
			Ext.Array.each(initWorkOrder, function(order, j) {
				// 处理workOrder
				var key = order.LASTWORKORDERNO ? order.LASTWORKORDERNO : '-1';
				if (me.workOrder[key]) {
					me.workOrder[key].push(order);
				} else {
					me.workOrder[key] = [order];
				}

				me.updateProcessLength4AddOrder(order); // 更新processLength
			});

			if (me.workOrder['-1']) {
				var nextWorOrder = Ext.clone(me.workOrder['-1']);
				nextWorOrder = me.formatWorkOrder(nextWorOrder, []);
				me.workOrderArray = nextWorOrder;
				me.setWorkOrder2Array(me.workOrderArray);
			}
		},

		// 清楚缓存
		clearCache : function() {
			var me = this;
			me.workOrder = {}, // key:上一道生产单号; value:[生产单]
			me.processList = [], // 工序
			me.orderData = [], // 订单产品
			me.jumpProcess = [],

			me.workOrderArray = [], // 树形结构的生产单数组对象
			me.orderProcessLength = {}; // {orderItemId:[{processCode :长度},
			// {processCode :长度}]}
		},

		// 判断工序是否已经下单完成了
		processFinished : function(processCode) {
			var me = this;
			var finished = true;
			if(arrayContains(me.jumpProcess, processCode)){
				return finished;
			}
			Ext.Array.each(me.orderData, function(order, i) {
				var sentLength = me.getSenteddLength(order.id, processCode);
				if (sentLength < order.contractLength) {
					finished = false;
					return false;
				}
			});
			return finished
		},

		// 递归将生产单变成一个树形结构
		setWorkOrder2Array : function(orderArray) {
			var me = this;
			Ext.Array.each(orderArray, function(order, i) {
				var nextWorOrder = Ext.clone(me.workOrder[order.WORKORDERNO]);
				nextWorOrder = me.formatWorkOrder(nextWorOrder, []);
				order.nextWorOrder = nextWorOrder
				me.setWorkOrder2Array(nextWorOrder);
			});
		},

		// nextWorOrder ：
		// {WORKORDERNO:1, ORDERITEMID:1, LASTWORKORDERNO:1,
		// ORDERTASKLENGTH:100},
		// {WORKORDERNO:1, ORDERITEMID:2, LASTWORKORDERNO:1,
		// ORDERTASKLENGTH:200},
		// {WORKORDERNO:2, ORDERITEMID:1, LASTWORKORDERNO:1,
		// ORDERTASKLENGTH:50},
		// {WORKORDERNO:2, ORDERITEMID:2, LASTWORKORDERNO:1, ORDERTASKLENGTH:60}
		// 变成
		// {WORKORDERNO:1, ITEMLENGTHINFO:{1:100,2:200}, LASTWORKORDERNO:1},
		// {WORKORDERNO:2, ITEMLENGTHINFO:{1:50,2:60}, LASTWORKORDERNO:1}
		formatWorkOrder : function(orderArray, oldArray) {
			var me = this;
			var map = {}, r = [];
			if (oldArray) {
				Ext.Array.each(oldArray, function(order, i) { // 旧的放进去
					map[order.WORKORDERNO] = order;
				});
			}

			Ext.Array.each(orderArray, function(order, i) {
				if (!map[order.WORKORDERNO]) {
					map[order.WORKORDERNO] = Ext.clone(order);
					map[order.WORKORDERNO].ITEMLENGTHINFO = {};
					map[order.WORKORDERNO].SPLITLENGTHROLE = {};
					delete map[order.WORKORDERNO].ORDERITEMID;
					delete map[order.WORKORDERNO].ORDERTASKLENGTH;
				}
				map[order.WORKORDERNO].ITEMLENGTHINFO[order.ORDERITEMID] = order.ORDERTASKLENGTH;
				map[order.WORKORDERNO].SPLITLENGTHROLE[order.ORDERITEMID] = order.SPLITLENGTHROLE;
			});
			for (var i in map) {
				r.push(map[i]);
			}
			if (r.length == 0) {
				return null
			}
			return r;
		},

		recursion : false, // 递归开关

		// 添加一组生产单
		setWorkOrderArray : function(orderArray) {
			var me = this;
			Ext.Array.each(orderArray, function(order, i) {
				me.setWorkOrder(order);
			});
		},

		// 添加一个生产单
		setWorkOrder : function(order) {
			var me = this;
			if (me.workOrderArray) {
				if (order.LASTWORKORDERNO && order.LASTWORKORDERNO != '') {
					me.recursion = true;
					me.recursionSetWorkOrder(me.workOrderArray, order);
					me.recursion = false;
				} else {
					me.workOrderArray = me.formatWorkOrder([order], me.workOrderArray);
				}
			}
			me.updateProcessLength4AddOrder(order); // 更新processLength
		},

		// 初始化processLength
		initOrderProcessLength : function(orderData) {
			var me = this;
			me.orderData = orderData;
			Ext.Array.each(orderData, function(order, i) {
				me.orderProcessLength[order.id] = {};
				me.orderProcessLength[order.id].contractLength = order.contractLength;
			});
		},

		// 更新processLength
		updateProcessLength4AddOrder : function(order) {
			var me = this;
			var item = me.orderProcessLength[order.ORDERITEMID];
			if (item) {
				if (item[order.PROCESSCODE]) {
					item[order.PROCESSCODE] = Number(item[order.PROCESSCODE]) + Number(order.ORDERTASKLENGTH);
				} else {
					item[order.PROCESSCODE] = Number(order.ORDERTASKLENGTH);
				}
			} else {
				item = {};
				item[order.PROCESSCODE] = Number(order.ORDERTASKLENGTH);
			}
			if (item && !item.contractLength && order.CONTRACTLENGTH && order.CONTRACTLENGTH != '') {
				item.contractLength = order.CONTRACTLENGTH;
			}
		},

		// 内部方法: 递归添加一个生产单
		recursionSetWorkOrder : function(orderArray, pushOrder) {
			var me = this;
			if (me.recursion) {
				Ext.Array.each(orderArray, function(order, i) {
					if (!me.recursion) {
						return false;
					}
					if (order.WORKORDERNO == pushOrder.LASTWORKORDERNO) {
						order.nextWorOrder = me.formatWorkOrder([pushOrder], order.nextWorOrder);
						me.recursion = false;
						return false;
					}
					me.recursionSetWorkOrder(order.nextWorOrder, pushOrder);
				});
			}
		},

		// 获取已经下发的长度
		getSenteddLength : function(orderItemId, processCode) {
			var me = this;
			var item = me.orderProcessLength[orderItemId];
			if (item) {
				if (item[processCode]) {
					return item[processCode];
				}
			}
			return 0;
		},

		// 获取剩余未下发的长度
		getUnSenteddLength : function(orderItemId, processCode) {
			var me = this;
			var item = me.orderProcessLength[orderItemId];
			if (item) {
				if (item[processCode]) {
					return Number(item.contractLength) - Number(item[processCode]);
				} else {
					return item.contractLength;
				}
			}
			return 0;
		},

		// 首次获取上一道生产单
		getInitWorkOrder : function() {
			var me = this;
			var back = {}; // 返回上一道生产单号，每个订单长度：注：生产单号随机取的一个

			me.recursion = true;
			back = me.recursionGetWorkOrder(me.workOrderArray)
			me.recursion = false;
			if (!back || !back.PROCESSCODE || (back.PROCESSCODE && back.PROCESSCODE == me.processList[me.processList.length - 1].CODE)) { // 如果是最后一道或者找不到，返回第一道
				back = me.getFirstWorkOrder();
			} else {
				back.NEXTPROCESSCODE = me.getNextProcessCode(back.PROCESSCODE);
			}
			return back;
		},

		// 首次获取上一道生产单
		getFirstWorkOrder : function() {
			var me = this;
			var back = {}; // 返回上一道生产单号，每个订单长度：注：生产单号随机取的一个
			back.NEXTPROCESSCODE = me.processList[0] ? me.processList[0].CODE : '';
			Ext.Array.each(me.orderData, function(order, i) {
				if (!back.ITEMLENGTHINFO) {
					back.ITEMLENGTHINFO = {}
				}
				if (!back.SPLITLENGTHROLE) {
					back.SPLITLENGTHROLE = {}
				}
				back.ITEMLENGTHINFO[order.id] = me.getUnSenteddLength(order.id, back.NEXTPROCESSCODE);
				back.SPLITLENGTHROLE[order.id] = order.splitLengthRole;
			});
			return back;
		},

		// 获取上一道生产单信息
		getLastWorkOrder : function(processCode) {
			var me = this;
			var back = null; // 返回上一道生产单号，每个订单长度：注：生产单号随机取的一个
			var lastProcessCode = me.getLastProcessCode(processCode);
			if (lastProcessCode != '') { // 第一道工序，取余量即可
				me.recursion = true;
				back = me.recursionGetWorkOrder(me.workOrderArray, lastProcessCode);
				if (back) { // 确实没有
					back.NEXTPROCESSCODE = me.getNextProcessCode(back.PROCESSCODE);
				}
				me.recursion = false;
			}
			if (!back) {
				back = me.getFirstWorkOrder();
			}
			return back;
		},

		// 内部方法: 递归获取上一个生产单
		recursionGetWorkOrder : function(orderArray, processCode) {
			var me = this;
			var lastWorkOrder = null;
			if (me.recursion) {
				Ext.Array.each(orderArray, function(order, i) {
					if (!me.recursion) {
						return false;
					}
					if (arrayContains(me.jumpProcess, order.PROCESSCODE)) { // 过滤了跳过的工序
						return true;
					}
					if ((!processCode || (processCode && order.PROCESSCODE == processCode)) && !order.nextWorOrder && order.PROCESSCODE != me.processList[me.processList.length - 1].CODE) {
						lastWorkOrder = Ext.clone(order);
						me.recursion = false;
						return false;
					}
					lastWorkOrder = me.recursionGetWorkOrder(order.nextWorOrder, processCode)
				});
			}
			return lastWorkOrder;
		},

		// 获取上一道工序编码
		getLastProcessCode : function(processCode) {
			var me = this;
			var lastProcessCode = ''; // 
			Ext.Array.each(me.processList, function(process, i) {
				if (arrayContains(me.jumpProcess, process.CODE)) { // 过滤了跳过的工序
					return true;
				}
				if (process.CODE == processCode) {
					return false;
				}
				lastProcessCode = process.CODE;
			});
			return lastProcessCode;
		},

		// 获取下一道工序编码
		getNextProcessCode : function(processCode) {
			var me = this;
			var nextProcessCode = '', stop = false; // 
			Ext.Array.each(me.processList, function(process, i) {
				if (process.CODE == processCode) {
					stop = true;
					return true;
				}
				if (arrayContains(me.jumpProcess, process.CODE)) { // 过滤了跳过的工序
					return true;
				}
				if (stop) {
					nextProcessCode = process.CODE;
					return false;
				}
			});
			return nextProcessCode;
		},

		/**
		 * 判断是否需要蒸线工序 型号包含YJ的就返回true
		 */
		initProcessList : function(processList) {
			var me = this;
			Ext.Array.each(me.orderData, function(order, n) {
				if (order.productType.indexOf('YJ') >= 0) {
					processList.push({
								CODE : 'Steam-Line',
								NAME : '蒸线'
							});
					return false;
				}
			});
			me.processList = processList;
		}
	},
	
	// 设置下单跳过工序
	setJumpProcess : function(processesMergedArrayStr){
		var me = this;
		var processesMergedTimeArray = [];
		if (processesMergedArrayStr && processesMergedArrayStr != '') {
			processesMergedTimeArray = eval('([' + processesMergedArrayStr + '])');
		}
		
		// processesMergedTimeArray 结构 : [跳过的,跳过的,下单的]
		// 跳过的结构 : [{型号 : 工序组}, {型号 : 工序组}]
		// 下单的结构 : [{型号 : 工序组}, {型号 : 工序组}]
		// 工序组的结构 : [{name : xx , code : xx }, {name : xx , code : xx }]
		Ext.Array.each(processesMergedTimeArray, function(processesMergedArray, m) {
			// 取最后一个之前的，都是跳过的
			if(m < processesMergedTimeArray.length - 1){
				Ext.Array.each(processesMergedArray, function(craftsProcessArray, n) {
					for (var productType in craftsProcessArray) { // 工艺：工序列表
						var processArray = craftsProcessArray[productType];
						Ext.Array.each(processArray, function(process, j) { // 工序列表
							me.workOrderFlowObject.jumpProcess.push(process.code); // 记录跳过的工序
						});
					}
				});
			}
		});
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
		$('#isAbroad', '#' + me.id).prop('checked', (remarks.indexOf('出口') >= 0));
		$('#receiverUserCode', '#' + me.id).val(receivers.length > 0 ? receivers[0].receiverUserCode : '');
		
		$userComment.height($userComment[0].scrollHeight);
	},
	
	/**
	 * 更新me.outDataCacheMap中的属性字段
	 * 
	 * @param orderItemId : 修改的订单id/required
	 * @param fields: 修改后字段与值的json对象/required
	 * @param processCode : 工序编码
	 * @param filters : 条件过滤
	 */
	updateOutDataField : function(orderItemId, fields, processCode, filters) {
		var me = this;
		Ext.Array.each(me.outDataCacheMap[orderItemId], function(outData, m) { // 遍历工序列表变更长度
			var pass = true;
			filterLoop : for (var filter in filters) {
				if (outData[filter] != filters[filter]) {
					pass = false;
					break filterLoop;
				}
			}

			if (pass && processCode == outData.processCode) {
				for (var field in fields) {
					if (field == 'wireCoil' && outData[field] && outData[field] != '') // 特殊字段更新，产出的盘具没有才使用默认
						continue;
					outData[field] = fields[field];
				}
			}

			// 设置模芯模套
			if (outData.wiresStructure2 && outData.outsideValue && outData.standardPly) {
				me.setMoldCoreSleeve(outData);
			}

		});
	},

	/**
	 * 更新inDataCacheMap中的属性字段
	 * 
	 * @param orderItemId : 修改的订单id/required
	 * @param fields: 修改后字段与值的json对象/required
	 * @param processCode : 工序编码
	 * @param filters : 条件过滤
	 */
	updateInDataField : function(orderItemId, fields, processCode, filters) {
		var me = this;
		var inDataFir = me.inDataCacheMap[processCode];
		if (inDataFir) {
			for (var secKey in inDataFir) {
				Ext.Array.each(inDataFir[secKey], function(inData, k) { // 遍历工序列表变更长度

					var pass = true;
					filterLoop : for (var filter in filters) {
						if (inData[filter] != filters[filter]) {
							pass = false;
							break filterLoop;
						}
					}

					if (pass && orderItemId == inData.orderItemId) {
						for (var field in fields) {
							inData[field] = fields[field];
						}
					}
				});
			}
		}
	},
	
	/**
	 * 排生产单: 显示工序使用可选设备
	 * 
	 * @param win 当前弹出框
	 * @param processCode 工序编码
	 */
	getEquip : function(processCode) {
		var me = this;
		var e = new Date();
		
		// 1、移除原来的
		$('#equipCodesSpan', '#' + me.id).children().remove();
		var equipArray = [], defaultEquip;
		Ext.Array.each(me.sectionEquipArray, function(equip, i) {
			if (processCode == equip.processCode) {
				equipArray.push(equip);
				if(!defaultEquip)
				    defaultEquip = equip.code;
			}
			
		});
		
		if(me.equipCombobox){
		    me.equipCombobox.destroy();
		}
		
		// 2、使用extjs的combobox控件渲染到要选择机台
		me.equipCombobox = new Ext.form.field.ComboBox({  
	        renderTo : 'equipCodesSpan',//节点的id  
	        itemId : 'equipCodes',
			store : Ext.create('Ext.data.Store', {
				fields : ['code', 'name'],
				data : equipArray
			}),
			multiSelect : (processCode == 'Respool'),
			displayField : 'name',
			valueField : 'code',
			width : 450,
		    allowBlank : false,
		    value : defaultEquip
	    }); 
		
		var e1 = new Date();
		console.log('  getEquip--使用extjs的combobox控件渲染到要选择机台用时:' + Math.round(e1 - e))
	},
	
	/**
	 * 保存生产单
	 */
	saveWorkOrder : function(but) {
		var me = this;

		// 判断工序是否按次序下发
		if (!me.beforeProcessIsSaved()) {
			return;
		}

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
		
//				 var a = [];
//				 var b = me.generateSubResult(a);
//				 console.log(b)
//				 for (var i in b) {
//				 	console.log(b[i].inOrOut + '--' + b[i].matName + '--' + b[i].color + '--' + b[i].orderLength + '--' + b[i].unFinishedLength + b[i].wireCoil)
//				 }
//				 return;

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
		 * @param ids4FinishedJY 绝缘工序全部下发完成的订单
		 * @param isDispatch 是否急件
		 * @param isHaved 是否陈线
		 * @param isAbroad 是否出口线
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
				processCode : me.getProcessCode(),
				userComment : $('#userComment', '#' + me.id).val(),
				specialReqSplit : $('#specialReqSplit', '#' + me.id).val(),
				processJsonData : Ext.encode(subResult),
				preWorkOrderNo : me.preWorkOrderNo,
				//processesMergedArray : Ext.encode(processesMergedArray),
				processesMergedArray : (me.jumpProcessesMergedArray.length > 0 ? (Ext.encode(me.jumpProcessesMergedArray)+',') : '') + Ext.encode(processesMergedArray),
				orderTaskLengthJsonData : Ext.encode(me.getOrderTaskLengthJsonData()),
				workOrderSection : me.getSection(),
				nextSection : me.getNextSection(),
				cusOrderItemIds : me.getCusOrderItemIds(),
				ids4FinishedJY : me.getCIDS4FinishedJY(),
				isDispatch : $('#isDispatch', '#' + me.id).prop('checked'),
				isHaved : $('#isHaved', '#' + me.id).prop('checked'),
				isAbroad : $('#isAbroad', '#' + me.id).prop('checked')
			},
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.PROMPT, '保存成功！');
				var result = Ext.decode(response.responseText);
				me.jumpProcessesMergedArray = []; // 清理跳过对象
				// me.boolenSavedOldLine = (me.getProcessCode() == 'Extrusion-Single' && $('#isHaved', '#' + me.id).prop('checked'));
				me.afterSaveWorkOrder(but, result); // 保存提交后需要进行的操作
				
				if($('#isHaved', '#' + me.id).prop('checked')){ // 如果下的陈线，直接下发更新成完成
					me.auditWorkOrder('FINISHED');
					$('#isHaved', '#' + me.id).prop('checked', false);
				}
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.PROMPT, '保存失败！');
			}
		});
	},

	/**
	 * 判断工序是否按次序下发
	 */
	beforeProcessIsSaved : function() {
		var me = this;
		var firstProcess = me.getFirstProcessRadio();
		// 前面的都不是disabled，其他的没有上一道生产单号，说明没有按顺序
		var checks = me.getProcessRadio();
		var unChecked = false;
		checks.each(function(n, check){
			if($(check).prop('checked')){
			    return false;
			}else if(!$(check).prop('disabled')){
				unChecked = true;
				return false;
			}
		});
		if (unChecked && (!me.preWorkOrderNo || me.preWorkOrderNo == '')) {
			Ext.Msg.alert(Oit.msg.PROMPT, '请按工序先后顺序下发生产单！');
			return false;
		} else {
			return true;
		}
	},

	/**
	 * 生成提交数据，当前所选工艺包括的投入产出
	 */
	generateSubResult : function(processesMergedArray) {
		var me = this;
		var subResult = []
		
		var process = me.getCheckedProcess();
		var processCode = process.val(), processName = process.attr('id');
		var processArray = [];
		var productType = '';
		
		// 遍历工序组中选中的工序

		// 投入表
		var processInDataMap = me.inDataCacheMap[processCode];
		if (processInDataMap) {
			for (var secKey in processInDataMap) {
				Ext.Array.each(processInDataMap[secKey], function(inData, j) {
					if (j == 0) { // 赋值一次就够了
						productType = inData.productType + ' ' + inData.productSpec;
					}
					if (inData.unFinishedLength > 0) { // 有任务长度的才提交
						subResult.push(inData);
					}
				});
			}
		}
		// 产出表
		for (var id in me.outDataCacheMap) {
			var outDataTmp = me.outDataCacheMap[id];
			if (outDataTmp) {
				Ext.Array.each(outDataTmp, function(outData, m) {
					if (processCode == outData.processCode && outData.unFinishedLength > 0) { // 有任务长度的才提交
						subResult.push(outData);
					}
				});
			}
		}

		processArray.push({
			name : processName,
			code : processCode
		});
							
		var craftsProcesses = {};
		craftsProcesses[productType] = processArray;
		processesMergedArray.push(craftsProcesses);

		return subResult;
	},

	// 获取当前工段
	getSection : function(){
		var me = this;
	    return me.getCheckedProcess().attr('section');
	},
	
	// 获取下一个工段:@description 获取生产单提交参数nextSection: 生产单下一个加工工段 1:绝缘工段 2:成缆工段 3:护套工段
	getNextSection : function(){
		var me = this;
	    return me.getCheckedProcess().attr('nextSection');
	},

	/**
	 * 判断订单每个工序是否都已经下发完毕了
	 */
	getCIDS4FinishedJY : function() {
		var me = this;
		var processs = me.getProcessRadio(); // 获取所有工序组列表
		var finishIds = [];

		Ext.Array.each(me.orderData, function(order, i) {
			var contractLength = order.contractLength; // 合同长度
			var orderLength = order.orderLength; // 本次下发长度
			var finish = true; // 默认该订单全部下完了
			
			
			processs.each(function(j) {
				var process = $(this);
				if (process.prop('disabled')) {
					return true;
				}
				var processCode = process.val();
				var orderProcessLength = me.workOrderFlowObject.getSenteddLength(order.id, processCode); // 该订单该工序下发的记录长度
				if (process.prop('checked')) { // 如果当前是选中的话
					orderProcessLength = Number(orderProcessLength) + Number(orderLength);
				}
				if (orderProcessLength < contractLength) {
					finish = false;
					return false;
				}
			});

			if (finish) {
				finishIds.push(order.id)
			}
		});
		return finishIds.join();
	},
	
	/**
	 * 下发后：从orderData更新到workOrderFlowObject 下单后修改页面存的缓存，绝缘下单情况
	 */
	updateWorkOrderFlowObject : function(workOrderNo) {
		var me = this, processCode = me.getProcessCode(), workOrderArray = [];
		Ext.Array.each(me.orderData, function(order, i) {
			workOrderArray.push({
						WORKORDERNO : workOrderNo,
						PROCESSCODE : processCode,
						LASTWORKORDERNO : me.preWorkOrderNo,
						ORDERTASKLENGTH : order.orderLength,
						ORDERITEMID : order.id,
						CONTRACTLENGTH : order.contractLength,
						SPLITLENGTHROLE : order.splitLengthRole
					});
		});
		me.workOrderFlowObject.setWorkOrderArray(workOrderArray);
	},

	/**
	 * @description private
	 * @description 方法中包含的私有方法
	 * @description 获取生产单提交参数cusOrderItemIds: 当前生产单中下发的所有客户生产订单明细IDs
	 */
	getCusOrderItemIds : function() {
		var me = this, idArray = [];
		Ext.Array.each(me.orderData, function(order, n) {
			if (order.orderLength > 0) { // 长度大于零才放
				idArray.push(order.id);
			}
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
			if (order.orderLength > 0) { // 长度大于零才放
				orderTaskLengthJsonData.push({
							cusOrderItemId : order.id,
							orderTaskLength : order.orderLength,
							splitLengthRole : order.splitLengthRole
						});
			}
		});
		return orderTaskLengthJsonData;
	},

	/**
	 * 保存提交后需要进行的操作 变更订单产品record中的长度信息 刷新右侧列表和物料列表的长度 刷新主页面列表store
	 */
	afterSaveWorkOrder : function(button, result) {
		var me = this;
//		if ($('#isHaved', '#' + me.id).prop('checked')) {
//			var processs = me.getProcessRadio();
//			processs.each(function(i){
//				var process = $(this);
//			    if($(this).attr('id') == '挤出-单层'){
//			    	process.attr('disabled', true);
//			    }else if($(this).attr('id') == '火花配套'){
//			    	process.prop('checked', true).change();
//			    	$('input#isHaved', '#' + me.id).prop('checked', false);
//			    }
//			});
//		} else {
			me.changeButtonStatus(false, true, false); // 1、 改变button的显示影藏状态
//		}

		// 2、生产单页面赋值，供下发功能使用和下一级生产单生成使用；
		me.savedWorkOrderNo = result.workOrderNo; // 将页面的上一次生产单号放入页面，以便下次保存使用

		// 3、改变页面缓存的值 workOrderFlowObject : null, // 绝缘部分所有的下单信息
		me.updateWorkOrderFlowObject(result.workOrderNo);

		// 刷新父页面
		Ext.ComponentQuery.query('handScheduleGrid')[0].getStore().load();

	},
	/**
	 * 下发生产单
	 */
	auditWorkOrder : function(status) {
		var me = this;
		Ext.Msg.wait('数据处理中，请稍后...', '提示');
		Ext.Ajax.request({
			url : 'handSchedule/updateWorkerOrderStatus',
			params : {
				workOrderNo : me.savedWorkOrderNo,
				status : status ? status : 'TO_DO'
			},
			success : function(response) {
				Ext.Msg.hide(); // 隐藏进度条
				me.changeButtonStatus(false, false, true); // 1、改变button的显示影藏状态

				Ext.Msg.alert(Oit.msg.PROMPT, '下发成功！');
				// 2、最后一个也是只读关闭窗口：全部下发完成
				var lastRadio = me.getLastProcessRadio();
				if (lastRadio.prop('checked') && me.workOrderFlowObject.processFinished(lastRadio.val())) { // 最后一个选中并且已经下发完了
					me.close();
					return;
				}
				// 3、默认设置每一组group的第一项为选中
				me.initRadioGroupChecked();
			},
			failure : function(response, action) {
				Ext.Msg.hide(); // 隐藏进度条
				Ext.Msg.alert(Oit.msg.PROMPT, '下发失败！');
			}
		});
	},
	
	/**
	 * 合并订单
	 * 
	 * @param strategy 策略：{0:'正常长度叠加就可以', 1:'合并后放余量'}
	 */
	mergeOrderItem : function(strategy) {
		var me = this;
		var relateOrderIds = me.getSelectionGridIds(me.orderGrid);
		if (relateOrderIds.length > 0) {
			// 1.1、判断所选择的合并项目是否已经与其他订单合并了
			// @param relateOtherNames 关联了其他订单的名字，用于提示显示用
			// @param relateOtherIds 关联了其他订单的id 用于过滤判断用
			// @param chagedRelateIds 强制合并时，设计到的需要变更的所有id
			var relateOtherNames = [], relateOtherIds = [], chagedRelateIds = [], contractNo, operator, custProductType, productSpec, contractLength, tmp;
			var reg = /[a-zA-Z]/g;
			Ext.Array.each(me.orderData, function(record, i) {
				if (arrayContains(relateOrderIds, record.id)) {
					if (!arrayContains(relateOrderIds, record.relateOrderIds)) { // 判断当前选择的与每一条中的属性是否包含，不包含说明此条与其他合并了
						contractNo = record.contractNo
						operator = record.operator
						custProductType = record.custProductType
						productSpec = record.productSpec
						contractLength = record.contractLength
						tmp = (contractNo.replace(reg, "").length > 5 ? contractNo.replace(reg, "").substring(contractNo.replace(reg, "").length - 5) : contractNo.replace(reg, "")) + '[' + operator + '] '
								+ custProductType + ' ' + productSpec + ' ' + contractLength;
						relateOtherNames.push(tmp);
						relateOtherIds.push(record.id);
						arrayAddAll(chagedRelateIds, record.relateOrderIds, relateOrderIds);
					}
				}
			});

			if (relateOtherIds.length > 0) { // 包含有与其他订单合并的项
				Ext.MessageBox.buttonText.yes = '强制合并';
				Ext.MessageBox.buttonText.no = '去除合并';
				Ext.MessageBox.buttonText.cancel = '取消';
				Ext.MessageBox.confirm = function(title, msg, fn) {
					this.show({
								title : title,
								width : 400,
								msg : msg,
								buttons : Ext.Msg.YESNOCANCEL,
								fn : fn,
								icon : this.QUESTION
							});
					return this;
				};
				var showHtml = '';
				for (var i = 0; i < relateOtherNames.length; i++) {
					showHtml += relateOtherNames[i] + '<br/>';
				}
				showHtml += '已经与其他订单合并，请选择合并方式！';
				Ext.MessageBox.confirm('选择合并方式', showHtml, function(btn, a, b, c) {
					if (btn == 'yes') { // 强制合并，从其他合并中剔除
						Ext.Array.each(me.orderData, function(order, n) {
							if (arrayContains(chagedRelateIds, order.id)) { // 涉及到的订单中的关联id属性数组中删除relateOtherIds含有的id
								arrayRemove(order.relateOrderIds, relateOrderIds);
							}
						});
						me.reLoadGridsForGroup(relateOrderIds, strategy);
					} else if (btn == 'no') { // 剔除合并，从本次选择中剔除
						arrayRemove(relateOrderIds, relateOtherIds);
						me.reLoadGridsForGroup(relateOrderIds, strategy);
					}
				});
				
				Ext.MessageBox.buttonText.yes = '是';
				Ext.MessageBox.buttonText.no = '否';
				Ext.MessageBox.confirm = function(title, msg, fn) {
					this.show({
								title : title,
								msg : msg,
								buttons : Ext.Msg.YESNO,
								fn : fn,
								icon : this.QUESTION
							});
					return this;
				};
			} else { // 不包含有与其他订单合并的项
				me.reLoadGridsForGroup(relateOrderIds, strategy);
			}
		}
	},
	
	/**
	 * 合并后重新刷新表格
	 * 
	 * @param relateOrderIds 此次选择的合并的订单id
	 * @param strategy 策略：{0:'正常长度叠加就可以', 1:'合并后放余量'}
	 */
	reLoadGridsForGroup : function(relateOrderIds, strategy) {
		var me = this;
		// 2、根据ID数组重新设置订单数据的关联ID属性
		var groupInfo = {}; // 所有分组情况,用来最后排序用
		var newData = []; // 新的order数组
		Ext.Array.each(me.orderData, function(order, n) {
			if (arrayContains(relateOrderIds, order.id)) {
				order.relateOrderIds = relateOrderIds;
				order.strategy = strategy;
			}
			groupInfo[order.relateOrderIds.join()] = order.relateOrderIds.join();
		});
		// 2.1、将合并的订单放在一起
		for (key in groupInfo) {
			Ext.Array.each(me.orderData, function(order, n) {
				if (key == order.relateOrderIds.join()) {
					newData.push(order);
				}
			});
		}
		me.orderData = newData;

		// 3、根据ID数组重新设置产出的关联ID属性
		Ext.Array.each(relateOrderIds, function(relateOrderId, n) {
			Ext.Array.each(me.outDataCacheMap[relateOrderId], function(outData, m) { // 遍历工序列表变更长度
				outData.relateOrderIds = relateOrderIds.join();
			});
		});

		// 4、重新处理分组和渲染
		me.groupObj = {};
		var processCode = me.getProcessCode();
		me.initResultData(processCode); // 处理groupObj
		me.initAndloadGridData(processCode); // 重新渲染几个表单
	},
	
	/**
	 * 拆分订单-拆分所包含
	 */
	splitOrderItemAll : function() {
		var me = this;
		var relateOrderIds = me.getSelectionGridIds(me.orderGrid);
		if (relateOrderIds.length > 0) {
			// 1.1、获取选择的订单的关联属性id数组
			var splitRelateIds = []; // 拆分的所有相关id
			Ext.Array.each(me.orderData, function(order, n) {
				if (arrayContains(relateOrderIds, order.id)) {
					arrayAddAll(splitRelateIds, order.relateOrderIds); // 获取所有相关的ID（排除选择了的）
				}
			});

			// 2、根据ID数组重新设置订单数据的关联ID属性
			Ext.Array.each(me.orderData, function(order, n) {
				if (arrayContains(splitRelateIds, order.id)) {
					order.relateOrderIds = [order.id];
				}
			});

			// 3、根据ID数组重新设置产出的关联ID属性
			Ext.Array.each(relateOrderIds, function(relateOrderId, n) {
				Ext.Array.each(me.outDataCacheMap[relateOrderId], function(outData, m) { // 遍历工序列表变更长度
					outData.relateOrderIds = relateOrderId;
				});
			});

			// 4、重新处理分组和渲染
			me.groupObj = {};
			var processCode = me.getProcessCode();
			me.initResultData(processCode); // 处理groupObj
			me.initAndloadGridData(processCode); // 重新渲染几个表单
		}
	},
	
	/**
	 * 设置-订单分盘要求
	 */
	setSplitLengthRole : function(btn) {
		var me = this;
		var selection = me.getSelectionGridDatas(me.orderGrid);
		var processCode = me.getProcessCode();
		if (selection && selection.length == 1) {
			var record = selection[0];

			// 1、获取当前选择的项，将合并订单ID放入数组
			var splitLengthRole = $('#splitLengthRole', '#' + me.id).val();
			if (!me.validateExpression(splitLengthRole, record.id, record.contractLength)) { // 验证表达式是否正确
				return;
			}

			var orderId = record.id;
			Ext.Array.each(me.orderData, function(order, n) {
				if (order.id == orderId) {
					var newOrderLength = me.getNewOrderLength(splitLengthRole); // 获取新的分段长度
					order.orderLength = newOrderLength;
					me.updateOutDataField(order.id, {
						unFinishedLength : newOrderLength
					}, processCode);
					me.updateInDataField(order.id, {
						unFinishedLength : newOrderLength
					}, processCode);

					if (splitLengthRole.indexOf('*') >= 0 || splitLengthRole.indexOf('+') >= 0) {
						order.splitLengthRole = splitLengthRole; // 获取所有相关的ID（排除选择了的）
					}
				}
			});

			// 4、重新处理分组和渲染
			me.groupObj = {};
			me.initResultData(processCode); // 处理groupObj
			me.initAndloadGridData(processCode); // 重新渲染几个表单
		} else {
			Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条订单产品记录！');
		}

	},
	
	// 火花配套半成品列表设置盘具
	setOutParamer : function(button){
		var me = this;
		var wireCoil = $('div.toolBar #wireCoil', '#' + me.id).val();
		var selection = me.getSelectionGridDatas(me.outGrid);
		if (wireCoil == '' || selection.length == 0) {
			return;
		}
		var idcolorArray = [];
		Ext.Array.each(selection, function(item, i) {
			idcolorArray.push(item.relateOrderIds + item.discrColor + item.color);
			if (wireCoil != null && wireCoil != '') {
				item.wireCoil = wireCoil;
			}
		});
		// 改变产出数据信息： colorArray为空改变所有，否则改变对用的颜色
		var processCode = me.getProcessCode();
		for (var id in me.outDataCacheMap) {
			Ext.Array.each(me.outDataCacheMap[id], function(outData, m) {
				if (processCode == outData.processCode || outData.processCode == 'Steam-Line') {
					for (var j in idcolorArray) {
						if ((outData.relateOrderIds + outData.discrColor + outData.color) == idcolorArray[j]) {
							if (wireCoil != null && wireCoil != '') {
								outData.wireCoil = wireCoil;
							}
						}
					}
				}
			});
		}

		// 蒸线的投入要改变：盘具
		if (wireCoil != null && wireCoil != '') {
			for (var secKey in me.inDataCacheMap['Steam-Line']) {
				Ext.Array.each(me.inDataCacheMap['Steam-Line'][secKey], function(inData, m) {
					for (var j in idcolorArray) {
						if ((inData.relateOrderIds + inData.discrColor + inData.color) == idcolorArray[j]) {
						    inData.wireCoil = wireCoil;
						}
					}
				});
			}
		}
		me.doLayoutTable(me.outGrid);
	},
	
	// 获取当前工序:选择按钮组
	getProcessRadio : function(){
		var me = this;
	    return $('span#chooseProcess input[name="chooseProcess"]', '#' + me.id);
	},
	
	// 获取当前工序:选择按钮第一个
	getFirstProcessRadio : function(){
		var me = this;
		return $('span#chooseProcess input[name="chooseProcess"]:first', '#' + me.id);
	},
	
	// 获取当前工序：选择按钮最后一个
	getLastProcessRadio : function(){
		var me = this;
		return $('span#chooseProcess input[name="chooseProcess"]:last', '#' + me.id);
	},
	
	// 获取当前工序：选取选中的
	getCheckedProcess : function(){
		var me = this;
	    return $('span#chooseProcess input[name="chooseProcess"]:checked', '#' + me.id);
	},
	
	// 获取当前工序
	getProcessCode : function(){
		var me = this;
	    return me.getCheckedProcess().val();
	},
	
	/**
	 * 获取分段下单长度：格式：1500+1000*2+500*3
	 */
	getNewOrderLength : function(expression) {
		var me = this;
		var total_ = 0;
		var specSplitRequire = me.analysisSplitRoleExpression(expression);
		Ext.Array.each(specSplitRequire, function(require, m) {
					total_ += Math.round(Number(require.length) * require.volNum);
				});
		return total_;
	},
	
	/**
	 * 判断是否需要蒸线工序 型号包含YJ的就返回true
	 */
	hasSteamLineProcess : function() {
		var me = this;
		var hasSteamLineProcess = false;
		Ext.Array.each(me.orderData, function(order, n) {
			if (order.productType.indexOf('YJ') >= 0) {
				hasSteamLineProcess = true;
				return false;
			}
		});
		return hasSteamLineProcess
	},
	
	/**
	 * 获取合并产品类型
	 */
	getReplaceColor : function(color) {
		if (color) {
			return color.replace('双色', '').replace('色', '').replace('/', '').replace('\\', '');
		} else {
			return '';
		}
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
	 * 判断是否是蒸线工序 型号包含YJ的就返回true
	 */
	isSteamLine : function(productType) {
		var me = this;
		var isSteamLine = false;
		if (productType.indexOf('YJ') >= 0) {
			isSteamLine = true;
		}
		return isSteamLine
	},
	
	/**
	 * 计算模芯模套 (旧,删)模芯：A、线芯+0.1；B、线芯+0.15；C、线芯+0.2； 模套：外径+厚度*2
	 * 模芯：
	 * A: 单丝直径＋0.1
	 * B: 单丝直径＊3+0.15
	 * C: 绝缘前外经+0.2
	 * 模套：
	 * A: 单丝直径+厚度＊2＋0.1
	 * B: 单丝直径＊3+厚度＊2+0.1
	 * C: 绝缘前外经+厚度＊2+0.1
	 */
	setMoldCoreSleeve : function(outData) {
		var me = this;
//		console.log('wiresStructure2=' + outData.wiresStructure2  +';wiresStructure='+ outData.wiresStructure
//		 +';dansizhijing='+ outData.dansizhijing +';frontOutsideValue='+ outData.frontOutsideValue
//		  +';standardPly='+ outData.standardPly)
//		  console.log('------------------------------')
		// 1、获取模芯近似值:A: 0.1,B: 0.15,C: 0.2
		var ws = outData.wiresStructure2;
		var coef = ws == 'A' ? 0.1 : (ws == 'B' ? 0.15 : (ws == 'C' ? 0.2 : 0));
		// 2、获取导体结构(备用，当单丝直径)
		var wiresStructure = outData.wiresStructure ? outData.wiresStructure.split('<br/>')[0] : 0;
		wiresStructure = wiresStructure.substring(wiresStructure.indexOf('/') + 1, wiresStructure.length);
		if (!outData.dansizhijing) {
			console.log('单丝直径查询不到哦')
		}
		// 3、获取单丝直径，没有用导体结构里面截取
		var dansizhijing = outData.dansizhijing ? outData.dansizhijing : wiresStructure; // 从原材料的单丝直径找不到就去导体结构的/后面的
		dansizhijing = dansizhijing.replace(/[a-zA-Z]/g, ''); // 去掉英文，主要数据录入有的带了单位
		// 4、根据规则处理单丝直径
		var frontOutsideValue = outData.frontOutsideValue ? outData.frontOutsideValue : dansizhijing;
		dansizhijing =  ws == 'A' ? dansizhijing : (ws == 'B' ? Number(dansizhijing)*3 : (ws == 'C' ? frontOutsideValue : dansizhijing));
		// 5、计算模芯模套:单丝直径+coef;单丝直径＋厚度*2+0.1
		var moldCore = Number(dansizhijing) + Number(coef);
		var moldSleeve = Number(dansizhijing) + Number(outData.standardPly) * 2 + Number(0.1);
		outData.moldCoreSleeve = (Math.ceil(moldCore * 100) / 100) + '/' + (Math.ceil(moldSleeve * 100) / 100);
	},
	
	/**
	 * 余量计算 四舍五入，取整数
	 */
	redundantAmount : function(productType, length, wiresStructure, processCode) {
		var yuliang = 0;
		if (length == 0) {
			return yuliang;
		} else if (processCode && processCode == 'wrapping_ymd') {
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
				} else if (wiresStructure == 'B') {  //TO_DO 规格是16平方的余量为10‰,暂时先统一以10‰处理.下周做进一步修改.
					if (length > 600) {
						yuliang = length * 0.010;
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
	 * 调整工序顺序
	 */
	changeProcessSeqShowWin : function() {
		var me = this;
		var data = [];
		var radios = me.getProcessRadio(); // 获取所有工序组列表
		var firstNextSection = ''; // 原第一个的下一道工段
		var lastNextSection = ''; // 原最后一个的下一道工段
		radios.each(function(i, radio) { // 遍历工序组中选中的工序
			data.push({
				NAME : $(radio).attr('id'),
				CODE : $(radio).val(),
				SECTION : $(radio).attr('section'),
				NEXTSECTION : $(radio).attr('nextsection'),
				DISABLE : $(radio).prop('disabled')
			});
			
			firstNextSection = (i == 0 ? $(radio).attr('nextsection') : firstNextSection); // 原第一个的下一道工段：赋值
			lastNextSection = (i == (radios.length -1)? $(radio).attr('nextsection') : lastNextSection); // 原最后一个的下一道工段：赋值
		
		});

		var win = new Ext.Window({
			title : '调整工序顺序',
			layout : 'anchor',
			padding : '10',
			width : document.body.scrollWidth / 3,
			height : document.body.scrollHeight / 2,
			firstNextSection : firstNextSection, // 原第一个的下一道工段
		    lastNextSection : lastNextSection, // 原最后一个的下一道工段
			items : [{
				xtype : 'grid',
				store : new Ext.data.Store({
					fields : ['NAME', 'CODE', 'SECTION', 'NEXTSECTION', {
						name : 'DISABLE',
						type : 'boolean'
					}],
					data : data
				}),
				columns : [{
					hidden : true,
					xtype : 'rownumberer'
				}, {
					text : '工序',
					dataIndex : 'NAME',
					flex : 1.6
				}],
				tbar : [{
					itemId : 'move', text : Oit.msg.pla.customerOrderItem.button.move,
					handler : function() { me.upMove(win); }
				}, { itemId : 'down', text : Oit.msg.pla.customerOrderItem.button.down, 
					handler : function() { me.downMove(win); }
				}]
			}],
			buttons : ['->', { text : Oit.btn.ok, handler : function() { me.changeProcessSeq(win); }
			}, { text : Oit.btn.close, handler : function() { win.close(); } }]
		});
		win.show();

	},

	/**
	 * 调整工序顺序
	 */
	changeProcessSeq : function(window) {
		var me = this;
		var grid = window.down('grid');
		var store = grid.getStore();
		var processList = [];
		for (var i = 0; i < store.getCount(); i++) {
			var record = store.getAt(i);
			processList.push(record.getData());
		}
		
		// 因为工序调整，工序的NEXTSECTION起了变化，此处手动调整NEXTSECTION的值
		Ext.Array.each(processList, function(d, i) { 
			if(i < processList.length -1){
				d.NEXTSECTION = window.firstNextSection;
			}else{
				d.NEXTSECTION = window.lastNextSection;
			}
		});
		
 		// 重新初始化
		me.setChooseProcessRadioGroup(processList, false);
		// 全选工序选择框:用于最后促发change事件，预设全选不能促发
		me.initRadioGroupChecked(); 

		window.close();
	},

	// 上移
	upMove : function(window) {
		var me = this;
		var grid = window.down('grid');
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			var insertIndex = store.indexOf(selection[0]);
			Ext.Array.each(selection, function(record, i) {
				var index = store.indexOf(record);
				if (insertIndex > 0) {
					store.insert(index - 1, record);
				}
			});
			grid.getView().refresh();
		}
	},
	// 下移
	downMove : function(window) {
		var me = this;
		var grid = window.down('grid');
		var store = grid.getStore();
		var selection = grid.getSelectionModel().getSelection();
		if (selection) {
			var insertIndex = store.indexOf(selection[0]);
			Ext.Array.each(selection, function(record, i) {
				var index = store.indexOf(record);
				if (insertIndex < store.getCount() - 1) {
					store.insert(index + 2, record);
				}
			});
			grid.getView().refresh();// 刷新行号
		}
	},
	
	/**
	 * 跳过该工序
	 */
	jumpProcess : function(btn) {
		var me = this;
		Ext.MessageBox.confirm('确认', '确认跳过下发该工序的生产单？', function(btn) {
			if (btn == 'yes') {
				// 1、改变当前所选的工序状态，已经选择了的移除
				var checked = me.getCheckedProcess(); 
				var processCode = checked.val();
				me.workOrderFlowObject.jumpProcess.push(processCode); // 记录跳过的工序
				checked.attr('disabled', true);
				
				// 保存跳过的工序
				var orderTmp = me.orderData[0];
				var productTypeSpec = orderTmp.productType + ' ' + orderTmp.productSpec;
				var craftsProcesses = {};
				craftsProcesses[productTypeSpec] = [{
							name : checked.attr('id'),
							code : processCode
						}];
				me.jumpProcessesMergedArray.push(craftsProcesses);
				
				// 2、默认设置每一组group的第一项为选中
				me.initRadioGroupChecked(true);
				// 3、最后一个也是只读关闭窗口：全部下发完成，更新1、订单已经全部下发，2、将上一道nextSection更改成2
				if (me.getLastProcessRadio().prop('disabled')) {

					// 3.1、更新订单已经全部下发
					var finishIds = [];
					var lastProcessCode = me.workOrderFlowObject.getLastProcessCode(processCode);
					Ext.Array.each(me.orderData, function(order, i) {
						var contractLength = order.contractLength; // 合同长度
						var orderProcessLength = me.workOrderFlowObject.getSenteddLength(order.id,
								lastProcessCode); // 该订单该工序下发的记录长度
						if (orderProcessLength >= contractLength) {
							finishIds.push(order.id)
						}
					});
					if (finishIds.length > 0) {
						Ext.Ajax.request({
							url : 'handSchedule/updateFinishedJY?finishIds=' + finishIds.join(),
							success : function(response) {
								// 刷新父页面
								Ext.ComponentQuery.query('handScheduleGrid')[0].getStore().load();
							}
						});
					}

					// 3.2、将上一道nextSection更改成2
					if (me.preWorkOrderNo != '') {
						Ext.Ajax.request({
							url : 'handSchedule/updateNextSection?workOrderNo=' + me.preWorkOrderNo
									+ '&nextSection=' + me.getNextSection()
						});
					}
					me.close();
				}
			}
		});
	},
	
	// 获取列表选择的id
	getSelectionGridIds : function(grid){
	    var me = this;
		var checkedRow = $('#' + grid.id + ' table input[type="checkbox"][class!="checkAll"]:checked', '#' + me.id);
		var selection = [];
		checkedRow.each(function(j){
		    selection.push($(this).attr('id'))
		});
		return selection;
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
	 * 验证表达式是否正确：格式：1500+1000*2+500*3
	 */
	validateExpression : function(expression, id, contractLength) {
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

		// ordersParams:订单产品的下发情况;sentLength:已经下发长度;leftLength:剩余长度;processCode:当前工序
		var processCode = me.getProcessCode();
		var sentLength = me.workOrderFlowObject.getSenteddLength(id, processCode);
		var leftLength = me.workOrderFlowObject.getUnSenteddLength(id, processCode);
		if (total_ > leftLength) {
			Ext.Msg.alert(Oit.msg.PROMPT, '设置分盘要求总长度超过了剩余长度！合同长度:' + contractLength + '，已发:' + sentLength + ';剩余:' + leftLength + ';分盘：' + expression + '=' + total_);
			return false;
		}
		return true;
	}

});
