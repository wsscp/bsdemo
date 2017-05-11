Ext.define('bsmes.model.WorkOrderEquipList',{
	extend:'Ext.data.Model',
	fields : [
	  	'id',
	  	'equipCode',
	  	'equipName',
		'processId',
		{
      		name:'type',
      		type:'string',
      		renderer:function(value){
      			if(value == 'PRODUCT_LINE'){
      				return Oit.msg.pro.equipList.equipTypeLine;
      			} else {
      				return Oit.msg.pro.equipList.equipTypeDefault;
      			}
      		}
      	},
		'equipCapacity',
		'setUpTime',
		'shutDownTime']
});