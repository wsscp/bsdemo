Ext.define('bsmes.store.ToolsRequirementPlanStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.ToolsRequirementPlan',
	sorters : [ {
		property : 'planDate',
		direction : 'ASC'
	} ],
	proxy : {
		type: 'rest',
		url : 'toolsRequirementPlan'
	}
});