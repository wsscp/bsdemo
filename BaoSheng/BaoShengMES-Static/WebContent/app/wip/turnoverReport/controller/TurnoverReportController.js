Ext.define("bsmes.controller.TurnoverReportController", {
			extend : 'Oit.app.controller.GridController',
			view : 'turnoverReportGrid',
			views : ['TurnoverReportGrid'],
			stores : ['TurnoverReportStore'],
			constructor : function(){
				var me = this;
				// 初始化refs
				me.refs = me.refs || [];

				me.callParent(arguments);

			}
			
		})