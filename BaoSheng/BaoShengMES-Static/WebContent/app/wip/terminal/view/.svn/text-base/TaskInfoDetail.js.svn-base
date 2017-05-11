/**
 * Created by joker on 2014/6/11 0011.
 */
Ext.define("bsmes.view.TaskInfoDetail", {
			extend : 'Ext.grid.Panel',
			alias : 'widget.taskInfoDetail',
			store : 'TaskInfoStore',
			labelWidth : 'auto',
			forceFit : false,
			width : document.body.scrollWidth,
			title : '质量要求',
			viewConfig : {
				stripeRows : false,
				getRowClass : function(record, rowIndex, rowParams, store) {
					var statusColorMap = {
						FINISHED : 'x-grid-record-yellow',
						IN_PROGRESS : 'x-grid-record-green',
						TO_DO : 'x-grid-record-white'
					}
					var status = record.get("STATUS");
					var equipCode = Ext.fly('equipInfo').getAttribute('code');
					if (status == 'IN_PROGRESS' && equipCode != record.get('EQUIPCODE')) {
						return 'x-grid-record-grey';
					} else {
						return statusColorMap[status];
					}
				}
			},
			initComponent : function() {
				var me = this, columns = [], outMatDesc = "材料";
				columns = [{
					text : '合同号',
					dataIndex : 'CONTRACTNO',
					flex : 2.5,
					minWidth : 125,
					renderer : function(value, metaData, record) {
						var reg = /[a-zA-Z]/g;
						value = (value.replace(reg, "").length > 5
								? value.replace(reg, "").substring(value.replace(reg, "").length - 5)
								: value.replace(reg, ""))
								+ '[' + record.get('OPERATOR') + ']';
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						return value;
					}
				}, {
					text : '颜色',
					dataIndex : 'COLOR',
					minWidth : 100,
					flex : 2,
					renderer : function(value, metaData, record, row, column) {
						return value;
					}
				}, {
					text : '长度',
					dataIndex : 'TASKLENGTH',
					flex : 2.5,
					minWidth : 125,
					renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
						if (record.get('SPLITLENGTHROLEWITHYULIANG') && record.get('SPLITLENGTHROLEWITHYULIANG') != '') {
							if (value != record.get('SPLITLENGTHROLEWITHYULIANG')) {
								value += '=' + record.get('SPLITLENGTHROLEWITHYULIANG');
							}
						}
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						return value;
					}
				}, {
					text : "材料",
					dataIndex : 'OUTATTRDESC',
					flex : 4,
					minWidth : 200,
					renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						var json = Ext.decode(value);
						value = typeof(json.material) == "undefined" ? '' : json.material;
						value = value.replace(/\<br\/>/g, ';');
						return value;
					}
				}, {
					text : '线芯结构',
					dataIndex : 'OUTATTRDESC',
					flex : 1.3,
					minWidth : 65,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						var json = Ext.decode(value);
						value = typeof(json.wiresStructure) == "undefined" ? '' : json.wiresStructure;
						return value;
					}
				}, {
					text : '标称厚度',
					dataIndex : 'OUTATTRDESC',
					flex : 1.3,
					minWidth : 65,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						var json = Ext.decode(value);
						value = typeof(json.standardPly) == "undefined" ? '' : json.standardPly;
						return value;
					}
				}, {
					text : '最大值',
					dataIndex : 'OUTATTRDESC',
					flex : 1.3,
					minWidth : 65,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						var json = Ext.decode(value);
						value = typeof(json.standardMaxPly) == "undefined" ? '' : json.standardMaxPly;
						return value;
					}
				}, {
					text : '最小值',
					dataIndex : 'OUTATTRDESC',
					flex : 1.3,
					minWidth : 65,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						var json = Ext.decode(value);
						value = typeof(json.standardMinPly) == "undefined" ? '' : json.standardMinPly;
						return value;
					}
				}, {
					text : '外径',
					dataIndex : 'OUTATTRDESC',
					flex : 1.3,
					minWidth : 65,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						var json = Ext.decode(value);
						value = typeof(json.outsideValue) == "undefined" ? '' : json.outsideValue;
						return value;
					}
				}];

				me.columns = columns;
				me.callParent(arguments);

			}
		});