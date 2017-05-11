Ext.define('bsmes.controller.HalfProductInController',{
        extend: 'Ext.app.Controller',
		view:'halfProductInfoForm',
        printView:'printBarCodeView',
        views:['HalfProductInfoForm','PrintBarCodeView'],
        stores:['WarehouseStore','LocationStore'],
        constructor: function() {
            var me = this;
            me.refs = me.refs || [];

            me.refs.push({
                ref: 'printView',
                selector: me.printView,
                autoCreate: true,
                xtype: me.printView
            });

            me.refs.push({
                ref:'printForm',
                selector:me.printView + ' form'
            });

            me.callParent(arguments);
        },
        init: function() {
            var me = this;
            if (!me.view) {
                Ext.Error.raise("A view configuration must be specified!");
            }

            // 初始化工具栏
            me.control(me.view + ' button[itemId=save]', {
                click: me.onSave
            });

            me.control(me.view + ' button[itemId=print]', {
                click: me.onPrint
            });
            me.callParent(arguments);
        },
        onSave:function(){
            var barCode = Ext.getCmp('barCodeField').getValue();
            var warehouseId = Ext.getCmp('warehouseComField').getValue();
            var locationId = Ext.getCmp('locationComField').getValue();
            if(Ext.isEmpty(barCode)||Ext.isEmpty(warehouseId)||Ext.isEmpty(locationId)){
                Ext.MessageBox.alert(Oit.msg.WARN, Oit.msg.inv.halfProductIn.msg.saveWarnMsg);
                return;
            }
            Ext.Msg.wait('处理中，请稍后...', '提示');
            Ext.Ajax.request({
                url:'halfProductIn/inWarehouse',
                method:'POST',
                params:{
                    barCode:barCode,
                    warehouseId:warehouseId,
                    locationId:locationId
                },
                success:function(){
                    Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.inv.halfProductIn.msg.handInWarehouseMsg);
                },
                failure:function(){

                }
            });
        },
        onPrint:function(){
            var me = this;
            var barCode = Ext.getCmp('barCodeField').getValue();
            if(Ext.isEmpty(barCode)){
                Ext.MessageBox.alert(Oit.msg.PROMPT, Oit.msg.inv.halfProductIn.msg.selectRefreshBarCode);
                return;
            }
            Ext.Ajax.request({
                url:'halfProductIn/printBar',
                method:'POST',
                params:{
                    barCode:barCode
                },
                success:function(response){
                    var result = Ext.decode(response.responseText);
                    var report = result.report;

                    var LODOP=getLodop();
                    var top = 10;
                    var left = 10;
                    var printWidth = 355;
                    var printHeight = 535;
                    var trHeight = 25;
                    var topSuf = 5;
                    var leftSuf = 5;

                    LODOP.PRINT_INIT("报工条码打印");
                    LODOP.SET_PRINT_PAGESIZE(0,1040,625,"BC");
                    LODOP.SET_PRINT_STYLE("FontSize",12);

                    LODOP.ADD_PRINT_LINE(top,left,top,printWidth+left,0,1);

                    //设置BarCode
                    top =  top + 10; //top = 20
                    LODOP.ADD_PRINT_BARCODE(25,30,330,60,"128Auto",report.serialNum);
                    //print
                    top = top + 80; //top = 80
                    LODOP.ADD_PRINT_LINE(top,left,top,printWidth+left,0,1);
                    LODOP.ADD_PRINT_TEXT(top+topSuf,left + leftSuf,375,20,"产品代码:"+report.halfProductCode);

                    top = top + trHeight; //top = 130
                    LODOP.ADD_PRINT_LINE(top,left,top,printWidth+left,0,1);
                    LODOP.ADD_PRINT_TEXT(top+topSuf,left+leftSuf,375,20,"打印时间:"+report.printTime);

                    top = top + trHeight; //top = 130
                    LODOP.ADD_PRINT_LINE(top,left,top,printWidth+left,0,1);
                    LODOP.ADD_PRINT_TEXT(top+topSuf,left+leftSuf,375,20,"工序:"+report.processName);
                    LODOP.ADD_PRINT_TEXT(top+topSuf,215,150,20,"颜色:"+report.color);

                    top =top + trHeight; //top = 155
                    LODOP.ADD_PRINT_LINE(top,left,top,printWidth+left,0,1);
                    LODOP.ADD_PRINT_LINE(top-trHeight,210,top,210,0,1);
                    LODOP.ADD_PRINT_TEXT(top+topSuf,left+leftSuf,150,20,"定制区:"+report.locationName);
                    LODOP.ADD_PRINT_TEXT(top+topSuf,215,150,20,"长度:"+report.reportLength+"M");

                    top = top + trHeight;//top = 180
                    LODOP.ADD_PRINT_LINE(top,left,top,printWidth+left,0,1);
                    LODOP.ADD_PRINT_LINE(top-trHeight,210,top,210,0,1);
                    LODOP.ADD_PRINT_LINE(10,10,top,10,0,1);
                    LODOP.ADD_PRINT_LINE(10,printWidth+left,top,printWidth+left,0,1);
                    LODOP.PRINT();
                },
                failure:function(response){
                    var result = Ext.decode(response.responseText);
                    Ext.MessageBox.alert(Oit.msg.PROMPT, result.msg);
                }
            });
        }
});