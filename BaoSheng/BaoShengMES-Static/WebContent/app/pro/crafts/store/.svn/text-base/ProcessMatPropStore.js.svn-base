Ext.define('bsmes.store.ProcessMatPropStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.ProcessMatProp',
			autoLoad : false, // 默认false
			proxy : {
				type : 'rest',
				url : '../inv/prop',
				reader : {
					type : 'json',
					root : 'rows'
				}
			},
			sorters : [{
						property : 'propName',
						direction : 'ASC'
					}]

		});