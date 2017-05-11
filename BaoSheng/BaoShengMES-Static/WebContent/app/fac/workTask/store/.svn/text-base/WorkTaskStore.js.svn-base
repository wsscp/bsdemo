Ext.define('bsmes.store.WorkTaskStore', {
    extend : 'Sch.data.ResourceTreeStore',
    model : 'bsmes.model.WorkTask',
    proxy: {
    	type: 'ajax',
    	url :'/bsmes/fac/workTask/loadData',
    	reader: {
            type: 'json'
        }
    },
    folderSort:true
});
