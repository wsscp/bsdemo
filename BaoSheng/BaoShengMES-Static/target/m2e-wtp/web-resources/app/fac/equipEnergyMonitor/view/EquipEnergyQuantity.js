Ext.define("bsmes.view.EquipEnergyQuantity",{
	extend: 'Oit.app.view.Grid',
	alias: 'widget.equipEnergyQuantity',
	id : 'equipEnergyQuantity',
	defaultEditingPlugin : false,
	store : 'EquipEnergyQuantityStore',
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
		text : '有功负荷',
		dataIndex : 'gd',
		flex :4,
		columns : [{
			dataIndex : 'POW_AT',
			text : '总(kw)',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'POW_AA',
			text : 'Pu/PI(kw)',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'POW_AB',
			text : 'PV(kw)',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'POW_AC',
			text : 'PW/PII(kw)',
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
			var store = Ext.ComponentQuery.query('#equipEnergyQuantity')[0].getStore();
			store.getProxy().url = 'equipEnergyMonitor/energyQuantity';
			store.loadPage(1);
    	}
	},{
		   itemId : 'realTimeCurve',
		   text : '电量能耗曲线图'
	}]
});