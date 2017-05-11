Ext.define("bsmes.view.InvLocationWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.invLocationWindow',
	width:document.body.scrollWidth-100,
    height:document.body.scrollHeight-100,
	title: '库位信息',
	items : [ {
		xtype : 'grid',
		//forceFit : false,
        height:document.body.scrollHeight-187,
		store : 'InvLocationStore',
		columns : [{
			text : '物料代码',
			dataIndex : 'materialCode',
			flex:2
		},{
			text : '条码',
			dataIndex : 'serialNum',
			flex:3
		},{
			text : '库位',
			dataIndex : 'locationName',
			flex:1
		}]
	}],
	buttons:[{
        text:Oit.btn.close,
        handler: function(){
            this.up('window').close();
        }
    }]
});