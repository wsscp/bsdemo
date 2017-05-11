Ext.define("bsmes.store.ProcessReceiptStore",{
    extend:'Ext.data.Store',
    fields:['receiptName', 'receiptTargetValue', 'id', 'processId'],
    proxy:{
        type: 'rest',
        url:'customerOrderItem/queryProcessReceipt'
    },
    autoLoad:false
});