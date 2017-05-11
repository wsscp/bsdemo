Ext.define('bsmes.store.SummarizeStore', {
	extend : 'Oit.app.data.GridStore',
	model :'bsmes.model.Summarize',
	autoLoad :　false,　
	sorters : [ {
		property : 'planDate',
		direction : 'DESC'
	} ],
	proxy : {
		type: 'rest',
		url : 'materialRequirementPlan/sumPlanData'
	}
});