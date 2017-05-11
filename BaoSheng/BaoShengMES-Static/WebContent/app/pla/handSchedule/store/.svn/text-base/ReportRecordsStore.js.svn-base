Ext.define('bsmes.store.ReportRecordsStore', {
			extend : 'Oit.app.data.GridStore',
			autoLoad : false,
			fields : ['contractNo', 'custProductType', 'custProductSpec', 'color', 'coilNum', 'reportLength', 'goodLength', 'reportUserName', 'reportEquip', {
						name : 'reportTime',
						type : 'date'
					}],
			proxy : {
				type : 'rest',
				url : 'handSchedule/getWorkOrderReportHistory'
				//+ '/' + preWorkOrderNo
			}
		});