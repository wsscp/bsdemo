Ext.define('bsmes.store.MaterialNameStore', {
			extend : 'Oit.app.data.GridStore',
			fields : ['MATNAME'],
			autoLoad : false,
			proxy : {
				type : 'rest',
				url : 'materialRequirementPlan/getAllMatName'
			}
		});