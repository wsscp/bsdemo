Ext.define("bsmes.view.ProductSOPList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.productSOPList',
	//store : 'ProductSOPStore',
	defaultEditingPlugin:false,
	columns : [{
		text : Oit.msg.pla.productSOP.productType,
		dataIndex : 'productType'
	},{
		text : Oit.msg.pla.productSOP.productCode,
		dataIndex : 'productCode'
		
	},{
		text : Oit.msg.pla.productSOP.productSpec,
		dataIndex : 'productSpec'
		
	},{
		text : Oit.msg.pla.productSOP.earliestFinishDate,
		dataIndex : 'earliestFinishDate',
		renderer:Ext.util.Format.dateRenderer('Y-m-d') 
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
				items : [ {
					items : [ {
						fieldLabel : Oit.msg.pla.productSOP.productType,
						xtype : 'textfield',
						name : 'productType'
					}, {
						fieldLabel :Oit.msg.pla.productSOP.productCode,
						xtype : 'textfield',
						name : 'productCode'
					}]
				}],
				buttons : [{
							 itemId : 'search',
							 text : Oit.btn.search
						  },{
							  itemId:'reset',
							  text : '重置',
							  handler : function(e) {
								  this.up("form").getForm().reset();
							  }
						  },{
							  itemId:'recalculate',
			                  text:Oit.msg.pla.orderOA.recalculate,
			                  handler:function(e){
			                        window.location.href="productSOP/calculateSOP.action"
			                  }
						  },{
                              itemId:'export',
                              text:Oit.btn.export
                         }]

			} ]
		}]
	}]
});


