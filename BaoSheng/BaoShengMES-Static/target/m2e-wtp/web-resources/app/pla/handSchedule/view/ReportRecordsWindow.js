// 生产单列表: 查看报工记录
Ext.define('bsmes.view.ReportRecordsWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.reportRecordsWindow',
			title : '查看报工记录',
			width : document.body.scrollWidth * 0.8,
			height : document.body.scrollHeight * 0.8,
			modal : true,
			plain : true,
			layout : 'fit',
			initComponent : function() {
				var me = this;

				me.items = [{
							xtype : 'grid',
							columnLines : true,
							store : 'ReportRecordsStore',
							columns : [{
										text : '线盘号',
										dataIndex : 'coilNum',
										flex : 1,
										sortable : false,
										menuDisabled : true
									}, {
										text : '合同号-产品型号规格',
										dataIndex : 'contractNo',
										flex : 3.5,
										sortable : false,
										menuDisabled : true,
										renderer : function(value, metaData, record) {
											var res = '';
											for (var i = 0; i < value.split(",").length; i++) {
												var contractNo = value.split(",")[i];
												res += contractNo + "<br/>";
											}
											metaData.tdAttr = 'data-qtip="' + res + '"';
											return res;
										}
									}, {
										text : '颜色',
										dataIndex : 'color',
										flex : 1.5,
										sortable : false,
										menuDisabled : true,
										renderer : function(value, metaData, record) {
											metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
											return value;
										}
									}, {
										text : '报工长度',
										dataIndex : 'reportLength',
										flex : 1,
										sortable : false,
										menuDisabled : true
									}, {
										text : '报工人',
										dataIndex : 'reportUserName',
										flex : 2,
										sortable : false,
										menuDisabled : true,
										renderer : function(value, metaData, record) {
											metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
											return value;
										}
									}, {
										text : '报工机台',
										dataIndex : 'reportEquip',
										flex : 2,
										sortable : false,
										menuDisabled : true
									}, {
										text : '报工时间',
										dataIndex : 'reportTime',
										flex : 1.8,
										sortable : false,
										menuDisabled : true,
										renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
											var gedt = Ext.util.Format.date(value, 'm-d h:i:s');
											return gedt;
										}
									}]
						}];

				Ext.apply(me, {
							buttons : ['->', {
										text : Oit.btn.cancel,
										scope : me,
										handler : me.close
									}]
						});

				this.callParent(arguments);
			}
		});
