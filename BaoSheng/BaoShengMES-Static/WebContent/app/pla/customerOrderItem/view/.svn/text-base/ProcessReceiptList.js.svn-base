Ext.define('bsmes.view.ProcessReceiptList',{
    extend:'Ext.grid.Panel',
    alias:'widget.processReceiptList',
    store: 'ProcessReceiptStore',
    forceFit : true,
    columns: [
                {
                    text: Oit.msg.pla.customerOrderItem.index,
                    xtype: 'rownumberer',
                    width: 100},
                {
                    text: Oit.msg.pla.customerOrderItem.receiptName,
                    dataIndex: 'receiptName'},
                {
                    text: Oit.msg.pla.customerOrderItem.receiptTargetValue,
                    dataIndex: 'receiptTargetValue'}
            ],
    width: '100%'
});