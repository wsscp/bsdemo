Ext.define("bsmes.view.SchedulerLogList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.schedulerLoglist',
	store : 'SchedulerLogStore',
	defaultEditingPlugin : false,
	columns : [{
		text : Oit.msg.job.jobLog.jobName,
		dataIndex : 'jobName'
	}, {
		text : Oit.msg.job.jobLog.jobDesc,
		dataIndex : 'jobDesc'
	},{
		text : Oit.msg.job.jobLog.hostName,
		dataIndex : 'hostName'
	},{
		text : Oit.msg.job.jobLog.hostAddress,
		dataIndex : 'hostAddress'
	},{
		text : Oit.msg.job.jobLog.flag,
		dataIndex : 'flag'
	},{
		text : Oit.msg.job.jobLog.prevStartTime,
		dataIndex : 'prevStartTime',
		xtype : 'datecolumn',
		format: 'Y-m-d H:i:s'
	},{
		text : Oit.msg.job.jobLog.prevResult,
		dataIndex : 'prevResult'
	},{
		text : Oit.msg.job.jobLog.prevEndTime,
		dataIndex : 'prevEndTime',
		xtype : 'datecolumn',
		format: 'Y-m-d H:i:s'
	},{
		text : Oit.msg.job.jobLog.errorMessage,
		dataIndex : 'errorMessage'
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
				items : [ {
					items : [{
						  fieldLabel:Oit.msg.job.jobLog.hostName,
						  xtype : 'textfield',
					      name: 'hostName'
					},{
						fieldLabel: Oit.msg.job.jobLog.flag,
				        name: 'flag',
				        xtype : 'combobox',
				        displayField: 'name',
					    valueField: 'code',
					    editable:false,
					    store:new Ext.data.Store({
						    fields:['code', 'name'],
						    data : [
						        {"name":'完成', "code":"false"},
						        {"name":'进行中', "code":"true"}
					        ]
						})
					}]
				}],
				buttons : [ {
					itemId : 'search',
					text : Oit.btn.search
				},{
					itemId : 'reset',
					text : '重置',
					handler : function(e) {
						this.up("form").getForm().reset();
					}
				}]

			} ]
		} ]
	} ]
});
