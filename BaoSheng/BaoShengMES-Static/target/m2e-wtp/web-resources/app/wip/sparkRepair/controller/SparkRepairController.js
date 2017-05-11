Ext.define("bsmes.controller.SparkRepairController", {
			extend : 'Oit.app.controller.GridController',
			view : 'sparkRepairList',
			views : ['SparkRepairList'],
			stores : ['SparkRepairStore'],
			constructor : function(){
				var me = this;
				// 初始化refs
				me.refs = me.refs || [];

				me.callParent(arguments);

				setInterval(function(){
							me.refresh.apply(me);
						}, 10000);
			},
			refresh: function(){
			    var me = this;
			    me.getGrid().getStore().load();
			}
			
		})