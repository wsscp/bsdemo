var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToMoveEditor: 1,
    autoCancel: false,
    listeners:{
    	edit : function(editor, context, eOpts){
    		var first = context.record.get('userCode').indexOf("[") + 1;
    		var last = context.record.get('userCode').indexOf("]");
    		var length = last - first;
    		var userCode = context.record.get('userCode').substr(first, length);
        	var statDate =Ext.util.Format.date(context.record.get('statDate'),"Y-m-d");
        	var feiTong =context.record.get('feiTong');
        	var feiXian =context.record.get('feiXian');
        	var jiaoLian =context.record.get('jiaoLian');
        	var zaLiao =context.record.get('zaLiao');
        	var fsLiao =context.record.get('fsLiao');
        	var wuLu =context.record.get('wuLu');
        	var id = context.record.get('id');
        	var theorySlopLine = context.record.get('theorySlopLine');
        	var jiechaoSlopLine = context.record.get('jiechaoSlopLine');
        	var slopLineDeductions = context.record.get('slopLineDeductions');
        	var realityMaterialPro = context.record.get('realityMaterialPro');
        	var theoryWaste = context.record.get('theoryWaste');
        	var realityWaste = context.record.get('realityWaste');
        	var wasteDeductions = context.record.get('wasteDeductions');
        	var rewardPunish = context.record.get('rewardPunish');
        	if(id == ''||id == null){
        		Ext.Ajax.request({
    				url : 'storeManage/insertNewData',
    				params : {
    					userCode : userCode,
    	            	statDate : statDate,
    	            	feiTong : feiTong,
    	            	feiXian : feiXian,
    	            	jiaoLian : jiaoLian,
    	            	zaLiao : zaLiao,
    	            	fsLiao : fsLiao,
    	            	wuLu : wuLu,
    	            	theorySlopLine : theorySlopLine,
    	            	jiechaoSlopLine : jiechaoSlopLine,
    	            	slopLineDeductions : slopLineDeductions,
    	            	realityMaterialPro : realityMaterialPro,
    	            	theoryWaste : theoryWaste,
    	            	realityWaste : realityWaste,
    	            	wasteDeductions : wasteDeductions,
    	            	rewardPunish : rewardPunish
    				},
    				success : function(response) {
    					Ext.ComponentQuery.query('storeManageView')[0].getStore().reload();
    				}
    			});
        	}else{
        		Ext.Ajax.request({
    				url : 'storeManage/updateData',
    				params : {
    					userCode : userCode,
    	            	statDate : statDate,
    	            	feiTong : feiTong,
    	            	feiXian : feiXian,
    	            	jiaoLian : jiaoLian,
    	            	zaLiao : zaLiao,
    	            	fsLiao : fsLiao,
    	            	wuLu : wuLu,
    	            	id : id,
    	            	theorySlopLine : theorySlopLine,
    	            	jiechaoSlopLine : jiechaoSlopLine,
    	            	slopLineDeductions : slopLineDeductions,
    	            	realityMaterialPro : realityMaterialPro,
    	            	theoryWaste : theoryWaste,
    	            	realityWaste : realityWaste,
    	            	wasteDeductions : wasteDeductions,
    	            	rewardPunish : rewardPunish
    				},
    				success : function(response) {
    					Ext.ComponentQuery.query('storeManageView')[0].getStore().reload();
    				}
    			});
        	}
    		
    	},
    	
    	cancelEdit : function(){
    		Ext.ComponentQuery.query('storeManageView')[0].getStore().reload();
    	},
    	
    	beforeedit : function( editor, context, eOpts){
    		var id = context.record.get('id');
    		if(id == ''||id == null){
    			context.record.set('statDate',new Date());
    		}
    	}
    }
});

Ext.define("bsmes.view.StoreManageView",{
	extend: 'Oit.app.view.Grid',
	alias: 'widget.storeManageView',
	id : 'storeManageView',
	store : 'StoreManageStore',
	defaultEditingPlugin : false,
	selType : 'checkboxmodel',
	selModel : {
		mode : "MULTI" // "SINGLE"/"SIMPLE"/"MULTI"
	},
	columns : [{
		dataIndex : 'statDate',
		text : '创建时间',
		flex : 1.5,
		editor: {
			xtype : 'datefield',
			format : 'Y-m-d'
        },
        renderer : Ext.util.Format.dateRenderer('Y-m-d')
	},{
		dataIndex : 'userCode',
		text : '姓名',
		flex : 2,
		editor: {
			xtype : 'combobox',
            allowBlank: true,
            displayField: 'name',
			valueField: 'name',
			multiSelect:true,
			width:250,
			labelWidth:60,
			minChars: 1 ,
			queryMode : 'local',
			typeAhead:true,
			triggerAction:'all',
			store:new Ext.data.Store({
			  autoLoad:true,
			  fields:[{
				  name :'name',
				  convert : function(value, record) {
					  return value + '[' + record.get('userCode') + ']';
				  }
					  },'userCode'],
			  		proxy : {										
			  			type : 'rest',
						url : 'storeManage/getUserInfo'
				},
				sorters : [{
					property : 'userCode',
					direction : 'ASC'
				}]
			}),
			listeners:{
				beforequery:function(e){
					var combo=e.combo;
					combo.collapse();
					if(!e.forceAll){
						var value = e.query;
						if(value!=null&&value!=''){
							combo.store.filterBy(function(record,id){																											
								var text = record.get('name');
								return (text.indexOf(value)!=-1);
							});
						}else{
							combo.store.clearFilter();
						}
						combo.onLoad();
						combo.expand();
						return false;
					}
				}
			}
        }
	},{
		text : '废品名称及重量',
		dataIndex : 'gd',
		flex : 2.5,
		columns : [{
			dataIndex : 'feiTong',
			text : '废铜',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'feiXian',
			text : '废线',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'jiaoLian',
			text : '交联',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'zaLiao',
			text : '杂料',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'fsLiao',
			text : '粉碎料',
			editor: {
	            allowBlank: true
	        }
		},{
			dataIndex : 'wuLu',
			text : '无卤',
			editor: {
	            allowBlank: true
	        }
		}]
	},{
		text : '理论</br>废线',
		dataIndex : 'theorySlopLine',
		flex : 1,
		editor: {
            allowBlank: true
        }
	},{
		text : '节超</br>废线',
		dataIndex : 'jiechaoSlopLine',
		flex : 1,
		editor: {
            allowBlank: true
        }
	},{
		text : '废线</br>扣款',
		dataIndex : 'slopLineDeductions',
		flex : 1,
		editor: {
            allowBlank: true
        }
	},{
		text : '实际用</br>料/产量',
		dataIndex : 'realityMaterialPro',
		flex : 1,
		editor: {
            allowBlank: true
        }
	},{
		text : '理论</br>废料',
		dataIndex : 'theoryWaste',
		flex : 1,
		editor: {
            allowBlank: true
        }
	},{
		text : '实际</br>废料',
		dataIndex : 'realityWaste',
		flex : 1,
		editor: {
            allowBlank: true
        }
	},{
		text : '废料</br>扣款',
		dataIndex : 'wasteDeductions',
		flex : 1,
		editor: {
            allowBlank: true
        }
	},{
		text : '奖罚</br>总金',
		dataIndex : 'rewardPunish',
		flex : 1,
		editor: {
            allowBlank: true
        }
	}],
	plugins: [rowEditing],
	tbar : [{
		fieldLabel : '姓名',
		xtype : 'combobox',
		itemId :'nameCom',
        allowBlank: true,
        displayField: 'name',
		valueField: 'userCode',
		multiSelect:true,
		width:250,
		labelWidth:60,
		minChars: 1 ,
		queryMode : 'local',
		typeAhead:true,
		triggerAction:'all',
		store:new Ext.data.Store({
		  autoLoad:true,
		  fields:[{
			  name :'name',
			  convert : function(value, record) {
				  return value + '[' + record.get('userCode') + ']';
			  }
				  },'userCode'],
		  		proxy : {										
		  			type : 'rest',
					url : 'storeManage/getUserInfo'
			},
			sorters : [{
				property : 'userCode',
				direction : 'ASC'
			}]
		}),
		listeners:{
			beforequery:function(e){
				var combo=e.combo;
				combo.collapse();
				if(!e.forceAll){
					var value = e.query;
					if(value!=null&&value!=''){
						combo.store.filterBy(function(record,id){																											
							var text = record.get('name');
							return (text.indexOf(value)!=-1);
						});
					}else{
						combo.store.clearFilter();
					}
					combo.onLoad();
					combo.expand();
					return false;
				}
			}
		}
    }, {
		fieldLabel : '创建日期',
		xtype : 'datefield',
		itemId : 'datetext',
		name : 'createDate',
		format : 'Y-m-d',
		labelWidth:60,
		width:250, 
		value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.MONTH,0),"Y-m-d")
	}, {
		xtype : 'button',
		text : '查找',
		handler : function(){
			var nowDate = Ext.util.Format.date(Ext.ComponentQuery.query('storeManageView #datetext')[0].lastValue,"Y-m-d");
			var nowUserCode = Ext.ComponentQuery.query('storeManageView #nameCom')[0].lastValue;
			Ext.ComponentQuery.query('storeManageView')[0].getStore().load({
				params:{
					'nowDate':nowDate,
					'nowUserCode':nowUserCode
				}
			});
		}
	}, {
		itemId : 'addNew',
		xtype : 'button',
		text : '添加',
		handler : function(){
            var r = Ext.create('bsmes.model.StoreManage',{
            	userCode : '',
            	statDate : '',
            	feiTong : '0',
            	feiXian : '0',
            	jiaoLian : '0',
            	zaLiao : '0',
            	fsLiao : '0',
            	wuLu : '0',
            	theorySlopLine : '0',
            	jiechaoSlopLine : '0',
            	slopLineDeductions : '0',
            	realityMaterialPro : '0',
            	theoryWaste : '0',
            	realityWaste : '0',
            	wasteDeductions : '0',
            	rewardPunish : '0'
            });           
            Ext.getCmp('storeManageView').getStore().insert(0, r);
            rowEditing.startEdit(0, 0);
		}
	},{
		itemId : 'removeSelect',
		xtype : 'button',
		text : '删除',
		handler : function(){
			var thisstoreManageView = Ext.ComponentQuery.query('storeManageView')[0];
			var selection = thisstoreManageView.getSelectionModel().getSelection();
			if(selection&&selection!=''){
				Ext.MessageBox.confirm('警告','确认删除？',function(btn){
		         	   if(btn == 'yes'){
		         		  var ids = new Array(); 
		  				for(var i = 0;i<selection.length;i++){
		  					ids[i]=selection[i].get('id');
		  				}
		  				Ext.Ajax.request({
		      				url : 'storeManage/deleteData',
		      				params : {
		      					ids :ids
		      				},
		      				success : function(response) {
		      					Ext.ComponentQuery.query('storeManageView')[0].getStore().reload();
		      				}
		      			});
		         	   }
		            });
			}else{
				Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条数据！');
			}
		}
	},{
		itemId : 'importScrapWin',
		xtype : 'button',
		text : '导入废料'
	}]
});