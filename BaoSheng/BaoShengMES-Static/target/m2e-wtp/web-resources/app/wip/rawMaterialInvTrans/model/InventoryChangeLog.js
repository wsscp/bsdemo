Ext.define("bsmes.model.InventoryChangeLog",{
	extend 	: 'Ext.data.Model',
	fields	:[
		     	  'id',
		     	  'beforeQuantity',
		     	  'quantity',
		     	  'afterQuantity',
		     	  'type',
		     	  {
		     		  name:'createTime',
		     		  type:'date'
		     	  },
		     	  'userName'
	     	  ]
});