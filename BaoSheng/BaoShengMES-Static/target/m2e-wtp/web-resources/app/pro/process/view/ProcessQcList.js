Ext.define('bsmes.view.ProcessQcList', {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.processQcList',
			collapsible : false,
			animCollapse : false,
			height : document.body.scrollHeight - 140,
			forceFit : false,
			hasPaging : false,
			store : 'ProcessQcStore',
			columns : [{
						text : '检测项CODE',
						dataIndex : 'checkItemCode',
						minWidth : 100,
						flex : 2
					}, {
						text : '检测项名称',
						dataIndex : 'checkItemName',
						minWidth : 200,
						flex : 3
					}, {
						text : '检测频率',
						dataIndex : 'frequence',
						minWidth : 100,
						flex : 1
					}, {
						text : '参数目标值',
						dataIndex : 'itemTargetValue',
						minWidth : 100,
						flex : 1
					}, {
						text : '参数上限',
						dataIndex : 'itemMaxValue',
						minWidth : 100,
						flex : 1
					}, {
						text : '参数下限',
						dataIndex : 'itemMinValue',
						minWidth : 100,
						flex : 1
					}, {
						text : '参数数据类型',
						dataIndex : 'dataType',
						minWidth : 100,
						flex : 1
					}, {
						text : '参数单位',
						dataIndex : 'dataUnit',
						minWidth : 100,
						flex : 1
					}, {
						text : '是否有附件',
						dataIndex : 'hasPic',
						minWidth : 100,
						flex : 1
					}, {
						text : '是否在终端显示',
						dataIndex : 'needShow',
						minWidth : 126,
						flex : 1
					}, {
						text : '是否重点显示',
						dataIndex : 'emphShow',
						minWidth : 153,
						flex : 1
					},
					// {
					// text : '是否要首检',
					// dataIndex : 'needFirstCheck',
					// width : 100
					// }, {
					// text : '是否要中检',
					// dataIndex : 'needMiddleCheck',
					// width : 100
					// }, {
					// text : '是否要上车检',
					// dataIndex : 'needInCheck',
					// width : 100
					// }, {
					// text : '是否要下车检',
					// dataIndex : 'needOutCheck',
					// width : 100
					// },
					{
						text : '是否需要数采',
						dataIndex : 'needDa',
						minWidth : 100,
						flex : 1
					}, {
						text : '是否需要下发',
						dataIndex : 'needIs',
						minWidth : 100,
						flex : 1
					}, {
						text : '超差是否报警',
						dataIndex : 'needAlarm',
						minWidth : 100,
						flex : 1
					}, {
						text : '值域',
						dataIndex : 'valueDomain',
						minWidth : 100,
						flex : 1
					}],
			// 查询栏
			dockedItems : [{
						items : [{
									xtype : 'hform',
									items : [{
												xtype : 'hiddenfield',
												name : 'processId'
											}]
								}]

					}],
			tbar : [{
						itemId : 'qcEdit',
						text : '检测项维护'
					}, {
						name : 'checkType',
						xtype : 'radiogroup',
						width : 320,
						items : [{
									boxLabel : '上车检',
									name : 'checkType',
									inputValue : 'needInCheck',
									checked : true
								}, {
									boxLabel : '中检',
									name : 'checkType',
									inputValue : 'needMiddleCheck'
								}, {
									boxLabel : '下车检',
									name : 'checkType',
									inputValue : 'needOutCheck'
								}],
						listeners : {
							change : function(obj, newValue, oldValue, eOpts){
								var me = this;
								var store = me.up('grid').getStore();
								store.loadPage(1, {
											params : {
												checkType : newValue
											}
										});
							}
						}
					}, '->', {
						text : '关  闭',
						handler : function(){
							var me = this;
							me.up('window').close();
						}
					}]
		});