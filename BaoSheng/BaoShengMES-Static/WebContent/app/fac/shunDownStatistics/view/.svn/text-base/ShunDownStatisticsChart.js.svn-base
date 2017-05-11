Ext.define("bsmes.view.ShunDownStatisticsChart", {
	extend : 'Ext.container.Container',
	alias : 'widget.shunDownStatisticsChart',
	height : '100%',
	layout : 'vbox',
	padding : 5,
	defaults : {
		width : '100%',
		height : '200'
	},
	controller:{},
	items : [{
		xtype : "form",
		width : '100%',
		padding:'10 0 0 20',
		height : '200',
		layout : "hbox",
		buttonAlign : 'left',
		defaults : {
			labelAlign : 'left',
			labelWidth : 60
		},
		items : [{
					fieldLabel : Oit.msg.fac.shunDownStatistics.equipCode,
					xtype : 'combobox',
					name : 'equipCode',
					editable:false,  
					mode:'remote',
					displayField:'name',
					valueField : 'code',
					width:380,
					labelWidth:50,
					store:new Ext.data.Store({
						fields:['code','name'],
						proxy:{
							type: 'rest',
							url:'shunDownStatistics/equip'
						}
					})
				},
				{
					fieldLabel :  Oit.msg.fac.shunDownStatistics.startTime,
					xtype : 'datefield',
					name : 'startTime',
					padding:'0 0 0 20',
					format: 'Y-m-d',
					value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.MONTH,-1),"Y-m-d")
				},{
					fieldLabel :  Oit.msg.fac.shunDownStatistics.endTime,
					xtype : 'datefield',
					name : 'endTime',
					padding:'0 0 0 20',
					format: 'Y-m-d',
					value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.MONTH,0),"Y-m-d")
		}],
		buttons : [ {
			itemId : 'search',
			text : Oit.btn.search
		}, {
			itemId : 'reset',
			text :  Oit.msg.fac.shunDownStatistics.resert,
			handler : function(e) {
				this.up("form").getForm().reset();
			}
		} ]
	},{
		id : 'shunDownStatistics_id',
		xtype : 'panel',
		width: document.body.scrollWidth-150,
    	height: document.body.scrollHeight-100
	}]
});
