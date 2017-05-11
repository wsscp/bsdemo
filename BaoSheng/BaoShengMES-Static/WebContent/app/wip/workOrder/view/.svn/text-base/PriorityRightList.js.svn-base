Ext.define('bsmes.view.PriorityRightList', {
    extend: 'Ext.grid.Panel',
    id: 'rightGrid',
    stripeRows: true,
    selType: 'checkboxmodel',
    alias: 'widget.rightGridView',
    store: 'RightStore',
    forceFit: true,
    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            text: Oit.msg.wip.workOrder.workOrderNO,
            dataIndex: 'workOrderNo'
        },
        {text: Oit.msg.wip.workOrder.halfProductCode, dataIndex: 'halfProductCode'}
    ],
    tbar: [
        {
            itemId: 'move',
            text: Oit.msg.wip.btn.move
        },
        {
            itemId: 'down',
            text: Oit.msg.wip.btn.down
        },
        {
            itemId: 'top',
            text: Oit.msg.wip.btn.top
        },
        {
            itemId: 'end',
            text: Oit.msg.wip.btn.end
        }
    ]

});