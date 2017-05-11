Ext.define('bsmes.view.ManualManageEdit', {
			extend : 'Ext.window.Window',
			width : 350,
			layout : 'fit',
			modal : true,
			plain : true,
			title : '手动交货期管理',
			alias : 'widget.manualManageEdit',
			height : 300,
			initComponent : function() {
				var me = this;
				Ext.apply(me, {
							buttons : [{
										itemId : 'ok',
										text : '确定'
									}, {
										itemId : 'cancel',
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});

				me.items = [{
							xtype : 'form',
							bodyPadding : '15 15 15',
							defaultType : 'textfield',
							defaults : {
								labelAlign : 'center'
							},
							url:'manualManage/updateInformation',
							enctype : "multipart/form-data",
							items : [{
								xtype : 'textfield',
								name : 'id',
								hidden :true
							},{
					            xtype: 'datefield',
					            fieldLabel: '配套完成时间',
					            name : 'ptFinishTime',
					            id : 'ptFinishTime',
					            labelWidth : 90,
								anchor : '80%',
								format: 'Y-m-d',
								listeners : {
									change : function(combo, newValue, oldValue, eOpts) {
										var  clFinishTime1 = Ext.ComponentQuery.query('#clFinishTime')[0];
										var  bzFinishTime1 = Ext.ComponentQuery.query('#bzFinishTime')[0];
										var  htFinishTime1 = Ext.ComponentQuery.query('#htFinishTime')[0];
										if((bzFinishTime1.getValue()=="" || bzFinishTime1.getValue() == null)&&(clFinishTime1.getValue()=="" || clFinishTime1.getValue()==null)&&(htFinishTime1.getValue()=="" || htFinishTime1.getValue()==null)){
											var aa = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, 3), "Y-m-d");
											var bb = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, 6), "Y-m-d");
											var cc = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, 9), "Y-m-d");
											clFinishTime1.setValue(aa);
											bzFinishTime1.setValue(bb);
											htFinishTime1.setValue(cc);
										}
									}
								}
					        },{
					            xtype: 'datefield',
					            fieldLabel: '成缆完成时间',
					            name : 'clFinishTime',
					            labelWidth : 90,
					            id :'clFinishTime',
								anchor : '80%',
								format: 'Y-m-d',
								listeners : {
									change : function(combo, newValue, oldValue, eOpts) {
										var  ptFinishTime1 = Ext.ComponentQuery.query('#ptFinishTime')[0];
										var  bzFinishTime1 = Ext.ComponentQuery.query('#bzFinishTime')[0];
										var  htFinishTime1 = Ext.ComponentQuery.query('#htFinishTime')[0];
										if((ptFinishTime1.getValue()=="" || ptFinishTime1.getValue() == null)&&(bzFinishTime1.getValue()=="" || bzFinishTime1.getValue()==null)&&(htFinishTime1.getValue()=="" || htFinishTime1.getValue()==null)){
											var aa = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, -3), "Y-m-d");
											var bb = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, 3), "Y-m-d");
											var cc = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, 6), "Y-m-d");
											ptFinishTime1.setValue(aa);
											bzFinishTime1.setValue(bb);
											htFinishTime1.setValue(cc);
										}
									}
								}
					        },{
					            xtype: 'datefield',
					            fieldLabel: '编织完成时间',
					            name : 'bzFinishTime',
					            labelWidth : 90,
					            id : 'bzFinishTime',
								anchor : '80%',
								format: 'Y-m-d',
								listeners : {
									change : function(combo, newValue, oldValue, eOpts) {
										var  ptFinishTime1 = Ext.ComponentQuery.query('#ptFinishTime')[0];
										var  clFinishTime1 = Ext.ComponentQuery.query('#clFinishTime')[0];
										var  htFinishTime1 = Ext.ComponentQuery.query('#htFinishTime')[0];
										if((ptFinishTime1.getValue()=="" || ptFinishTime1.getValue() == null)&&(clFinishTime1.getValue()=="" || clFinishTime1.getValue()==null)&&(htFinishTime1.getValue()=="" || htFinishTime1.getValue()==null)){
											var aa = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, -6), "Y-m-d");
											var bb = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, -3), "Y-m-d");
											var cc = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, 3), "Y-m-d");
											ptFinishTime1.setValue(aa);
											clFinishTime1.setValue(bb);
											htFinishTime1.setValue(cc);
										}
									}
								}
					        },{
					            xtype: 'datefield',
					            fieldLabel: '护套完成时间',
					            name : 'htFinishTime',
					            id : 'htFinishTime',
					            labelWidth : 90,
								anchor : '80%',
								format: 'Y-m-d',
								listeners : {
									change : function(combo, newValue, oldValue, eOpts) {
										var  ptFinishTime1 = Ext.ComponentQuery.query('#ptFinishTime')[0];
										var  clFinishTime1 = Ext.ComponentQuery.query('#clFinishTime')[0];
										var  bzFinishTime1 = Ext.ComponentQuery.query('#bzFinishTime')[0];
										if((ptFinishTime1.getValue()=="" || ptFinishTime1.getValue() == null)&&(clFinishTime1.getValue()=="" || clFinishTime1.getValue()==null)&&(bzFinishTime1.getValue()=="" || bzFinishTime1.getValue()==null)){
											var aa = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, -9), "Y-m-d");
											var bb = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, -6), "Y-m-d");
											var cc = Ext.util.Format.date(Ext.Date.add(newValue, Ext.Date.DAY, -3), "Y-m-d");
											ptFinishTime1.setValue(aa);
											clFinishTime1.setValue(bb);
											bzFinishTime1.setValue(cc);
										}
									}
								}
					        },{
					            xtype: 'textfield',
					            fieldLabel: '备注',
					            labelWidth : 90,
					            name : 'remarks',
								anchor : '80%'
					        },{
					            xtype: 'datefield',
					            fieldLabel: '排产时间',
					            name : 'coordinateTime',
					            labelWidth : 90,
								anchor : '80%',
								format: 'Y-m-d'
					         },{
					            xtype: 'combobox',
					            fieldLabel: '信息来源',
					            name : 'infoSources',
					            labelWidth : 90,
								anchor : '80%',
							    queryMode: 'local',
							    displayField: 'infoSources',
							    valueField: 'infoSources',
							    store :  Ext.create('Ext.data.Store', {
							    	fields: ['infoSources'],
								    data : [
								        {"infoSources":"oa系统"},
								        {"infoSources":"杨总"},
								        {"infoSources":"唐总"},
								        {"infoSources":"吉林海"},
								        {"infoSources":"其他"},
								        {"infoSources":"排产表"},
								        {"infoSources":"沈宇"}
								    ]
								})
					        }]
						}],
				this.callParent(arguments);
			}
		});
		