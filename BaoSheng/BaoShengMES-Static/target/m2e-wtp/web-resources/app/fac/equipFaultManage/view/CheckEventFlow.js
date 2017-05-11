Ext.define('bsmes.view.CheckEventFlow', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.checkEventFlow',
	title: '设备维修记录',
	width: 700,
	height : document.body.scrollHeight-100,
	maxHeight : 850,
	overflowY  : 'auto',
	overflowX : 'hidden',
	defaults:{
        margin : '0 0 0 30'
    },
	formItems: [{
		xtype : 'hform',
		bodyPadding: '15 10 10',
		defaults:{
            labelAlign:'left'
        },
		layout: {
	        type: 'table',
	        columns: 2,
	        tableAttrs: {
	            style: {
	                width: '100%'
	            }
	        }
	    },
		items : [{
				fieldLabel: '设备名称',
				xtype:'displayfield',
		        name: 'equipName',
		        allowBlank: false
			},{
				fieldLabel: '报修时间',
				xtype:'displayfield',
		        name: 'createTime',
		        renderer : Ext.util.Format.dateRenderer('Y-m-d H:i'),
		        allowBlank: false
			},{
				fieldLabel: '型号规格',
				xtype:'displayfield',
				width : 250,
		        name: 'equipModelStandard',
		        allowBlank: false
			},{
				fieldLabel: '开始修理时间',
				xtype:'displayfield',
				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i'),
		        name: 'startRepairTime',
		        allowBlank: false
			},/*{
				fieldLabel: '所在位置',
				xtype:'displayfield',
		        name: 'weizhi',
		        value : '1A3D',
		        allowBlank: false
			},*/{
				fieldLabel: '报修人',
				xtype:'displayfield',
		        name: 'protectMan',
		        allowBlank: false
			},{
				fieldLabel: '完成修理时间',
		        name: 'finishRepairTime',
		        xtype:'displayfield',
		        renderer : Ext.util.Format.dateRenderer('Y-m-d H:i'),
		        allowBlank: false
			},/*{
				fieldLabel: '修后反馈',
				xtype:'displayfield',
		        name: 'x',
		        value : '好-操作工</br>好-班组长',
		        allowBlank: false
			},*/{
				fieldLabel: '修理人',
		        name: 'repairMan',
		        xtype:'displayfield',
		        allowBlank: false
			},{
				xtype: 'radiogroup',
		        fieldLabel: '故障类型',
		        width : 230,
		        columns: 2,
		        vertical: true,
		        items: [
		            { boxLabel: '机械', name: 'failureModel', inputValue: '机械' },
		            { boxLabel: '电气', name: 'failureModel', inputValue: '电气'}
		        ]
			}]
		},{
			xtype     : 'textareafield',
	        grow      : true,
	        readOnly : true,
	        name      : 'equipTroubleDescribetion',
	        labelAlign : 'top',
	        width  : 580,
	        height : 100,
	        margin : '0 0 30 0',
	        fieldLabel: '设备故障状况描述'
		},{
			xtype     : 'textareafield',
	        grow      : true,
	        readOnly : true,
	        name      : 'equipTroubleAnalyze',
	        labelAlign : 'top',
	        width : 580,
	        height : 100,
	        margin : '0 0 30 0',
	        fieldLabel: '故障原因分析'
		},{
			xtype     : 'textareafield',
	        grow      : true,
	        readOnly : true,
	        name      : 'repairMeasures',
	        labelAlign : 'top',
	        width : 580,
	        height : 100,
	        margin : '0 0 30 0',
	        fieldLabel: '修理措施'
		},{
			title : '零件更换',
			xtype : 'fieldset',
			width : 580,
			margin : '0 0 30 0',
			items : [{
				xtype : 'grid',
				id : 'sparePartId',
				margin : '0 10 10 0',
				store : 'SparePartStore',
				columns : [{
					text : 'id',
					dataIndex : 'id',
					hidden : true
				},{
					text : '新备件编码',
					flex : 0.8,
					dataIndex : 'newSparePartCode'
				},{
					text : '备件型号规格',
					flex : 1,
					dataIndex : 'sparePartModel'
				},{
					text : '使用部位',
					flex : 0.7,
					dataIndex : 'useSite'
				},{
					text : '替换数量',
					flex : 0.7,
					dataIndex : 'quantity'
				},{
					text : '被替换件编码',
					flex : 1,
					dataIndex : 'oldSparePartCode'
				},{
					text : '被替换件情况',
					flex : 1,
					dataIndex : 'oldSparePartSituation'
				}]
			}]
	}],
	initComponent: function() {
		var me = this;
		
		this.callParent(arguments);
		var ok = Ext.ComponentQuery.query('#ok')[1];
		ok.setVisible(false);
	},

});
