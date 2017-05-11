Ext.define("Oit.app.view.grid.FormFiltersFeature", {
    extend : 'Ext.ux.grid.FiltersFeature',
    alias: 'feature.formFilters',
    init: function(grid) {
        this.callParent(arguments);
        this.gridXtype = grid.xtype;
    },
    onBeforeLoad : function (store, options) {
        this.callParent(arguments);
        var searchForm = Ext.ComponentQuery.query(this.gridXtype + " form")[0];
        if (!searchForm) {
            return;
        }
        var params = searchForm.form.getValues();
        Ext.apply(options.params, params);
    }
});
