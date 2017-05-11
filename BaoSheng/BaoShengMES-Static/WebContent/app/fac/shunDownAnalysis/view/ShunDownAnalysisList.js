Ext.define("bsmes.view.ShunDownAnalysisList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.shunDownAnalysisList',
	store : 'ShunDownAnalysisStore',
	defaultEditingPlugin : false,
	columns : [{
		text : Oit.msg.fac.shunDownAnalysis.equipCode,
		dataIndex : 'equipCode'
	},{
		text : Oit.msg.fac.shunDownAnalysis.equipName,
		width:200,
		dataIndex : 'equipName'
	},{
		text : Oit.msg.fac.shunDownAnalysis.reason,
		dataIndex : 'reason'
	},{
		text : Oit.msg.fac.shunDownAnalysis.startTime,
		dataIndex : 'startTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		text : Oit.msg.fac.shunDownAnalysis.endTime,
		dataIndex : 'endTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		text : Oit.msg.fac.shunDownAnalysis.timeBet,
		dataIndex : 'timeBet'
	}],
	actioncolumn : [{
    	itemId : 'edit'
    }],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			title : '查询条件',
			xtype : 'fieldset',
			collapsible : true,
			width : '100%',
			items : [ {
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
				items : [{
					items : [{
						fieldLabel : '生产线',
						xtype : 'combobox',
						name : 'equipCode',
						editable:false,  
						mode:'remote',
						displayField:'text',
						valueField : 'value',
						width: 420,
						store:new Ext.data.Store({
							fields:[{name:'text',mapping:'name'},{name:'value',mapping:'code'}],
							proxy:{
								type: 'rest',
								url:'shunDownAnalysis/equip'
							}
						})
					},{
                        fieldLabel :Oit.msg.fac.shunDownAnalysis.startTime,
                        xtype : 'datefield',
                        name : 'startTime',
                        format: 'Y-m-d',
                        value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.MONTH,-1),"Y-m-d")
                    },{
                        fieldLabel :Oit.msg.fac.shunDownAnalysis.endTime,
                        xtype : 'datefield',
                        name : 'endTime',
                        format: 'Y-m-d',
                        value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.MONTH,0),"Y-m-d")
                    },{
                    	fieldLabel:Oit.msg.fac.shunDownAnalysis.isCompleted,
                        name: 'isCompleted',
						xtype : 'combobox',
						displayField: 'value',
					    valueField: 'isCompleted',
					    editable:false,
					    store:new Ext.data.Store({
						    fields:['isCompleted', 'value'],
						    data : [
						        {"value":Oit.msg.fac.shunDownAnalysis.all, "isCompleted":"all"},
						        {"value":Oit.msg.fac.shunDownAnalysis.yes, "isCompleted":"yes"},
						        {"value":Oit.msg.fac.shunDownAnalysis.no, "isCompleted":"no"}
					        ]
						})
                    }]
				}],
				buttons : [ {
					itemId : 'search',
					text : Oit.btn.search
				}, {
					itemId : 'reset',
					text : '重置',
					handler : function(e) {
						this.up("form").getForm().reset();
					}
				},{
					itemId : 'exportToXls',
					text : Oit.btn.export
				}]
			} ]
		} ]
	} ]
});