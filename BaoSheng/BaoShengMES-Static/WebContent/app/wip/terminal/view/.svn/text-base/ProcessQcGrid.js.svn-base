/**
 * Created by joker on 2014/6/11 0011.
 */
Ext.define("bsmes.view.ProcessQcGrid", {
			extend : 'Ext.panel.Panel',
			alias : 'widget.processQcGrid',
			title : Oit.msg.wip.terminal.qcTitle,
			//store : 'ProcessQcStore',
			itemId : 'processQcGrid',
			labelWidth : 'auto',
			forceFit : false,
			width : document.body.scrollWidth,
			padding : '10 15 10 15',
			layout : 'vbox',
			initComponent : function() {
				var me = this, items = [], row = [];
				Ext.Array.each(me.dataArray, function(date, i) {
							row.push({
										fieldLabel : date.checkItemName,
										xtype : 'displayfield',
										width : (document.body.scrollWidth * 0.6 - 80) / 3,
										labelWidth : 150,
										value : date.itemTargetValue
									});
							if ((i + 1) % 3 == 0) {
								items.push({
											xtype : 'container',
											layout : 'hbox',
											items : row
										});
								row = [];
							}
						});

				me.items = {
					title : '生产要求',
					xtype : 'fieldset',
					anchor : '100%',
					layout : 'vbox',
					padding : '10 10 10 10',
					defaults : {
						width : document.body.scrollWidth * 0.6 - 80
					},
					items : items
				};

				this.callParent(arguments);
			}
		});