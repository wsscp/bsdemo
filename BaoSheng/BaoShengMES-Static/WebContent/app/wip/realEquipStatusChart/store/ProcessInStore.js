Ext.define('bsmes.store.ProcessInStore',{
    extend:'Oit.app.data.GridStore',
    model:'bsmes.model.ProcessIn',
    proxy:{
        type:'rest',
        url:'terminal/processMatIn/' + Ext.fly('orderInfo').getAttribute('num'),
        reader: {
            type: 'json',
            root: 'rows'
        }
    }
});