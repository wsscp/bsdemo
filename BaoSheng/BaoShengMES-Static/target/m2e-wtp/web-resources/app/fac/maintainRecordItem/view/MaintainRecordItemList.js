Ext.define('bsmes.view.MaintainRecordItemList', {
    extend: 'Oit.app.view.Grid',
    alias: 'widget.maintainRecordItemList',
    store: 'MaintainRecordItemStore',
    id:'ss',
    defaultEditingPlugin: false,
    plugins: [
        {
            ptype: 'cellediting',
            clicksToEdit: 1,
            pluginId: 'cellplugin'
        }
    ],
    columns: [
        {
            text: Oit.msg.fac.maintainRecordItem.describe,
            dataIndex: 'describe',
            filter: {
                type: 'string'
            }
        },
        {
            text: Oit.msg.fac.maintainRecordItem.value,
            dataIndex: 'value',
            filter: {
                type: 'numeric'
            },
            editor: {
                xtype: 'numberfield'
            }
        },
        {
            text: Oit.msg.fac.maintainRecordItem.isPassed,
            dataIndex: 'isPassed',
            xtype: 'booleancolumn',
            trueText: '是',
            falseText: '否',
            filter: {
                type: 'boolean'
            },
            editor: {
                xtype: 'checkboxfield'
            }
        },
        {
            text: Oit.msg.fac.maintainRecordItem.remarks,
            dataIndex: 'remarks',
            filter: {
                type: 'string'
            },
            editor: {
                xtype: 'textareafield'
            }
        }
    ],
    dockedItems: [
        {
            xtype: 'toolbar',
            dock: 'top',
            hidden: Ext.fly('completed').getHTML() == 'true',
            items: [
                {
                    title: '维修时间',
                    xtype: 'fieldset',
                    collapsible: true,
                    width: '100%',
                    items: [
                        {
                            xtype: 'form',
                            id: 'maintainRecordTimeForm',
                            width: '100%',
                            buttonAlign: 'left',
                            labelAlign: 'right',
                            layout: {
                                type: 'table',
                                columns: 2
                            },
                            defaults: {
                                padding: 5
                            },
                            items: [
                                {
                                    fieldLabel: Oit.msg.fac.maintainRecord.startTime,
                                    width : 300,
                                    labelWidth : 110,
                                    xtype: 'datetimefield',
                                    name: 'startTime',
                                    listeners: {
                                        change: function (field, newValue, oldValue, eOpts) {
                                            Ext.Ajax.request({
                                                url: 'maintainRecord/startTime/' + newValue,
                                                params: Oit.url.urlParams(),
                                                failure: function () {
                                                    field.setValue(oldValue);
                                                }
                                            });
                                        }
                                    },
                                    value: Ext.fly('startTime').getHTML() == "" ? "" : new Date(Number(Ext.fly('startTime').getHTML()))
                                },
                                {
                                    fieldLabel: Oit.msg.fac.maintainRecord.finishTime,
                                    width : 300,
                                    labelWidth : 110,
                                    xtype: 'datetimefield',
                                    name: 'finishTime',
                                    listeners: {
                                        change: function (field, newValue, oldValue, eOpts) {
                                            if (newValue == null) {
                                                return;
                                            }
                                            Ext.Ajax.request({
                                                url: 'maintainRecord/finishTime/' + newValue,
                                                params: Oit.url.urlParams(),
                                                failure: function () {
                                                    field.setValue(oldValue);
                                                }
                                            });
                                        }
                                    },
                                    value: Ext.fly('finishTime').getHTML() == "" ? "" : new Date(Number(Ext.fly('finishTime').getHTML()))
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ],
    tbar: [
        {
            text: Oit.btn.back,
            hidden: Ext.fly('touch').getHTML() == 'true',
            handler: function () {
                window.history.back();
            }
        },
        {
            itemId: 'save',
            iconCls: '',
            hidden: Ext.fly('completed').getHTML() == 'true'
        },
        {
            itemId: 'delete',
            text: Oit.msg.button.remove,
            iconCls: '',
            hidden: Ext.fly('completed').getHTML() == 'true'
        },
        {
            text: Oit.msg.fac.maintainRecord.btn.complete,
            itemId: 'complete',
            hidden: Ext.fly('completed').getHTML() == 'true'
        }
    ]
});