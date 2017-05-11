Ext.define('bsmes.store.ProductReportStore',{
    extend : 'Oit.app.data.GridStore',
    model : 'bsmes.model.Report',
    autoLoad : false,
    proxy:{
        type:'rest',
        url:'equipEnergyMonitor/equipEnergyInfo'
    }
});	