Ext.define('bsmes.view.MaintainTemplateList',{
	extend : 'Oit.app.view.Grid',
	alias : 'widget.maintainTemplateList',
	store : 'MaintainTemplateStore',
	columns : [{
		text : Oit.msg.fac.maintainTemplate.model,
		dataIndex : 'model',
		filter : {
			type : 'string'
		}
	}, {
		text : Oit.msg.fac.maintainTemplate.triggerType,
		dataIndex : 'triggerType',
		filter : {
			type : 'string'
		},
        renderer: function(value){
            if (value == "NATURE") {
                return "自然时间";
            } else {
                return "设备运行时间";
            }
        }
	} ,{
		text : Oit.msg.fac.maintainTemplate.triggerCycle,
		dataIndex : 'triggerCycle',
		filter : {
			type : 'numeric'
		}
	} ,{
		text : Oit.msg.fac.maintainTemplate.unit,
		dataIndex : 'unit'
	} ,{
		text : Oit.msg.fac.maintainTemplate.type,
		dataIndex : 'type',
		filter : {
			type : 'string'
		},
        renderer: function(value){
            if (value == "DAILY") {
                return "点检";
            } else if (value =="MONTHLY") {
                return "月检";
            } else if (value =="FIRST_CLASS") {
                return "一级保养";
            } else if (value =="SECOND_CLASS") {
                return "二级保养";
            } else {
                return "大修";
            }
        }
	} ,{
		text : Oit.msg.fac.maintainTemplate.describe,
		dataIndex : 'describe',
		filter : {
			type : 'string'
		}
	} ,{
		text : Oit.msg.createUserCode,
		dataIndex : 'createUserCode',
		filter : {
			type : 'string'
		}
	} ,{
		text : Oit.msg.createTime,
		dataIndex : 'createTime',
		xtype : 'datecolumn',
		filter : {
			type : 'date'
		}
	} ,{
		text : Oit.msg.modifyUserCode,
		dataIndex : 'modifyUserCode',
		filter : {
			type : 'string'
		}
	} ,{
		text : Oit.msg.modifyTime,
		dataIndex : 'modifyTime',
		xtype : 'datecolumn',
		filter : {
			type : 'date'
		}
	},{
		text : '设备类型',
		dataIndex : 'eqipCategory',
		filter : {
			type : 'string'
		}
	} ],
	actioncolumn : ['',{
        itemId : 'edit'
    },'',{
        itemId : 'editItem',
        icon : '/bsstatic/icons/table.png',
        tooltip : Oit.msg.fac.maintainTemplate.btn.editItem,
        handler : function(grid, rowIndex) {
            var row = grid.getStore().getAt(rowIndex);
            Oit.app.controller.GridController.openSubWindow('maintainItem.action?tempId=' + row.get('id'));
        }
    }],
	tbar : [ {
		itemId : 'add'
	},{
		itemId : 'remove'
	}]
});