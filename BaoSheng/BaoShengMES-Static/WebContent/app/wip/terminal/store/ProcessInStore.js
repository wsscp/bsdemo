Ext.define('bsmes.store.ProcessInStore',{
    extend:'Oit.app.data.GridStore',
    model:'bsmes.model.ProcessIn',
    autoLoad : false,
    proxy:{
        type:'rest',
        url:'processMatIn/' + Ext.fly('orderDetail').getAttribute('num')+'/'+Ext.fly('processInfo').getAttribute('section'),
        reader: {
            type: 'json',
            root: 'rows'
        }
    }
});