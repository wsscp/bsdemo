Ext.define('bsmes.store.ReportSectionStore', {
    extend : 'Oit.app.data.GridStore',
    model : 'bsmes.model.Section',
    autoLoad:false,
    proxy:{
        type:'rest',
        url:'reportSection'
    }
});