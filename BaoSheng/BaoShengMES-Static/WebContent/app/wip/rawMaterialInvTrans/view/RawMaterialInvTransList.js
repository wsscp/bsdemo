Ext.define("bsmes.view.RawMaterialInvTransList",{
		extend 	: 'Oit.app.view.Grid',
		alias 	: 'widget.rawMaterialInvTransList',
		store 	: 'RawMaterialInvTransStore',
		forceFit : false,
		plugins: [{  
	        ptype: 'rowexpander',
	        rowBodyTpl: [  
	                     '<div id="{id}">',  
	                     '</div>'  
	                 ]  
	    }],  
	    selType:'rowmodel',
		columns	:[
		       	   {
		       		   text:Oit.msg.wip.rawMaterialInvTrans.materialCode,
		       		   width:250,
		       		   dataIndex:'materialCode'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.materialName,
		       		   width:200,
		       		   dataIndex:'materialName'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.barCode,
		       		   width:250,
		       		   dataIndex:'barCode'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.createTime,
		       		   dataIndex:'createTime',
		       		   width:250,
		       		   xtype:'datecolumn',
		       		   format: 'Y/m/d H:i'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.locationName,
		       		   width:150,
		       		   dataIndex:'locationName'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.quantity,
		       		   width:100,
		       		   dataIndex:'quantity'
		       	   }
	       	   ], 
	       	dockedItems :[ {
	    		xtype : 'toolbar',
	    		dock : 'top',
	    		items : [ {
	    			id :'equipProcessTraceSearchForm',
	    			xtype : 'form',
	    			width : '100%',
	    			layout : 'hbox',
	    			buttonAlign : 'left',
	    			labelAlign : 'right',
	    			bodyPadding : 1,
	    			defaults : {
	    				xtype : 'panel',
	    				// width : '100%',
	    				layout : 'vbox',
	    				defaults : {
	    					labelAlign : 'right'
	    				}
	    			},
	    			items : [{
	    						fieldLabel: Oit.msg.wip.rawMaterialInvTrans.materialCode,
	    						xtype : 'textfield',
	    						name: 'materialCode'
	    					
	    				},{
	    					 	fieldLabel: Oit.msg.wip.rawMaterialInvTrans.locationName,
	    					 	xtype : 'textfield',
						        name: 'locationName'
	    				}],
	    			buttons : [ {
	    				itemId : 'search',
	    				text : Oit.btn.search
	    			}, {
	    				itemId:'reset',
	    				text : '重置',
	    				handler : function(e) {
	    					this.up("form").getForm().reset();
	    				}
	    			},{
	    				itemId:'exportToXls',
	    				text : '导出到xls',
	    			}]
	    		}]
	    	} ],
    	initComponent: function () {  
            var me = this;  
            this.callParent(arguments);
            me.view.on('expandBody', function (rowNode, record, expandRow, eOpts) {
            	var renderId = record.get('id');
            	var quantity = record.get('quantity');
            	var url= "rawMaterialInvTrans/inventoryLogs/"+renderId+"/"+quantity;

            	var subGrid = Ext.create("bsmes.view.RawMaterialInvTransSubList",{
            		renderTo: renderId  
            	});
            	
            	var innerStore=subGrid.getStore();// Ext.create('bsmes.store.WorkOrderSubStore');
            	innerStore.getProxy().url=url;
            	innerStore.reload();
            	subGrid.getEl().swallowEvent([  
                    'mousedown', 'mouseup', 'click',  
                    'contextmenu', 'mouseover', 'mouseout',  
                    'dblclick', 'mousemove'  
                ]);
            }); 
            me.view.on('collapsebody', function (rowNode, record, expandRow, eOpts) {  
            	var parent = document.getElementById(record.get('id'));  
                var child = parent.firstChild;  
                while (child) {  
                    child.parentNode.removeChild(child);  
                    child = child.nextSibling;  
                }   
            }); 
          
        } 
});