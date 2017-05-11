Ext.define("bsmes.view.ToolsRequirementPlanList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.toolsRequirementPlanList',
	store : 'ToolsRequirementPlanStore',
	defaultEditingPlugin:false,
	columns : [{
		text : Oit.msg.pla.materialRequirementPlan.processName,
		dataIndex : 'processName'
	},{
		text : Oit.msg.pla.materialRequirementPlan.processCode,
		dataIndex : 'processCode'
	},{
		text : Oit.msg.pla.materialRequirementPlan.planDate,
		dataIndex : 'planDate',
		renderer:Ext.util.Format.dateRenderer('Y-m-d') 
	},{
        text : Oit.msg.pla.materialRequirementPlan.workOrderNO,
        dataIndex : 'workOrderNO'
    },{
		text : Oit.msg.pla.materialRequirementPlan.productLine,
		dataIndex : 'equipCode'
	},{
        text : Oit.msg.pla.materialRequirementPlan.tooles,
        dataIndex : 'tooles'
    },{
		text :Oit.msg.pla.materialRequirementPlan.quantity,
		dataIndex : 'quantity'
	}],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			title : '查询条件',
			xtype : 'fieldset',
			width : '100%',
			items : [ {
				id :'workOrderSearchForm',
				xtype : 'form',
				width : '100%',
				layout : 'vbox',
				buttonAlign : 'left',
				labelAlign : 'right',
				bodyPadding : 5,
				defaults : {
					xtype : 'panel',
					width : '100%',
					layout : 'hbox',
					defaults : {
						labelAlign : 'right'
					}
				},
				items : [ {
					items : [ {
						fieldLabel : Oit.msg.pla.materialRequirementPlan.planDate,
						xtype : 'datefield',
						name : 'planDate',
						format: 'Y-m-d'
					}, {
						fieldLabel :Oit.msg.pla.materialRequirementPlan.section,
						xtype : 'textfield'
					}]
				},{
					height : 5
				}, {
					items : [{
						fieldLabel : Oit.msg.pla.materialRequirementPlan.processName,
						xtype : 'combobox',
						name : 'processCode',
						editable:false,
						mode:'remote',
                        displayField:'processName',
                        valueField:'processCode',
						store:'ToolsRequirementProcessStore'
					}, {
						fieldLabel :Oit.msg.pla.materialRequirementPlan.tooles,
						xtype : 'combobox',
						name : 'tooles',
						editable : false,  
						mode:'remote',
						displayField:'name',
                        valueField:'code',
						store:'ToolsRequirementEquipmentStore'
					}]
				}],
				buttons : [{
					itemId : 'search',
					text : Oit.btn.search
				}, {
					itemId:'reset',
					text : '重置',
					handler : function(e) {
						this.up("form").getForm().reset();
					}
				},{
                    itemId:'export',
                    text:Oit.btn.export
                }]
			} ]
		}]
	}]
});


