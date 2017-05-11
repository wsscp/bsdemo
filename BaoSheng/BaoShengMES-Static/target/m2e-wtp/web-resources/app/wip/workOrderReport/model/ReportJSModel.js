Ext.define('bsmes.model.ReportJSModel', {
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
	    'jsThicknessMin', // 挤塑厚度/min
	    'jsThicknessMax', // 挤塑厚度/max
	    'jsFrontOuterdiameterMin', // 挤塑前外径/min
	    'jsFrontOuterdiameterMax', // 挤塑前外径/max
	    'jsBackOuterdiameterMin', // 挤塑后外径/min
	    'jsBackOuterdiameterMax', // 挤塑后外径/max
	    'finishedLength', // 制造长度
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