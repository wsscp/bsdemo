Ext.define("bsmes.view.MaterialCheckGrid", {
		extend : 'Oit.app.view.Grid',
		alias : 'widget.materialCheckGrid',
		itemId : 'materialCheckGrid',
		forceFit : true,
		defaultEditingPlugin : false,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight,
		store : 'MaterialCheckReportStore',
//		columns : [{
//					flex : 1.2,
//					minWidth : 60,
//					sortable : false,
//					menuDisabled : true,
//					text : '厂家',
//					dataIndex : 'manuName'
//				}, {
//					flex : 1.5,
//					minWidth : 75,
//					sortable : false,
//					menuDisabled : true,
//					text : '品名',
//					dataIndex : 'matName'
//				}, {
//					flex : 1,
//					minWidth : 50,
//					sortable : false,
//					menuDisabled : true,
//					text : '型号',
//					dataIndex : 'spec'
//				},{
//					flex : 1,
//					minWidth : 50,
//					sortable : false,
//					menuDisabled : true,
//					text : '库号',
//					dataIndex : 'warehouseNo'
//				},{
//					flex : 1,
//					minWidth : 50,
//					sortable : false,
//					menuDisabled : true,
//					text : '备注',
//					dataIndex : 'stockComment'
//				},{
//					flex : 1,
//					minWidth : 50,
//					sortable : false,
//					menuDisabled : true,
//					text : '生产日期',
//					dataIndex : 'manuDate'
//				}, {
//					flex : 1,
//					minWidth : 50,
//					sortable : false,
//					menuDisabled : true,
//					text : '收货人',
//					dataIndex : 'disciplinePhenomenon'
//		}],
		dockedItems : [{
			xtype : 'toolbar',
			dock : 'top',
			items : [{
						title : '查询条件',
						xtype : 'fieldset',
						collapsible : true,
						width : '100%',
						items : [{
									xtype : 'hform',
									id : 'formId',
									width : '100%',
									buttonAlign : 'left',
									labelAlign : 'right',
									bodyPadding : 5,
									items : [{
												fieldLabel : '库号',
												xtype : 'textfield',
												name : 'warehouseNo',
												labelWidth : 45,
												firstLoad : true,
												width : 250
											},{
												fieldLabel: "品种",
										        name: 'matName',
										        xtype:'textfield',
										        labelWidth : 45,
										        width : 250
											},{
												fieldLabel: "日期",
										        name: 'yearMonth',
										        xtype:'combobox',
										        name : 'yearMonth',
												displayField: 'yearMonth',
												valueField: 'yearMonth',
										        labelWidth : 45,
										        width : 250,
										        store:new Ext.data.Store({
													autoLoad:false,
												  fields:['yearMonth'],
												  		proxy : {										
												  			type : 'rest',
															url : 'materialCheck/getCheckMonth'
													},
													sorters : [{
														property : 'yearMonth',
														direction : 'ASC'
													}]
												}),
												listeners:{
													expand:function(){
															Ext.ComponentQuery.query('materialCheckGrid combobox')[0].getStore().load();
													}
												}
											},{
												fieldLabel: "工段",
										        name: 'sectionName',
										        xtype:'combobox',
										        queryMode: 'local',
										        displayField:'sectionName',
										        valueField: 'sectionName',
										        labelWidth : 45,
										        width : 250,
										        store : new Ext.data.Store({
										        	fields:['sectionCode','sectionName'],
										        	autoLoad:true,
										        	data : [{sectionCode : 'jy',sectionName : '绝缘'},
										        	        {sectionCode : 'ht',sectionName : '护套'},
										        	        {sectionCode : 'cl',sectionName : '成缆'},
										        	        {sectionCode : 'gxj',sectionName : '硅橡胶'},
										        	        {sectionCode : 'gw',sectionName : '高温'}]
										        })
											},{
												xtype : 'button',
												itemId : 'searchMaterialCheckReport',
												text : '查找',
												margin : '0 0 0 10'
											},{
												xtype : 'button',
												itemId : 'importMaterialCheck',
												text : '导入库存明细',
												margin : '0 0 0 175'
											}]
								}]
					}]
		}],		
		initComponent : function() {
			var me = this;
			var columns = [{
						flex : 1.2,
						minWidth : 120,
						sortable : false,
						menuDisabled : true,
						text : '厂家',
						dataIndex : 'manuName'
					}, {
						flex : 1.5,
						minWidth : 325,
						sortable : false,
						menuDisabled : true,
						text : '品名',
						dataIndex : 'matName'
					}, {
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						text : '型号',
						dataIndex : 'spec'
					},{
						flex : 1,
						minWidth : 50,
						sortable : false,
						menuDisabled : true,
						text : '库号',
						dataIndex : 'warehouseNo'
					},{
						flex : 1,
						minWidth : 175,
						sortable : false,
						menuDisabled : true,
						text : '备注',
						dataIndex : 'stockComment'
					},{
						flex : 1,
						minWidth : 100,
						sortable : false,
						menuDisabled : true,
						text : '生产日期',
						dataIndex : 'manuDate'
					}, {
						flex : 1,
						minWidth : 70,
						sortable : false,
						menuDisabled : true,
						text : '收货人',
						dataIndex : 'disciplinePhenomenon'
			}];
			
			var yearMonth = Ext.util.Format.date(new Date(), "Y-m");
			console.log(yearMonth);
			if(yearMonth != null){
				me.columns = columns;
				var store = new Ext.data.Store({
					fields : ['SEQ','checkDay'],
					proxy : {
						type : 'rest',
						url : 'materialCheck/getCheckDays?yearMonth='+yearMonth
					}
				});
				store.load(function(records, operation, success) {
					if(records != null){
						var length = records.length;
						var j = columns.length;
						for(var i=0;i<length;i++){
							var checkDay = store.getAt(i).raw.checkDay;
							var seq = store.getAt(i).raw.seq;
							var column = [{
								flex : 1,
								minWidth : 50,
								sortable : false,
								menuDisabled : true,
								text : checkDay,
								triStateSort : false,
								dataIndex : seq+''
							}];
							columns[j]=column[0];
							j++;
						}
						var reportStore = me.getStore();
						reportStore.load();
						console.log(reportStore)
						me.columns = columns;
						me.reconfigure(reportStore, columns);
					}
				});
			}
			this.callParent(arguments);
			
			// 设置默认查询时间
			me.down('form').form.findField('yearMonth').setValue(Ext.util.Format.date(new Date(), "Y-m"));
			
		}
});