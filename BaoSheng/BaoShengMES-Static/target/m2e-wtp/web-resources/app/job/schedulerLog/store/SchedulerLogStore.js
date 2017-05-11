Ext.define('bsmes.store.SchedulerLogStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.SchedulerLog',
	sorters : [{}],
	proxy : {
		url : 'schedulerLog'
	}
});