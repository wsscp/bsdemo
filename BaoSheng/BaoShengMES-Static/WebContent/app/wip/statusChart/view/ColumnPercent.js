Ext.define("bsmes.view.ColumnPercent", {
	extend : 'Ext.panel.Panel',
	alias : 'columnPercent',
	requires : [ 'Ext.chart.Chart' ],
	width:'',
	height:'',
	initComponent : function() {
		 var me=this;
		 me.items = [{
			 	xtype : 'panel',
				layout : 'hbox',
				items:[{
					xtype:'panel',
					id:'columnChartId',
					width:(me.width-105)/2,
					height:me.height
				},{width:100}
				,{
					xtype: 'panel',
					id:'pieChartId',
					width:(me.width-105)/2,
					height:me.height
				}
				]
		 }];
		 me.callParent(arguments);
	}
});
