Ext.define("bsmes.view.WorkOrderProductList", {
	extend : 'Ext.grid.Panel',
	alias : 'widget.workOrderProductList',
	store : 'WorkOrderProductStore',
	animCollapse : true,
	columnLines : true,
	//autoWidth : true,
	//autoHeight : true,
	columns : [ {
		text : Oit.msg.wip.workOrder.productType,
		width:250,
		dataIndex : 'productType'
	}, {
		text : Oit.msg.wip.workOrder.productSpec,
		width:250,
		dataIndex : 'productSpec'
	}]
});
