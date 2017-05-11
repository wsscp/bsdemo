/**
 * Created by Administrator on 2014/6/17.
 */
Ext.define("bsmes.view.ReportDetailGrid",{
    extend:'Ext.grid.Panel',
    alias:'widget.reportDetailGrid',
    store:'EquipReportStore',
    title:'报工信息',
    forceFit : true,
    defaultEditingPlugin:false,
    columns:[
        {
            text : Oit.msg.wip.terminal.productType,
            dataIndex : 'productType',
            flex:2
        },{
            text : Oit.msg.wip.terminal.productSpec,
            dataIndex : 'productSpec',
            flex:1
        },{
            text : Oit.msg.wip.terminal.workOrderNo,
            dataIndex : 'workOrderNo',
            flex:2
        },{
            dataIndex:'color',
            text:Oit.msg.wip.terminal.productColor,
            flex:1
        },{
            dataIndex:'coilNum',
            text:'线盘号',
            flex:1
        },{
            dataIndex:'reportLength',
            text:'总长度',
            flex:1
        },{
            dataIndex:'goodLength',
            text:'合格长度',
            flex:1
        },{
            dataIndex:'reportUserName',
            text:'报工人',
            flex:1
        },{
            dataIndex:'reportTime',
            text:'报工时间',
            xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
            flex:2
        }],
    initComponent: function() {
        var me = this;
        me.dockedItems = [{
            xtype : 'toolbar',
            dock 	: 'top',
            layout:'vbox',
            items  : [{
                title: '查询条件',
                xtype: 'fieldset',
                collapsible: true,
                width: '99%',
                items:{
                    xtype: 'form',
                    width: '100%',
                    layout: 'vbox',
                    buttonAlign: 'left',
                    labelAlign: 'right',
                    bodyPadding: 5,
                    items:[
                        {
                            xtype: 'panel',
                            width: '100%',
                            layout: 'column',
                            defaults: {
                                labelAlign: 'right',
                                labelWidth:100
                            },
                            items:[{
                                fieldLabel: "报工时间",
                                xtype: 'radiogroup',
                                id:'reportTimeRound',
                                width:document.body.scrollWidth-200,
                                columns: 5,
                                items:[{
                                    boxLabel  : '当天',
                                    name      : 'reportDate',
                                    inputValue: '0'
                                },{
                                    boxLabel  : '近三天',
                                    name      : 'reportDate',
                                    inputValue: '-2'
                                },{
                                    boxLabel  : '近七天',
                                    name      : 'reportDate',
                                    inputValue: '-6'
                                }],
                                listeners:{
                                    change:function(me,newValue,oldValue,opts){
                                        var me = this;
                                        var grid =  me.up('grid');
                                        grid.getStore().loadPage(1, {
                                            params: {
                                            	reportDate:newValue
                                            }
                                        });
                                    }
                                }
                            }]
                        }
                    ]
                }
            }]
        }];
        me.callParent(arguments);

/*        me.view.on('expandBody', function (rowNode, record, expandRow, eOpts) {
            var renderId = record.get('id');
            var url = "reportSection/"+renderId+"/";
            var innerStore = Ext.create('bsmes.store.ReportSectionStore');
            innerStore.getProxy().url = url;
            innerStore.reload();

            var subGrid = Ext.create("bsmes.view.ReportSectionGrid",{
                store: innerStore,
                renderTo: renderId
            });

            subGrid.getEl().swallowEvent([
                'mousedown', 'mouseup', 'click',
                'contextmenu', 'mouseover', 'mouseout',
                'dblclick', 'mousemove'
            ]);
        });
        me.view.on('collapsebody', function (rowNode, record, expandRow, eOpts) {
            var parent = document.getElementById(record.get('id'));
            var child = parent.firstChild;
            while (child) {
                child.parentNode.removeChild(child);
                child = child.nextSibling;
            }
        });*/
    }
   /* initColorGroup:function(obj,view,opts){
        var me = this;
        Ext.Ajax.request({
            url:"../terminal/productColors",
            method:'GET',
            success:function(response){
                var result = Ext.decode(response.responseText);
                var items = [];
                Ext.Array.each(result,function(color){
                    items.push(new Ext.form.Radio({
                        boxLabel: color.name,
                        inputValue: color.name,
                        name: 'productColor'
                    }));
                });
                items.push(new Ext.form.Radio({
                    boxLabel: "全部颜色",
                    inputValue: "",
                    name: 'productColor'
                }));
                obj.add(items);
            }
        });
    }*/
});