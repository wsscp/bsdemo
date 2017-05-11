var sparkRepairMsg;
var sparkRepairId = [];
Ext.define("bsmes.view.SparkRepairList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.sparkRepairList',
	store : 'SparkRepairStore',
	forceFit : false,
	
	defaultEditingPlugin : false,
	viewConfig: {
        stripeRows: false,
        enableTextSelection : true,
        getRowClass: function (record, rowIndex, rowParams, store) {
        	var resultColor = '';
            if(rowIndex == 0){
                sparkRepairMsg = '';
            }
            
            var id = record.get("id");
            if (record.get("isNotice")) {
            	if($.inArray(id, sparkRepairId) < 0){
            	  sparkRepairMsg += '设备['+record.get("equipCode")+']，生产单号['+record.get("workOrderNo")+']产生火花点，位置['+record.get("sparkPosition")+']<br/>';
            	  sparkRepairId.push(id);
            	}
            }
            
            if(store.getCount() == (rowIndex + 1) && sparkRepairMsg != ''){
                Ext.MessageBox.show({
	           	     title: Oit.msg.PROMPT,
	        	     msg: '<span style="font-size:14px;line-height: 16px;color:red;font-weight: bold;">'+sparkRepairMsg+'</span>',
	        	     buttons: Ext.Msg.YES,
	        	     icon: Ext.Msg.WARNING
	        	});
	        	
            }
            
            if(record.get("status") == 'UNCOMPLETED'){
                resultColor =  'x-grid-record-red';
            }
            
            return resultColor;
        }
    },
	columns : [{
				width : 200,
				text : '合同号1',
				dataIndex : 'contractNo'
			}, {
				width : 200,
				text : '生产单号',
				dataIndex : 'workOrderNo'
			}, {
				width : 250,
				text : '产品代号',
				dataIndex : 'productCode'
			}, {
				width : 100,
				text : '火花点位置',
				dataIndex : 'sparkPosition'
			}, {
				width : 200,
				text : '设备代号',
				dataIndex : 'equipCode'
			}, {
				width : 100,
				text : '修复人',
				dataIndex : 'repairUserName'
			}, {
				width : 150,
				text : '修复类型',
				dataIndex : 'repairType'
			}, {
				width : 100,
				text : '修复状态',
				dataIndex : 'status',
				renderer : function(value){
					if (value == 'UNCOMPLETED') {
						return '未修复';
					} else if (value == 'COMPLETED') {
						return '已修复';
					} else {
						return ''
					}
				}
			}, {
				width : 150,
				text : '产生时间',
				dataIndex : 'createTime',
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i')
			}, {
				width : 150,
				text : '修复时间',
				dataIndex : 'modifyTime',
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i')
			}],
	dockedItems : [{
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
				items : [{
					items : [{
								fieldLabel : Oit.msg.wip.sparkRepair.contractNo,
								xtype : 'textfield',
								name : 'contractNo'
							}, {
								fieldLabel : Oit.msg.wip.sparkRepair.productCode,
								xtype : 'textfield',
								name : 'productCode'
							}, {
								fieldLabel : Oit.msg.wip.sparkRepair.equipCode,
								xtype : 'textfield',
								name : 'equipCode'
							}, {
								fieldLabel : Oit.msg.wip.sparkRepair.repairType,
								xtype : 'combo',
								name : 'repairType',

								margin : '0 0 0 50',
								itemId : 'repairType',
								displayField : 'name',
								valueField : 'id',
								store : new Ext.data.Store({
											fields : ['name', 'id'],
											proxy : {
												type : 'rest',
												url : 'sparkRepair/repairType'
											}
										})
							}]
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
						}, {
							itemId : 'exportToXls',
							text : Oit.btn.export
						}]

			}]
		}]
	}]
}
);