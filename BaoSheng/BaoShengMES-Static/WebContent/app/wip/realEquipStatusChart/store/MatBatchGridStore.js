/**
 * Created by joker on 2014/6/24 0024.
 */
Ext.define('bsmes.store.MatBatchGridStore',{
    extend: 'Oit.app.data.GridStore',
    autoLoad:false,
    model:'bsmes.model.RealCost',
    proxy:{
        reader: {
            type: 'json',
            root: 'rows'
        }
    }
});