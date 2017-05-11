Ext.define('bsmes.store.LocationStore',{
    extend:'Ext.data.Store',
    fields:['id','locationName'],
    autoLoad:false,
    proxy:{
        type: 'rest',
        url:'locations'
    }
});