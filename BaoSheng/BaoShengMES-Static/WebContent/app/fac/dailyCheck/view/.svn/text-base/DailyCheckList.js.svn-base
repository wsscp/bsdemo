Ext.define("bsmes.view.DailyCheckList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.dailyCheckList',
	store : 'DailyCheckStore',
	defaultEditingPlugin : false,
	forceFit:false,
	columns : [ {
		text : Oit.msg.fac.dailyCheck.equipCode,
		width:200,
		dataIndex : 'equipCode'
	}, {
		text : Oit.msg.fac.dailyCheck.startTime,
		width:200,
		dataIndex : 'startTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
		text : Oit.msg.fac.dailyCheck.finishTime,
		width:200,
		dataIndex : 'finishTime',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
		text : Oit.msg.fac.dailyCheck.describe,
		width:200,
		dataIndex : 'describe'
	}, {
		text : Oit.msg.fac.dailyCheck.value,
		width:150,
		dataIndex : 'value'
	}, {
		text : Oit.msg.fac.dailyCheck.isPassed,
		width:200,
		dataIndex : 'isPassed',
		renderer:function(value){
			if(value){
				return '是';
			}else{
				return '否';
			}
		}
	},{
		text : Oit.msg.fac.dailyCheck.remarks,
		width:200,
		dataIndex : 'remarks'
	}, {
		text : Oit.msg.fac.dailyCheck.status,
		width:200,
		dataIndex : 'status'
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
						fieldLabel : Oit.msg.fac.dailyCheck.equipCode,
						xtype : 'combobox',
						name : 'equipCode',
						editable:false,  
						mode:'remote',
						displayField:'text',
						valueField : 'value',
						width: 420,
						store:new Ext.data.Store({
							fields:[{name:'text',mapping:'name'},{name:'value',mapping:'code'}],
						    autoLoad:true,
							proxy:{
								type: 'rest',
								url:'dailyCheck/equip'
							}
						})
					}]
				}],
				buttons : [ {
					itemId : 'search',
					text : Oit.btn.search
				}, {
					itemId : 'reset',
					text : '重置',
					handler : function(e) {
						this.up("form").getForm().reset();
					}
				} ]

			} ]
		} ]
	} ]

});