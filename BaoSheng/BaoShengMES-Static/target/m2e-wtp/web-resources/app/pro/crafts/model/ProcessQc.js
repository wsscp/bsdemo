Ext.define('bsmes.model.ProcessQc', {
			extend : 'Ext.data.Model',
			fields : ['processId', 'checkItemCode', 'checkItemName', 'frequence', {
						name : 'needDa',
						type : 'boolean'
					}, {
						name : 'needIs',
						type : 'boolean',
						defaultValue : false
					}, {
						name : 'dataType',
						type : 'string',
						defaultValue : 'STRING'
					}, 'dataUnit', 'marks', 'itemTargetValue', 'itemMaxValue', 'itemMinValue', 'itemUpValue',
					'itemDownValue', {
						name : 'hasPic',
						type : 'string',
						defaultValue : '0'
					}, {
						name : 'needShow',
						type : 'string',
						defaultValue : '0'
					}, {
						name : 'needFirstCheck',
						type : 'string',
						defaultValue : '0'
					}, {
						name : 'needMiddleCheck',
						type : 'string',
						defaultValue : '0'
					}, {
						name : 'needInCheck',
						type : 'string',
						defaultValue : '0'
					}, {
						name : 'needOutCheck',
						type : 'string',
						defaultValue : '0'
					}, {
						name : 'needAlarm',
						type : 'string',
						defaultValue : '0'
					}, {
						name : 'dataStatus',
						type : 'string',
						defaultValue : 'NORMAL'
					}, 'valueDomain', {
						name : 'emphShow',
						type : 'string'
					}]
		});