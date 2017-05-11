Ext.define('bsmes.view.PriorityLeftList',{
	extend:'Oit.app.view.Grid',
	id:'leftgrid',
    stripeRows: true,
    selType: 'checkboxmodel',
    alias: 'widget.leftGridView',
    frame: true,
    store: 'LeftStore',
    defaultEditingPlugin:false,
    columns: [
              {text : Oit.msg.wip.workOrder.workOrderNO,dataIndex : 'workOrderNo'}, 
              {text : Oit.msg.wip.workOrder.halfProductCode,dataIndex : 'halfProductCode'}
    		]
});