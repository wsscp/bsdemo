Ext.define("bsmes.view.Toolbar", {
    extend : "Ext.Toolbar",
    height:30,
    width:500,
    gantt : null,
	initComponent : function() {
		var gantt = this.gantt;
		Ext.apply(this, {
			items : [{
						iconCls : 'icon-prev',
						text : 'Previous',
						handler : function() {
							gantt.shiftPrevious();
						}
					},{
						iconCls : 'icon-next',
						text : 'Next',
						handler : function() {
							gantt.shiftNext();
						}
					},{
						text : 'Collapse all',
						iconCls : 'icon-collapseall',
						handler : function() {
							gantt.collapseAll();
						}
					},{
						text : 'Expand all',
						iconCls : 'icon-expandall',
						handler : function() {
							gantt.expandAll();
						}
					},{
						text : 'Zoom to fit',
						iconCls : 'zoomfit',
						handler : function() {
							gantt.zoomToFit();
						}
					},{
						xtype : 'textfield',
						emptyText : 'Search for task...',
						width : 150,
						enableKeyEvents : true,
						listeners : {
							keyup : {
								fn : function(field, e) {
									var value = field.getValue();
									var regexp = new RegExp(Ext.String
											.escapeRegex(value), 'i')

									if (value) {
										gantt.taskStore.filterTreeBy(function(
												task) {
											return regexp
													.test(task.get('Name'))
										});
									} else {
										gantt.taskStore.clearTreeFilter();
									}
								},
								buffer : 300,
								scope : this
							},
							specialkey : {
								fn : function(field, e) {
									if (e.getKey() === e.ESC) {
										field.reset();

										gantt.taskStore.clearTreeFilter();
									}
								}
							}
						}

					}]
		});
		this.callParent(arguments);
	}
});
