Ext.define("bsmes.view.ProcessBalanceChart", {
	extend : 'Ext.container.Container',
	alias : 'widget.processBalanceChart',
	height : '100%',
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
		padding:'10 0 0 20',
		height : '200',
		layout : "hbox",
		buttonAlign : 'left',
		defaults : {
			labelAlign : 'left',
			labelWidth : 60
		},
		items : [{
					fieldLabel : Oit.msg.wip.processBalanceChart.productName,
					xtype : 'combo',
					name : 'productCode',
					id:'processBalanceChart_code',
					width:250,
					labelWidth:40,
					mode : 'remote',
					allowBlank  : false,
					displayField : 'productName',
					valueField : 'productCode',
					selectOnFocus : true,
					forceSelection:true,
					hideTrigger : true,
					minChars : 1,
					store : new Ext.data.Store({
						fields : [ 'productCode', 'productName' ],
						proxy : {
							type : 'rest',
							url : 'processBalanceChart/product'
						}
				    })
			},{
				fieldLabel :  Oit.msg.wip.processBalanceChart.startTime,
                xtype : 'datefield',
                id:'processBalanceChart_start',
                name : 'startTime',
                padding:'0 0 0 20',
                allowBlank  : false,
                labelWidth:70,
                width:300,
                emptyText:'请选择起始时间',
				blankText:'请输入起始时间',
                format: 'Y-m-d'
			},{
				fieldLabel :  Oit.msg.wip.processBalanceChart.endTime,
                xtype : 'datefield',
                name : 'endTime',
                padding:'0 0 0 20',
                labelWidth:40,
                id:'processBalanceChart_end',
                emptyText:'请选择结束时间',
				blankText:'请输入结束时间',
				allowBlank  : false,
                format: 'Y-m-d'
			}],
		buttons : [ {
			itemId : 'search',
			text : Oit.btn.search
		}, {
			itemId : 'reset',
			text :  Oit.msg.wip.processBalanceChart.resert,
			handler : function(e) {
				this.up("form").getForm().reset();
			}
		} ]
	}, {
		id : 'processBalanceChartId',
		xtype : 'panel',
		width: document.body.scrollWidth-150,
    	height: document.body.scrollHeight-100
	} ]
	
});
