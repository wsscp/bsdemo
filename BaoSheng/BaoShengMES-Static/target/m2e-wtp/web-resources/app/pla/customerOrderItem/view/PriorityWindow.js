Ext.define('bsmes.view.PriorityWindow',{
	extend:'Ext.window.Window',
    title: Oit.msg.pla.customerOrderItem.adjustmentOrderPriority,  
    layout: "border",  
    alias: 'widget.priorityWindow',
    width: 1300,  
    height: 543,  
    modal: true,  
    closeAction:'destory',
    plain: true,  
    requires:[
              'bsmes.view.PriorityLeftList',
              'bsmes.view.PriorityCenter',
              'bsmes.view.PriorityRightList'],
    items: [  
            {	
            	title: Oit.msg.pla.customerOrderItem.optionalOrder,
            	region: "west",
            	collapsible: false,
            	split: true,
            	width: 600,
            	items: [{
            				xtype:'leftGridView'
            			}]
            },{	
            	region: "center",
	            split:true,
	            width: 50,
	            height: 500, 
	            items:[{
			            	xtype:'centerView'
			            }]
            },{	
            	title:Oit.msg.pla.customerOrderItem.selectedOrder,
            	region: "east",
            	split: true,
            	width: 600,
            	items: [{
        					xtype:'rightGridView'
        				}]
            }],
    buttons: [  {
    				text:Oit.btn.ok,
    				itemId:'ok'
    			},{
    				text:Oit.btn.cancel,
    				itemId:'cancel'
    			}
             ]
});