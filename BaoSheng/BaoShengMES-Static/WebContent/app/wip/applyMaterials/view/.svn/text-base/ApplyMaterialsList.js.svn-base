var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
	clicksToMoveEditor: 1,
    autoCancel: false,
    listeners : {
    	edit : function(editor, context, eOpts){
    		Ext.Ajax.request({
    		    url: 'applyMaterials/update',
    		    params: {
    		    	id: context.record.get('id'),
    		    	issueQuntity : context.record.get('issueQuntity')
    		    },
    		    success: function(response){
    		    	context.store.reload();
    		    }
    		});
    	},
    	beforeedit : function(editor, context, eOpts){
    		var status = context.record.get('status');
    		if(status == 'MAT_GETED'){
    			return false;
    		}else{
    			context.record.set('issueQuntity',context.record.get('applyQuntity'));
    		}
    	},
    	canceledit : function(editor,e,eOpts){
    		e.record.set('issueQuntity','');
    	}
    }
});
Ext.define("bsmes.view.ApplyMaterialsList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.applyMaterialsList',
	store : 'ApplyMaterialsStore',
	defaultEditingPlugin : false,
	plugins: [rowEditing],
	columns : [ 
	    { flex: 1,  text: '机台', sortable: false, dataIndex: 'equipCode' }, 
	    { flex: 1,  text: '要料人', sortable: false, dataIndex: 'userName' }, 
		{ flex: 1,  text: '物料名称', sortable: false, dataIndex: 'matName' }, 
		{ flex: 1,  text: '要料量(KG)', sortable: false, dataIndex: 'applyQuntity' }, 
		{ flex: 1,  text: '发料量(KG)', sortable: false, dataIndex: 'issueQuntity',editor: {allowBlank: false,xtype : 'numberfield'} }, 
		{ flex: 1, text: '要料时间', sortable: false, dataIndex: 'createTime', 
			renderer : function (val) {
			    return Ext.util.Format.date(val, 'Y-m-d H:i:s');
			} 
		}
	],
	
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			xtype : 'hform',
			items: [{
				fieldLabel : '物料状态',
            	name: 'status',
				xtype : 'combobox',
				editable : false,
				queryMode: 'local',
			    displayField: 'name',
			    valueField: 'status',
			    store : new Ext.data.Store({
			    	fields: ['status', 'name'],
			    	data : [{'status':'MAT_DOWN','name':'已要料'},
			    	        {'status':'MAT_GETED','name' : '已发料'}]
			    }),
			    listeners : {
		        	'afterrender' : function(comp,eOpts){
		        		comp.setValue(comp.getStore().getAt(0));
		        	}
		        }
		    },{
				fieldLabel : '机台',
				margin : '0 0 0 30',
            	name: 'equipCode',
				xtype : 'textfield'
		    },{
		    	fieldLabel : '物料名称',
            	name: 'matName',
				xtype : 'combobox',
				editable : false,
				labelWidth : 60,
				queryMode: 'local',
			    displayField: 'matName',
			    valueField: 'matName',
			    store : new Ext.data.Store({
			    	fields : ['matName'],
			    	proxy : {
			    		type : 'ajax',
			    		url : 'applyMaterials/getMatName'
			    	},
			    	autoLoad : true
			    })
		    }]
		}, {
			itemId : 'search'
		},{
			itemId : 'reset',
			handler : function(e){
				Ext.ComponentQuery.query('form')[0].getForm().reset();
			}
		}]
	} ]/*,
	listeners : {
		'itemdblclick' : function(record,item,index,e,eOpts){
			alert('-------');
		}
	}*/
	
    
});
