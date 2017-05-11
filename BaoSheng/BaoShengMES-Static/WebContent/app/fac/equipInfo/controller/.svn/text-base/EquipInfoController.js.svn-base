Ext.define('bsmes.controller.EquipInfoController', {
	extend : 'Oit.app.controller.GridController',
	view : 'equipInfoList',
	views : [ 'EquipInfoList'],
	stores : [ 'EquipInfoStore' ],
    exportUrl:'equipInfo/export/设备信息',
    onExport: function() {
        var me = this;
        if (!me.exportUrl) {
            Ext.Error.raise("A view configuration must be specified!");
        }
        var params = [];
        Ext.each(me.getGrid().columns, function(column) {
            if (!column.dataIndex) {
                return;
            }
            params.push({
                text : column['text'],
                dataIndex : column['dataIndex']
            })
        });
        falseAjaxTarget.document.write('<form method="post"><input id="params" name="params"><input id="queryParams" name="queryParams"></form>');
        falseAjaxTarget.document.getElementById("params").value = JSON.stringify(params);
        falseAjaxTarget.document.getElementById("queryParams").value = JSON.stringify({"type":"MAIN_EQUIPMENT"});
        var form = falseAjaxTarget.document.forms[0];
        form.action = me.exportUrl;
        form.submit();
    }
});