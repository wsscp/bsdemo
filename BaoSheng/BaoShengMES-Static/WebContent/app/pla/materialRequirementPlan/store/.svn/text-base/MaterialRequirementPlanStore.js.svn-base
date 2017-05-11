Ext.define('bsmes.store.MaterialRequirementPlanStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.MaterialRequirementPlan',
	sorters : [ {
		property : 'planDate',
		direction : 'DESC'
	} ],
	proxy : {
		type: 'rest',
		url : 'materialRequirementPlan'
	}
});