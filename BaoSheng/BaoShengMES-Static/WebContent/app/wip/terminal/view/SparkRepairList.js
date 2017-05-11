/**
 * 火花点查看页面
 */
Ext.define("bsmes.view.SparkRepairList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.sparkRepairList',
	store : 'SparkRepairStore',
	height:document.body.scrollHeight-140,
	defaultEditingPlugin : false,
	columns : [{
		text : Oit.msg.wip.sparkRepair.contractNo,
		flex: 1,
		dataIndex : 'contractNo',
		renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
			var reg = /[a-zA-Z]/g;
			var contractNo = value.replace(reg, "");
            return contractNo.substring(contractNo.length - 5);
        }
	},{
		text : Oit.msg.wip.sparkRepair.workOrderNo,
		flex: 1,
		dataIndex : 'workOrderNo'
	}, {
		text : Oit.msg.wip.sparkRepair.sparkPosition,
		flex: 0.6,
		dataIndex : 'sparkPosition'
	}, {
		text : Oit.msg.wip.sparkRepair.repairType,
		flex: 1,
		dataIndex : 'repairType'
	}, {
		text : Oit.msg.wip.sparkRepair.createTime,
		flex: 1,
		dataIndex : 'createTime',
		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i')
	}, {
		text : Oit.msg.wip.sparkRepair.status,
		dataIndex : 'status',
		renderer:function(value){
			if(value=='UNCOMPLETED'){
				return "未修复";
			}else if(value=='COMPLETED'){
				return "已修复";
			}else{ return '' }
		},
		flex: 0.6
	}
	],
	dockedItems : [{
		xtype: 'container',
		margin: '10',
		layout: 'hbox',
		items:[{
				xtype : 'hform',
				items: [{
				        name: 'statusFindParam',
				        xtype: 'checkboxgroup', 
		                //columns: 4, // 一行显示几个
		                layout: { // 设置自动间距样式为false
		                    autoFlex: false
		                },
		                width:320,
		                defaults: { // 设置样式
		                    margin: '5 20 0 0'
		                	//boxLabelCls: 80  // 设置label的宽度
		                },
		                items: [  
		                    {boxLabel: '未处理', boxLabelCls: 'x-boxlabel-size-20', name: 'statusFindParam', inputValue: 'UNCOMPLETED', checked: true},     
		                    {boxLabel: '已处理', boxLabelCls: 'x-boxlabel-size-20', name: 'statusFindParam', inputValue: 'COMPLETED'}                   
		                ],
						listeners:{
							change:function(obj, newValue, oldValue, eOpts ){
								var me = this;
								var store = me.up('grid').getStore();
								store.loadPage(1, {
									params: {
										statusFindParam: newValue
									}
								});
							}
						}
					}
				]
			},{
				itemId : 'handleSpark',
				xtype:'button',
				text : '处理火花点',
				margin: '0 10 0 10'
			},{
				text : Oit.btn.close,
				xtype:'button',
				margin: '0 10 0 10',
				handler: function(){
					var me = this;
					me.up('window').close();
				}
			}
		
		]		
	}]
});