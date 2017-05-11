Ext.define('bsmes.controller.MaterialCheckController', {
	extend : 'Oit.app.controller.GridController',
	materialCheckPanel : 'materialCheckPanel',
	importMaterialCheckWindow : 'importMaterialCheckWindow',
	materialCheckGrid : 'materialCheckGrid',
	view : 'materialCheckPanel',
	views : [ 'MaterialCheckPanel','MaterialCheckGrid' ,'ImportMaterialCheckWindow'],
	stores : [ 'MaterialCheckReportStore' ],
	constructor : function() {
		var me = this;

		// 初始化refs
		me.refs = me.refs || [];
		
		me.refs.push({
			ref : 'materialCheckPanel',
			selector : '#materialCheckPanel'
		});
		
		me.refs.push({
			ref : 'materialCheckGrid',
			selector : me.materialCheckGrid,
			autoCreate : true,
			xtype : me.materialCheckGrid
		});
		me.refs.push({
			ref : 'importMaterialCheckWindow',
			selector : me.importMaterialCheckWindow,
			autoCreate : true,
			xtype : me.importMaterialCheckWindow
		});
		me.callParent(arguments);
	},
	init : function() {
		var me = this;
		me.control(me.materialCheckGrid + ' button[itemId=searchMaterialCheckReport]', {
			click : me.searchMaterialCheckReport
		});
		me.control(me.materialCheckGrid + ' button[itemId=importMaterialCheck]', {
			click : me.importMaterialCheck
		});
		me.control(me.importMaterialCheckWindow + ' button[itemId=ok]', {
			click : me.importMaterialCheckSub
		});
		me.callParent(arguments);
	},
	
	importMaterialCheck : function() {
		var me = this;
		var win = me.getImportMaterialCheckWindow();
		var grid = me.getMaterialCheckGrid();
		console.log(grid.columns);
		win.show();
	},
	
	importMaterialCheckSub : function(){
		var me = this;
		var grid = me.getMaterialCheckGrid();
		var columns = [];
		var win = me.getImportMaterialCheckWindow();
		var form = win.down('form');
		console.log(form.form.findField('importFile').lastValue);
		var fileName = form.form.findField('importFile').lastValue;
		var yearMonth = fileName.substr(0,7);
		if(yearMonth.indexOf('月')>0){
			yearMonth = yearMonth.replace("月",'');
			var month = yearMonth.charAt(5);
			yearMonth = yearMonth.replace(month,'0'+month)
		}
		console.log(yearMonth);
		if (form.isValid()) {
			form.submit({
						waitMsg : '正在导入库存明细,请耐心等待...',
						success : function(form, action) {
							var result = Ext.decode(action.response.responseText);
							Ext.Msg.alert(Oit.msg.PROMPT, result.message);
							for(var i in grid.columns){
								columns.push(Ext.create('Ext.grid.column.Column',{
									flex : 1,
									minWidth : 50,
									sortable : false,
									menuDisabled : true,
									text : grid.columns[i].text,
									triStateSort : false,
									dataIndex : grid.columns[i].dataIndex
								}))
							}
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
										columns.push(Ext.create('Ext.grid.column.Column',{
											flex : 1,
											minWidth : 50,
											sortable : false,
											menuDisabled : true,
											text : checkDay,
											triStateSort : false,
											dataIndex : seq+''
										}));
										j++;
									}
									grid.reconfigure(grid.getStore(), columns);
									grid.down('form').form.findField('yearMonth').setValue(yearMonth);
									grid.getStore().load();	
								}
							});
							win.close();
						},
						failure : function(form, action) {
							var result = Ext.decode(action.response.responseText);
							Ext.Msg.alert(Oit.msg.WARN, result.message);
						}
					});
		}
	},
	
	searchMaterialCheckReport : function(){
		var me = this;
		var form = Ext.ComponentQuery.query("materialCheckGrid hform")[0];
		var params = form.form.getValues();
		var grid = me.getMaterialCheckGrid();
		var yearMonth = params.yearMonth;
		var columns = [];
		for(var i in grid.columns){
			columns.push(Ext.create('Ext.grid.column.Column',{
				flex : 2,
				minWidth : grid.columns[i].minWidth,
				sortable : false,
				menuDisabled : true,
				text : grid.columns[i].text,
				triStateSort : false,
				dataIndex : grid.columns[i].dataIndex
			}))
		}
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
					columns.push(Ext.create('Ext.grid.column.Column',{
						flex : 1,
						minWidth : 65,
						sortable : false,
						menuDisabled : true,
						text : checkDay,
						triStateSort : false,
						dataIndex : seq+''
					}));
					j++;
				}
				grid.reconfigure(grid.getStore(), columns);
				grid.getStore().load();
			}
		});
	}
});

