Ext.define("bsmes.model.Receipt",{
	extend 	: 'Ext.data.Model',
	fields	:[
	     	  'workOrderNo',
	     	  'productCode',
	     	  'equipCode',
	     	  'receiptCode',
	     	  'receiptName',
	     	  'receiptTargetValue',
	     	  'receiptMaxValue',
	     	  'receiptMinValue',
	     	  'createUserCode',
	     	  {
	     		  name:'type',type:'string',
	     		  renderer:function(value){
	     			if(value=='PROCESS_RECEIPT'){
	     				return  Oit.msg.wip.receipt.processReceipt;
	     			}else if(value=='QA_RECEIPT'){
	     				return  Oit.msg.wip.receipt.qaReceipt;
	     			}else{
	     				return '';
	     			}
	     		}
	     	  },
	     	  {
	  			name : 'issuedTime',
	  			type : 'date'
	     	  }]
});
