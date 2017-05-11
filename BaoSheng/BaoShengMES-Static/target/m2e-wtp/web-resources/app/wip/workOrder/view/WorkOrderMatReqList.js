Ext.define("bsmes.view.WorkOrderMatReqList", {
	extend : 'Ext.grid.Panel',
	alias : 'widget.workOrderMatReqList',
	store : 'WorkOrderMatReqStore',
	animCollapse : true,
	columnLines : true,
	autoWidth : true,
	columns : [ {
		text : Oit.msg.wip.workOrder.matName,
		width:125,
		dataIndex : 'matName'
	}, {
		text : Oit.msg.wip.workOrder.matCode,
		width:125,
		dataIndex : 'matCode'
	}, {
		text : Oit.msg.wip.workOrder.quantity,
		width:125,
		dataIndex : 'quantity'
	}, {
		text : Oit.msg.wip.workOrder.unit,
		width:125,
		dataIndex : 'unit',
		renderer:function(value){
			if(value=='TON'){
				return "吨";
			}else if(value=='KG'){
				return "千克";
			}else if(value=='KM'){
				return "千米";
			}else if(value=='M'){
				return "已取消";
			}
		}
	}]
});
