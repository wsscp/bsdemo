Ext.define('bsmes.store.StockStore',{
        extend:'Ext.data.Store',
        fields:['id', 'length'],
        autoLoad:false,
        proxy:{
            type: 'rest',
            url:'customerOrderItem/queryInventorys'
        }
});