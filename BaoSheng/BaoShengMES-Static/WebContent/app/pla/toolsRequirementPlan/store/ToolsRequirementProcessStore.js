Ext.define('bsmes.store.ToolsRequirementProcessStore', {
	extend : 'Ext.data.Store',
	model :'bsmes.model.ToolsRequirementProcess',
    proxy : {
        type: 'rest',
        url : 'toolsRequirementPlan/process'
    }
});