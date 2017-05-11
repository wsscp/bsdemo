/**
 * Created by joker on 2014/5/28 0028.
 */
Ext.define('bsmes.store.ParmCodeStore',{
    extend:'Ext.data.Store',
    fields: ['itemCode','itemCode'],
    proxy:{
        type:'ajax',
        url:'equipMESWWMapping/getParmCode'
    }
});