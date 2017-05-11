Ext.define('bsmes.view.PriorityLeftList',{
	extend:'Oit.app.view.Grid',
	id:'leftgrid',
    stripeRows: true,
    height: 430,
    selType: 'checkboxmodel',
    alias: 'widget.leftGridView',
    frame: true,
    store: 'LeftStore',
    plugins: [{
        ptype: 'rowexpander',
        rowBodyTpl: [
            '<div id="{contractNo}">',
            '</div>'
        ]
    }],
    forceFit:false,
    defaultEditingPlugin:false,
    columns: [ {
					  text: Oit.msg.pla.customerOrderItem.customerContractNO,  
					  dataIndex:'contractNo',
                      width:150
				},{
                       text: Oit.msg.pla.customerOrderItem.customerCompany,
                       dataIndex:'customerCompany',
                       width:150
                },{
                      text: Oit.msg.pla.customerOrderItem.importance,
                      dataIndex:'importance',
                      width:100
                },{
					  text: Oit.msg.pla.customerOrderItem.customerOaDate,
					  dataIndex:'customerOaDate',
                      xtype:'datecolumn',
                      format:'Y-m-d',
                      width:100
				},{
				  	  text: Oit.msg.pla.customerOrderItem.fixedOa,
				  	  dataIndex:'fixedOa',
                        width:150
				},{
                    text: Oit.msg.pla.customerOrderItem.operator,
                    dataIndex:'operator',
                    width:100
                }],
  	  dockedItems:[{
					xtype : 'toolbar',
		   	  		dock 	: 'top',
		   	  		items  : [{
								xtype : 'hform',
								items: [{
									        fieldLabel: Oit.msg.pla.customerOrderItem.customerContractNO,
									        name: 'contractNo',
                                            width:170,
                                            labelWidth:70
									    },{
									        fieldLabel: Oit.msg.pla.customerOrderItem.productType,
									        name: 'productType',
                                            width:160,
                                            labelWidth:60
									    },{
                                            fieldLabel:Oit.msg.pla.customerOrderItem.operator,
                                            name:'operator',
                                            width:150,
                                            labelWidth:50
                                        }]
					   		},{
					   			itemId : 'search'
					   		}]
		   	  }],
      initComponent: function () {
            var me = this;
            this.callParent(arguments);
            me.view.on('expandBody', function (rowNode, record, expandRow, eOpts) {
               console.log(record);
                var renderId = record.get('contractNo');
                var url =  "customerOrderItem/findOrderItemInfo/"+record.get('id');
                var subGrid = Ext.create("bsmes.view.SubPriorityList",{
                    renderTo: renderId,
                    store:Ext.create('bsmes.store.SubPriorityStore')
                });
                var subStore = subGrid.getStore();
                subStore.getProxy().url=url;
                subStore.reload();
            });
            me.view.on('collapsebody', function (rowNode, record, expandRow, eOpts) {
                var parent = document.getElementById(record.get('contractNo'));
                var child = parent.firstChild;
                while (child) {
                    child.parentNode.removeChild(child);
                    child = child.nextSibling;
                }
            });
      }
});