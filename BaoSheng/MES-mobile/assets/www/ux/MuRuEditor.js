Ext.define('Ext.ux.MuRuEditor', {
	extend : 'Ext.Panel',
	xtype : ['murueditor'],
	requires : ['Ext.form.Panel', 'Ext.form.FieldSet', 'Ext.field.Number',
			'Ext.field.DatePicker', 'Ext.ux.field.DateTimePicker'],
	config : {
		id : 'muruEditor',
		width : '100%',// 200,
		height : '100%',
		title : '编辑',
		items : [{
					xtype : 'formpanel',
					id : 'muruFormPanel',
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
											xtype: 'selectfield',
											name: 'volume',
						                    label: '位置',
						                    options: [
						                        {text: '左',  value: '0'},
						                        {text: '右', value: '1'}
						                    ]
										}, {
											xtype : 'textareafield',
											name : 'remark',
											label : '备注'
										}]
							}]
				}, {
					xtype : 'toolbar',
					title : '母乳记录',
					docked : 'top',
					items : [{
								text : '返回',
								ui : 'back',
								handler : function() {
									Ext.getCmp('muruView').pop();
								}
							}, {
								xtype : 'spacer'
							}, {
								text : '删除',
								ui : 'action',
								handler : function() {
									Ext.Msg.confirm('删除记录', '是否真要删除当前记录?', function(res){
										if (res == 'yes') {
											var muruFormPanel = Ext .getCmp('muruFormPanel');
											var muruRecord = muruFormPanel.getRecord();
											if (muruRecord.data.itemhash != "") {
												del("weiyang", muruRecord.data.itemhash);
												muruStore.remove(muruRecord);
											}
											
											Ext.getCmp('muruView').pop();
										}
									}, this);
								}
							}, {
								text : '保存',
								ui : 'action',
								handler : function() {
									var muruFormPanel = Ext .getCmp('muruFormPanel');
									var muruRecord = muruFormPanel.getRecord();
									var newValues = muruFormPanel.getValues();

									muruRecord.set("date", Ext.Date.format(newValues.date, "Y-m-d H:i"));
									//muruRecord.set("time", Ext.Date.format(newValues.time, "H:i"));
									muruRecord.set("volume", newValues.volume);
									muruRecord.set("remark", newValues.remark);
									
									var now = new Date();
									var isNew = false;
									if (muruRecord.data.itemhash == "") {
										var noteId = (now.getTime()).toString() + (getRandomInt(0, 100)).toString();
										muruRecord.set("itemhash", noteId);
										isNew = true;
									}
									
									var sqlData = {};
									for(var i in muruRecord.data) {
										sqlData[i] = muruRecord.data[i]; 
									}
									delete sqlData.id;
									
									if(isNew) {
										insert("weiyang",sqlData);
										//Ext.getCmp('murulist').loadDate();
										muruStore.add(muruRecord);
									} else {
										update("weiyang",sqlData);
									}
									muruStore.sync();
									
									Ext.getCmp('muruView').pop();
								}
							}]
				}]
	}
});