Ext.util.Cookies.set("ExtRepoDevMode", "false");
Ext.Loader.setConfig({
    enabled: true,
    disableCaching : true
});
Ext.Ajax.timeout = 90000;//90秒

Ext.onReady(function(){
	Ext.QuickTips.init();
//	Ext.apply(Ext.tip.QuickTipManager.getQuickTip(), {
//	    minWidth: 100,
//	    shrinkWrap :1,
//	    shrinkWrapDock : true
//	});

	var appName = Ext.fly('app').getAttribute('name');
	var centerItems = [];
	var westItems = [];
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
	Ext.each(Ext.query('div[id="west"] widget'), function(html){
		var widget = Ext.fly(html);
		westItems.push({
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
	    	if (Ext.fly('north')) {
	    		borderItems.push({
	       			region : 'north',
	       			contentEl : 'north'
                });
	    	}
	    	if (Ext.fly('east')) {
	    		borderItems.push({
	       			region : 'east',
	       			collapsible: true,
	       			collapsed: true, 
	       			split: true,
	       			items : {
	       			    xtype : 'detailgrid'
	       			},
	       			width : 300
	       		});
	    	}
	    	if (Ext.fly('west')) {
	    		borderItems.push({
	    			region : 'west',
	    			items : westItems,
	       			collapsible: true,
	       			collapsed: false,
                    layout : 'fit',
	   				split: true,
	   				width : 300
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
