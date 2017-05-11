Ext.define('bsmes.view.ProcessInOutList',{
    extend:'Ext.grid.Panel',
    alias:'widget.processInOutList',
    store: 'ProcessInOutStore',
    forceFit : true,
    columns: [
        {
            text: Oit.msg.pla.customerOrderItem.index,
            xtype: 'rownumberer',
            width: 100},
        {
            text: Oit.msg.pla.customerOrderItem.matName,
            dataIndex: 'matName'},
        {
            text: Oit.msg.pla.customerOrderItem.quantity,
            dataIndex: 'quantity'}
    ],
    width: '100%'
});