//Employee
Ext.define('bsmes.model.EquipProcessTrace', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'equipCode',
	    'reEquipCode',
	    'equipName',
	    'batchNum',
	    'workOrderNo',
	    'preStartTime',
	    'preEndTime',
	    'processCode',
   	    'processName',
	    'realStartTime',
	    'realEndTime',
	    'userCode',
	    {
            name: 'usedTime',
            convert: function(value, record) {
                var realStartTime  = record.get('realStartTime');
                var realEndTime  = record.get('realEndTime');
                var minutes = 0;
                if (realStartTime && realEndTime) {
                	var startTime = Ext.Date.parse(realStartTime, "Y-m-d H:i:s");
                	var endTime = Ext.Date.parse(realEndTime, "Y-m-d H:i:s");
                	
                	minutes = (endTime.getTime()-startTime.getTime())/60000;
                }else if(realStartTime){
                	var startTime = Ext.Date.parse(realStartTime, "Y-m-d H:i:s");
                	var endTime = new Date();
                	
                	minutes = (endTime.getTime()-startTime.getTime())/60000;
                }
    			var t = parseInt(minutes/(60*24));
    			var h = parseInt(minutes/(60))%(24);
    			var m = parseInt(minutes%60);
                return t+'天'+h+'时'+m+'分';
            }
        },
        'orderLength',
	    'contractNo',
	    'ContractInfo'
	 ]
});