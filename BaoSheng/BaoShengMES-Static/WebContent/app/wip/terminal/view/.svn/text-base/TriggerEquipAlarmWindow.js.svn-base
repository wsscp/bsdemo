Ext.define("bsmes.view.TriggerEquipAlarmWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.triggerEquipAlarmWindow',
	width : document.body.scrollWidth - 100,
	height : document.body.scrollHeight - 100,
	modal : true,
	plain : true,
	layout : 'fit',
	eventTypeCache : new Ext.util.HashMap(),
	eventTypeDecCache : new Ext.util.HashMap(),
	title : '选择警报种类',
	processCode : '',
	listeners : {
		'afterrender' : function(e, eOpts) {
			var e = Ext.ComponentQuery.query('#director')[0];
			e.setVisible(false);
		}
	},
	initComponent : function() {
		var me = this;

		me.items = [{
					xtype : 'form',
					defaultType : 'textfield',
					bodyPadding : '5 50',
					autoScroll : true,
					fieldDefaults : { // 设置field的样式
						msgTarget : 'side', // 错误信息提示位置
						labelWidth : 150, // 设置label的宽度
						labelStyle : 'font-size:30px; margin-top:20px;'
					},
					url : '/bsmes/wip/terminal/triggerEquipAlarm',
					items : [{
								fieldLabel : '员工号',
								name : 'operator',
								allowBlank : false,
								margin : '30 30 30 30',
								fieldCls : 'x-panel-body-default',
								fieldStyle : 'font-size:30px;',
								height : 60,
								width : document.body.scrollWidth - 300,
								plugins : {
									ptype : 'virtualKeyBoard'
								}
							}, {
								fieldLabel : '设备号',
								name : 'equipCode',
								readOnly : true,
								allowBlank : false,
								margin : '30 30 30 30',
								fieldCls : 'x-panel-body-default',
								fieldStyle : 'font-size:30px;',
								height : 60,
								width : document.body.scrollWidth - 300
							}, {
								fieldLabel : '报警种类',
								itemId : 'eventTypeCodeComb',
								name : 'eventTypeCode',
								xtype : 'radiogroup',
								columns : 2, // 一行显示几个
								allowBlank : false,
								layout : {
									autoFlex : true
								}, // 设置自动间距样式为false
								// defaults: { margin: '0 20 0 0' }, // 设置样式
								items : [],
								listeners : {
									render : function(view, opt) {
										me.initEventTypeComb(me, this, view, opt);
									}
								},
								margin : '30 30 30 30'
							}, {
								fieldLabel : '详细说明',
								itemId : "eventTypeCodeDescComb",
								name : 'eventTypeCodeDesc',
								xtype : 'radiogroup',
								columns : 3, // 一行显示几个
								allowBlank : false,
								layout : {
									autoFlex : true
								}, // 设置自动间距样式为false
								items : [],
								margin : '30 30 30 30'
							}, {
								fieldLabel : '负责人',
								itemId : 'director',
								name : 'director',
								xtype : 'radiogroup',
								columns : 3, // 一行显示几个
								allowBlank : false,
								layout : {
									autoFlex : true
								}, // 设置自动间距样式为false
								items : [{
											boxLabel : '电工',
											name : 'director',
											inputValue : 'dg',
											boxLabelCls : 'x-boxlabel-size-30 x-boxlabel-left-10',
											style : 'margin-top:7px;',
											fieldStyle : 'margin-top:10px;'
										}, {
											boxLabel : '钳工',
											name : 'director',
											inputValue : 'qg',
											boxLabelCls : 'x-boxlabel-size-30 x-boxlabel-left-10',
											style : 'margin-top:7px;',
											fieldStyle : 'margin-top:10px;'
										}, {
											boxLabel : '其他',
											name : 'director',
											inputValue : 'qt',
											boxLabelCls : 'x-boxlabel-size-30 x-boxlabel-left-10',
											style : 'margin-top:7px;',
											fieldStyle : 'margin-top:10px;'
										}],
								margin : '30 30 30 30'
							}]
				}];

		Ext.apply(me, {
					buttons : ['->', {
						itemId : 'ok',
						text : Oit.btn.ok,
						handler : function() {
							var form = this.up('window').down('form').getForm();
							if (form.isValid()) {
								Ext.Msg.wait('处理中，请稍后...', '提示');
								form.submit({
											params : {
												eventTypeCode : Ext.ComponentQuery.query('#eventTypeCodeComb')[0]
														.getValue(),
												eventTypeCodeDesc : Ext.ComponentQuery.query('#eventTypeCodeDescComb')[0]
														.getValue()
											},
											success : function(form, action) {
												Ext.Msg.alert('提示', '成功触发警报', function() {
															Ext.Msg.hide(); // 隐藏进度条
														});
												me.close();
											},
											failure : function(form, action) {
												Ext.Msg.hide(); // 隐藏进度条
												var result = Ext.decode(action.response.responseText);
												Ext.Msg.alert(Oit.msg.WARN, result.message);
											}
										});
							}
						}
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
	 * 初始化报警种类radioGroup组件
	 * 
	 * @param me 整个弹出框
	 * @param group radioGroup组件组
	 * @param view radio组件
	 * @param opt radio组件check值
	 */
	initEventTypeComb : function(me, group, view, opt) {
		// 从jspMap缓存中取值，没有请求后台取值，然后保存如缓存Map
		// 1、判断缓存Map是否存在对应值
		var items = me.eventTypeCache.get('eventTypeItems');
		if (typeof(items) != 'undefined') {
			// 1.1、items存在，直接重置items区域
			// 不能直接将addAll(items)，会报js错误，故重新循环创建Radio
			for (var i in items) {
				group.items.add(new Ext.form.Radio({
							boxLabel : items[i].boxLabel,
							inputValue : items[i].inputValue,
							name : items[i].name,
							boxLabelCls : items[i].boxLabelCls,
							fieldStyle : items[i].fieldStyle,
							listeners : {
								change : function(view, opt) {
									// 初始化级联group组件
									me.initEventTypeDesComb(me, view, opt);
									if (opt) {
										var director = Ext.ComponentQuery.query('#director')[0];
										if (view.inputValue == 'EQIP') {
											director.setVisible(true);
											director.setDisabled(false);
										} else {
											director.setVisible(false);
											director.setDisabled(true);
										}
									}
								}
							}
						}));
			}
			group.doLayout();
		} else {
			// 1.2、items不存在，请求后台获取items
			Ext.Ajax.request({
						url : '/bsmes/wip/terminal/equipAlarmType',// 请求路径，需要手动输入指定
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var items = [];
							for (var j = 0; j < result.length; j++) {
								items[j] = new Ext.form.Radio({
											boxLabel : result[j].name,
											inputValue : result[j].code,
											name : 'eventTypeCode', // 这个是后台接收的表单域，这里的checkboxgroup都使用同一个name
											boxLabelCls : 'x-boxlabel-size-30 x-boxlabel-left-10',
											style : 'margin-top:7px;',
											fieldStyle : 'margin-top:10px;',
											listeners : {
												change : function(view, opt) {
													// 初始化级联group组件
													me.initEventTypeDesComb(me, view, opt);
													if (opt) {
														var director = Ext.ComponentQuery.query('#director')[0];
														if (view.inputValue == 'EQIP') {
															director.setVisible(true);
															director.setDisabled(false);
														} else {
															director.setVisible(false);
															director.setDisabled(true);
														}
													}
												}
											}
										});
							}
							group.items.addAll(items);
							group.doLayout();
							// 1.2.2、将其保存如缓存Map
							me.eventTypeCache.add('eventTypeItems', items);
						}
					})
		}
	},

	/**
	 * 初始化详细说明radioGroup组件
	 * 
	 * @param me 整个弹出框
	 * @param view radio组件
	 * @param opt radio组件check值
	 */
	initEventTypeDesComb : function(me, view, opt) {
		var me = this;
		// 修改设备工序信息：原来是根据当前加工生产的获取，现在从设备信息里面获取
		var processCode = me.processCode;
		if(me.processCode.indexOf(',')>0){
			processCode = me.processCode.substring(0, me.processCode.indexOf(','));
		}
		if (processCode == '') {
			processCode = Ext.fly('equipInfo').getAttribute('processCode');
			if(processCode.indexOf(',')>0){
				processCode = processCode.substring(0, processCode.indexOf(','));
			}
		}
		if (opt) {
			// 从jspMap缓存中取值，没有请求后台取值，然后保存如缓存Map
			// 1、清空区域items
			var group = Ext.ComponentQuery.query('#eventTypeCodeDescComb')[0];
			group.removeAll(true); // 清空原有的items
			// 2、判断缓存Map是否存在对应值
			var items = me.eventTypeDecCache.get(view.inputValue);
			// console.log(items);
			if (typeof(items) != 'undefined') {
				// 2.1、items存在，直接重置items区域
				// 不能直接将addAll(items)，会报js错误，故重新循环创建Radio
				for (var i in items) {
					group.items.add(new Ext.form.Radio({
								boxLabel : items[i].boxLabel,
								inputValue : items[i].inputValue,
								name : items[i].name,
								marksVal : items[i].marksVal,
								fieldStyle : items[i].fieldStyle,
								boxLabelCls : items[i].boxLabelCls
							}));
				}
				group.doLayout();
			} else {
				// 2.2、items不存在，请求后台获取items
				Ext.Ajax.request({
							url : '/bsmes/wip/terminal/equipAlarmTypeDesc/' + view.inputValue + '/', // 请求路径，需要手动输入指定
							success : function(response) {
								items = [];
								var result = Ext.decode(response.responseText);
								for (var j = 0; j < result.length; j++) {
									// 设备异常事件明细根据备注里面的工序经行区分显示
									if (view.inputValue == 'EQIP'
											&& (',' + result[j].marks + ',').indexOf(',' + processCode + ',') < 0) {
										continue;
									}
									items.push(new Ext.form.Radio({
												boxLabel : result[j].name,
												inputValue : result[j].code,
												marksVal : '',
												name : 'eventTypeCodeDesc', // 这个是后台接收的表单域，这里的checkboxgroup都使用同一个name
												style : 'margin-top:7px;',
												fieldStyle : 'margin-top:10px;',
												boxLabelCls : 'x-boxlabel-size-30 x-boxlabel-left-10'
											}));
								}
								// 2.2.1、充值items区域
								group.items.addAll(items);
								group.doLayout();
								// 2.2.2、将其保存如缓存Map
								me.eventTypeDecCache.add(view.inputValue, items);
							}
						})
			}
		}
	},
	isNullObj : function(obj) {
		for (var i in obj) {
			if (obj.hasOwnProperty(i)) {
				return false;
			}
		}
		return true;
	}

});
