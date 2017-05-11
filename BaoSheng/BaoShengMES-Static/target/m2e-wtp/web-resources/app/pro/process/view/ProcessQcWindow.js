Ext.define('bsmes.view.ProcessQcWindow',{
		extend:'Ext.window.Window',
		alias:'widget.processQcWindow',
		title:' 产品QA检测项维护',
		width:document.body.scrollWidth-100,
		height:document.body.scrollHeight-100,
		modal : true,
		plain : true,
		requires:[
		          'bsmes.view.ProcessQcList'
		          ],
		items:[{
					xtype:'processQcList'
		}]
});