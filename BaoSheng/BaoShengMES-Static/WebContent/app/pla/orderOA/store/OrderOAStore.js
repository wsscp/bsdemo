Ext.define('bsmes.store.OrderOAStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.OrderOA',
			sorters : [{
						property : 'createTime',
						direction : 'DESC'
					}],
			proxy : {
				type : 'rest',
				url : 'orderOA',
				extraParams : {
					orderItemStatus : 'TO_DO',
					orderItemStatus : 'IN_PROGRESS'
				}
			}
		});