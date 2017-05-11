Ext.define('bsmes.controller.PersonalMonitorController', {
	extend : 'Oit.app.controller.GridController',
	personalMonitorPanel : 'personalMonitorPanel',
	dailyPersonalMonitorGrid : 'dailyPersonalMonitorGrid',
	monthPersonalMonitorGrid : 'monthPersonalMonitorGrid',
	view : 'personalMonitorPanel',
	views : [ 'PersonalMonitorPanel','DailyPersonalMonitorGrid','MonthPersonalMonitorGrid','CreditCardGrid'],
	stores : ['DailyMonitorStore','MonthMonitorStore','CreditCardGridStore'],
	constructor : function() {
		var me = this;
		me.refs = me.refs || [];
		
		me.refs.push({
			ref : 'personalMonitorPanel',
			selector : '#personalMonitorPanel'
		});
		
		me.refs.push({
			ref : 'dailyPersonalMonitorGrid',
			selector : me.dailyPersonalMonitorGrid,
			autoCreate : true,
			xtype : me.dailyPersonalMonitorGrid
		});
		me.refs.push({
			ref : 'monthPersonalMonitorGrid',
			selector : me.monthPersonalMonitorGrid,
			autoCreate : true,
			xtype : me.monthPersonalMonitorGrid
		});
		me.refs.push({
			ref : 'creditCardGrid',
			selector : 'creditCardGrid',
			autoCreate : true,
			xtype : 'creditCardGrid'
		});
		me.callParent(arguments);
	},
	init : function() {
		var me = this;
		me.control(me.dailyPersonalMonitorGrid + ' button[itemId=searchDailyReport]', {
			click : me.searchDailyReport
		});
		me.control(me.monthPersonalMonitorGrid + ' button[itemId=searchMonthReport]', {
			click : me.searchMonthReport
		});
		
		/*me.control(me.dailyPersonalMonitorGrid + ' button[itemId=creditCard]', {
			click : me.creditCard
		});*/
		me.callParent(arguments);
	},
	searchDailyReport : function(btn) {
		var me = this;
		var form = Ext.ComponentQuery.query("dailyPersonalMonitorGrid hform")[0];
		var params = form.form.getValues();
		Ext.ComponentQuery.query('#dailyPersonalMonitorGrid')[0].getStore().loadPage(1,{
			params : params
		});
	},
	searchMonthReport : function(btn) {
		var me = this;
		var form = Ext.ComponentQuery.query("monthPersonalMonitorGrid hform")[0];
		var params = form.form.getValues();
		Ext.ComponentQuery.query('#monthPersonalMonitorGrid')[0].getStore().loadPage(1,{
			params : params
		});
	}/*,
	creditCard : function(btn){
		var win = Ext.create('Ext.window.Window', {
		    title: '人员考勤报表',
		    height: document.body.scrollHeight - 100,
		    width: document.body.scrollWidth-100,
		    layout: 'fit',
		    items: {  
		        xtype: 'creditCardGrid'
		    }
		});
		var grid = win.down('grid');
		grid.getStore().load({
			params : grid.down('form').getForm().getValues()
		});
		win.show();
	}*/
});

