Ext.define('bsmes.view.LocationList',{
		extend:'Oit.app.view.Grid',
		alias:'widget.locationList',
		store:'LocationStore',
		defaultEditingPlugin : false,
        forceFit : false,
		columns:[{
					text:Oit.msg.inv.location.warehouseName,
					dataIndex:'warehouseName'
				},{
					text:Oit.msg.inv.location.processCode,
					xtype: 'hiddenfield',
					dataIndex:'processCode'
				},{
					text:Oit.msg.inv.location.processName,
					dataIndex:'processName'
				},{
					text:Oit.msg.inv.location.locationName,
					dataIndex:'locationName'
				},{
					text:Oit.msg.inv.location.locationX,
					dataIndex:'locationX'
				},{
					text:Oit.msg.inv.location.locationY,
					dataIndex:'locationY'
				},{
					text:Oit.msg.inv.location.locationZ,
					dataIndex:'locationZ'
				},{
					text:Oit.msg.inv.location.type,
					dataIndex:'type'
				}],
		actioncolumn:[{
			itemId:'edit'
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
    						fieldLabel:Oit.msg.inv.location.processCode,
    				        name: 'processCode',
    				        xtype : 'combobox',
    				        mode : 'remote',
    						displayField : 'name',
    						valueField : 'code',
    						store : new Ext.data.Store({
    							fields : [ 'code', 'name' ],
    							proxy : {
    								type : 'rest',
    								url : '../pro/processInfo/getAllProcess'
    							}
    						})
    					}]
    				}],
    				buttons : [ {
    					itemId : 'search',
    					text : Oit.btn.search
    				},{
    					itemId : 'add',
    					iconCls : 'icon_add',
    					text:'添加'
    				},{
    					itemId : 'remove',
    					iconCls : 'icon_remove',
    					text:'删除'
    				}]

    			} ]
    		} ]
    	} ]
});