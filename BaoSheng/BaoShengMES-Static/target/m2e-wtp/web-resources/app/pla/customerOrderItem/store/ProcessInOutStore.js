Ext.define('bsmes.store.ProcessInOutStore',{
    extend:'Ext.data.Store',
    fields:['matName', 'quantity', 'id', 'processId'],
    proxy:{
        type: 'rest',
        url:'customerOrderItem/queryProcessInOut'
    },
    autoLoad:false
});