Ext.define('bsmes.model.ReportHHPTModel', {
	extend : 'Ext.data.Model',
	fields : [ 
	    'id', 
	    'contractNo',
	    'workOrderNo',
	    'equipCode',
	    { name: 'shiftId', convert: function(value, record) {
	            return value;
	        }
	    },
	    'shiftName',
	    'productType',
	    'productSpec',
	    'workHours',
	    'dbWorker',
	    'fdbWorker',
	    'fzgWorker',
	    'reportDate',
	    'colorOrWord', // 色别或字码
	    'testVoltage', // 试验电压
	    'punctureNum', // 击穿次数
	    'lineSpeed', // 线速度m/s
	    'planLength', // 计划长度
	    'realLength', // 实际长度
	    'testing', // 检验
	    'quality', // 质量
		'operator',
	    'equipName'
	 ]
});