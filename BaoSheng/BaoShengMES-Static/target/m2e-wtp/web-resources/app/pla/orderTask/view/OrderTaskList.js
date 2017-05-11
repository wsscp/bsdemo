var stratDate=new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),0,0,0) ;
var endDate=Sch.util.Date.add(new Date(), Sch.util.Date.DAY,14);
var gridNmae='orderTaskList';
Ext.Loader.setConfig({enabled: true, disableCaching : true });
 Ext.define('bsmes.view.OrderTaskList', {
	 extend : "Sch.panel.SchedulerTree",
	 alias : 'widget.orderTaskList',	  
	 id:'orderTaskList',
	 eventStore :Ext.create('bsmes.store.Events'),
	 resourceStore : Ext.create('bsmes.store.OrderTaskStore'),
     rowHeight : 28,
     infiniteScroll  : false,
     eventBorderWidth :1,
	 columnLines     : true,
	 rowLines:true,
	 highlightWeekends: true,
	 readOnly:true,
     loadMask: true ,
     tipCfg:{autoHide: false},
	 startDate:   stratDate,
	 endDate : endDate,
	 viewPreset      : 'hourAndDay',
	 columns :[{
	             xtype:'treecolumn',
	             text : '工序信息',
	             width: 220,
	             dataIndex: 'Name',
	             renderer  : function (v, meta, r) {
	                   if (!r.data.leaf){
	                    	  meta.tdCls = 'sch-gantt-parent-cell';
	                    }                   
	                    return v;
	             },
	             listeners:{
	 	 			  'click':function(v, record, item, index, e, eOpts){
	 	 				 var val=eOpts.data.Id;
	 	 				 var order=Ext.getCmp('orderTaskList');
	 	 				 var record=order.eventStore.findRecord('resourceId', val,0,false,true,false);
	 	 				 if(record==null)
	 	 				 {
	 	 					 return;
	 	 					}
	 	 				 order.getSchedulingView().scrollEventIntoView(record, true);
	 	 			 }
	 	 		  }
	 }],
	 eventRenderer : function(flight, resource, meta) {
	          if(resource.data.leaf) {
	               meta.cls = 'leaf';
	                return flight.get('productCode');
	          } else {
	                meta.cls = 'group';
	                return '&nbsp;';
	            }
	   },
	        
	   lockedGridConfig : {
	         resizeHandles : 'e',
	         resizable: { pinned : true } 
	           
	    },	        
	    schedulerConfig : {
	          scroll : true,
	          columnLines : false,
	          flex : 0
	    },	
	    tbar  : [ 
	                
	  	   	                 '过滤条件: ',
	                {
            xtype     : 'triggerfield',
            emptyText : '输入后请回车...',
            enableKeyEvents: true,
            triggerCls : 'x-form-clear-trigger',

            onTriggerClick : function () {
                this.setValue('');
                var resourceStore=Ext.ComponentQuery.query(gridNmae)[0].view.getStore();
   			    resourceStore.clearTreeFilter();
            },

            listeners : {
            	keyup: function(text, e, eOpts){
            		if(e.getKey()==Ext.EventObject.ENTER){
            			if(text.getValue( )!=''&&text.getValue()!=undefined){
            				var resourceStore=Ext.ComponentQuery.query(gridNmae)[0].view.getStore();
			                  var newValue=text.getValue( );
			                  if (newValue){
			                    	newValue=newValue.replace(/(^\s*)|(\s*$)/g, "");  
			                    		 
			                    			var regexps = Ext.Array.map(newValue.split(/\s+/), function (token){
			                    				return new RegExp(Ext.String.escapeRegex(token), 'i');
			                   				});
			                    				var length = regexps.length;
			                   					resourceStore.filterTreeBy({ filter:function (resource){
			                    				var name = resource.get('Name');
			                    				for (var i = 0; i < length; i++){
			                    							if (regexps[ i ].test(name)){
			                    								return true;
			                    							}                        	
			                    						}
			                    						return false;
			                    					},
			                    					checkParents :true
			                                       });
			                             }else {
			                                resourceStore.clearTreeFilter();
			                            }
            			}
            			else
            			{
            				var resourceStore=Ext.ComponentQuery.query(gridNmae)[0].view.getStore();
            				  resourceStore.clearTreeFilter();
            				}
            		}else
            			{
            			if(text.getValue( )==''||text.getValue( )==' '||text.getValue()==undefined){
            				var resourceStore=Ext.ComponentQuery.query(gridNmae)[0].view.getStore();
           				  resourceStore.clearTreeFilter();
           				 this.setValue('');
            			}
            			 
            			}
            		
                },    
                specialkey : function (field, e, t) {
                    if (e.keyCode === e.ESC) field.reset();
                }
              }
               // eof listeners
	          },
	          {
  	              text    : '',	                                    
  	              iconCls : 'expandAll',
  	              handler : function () {
  	            	    
  	                       Ext.ComponentQuery.query('orderTaskList')[0].expandAll( );
  	                   }
  	                 },
  	            {
  	   	              text    : '',	                                    
  	   	              iconCls : 'collapseAll',
  	   	              handler : function () {
  	   	            	    
  	   	                       Ext.ComponentQuery.query('orderTaskList')[0].collapseAll( );
  	   	                   }
  	   	                 }, 
  	   	            
  		   	              '预计开始日期 从：',
  		   		          {
  		   	                 
  		   	                  xtype : 'datefield',
  		   	                  name : 'fromDate',
  		   	                  width:110,
  		   	                  value:stratDate,
  		   	                  id:'orderTaskListqueryFromDate',
  		   	                  format: 'Y-m-d'
  		   	              },
  		   		         '到:'
  		   	              ,{
  		   	                  
  		   	                  xtype : 'datefield',
  		   	                  name : 'toDate',
  		   	                  id:'orderTaskListqueryToDate',
  		   	                  value:endDate,
  		   	                  width:110,
  		   	                  format: 'Y-m-d'
  		   	              }  ,
  		                  {
  		                   text    : '按时间查询',	                                    
  		                   iconCls : 'goSsearch',
  		                   handler : function () {
  		                   	   
  		                 	  var startDate= Ext.getCmp("orderTaskListqueryFromDate").getValue();
  		                 	     if(startDate==null)
  		                 		  { Ext.MessageBox.alert("提示", "请输入开始日期!");
  		                 		  return;
  		                 		  }
  		                 	 
  		                 	  
  		                   	  endDate=Ext.getCmp("orderTaskListqueryToDate").getValue();
  		                   	  if(endDate==null)
  		             		  {
  		                   	  Ext.MessageBox.alert("提示", "请输入截止日期!");
  		             		  return;
  		             		  }
  		                   	  if(endDate<startDate)
  		                   	    {
  		                   		 Ext.MessageBox.alert("提示", "开始日期必须小于截止日期!");
  		                		     return;
  		                   		 }
  		                   	   endDate=new Date(endDate.getFullYear(),endDate.getMonth(),endDate.getDate(),23,59,59) ; 
  		                   	   
  		                   	   stratDate=startDate;
		                     
		                       
  		                   	   Ext.ComponentQuery.query('orderTaskList')[0].getResourceStore().load({params:{"fromDate":startDate,"toDate":endDate}});
  		                   	   Ext.ComponentQuery.query('orderTaskList')[0].getEventStore().load({params:{"fromDate":startDate,"toDate":endDate}}); 
  		                   	   Ext.ComponentQuery.query('orderTaskList')[0].switchViewPreset('hourAndDay',startDate, endDate);
  		                   	  
  		                   	  
  		                           
  		                        }
  		                      },      
            
                  {
	                                   text    : '放大',	                                    
	                                   iconCls : 'zoomIn',
	                                   toggleGroup  : 'presets',
	                                   enableToggle : true,
	                                   handler : function () {
	                                	  
	                                	   var o=Ext.ComponentQuery.query(gridNmae)[0].zoomIn();
	                                	   if(!o)
	                                	   {
	                                		   Ext.MessageBox.alert("提示", "视图不能再放大了 !");
	                                	   }
	                                	   
	                                   }
	                               },
	                               {
	                                   text    : '缩小',	                                  
	                                   iconCls : 'zoomOut',
	                                   toggleGroup  : 'presets',
	                                   enableToggle : true,
	                                   handler : function () {
	                                	   var o= Ext.ComponentQuery.query(gridNmae)[0].zoomOut();  
	                                	   if(!o)
	                                	   {
	                                		   Ext.MessageBox.alert("提示", "视图不能再缩小了 !");
	                                	   }
	                                   }
	                               }  
	                               ,
	                               {
	                                   text         : '按分钟',
	                                   toggleGroup  : 'presets',
	                                   enableToggle : true,
	                                   iconCls      : 'icon-calendar',
	                                   handler      : function () {
	                                	   Ext.ComponentQuery.query(gridNmae)[0].switchViewPreset('minuteAndHour',stratDate, endDate);
	                                   }
	                               },
	                               {
	                                   text         : '按小时',
	                                   toggleGroup  : 'presets',
	                                   enableToggle : true,
	                                   iconCls      : 'icon-calendar',
	                                   handler      : function () {
	                                	   Ext.ComponentQuery.query(gridNmae)[0].switchViewPreset('hourAndDay',stratDate, endDate);
	                                   }
	                               },
	                               {
	                                   text         : '按天',
	                                   toggleGroup  : 'presets',
	                                   enableToggle : true,
	                                   iconCls      : 'icon-calendar',
	                                   handler      : function () {
	                                	   Ext.ComponentQuery.query(gridNmae)[0].switchViewPreset('hourAndDayOit',stratDate, endDate);
	                                   }
	                               }
	                               ,
	                               {
	                                   text         : '按周',
	                                   toggleGroup  : 'presets',
	                                   enableToggle : true,
	                                   iconCls      : 'icon-calendar',
	                                   handler      : function () {
	                                	   Ext.ComponentQuery.query(gridNmae)[0].switchViewPreset('dayAndWeek',stratDate, endDate);
	                                	  
	                                   }
	                               }
	                   ],
	    plugins:{
	        ptype: 'treeviewdragdrop',
	        containerScroll : true
	    },
	    tooltipTpl : new Ext.XTemplate(
	            '<dl class="eventTip">', 
	            '<dt  >客户合同号:{[values.contractNo]}</dt>',
	            '<dt  >客户生产订单号:{[values.name]}</dt>',
	            '<dt  >生产单号:{[values.workOrderNo]}</dt>',
	            '<dt  >产品代码:{[values.productCode]}</dt>',
	            '<dt  >产品规格:{[values.productSpec]}</dt>',
	            '<dt  >产出半成品代码:{[values.halfProductCode]}</dt>',	            
	            '<dt  >计划加工长度(米):{[values.taskLength]}</dt>',
	            '<dt  >计划开工日期:{[Ext.Date.format(values.startDate, "y-m-d G:i")]}</dt>',
	            '<dt  >计划完工日期:{[Ext.Date.format(values.endDate, "y-m-d G:i")]}</dt>',
	            '<dt  >进度(%):{[values.percentDone]}</dt>',
	            '</dl>'
	        ).compile()

	});


