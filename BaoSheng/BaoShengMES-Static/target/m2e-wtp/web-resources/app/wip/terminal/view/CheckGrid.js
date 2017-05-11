Ext.define('bsmes.view.CheckGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.checkGrid',
	id:'checkGridId',
	store : 'CheckGridStore',
	plugins: [
	           {
	               ptype: 'cellediting',
	               clicksToEdit: 1,
	               pluginId: 'cellplugin'
	           }
	 ],
	columns : [{
			text : Oit.msg.wip.terminal.check.describe,
			flex:4.5,
			dataIndex : 'describe',
			renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
				metaData.style = "white-space:normal";
				return value;
			}
		},{
			text : Oit.msg.wip.terminal.check.value,
			dataIndex : 'value',
			width:200,
			filter: {
	                type: 'numeric'
	        },
	        editor: {
	                xtype: 'textfield',
	                decimalPrecision:2,
                    listeners:{
                        focus:function( me, the, eOpts ){
                            var win =  Ext.create('bsmes.view.VirtualKeyBoardWindow',{
                                targetGridId:'checkGridId',
                                targetFieldName:'value',
                                targetVlaue:me.getValue()
                            });

                            win.show();
                            win.down('form').getForm().findField('targetValue').focus(false,100);
                        }
                    }
	        }
		},{
			text : Oit.msg.wip.terminal.check.isPassed,
			dataIndex : 'isPassed',
			width:250,
			renderer:function(value, cellmeta, record,rowIndex,store){
			     var pass=Oit.msg.wip.dataCollection.pass;
	             var noPass=Oit.msg.wip.dataCollection.noPass;
		         var res="<label><input value='true' name='isPassed"+rowIndex+"' type='radio' checked='checked'>" + pass + "</label>&nbsp;&nbsp;&nbsp;"+
		                    "<label><input value='false' name='isPassed"+rowIndex+"' type='radio'>" + noPass + "</label>";
		         return res;
		     }
		}/*,{
     text : Oit.msg.wip.terminal.check.remarks,
     dataIndex : 'remarks',
     flex:3,
     editor: {
     xtype: 'textareafield'
     }
     }*/
	]

});

