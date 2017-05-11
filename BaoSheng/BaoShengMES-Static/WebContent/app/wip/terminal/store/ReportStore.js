/**
 * Created by JinHanyun on 2014/5/27 0027.
 */
Ext.define('bsmes.store.ReportStore',{
    extend:'Oit.app.data.GridStore',
    fields : [  'id',
                'serialNum',
                'reportLength',
                'goodLength',
                'productType',
                'productSpec',
                'color',
                'coilNum'],
    autoLoad:false,
    proxy:{
        url : 'queryReport'
    }
});