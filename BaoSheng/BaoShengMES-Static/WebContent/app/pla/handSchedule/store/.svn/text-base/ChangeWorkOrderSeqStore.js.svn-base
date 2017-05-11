Ext.define('bsmes.store.ChangeWorkOrderSeqStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.WorkOrder',
			autoLoad : false,
			sorters : [{
						"property" : "releaseDate",
						"direction" : "DESC"
					}],
			proxy : {
				type : 'rest',
				url : 'handSchedule/changeWorkOrderSeq'
			}
		});