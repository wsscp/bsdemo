Ext.define('bsmes.store.WorkOrderStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.WorkOrder',
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'handSchedule/showWorkOrder'
			}
		});