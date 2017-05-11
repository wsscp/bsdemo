//var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
//    clicksToMoveEditor: 1,
//    autoCancel: false,
//    listeners:{
//    	edit : function(editor, context, eOpts){
//    		var id = context.record.get('id');
//    		var contractNo = context.record.get('contractNo');
//    		var custProductType = context.record.get('custProductType');
//    		var contractLength = context.record.get('contractLength');
//        	var ptFinishTime = Ext.util.Format.date(context.record.get('ptFinishTime'),"Y-m-d");
//        	var clFinishTime = Ext.util.Format.date(context.record.get('clFinishTime'),"Y-m-d");
//        	var bzFinishTime = Ext.util.Format.date(context.record.get('bzFinishTime'),"Y-m-d");
//        	var htFinishTime = Ext.util.Format.date(context.record.get('htFinishTime'),"Y-m-d");
//        	var coordinateTime = Ext.util.Format.date(context.record.get('coordinateTime'),"Y-m-d");
//        	var remarks = context.record.get('remarks');
//        	var infosources = Ext.ComponentQuery.query('#infosources')[0].getChecked()[0].boxLabel;
//        	if(ptFinishTime != null && ptFinishTime != ""&&clFinishTime != null && clFinishTime != ""&&bzFinishTime != null && bzFinishTime != ""&&htFinishTime != null && htFinishTime != "")
//        		{
//        			var ptFinishTime = Ext.util.Format.date(context.record.get('ptFinishTime'),"Y-m-d");
//		        	var clFinishTime = Ext.util.Format.date(context.record.get('clFinishTime'),"Y-m-d");
//		        	var bzFinishTime = Ext.util.Format.date(context.record.get('bzFinishTime'),"Y-m-d");
//		        	var htFinishTime = Ext.util.Format.date(context.record.get('htFinishTime'),"Y-m-d");
//		        	var coordinateTime = Ext.util.Format.date(context.record.get('coordinateTime'),"Y-m-d");
//        		}
//        	else{
//	        	if(ptFinishTime != null && ptFinishTime != ""){
//	        		clFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('ptFinishTime'),Ext.Date.DAY, +3), "Y-m-d");
//	        		bzFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('ptFinishTime'),Ext.Date.DAY, +6), "Y-m-d");
//	        		htFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('ptFinishTime'),Ext.Date.DAY, +9), "Y-m-d");
//	        	}else if(clFinishTime != null && clFinishTime != ""){
//	    			ptFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('clFinishTime'),Ext.Date.DAY, -3), "Y-m-d");
//	        		bzFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('clFinishTime'),Ext.Date.DAY, +3), "Y-m-d");
//	        		htFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('clFinishTime'),Ext.Date.DAY, +6), "Y-m-d");
//	        	}else if(bzFinishTime != null && bzFinishTime != ""){
//	        		ptFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('bzFinishTime'),Ext.Date.DAY, -6), "Y-m-d");
//	        		clFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('bzFinishTime'),Ext.Date.DAY, -3), "Y-m-d");
//	        		htFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('bzFinishTime'),Ext.Date.DAY, +3), "Y-m-d");
//	        	}else if(htFinishTime != null && htFinishTime != ""){
//	        		ptFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('htFinishTime'),Ext.Date.DAY, -9), "Y-m-d");
//	        		clFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('htFinishTime'),Ext.Date.DAY, -6), "Y-m-d");
//	        		bzFinishTime = Ext.util.Format.date(Ext.Date.add(context.record.get('htFinishTime'),Ext.Date.DAY, -3), "Y-m-d");
//	        	}
//        	}
//        		Ext.Ajax.request({
//    				url : 'manualManage/updateData',
//    				params : {
//    					id : id,
//    					contractNo:contractNo,
//    					custProductType : custProductType,
//    	            	ptFinishTime : ptFinishTime,
//    	            	clFinishTime : clFinishTime,
//    	            	bzFinishTime : bzFinishTime,
//    	            	htFinishTime : htFinishTime,
//    	            	remarks : remarks,
//    	            	coordinateTime : coordinateTime,
//    	            	infosources : infosources
//    				},
//    				success : function(response) {
//    					Ext.ComponentQuery.query('manualManageList')[0].getStore().reload();
//    				}
//    			});
//    		
//    	},
//    	
//    	cancelEdit : function(){
//    		Ext.ComponentQuery.query('manualManageList')[0].getStore().reload();
//    		}
//    	}
//    }
//);


Ext.define("bsmes.view.ManualManageList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.manualManageList',
			id : 'manualManageList',
			store : 'ManualManageStore',
			forceFit : false,
			stripeRows : true,
			selType : 'checkboxmodel',
			selModel : {
				mode : "SIMPLE"
//				checkOnly : true
			},
			columns : [{
						text : '合同号',
						dataIndex : 'contractNo',
						width : 60,
						sortable : false,
						renderer : function(value, metaData, record) {
							var me = this;
							var reg = /[a-zA-Z]/g;
							value = (value.replace(reg, "").length > 5 ? value.replace(reg, "").substring(value.replace(reg, "").length
									- 5) : value.replace(reg, ""));
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : '客户型号规格',
						dataIndex : 'custProductType',
						flex : 2,
						minWidth : 140,
						renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
							var me = this;
							metaData.tdAttr = 'data-qtip="' + value + '"';
							return value;
						}
					}, {
						text : '交货长度',
						flex : 1,
						minWidth : 80,
						dataIndex : 'contractLength'
					}, {
						text : '配套完成时间',
						flex : 2,
						minWidth : 60,
						dataIndex : 'ptFinishTime',
			        	renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						text : '成缆完成时间',
						flex : 2,
						minWidth : 60,
						dataIndex : 'clFinishTime',
			        	renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						text : '编织完成时间',
						flex : 2,
						minWidth : 60,
						dataIndex : 'bzFinishTime',
			        	renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						text : '护套完成时间',
						flex : 2,
						minWidth : 60,
						dataIndex : 'htFinishTime',
			        	renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						text : '备注',
						flex : 2,
						minWidth : 250,
						dataIndex : 'remarks'
						
					}, {
						text : '排产时间',
						flex : 2,
						minWidth : 60,
						dataIndex : 'coordinateTime',
			        	renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						text : '信息来源',
						flex : 2,
						minWidth : 200,
						dataIndex : 'infoSources'
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
							layout : 'column',
							defaults : {
								width : 300,
								padding : 1,
								labelAlign : 'right'
							}
						},
						items : [{
							items : [{
								fieldLabel : '合同号',
								xtype : 'combobox',
								name : 'contractNo',
								queryMode: 'local',
								displayField: 'contractNo',
								valueField: 'contractNo',
								typeAhead : true, // 是否延迟查询
								typeAheadDelay : 1000, // 延迟时间
								store : new Ext.data.Store({
								   autoLoad:false,
								   fields:['contractNo'],
								   proxy : {
												type : 'rest',
												url : 'manualManage/getcontractNo/-1'
											}
								}),
								listeners : {
									beforequery : function(e) {
										var combo = e.combo;
										conEmpty = true;
										combo.collapse(); // 折叠
										if (!e.forceAll) {
											conEmpty = false;
											var value = e.query;
											if (value == null || value == '') {
												value = -1
											}
											combo.store.getProxy().url = 'manualManage/getcontractNo' + '/' + value;
											var f = this.up("toolbar").down("form").getForm().getValues();
											combo.store.load({
														params : f
													});
											combo.expand(); // 展开
											return false;
										}
									},
									expand : function(e) {
										if (conEmpty) {
											var f = this.up("toolbar").down("form").getForm().getValues();
											e.getStore().getProxy().url = 'manualManage/getcontractNo/-1';
											e.getStore().load({
														params : f
													});
										}
									}
								}
							}, {
								fieldLabel : '配套完成时间',
								xtype : 'datefield',
								itemId : 'datetext',
								name : 'ptFinishTime',
								format : 'Y-m-d'
							}, {
								fieldLabel : '成缆完成时间',
								xtype : 'datefield',
								itemId : 'datetext1',
								name : 'clFinishTime',
								format : 'Y-m-d'
							}, {
								fieldLabel : '编织完成时间',
								xtype : 'datefield',
								itemId : 'datetext2',
								name : 'bzFinishTime',
								format : 'Y-m-d'
							}, {
								fieldLabel : '护套完成时间',
								xtype : 'datefield',
								itemId : 'datetext3',
								name : 'htFinishTime',
								format : 'Y-m-d'
							}, {
								fieldLabel : '排产时间',
								xtype : 'datefield',
								itemId : 'datetext4',
								name : 'coordinatetime',
								format : 'Y-m-d'
							}, {
								fieldLabel : '信息来源',
								xtype : 'combobox',
								name : 'infosources',
								displayField: 'infoSources',
								valueField: 'infoSources',
								store : new Ext.data.Store({
								   fields:['infoSources'],
								   proxy : {
												type : 'rest',
												url : 'manualManage/getinfosources/-1'
											}
								})
							}]
								}],
						buttons : [{
									itemId : 'search',
									text : '查找'
								}, {
									itemId : 'reset',
									text : '重置',
									handler : function(e) {
										this.up("form").getForm().reset();
									}
								},{
									itemId : 'change',
									text : '修改'
								},{
									itemId : 'export',
									text : '导入到exl'
								}]
					}]
				}]
			}],
			initComponent : function() {
				var me = this;
				this.callParent(arguments);
			}
		});


