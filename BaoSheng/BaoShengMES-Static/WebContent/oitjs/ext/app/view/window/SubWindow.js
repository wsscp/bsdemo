Ext.define('Oit.app.view.window.SubWindow',{
	extend : 'Ext.window.Window',
    requires : ['Oit.panel.IframePanel'],
    alias:'widget.subWindow',
    width: 800,
    height:500,
    modal: true,
    plain: true,
    useIframe : true,
    iframeName : 'subWindow',
    show : function(src) {
        this.callParent();
        subWindow.location.href = src;
    }

});