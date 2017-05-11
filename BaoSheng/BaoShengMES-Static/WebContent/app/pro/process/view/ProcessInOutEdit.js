Ext.define('bsmes.view.ProcessInOutEdit', {
	extend : 'Oit.app.view.form.EditForm',
	alias : 'widget.processInOutEdit',
	title : Oit.msg.pro.processInOut.processInOutEditText,
	iconCls : 'feed-edit',
	formItems : [ {
		fieldLabel : Oit.msg.pro.processInOut.matCode,
		xtype : 'displayfield',
		name : 'matCode'
	}, {
		fieldLabel : Oit.msg.pro.processInOut.inOrOut,
		xtype : 'displayfield',
		name : 'inOrOut',
		renderer:function(value,object){
			if (value == 'IN') {
				return Oit.msg.pro.processInOut.inType;
			} else if(value == 'OUT'){
				return Oit.msg.pro.processInOut.outType;
			}else {
				return '';
			}
		}
	}, {
		fieldLabel : Oit.msg.pro.processInOut.quantity,
		xtype : 'numberfield',
		name : 'quantity'
	}, {
		fieldLabel : Oit.msg.pro.processInOut.quantityFormula,
		name : 'quantityFormula'
	}, {
		fieldLabel : Oit.msg.pro.processInOut.unit,
		xtype : 'radiogroup',
		columns : 4,
		items : [ {
			boxLabel : Oit.unitType.ton,
			name : 'unit',
			inputValue : 'TON',
			checked : true
		}, {
			boxLabel : Oit.unitType.kg,
			name : 'unit',
			inputValue : 'KG'
		}, {
			boxLabel : Oit.unitType.km,
			name : 'unit',
			inputValue : 'KM'
		}, {
			boxLabel : Oit.unitType.m,
			name : 'unit',
			inputValue : 'M'
		} ]
	}, {
		fieldLabel : Oit.msg.pro.processInOut.useMethod,
		name : 'useMethod'
	}]
});