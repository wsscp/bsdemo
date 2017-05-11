Ext.define("bsmes.view.RawMaterialInvTransSubList",{
		extend 	: 'Ext.grid.Panel',
		alias 	: 'widget.rawMaterialInvTransSubList',
		store 	: 'RawMaterialInvTransSubStore',
		labelWidth : 'auto',
		forceFit : true,
		columns	:[
		       	   {
		       		   text:Oit.msg.wip.rawMaterialInvTrans.beforeQuantity,
		       		   dataIndex:'beforeQuantity'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.transactionQuantity,
		       		   dataIndex:'quantity'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.afterQuantity,
		       		   dataIndex:'afterQuantity'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.transactionType,
		       		   dataIndex:'type',
		       		   renderer:function(value){
		       			   if(value =="OUT"){
		       				   return "出库";
		       			   }else if(value =="IN"){
		       				   return "入库";
		       			   }else{
		       				   return value;
		       			   }
		       		   }
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.transactionTime,
		       		   dataIndex:'createTime',
		       		   xtype:'datecolumn',
		       		   format:'Y/m/d H:i'
		       	   },{
		       		   text:Oit.msg.wip.rawMaterialInvTrans.operator,
		       		   dataIndex:'userName'
		       	   }
	       	   ]
});