/**
 * 工序选择生成生产单
 */
Ext.define('bsmes.view.ProcessWorkOrderCLWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.processWorkOrderCLWindow',
	width : document.body.scrollWidth - 100,
	height : document.body.scrollHeight - 100,
	modal : true,
	plain : true,
	autoScroll : true,
	processSection : null,
	processCode : null,
	orderItemIdArray : null,

	initComponent : function(){
		var me = this;

		me.items = [{
			xtype : 'panel',
			bodyPadding : "12 10 10",
			layout : 'vbox',
			items : [{
						xtype : 'container',
						layout : 'hbox',
						items : [{
									xtype : 'text',
									text : '宝胜特种电缆有限公司成缆工序生产单',
									style : {
										fontSize : '20px',
										margin : '0 0 10 ' + Math.ceil((document.body.scrollWidth - 477) / 2)
									}
								}, {
									itemId : 'chooseProcess',
									xtype : 'combobox',
									fieldLabel : '选择工序',
									width : 450,
									margin : '0 0 0 80',
									mode : 'local',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'code'],
												autoLoad : false,
												proxy : {
													type : 'rest',
													url : 'handSchedule/getOrderProcessBySection',
													extraParams : {
														processSection : me.processSection,
														orderItemIdArray : me.orderItemIdArray
													}
												}
											}),
									displayField : 'name',
									valueField : 'code',
									labelWidth : 80,
									listeners : {
										change : function(combobox, value, oldValue, fun){
											me.changeProcess(me.orderItemIdArray, value)
										}
									}
								}]
					}, {
						xtype : 'fieldset',
						width : document.body.scrollWidth - 140,
						minHeight : document.body.scrollHeight - 230,
						anchor : '100%',
						layout : 'hbox',
						padding : '10 10 10 15',
						items : [{
									xtype : 'container',
									layout : 'vbox',
									items : [{
												fieldLabel : '制单人',
												itemId : 'docMakerUserCode',
												xtype : 'displayfield',
												value : 'XXXX'
											}, {
												fieldLabel : '工序名称',
												xtype : 'displayfield',
												value : '挤出单层'
											}, {
												itemId : 'equipCodes',
												xtype : 'combobox',
												multiSelect : true,
												allowBlank : false,
												fieldLabel : '选择机台',
												width : 450,
												store : Ext.create('Ext.data.Store', {
															fields : ['code', 'name']
														}),
												displayField : 'name',
												valueField : 'code',
												labelWidth : 80
											}, {
												xtype : 'processWorkOrderGrid'
											}, {
												xtype : 'matOAGrid'
											}]
								}, {
									xtype : 'container',
									layout : 'vbox',
									margin : '0 0 0 5',
									items : [{
										xtype : 'container',
										layout : 'hbox',
										margin : '0 0 0 5',
										items : [{
													xtype : 'container',
													layout : 'vbox',
													margin : '0 0 0 5',
													items : [{
																itemId : 'receiverUserCode',
																fieldLabel : '接收人',
																xtype : 'textfield',
																allowBlank : false
															}, {
																itemId : 'releaseDate',
																fieldLabel : '下达日期',
																xtype : 'datefield',
																format : 'Y-m-d',
																allowBlank : false
															}, {
																itemId : 'requireFinishDate',
																fieldLabel : '要求完成日期',
																xtype : 'datefield',
																format : 'Y-m-d',
																allowBlank : false
															}]
												}, {
													xtype : 'container',
													layout : 'vbox',
													margin : '0 0 0 5',
													items : [{
														itemId : 'noticeButton',
														text : '保存生产单',
														xtype : 'button',
														scale : 'large',
														handler : function(){
															// this.setVisible(false);
															// var alerttext =
															// me.query('#alerttext')[0];
															// alerttext.setVisible(true);

															var orderGrid = me.query('processWorkOrderGrid')[0];

															// 
															var docMakerUserCode = me.query('#docMakerUserCode')[0], receiverUserCode = me
																	.query('#receiverUserCode')[0], releaseDate = me
																	.query('#releaseDate')[0], requireFinishDate = me
																	.query('#requireFinishDate')[0], equipCodes = me
																	.query('#equipCodes')[0], processName = me
																	.query('#processName')[0];

															if (!releaseDate.validate()
																	|| !requireFinishDate.validate()
																	|| !receiverUserCode.validate()
																	|| !equipCodes.validate()) {
																Ext.Msg.alert(Oit.msg.PROMPT, '请填写完表单内容！');
																return;
															}

															Ext.Ajax.request({
																		url : 'handSchedule/mergeCustomerOrderItem',
																		method : 'POST',
																		params : {
																			'docMakerUserCode' : docMakerUserCode
																					.getValue(),
																			'receiverUserCode' : receiverUserCode
																					.getValue(),
																			'releaseDate' : releaseDate.rawValue
																					+ ' 00:00:00',
																			'requireFinishDate' : requireFinishDate.rawValue
																					+ ' 00:00:00',
																			'equipCodes' : equipCodes.getValue(),
																			'processName' : processName.getValue(),
																			'processJsonData' : Ext
																					.encode(orderGrid.result)
																		},
																		success : function(response){
																			Ext.Msg.alert(Oit.msg.PROMPT, '保存成功！');
																			console.log('添加通知单成功');
																		},
																		failure : function(response, action){
																			Ext.Msg.alert(Oit.msg.PROMPT, '保存失败！');
																		}
																	}
															);

														}
													}, {
														itemId : 'alerttext',
														width : 170,
														html : '<span style="color:#20DF76;font-size:20px;font-weight: bold;margin-left:'
																+ ((document.body.scrollWidth - 130) / 5 * 2 - 400)
																+ 'px;">已生成</span>',
														xtype : 'panel',
														hidden : true
													}]
												}]
									}, {
										xtype : 'container',
										layout : 'vbox',
										margin : '0 0 0 5',
										items : [{
													itemId : 'processItemGrid',
													xtype : 'processItemGrid'
												}]
									}]
								}]

					}]
		}];

		Ext.apply(me, {
					buttons : ['->', {
								itemId : 'ok',
								text : Oit.btn.ok
							}, {
								itemId : 'cancel',
								text : Oit.btn.cancel,
								scope : me,
								handler : me.close
							}]
				});

		this.callParent(arguments);
	},

	/**
	 * 监听工序变更方法： 改变工序变更页面
	 * 
	 * @param processCode
	 *            工序编码
	 * @param orderItemIdArray
	 *            订单产品ID
	 */
	changeProcess : function(orderItemIdArray, processCode){
		var me = this;
		// 获取成功取第一个工序CODE的工序明细
		Ext.Ajax.request({
					url : 'handSchedule/getOrderProcessByCode',
					params : {
						processCode : processCode,
						orderItemIdArray : orderItemIdArray
					},
					success : function(response){
						var result = Ext.decode(response.responseText);
						me.drawingGridData(result);
						me.getEquip(processCode, orderItemIdArray);
					}
				});

	},

	/**
	 * 排生产单: 显示工序使用可选设备
	 * 
	 * @param win
	 *            当前弹出框
	 * @param processCode
	 *            工序编码
	 * @param orderItemIdArray
	 *            订单产品ID
	 * 
	 */
	getEquip : function(processCode, orderItemIdArray){
		var me = this;
		var equipCodes = me.query('#equipCodes')[0]
		if (processCode == 'Extrusion-Single') { // 挤出单层为单选，火花为多选
			equipCodes.multiSelect = false;
		} else {
			equipCodes.multiSelect = true;
		}
		var store = equipCodes.getStore();
		store.setProxy({
					type : 'rest',
					url : 'handSchedule/getProcessEquip',
					extraParams : {
						processCode : processCode,
						orderItemIdArray : orderItemIdArray
					}
				});
		store.load({
					callback : function(records, operation, success){
						if (records.length > 0) { // 选择第一个 暂时
							var record = records[0];
							var code = record.getData().code;
							equipCodes.select(code);
						}
					}
				});
	},

	/**
	 * 排生产单 : 渲染工序和物料需求的表格
	 * 
	 * @param me
	 *            当前controller
	 * @param win
	 *            当前弹出框
	 * @param result
	 *            根据订单产品ID和工序编码获取的工序列表
	 */
	drawingGridData : function(result){
		var processWorkOrderGrid = Ext.ComponentQuery.query('processWorkOrderGrid')[0];
		processWorkOrderGrid.result = result; // 保存到页面临时缓存

		// data存放工序列表record，matOaData存放物料需求列表record，Map为临时缓存
		var date = [], dateMap = {}, matOaData = [], matOaDataMap = {};

		Ext.Array.each(result, function(record, i){
					var tmpRecord = Ext.clone(record); // 存放新对象，防止提交对象的变更
					if (record.inOrOut == 'IN') { // 物料需求
						var item = matOaDataMap[record.halfProductCode];
						if (item) {
							item.unFinishedLength = item.unFinishedLength + record.unFinishedLength;
						} else {
							matOaDataMap[record.halfProductCode] = tmpRecord;
						}
					} else if (record.inOrOut == 'OUT') { // 成揽不分组
						var item = dateMap[record.processId];
						if (item) {
							item.unFinishedLength = item.unFinishedLength + record.unFinishedLength;
						} else {
							dateMap[record.processId] = tmpRecord;
						}
					}
				});
		for (var key in dateMap) {
			date.push(dateMap[key])
		}
		for (var key in matOaDataMap) {
			matOaData.push(matOaDataMap[key])
		}

		Ext.ComponentQuery.query('#processItemGrid')[0].getStore().loadData(date, false);

		Ext.ComponentQuery.query('matOAGrid')[0].getStore().loadData(matOaData, false);
	}

}
);
