Ext.define('bsmes.store.CreditCardGridStore', {
			extend : 'Oit.app.data.GridStore',
			model : 'bsmes.model.DailyMonitorModel',
			sorters : [],
			proxy : {
				type : 'rest',
				url : 'personalMonitor/searchCreditCardList'
			}
		});