Ext.define("bsmes.store.QualityTraceStore", {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.QualityTrace',
			sorters : [{
						property : 'createTime',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'qualityTrace'
			}
		});