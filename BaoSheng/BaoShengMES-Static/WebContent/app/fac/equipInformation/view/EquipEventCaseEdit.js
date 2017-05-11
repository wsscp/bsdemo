Ext.define('bsmes.view.EquipEventCaseEdit', {
	extend: 'Ext.window.Window',
	alias: 'widget.equipEventCaseEdit',
	id : 'equipEventCaseEdit',
	title: '设备维修登记',
	modal: true,
	plain: true,
	layout: 'fit',
	autoScroll : true,
	width: 650,
	height : 500,
	items: [{
		xtype : 'panel',
		bodyPadding: '12 10 10',
		width: '100%',
	    defaultType: 'textfield',
        defaults:{
            labelAlign:'right'
        },
        bodyStyle : 'overflow-x:visible; overflow-y:scroll',
		items : [{
			xtype : 'hform',
			width : '100%',
			bodyPadding: '12 10 10',
			defaults:{
	            labelAlign:'right'
	        },
			layout: {
		        type: 'table',
		        columns: 2
		    },
			items : [{
				fieldLabel : 'id',
				name : 'id',
				hidden : true
			},{
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
		        name: 'equipModelStandard',
		        allowBlank: false
			},{
				fieldLabel: '开始修理时间',
				xtype : 'datefield',
				format : 'Y-m-d H:i:s',
		        name: 'startRepairTime',
		        allowBlank: false
			},{
				fieldLabel: '所在位置',
				xtype:'displayfield',
		        name: 'weizhi',
		        value : '1A3D',
		        allowBlank: false
			},{
				fieldLabel: '完成修理时间',
		        name: 'finishRepairTime',
		        xtype : 'datefield',
		        format : 'Y-m-d H:i:s',
		        allowBlank: false
			},{
				fieldLabel: '报修人',
				xtype:'displayfield',
		        name: 'protectMan',
		        allowBlank: false
			},{
				fieldLabel: '修后反馈',
				xtype:'displayfield',
		        name: 'x',
		        value : '好-操作工</br>好-班组长',
		        allowBlank: false
			},{
				fieldLabel: '修理人',
		        name: 'repairMan',
		        allowBlank: false
			},{
				xtype: 'radiogroup',
		        fieldLabel: '故障类型',
		        width : '250px',
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
	        name      : 'equipTroubleDescribetion',
	        labelAlign : 'top',
	        width : 475,
	        margin : '0 0 0 50',
	        fieldLabel: '设备故障状况描述'
		},{
			xtype     : 'textareafield',
	        grow      : true,
	        name      : 'equipTroubleAnalyze',
	        labelAlign : 'top',
	        width : 475,
	        margin : '0 0 0 50',
	        fieldLabel: '故障原因分析'
		},{
			xtype     : 'textareafield',
	        grow      : true,
	        name      : 'repairMeasures',
	        labelAlign : 'top',
	        width : 475,
	        margin : '0 0 0 50',
	        fieldLabel: '修理措施'
		},{
			title : '零件更换',
			xtype : 'fieldset',
			margin : '0 10 0 50',
			width : '475',
			items : [{
				xtype : 'grid',
				id : 'sparePartId',
				width : 450,
				margin : '0 10 10 0',
				store : 'SparePartStore',
				selModel : {
					mode : 'MULTI'
				},
				selType : 'checkboxmodel',
				tbar: [{ 
					xtype: 'button', 
					itemId : 'replace',
					text: '替换' 
				},{
					xtype: 'button', 
					text: '删除',
					handler : function(){
						var grid = Ext.ComponentQuery.query('#equipEventCaseEdit grid')[0];
						var selection = grid.getSelectionModel().getSelection();
			  			if(selection && selection!=''){
			  				Ext.Msg.show({
								title: Oit.msg.PROMPT,
		        			    msg: '确定删除?',
		        			    buttons: Ext.Msg.YESNO,
		        			    icon: Ext.Msg.QUESTION,
		        			    fn: function(buttonId){
		        			    	if(buttonId == 'yes'){
		        			    		var row = selection[0];
		        			    		var id = row.get('id');
		        			    		Ext.Ajax.request({
		        			    		    url: 'equipInformation/deleteSparePart',
		        			    		    params: {
		        			    		        id: id
		        			    		    },
		        			    		    success: function(response){
		        			    		    	grid.getStore().reload();
		        			    		    }
		        			    		});
		        			    	}
		        			    }
							})
			  			}else{
			  				Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条数据！');
			  			}
					}
				}],
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
					text : '被替换件编码',
					flex : 1,
					dataIndex : 'oldSparePartCode'
				},{
					text : '被替换件情况',
					flex : 1,
					dataIndex : 'oldSparePartSituation'
				}]
			}]
		}]
	}],
	initComponent: function() {
		var me = this;
		Ext.apply(me, {
			buttons: [{
				itemId: 'save',
				text:'保存',
				handler : function(){
					Ext.Msg.show({
						title: Oit.msg.PROMPT,
        			    msg: '确定保存?',
        			    buttons: Ext.Msg.YESNO,
        			    icon: Ext.Msg.QUESTION,
        			    fn: function(buttonId){
						    	if(buttonId == 'yes'){
						    		var form = Ext.ComponentQuery.query('#equipEventCaseEdit form')[0];
									var s = form.getValues();
									var data = [];
									var t = Ext.ComponentQuery.query('#equipEventCaseEdit textareafield');
									data.push({
										eventInfoId : s.id,
										repairMeasures : t[2].value,
										startRepairTime : s.startRepairTime,
										finishRepairTime : s.finishRepairTime,
										repairMan : s.repairMan,
										failureModel : s.failureModel,
										equipTroubleDescribetion : t[0].value,
										equipTroubleAnalyze : t[1].value
									});
									Ext.Ajax.request({
				    				    url: 'equipInformation/saveEquipRepairRecord',
				    				    params: {
				    				        jsonData: Ext.encode(data)
				    				    },
				    				    success: function(response){
				    				        Ext.Msg.alert(Oit.msg.PROMPT, '保存完成');
				    				        Ext.ComponentQuery.query('#equipEventCaseEdit')[0].close();
				    				    }
				    				});
								}
					    	}
					});
				}
			}
//			,{
//				itemId: 'ok',
//				text:'完成'
//			}
			,{
				itemId: 'cancel',
				text:Oit.btn.cancel,
				scope: me,
				handler: me.close
			}]
		});
		this.callParent(arguments);
	} 
});
