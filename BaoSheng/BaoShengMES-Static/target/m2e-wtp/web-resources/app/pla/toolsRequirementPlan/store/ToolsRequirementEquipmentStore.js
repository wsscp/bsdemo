Ext.define('bsmes.store.ToolsRequirementEquipmentStore', {
	extend : 'Ext.data.Store',
	model :'bsmes.model.ToolsRequirementEquipment',
    proxy : {
        type: 'rest',
        url : 'toolsRequirementPlan/equipment'
    }
});