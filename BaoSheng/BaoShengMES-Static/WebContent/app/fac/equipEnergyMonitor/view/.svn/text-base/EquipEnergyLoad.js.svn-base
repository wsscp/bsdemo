Ext.define("bsmes.view.EquipEnergyLoad",{
	extend: 'Oit.app.view.Grid',
	alias: 'widget.equipEnergyLoad',
	id : 'equipEnergyLoad',
	defaultEditingPlugin : false,
	store : 'EquipEnergyLoadStore',
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
		text : '有功电量',
		dataIndex : 'gd',
		flex :4,
		columns : [{
			dataIndex : 'SUMTO',
			text : '总(kWh/kVarh)',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'PE',
			text : '峰(kWh/kVarh)',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'FL',
			text : '平(kWh/kVarh)',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'VA',
			text : '谷(kWh/kVarh)',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'PI',
			text : '尖(kWh/kVarh)',
			editor: {
	            allowBlank: true
	        }
		}]
	}/*,{
		text : '无功电量',
		flex : 1,
		editor: {
            allowBlank: true
        }
	}*/],
	tbar : [
		{
	        xtype : 'form',
	        layout : 'column',
	        items :{
				fieldLabel : '创建时间',
		    	xtype : 'datefield',
		    	name : 'demandTime',
		    	format : 'Y-m-d',
		    	editable:false,
				value :Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,-1),'Y-m-d')
	        }
	},{
		itemId : 'search',
    	handler : function(){
    		var me = this;
			var store = Ext.ComponentQuery.query('#equipEnergyLoad')[0].getStore();
			store.getProxy().url = 'equipEnergyMonitor/energyLoad';
			store.loadPage(1);
    	}
	},{
		   itemId : 'realTimeCurve',
		   text : '电量能耗曲线图'
	}]
});