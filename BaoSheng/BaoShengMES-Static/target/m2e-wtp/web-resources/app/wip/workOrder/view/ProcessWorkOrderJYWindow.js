/**
 * 工序选择生成生产单
 */
Ext.define('bsmes.view.ProcessWorkOrderJYWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.processWorkOrderJYWindow',
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
											text : '宝胜特种电缆有限公司绝缘工序生产单',
											style : {
												fontSize : '20px',
												margin : '0 0 10 ' + Math.ceil((document.body.scrollWidth - 582) / 2)
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
											xtype : 'panel',
											layout : 'vbox',
											width : Math.ceil((document.body.scrollWidth - 172) / 5 * 3),
											items : [{
														fieldLabel : '制单人',
														itemId : 'docMakerUserCode',
														xtype : 'displayfield',
														value : Ext.fly('userSessionKeyName').getAttribute('userName')
													}, {
														fieldLabel : '工序名称',
														itemId : 'processName',
														xtype : 'displayfield'
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
														title : '订单信息',
														xtype : 'fieldset',
														width : Math.floor((document.body.scrollWidth - 172) / 5 * 3),
														anchor : '100%',
														layout : 'vbox',
														items : [{
																	xtype : 'processWorkOrderGrid'
																}]
													}, {
														title : '物料需求信息',
														xtype : 'fieldset',
														itemId : 'matOAGridFieldset',
														hidden : true,
														width : Math.floor((document.body.scrollWidth - 172) / 5 * 3),
														anchor : '100%',
														layout : 'vbox',
														items : [{
																	xtype : 'matOAGrid'
																}]
													}, {
														title : '注意事项',
														xtype : 'fieldset',
														width : Math.floor((document.body.scrollWidth - 172) / 5 * 3),
														anchor : '100%',
														layout : 'vbox',
														padding : '10 10 10 15',
														items : [{
																	xtype : 'displayfield',
																	fieldStyle : {
																		color : 'red'
																	},
																	value : '1、请严格对照相应工艺文件生产，平均值不小于标称值；'
																}, {
																	xtype : 'displayfield',
																	fieldStyle : {
																		color : 'red'
																	},
																	value : '2、最薄点厚度应不小于标称值的90%-0.1mm；'
																}, {
																	xtype : 'displayfield',
																	fieldStyle : {
																		color : 'red'
																	},
																	value : '3、按生产工艺、材料定额组织生产，保证产品质量；'
																}, {
																	xtype : 'displayfield',
																	fieldStyle : {
																		color : 'red'
																	},
																	value : '4、严格按照顺序生产，确保每根绝缘线芯长度，做好分段标识，不得私自分头；'
																}, {
																	xtype : 'displayfield',
																	fieldStyle : {
																		color : 'red'
																	},
																	value : '5、认真填写相关记录，按时间完成生产工作；'
																}, {
																	xtype : 'displayfield',
																	fieldStyle : {
																		color : 'red'
																	},
																	value : '6、请按生产进度要求组织生产，保证电缆按时交货。'
																}]
													}]
										}, {
											xtype : 'panel',
											layout : 'vbox',
											margin : '0 0 0 5',
											width : Math.ceil((document.body.scrollWidth - 172) / 5 * 2),
											items : [{
														xtype : 'panel',
														layout : 'hbox',
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
																	value : new Date(),
																	allowBlank : false
																}, {
																	itemId : 'requireFinishDate',
																	fieldLabel : '要求完成日期',
																	xtype : 'datefield',
																	format : 'Y-m-d',
																	value : new Date(),
																	allowBlank : false
																}]
													}, {
														xtype : 'panel',
														layout : 'vbox',
														items : [{
															title : '制造半成品信息',
															xtype : 'fieldset',
															itemId : 'matOAGridFieldset',
															width : Math.floor((document.body.scrollWidth - 172) / 5
																	* 2),
															anchor : '100%',
															layout : 'vbox',
															items : [{
																		itemId : 'processItemGrid',
																		xtype : 'processItemGrid'
																	}, {
																		xtype : 'textareafield',
																		itemId : 'remark',
																		grow : true,
																		name : 'message',
																		fieldLabel : '备注',
																		anchor : '100%',
																		width : Math
																				.floor((document.body.scrollWidth - 172)
																						/ 5 * 2 - 25),
																		margin : '20 0 20 0'
																	}]
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
			 * 排生产单 : 渲染工序和物料需求的表格
			 * 
			 * @param me
			 *            当前controller
			 * @param win
			 *            当前弹出框
			 * @param result
			 *            根据订单产品ID和工序编码获取的工序列表
			 */
			drawingGridData : function(processCode, result){
				var me = this;
				var processWorkOrderGrid = me.query('processWorkOrderGrid')[0];
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
							} else if (record.inOrOut == 'OUT') { // 
								var item = dateMap[record.color];
								if (item) {
									var processIdArray = item.processIdArray;
									processIdArray.push(tmpRecord.processId + tmpRecord.orderItemId);
									item.unFinishedLength = item.unFinishedLength + record.unFinishedLength;
								} else {
									var processIdArray = [];
									processIdArray.push(tmpRecord.processId + tmpRecord.orderItemId);
									tmpRecord.processIdArray = processIdArray;
									dateMap[record.color] = tmpRecord;
								}
							}
						});
				for (var key in dateMap) {
					date.push(dateMap[key]);
				}
				for (var key in matOaDataMap) {
					matOaData.push(matOaDataMap[key]);
				}

				me.query('#processItemGrid')[0].getStore().loadData(date, false);

				me.query('matOAGrid')[0].getStore().loadData(matOaData, false);

				if (processCode != 'Respool') { // 如果是火花，物料不显示, 不允许编辑订单长度
					me.query('#matOAGridFieldset')[0].setVisible(true);
					me.query('#processItemGrid')[0].plugins = [rowEditing];
				} else {
					me.query('#matOAGridFieldset')[0].setVisible(false);
					me.query('#processItemGrid')[0].plugins = null;
				}

			}

		}
);
