Ext.define("bsmes.view.SemiFinishedProductsView",{
	extend: 'Oit.app.view.Grid',
	alias: 'widget.semiFinishedProductsView',
	id : 'SemiFinishedProductsView',
	store : 'SemiFinishedProductsStore',
	defaultEditingPlugin : false,
	columns : [{
		dataIndex : 'contractNo',
		flex : 0.5,
		text : '合同号',
		renderer: function(value, metaData, record){
			return value.substr(value.length-4,value.length);
		}
	},{
		dataIndex : 'matCode',
		flex : 1.5,
		text : '物料代码',
		renderer: function(value, metaData, record){	
			metaData.tdAttr='data-qtip="'+value+'"';
			return value;
		}
	},{
		dataIndex : 'matName',
		flex : 1.2,
		text : '物料名称',
	},{
		dataIndex : 'productType',
		flex : 1.5,
		text : '产品型号'
	},{
		dataIndex : 'productSpec',
		flex : 0.5,
		text : '产品规格'
	},{
		dataIndex : 'taskLength',
		flex : 0.5,
		text : '长度'
	},{
		dataIndex : 'wireCoil',
		flex : 0.5,
		text : '盘具'
	},{
		dataIndex : 'processName',
		flex : 0.5,
		text : '所在工序'
	},{
		dataIndex : 'finishDate',
		flex : 1,
		text : '最后工序结束时间',
		renderer: function(value, metaData, record){
			var date=Ext.Date.format(value,'Y-m-d H:s:i');
			return date;
		}
	},{
		dataIndex : 'locationName',
		flex : 1,
		text : '存放位置'
	},{
		dataIndex : 'userName',
		flex : 0.5,
		text : '盘点人'
	}],
	dockedItems: [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			title : '查询条件',
			xtype : 'fieldset',
			collapsible : true,
			width : '100%',
			items : [{
				xtype : 'form',
				width : '100%',
				layout : 'hbox',
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
				items : [{
				    fieldLabel : '合同号',
					name : 'contractNo',
					id:'contractNo',
					xtype: 'combobox',
					width : 220,
					labelWidth:70,
					displayField : 'contractNo',
					valueField:'contractNo',
					selectOnFocus : true,
					hideTrigger : true,
					minChars : 1,
					height: 25,
					margin: '10,15,10,10',
					store: new Ext.data.Store({
						fields:['contractNo'],
						proxy:{
							type:'rest',
							url:'SemiFinishedProducts/searchContractNo/1'
						}
					}),
					listeners: {
				    	 beforequery : function(e) {
								var combo = e.combo;
								combo.collapse();
								if (!e.forceAll) {
									var value = e.query;
									if(value==null||value==''){
										value=-1;
									}
									combo.store.getProxy().url='SemiFinishedProducts/searchContractNo'+'/'+value;
									combo.store.load();
									combo.expand();
									return false;
								}
							}
				    }				
				},{
				    fieldLabel : '工序名称',
					name : 'processName',
					id:'processName',
					xtype: 'combobox',
					width : 220,
					labelWidth:70,
					displayField : 'processName',
					valueField:'processName',
					selectOnFocus : true,
					height: 25,
					margin: '10,15,10,10',
					store: new Ext.data.Store({
						fields:['processName'],
						proxy:{
							type:'rest',
							url:'SemiFinishedProducts/searchProcessName'
						}
					})			
				}],
				buttons : [{
						itemId : 'search',
						text : '查询'
					}, {
						itemId : 'reset',
						text : '重置',
						handler : function(e){
						this.up("form").getForm().reset();
					}
				}]
			}]
		}]
	}] 	              
	      
});