Ext.util.Cookies.set("ExtRepoDevMode", "true");
Ext.Loader.setConfig({
    enabled: true,
    disableCaching : true
});

Ext.onReady(function(){
	Ext.QuickTips.init();
//	Ext.apply(Ext.tip.QuickTipManager.getQuickTip(), {
//	    minWidth: 100,
//	    shrinkWrap :1,
//	    shrinkWrapDock : true
//	});

	var appName = Ext.fly('app').getAttribute('name');
	var centerItems = [];
	var eastItems = [];
	var controllers = [];
	Ext.each(Ext.query('div[id="center"] widget'), function(html){
		var widget = Ext.fly(html);
		centerItems.push({
			items : [{
				xtype : widget.getAttribute('xtype')
			}],
			layout : 'fit'
		});
		controllers.push(widget.getAttribute('controller'));
	});
	Ext.each(Ext.query('div[id="east"] widget'), function(html){
		var widget = Ext.fly(html);
		eastItems.push({
			items : [{
				xtype : widget.getAttribute('xtype')
			}],
			layout : 'fit'
		});
		controllers.push(widget.getAttribute('controller'));
	});
	
	Ext.application({
	    name: appName.substring(1),
	    appFolder: '/bsstatic/app/' + Ext.fly('app').getAttribute('module') + '/' + Ext.fly('app').getAttribute('submodule'),
	    paths: {'Oit': '/bsstatic/oitjs/ext'},
		requires : [
		    'Oit.form.Panel', 
		    'Oit.app.view.DetailGrid',
		    'Oit.toolbar.Paging',
			'Oit.locale.Message'
		],
	    enableQuickTips: true,
	    launch: function() {
	    	var borderItems = [ {
                region : 'center',
                items : centerItems,
                layout : 'fit'
            } ];
	    	if (Ext.fly('east')) {
	    		borderItems.push({
	       			region : 'east',
	       			split: true,
	       			layout : 'fit',
	       			items : eastItems,
	       			width :630
	       		});
	    	}
	        Ext.create('Ext.Viewport',{
	       		layout : 'border',
	       		items : borderItems
	        });
	    },
	    controllers: controllers
	});
});
