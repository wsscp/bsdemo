/**
 * Created by JinHy on 2014/6/11 0011.
 */
Ext.define('bsmes.view.MaterialGrid', {
	extend : 'Ext.grid.Panel',
	itemId : 'materialGrid',
	alias : 'widget.materialGrid',
	woNo : '',
	processCode : Ext.fly('processInfo').getAttribute('code'),
	store : 'ProcessInStore',
	forceFit : true,
	columnLines : true,
	title : Oit.msg.wip.terminal.materialTitle,
	dataArray : null, // 加载数据对象
	viewConfig : {
		stripeRows : false,
		getRowClass : function(record, rowIndex, rowParams, store) {
			var resultColor = '';
			if (record.get("hasPutIn")) {
				resultColor = 'x-grid-record-green';
			}
			return resultColor;
		}
	},
	listeners : {
		itemclick : function(me, record, index) {
			var textfeed = Ext.ComponentQuery.query("#feedText")[0];
			textfeed.setValue(record.get("matCode"));
		}
	},
	sortableColumns : false,
	plugins : [{
				ptype : 'rowexpander',
				rowBodyTpl : ['<div id="{id}">', '</div>']
			}],

	initComponent : function() {
		var disableSee = true;
		if(processCode == 'Braiding'){
			disableSee = false;
		}
		var me = this;
		var tbar = [{
					xtype : "form",
					id : 'feedForm',
					items : [{
								xtype : 'textfield',
								itemId : 'feedText',
								name : 'batchNo',
								enableKeyEvents : true,
								height : 30,
								width : 300,
								listeners : {
									specialKey : function(field, e) {
										if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
											Ext.ComponentQuery.query('#feed')[0].fireEvent('click');
										}
									}
								}
							}, {
								xtype : 'hiddenfield',
								name : 'orderTaskId',
								itemId : 'feedOrderTaskId',
								value : Ext.fly('currentOrderTask').getAttribute('orderTaskId')
							}, {
								xtype : 'hiddenfield',
								name : 'color',
								itemId : 'feedColor',
								value : Ext.fly('currentOrderTask').getAttribute('color')
							}, {
								xtype : 'hiddenfield',
								itemId : 'isInProgress',
								value : Ext.fly('currentOrderTask').getAttribute('orderTaskId') == null ? false : true
							}]
				}, {
					itemId : 'feed',
					text : Oit.msg.wip.terminal.feed,
					xtype : 'button',
					isProIntercept : Ext.fly('single').getAttribute('isProIntercept')
				}, {
					itemId : 'see',
					text : '查看',
					xtype : 'button',
					hidden : disableSee
				}];
		var columns = [{
					text : Oit.msg.wip.terminal.name + '/' + Oit.msg.wip.terminal.code,
					dataIndex : 'matName',
					flex : 3,
					minWidth : 150,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						value = value + '<br/>' + record.get('matCode');
						return value;
					}
				}, {
					text : '物料图片',
					xtype : 'actioncolumn',
					flex : 1.5,
					minWidth : 75,
					dataIndex : 'id',
					renderer : function(value, metaData, record) {
						return '<img src="showFile?refId='
								+ value
								+ '" onerror="this.src=\'../../../bsstatic/matimg/noImage.jpg\'" width = "50%" height = "50%" onclick="showMaxPicture(\''
								+ value + '\')">';
					}
				}, {
					text : Oit.msg.wip.terminal.planAmount,
					dataIndex : 'taskLength',
					flex : 2,
					minWidth : 100,
					sortableColumns : false,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						value = (value * record.get('quantity') / 1000).toFixed(3) + ' ' + record.get('unit');
						metaData.tdAttr = 'data-qtip="' + value + '"';
						return value;
					}
				}, {
					text : '物料信息描述',
					dataIndex : 'inAttrDesc',
					flex : 3,
					minWidth : 150,
					sortableColumns : false,
					renderer : function(value, metaData, record) {
						var json = Ext.decode(value);
						var matDesc = '';
						if (record.get('color') != '') { // 宽度
							matDesc += '<font color="red">颜色:' + me.getColor(record.get('color'), record.get('matName')) + '</font>;'
						}
						if (json.kuandu) { // 宽度
							matDesc += '宽度:' + json.kuandu + ';'
						}
						if (json.houdu) { // 厚度
							matDesc += '厚度:' + json.houdu + ';'
						}
						if (json.caizhi) { // 材质
							matDesc += '材质:' + json.caizhi + ';'
						}
						if (json.chicun) { // 尺寸
							matDesc += '尺寸:' + json.chicun + ';'
						}
						if (json.guige) { // 规格
							matDesc += '规格:' + json.guige + ';'
						}
						if (json.dansizhijing) { // 单丝直径
							matDesc += '单丝直径:' + json.dansizhijing;
						}
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						return matDesc;
					}
				}, {
					dataIndex : 'inAttrDesc',
					text : '盘具要求/库位:库存',
					flex : 4.6,
					minWidth : 230,
					renderer : function(value, metaData, record) {
						metaData.style = "white-space:normal;word-break:break-all;padding:5px 5px 5px 5px;";
						var json = Ext.decode(value);
						value = typeof(json.disk) == "undefined" ? '' : json.disk;
						var wireCoil = typeof(json.wireCoil) == "undefined" ? '' : json.wireCoil;
						var disk = record.get('remark') == '' ? (value == '' ? [] : value.split(",")) : record.get('remark').split(",");
						var res = (wireCoil == '' ? '' : ('<font color="red">盘具：</font>' + wireCoil)) + '<br/>';
						if (disk.length > 0) {
							res = res + '<font color="red">库存：</font>';
						}
						for (var i = 0; i < disk.length; i++) {
							if (disk.length > 1) {
								res = res + (i + 1) + '、' + disk[i].split(';')[0] + "<br/>";
							} else {
								res = res + disk[i].split(';')[0];
							}
						}
						return res;
					}
				}];
		me.tbar = tbar;
		me.columns = columns;
		this.callParent(arguments);
		me.view.on('expandBody', function(rowNode, record, expandRow, eOpts) {
					var renderId = record.get('id');
					var url = "../terminal/matBatchs";
					var subGrid = Ext.create("bsmes.view.MatBatchGrid", {
								renderTo : renderId,
								store : Ext.create('bsmes.store.MatBatchGridStore')
							});
					var subStore = subGrid.getStore();
					subStore.getProxy().url = url;
					subStore.load({
								params : {
									matCode : record.get('matCode'),
									workOrderNo : me.woNo
								}
							});
					subGrid.getEl().swallowEvent(['mousedown', 'mouseup', 'click', 'contextmenu', 'dblclick', 'mousemove']);
				});
		me.view.on('collapsebody', function(rowNode, record, expandRow, eOpts) {
					var parent = document.getElementById(record.get('id'));
					var child = parent.firstChild;
					while (child) {
						child.parentNode.removeChild(child);
						child = child.nextSibling;
					}
				});
		if (me.dataArray) {
			me.getStore().loadData(me.dataArray) // 加载数据
		}
	},

	/**
	 * 获取色标
	 */
	getColor : function(color, matName) {
		var me = this;
		var pvcColor = {
			'红' : 3027,
			'绿' : 6017,
			'黄' : 1016,
			'兰' : 5015,
			'灰' : 7040,
			'黑' : 9005,
			'棕' : 8024,
			'白' : 9003
		};
		var jlColor = {
			'红' : 3001,
			'绿' : 6037,
			'黄' : 1003,
			'兰' : 5019,
			'灰' : 7046,
			'黑' : 9005,
			'棕' : 8024,
			'粉' : 4003,
			'白' : 9016,
			'橙' : 2004
		};
		var processCode = me.processCode;
		var isMaterial = matName.contains('绝缘料');
		var isBanchengpin = matName.contains('半成品');
		if (processCode == 'Respool' || processCode == 'Extrusion-Single') {
			if (isMaterial) {
				if (color.contains('蓝') || color.contains('兰')) {
					return color + '(色标:' + pvcColor['兰'] + ')';
				} else if (color.contains('红')) {
					return color + '(色标:' + pvcColor['红'] + ')';
				} else if (color.contains('绿')) {
					return color + '(色标:' + pvcColor['绿'] + ')';
				} else if (color.contains('黄')) {
					return color + '(色标:' + pvcColor['黄'] + ')';
				} else if (color.contains('灰')) {
					return color + '(色标:' + pvcColor['灰'] + ')';
				} else if (color.contains('黑')) {
					return color + '(色标:' + pvcColor['黑'] + ')';
				} else if (color.contains('棕')) {
					return color + '(色标:' + pvcColor['棕'] + ')';
				} else if (color.contains('白')) {
					return color + '(色标:' + pvcColor['白'] + ')';
				} else {
					return color;
				}
			}
			if (isBanchengpin) {
				if (color.contains('蓝') || color.contains('兰')) {
					return color + '(色标:' + jlColor['兰'] + ')';
				} else if (color.contains('红')) {
					return color + '(色标:' + jlColor['红'] + ')';
				} else if (color.contains('绿')) {
					return color + '(色标:' + jlColor['绿'] + ')';
				} else if (color.contains('黄')) {
					return color + '(色标:' + jlColor['黄'] + ')';
				} else if (color.contains('灰')) {
					return color + '(色标:' + jlColor['灰'] + ')';
				} else if (color.contains('黑')) {
					return color + '(色标:' + jlColor['黑'] + ')';
				} else if (color.contains('棕')) {
					return color + '(色标:' + jlColor['棕'] + ')';
				} else if (color.contains('白')) {
					return color + '(色标:' + jlColor['白'] + ')';
				} else if (color.contains('粉')) {
					return color + '(色标:' + jlColor['粉'] + ')';
				} else if (color.contains('橙')) {
					return color + '(色标:' + jlColor['橙'] + ')';
				} else {
					return color;
				}
			}
		} else {
			return color;
		}

	}
});

/**
 * 显示图片
 */
function showMaxPicture(refId) {
	var width = document.body.scrollWidth / 2 - 100, height = width * 0.618;
	var win = new Ext.Window({
				title : '查看物料图片',
				//autoScroll : true,
				modal : true,
				plain : true,
				//layout : 'vbox',
				items : [{
					xtype : 'panel',
					width : width,
					height : height,
					overflowY : 'auto',
					html : '<img src="showFile?refId=' + refId + '" onerror="this.src=\'../../../bsstatic/matimg/noImage.jpg\'" width = "'
							+ width + 'px" height = "' + height + 'px">'
				}],
				buttons : ['->', {
							text : '关闭',
							handler : function() {
								this.up('window').close();
							}
						}]
			});
	win.show();
	return;
	//	Ext.Ajax.request({
	//				url : 'showFileList?refId=' + refId,
	//				success : function(response) {
	//					var fileArray = Ext.decode(response.responseText); // 请求响应转换成json格式
	//					var items = [], width = 600, height = 400;
	//					Ext.Array.each(fileArray, function(file, i) {
	//								var img = new Image();
	//								img.src = 'showFile?id=' + file.id
	//								items.push({
	//											xtype : 'box',
	//											width : img.width * 0.35,
	//											height : img.height * 0.35,
	//											autoEl : {
	//												tag : 'img',
	//												src : 'showFile?id=' + file.id
	//											}
	//										})
	//								if (fileArray.length == 1) {
	//									width = img.width * 0.35;
	//									height = img.height * 0.35 + 88;
	//								}
	//							})
	//					var win = new Ext.Window({
	//								title : '查看物料图片',
	//								width : width,
	//								height : height,
	//								//autoScroll : true,
	//								overflowY : 'auto',
	//								//layout : 'vbox',
	//								items : items,
	//								buttons : ['->', {
	//											text : '关闭',
	//											handler : function() {
	//												this.up('window').close();
	//											}
	//										}]
	//							});
	//					win.show();
	//				}
	//			});
};