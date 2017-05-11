Ext.define('bsmes.model.InvLocationModel', {
	extend : 'Ext.data.Model',
	fields : [
	  {name : 'id',type : 'string'},
      {name : 'materialCode',type : 'string'},
      {name : 'serialNum',type : 'string'},
      {name : 'locationName',type : 'string'}
      
	]
});