Ext.define('bsmes.view.MaintainerEditWindow', {
    extend: 'Ext.window.Window',
    alias : 'widget.maintainerEditWindow',
    width: 450,
    layout: 'fit',
    modal: true,
    plain: true,
    store : 'MaintainerStore',
    items: [{
        xtype : 'grid',
        tbar : [ {
            fieldLabel :Oit.msg.fac.equipInformation.maintainer,
            itemId : 'maintainerField',
            name : 'maintainer',
			xtype : 'combo',
			mode : 'remote',
			displayField : 'name',
			valueField:'id',
			selectOnFocus : true,
			forceSelection:true,
			hideTrigger : true,
			minChars : 1,
			width: 350,
			store:Ext.create('bsmes.store.EmployeeUserStore'),
			listeners: { 
				'beforequery': function (queryPlan, eOpts) {
					var me=this;
					var url = '../eve/eventType/getEmployeeOrRole/'+queryPlan.query+'/0/';
					me.getStore().getProxy().url=url;
				}
			}
        }, {
            itemId : 'checkAndAdd',
            text : Oit.btn.add
        }],
        columns : [{
            xtype:'actioncolumn',
            flex: 1,
            items: [{
                text : Oit.btn.remove,
                iconCls : 'icon_remove',
                handler: function(grid, rowIndex) {
                    grid.getStore().removeAt(rowIndex);
                }
            }]
        }, {
            text : Oit.msg.fac.equipInformation.maintainer,
            dataIndex : 'maintainer',
            flex: 4
        }]
    }],
    buttons: [{
        itemId: 'ok',
        text:Oit.btn.ok
    }, {
        itemId: 'cancel',
        text: Oit.btn.cancel
    }]
});