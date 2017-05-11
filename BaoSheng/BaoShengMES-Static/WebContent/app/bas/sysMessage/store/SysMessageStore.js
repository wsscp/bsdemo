Ext.define('bsmes.store.SysMessageStore', {
	extend : 'Oit.app.data.GridStore',
	model : 'bsmes.model.SysMessage',
	//fields: ['name', 'id'],
	sorters : [ {
		property : 'messageTitle',
		direction : 'ASC'
	} ],
	proxy : {
		url : 'sysMessage'
	}
});