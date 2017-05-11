Ext.define("bsmes.view.EventTraceList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.eventTraceList',
	store : 'EventTraceStore',
	defaultEditingPlugin : false,
	forceFit:false,
	columns : [ {
		text : Oit.msg.wip.eventTrace.code,
		width:100,
		dataIndex : 'eventTitle'
	}, {
		text : Oit.msg.wip.eventTrace.eventContent,
		width:300,
		dataIndex : 'eventContent',
		renderer: function(value, metaData, record){	
			metaData.tdAttr='data-qtip="'+value+'"';
			return value;
	}
	}, {
		text : Oit.msg.wip.eventTrace.eventReason,
		width:250,
		dataIndex : 'eventReason',
		renderer: function(value, metaData, record){	
			metaData.tdAttr='data-qtip="'+value+'"';
			return value;
	}
	}, {
		text : Oit.msg.wip.eventTrace.eventResult,
		width:250,
		dataIndex : 'eventResult',
		renderer: function(value, metaData, record){	
			metaData.tdAttr='data-qtip="'+value+'"';
			return value;
	}
	},{
		text : Oit.msg.wip.eventTrace.processName,
		width:100,
		dataIndex : 'processName'
	}, {
		text : Oit.msg.wip.eventTrace.equipCode,
		width:150,
		dataIndex : 'equipAlias'
	}/*, {
		text : Oit.msg.wip.eventTrace.triggerTime,
		width:150,
		dataIndex : 'triggerTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}*/, {
		text : Oit.msg.wip.eventTrace.triggerUser,
		width:150,
		dataIndex : 'triggerUser'
	}, /*{
		text : Oit.msg.wip.eventTrace.respondedTime,
		width:150,
		dataIndex : 'respondedTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},*/ {
		text : Oit.msg.wip.eventTrace.processUser,
		width:150,
		dataIndex : 'processUser'
	}, {
		text : Oit.msg.wip.eventTrace.completedTime,
		width:150,
		dataIndex : 'completedTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}/*, {
		text : Oit.msg.wip.eventTrace.processTime,
		width:150,
		dataIndex : 'processTime'
	}*/],
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
						fieldLabel : Oit.msg.wip.eventTrace.code,
						xtype : 'combobox',
						name : 'eventTitle',
						editable : false,
						mode : 'remote',
						displayField : 'name',
						valueField : 'code',
						triggerAction : 'all',
						store : new Ext.data.Store({
							fields : [ 'name', 'code' ],
							proxy : {
								type : 'rest',
								url : 'eventTrace/getEventType'
							}
						})
					},{
						fieldLabel : Oit.msg.wip.eventTrace.processCode,
						xtype : 'combobox',
						id: 'processCode',
						name : 'processCode',
						editable : false,
						displayField : 'processName',
						valueField : 'processCode',
						mode : 'remote',
						store : new Ext.data.Store({
							fields : [ 'processName', 'processCode' ],
							proxy : {
								type : 'rest',
								url : 'eventTrace/getProcess'
							}
						})
					}, {
						fieldLabel : Oit.msg.wip.eventTrace.equipCode,
						xtype : 'combo',
						name : 'equipCode',
						mode : 'remote',
						displayField : 'equipAlias',
						valueField:	'equipCode',
						selectOnFocus : true,
						forceSelection:true,
						minChars : 1,
						store:new Ext.data.Store({
							  fields:['equipAlias', 'equipCode'],
							  proxy:{
								  type: 'rest',
								  url:'eventTrace/equip/1'
							  }
						}),
						listeners: { 
							'beforequery': function (e) {
								var combo = e.combo;
								var processCode=Ext.ComponentQuery.query('#processCode')[0].value;
								combo.store.getProxy().url='eventTrace/equip'+'/'+processCode;
								combo.store.load();
							}
						}
					}]
				}, {
					height : 5
				}/*, {
					items : [ {
						fieldLabel : Oit.msg.wip.eventTrace.startTime,
						xtype : 'datefield',
						format : 'Y-m-d',
						name : 'startTime'
					}, {
						fieldLabel : Oit.msg.wip.eventTrace.endTime,
						xtype : 'datefield',
						format : 'Y-m-d',
						name : 'endTime'
					} ]
				} */],
				buttons : [ {
					itemId : 'search',
					text : Oit.btn.search
				}, {
					itemId : 'reset',
					text : '重置',
					handler : function(e) {
						this.up("form").getForm().reset();
					}
				}, {
					itemId : 'exportToXls',
					text : Oit.btn.export
				} ]

			} ]
		} ]
	} ]

});