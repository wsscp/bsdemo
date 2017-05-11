Ext.define('Ext.ux.NiaoBuEditor', {
	extend : 'Ext.Panel',
	xtype : ['niaobueditor'],
	requires : ['Ext.form.Panel', 'Ext.form.FieldSet', 'Ext.field.Number',
			'Ext.field.DatePicker', 'Ext.ux.field.DateTimePicker'],
	config : {
		id : 'niaobuEditor',
		width : '100%',// 200,
		height : '100%',
		title : '编辑',
		items : [{
					xtype : 'formpanel',
					id : 'niaobuFormPanel',
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
					                    label: '计量',
					                    options: [
					                        {text: '大便&小便',  value: '0'},
					                        {text: '小便', value: '1'},
					                        {text: '大便',  value: '2'}
					                    ]
					                    }, {
											xtype : 'textareafield',
											name : 'remark',
											label : '备注'
										}]
							}]
				}, {
					xtype : 'toolbar',
					title : '尿布记录',
					dock : 'top',
					items : [{
								text : '返回',
								ui : 'back',
								handler : function() {
									Ext.getCmp('niaobuView').pop();
								}
							}, {
								xtype : 'spacer'
							}, {
								text : '删除',
								ui : 'action',
								handler : function() {
									Ext.Msg.confirm('删除记录', '是否真要删除当前记录?', function(res){
										if (res == 'yes') {
											var niaobuFormPanel = Ext .getCmp('niaobuFormPanel');
											var niaobuRecord = niaobuFormPanel.getRecord();
											if (niaobuRecord.data.itemhash != "") {
												del("weiyang", niaobuRecord.data.itemhash);
												niaobuStore.remove(niaobuRecord);
											}
											
											Ext.getCmp('niaobuView').pop();
										}
									}, this);
								}
							}, {
								text : '保存',
								ui : 'action',
								handler : function() {
									var niaobuFormPanel = Ext .getCmp('niaobuFormPanel');
									var niaobuRecord = niaobuFormPanel.getRecord();
									var newValues = niaobuFormPanel.getValues();

									niaobuRecord.set("date", Ext.Date.format(newValues.date, "Y-m-d H:i"));
									//niaobuRecord.set("time", Ext.Date.format(newValues.time, "H:i"));
									niaobuRecord.set("volume", newValues.volume);
									niaobuRecord.set("remark", newValues.remark);
									
									var now = new Date();
									var isNew = false;
									if (niaobuRecord.data.itemhash == "") {
										var noteId = (now.getTime()).toString() + (getRandomInt(0, 100)).toString();
										niaobuRecord.set("itemhash", noteId);
										isNew = true;
									}
									
									var sqlData = {};
									for(var i in niaobuRecord.data) {
										sqlData[i] = niaobuRecord.data[i]; 
									}
									delete sqlData.id;
									
									if(isNew) {
										insert("weiyang",sqlData);
										//Ext.getCmp('niaobulist').loadDate();
										niaobuStore.add(niaobuRecord);
									} else {
										update("weiyang",sqlData);
									}
									niaobuStore.sync();
									
									Ext.getCmp('niaobuView').pop();
								}
							}]
				}]
	}
});