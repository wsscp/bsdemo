Ext.Loader.setConfig({enabled: true, disableCaching : true });
Ext.define("bsmes.view.WorkTaskView", {
    extend : "Sch.panel.SchedulerTree",
    alias : 'widget.workTaskView',
    rowHeight       : 25, 
    eventBorderWidth :1,
    rowLines    : true,
    eventStore : 'EventStore', 
    resourceStore : 'WorkTaskStore',
    viewPreset: 'hourAndDayOit',
    readOnly : true, 
    startDate: new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),0,0,0)  ,
    trackHeaderOver :true,
    useArrows        : true,   
    multiSelect     : true,
    endDate : Sch.util.Date.add(new Date(), Sch.util.Date.WEEK,15),
    columnLines : true,
    layout : { type : 'hbox', align : 'stretch' },
    columns: [{
                    xtype : 'treecolumn', 
                    text : '工序信息',
                    width : 238,
                    dataIndex: 'Name',
                    renderer  : function (v, meta, r) {
 	                   if (!r.data.leaf){
 	                    	  meta.tdCls = 'sch-gantt-parent-cell';
 	                    }                   
 	                    return v;
 	             }
                }],
         eventRenderer : function(flight, resource, meta) {
      	          if(resource.data.leaf) {
      	               meta.cls = 'leaf';      	                
      	                return flight.get('Name');
      	          } else {
      	                meta.cls = 'group';
      	                return '&nbsp;';
      	            }
      	   },
    tooltipTpl : new Ext.XTemplate(
            '<dl class="tip">', 
                '<dt  >开始时间 :{[Ext.Date.format(values.startDate, "Y-m-d G:i")]}</dt>',
                '<dt  >结束时间 :{[Ext.Date.format(values.endDate, "Y-m-d G:i")]}</dt>',
                '<dt  >产出半成品 :{[values.name]}</dt>',
            '</dl>'
        ).compile() 
 });
 
