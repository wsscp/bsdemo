Ext.define('bsmes.view.MaintainRecordList',{
	extend : 'Oit.app.view.Grid',
	alias : 'widget.maintainRecordList',
	store : 'MaintainRecordStore',
	columns : [{
		text : Oit.msg.fac.maintainRecord.equipCode,
		dataIndex : 'equipCode',
		filter : {
			type : 'string'
		}
	} ,{
		text : Oit.msg.fac.maintainRecord.createUserCode,
		dataIndex : 'createUserCode',
		filter : {
			type : 'string'
		}
	} ,{
		text : Oit.msg.fac.maintainRecord.startTime,
		dataIndex : 'startTime',
		xtype : 'datecolumn',
		filter : {
			type : 'date'
		}
	} ,{
		text : Oit.msg.fac.maintainRecord.finishTime,
		dataIndex : 'finishTime',
		xtype : 'datecolumn',
		filter : {
			type : 'date'
		}
	} ,{
		text : Oit.msg.fac.maintainRecord.status,
		dataIndex : 'status',
        renderer: function (value, cellmeta, record) {
            switch (value) {
                case 'FINISHED':
                    return '已完成';
                case 'IN_PROGRESS':
                    return '进行中';
                default:
                    return '进行中';
            }
        }
	},{
        text : Oit.msg.fac.maintainTemplate.type,
        dataIndex : 'type',
        filter : {
            type : 'string'
        },
        renderer: function(value){
            if (value == "DAILY") {
                return "点检";
            } else if (value == "FIRST_CLASS") {
                return "一级保养";
            } else if (value == "SECOND_CLASS") {
                return "二级保养";
            } else if (value == "OVERHAUL") {
                return "大修";
            } else {
                return "月检";
            }
        }
    } ],
	actioncolumn : [{
		itemId : 'recordDetail',
        tooltip : Oit.btn.detail,
        iconCls : 'icon_detail',
        handler : function(grid, rowIndex) {
            var row = grid.getStore().getAt(rowIndex);
            window.location.href = 'maintainRecordItem.action' + window.location.search + '&recordId=' + row.get('id');
        }
	}],
    dockedItems : [{
        xtype : 'toolbar',
        dock : 'top',
        items : [{
            title : '查询条件',
            xtype : 'fieldset',
            collapsible: true,
            width : '100%',
            items : [{
                xtype : 'form',
                width : '100%',
                layout : 'vbox',
                buttonAlign : 'left',
                labelAlign : 'right',
                bodyPadding : 5,
                defaults : {
                    xtype : 'panel',
                    width : '100%',
                    layout:'column',
                    defaults:{
                        width:300,
                        padding:1,
                        labelAlign:'right'
                    }
                },
                items:[{
                    items : [{
                        fieldLabel: Oit.msg.fac.maintainRecord.status,
                        xtype: 'checkboxgroup',
                        width:500,
                        columns: 5,
                        vertical: true,
                        items: [
                            {boxLabel: '进行中',name: 'status', inputValue: 'IN_PROGRESS',checked:true},
                            {boxLabel: '已完成',name: 'status', inputValue: 'FINISHED',checked:true}
                        ]
                    }]
                }],
                buttons : [{
                    itemId : 'search',
                    text : Oit.btn.search
                },{
                    itemId:'reset',
                    text : Oit.btn.reset,
                    handler : function(e) {
                        this.up("form").getForm().reset();
                    }
                }]
            }]
        }]
    }],
	tbar : [ {
        text : Oit.msg.fac.maintainRecord.btn.dailyAdd,
        handler : function() {
        	Oit.app.controller.GridController.refreshParentWindow('maintainRecordItem.action' + window.location.search + '&recordId=-1&type=DAILY',Oit.msg.fac.maintainRecord.btn.dailyAdd);
        }
	}, {
        text : Oit.msg.fac.maintainRecord.btn.monthlyAdd,
        handler : function() {
            Oit.app.controller.GridController.refreshParentWindow('maintainRecordItem.action' + window.location.search + '&recordId=-1&type=MONTHLY',Oit.msg.fac.maintainRecord.btn.monthlyAdd);
        }
	}, {
        text : Oit.msg.fac.maintainRecord.btn.firstClassAdd,
        handler : function() {
        	Oit.app.controller.GridController.refreshParentWindow('maintainRecordItem.action' + window.location.search + '&recordId=-1&type=FIRST_CLASS',Oit.msg.fac.maintainRecord.btn.firstClassAdd);
        }
	}, {
        text : Oit.msg.fac.maintainRecord.btn.secondClassAdd,
        handler : function() {
        	Oit.app.controller.GridController.refreshParentWindow('maintainRecordItem.action' + window.location.search + '&recordId=-1&type=SECOND_CLASS',Oit.msg.fac.maintainRecord.btn.secondClassAdd);
        }
	}, {
        text : Oit.msg.fac.maintainRecord.btn.overhaulAdd,
        handler : function() {
        	Oit.app.controller.GridController.refreshParentWindow('maintainRecordItem.action' + window.location.search + '&recordId=-1&type=OVERHAUL',Oit.msg.fac.maintainRecord.btn.overhaulAdd);
        }
    }]
});