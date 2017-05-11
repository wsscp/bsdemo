Ext.define("bsmes.view.EquipMaintenanceList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.equipMaintenanceList',
	defaultEditingPlugin : false,
	store : 'EquipMaintenanceStore',
	columns : [{
		text : Oit.msg.fac.equipMaintenance.equipCode,
		dataIndex : 'equipCode'
    } ,{
        text : Oit.msg.fac.equipMaintenance.equipName,
        dataIndex : 'equipName'
	},{
		text : Oit.msg.fac.equipMaintenance.eventContent,
        dataIndex : 'eventContent'
	},{
		text : Oit.msg.fac.equipMaintenance.createTime,
		dataIndex : 'createTime',
		xtype:'datecolumn',
		format: 'Y-m-d H:i:s'
	},{
		text : Oit.msg.fac.equipMaintenance.responseTime,
		dataIndex : 'responseTime',
		xtype:'datecolumn',
		format: 'Y-m-d H:i:s'
	},{
		text : Oit.msg.fac.equipMaintenance.responsed,
		dataIndex : 'responsed'
	},{
		text : Oit.msg.fac.equipMaintenance.responseTimes,
		dataIndex : 'responseTimes'
	},{
		text : Oit.msg.fac.equipMaintenance.completeTime,
		dataIndex : 'completeTime',
		xtype:'datecolumn',
		format: 'Y-m-d H:i:s'
	},{
		text : Oit.msg.fac.equipMaintenance.complete,
		dataIndex : 'complete'
	},{
		text : Oit.msg.fac.equipMaintenance.completeTimes,
		dataIndex : 'completeTimes'
	}],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			title : '查询条件',
			xtype : 'fieldset',
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
					items : [{
						items : [ {
									fieldLabel :Oit.msg.fac.equipMaintenance.equipCode,
									xtype : 'combobox',
									name : 'equipCode',
									editable : false,  
									displayField:'name',
									value:'',
									valueField: 'code',
									mode:'remote',
									width:300,
									store:new Ext.data.Store({
											 fields:['name','code'],
											 autoLoad:true,
											 proxy:{
												  type: 'rest',
												  url:'equipMaintenance/equipment'
											  }
									})
								},{
									fieldLabel: Oit.msg.fac.equipMaintenance.status,
									xtype : 'combobox',
									name : 'status',
									displayField: 'name',
								    valueField: 'code',
								    value:'',
								    flex : 2,
									editable:false,
									store:new Ext.data.Store({
									    fields:['code', 'name'],
									    data : [
									        {"name":Oit.msg.fac.equipMaintenance.type.all, "code":""},
									        {"name":Oit.msg.fac.equipMaintenance.type.uncompleted, "code":"UNCOMPLETED"},
									        {"name":Oit.msg.fac.equipMaintenance.type.responded, "code":"RESPONDED"},
								            {"name":Oit.msg.fac.equipMaintenance.type.completed, "code":"COMPLETED"}
								        ]
									})
							},{
								fieldLabel :Oit.msg.fac.equipMaintenance.createTime,
								xtype : 'datefield',
								name : 'createTime',
								flex : 3,
								format : 'Y-m-d H:i:s'
							},{
								fieldLabel :Oit.msg.fac.equipMaintenance.completeTime,
								xtype : 'datefield',
								name : 'completeTime',
								flex : 3,
								format : 'Y-m-d H:i:s'
							},{
								fieldLabel :Oit.msg.fac.equipMaintenance.responsed,
								xtype : 'textfield',
								name : 'responsed',
								flex : 2
							}]
						}],
						buttons : [{
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
							text :Oit.btn.export
						}]

					} ]
				}]
			}]
});


