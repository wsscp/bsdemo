Ext.define("bsmes.view.EquipProcessTraceList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.equipProcessTraceList',
	store : 'EquipProcessTraceStore',
    collapsible: false,  
    animCollapse: false,
    forceFit : false,
	columns : [
		{text : Oit.msg.wip.equipProcessTrace.equipCode,dataIndex : 'reEquipCode',width:150}, 
		{text : Oit.msg.wip.equipProcessTrace.equipName,dataIndex : 'equipName',width:150}, 
		{
			text : Oit.msg.wip.equipProcessTrace.contractNo,
			dataIndex : 'contractNo',
			renderer:function (value, metaData, record, rowIdx, colIdx, store) {
				if(value){
					metaData.tdAttr = 'data-qtip=" <div style=\'word-break:break-all;word-wrap:break-word;\'>' + value + '</div>"';
				}
                return value;
			},
			width:250
		},
		{text : Oit.msg.wip.equipProcessTrace.processCode,dataIndex : 'processCode',width:200},
		{text : Oit.msg.wip.equipProcessTrace.processName,dataIndex : 'processName',width:100},
		{text : Oit.msg.wip.equipProcessTrace.realStartTime,dataIndex : 'realStartTime',width:200},
		{text : Oit.msg.wip.equipProcessTrace.realEndTime,dataIndex : 'realEndTime',width:200},
		{
			text : Oit.msg.wip.equipProcessTrace.userCode,
			dataIndex : 'userCode',
			renderer:function (value, metaData, record, rowIdx, colIdx, store) {
				if(value){
					metaData.tdAttr = 'data-qtip=" <div style=\'word-break:break-all;word-wrap:break-word;\'>' + value + '</div>"';
				}
                return value;
			},
			width:100
		},
		{text : Oit.msg.wip.equipProcessTrace.orderLength,dataIndex : 'orderLength',width:100},
		{text : Oit.msg.wip.equipProcessTrace.usedTime,dataIndex : 'usedTime',width:200}
	],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			id :'equipProcessTraceSearchForm',
			xtype : 'form',
			width : '100%',
			layout : 'hbox',
			buttonAlign : 'left',
			labelAlign : 'right',
			bodyPadding : 1,
			defaults : {
				xtype : 'panel',
				//width : '100%',
				layout : 'vbox',
				defaults : {
					labelAlign : 'right'
				}
			},
			items : [{
				fieldLabel :Oit.msg.wip.equipProcessTrace.processName,
				xtype : 'combobox',
				labelWidth:70,
				name : 'processCode',
				labelAlign:'right',
//				editable:false,  
				displayField:'processName',
				valueField: 'processCode',
//				queryMode : 'local',
				store:new Ext.data.Store({
					  fields:['processName', 'processCode'],
					  proxy:{
						  type: 'rest',
						  url:'qualityTrace/process'
					  }
				}),
				listeners : {
					beforequery : function(e) {
						opeaEmpty = true;
						var combo = e.combo;
						combo.collapse();
						if (!e.forceAll) {
							opeaEmpty = false;
							var value = e.query;
							if (value != null && value != '') {
								combo.store.filterBy(function(record, id) {
											var text = record.get('processName');
											return (text.indexOf(value) != -1);
										});
							} else {
								combo.store.clearFilter();
							}
							combo.onLoad();
							combo.expand();
							return false;
						}
					}
				}
			}, {
					fieldLabel : Oit.msg.wip.equipProcessTrace.equipCode+"&nbsp;",
//					xtype : 'textfield',
//					labelWidth:50,
//					name : 'equipCode',
//					fieldLabel : '机台',
					name : 'equipCode',
					itemId : 'equipCode',
					xtype : 'combobox',
					displayField : 'name',
					valueField : 'code',
					labelAlign:'right',
//					editable:false,
					queryMode : 'local',
					labelWidth : 50,
					store : new Ext.data.Store({
								fields : ['code', {
											name : 'name',
											type : 'string',
											convert : function(value, record) {
												if (record.get('equipAlias') == '') {
													return value + '[' + record.get('code') + ']';
												} else {
													return record.get('equipAlias') + '-' + value + '[' + record.get('code')
															+ ']';
												}

											}
										}, 'equipAlias'],
								proxy : {
									type : 'rest',
									url : '../fac/equipInfo/getEquipLine'
								}
							}),
							listeners : {
								beforequery : function(e) {
									equipEmpty = true;
									var combo = e.combo;
									combo.collapse();
									if (!e.forceAll) {
										equipEmpty = false;
										var value = e.query;
										if (value != null && value != '') {
											combo.store.filterBy(function(record, id) {
														var text = record.get('name');
														return (text.indexOf(value) != -1);
													});
										} else {
											combo.store.clearFilter();
										}
										combo.onLoad();
										combo.expand();
										return false;
									}
								},
								expand : function() {
									if (equipEmpty) {
										var f = this.up("toolbar").down("form").getForm().getValues();
										var processCode = '';
										var process = Ext.ComponentQuery.query('equipProcessTraceList combobox[name="processCode"]')[0].displayTplData;
										if (process && process[0]) {
											processCode = process[0].processCode;
										}
										Ext.ComponentQuery.query('equipProcessTraceList #equipCode')[0].getStore().load({
													params : {
														'processName' : processCode
													}
												});
									}
								}
							}
				},{
					fieldLabel : "&nbsp;"+Oit.msg.wip.equipProcessTrace.contractNo,
					xtype : 'textfield',
					name : 'contractNo',
					labelWidth:50,
				},{
					fieldLabel : "&nbsp;开始时间大于",
					xtype : 'datefield',
					name : 'realStartTime',
					format: 'Y-m-d',
					labelWidth:90
				}, {
					fieldLabel :"&nbsp;结束时间小于",
					xtype : 'datefield',
					name : 'realEndTime',
					format: 'Y-m-d',
					labelWidth:90
				}],
			buttons : [ {
				itemId : 'search',
				text : Oit.btn.search
			}, {
				itemId:'reset',
				text : '重置',
				handler : function(e) {
					this.up("form").getForm().reset();
				}
			},{
				itemId:'exportToXls',
				text : '导出到xls',
			}]
		}]
	} ]
});
