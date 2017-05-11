Ext.define("bsmes.view.ProcessReoprtGrid", {
	extend : 'Oit.app.view.Grid',
	id:'processReoprtGridId',
	alias : 'widget.processReoprtGrid',
	store : 'ProcessTraceReportStore',
	forceFit:true,
	columns : [{
		text : Oit.msg.wip.processTraceReport.contractNo,
		dataIndex : 'contractNo'
	},{
		text : Oit.msg.wip.processTraceReport.productCode,
		dataIndex : 'productCode'
	},{
		text : Oit.msg.wip.processTraceReport.processCode,
		dataIndex : 'processName'
	},{
		text : Oit.msg.wip.processTraceReport.equipCode,
		dataIndex : 'equipCode'
	},{
		text : Oit.msg.wip.processTraceReport.realStartTime,
		xtype : 'datecolumn',
	    format : 'Y-m-d H:i:s',  
		dataIndex : 'realStartTime'
	},{
		text : Oit.msg.wip.processTraceReport.realEndTime,
		xtype : 'datecolumn',
	    format : 'Y-m-d H:i:s',
		dataIndex : 'realEndTime'
	},{
		text : Oit.msg.wip.processTraceReport.orderlength,
		dataIndex : 'orderlength'
	},{
		text : Oit.msg.wip.processTraceReport.reportUserCode,
		dataIndex : 'reportUserCode'
	}]
});