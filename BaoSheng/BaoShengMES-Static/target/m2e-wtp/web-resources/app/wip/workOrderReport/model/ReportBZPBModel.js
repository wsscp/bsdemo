Ext.define('bsmes.model.ReportBZPBModel', {
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
	    'outerPosition', // 外层方向
	    'beltPosition', // 包带方向
	    'cuCoverLevel', // 铜丝覆盖率或铜带重叠率
	    'planLength', // 计划长度
	    'realLength', // 实际长度
	    'testing', // 检验
	    'quality', // 质量
	    'kind', // 种类
	    'preLeave', // 上班盘存
	    'thisTake', // 本班领用
	    'thisBack', // 本班退用
	    'operator',
	    'equipName'
		
	 ]
});