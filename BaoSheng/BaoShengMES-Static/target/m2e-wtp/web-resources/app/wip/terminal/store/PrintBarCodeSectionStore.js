Ext.define('bsmes.store.PrintBarCodeSectionStore', {
    extend : 'Oit.app.data.GridStore',
    model : 'bsmes.model.Section',
    autoLoad:false,
    proxy:{
        type:'rest',
        url:'printSection'
    }
});