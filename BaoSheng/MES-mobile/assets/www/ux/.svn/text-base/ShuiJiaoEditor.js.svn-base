Ext.define('Ext.ux.ShuiJiaoEditor', {
	extend : 'Ext.Panel',
	xtype : ['shuijiaoeditor'],
	requires : ['Ext.form.Panel', 'Ext.form.FieldSet', 'Ext.field.Number',
			'Ext.field.DatePicker', 'Ext.ux.field.DateTimePicker'],
	config : {
		id : 'shuijiaoEditor',
		width : '100%',// 200,
		height : '100%',
		title : '编辑',
		items : [{
					xtype : 'formpanel',
					id : 'shuijiaoFormPanel',
					width : '100%',// 200,
					height : '100%',
					items : [{
								xtype : 'fieldset',
								title : '',
								instructions : '',
								items : [{
											xtype : 'datetimepickerfield',
											name : 'date',
											value : new Date(),
											//dateFormat: 'Y-m-d',
											dateTimeFormat : 'Y-m-d H:i',
											label : '日期',
						                    picker: {
						                    	useTitles: true,
						                        yearFrom: 1980,
						                        minuteInterval : 1,
						                        ampm : false,
						                        yearText: '年',
						                        monthText: '月',
						                        dayText: '日',
						                        hourText: '时',
						                        minuteText: '分',
						                        slotOrder: ['year', 'month', 'day', 'hour','minute']
						                    }
										}, {
											xtype : 'numberfield',
											name : 'volume',
											minValue : 0,
											maxValue : 1000,
											label : '时长'
										}, {
											xtype : 'textareafield',
											name : 'remark',
											label : '备注'
										}]
							}]
				}, {
					xtype : 'toolbar',
					title : '睡觉记录',
					dock : 'top',
					items : [{
								text : '返回',
								ui : 'back',
								handler : function() {
									Ext.getCmp('shuijiaoView').pop();
								}
							}, {
								xtype : 'spacer'
							}, {
								text : '删除',
								ui : 'action',
								handler : function() {
									Ext.Msg.confirm('删除记录', '是否真要删除当前记录?', function(res){
										if (res == 'yes') {
											var shuijiaoFormPanel = Ext .getCmp('shuijiaoFormPanel');
											var shuijiaoRecord = shuijiaoFormPanel.getRecord();
											if (shuijiaoRecord.data.itemhash != "") {
												del("weiyang", shuijiaoRecord.data.itemhash);
												shuijiaoStore.remove(shuijiaoRecord);
											}
											
											Ext.getCmp('shuijiaoView').pop();
										}
									}, this);
								}
							}, {
								text : '保存',
								ui : 'action',
								handler : function() {
									var shuijiaoFormPanel = Ext .getCmp('shuijiaoFormPanel');
									var shuijiaoRecord = shuijiaoFormPanel.getRecord();
									var newValues = shuijiaoFormPanel.getValues();

									shuijiaoRecord.set("date", Ext.Date.format(newValues.date, "Y-m-d H:i"));
									//shuijiaoRecord.set("time", Ext.Date.format(newValues.time, "H:i"));
									shuijiaoRecord.set("volume", newValues.volume);
									shuijiaoRecord.set("remark", newValues.remark);
									
									var now = new Date();
									var isNew = false;
									if (shuijiaoRecord.data.itemhash == "") {
										var noteId = (now.getTime()).toString() + (getRandomInt(0, 100)).toString();
										shuijiaoRecord.set("itemhash", noteId);
										isNew = true;
									}
									
									var sqlData = {};
									for(var i in shuijiaoRecord.data) {
										sqlData[i] = shuijiaoRecord.data[i]; 
									}
									delete sqlData.id;
									
									if(isNew) {
										insert("weiyang",sqlData);
										//Ext.getCmp('shuijiaolist').loadDate();
										shuijiaoStore.add(shuijiaoRecord);
									} else {
										update("weiyang",sqlData);
									}
									shuijiaoStore.sync();
									
									Ext.getCmp('shuijiaoView').pop();
								}
							}]
				}]
	}
});