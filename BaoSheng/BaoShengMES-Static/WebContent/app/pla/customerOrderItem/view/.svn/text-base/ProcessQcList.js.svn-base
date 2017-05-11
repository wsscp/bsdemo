Ext.define('bsmes.view.ProcessQcList',{
    extend:'Ext.grid.Panel',
    alias:'widget.processQcList',
    store: 'ProcessQcStore',
    forceFit : true,
    columns: [
        {
            text: Oit.msg.pla.customerOrderItem.index,
            xtype: 'rownumberer',
            width: 100},
        {
            text: Oit.msg.pla.customerOrderItem.receiptName,
            dataIndex: 'checkItemName'},
        {
            text: Oit.msg.pla.customerOrderItem.frequence,
            dataIndex: 'frequence'}
    ],
    width: '100%'
});