Ext.define('bsmes.store.SortAndCalculateStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.SortAndCalculate',
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'handSchedule/findForHandSetPriority'
			}
		});