Ext.define('bsmes.store.WorkOrderStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.WorkOrder',
			sorters:[{"property":"releaseDate","direction":"DESC"}, {"property":"realEndTime","direction":"DESC"}],
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'handSchedule/showWorkOrder'
			}
		});
