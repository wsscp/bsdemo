Ext.define("bsmes.view.TurnOverMaterialWindow", {
       extend : 'Ext.window.Window',
       alias : 'widget.turnOverMaterialWindow',
       width:document.body.scrollWidth,
       height:document.body.scrollHeight,
       modal: true,
       autoScroll: true,
       title: '交料' ,
       itemId:'turnOverMaterialWindow',
       initComponent : function(){
    	   var me=this;
    	   var workOrderNo=Ext.fly('orderDetail').getAttribute('num');
    	   var processCode = Ext.fly( "processInfo" ).getAttribute('code' );
    	   var its=[];
    	   var assistItems=[];
    	   var assistNames=[];
    	   var jsonData={};
    	   //var totalItems=[];
    	   jsonData['equipCode']=Ext.fly('equipInfo').getAttribute('code');
    	   jsonData['processCode']=processCode;
    	   jsonData['workOrderNo']=workOrderNo;
    	   var isTure=false;
    	   var hasAssistNames=false;
    	   if(processCode=='Extrusion-Single'||processCode=='Jacket-Extrusion'){
    		   isTure=true;
    	   }
    	   if(isTure){
    		   Ext.Ajax.request({
                   url: 'getMat',
                   method:'POST',
                   async :  false,
                   params:{
                           'workOrderNo': workOrderNo,
                           'processCode': processCode                
                          },
                   success: function(response){     
                	       var matNames=Ext.decode(response.responseText);    
                	       jsonData['matNames']=matNames;
                	       for(var i in matNames){
                	    	   its.push({
                        		   xtype: 'panel',
                        		   height: 40,
                        		   layout: 'hbox',
                        		   items:[{
                        			   xtype:'text',
                        			   text: matNames[i].matName,
                        			   width: 150,
                        			   margin:'10 0 0 60'
                        		   },{
                        			   xtype:'textfield',
                        			   width: 120, 
                        			   name: matNames[i].matCode+'Sbjl',
                        			   margin:'8 0 0 5',
                        			   border: '5 5 5 5',
                        			   value: 0,
                        			   enableKeyEvents:true,
                                       plugins:{
                                       ptype: 'virtualKeyBoard'        //定义虚拟数字键盘
                              }

                        		   },{
                        			   xtype:'textfield',
                        			   width: 120,
                        			   value: 0,
                        			   margin:'8 0 0 90',
                        			   name: matNames[i].matCode+'Bbll',
                        			   enableKeyEvents:true,
                                       plugins:{
                                       ptype: 'virtualKeyBoard'        //定义虚拟数字键盘
                              }

                        		   },{
                        			   xtype:'textfield',
                        			   width: 120,
                        			   value: 0,
                        			   margin:'8 0 0 90',
                        			   name: matNames[i].matCode+'Bbtl',
                        			   enableKeyEvents:true,
                                       plugins:{
                                       ptype: 'virtualKeyBoard'       
                              }

                        		   },{
                        			   xtype:'textfield',
                        			   width: 120,
                        			   margin:'8 0 0 90',
                        			   value: 0,
                        			   name: matNames[i].matCode+'Bbjl',
                        			   enableKeyEvents:true,
                                       plugins:{
                                       ptype: 'virtualKeyBoard'      
                              }

                        		   }]                   	   
                	    	   });
                	       }           	       
                            },
                   failure: function(response,action){
                            Ext.Msg.alert(Oit.msg.PROMPT, '错误' );    
                            }       
    });
    	   }    	 
    	   Ext.Ajax.request({
    		   url: 'getAssistOp',
               method:'POST',
               params:{
            	   'processCode': processCode
               },
               async :  false,
               success: function(response){
            	   assistNames=Ext.decode(response.responseText);
            	   if(assistNames.length==0){
            		   hasAssistNames=true;
            	   }
            	   jsonData['assistNames']=assistNames;
            	   for(var i in assistNames){
            		   assistItems.push({
    		    		   xtype:'panel',
    		    		   height: 40,
    		    		   margin:'0 0 0 50',
    		    		   layout:'hbox',
    		    		   items:[{
						       fieldLabel : assistNames[i].assistOptionName,
						       name : assistNames[i].assistOptionEname+'Opt',
	    					   id: assistNames[i].assistOptionEname+'Opt',
						       xtype: 'combobox',
						       width : 300,
						       labelWidth:100,
						       //multiSelect :true,
						       height: 25,
						       margin: '10 60 10 0',
						       displayField:'userName',
				               valueField: 'userCode',
				               allowBlank:false,
						       store: new Ext.data.Store({
							   fields:['userCode','userName'],
							   autoLoad:true,
							   proxy:{
								      type:'rest',
								      url:'getAllUsers'
							}
						})
					},{
						xtype:'textfield',
						fieldLabel: '工时',
						name:assistNames[i].assistOptionEname+'Hour',
						id: assistNames[i].assistOptionEname+'Hour',
						labelWidth: 100,
						enableKeyEvents:true,
                        plugins:{
                        ptype: 'virtualKeyBoard'       
               },
						margin: '10 0 10 0'
					}]    		    	   
            		   });
            	   }
               },
               failure: function(response,action){
            	   Ext.Msg.alert(Oit.msg.PROMPT, '数据错误' ); 
               }
    	   });
    	   me.items=[{
    		   xtype: 'form',
    		   itemId: 'form1',
    		   id: 'form1',
    		   layout: 'vbox',
    		   width: '100%',
    		   items:[{
        		   title:'交料详情',
        		   xtype: 'fieldset',
        		   //height:200,
    		       width: '99%',
    		       margin: '0 10 0 18',
    		       hidden: !isTure,
    		       padding:'0 0 10 0',
    		       collapsible : false,
    		       layout:'vbox',
                   items:[{
                	   xtype: 'panel',
                	   height: 40,
                	   margin:'5 0 0 0',
                	   layout: 'hbox',
                	   items:[{
                		   xtype:'text',
                		   text:'上班交料',
                		   width: 200,
                		   margin:'15 0 0 250'
                	   },{
                		   xtype:'text',
                		   text:'本班领料',
                		   width: 200,
                		   margin:'15 0 0 10'
                	   },{
                		   xtype:'text',
                		   text:'本班退料',
                		   width: 200,
                		   margin:'15 0 0 10'               	   
                	   },{
                		   xtype:'text',
                		   text:'本班交料',
                		   margin:'15 0 0 10'                	   
                	   }]
                   },{
                	   xtype: 'panel',
                	   id: 'its',
                	   layout: 'vbox',
                	   items: its
                   }]
        		       	   
    		   },{
    			   title:'生产辅助工时',
        		   xtype: 'fieldset',
        		   padding: '5 0 5 0',
    		       width: '99%',
    		       layout: 'vbox',
    		       hidden: hasAssistNames,
    		       margin: '15 10 0 18',
    		       collapsible : false,
    		       items: [{
    		    	   xtype: 'panel',
    		    	   id:'assistItems',
    		    	   width: '100%',
    		    	   layout: 'vbox',
    		    	   items:assistItems
    		       }]
    		   },{
    			   title:'辅助工时',
        		   xtype: 'fieldset',
    		       width: '99%',
    		       padding: '5 0 5 0',
    		       layout: 'vbox',
    		       margin: '15 10 0 18',
    		       collapsible : false,
    		       items:[{
		    		   xtype:'panel',
		    		   layout:'hbox',
		    		   margin:'0 0 0 50',
		    		   items:[{
					       fieldLabel : '辅助工时',
					       name : 'assistHourOpt',
    					   id: 'assistHourOpt',
					       xtype: 'combobox',
					       width : 300,
					       labelWidth:100,
					       //multiSelect :true,
					       height: 25,
					       margin: '10 60 10 0',
					       displayField:'userName',
			               valueField: 'userCode',
			               allowBlank:false,
					       store: new Ext.data.Store({
						   fields:['userCode','userName'],
						   autoLoad:true,
						   proxy:{
							      type:'rest',
							      url:'getAllUsers'
						}
					})				
		    		   },{
		    			    xtype:'textfield',
							fieldLabel: '工时',
							id: 'assistHour',
							name: 'assistHour',
							labelWidth: 100,
							enableKeyEvents:true,
                            plugins:{
                            ptype: 'virtualKeyBoard'       
                   },
							margin: '10 0 10 0'
		    		   }]		   	    	   
    		       }]
    		   }]
    	   }];
    	   me.dockedItems=[{
    		    xtype: 'toolbar',
    			dock: 'top',
    			layout: 'vbox',
    			items: [{
    				   xtype: 'form',
    				   itemId: 'form2',
    			       layout: 'hbox',
    			       width: '100%',
    			       height: '120',
    			       margin: '5,0,0,0',
    			       items:[{
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
   							}
   						}					
    			       },{
							margin: '0 0 0 20',
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
    				items: [{
    					xtype : 'hform',
    					itemId: 'form3',
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
    						text : '保存',
    						xtype:'button',
    						margin: '5 0 0 350',
    						handler : function() {    							
    							var form1=Ext.ComponentQuery.query('#form1')[0];
    							var Dbname=Ext.getCmp('DB').getValue(); 
    							if(Dbname==null){
									Ext.Msg.alert(Oit.msg.PROMPT,'请选择挡班！');
									return;
								}
    							for(var i in assistNames){
    								var user=assistNames[i].assistOptionEname+'Opt';
    								var hour=assistNames[i].assistOptionEname+'Hour';
    								var combo1=Ext.getCmp(user);
    								var text1=Ext.getCmp(hour);   								   								    								
    								if(combo1.getValue()==null&&text1.getValue().length!=0||
    										combo1.getValue()!=null&&text1.getValue().length==0){
    									Ext.Msg.alert(Oit.msg.PROMPT,'人员信息或者工时没有填写！');
    									return;
    								}
    							}
    								var combo2=Ext.getCmp('assistHourOpt');
    								var text2=Ext.getCmp('assistHour');
    								if(combo2.getValue()==null&&text2.getValue().length!=0||
    										combo2.getValue()!=null&&text2.getValue().length==0){
    									Ext.Msg.alert(Oit.msg.PROMPT,'人员信息或者工时没有填写！');
    									return;
    								}
    								Ext.MessageBox.confirm('确认','确认保存？',function(btn){
    									if(btn=='yes'){
    										if(processCode=='Extrusion-Single'||processCode=='Jacket-Extrusion'){
    											var subIts=Ext.getCmp('its').items.items;
    											var matNames=jsonData['matNames'];
    											for(var i in subIts){
    												var sub=subIts[i].items.items;
    												for(var j in sub){
    													if(sub[j].xtype=='textfield'){
    														jsonData[sub[j].name]=sub[j].value;
    													}
    												}
    											}
    										}
    										var asitHour=Ext.getCmp('assistItems').items.items;
    										for(var i in asitHour){
    											var subAsistHour=asitHour[i].items.items;
    											for(var j in subAsistHour){
    												if(subAsistHour[j].xtype=='combobox'){
    													jsonData[subAsistHour[j].name+'Name']=subAsistHour[j].rawValue;
    												}
    												jsonData[subAsistHour[j].name]=subAsistHour[j].value;										
    											}
    										}
    										jsonData['assistHourOpt']=Ext.getCmp('assistHourOpt').getValue();
    										jsonData['assistHourOptName']=Ext.getCmp('assistHourOpt').rawValue;
    										jsonData['assistHour']=Ext.getCmp('assistHour').getValue();
    										jsonData['DB']=Ext.getCmp('DB').getValue();
    										jsonData['dbUserName']=Ext.getCmp('DB').rawValue;
    										jsonData['FDB']=Ext.getCmp('FDB').getValue();
    										jsonData['fdbUserName']=Ext.getCmp('FDB').rawValue;
    										jsonData['FZG']=Ext.getCmp('FZG').getValue();
    										jsonData['fzgUserName']=Ext.getCmp('FZG').rawValue;
    										jsonData['shiftName']=Ext.getCmp('shift').getValue().shift;
    										jsonData['shiftDate']=Ext.getCmp('shiftDate').getValue();  										
    										Ext.Ajax.request({
    											url:'matTurnover',
    											method:'POST',
  											    params:{
  											    	'jsonData': Ext.encode(jsonData),
  											    	'operator':Ext.util.Cookies.get('operator'),
  											    },
  											    success: function(response){
  											      console.log(response.responseText);
												  Ext.Msg.alert(Oit.msg.PROMPT, '保存成功');
											  },
											    failure: function(response,action){
												  Ext.Msg.alert(Oit.msg.PROMPT, '保存失败！');
											  }
    										});
    									}
    								});
    							}
    					},{
    						text : Oit.btn.close,
    						xtype:'button',
    						margin: '5 0 0 20',
    						handler: function(){
    								var me = this;
    								me.up('window').close();
    							}
    					}]
    				
    				}]
    			}]
    	   }];
    	   this.callParent(arguments);
       }
});