Ext.define('bsmes.controller.MaintainRecordItemController',{
	extend : 'Oit.app.controller.GridController',
	view : 'maintainRecordItemList',
	editview : 'maintainRecordItemEdit',
	views : ['MaintainRecordItemList', 'MaintainRecordItemEdit'],
	stores : ['MaintainRecordItemStore'],
    onLaunch : function() {
        var me = this;
        me.control(me.view + ' button[itemId=save]', {
            click: me.save
        });
        me.control(me.view + ' button[itemId=delete]', {
            click: me.remove
        });
        me.control(me.view + ' button[itemId=complete]', {
            click: me.complete
        });
    },
    remove : function() {
        Ext.Ajax.request({
            url: 'maintainRecord/' + Oit.url.urlParam('recordId'),
            method: "DELETE",
            success: function(response){
                window.history.back();
            }
        });
    },
    save : function() {
        this.getGrid().getStore().sync();
    },
    complete : function() {
        var failure = false;
        this.getGrid().getStore().sync({
            failure : function() {
                failure = true;
            }
        });
        if (!failure) {
            this.doComplete();
        }
    },
    doComplete : function() {
        Ext.Ajax.request({
            url: 'maintainRecord/complete/' + Oit.url.urlParam('recordId') + '?touch=' + Ext.fly('touch').getHTML(),
            success: function(response){
                var text = response.responseText;
                console.log(Ext.fly('touch').getHTML());
                if (text.length == 0) {
                    if (Ext.fly('touch').getHTML()=='true') {
                        parent.Oit.app.controller.GridController.closeSubWindow();
                    } else {
                        window.history.back();
                    }
                } else {
                    var result = Ext.JSON.decode(text);
                    if (result.success == 'false') {
                        Ext.msg.alert(Oit.ERROR ,result.message);
                    }
                }
            }
        });
    }

});