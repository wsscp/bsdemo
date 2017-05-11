Ext.define('bsmes.controller.WorkTaskController', {
    extend:'Ext.app.Controller',
    models: ['EventModel','WorkTask'],
    stores:['EventStore','WorkTaskStore'],
    views:['WorkTaskView']
});