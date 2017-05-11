Ext.define("Oit.panel.IframePanel", {
    override : "Ext.panel.Panel",
    iframeSrc : '',
    initComponent: function() {
        var me = this;
        if (me.useIframe) {
            me.html = "<iframe";
            if (me.iframeId) {
                me.html += " id='" + me.iframeId + "'";
            }
            if (me.iframeName) {
                me.html += " name='" + me.iframeName + "'";
            }
            if (me.iframeSrc) {
                me.html += " src='" + me.iframeSrc + "'";
            }
            me.html += " frameborder='0'  scrolling='no' width='100%' height='100%'></iframe>";
        }

        me.callParent(arguments); // ------------call父类--------------
    }
});