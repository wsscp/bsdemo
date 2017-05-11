Ext.define('bsmes.view.AmoebaEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.amoebaEdit',
	id:'amoebaEdit',
	title: '修改数据',
	formItems: [{
			fieldLabel : '截面',
	        xtype: 'displayfield',
			name : 'col00'
		},{
			fieldLabel : '类型',
	        xtype: 'displayfield',
			name : 'col01'
		}, {
			fieldLabel : '规格',
	        xtype: 'displayfield',
			name : 'col02'
		}, {
			fieldLabel : '线芯外径',
	        xtype: 'displayfield',
			name : 'col03'
		} , {
			fieldLabel : '线芯重量',
			name : 'col04',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCal('col04','col05','col06');
			}}
		} , {
			fieldLabel : '铜(锡)材单价',
			name : 'col05',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCal('col04','col05','col06');
			}}
		} , {
			fieldLabel : '铜(锡)价格',
	        xtype:'displayfield',
			name : 'col06'
		} , {
			fieldLabel : '绝缘料',
	        xtype: 'displayfield',
			name : 'col07'
		} , {
			fieldLabel : '绝缘厚度',
			name : 'col08'
		} , {
			fieldLabel : '绝缘料用量',
			name : 'col09',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCal('col09','col10','col11');
			}}
		} , {
			fieldLabel : '绝缘料单价',
			name : 'col10',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCal('col09','col10','col11');
			}}
		} , {
			fieldLabel : '绝缘料价格',
	        xtype:'displayfield',
			name : 'col11'
		} , {
			fieldLabel : '厂房设备折旧',
			name : 'col12',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCalTotal();
			}}
		} , {
			fieldLabel : '水.电.气',
			name : 'col13',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCalTotal();
			}}
		} , {
			fieldLabel : '福利劳保',
			name : 'col14',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCalTotal();
			}}
		} , {
			fieldLabel : '管理,仓库物流',
			name : 'col15',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCalTotal();
			}}
		} , {
			fieldLabel : '维修,机物耗',
			name : 'col16',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCalTotal();
			}}
		} , {
			fieldLabel : '尼龙护套厚度',
			name : 'col17'
		} , {
			fieldLabel : '尼龙料用量',
			name : 'col18',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCal('col18','col19','col20');
			}}
		} , {
			fieldLabel : '尼龙价格',
			name : 'col19',
			listeners: { 
				'change':function(){
					var editWin = Ext.getCmp('amoebaEdit');
					editWin.doCal('col18','col19','col20');
			}}
		} , {
			fieldLabel : '尼龙料金额',
	        xtype:'displayfield',
			name : 'col20'
		}, {
			fieldLabel : '合计生产费用',
	        xtype:'displayfield',
			name : 'col30'
		}],
		doCal: function(col1,col2,col3) {
			var me = this;
			var form = this.down('form').getForm();
			var colA = form.findField(col1);
			var colB = form.findField(col2);
			var colC = form.findField(col3);
			colC.setValue((1*colA.getValue()*colB.getValue()).toFixed(3));
			this.doCalTotal();
		},
		doCalTotal: function() {
			var me = this;
			var form = this.down('form').getForm();
			var colA = form.findField('col06');
			var colB = form.findField('col11');
			var colC = form.findField('col20');
			var col1 = form.findField('col12');
			var col2 = form.findField('col13');
			var col3 = form.findField('col14');
			var col4 = form.findField('col15');
			var col5 = form.findField('col16');
			var colT = form.findField('col30');
			var myTotal = 1*colA.getValue()
				+1*colB.getValue()
				+1*colC.getValue()
				+1*col1.getValue()
				+1*col2.getValue()
				+1*col3.getValue()
				+1*col4.getValue()
				+1*col5.getValue();
//			colT.setValue(myTotal.toFixed(3));
			colT.setValue(myTotal.toFixed(3));
		}
});

