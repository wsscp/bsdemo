Ext.define("bsmes.view.EquipEnergyMonitorList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.equipEnergyMonitorList',
	itemId : 'equipEnergyMonitorList',
	store : 'EquipEnergyMonitorStore',
	defaultEditingPlugin : false,
	columns : [{
				width : 120,
				dataIndex : 'eventStatus',
				renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
						return '<button class = "gridButton" style="margin : 0 5 0 10" onClick = "openCheckWin(\''
								+ record.get('EQUIPCODE') +','+ record.get('ENERGY_MODIFY_TIME')+ '\')">能耗详情</button>';
				}
			},{
				text : '设备名称',
				flex : 1,
				dataIndex : 'EQUIP_NAME'
			},{
				text : '日期',
				flex : 1,
				dataIndex : 'ENERGY_MODIFY_TIME'
			},{
				text : '早班能耗(KWH)',
				flex : 1,
				dataIndex : 'MSHIFT_ENERGY'
			},{
				text : '中班能耗(KWH)',
				flex : 1,
				dataIndex : 'ASHIFT_ENERGY'
			},{
				text : '晚班能耗(KWH)',
				flex :　1,
				dataIndex : 'ESHIFT_ENERGY'
			},{
				text : '尖费率数值(kWh/kVarh)',
				flex :　1,
				dataIndex : 'PI'
			},{
				text : '峰费率数值(kWh/kVarh)',
				flex :　1,
				dataIndex : 'PE'
			},{
				text : '谷费率数值(kWh/kVarh)',
				flex :　1,
				dataIndex : 'VA'
			},{
				text : '平费率数值(kWh/kVarh)',
				flex :　1,
				dataIndex : 'FL'
			}],
			dockedItems: [{
		        xtype: 'toolbar',
		        dock: 'top',
		        items: [{
		            xtype : 'form',
		            layout : 'column',
		            items : [{
		            	fieldLabel : '设备名称',
						xtype : 'combobox',
						name : 'equipName',
						model:'local',
						displayField : 'equipCodeName',
						valueField : 'equipName',
						store:new Ext.data.Store({
						    fields: ['equipName', 'equipCodeName'],
						    data : [
						        {"equipName":"CL", "equipCodeName":"4#成缆"},
						        {"equipName":"GXJ", "equipCodeName":"115#硅橡胶"},
						        {"equipName":"JY8", "equipCodeName":"绝缘8#"},
						        {"equipName":"HT4", "equipCodeName":"护套4#"},
						        {"equipName":"GD1", "equipCodeName":"高登1"},
						        {"equipName":"GD2", "equipCodeName":"高登2"}
						    ]
						})
		            },{
		            	fieldLabel : '创建时间',
		            	xtype : 'datefield',
		            	name : 'demandTime',
		            	format : 'Y-m-d',
		            	editable:false,
						value : Ext.util.Format.date(new Date(), "Y-m-d")
		            }]
		        },{
		        	itemId : 'search',
		        	handler : function(){
		        		var grid = this.up('grid');
		        		var form = grid.down('form').getForm();
		        		var params = form.getValues();
						grid.getStore().load({params : params});
		        	}
		        },{
		        	itemId : 'reset',
		        	text : '重置',
		        	handler : function(e) {
		        		var grid = this.up('grid');
		        		grid.down('form').getForm().reset();
					}
		        },{
		        	itemId : 'realTimeCurve',
		        	text : '电量能耗曲线图'
		        }]
		    }]
})

function openCheckWin(equipCode){
	var array = equipCode.split(",");
	console.log(array);
	var win = Ext.create('bsmes.view.ProductReportDetailsView', {
				title : '能耗详情'
	});
	var grid = win.down('grid');
	var store = grid.getStore();
	store.load({
		params : {
			equipCodes :array[0],
			energyModifyTime : array[1]
		}
	});
	win.show();
}
