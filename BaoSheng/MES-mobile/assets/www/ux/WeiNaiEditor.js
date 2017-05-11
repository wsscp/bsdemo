Ext.define('Ext.ux.WeiNaiEditor', {
	extend : 'Ext.Panel',
	xtype : ['weinaieditor'],
	requires : ['Ext.form.Panel', 'Ext.form.FieldSet', 'Ext.field.Number',
			'Ext.field.DatePicker', 'Ext.ux.field.DateTimePicker'],
	config : {
		id : 'weinaiEditor',
		width : '100%',// 200,
		height : '100%',
		title : '编辑',
		items : [{
					xtype : 'formpanel',
					id : 'weinaiFormPanel',
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
											label : '计量'
										}, {
											xtype : 'textareafield',
											name : 'remark',
											label : '备注'
										}]
							}]
				}, {
					xtype : 'toolbar',
					title : '奶瓶记录',
					dock : 'top',
					items : [{
								text : '返回',
								ui : 'back',
								handler : function() {
									Ext.getCmp('weinaiView').pop();
								}
							}, {
								xtype : 'spacer'
							}, {
								text : '删除',
								ui : 'action',
								handler : function() {
									Ext.Msg.confirm('删除记录', '是否真要删除当前记录?', function(res){
										if (res == 'yes') {
											var weinaiFormPanel = Ext .getCmp('weinaiFormPanel');
											var weinaiRecord = weinaiFormPanel.getRecord();
											if (weinaiRecord.data.itemhash != "") {
												del("weiyang", weinaiRecord.data.itemhash);
												weinaiStore.remove(weinaiRecord);
											}
											
											Ext.getCmp('weinaiView').pop();
										}
									}, this);
								}
							}, {
								text : '保存',
								ui : 'action',
								handler : function() {
									var weinaiFormPanel = Ext .getCmp('weinaiFormPanel');
									var weinaiRecord = weinaiFormPanel.getRecord();
									var newValues = weinaiFormPanel.getValues();

									weinaiRecord.set("date", Ext.Date.format(newValues.date, "Y-m-d H:i"));
									//weinaiRecord.set("time", Ext.Date.format(newValues.time, "H:i"));
									weinaiRecord.set("volume", newValues.volume);
									weinaiRecord.set("remark", newValues.remark);
									
									var now = new Date();
									var isNew = false;
									if (weinaiRecord.data.itemhash == "") {
										var noteId = (now.getTime()).toString() + (getRandomInt(0, 100)).toString();
										weinaiRecord.set("itemhash", noteId);
										isNew = true;
									}
									
									var sqlData = {};
									for(var i in weinaiRecord.data) {
										sqlData[i] = weinaiRecord.data[i]; 
									}
									delete sqlData.id;
									
									if(isNew) {
										insert("weiyang",sqlData);
										//Ext.getCmp('weinailist').loadDate();
										weinaiStore.add(weinaiRecord);
									} else {
										update("weiyang",sqlData);
									}
									weinaiStore.sync();
									
									Ext.getCmp('weinaiView').pop();
								}
							}]
				}]
	}
});