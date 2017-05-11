Ext.define("Oit.app.view.DetailGrid", {
	extend : 'Ext.grid.property.Grid',
	alias : 'widget.detailgrid',
	hidden : true,
	initComponent: function() {
		this.callParent(arguments);
		this.on('show', function() {
			this.ownerCt.expand();
		});
	},
	setSource : function(source, sourceConfig, show ) {
		if (Ext.isBoolean(sourceConfig)) {
			show = sourceConfig;
			sourceConfig = null;
		}
		if (show || show == undefined) {
			this.show();
		}
		this.callParent([source, sourceConfig]);
	}
});
