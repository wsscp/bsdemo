Ext.define('bsmes.view.PrintBarCodeView',{
    extend:'Ext.window.Window',
    alias:'widget.printBarCodeView',
    title:'条码打印',
    height:250,
    width:400,
    modal: true,
    plain: true,
    items:[{
        xtype:'form',
        id:'printArea',
        width:392,
        height:166,
        layout:{
            type:'table',
            columns:2,
            border:1
        },
        padding:'10 10 10 10',
        defaults:{
            labelWidth:80,
            labelAlign:'right'
        },
        items:[{
                xtype:'image',
                fieldLabel:'条码',
                id:'barCodeImage',
                colspan:2,
                height:56
            },{
                xtype:'displayfield',
                fieldLabel:'产品代码',
                name:'halfProductCode',
                colspan:2,
                height:30,
                width:372
            },{
                xtype:'displayfield',
                fieldLabel:'生成单号',
                name:'workOrderNo',
                height:30,
                width:250
            },{
                xtype:'displayfield',
                fieldLabel:'库位',
                name:'locationName',
                labelWidth:40,
                height:30,
                width:122
            },{
                xtype:'displayfield',
                fieldLabel:'打印时间',
                name:'printTime',
                height:30,
                width:250
            },{
                xtype:'displayfield',
                fieldLabel:'长度',
                labelWidth:40,
                name:'reportLength',
                height:30,
                width:122
            }]
    }],
    buttons : [ {
        itemId : 'print',
        text : Oit.btn.print,
        handler : function() {
            $('div#printArea').printArea();
        }
    }, '->',{
        text : Oit.btn.close,
        handler : function() {
            this.up('.window').close();
        }
    }]
});