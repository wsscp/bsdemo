var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
	clicksToMoveEditor: 1,
    autoCancel: false,
    listeners:{
    	edit : function(editor, context, eOpts){
    		var id = context.record.get('id');
    		var quantity = context.record.get('faLiaoQuantity');
    		var workOrderNo = context.record.get('workOrderNo');
    		var status = context.record.get('status');
    		console.log(context.record);
    		if(status == 'MAT_DOWN'){
    			Ext.Ajax.request({
    				async: true,
    				url:'materialMng/save/'+quantity+'/'+id + '/' + status + '/' + workOrderNo,
    				method:'GET',
    				success:function(response){
    					Ext.ComponentQuery.query('#materialMngGrid')[0].getStore().reload();
    				}
    			});
    		}
    		if(status == 'MAT_BORROW'){
    			Ext.Ajax.request({
    				async: true,
    				url:'materialMng/save/'+quantity+'/'+id + '/'+status + '/' + workOrderNo,
    				method:'GET',
    				success:function(response){
    					Ext.ComponentQuery.query('#materialMngGrid')[0].getStore().reload();
    				}
    			});
    		}
			
    	},
    	beforeedit : function(editor, context, eOpts){
    		if(context.record.get('status')=='MAT_DOWN'){
    			context.record.set('faLiaoQuantity',context.record.get('yaoLiaoQuantity'));
    		}else if(context.record.get('status')=='MAT_BORROW'){
    			context.record.set('faLiaoQuantity',context.record.get('buLiaoQuantity'));
    		}else{
    			return false;
    		}
    	},
    	canceledit : function(editor, context, eOpts){
    		context.record.set('faLiaoQuantity','');
    	}
    }
});

function rowColorCha(record,metaData){
	if(record.get('status') == 'MAT_DOWN'){
        metaData.style = "background-color: #f93;white-space:normal !important;";
    }else if(record.get('status') == 'MAT_GETED'){
        metaData.style = "background-color: #3c3;white-space:normal !important;";
    }else if(record.get('status') == 'MAT_BORROW'){
    	metaData.style = "background-color: #00B2EE;white-space:normal !important;";
    }
}

Ext.define("bsmes.view.MaterialMngList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.materialMngList',
	store : 'MaterialMngStore',
	itemId : 'materialMngGrid',
	defaultEditingPlugin : false,
	plugins: [rowEditing],
	listeners : {
		'afterrender' : function(e,eOpts){
			var grid = Ext.ComponentQuery.query('#materialMngGrid')[0];
			grid.down('#4').setVisible(false);
			grid.down('#7').setVisible(false);
			grid.down('#8').setVisible(false);
			grid.down('#9').setVisible(false);
		}
	},
	initComponent:function(){
		var me = this;
		var columns = [{
			dataIndex:'id',
			hidden : true
		},{
			text : '生产单号',
			flex : 1.6,
			minWidth : 80,
			dataIndex : 'workOrderNo',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value.substring(7);
	        }
		}, {
			text : '机台',
			flex : 2,
			minWidth : 100,
			dataIndex : 'equipName',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		}, {
			text : '要料人',
			flex : 1,
			minWidth : 50,
			dataIndex : 'userName',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		}, {
			text : '物料代码',
			flex : 2,
			minWidth : 100,
			dataIndex : 'matCode',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		}, {
			text : '物料名称',
			flex : 4,
			minWidth : 200,
			dataIndex : 'matName',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
				if(record.get('inAttrDesc') != ''){
					var json = Ext.decode(record.get('inAttrDesc'));
				}else{
					var json = {};
				}
				var matDesc = '';
				var a = false;
				if (json.kuandu) { // 宽度
					matDesc += '宽度:' + json.kuandu + '<br/>';
					a = true;
				}
				if (json.color) { // 颜色
					matDesc += '<font color="red">颜色:' + json.color + '</font><br/>'
					a = true;
				}
				if (json.houdu) { // 厚度
					matDesc += '厚度:' + json.houdu + '<br/>';
					a = true;
				}
				if (json.caizhi) { // 材质
					matDesc += '材质:' + json.caizhi + '<br/>';
					a = true;
				}
				if (json.chicun) { // 尺寸
					matDesc += '尺寸:' + json.chicun + '<br/>';
					a = true;
				}
				if (json.guige) { // 规格
					matDesc += '规格:' + json.guige + '<br/>';
					a = true;
				}
				if (json.dansizhijing) { // 单丝直径
					matDesc += '单丝直径:' + json.dansizhijing + '<br/>';
					a = true;
				}
				if(a){
					value = value + '(' + matDesc + ')' + '(KG)';
				}else{
					value = value+ '(KG)';
				}
	            return value;
	        }
		},{
			text : '计划用量(KG)',
			flex : 1.6,
			minWidth : 80,
			id : '1',
			dataIndex : 'planAmount',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		},/*{
			text : '机台余料(KG)',
			flex : 1.6,
			minWidth : 80,
			id : '2',
			dataIndex : 'jiTaiQuantity',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		}, */{
			text : '要料量(KG)',
			flex : 1.6,
			minWidth : 80,
			id : '3',
			dataIndex : 'yaoLiaoQuantity',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		},{
			text : '补料量(KG)',
			flex : 1.6,
			minWidth : 80,
			id : '4',
			dataIndex : 'buLiaoQuantity',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		},{
			text : '发料量(KG)',
			flex : 1.6,
			minWidth : 80,
			dataIndex : 'faLiaoQuantity',
			id : 'quantityId',
			editor: {
	            allowBlank: false
	        },
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		},{
			text : '需求时间',
			dataIndex : 'createTime',
			flex : 3,
			minWidth : 150,
			id : '6',
			xtype : 'datecolumn', 
			format : 'Y-m-d H:i',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
				var gedt = Ext.util.Format.date(value, 'Y-m-d H:i');
	            return gedt;
	        }
		},{
			text : '要料发料量(KG)',
			minWidth : 110,
			flex : 2.2,
			id : '7',
			dataIndex : 'faYaoLiaoQuantity',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		},{
			text : '补料发料量(KG)',
			minWidth : 200,
			flex : 4,
			id : '8',
			dataIndex : 'faBuLiaoQuantity',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
	            return value;
	        }
		},{
			text : '总发料量(KG)',
			flex : 2,
			minWidth : 100,
			id : '9',
			dataIndex : 'totalFaLiaoQuantity',
			renderer :function(value,metaData, record, rowIndex, columnIndex, store, view){
				rowColorCha(record,metaData);
				var y = record.get('faYaoLiaoQuantity');
				var b = record.get('faBuLiaoQuantity');
				var str = b.split(',');
				var totalFaLiaoQuantity = Number(y);
				if(str.length>0){
					for(var i=0;i<str.length;i++){
						totalFaLiaoQuantity += Number(str[i]);
					}
				}
	            return totalFaLiaoQuantity;
	        }
		}];
		var dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			items : [{
				xtype : 'hform',
				items: [{
			        fieldLabel: '生产单号',
			        name: 'workOrderNo'
			    },{
			        fieldLabel: '物料代码',
			        name: 'matCode'
			    },{
					fieldLabel : "状态",
					xtype : 'radiogroup',
					width: 250,
					id : 'status',
					labelWidth:40,
					vertical: true,
					items : [{
						boxLabel : '已要料',
						name : 'status',
						inputValue : 'MAT_DOWN',
						checked : true
					}, {
						boxLabel : '已发料',
						name : 'status',
						inputValue : 'MAT_GETED',
						checked : false
					}, {
						boxLabel : '已补料',
						name : 'status',
						inputValue : 'MAT_BORROW',
						checked : false
					}]
				}]
			}, {
				itemId : 'search',
				handler: function() {
					var grid = this.up('grid');
					console.log(grid);
					var form = grid.down('form').getForm();
					var s = form.findField('status').getValue();
					if(s.status == 'MAT_BORROW'){
						grid.down('#1').setVisible(false);
//						grid.down('#2').setVisible(false);
						grid.down('#3').setVisible(false);
						grid.down('#4').setVisible(true);
						grid.down('#quantityId').setVisible(true);
						grid.down('#6').setVisible(true);
						grid.down('#7').setVisible(false);
						grid.down('#8').setVisible(false);
						grid.down('#9').setVisible(false);
					}else if(s.status == 'MAT_GETED'){
						grid.down('#1').setVisible(false);
//						grid.down('#2').setVisible(false);
						grid.down('#3').setVisible(false);
						grid.down('#4').setVisible(false);
						grid.down('#quantityId').setVisible(false);
						grid.down('#6').setVisible(false);
						grid.down('#7').setVisible(true);
						grid.down('#8').setVisible(true);
						grid.down('#9').setVisible(true);
					}else{
						grid.down('#1').setVisible(true);
//						grid.down('#2').setVisible(true);
						grid.down('#3').setVisible(true);
						grid.down('#4').setVisible(false);
						grid.down('#quantityId').setVisible(true);
						grid.down('#6').setVisible(true);
						grid.down('#7').setVisible(false);
						grid.down('#8').setVisible(false);
						grid.down('#9').setVisible(false);
					}
					var params = form.getValues();
					grid.getStore().load({params : params});
				}
			}]
		}];
		me.dockedItems = dockedItems;
		me.columns = columns;
		me.callParent(arguments);
	}
	
});



