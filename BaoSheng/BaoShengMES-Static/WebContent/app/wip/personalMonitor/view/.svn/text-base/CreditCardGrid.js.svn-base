Ext.define("bsmes.view.CreditCardGrid",{
	extend : 'Oit.app.view.Grid',
	alias : 'widget.creditCardGrid',
	store : 'CreditCardGridStore',
	columns : [{
		text : '刷卡机台',
		dataIndex : 'code'
	},{
		text : '班次',
		dataIndex : 'shiftId',
		renderer : function(value, metaData, record, row, column) {
			if(value == '1'){
				value = '早班';
			}else if(value == '2'){
				value = '中班';
			}else{
				value = '晚班';
			}
			return value;
		}
	},{
		text : '员工姓名',
		dataIndex : 'userName'
	},{
		text : '上班时间',
		dataIndex : 'onTime'
	},{
		text : '下班时间',
		dataIndex : 'offTime'
	},{
		text : '备注',
		dataIndex : 'remark',
		renderer : function(value, metaData, record, row, column) {
			if(record.get('onTime') != '' && record.get('offTime') !=''){
				var onTime = new Date(record.get('onTime')).getTime();
				var offTime = new Date(record.get('offTime')).getTime();
				if((offTime-onTime)/(1000*60*60)>9.5){
					value = '刷下班卡不及时';
				}
			}else if(record.get('onTime') != '' && record.get('offTime') ==''){
				value = '未刷下班卡';
			}else{
				value = '';
			}
			return value;
		}
	}],
	tbar : [{
		xtype : 'form',
		width : '100%',
		layout : 'column',
		items : [{
			fieldLabel : '开始日期',
			id : 'startDate',
			xtype : 'datefield',
			name : 'startDate',
			format : 'Y-m-d',
			labelWidth : 60,
			firstLoad : true,
			width : 200
		},{
			fieldLabel : '结束日期',
			xtype : 'datefield',
			id : 'endDate',
			name : 'endDate',
			format : 'Y-m-d',
			labelWidth : 60,
			firstLoad : true,
			width : 200
		},{
			fieldLabel: "班次",
	        name: 'shiftId',
	        xtype:'combobox',
	        labelWidth : 45,
	        width : 200,
	        allowBlank:false,
	        displayField:'shiftName',
	        valueField: 'shiftId',
	        store:new Ext.data.Store({
	        	fields:['shiftId','shiftName'],
	        	data : [{'shiftId':'1','shiftName':'早班'},
	        	        {'shiftId':'2','shiftName':'中班'},
	        	        {'shiftId':'3','shiftName':'晚班'}],
	        	sorters : [{
					property : 'shiftId',
					direction : 'ASC'
				}]
	        })
		},{
			fieldLabel: "员工",
	        name: 'userCode',
	        xtype:'combobox',
	        labelWidth : 45,
	        width : 200,
	        allowBlank:false,
	        displayField:'userName',
	        valueField: 'userCode',
	        store:new Ext.data.Store({
	        	fields:['userCode','userName'],
	        	autoLoad:false,
	        	proxy:{
	        		type: 'rest',
	        		url:''
	        	},
	        	sorters : [{
					property : 'userCode',
					direction : 'ASC'
				}]
	        }),
	        listeners:{
				expand:function(){
					var grid = Ext.ComponentQuery.query('creditCardGrid')[0];
					var startDate = Ext.util.Format.date(grid.down('#startDate').getValue(),'Y-m-d');
					var endDate = Ext.util.Format.date(grid.down('#endDate').getValue(),'Y-m-d');
					var store = grid.query('combobox')[1].getStore();
					store.proxy.url = 'personalMonitor/getDailyUserCode?startDate='+startDate+'&endDate='+endDate;
					store.load();
				}
	        }
		},{
			fieldLabel: "查询状态",
	        name: 'queryType',
	        xtype:'combobox',
	        labelWidth : 80,
	        width : 200,
	        allowBlank:false,
	        displayField:'queryTypeN',
	        valueField: 'queryType',
	        store:new Ext.data.Store({
	        	fields:['queryType','queryTypeN'],
	        	data : [{'queryType':'1','queryTypeN' : '未刷下班卡'},
	        	        {'queryType':'0','queryTypeN' : '刷下班卡不及时'}]
	        })
		},{
			xtype : 'button',
			itemId : 'search1',
			text : '查询',
			margin : '0 0 0 10',
			handler : function(){
				var grid = this.up('grid');
				grid.getStore().reload({
					params : grid.down('form').getForm().getValues()
				});
			}
		}]
	}],
	initComponent : function() {
		var me = this;
		this.callParent(arguments);
		// 设置默认查询时间
		me.down('form').form.findField('startDate').setValue(Ext.Date.add(new Date(),Ext.Date.DAY,-7),"Y-m-d");
		me.down('form').form.findField('endDate').setValue(Ext.Date.add(new Date(),Ext.Date.DAY,-1),"Y-m-d");

	}
});