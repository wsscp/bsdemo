Ext.define("bsmes.view.WorkOrderToolsReqList", {
	extend : 'Ext.grid.Panel',
	alias : 'widget.workOrderToolsReqList',
	store : 'WorkOrderToolsReqStore',
	animCollapse : true,
	columnLines : true,
	// autoWidth : true,
	// autoHeight : true,
	columns : [ {
		text : Oit.msg.wip.workOrder.tools,
		width:250,
		dataIndex : 'tools'
	}, {
		text : Oit.msg.wip.workOrder.quantity,
		width:250,
		dataIndex : 'quantity'
	} ]
});
