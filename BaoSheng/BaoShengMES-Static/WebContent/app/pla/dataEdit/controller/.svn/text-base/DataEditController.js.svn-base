Ext.define('bsmes.controller.DataEditController', {
			extend : 'Ext.app.Controller',
			dataEditPanel : 'dataEditPanel',
			views : ['DataEditPanel'],
			stores : [],
			constructor : function() {
				var me = this;

				// 初始化refs
				me.refs = me.refs || [];

				/**
				 * 注册 - 合并生产WINDOW视图
				 */
				me.refs.push({
							ref : 'dataEditPanel',
							selector : me.dataEditPanel,
							autoCreate : true,
							xtype : me.dataEditPanel
						});

				me.callParent(arguments);
			},

			init : function() {
				var me = this;

				me.control(me.dataEditPanel + ' button[itemId=equipLineButton]', {
							click : me.equipLineButton
						});
				me.control(me.dataEditPanel + ' button[itemId=inQuantityButton]', {
							click : me.inQuantityButton
						});
				me.control(me.dataEditPanel + ' button[itemId=processQcButton]', {
							click : me.processQcButton
						});

			},

			onSearch : function(btn) {
				var me = this;
			},

			equipLineButton : function(btn) {
				var me = this;
				
			},

			inQuantityButton : function(btn) {
				var me = this;
			},

			processQcButton : function(btn) {
				var me = this;
			}

		});
