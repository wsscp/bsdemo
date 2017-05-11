Ext.define("bsmes.view.EquipmentStatusImage", {
			extend : 'Ext.panel.Panel',
			alias : 'widget.equipmentStatusImage',
			equipStatus : '',
			equipCode : '',
			equipName : '',
			equipAlias : '',
			width : 90,
			height : 70,
			initComponent : function() {
				var me = this;
				me.items = [{
							width : 90,
							height : 90,
							style : {
								borderWidth : 1,
								borderColor : '#EDEDED',
								borderStyle : 'solid'
							},
							items : [{
										xtype : 'container',
										width : 90,
										height : 30,
										layout : {
											type : 'hbox',
											align : 'middle ',
											pack : 'center'
										},
										padding : '10 0 0 0',
										items : [{
													xtype : 'box',
													width : 16,
													height : 16,
													id : 'status_' + me.equipCode,
													autoEl : {
														tag : 'img',
														src : '/bsstatic/icons/' + me.equipStatus + '-min.png'
													},
													listeners : {
														scope : this,
														el : {
															click : function(e, a) {
																me.doSearchItem();
															}
														}
													}
												}]
									}, {
										xtype : 'container',
										width : 90,
										height : 15,
										layout : {
											type : 'hbox',
											align : 'middle ',
											pack : 'center'
										},
										items : [{
													xtype : 'label',
													text : me.equipCode
												}]
									}, {
										xtype : 'container',
										padding : '4 0 0 0',
										width : 90,
										height : 20,
										layout : {
											type : 'hbox',
											align : 'middle ',
											pack : 'center'
										},
										items : [{
													xtype : 'label',
													text : me.equipAlias
												}]
									}]
						}];
				me.callParent(arguments);
			},
			doSearchItem : function() {
				var me = this;
				parent.openTab(Oit.msg.wip.realTimeEquipmentStatus.equipStatusMointorHistory,
						'wip/realEquipStatusChart.action?code=' + me.equipCode + '&name=' + me.equipName);
			}
		});
