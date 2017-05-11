/**
 * Created by Administrator on 2014/6/17.
 */
Ext.define("bsmes.view.ProductReportDetailGrid",{
    extend : 'Oit.app.view.Grid',
    alias:'widget.productReportDetailGrid',
    store:'ProductReportStore',
    id:'multiReportDetailGrid_01',
    overflowY:'auto',
    overflowX:'auto',
    height:document.body.scrollHeight-140,
    forceFit : true,
    defaultEditingPlugin:false,
    columns:[{
    		text : '合同号',
    		dataIndex : 'contractNo',
    		sortable :false,
    		flex : 2,
    		renderer:function(v){
                v = v.replace(/[a-zA-Z]/g,"");
                if(v && v.length > 5){
                    return v.substring(v.length-5, v.length);
                }else{
                    return v;
                }
            }
    	},{
            text : '产品型号',
            dataIndex : 'custProductType',
            sortable : false,
            flex:2,
            renderer : function(value, metaData, record,
                                rowIndex, columnIndex, store, view) {
                metaData.style = "white-space:normal";
                return value.replace(/,/g,'<br/>');
            }
        },{
            text : '产品规格',
            dataIndex : 'productSpec',
            sortable : false,
            flex:1
        },{
            dataIndex:'productColor',
            text:'产品颜色',
            sortable : false,
            flex:1.5,
            renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                metaData.style = "white-space:normal";
                return value;
            }
        },{
            dataIndex:'coilNum',
            text:'线盘号',
            sortable : false,
            flex:1
        },{
            dataIndex:'reportLength',
            text:'总长度',
            sortable : false,
            flex:1
        },{
            dataIndex:'goodLength',
            text:'合格长度',
            sortable : false,
            flex:1
        },{
            dataIndex:'reportUserName',
            text:'报工人',
            sortable : false,
            flex:1
        },{
            dataIndex:'reportTime',
            text:'报工时间',
            xtype: 'datecolumn',
            format: 'Y-m-d H:i:s',
            sortable : false,
            flex:2

        },{
        	dataIndex : 'rMarks',
        	text : '用料',
        	sortable : false,
            flex:3
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
                            }
                        }
                    ],
                    buttons:[{
                        xtype:'button',
                        text:'关闭',
                        handler:function(){
                            this.up('window').close();
                        }
                    }]
                }
            }]
        }];
        me.callParent(arguments);
    }
});