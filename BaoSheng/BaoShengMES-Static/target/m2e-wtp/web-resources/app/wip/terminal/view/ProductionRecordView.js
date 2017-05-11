Ext.define("bsmes.view.ProductionRecordView", {
	extend : 'Ext.window.Window',
	itemId :'productionRecordView',
	alias : 'widget.productionRecordView',
	width:document.body.scrollWidth-150,
    height:document.body.scrollHeight-100,
    title : '生产记录表',
	modal: true,
	plain : true,
	closeAction:'destory',
	initComponent:function(){
		var me = this;
		me.items = [{
			xtype: 'panel',
			items : [{
				fieldLabel:'机台',
				xtype:'displayfield',
				labelWidth : 45,
				name:'equipInfo',
				value : Ext.fly('equipInfo').getAttribute('equipAlias')
			},{
				itemId:'shifts',
				fieldLabel:'班次',
				labelWidth : 45,
				name : 'shifts',
				xtype : 'radiogroup',
				vertical : true,
				defaults : { // 设置样式
					width : 80
					// 设置label的宽度
				},
				items : [{
					boxLabel : '早班',
					name : 'shifts',
					inputValue : '早班',
					checked : true
				}, {
					boxLabel : '中班',
					name : 'shifts',
					inputValue : '中班'
				}, {
					boxLabel : '晚班',
					name : 'shifts',
					inputValue : '晚班'
				}]
			},{
				xtype : 'form',
				layout:'hbox',
				height: 30,
				items: [{
			        fieldLabel: '挡班',
			        xtype:'combobox',
			        name: 'dbName',
			        id : 'dbId',
			        labelWidth:60,
			        height : 25,
			        queryMode: 'local',
			        displayField:'userName',
			        valueField: 'userName',
			        allowBlank:false,
			        store:new Ext.data.Store({
			        	fields:['userName','userCode'],
			        	autoLoad:false,
			        	proxy:{
			        		type: 'rest',
			        		url:'getUsers?role=DB'
			        	}
			        }),
			        listeners : {
			        	'change' : function(){
			        		Ext.getCmp("formId").removeAll();
			        		var a = Ext.ComponentQuery.query('productionRecordView combobox')[0].getRawValue();
			        		console.log(a);
			        		var form = Ext.getCmp("formId");
			        		var component = [{
								xtype:'label',
								text : a,
								margin : '2 0 2 0',
								padding : '0 100 0 80',
								width:250
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							}];
			        		var formPanel = Ext.getCmp("formId").add(component);
			        	}
			        }
			    },{
			        fieldLabel: '副挡班',
			        xtype:'combobox',
			        height : 25,
			        labelWidth:60,
			        name: 'fdbName',
			        id : 'fdbId',
			        queryMode: 'local',
			        displayField:'userName',
			        valueField: 'userName',
			        allowBlank:false,
			        store:new Ext.data.Store({
			        	fields:['userName','userCode'],
			        	autoLoad:false,
			        	proxy:{
			        		type: 'rest',
			        		url:'getUsers?role=FDB'
			        	}
			        }),
			        listeners : {
			        	'change' : function(){
			        		Ext.getCmp("formId").removeAll();
			        		var a = Ext.ComponentQuery.query('productionRecordView combobox')[1].getRawValue();
			        		console.log(a);
			        		var component = [{
								xtype:'label',
								text : a,
								margin : '2 0 2 0',
								padding : '0 100 0 80',
								width:250
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							}];
			        		var formPanel = Ext.getCmp("formId").add(component);
			        	}
			        }
			    },{
			        fieldLabel: '辅助工',
			        xtype:'combobox',
			        labelWidth:60,
			        height : 25,
			        name: 'fzgName',
			        id : 'fzgId',
			        queryMode: 'local',
			        displayField:'userName',
			        valueField: 'userName',
			        allowBlank:false,
			        store:new Ext.data.Store({
			        	fields:['userName','userCode'],
			        	autoLoad:false,
			        	proxy:{
			        		type: 'rest',
			        		url:'getUsers?role=FZG'
			        	}
			        }),
			        listeners : {
			        	'change' : function(){
			        		var a = Ext.ComponentQuery.query('productionRecordView combobox')[2].getRawValue();
			        		console.log(a);
			        		var component = [{
								xtype:'label',
								text : a,
								margin : '2 0 2 0',
								padding : '0 100 0 80',
								width:250
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							},{
								xtype:'textfield',
								width:250,
								margin : '2 2 2 2',
								allowBlank : false,
				                plugins:{
				                    ptype:'virtualKeyBoard'
				                },
				                listeners : {
				                    specialKey : function(field, e) {
				                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
				                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
				                        }
				                    }
				                }
							}];
			        		var formPanel = Ext.getCmp("formId").add(component);
			        	}
			        }
			    }]
			},{
				title: '原材料消耗情况',
		        xtype: 'fieldset',
		        collapsible: true,
		        width: '99%',
		        height : '230',
		        margin : '5 0 5 0',
		        items: {
		        	xtype : 'hform',
		        	buttonAlign :'left',
					labelAlign : 'right',
					layout: 'vbox',
					width : '100%',
					bodyPadding : 4,
		        	items : [{
		        		xtype: 'panel',
		        		width : '100%',
						layout: 'column',
						items :[{
							xtype:'label',
							text : '原材料',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'label',
							text : '上班固存',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'label',
							text : '本班取料',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'label',
							text : '本班退料',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'label',
							text : 'TPE',
							margin : '2 0 2 0',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'label',
							text : 'PUC',
							margin : '2 0 2 0',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'label',
							text : 'XLPE',
							margin : '2 0 2 0',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'label',
							text : 'PE',
							padding : '0 100 0 80',
							margin : '2 0 2 0',
							width:250
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'label',
							text : '尼龙',
							margin : '2 0 2 0',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'label',
							text : '铜线',
							margin : '2 0 2 0',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						},{
							xtype:'textfield',
							width:250,
							margin : '2 2 2 2',
							allowBlank : false,
			                plugins:{
			                    ptype:'virtualKeyBoard'
			                },
			                listeners : {
			                    specialKey : function(field, e) {
			                        if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
			                            Ext.getCmp('userInfoDetailValidOkButton').fireEvent('click')
			                        }
			                    }
			                }
						}]
		        	}]
		        }
			},{
				title: '员工工时',
		        xtype: 'fieldset',
		        collapsible: true,
		        width: '99%',
		        height : '200',
		        items: {
		        	xtype : 'hform',
		        	buttonAlign :'left',
					labelAlign : 'right',
					layout: 'vbox',
					width : '100%',
					bodyPadding : 4,
		        	items : [{
		        		xtype: 'panel',
		        		width : '100%',
						layout: 'column',
						items :[{
							xtype:'label',
							text : '员工姓名',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'label',
							text : '生产性辅助工时',
							padding : '0 50 0 80',
							width:250
						},{
							xtype:'label',
							text : '辅助工时',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'label',
							text : '加班工时',
							padding : '0 100 0 80',
							width:250
						},{
							xtype:'panel',
							id:'formId',
							width : '100%',
							layout: 'column',
						}]
		        	}]
		        }
			}]
		}];
		Ext.apply(me, {
			buttons: [{
				itemId: 'productionRecordSave',
				text : Oit.btn.ok
			}, '->',{
				text : Oit.btn.close,
				handler : function() {
					this.up('.window').close();
				}
			} ]
		});
		this.callParent(arguments);
	}
});