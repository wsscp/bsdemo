Ext.define('bsmes.controller.ResourceGanttController', {
	extend : 'Oit.app.controller.GridController',
	view : 'resourceGantt',
	views : [ 'ResourceGantt'],
	stores : [ 'EventStore','WorkTaskStore']
});