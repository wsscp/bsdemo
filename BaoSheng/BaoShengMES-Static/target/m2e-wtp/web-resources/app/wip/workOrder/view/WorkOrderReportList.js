Ext.define("bsmes.view.WorkOrderReportList", {
    extend: 'Ext.grid.Panel',
    alias: 'widget.workOrderReportList',
    forceFit : false,
    height:150,
    columnLines: true,
    autoHeight : true,
    frame: false,
    columns : [{
                    text : Oit.msg.wip.workOrder.productType,
                    dataIndex : 'productType',
                    width:200
                },{
                    text : Oit.msg.wip.workOrder.productSpec,
                    dataIndex : 'productSpec',
                    width:200
                },{
                    text : Oit.msg.wip.workOrder.color,
                    dataIndex : 'color',
                    width:200
                },{
                    text : Oit.msg.wip.workOrder.reportNum,
                    dataIndex : 'reportLength',
                    width:150
                },{
                    text : Oit.msg.wip.workOrder.serialNum,
                    dataIndex : 'serialNum',
                    width:300
                },{
                    text : Oit.msg.wip.workOrder.reportUserCode,
                    dataIndex : 'reportUserName',
                    width:200
                },{
                    text : Oit.msg.wip.workOrder.reportTime,
                    dataIndex : 'reportTime',
                    width:200,
                    xtype : 'datecolumn',
                    format : 'Y-m-d H:i:s'
                }]
});
