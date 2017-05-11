/**
 * 交班报表
 */
var processCode=Ext.fly('processInfo').getAttribute('code');
var isHidden=true;
if(processCode=='Extrusion-Single'||processCode=='Jacket-Extrusion'){
	isHidden=false;
}
Ext.define("bsmes.view.ShiftRecordView", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.shiftRecordView',
	id: 'shiftRecordView',
	store : 'ShiftRecordStore',
	height:document.body.scrollHeight-140,
	defaultEditingPlugin : false,
	matUsedMapCache : {}, 
	columns : [{
		text : Oit.msg.wip.workOrder.workOrderNO,
		width: 60,
		dataIndex : 'workOrderNo',
		renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
            return value.substring(value.length - 5);
        }
	},{ 
		text:'id',
		width:30,
		dataIndex : 'id',
		hidden: true
	},{ 
		text:'合同号',
		width:50,
		dataIndex : 'contractNo',
		hidden: true
	},{
		text : Oit.msg.wip.workOrder.productType,
		width: 120,
		dataIndex : 'custProductType'
	}, {
		text : Oit.msg.wip.workOrder.productSpec,
		width: 70,
		dataIndex : 'custProductSpec'
	}, {
		text : Oit.msg.wip.terminal.productLength,
		width: 70,
		dataIndex : 'workOrderLength'
	}, {
		text : Oit.msg.wip.workOrder.reportNum,
		width: 70,
		dataIndex : 'reportLength'
	}, {
		text : Oit.msg.wip.workOrder.matName,
		dataIndex : 'matName',
		width: 130,
		hidden: isHidden,
		renderer: function(value, metaData, record){
			return value.replace(/,/g,'<br/>');
		}
	}, {
		dataIndex : 'matCode',
		width: 1,
		hidden: true
	},{
		text : Oit.msg.wip.terminal.quotaAmount,
		dataIndex : 'quotaQuantity',
		width: 110,
		hidden: isHidden,
		renderer: function(value, metaData, record){
			return value.replace(/,/g,'<br/>');
		}
	}, {
		text : Oit.msg.wip.terminal.realAmount,
		dataIndex : 'realQuantity',
		width: 160,
		hidden: isHidden,
	    renderer: function(value, metaData, record){
	    	var html = '<a style="color:blue;cursor:pointer;" onclick="fillOutQuantity(\'' + record.get('id') + '\', \'' + record.get('matName') + '\')">填写</a>';
	    	var shiftRecordView = Ext.ComponentQuery.query('shiftRecordView')[0];	    	
	    	var matNames=record.get('matName').split(',');
	    	var realQuantity='';
	    	if(!shiftRecordView.matUsedMapCache[record.get('id')]){
	    		return html;
	    	}
	    	else{
	    		for(var i in matNames){
		    		realQuantity=realQuantity+shiftRecordView.matUsedMapCache[record.get('id')][matNames[i]]+',';		    		
		    	}
	    		return realQuantity.substr(0,realQuantity.length-1).replace(/,/g,'<br/>') +html;
	    	}
	    }
	}
	],
	dockedItems : [{
		xtype: 'toolbar',
		dock: 'top',
		margin: '10',
		layout: 'vbox',
		items:[{
		       xtype: 'form',
		       layout: 'hbox',
		       width: '100%',
		       height: '120',
		       margin: '5,0,0,0',
		       items: [{
					fieldLabel:'<font size="4.5px">机台</font>',
					xtype:'displayfield',
					labelWidth : 45,
					name:'equipInfo',
					value : Ext.fly('equipInfo').getAttribute('equipAlias')
				 },{
						fieldLabel:'<font size="4.5px">班次</font>',
						xtype:'radiogroup',
						id: 'shift',
						labelWidth : 45,
						width: 400,
						margin: '0 0 0 50',
						items: [{
							boxLabel:'早班',
							name:'shift',
							inputValue:'mShift'
						},{
							boxLabel:'中班',
							name:'shift',
							inputValue:'aShift'
						},{
							boxLabel:'晚班',
							name:'shift',
							inputValue:'eShift'
						}
						],
						listeners: {
							beforerender: function(){								
								var time=Ext.Date.format(new Date(),'H:i:s');
								var now=Ext.Date.parse(time,'H:i:s');
								var shift1=Ext.Date.parse('07:45:00','H:i:s');
								var shift2=Ext.Date.parse('15:45:00','H:i:s');
								var shift3=Ext.Date.parse('23:45:00','H:i:s');
							    if(now.getTime()>=shift1.getTime()&&now.getTime()<shift2.getTime()){
							    	this.setValue({shift:'mShift'});
							    }
							    else if(now.getTime()>=shift2.getTime()&&now.getTime()<shift3.getTime()){
							    	this.setValue({shift:'aShift'});
							    }
							    else 
							    	this.setValue({shift:'eShift'});
							}/*,
							change :function(radoi, newValue, oldValue, eOpts){
								var store=Ext.ComponentQuery.query('#shiftRecordView')[0].getStore();
								Ext.ComponentQuery.query('#shiftRecordView')[0].getStore().getProxy().
								extraParams={'shift':newValue.shift,
									         'equipCode': Ext.fly('equipInfo').getAttribute('code')};
								store.load();
								
							}*/
						}
						
						},{
							padding: '0 0 0 20',
							fieldLabel: '班次日期',
							labelwidth: 30,
							xtype: 'datefield',
							name: 'shiftDate',
							id: 'shiftDate',
							format: 'Y-m-d',
							height: 22,
							listeners:{
								beforerender: function(ths,eOpts){
									var shiftName=Ext.ComponentQuery.query('#shift')[0].getValue().shift;
									var time=Ext.Date.format(new Date(),'H:i:s');
									var current=new Date();
									var now=Ext.Date.parse(time,'H:i:s').getTime();
									var shift1=Ext.Date.parse('07:45:00','H:i:s');
									var shift2=Ext.Date.parse('00:00:00','H:i:s');
                                    if(shiftName=='eShift'&&now>=shift2.getTime()&&now<shift1.getTime()){
                                    	ths.setValue(new Date(current.getTime()-3600*1000*24));
                                    }
                                    else{
                                    	ths.setValue(current);
                                    }
									
								}	
							}													
						}]
		   },{ 
			    title:'人员信息',
				width: '99%',
				xtype: 'fieldset',
				margin: '10,0,0,0',
				height : 80,
				collapsible : false,
				// layout:'hbox',
				items:[{
					xtype : 'hform',
					layout : 'hbox',
					bodyPadding : 5,
					items:[{
						fieldLabel : '<font size="4.5px">挡班</font>',
						name : 'DB',
						id:'DB',
						xtype: 'combobox',
						width : 200,
						labelWidth:50,
						height: 25,
						margin: '10,0,10,30',
						displayField:'userName',
				        valueField: 'userCode',
				        allowBlank:false,
				        multiSelect : true,
						store: new Ext.data.Store({
							fields:['userCode','userName'],
							autoLoad:false,
							proxy:{
								type:'rest',
								url:'getUsers?role=DB'
							}
						}),
						listeners:{
							'afterRender': function(){
								var combo = Ext.getCmp('DB');
								var store=combo.getStore();
								combo.setValue(store.getAt(0));
							}
						}
					},{
						fieldLabel : '<font size="4.5px">副挡班</font>',
						name : 'FDB',
						id:'FDB',
						xtype: 'combobox',
						width : 200,
						labelWidth:60,
						height: 25,
						margin: '10,0,10,30',
						displayField:'userName',
				        valueField: 'userCode',
				        allowBlank:false,
						store: new Ext.data.Store({
							fields:['userCode','userName'],
							autoLoad:false,
							proxy:{
								type:'rest',
								url:'getUsers?role=FDB'
							}
						}),
						listeners:{
							'afterrender': function(){
								var combo = Ext.getCmp('FDB');
								var store=combo.getStore();
								combo.setValue(store.getAt(0));
								//Ext.ComponentQuery.query('#shiftRecordView')[0].getStore().load();
							}
						}
					},{
						fieldLabel : '<font size="4.5px">辅助工</font>',
						name : 'FZG',
						id:'FZG',
						xtype: 'combobox',
						width : 200,
						labelWidth:60,
						height: 25,
						margin: '10,200,10,30',
						displayField:'userName',
				        valueField: 'userCode',
				        allowBlank:false,
						store: new Ext.data.Store({
							fields:['userCode','userName'],
							autoLoad:false,
							proxy:{
								type:'rest',
								url:'getUsers?role=FZG'
							}
						}),
						listeners:{
							'afterrender': function(){
								var combo = Ext.getCmp('DB');
								var store=combo.getStore();
								combo.setValue(store.getAt(0));
							}
						}
					},{
						text: '查询',
						xtype:'button',
						margin: '5 0 0 300',
						handler: function(){
							var store=Ext.ComponentQuery.query('#shiftRecordView')[0].getStore();
							store.load();
						}
					},{
						text : '保存',
						xtype:'button',
						margin: '5 0 0 15',
						handler : function() {
							var shiftRecordView = Ext.ComponentQuery.query('shiftRecordView')[0];
							if(shiftRecordView.getStore().getCount()==0){
								Ext.Msg.alert(Oit.msg.PROMPT, '没有数据！' );
								return;
							}
							Ext.MessageBox.confirm('确认','确认保存？',function(btn){
								if(btn=='yes'){
									var date=Ext.util.Format.date(new Date(),'Y-m-d H:i:s');
									var data = [];
									var realJsonDatas=[];
									for(var i = 0; i< shiftRecordView.getStore().getCount(); i++){
										var record = shiftRecordView.getStore().getAt(i);
										var temp = record.getData();	
										temp.realAmount = shiftRecordView.matUsedMapCache[record.get('id')];
										//console.log(Ext.encode(temp));
										data.push(temp);
										realJsonDatas.push(temp.realAmount);
									}
									//console.log(Ext.encode(data));
										Ext.Ajax.request({
											  url:'shiftRecordInsert',
											  method:'POST',
											  params:{
												  //'orderItemDecId' : temp.id,
												  'equipCode' : Ext.fly('equipInfo').getAttribute('code'),
												  'shiftName' : Ext.ComponentQuery.query('#shiftRecordView radiogroup')[0].getValue().shift,
										          'dbUserCode': Ext.ComponentQuery.query('#DB')[0].value,
										          'dbUserName': Ext.ComponentQuery.query('#DB')[0].rawValue,
										          'fdbUserCode': Ext.ComponentQuery.query('#FDB')[0].value,
										          'fdbUserName': Ext.ComponentQuery.query('#FDB')[0].rawValue,
										          'fzgUserCode': Ext.ComponentQuery.query('#FZG')[0].value,
										          'fzgUserName': Ext.ComponentQuery.query('#FZG')[0].rawValue,
										          //'workOrderNo': temp.workOrderNo,
										          //'contractNo':  temp.contractNo,
										          //'custProductType':temp.custProductType,
										         // 'custProductSpec': temp.custProductSpec,
										          //'workOrderLength': temp.workOrderLength,
										          //'reportLength': temp.reportLength,
										            'realJsonDatas': Ext.encode(realJsonDatas),
		                                         // 'matName': temp.matName,
		                                        //  'matCode': temp.matCode,
		                                         // 'quotaQuantity': temp.quotaQuantity,
										          'param': Ext.encode(data),
		                                          'operator': Ext.util.Cookies.get('operator'),
										          'turnOverDate': date,
										          'shiftDate': Ext.ComponentQuery.query('#shiftDate')[0].value,
										          'processCode': processCode,
										          'createTime': Ext.Date.format(new Date(),'Y-m-d H:i:s')
											  },
											  success: function(response){
												  Ext.Msg.alert(Oit.msg.PROMPT, '保存成功！');
											  },
											  failure: function(response,action){
												  Ext.Msg.hide();
												  Ext.Msg.alert(Oit.msg.PROMPT, '保存失败！');
											  }
											  
										  });
								
								}				
							});			
						}
					},{
						text : Oit.btn.close,
						xtype:'button',
						margin: '5 0 0 15',
						handler: function(){
								var me = this;
								me.up('window').close();
							}
					}]
				}]
			}		
		]		
	}]
});

function fillOutQuantity(id, matNames){
	var matName=matNames.split(',');
	var items = [];
	for(var i in matName){
		items.push({
			xtype : 'container',
			layout : 'vbox',
			padding :  '15 0 0 0',
			items : [{
			     xtype : 'textfield', 
			     fieldLabel: matName[i],
			     minValue: 0,
			     labelWidth: 120,
			     name : matName[i],
			     padding :  '5 0 20 0',
			     decimalPrecision:2, 
			     height:30,
			     enableKeyEvents:true,
			     plugins:{
                     ptype:'virtualKeyBoard'
                 }
		}]
		})		
	}
	
	var win = new Ext.Window({
		title : '填写实际耗用量',
		padding : '10',
		modal : true,
		plain : true,
		width : Math.round(document.body.scrollWidth / 2),
		height : Math.round(document.body.scrollHeight)-140,
		items : [{
			xtype : 'form',
			width : Math.round(document.body.scrollWidth / 2) - 30,
			height : Math.round(document.body.scrollHeight) - 100,
			layout: 'vbox',
			items : items
		}],		
		buttons : ['->',  {
					text : '保存',
					handler : function() {
						var matUsed = win.query('textfield'), dataJson = {};
						for(var i in matUsed){
							dataJson[matUsed[i].name] = matUsed[i].value
						}
						var shiftRecordView = Ext.ComponentQuery.query('shiftRecordView')[0];
						var matUsedMapCache = shiftRecordView.matUsedMapCache;
						matUsedMapCache[id] = dataJson;
						win.close();
						Ext.ComponentQuery.query('shiftRecordView')[0].getStore().load();
					}
				},{
					text : '关闭',
					handler : function() {
						this.up('window').close();
					}
				},{
					text: '清空',
					handler: function(){
						this.up('window').down('form').getForm().reset();
					}
				}]
	});
	win.show();
}