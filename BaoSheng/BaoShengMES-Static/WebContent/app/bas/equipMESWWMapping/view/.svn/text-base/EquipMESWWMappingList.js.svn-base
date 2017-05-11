Ext.define("bsmes.view.EquipMESWWMappingList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.equipMESWWMappingList',
	store : 'EquipMESWWMappingStore',
	columns : [{
		text : Oit.msg.bas.equipMESWWMapping.acEquipCode,
		dataIndex : 'acEquipCode'
	}, {
		text : Oit.msg.bas.equipMESWWMapping.tagName,
		dataIndex : 'tagName'
	},{
		text : Oit.msg.bas.equipMESWWMapping.equipCode,
		dataIndex : 'equipCode'
	},{
		text : Oit.msg.bas.equipMESWWMapping.parmCode,
		dataIndex : 'parmCode'
	},{
		text : Oit.msg.bas.equipMESWWMapping.eventType,
		dataIndex : 'eventType'
	}],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
		        fieldLabel: Oit.msg.bas.equipMESWWMapping.tagName,
		        name: 'tagName'
		    },{
		        fieldLabel: Oit.msg.bas.equipMESWWMapping.equipCode,
		        name: 'equipCode'
		    }]
		}, {
			itemId : 'search'
		}]
	} ],
	actioncolumn : [{
    	itemId : 'edit'
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	}]
});
