/**
 * Created by joker on 2014/6/6 0006.
 */
Ext.define('bsmes.store.EquipReportStore',{
    extend : 'Oit.app.data.GridStore',
    model : 'bsmes.model.Report',
    proxy:{
        type:'rest',
        url:'terminal/reportDetail/'+Ext.fly('equipInfo').getAttribute('code')
    }
});