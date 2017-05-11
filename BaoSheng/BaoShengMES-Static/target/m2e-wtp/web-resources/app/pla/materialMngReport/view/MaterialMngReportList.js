Ext.define("bsmes.view.MaterialMngReportList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.materialMngReportList',
	store : 'MaterialMngReportStore',
	defaultEditingPlugin : false,
	columns : [{
		text : '物料代码',
		flex : 1,
		dataIndex : 'matCode'
	},{
		text : '物料名称',
		flex : 2.5,
		dataIndex : 'matName',
		renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
			if(record.get('inAttrDesc') != ''){
				var json = Ext.decode(record.get('inAttrDesc'));
			}else{
				var json = {};
			}
			var matDesc = '';
			var a = false;
			if (json.kuandu) { // 宽度
				matDesc += '宽度:' + json.kuandu + ',';
				a = true;
			}
			if (json.color) { // 颜色
				matDesc += '<font color="red">颜色:' + json.color + '</font>,'
				a = true;
			}
			if (json.houdu) { // 厚度
				matDesc += '厚度:' + json.houdu + ',';
				a = true;
			}
			if (json.caizhi) { // 材质
				matDesc += '材质:' + json.caizhi + ',';
				a = true;
			}
			if (json.chicun) { // 尺寸
				matDesc += '尺寸:' + json.chicun + ',';
				a = true;
			}
			if (json.guige) { // 规格
				matDesc += '规格:' + json.guige + ',';
				a = true;
			}
			if (json.dansizhijing) { // 单丝直径
				matDesc += '单丝直径:' + json.dansizhijing + ',';
				a = true;
			}
			if(a){
				value = value + '(' + matDesc.substring(0,matDesc.lastIndexOf(',')) + ')' + '(KG)';
			}else{
				value = value+ '(KG)';
			}
            return value;
        }
	},{
		text : '要料量(KG)',
		flex : 1,
		dataIndex : 'yaoLiaoQuantity'
	},{
		text : '发料量(KG)',
		flex : 1,
		dataIndex : 'faLiaoQuantity'
	},{
		text : '需求时间',
		flex : 1,
		dataIndex : 'demandTime'
	}],
	dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
        items: [{
            xtype : 'form',
            layout : 'column',
            items : [{
            	fieldLabel : '物料编码',
            	xtype : 'textfield',
            	name : 'matCode'
            },{
            	fieldLabel : '需求时间',
            	xtype : 'datefield',
            	name : 'demandTime',
            	format : 'Y-m-d',
				value : Ext.util.Format.date(new Date('2016-04-03'), "Y-m-d")
            }]
        },{
        	itemId : 'search',
        	handler : function(){
        		var grid = this.up('grid');
        		var form = grid.down('form').getForm();
        		var params = form.getValues();
				grid.getStore().load({params : params});
        	}
        },{
        	itemId : 'reset',
        	text : '重置',
        	handler : function(e) {
        		var grid = this.up('grid');
        		grid.down('form').getForm().reset();
			}
        }]
    }]
})