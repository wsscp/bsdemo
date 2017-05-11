Ext.define("bsmes.view.AmoebaList", {
	extend : 'Oit.app.view.Grid',
	alias : 'widget.amoebaList',
	store : 'AmoebaStore',
	defaultEditingPlugin : false,
	forceFit : false,
	columns : [{
			text : '截面',
			dataIndex : 'col00'
		},{
			text : '类型',
			dataIndex : 'col01'
		}, {
			text : '规格',
			dataIndex : 'col02'
		}, {
			text : '线芯外径',
			dataIndex : 'col03'
		} , {
			text : '线芯重量',
			dataIndex : 'col04'
		} , {
			text : '铜(锡)材单价',
			dataIndex : 'col05'
		} , {
			text : '铜(锡)价格',
			dataIndex : 'col06'
		} , {
			text : '绝缘料',
			dataIndex : 'col07'
		} , {
			text : '绝缘厚度',
			dataIndex : 'col08'
		} , {
			text : '绝缘料用量',
			dataIndex : 'col09'
		} , {
			text : '绝缘料单价',
			dataIndex : 'col10'
		} , {
			text : '绝缘料价格',
			dataIndex : 'col11'
		} , {
			text : '厂房设备折旧',
			dataIndex : 'col12'
		} , {
			text : '水.电.气',
			dataIndex : 'col13'
		} , {
			text : '福利劳保',
			dataIndex : 'col14'
		} , {
			text : '管理,仓库物流',
			dataIndex : 'col15'
		} , {
			text : '维修,机物耗',
			dataIndex : 'col16'
		} , {
			text : '尼龙护套厚度',
			dataIndex : 'col17'
		} , {
			text : '尼龙料用量',
			dataIndex : 'col18'
		} , {
			text : '尼龙价格',
			dataIndex : 'col19'
		} , {
			text : '尼龙料金额',
			dataIndex : 'col20'
		}, {
			text : '合计生产费用',
			dataIndex : 'col30'
		} ],
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
					xtype : 'hform',
					items : [
					         {
								fieldLabel : '截面',
								name : 'col01'
							}, {
								fieldLabel : '类型',
								name : 'col02'
							}
							]
				}]
	}],
	actioncolumn : ['',{
		itemId : 'edit'
	},''],
	tbar : [
    /* {
		itemId : 'add'
	}, */ {
		itemId : 'remove'
	},{
		itemId : 'search'
	}, {
		itemId : 'importData',
		text : '导入数据'
	}]
});
