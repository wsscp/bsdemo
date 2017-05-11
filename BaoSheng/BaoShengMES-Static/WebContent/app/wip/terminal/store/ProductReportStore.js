/**
 * Created by joker on 2014/6/6 0006.
 */
Ext.define('bsmes.store.ProductReportStore',{
    extend : 'Oit.app.data.GridStore',
    model : 'bsmes.model.Report',
    autoLoad:false,
    proxy:{
        type:'rest',
        url:'terminal/productInformation'
    }
});