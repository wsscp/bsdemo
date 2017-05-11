/**
 * 工序选择生成生产单
 */
Ext.define('bsmes.view.ShowProcessWorkOrderCLWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.showProcessWorkOrderCLWindow',
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
	
	orderGrid:{
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
			    if (json.splitLengthRole && json.splitLengthRole != '') {
					value += '<br/>' + json.splitLengthRole;
				}
				return value;
			}}, 
			{dataIndex : 'COLOR',text : '颜色字码'},
			{dataIndex : 'TASKLENGTH',text : '成缆长度', renderer : function(value, record){
				var json = Ext.decode(record.OUTATTRDESC);
			    if (json.splitLengthRoleWithYuliang && json.splitLengthRoleWithYuliang != '') {
					value += '<br/>' + json.splitLengthRoleWithYuliang;
				}
				return value;
			}}, 
			{dataIndex : 'OUTATTRDESC',text : '导体结构', renderer : function(value, record){
				var json = Ext.decode(value);
				return (typeof(json.conductorStruct) == "undefined" ? '' : json.conductorStruct);
			}}, 
			{dataIndex : 'OUTATTRDESC',text : '标称厚度', renderer : function(value, record){
				var json = Ext.decode(value);
				return (typeof(json.standardPly) == "undefined" ? '' : json.standardPly);
			}}, 
			{dataIndex : 'OUTATTRDESC',text : '最大值', renderer : function(value, record){
				var json = Ext.decode(value);
				return (typeof(json.standardMaxPly) == "undefined" ? '' : json.standardMaxPly);
			}}, 
			{dataIndex : 'OUTATTRDESC',text : '最小值', renderer : function(value, record){
				var json = Ext.decode(value);
				return (typeof(json.standardMinPly) == "undefined" ? '' : json.standardMinPly);
			}}, 
			{dataIndex : 'OUTATTRDESC',text : '外径', renderer : function(value, record){
				var json = Ext.decode(value);
				return (typeof(json.outsideValue) == "undefined" ? '' : json.outsideValue);
			}}, 
			{dataIndex : 'WIRECOIL',text : '收盘要求'},
			{dataIndex : 'OUTMATDESC',text : '成缆/绕包要求', maxWidth : (document.body.scrollWidth/3)
		}]
	}, // 云母绕包的订单列表
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
			title : '<div style="float:left;">' + me.title + '</div><span style="font-size: 16px;margin-left: -120px;">宝 胜 特 种 电 缆 有 限 公 司 成 缆 工 序 生 产 单</span>',
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
	    var e = new Date();
				
		// 2、是否急件、是否出口、是否陈线
		var jjckcx = (workOrder.isDispatch ? '急件，' : '') + (workOrder.isAbroad ? '出口，' : '');
				jjckcx = ((jjckcx.substring(jjckcx.length - 1) == '，') ? jjckcx.substring(0, jjckcx.length - 1) : jjckcx);
				jjckcx = '<b style="color:red;margin-left: 10px;">' + jjckcx + '</b>';
				
		// 3、注意事项
		var noticeHtml = '<b>1、成缆最外层绞向为右向，相邻层绞向相反；</b><br/>' +
				'<b>2、绝缘线芯采用数字标志时，由内层到外层从1开始按自然顺序顺时针方向排列；</b><br/>' +
				'<b>3、绿/黄双色绝缘线芯应放置在缆芯最外层；</b><br/>' +
				'<b>4、成缆绕包的重叠率为带宽的15%-25%；</b><br/>' +
				'<b>5、产品加工时应严格遵守机台的操作规程；</b><br/>' +
				'<b>6、产品加工结束后，应挂上制造卡，注明型号、规格、制造长度、制造日期、制造人。</b>';
		
		// 4、通过方法获取主要的几块区域的html
		var orderFieldSet = me.getFieldSet('orderTable', '订单信息', '', false);
		var inFieldSet = me.getFieldSet('inTable', '物料需求信息', '', false);
		var userComment = me.getFieldSet('', '备注', '<textarea id="userComment" readonly="true">' + workOrder.userComment + '</textarea>', false);
		var noticeFieldset = me.getFieldSet('noticeFieldset', '注意事项', noticeHtml, false);
		
		// 5、整体布局拼接
		// 5.1、topDiv：最上层按钮及工序选择
		// 5.2、一个大的fieldset包含了一个2行2列的大table：leftTd1、rightTd1、leftTd2、rightTd2
		// 5.3、每个单元格基本都是一个n行1列的table
		var topDiv = '<div id="workOrderFlowBox" style="padding: 0px 0px 4px 3px;"></div>';
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
				'<tr><td><span class="label">选择机台：</span>' + workOrder.equipName + '</td></tr>' +
				'</table>';
		var rightTd1 = '<ul>' +
					'<li style="width: 70%;">' +
						'<table class="mesTableLayout" width="100%">' +
						'<tr><td><span class="label">接收人：</span>' + workOrder.receiverUserCode + '</td></tr>' +
						'<tr><td>' + jjckcx + '</td></tr>' +
						'<tr><td><span class="label">要求完成日期：</span>' + ((workOrder.requireFinishDate instanceof Date) ? Ext.util.Format.date(workOrder.requireFinishDate, "Y-m-d") : Ext.util.Format.date(new Date(workOrder.requireFinishDate), "Y-m-d")) + '</td></tr>' +
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
		$(document, '#' + me.id).off("click", 'button.hasEvent')
		
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
		me.drawingWorkOrderFlow(result.workOrderArray); // 渲染生产单流程
		me.drawingGridData(processCode, result);

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
	 * 排生产单 : 渲染工序和物料需求的表格
	 * 
	 * @param me 当前controller
	 * @param win 当前弹出框
	 * @param result 根据订单产品ID和工序编码获取的工序列表
	 */
	drawingGridData : function(processCode, result) {
		var me = this;

		// data存放工序列表record，matOaData存放物料需求列表record，Map为临时缓存
		var matOaData = [], matOaDataMap = {};

		Ext.Array.each(result.matList, function(record, i) {
					var item = matOaDataMap[record.MATCODE + record.COLOR + record.QUANTITY];
					if (item) {
						item.UNFINISHEDLENGTH = item.UNFINISHEDLENGTH + record.UNFINISHEDLENGTH;
					} else {
						matOaDataMap[record.MATCODE + record.COLOR + record.QUANTITY] = record;
					}
				});

		for (var key in matOaDataMap) {
			matOaData.push(matOaDataMap[key]);
		}

		// 5.2、将三个表格里面的数据重新赋值并重新加载html
		me.orderGrid.datas = result.orderItemList;
		me.inGrid.datas = matOaData;
		me.doLayoutTable(me.orderGrid);
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
