Ext.define('bsmes.store.ProcessQcStore',{
    extend:'Ext.data.Store',
    fields:['checkItemName', 'frequence', 'id', 'processId'],
    proxy:{
        type: 'rest',
        url:'customerOrderItem/queryProcessQc'
    },
    autoLoad:false
});