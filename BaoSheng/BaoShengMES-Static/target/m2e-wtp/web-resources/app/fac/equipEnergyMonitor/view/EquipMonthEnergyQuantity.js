Ext.define("bsmes.view.EquipMonthEnergyQuantity",{
	extend: 'Oit.app.view.Grid',
	alias: 'widget.equipMonthEnergyQuantity',
	id : 'equipMonthEnergyQuantity',
	defaultEditingPlugin : false,
	store : 'EquipMonthEnergyQuantityStore',
	selType : 'checkboxmodel',
	selModel : {
		mode : "MULTI" 
	},
	columns : [{
		dataIndex : 'EQUIP_NAME',
		text : '设备名称',
		flex : 1
	},{
		dataIndex : 'ENERGY_MODIFY_TIME',
		text : '日期',
		flex : 1
	},{
		text : '负荷(kW)',
		dataIndex : 'gd',
		flex :4,
		columns : [{
			dataIndex : 'MAX_POWER',
			text : '最大负荷',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'MIN_POWER',
			text : '最小负荷',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'AVG_POWER',
			text : '平均负荷',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'DIFR',
			text : '峰谷差率',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'AVGR',
			text : '负荷率',
			editor: {
	            allowBlank: true
	        }
		}]
	}/*,{
		text : '无功负荷',
		dataIndex : 'gd',
		flex :4,
		columns : [{
			dataIndex : 'pow_at',
			text : '总',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'PE',
			text : 'U相',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'V相',
			text : '平',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'W相',
			text : '谷',
			editor: {
	            allowBlank: true
	        }
		}]
	}*/],
	tbar : [{
	        xtype : 'form',
	        layout : 'column',
	        items :{
				fieldLabel : '创建时间',
		    	xtype : 'datefield',
		    	name : 'demandTime',
		    	format : 'Y-m-d',
		    	editable:false,
		    	hidden : true,
				value :Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,0),'Y-m-d')
	        }
	}, {
		itemId : 'search',
    	handler : function(){
    		var me = this;
			var store = Ext.ComponentQuery.query('#equipMonthEnergyQuantity')[0].getStore();
			store.getProxy().url = 'equipEnergyMonitor/energyMonthQuantity';
			store.loadPage(1);
    	}
	}]
});