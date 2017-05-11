Ext.define('bsmes.controller.MaterialRequirementPlanController', {
	extend : 'Oit.app.controller.GridController',
	view : 'materialRequirementPlanList',
	views : ['MaterialRequirementPlanList','SummarizeWindow','SummarizeGrid'],
	stores : ['MaterialRequirementPlanStore','SummarizeStore'],
    exportUrl:'materialRequirementPlan/export/物料需求计划',
    
    constructor : function() {
		var me = this;

		// 初始化refs
		me.refs = me.refs || [];
		me.refs.push({
			ref : 'summarizeWindow',
			selector : 'summarizeWindow',
			autoCreate : true,
			xtype : 'summarizeWindow'
		});
		me.refs.push({
			ref : 'materialRequirementPlanList',
			selector : 'materialRequirementPlanList',
			autoCreate : true,
			xtype : 'materialRequirementPlanList'
		});
		me.refs.push({
			ref : 'summarizeGrid',
			selector : 'summarizeGrid',
			autoCreate : true,
			xtype : 'summarizeGrid'
		});
		me.callParent(arguments);
	},
	init : function() {
		var me = this;

		// 初始化工具栏
		me.control(me.view + ' button[itemId=summarize]', {
					click : me.onSummarize
				});

		me.callParent(arguments);
	},
	
	onSummarize : function() {
		var me = this, orderItemIds = [];
		var win = me.getMaterialRequirementPlanList();
		var form = win.down('form').getForm();
		var value = form.findField('planDate').getValue();
		if (value == null || value == '') {
			Ext.Msg.alert('提示','请选择需求日期');
			return;
		}
		var win = me.getSummarizeWindow();
		var store = me.getSummarizeGrid().getStore();
		store.load({
			params:{
				'planDate' : value
			}
		});
		win.show();
	}
});