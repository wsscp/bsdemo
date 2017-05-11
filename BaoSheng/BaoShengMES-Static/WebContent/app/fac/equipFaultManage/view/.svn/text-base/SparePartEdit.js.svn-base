Ext.define('bsmes.view.SparePartEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.sparePartEdit',
	title: '备品备件替换',
	width: 600,
	defaults:{
		layout: {
	        type: 'table',
	        // The total column count must be specified here
	        columns: 2
	    },
    },
	
	formItems: [{
    	fieldLabel: '备件型号规格',
    	allowBlank: false,  
    	name: 'sparePartModel'
    },{
    	fieldLabel: '使用部位',
    	allowBlank: false,  
    	name: 'useSite'
    },{
    	fieldLabel: '新备件编码',
    	allowBlank: false,  
    	name: 'newSparePartCode'
    },{
    	fieldLabel: '替换数量',
    	allowBlank: false,  
    	name: 'quantity'
    },{
    	fieldLabel: '被替换备件编码',
    	allowBlank: false,  
    	name: 'oldSparePartCode'
    },{
    	fieldLabel: '被替换备件情况',
    	allowBlank: false,  
    	xtype : 'radiogroup',
    	name: 'oldSparePartSituation',
    	columns: 3,
    	width : 300,
        vertical: true,
        items: [
            { boxLabel: '完好', name: 'oldSparePartSituation', inputValue: '完好' },
            { boxLabel: '需修补', name: 'oldSparePartSituation', inputValue: '需修补', checked: true},
            { boxLabel: '报废', name: 'oldSparePartSituation', inputValue: '报废' }
        ]
    }]
});
