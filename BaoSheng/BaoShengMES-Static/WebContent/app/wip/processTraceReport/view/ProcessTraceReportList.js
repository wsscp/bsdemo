Ext.define("bsmes.view.ProcessTraceReportList", {
	extend : 'Ext.container.Container',
	alias : 'widget.processTraceReportList',
	requires: ['bsmes.view.ProcessReoprtGrid'],
	height:document.body.scrollHeight-110,
	layout : 'vbox',
	padding : 5,
	defaults : {
		width : '100%',
		height : '200'
	},
	controller:{},
	items : [ {
		xtype : "form",
		width : '100%',
		height : '200',
		layout : "hbox",
		buttonAlign : 'left',
		defaults : {
			labelAlign : 'left',
			labelWidth : 60
		},
		items : [ {
			fieldLabel : Oit.msg.wip.processTraceReport.contractNo,
			name : 'contractNo',
			id:'contractNoComb',
			xtype : 'combobox',
			displayField : 'contractNo',
			editable:false,
			valueField : 'id', 
			width: 200,
			margin: '0 0 0 20',
			store:new Ext.data.Store({
				  fields:['id','contractNo'],
				  proxy:{
					  type: 'rest',
					  url:'processTraceReport/getContractNo'
				  }
			})
		},{
			fieldLabel : Oit.msg.wip.processTraceReport.productCode,
			name : 'productCode',
			xtype : 'combobox',
			displayField : 'productCode',
			valueField : 'productCode',
			store : Ext.create('bsmes.store.ProductStore'),
			editable:false,
			width: 280,
			margin: '0 0 0 20',
			listeners : {
				'beforequery' : function(queryPlan, eOpts) {
					var me = this;
					var contractNoComb = me.findParentByType("form").queryById("contractNoComb");
					var id = contractNoComb.getValue();
					var url = 'processTraceReport/product';
					if(id){
						url = url+"/" +id;
					}else{
						url = url+"/-1";
					}
					if (queryPlan.query) {
						me.getStore().getProxy().url = url + "/"+ queryPlan.query+'/';
					} else {
						me.getStore().getProxy().url = url + "/-1";
					}
				},
				'expand':function( field, eOpts ){
					var me = this;
					me.clearValue();
					var contractNoComb = me.findParentByType("form").queryById("contractNoComb");
					var id = contractNoComb.getValue();
					var url = 'processTraceReport/product';
					if(id){
						url = url+"/" +id;
					}else{
						url = url+"/-1";
					}
					me.getStore().getProxy().url = url + "/-1";
					me.getStore().reload();
				}
			}
		},{
			fieldLabel : Oit.msg.wip.processTraceReport.processCode,
			name : 'processCode',
			xtype : 'combobox',
			displayField : 'processName',
			valueField : 'processCode', 
			mode:'remote',
			margin: '0 0 0 20',
			width: 200,
			editable:false,
			store:new Ext.data.Store({
				  fields:['processCode','processName'],
				  proxy:{
					  type: 'rest',
					  url:'qualityTrace/process'
				  }
			})
		},{
			fieldLabel : Oit.msg.wip.processTraceReport.equipCode,
			name : 'equipCode',
			xtype : 'combobox',
			displayField : 'name',
			valueField : 'code', 
			margin: '0 0 0 20',
			width: 250,
			editable:false,
			store:new Ext.data.Store({
				  fields:['code','name'],
				  proxy:{
					  type: 'rest',
					  url:'processTraceReport/equip'
				  }
			})
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
	},{
		xtype:'processReoprtGrid',
		height:document.body.scrollHeight-80
	}]
});
