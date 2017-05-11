Ext.define('bsmes.view.ProcessQAEdit',{
		extend:'Oit.app.view.form.EditForm',
		alias:'widget.processQAEdit',
		title: Oit.msg.pro.processQA.title.editWindowTitle,
		iconCls: 'feed-add',
		formItems:[{
				        fieldLabel: Oit.msg.pro.processQA.qcValue,
				        name: 'qcValue',
				        xtype:'textfield',
				        readOnly:true
					},{
						fieldLabel:Oit.msg.pro.processQA.qcResult,
				        xtype:'radiogroup',
				        columns:4,
				        vertical:true,
				        items:[{boxLabel:Oit.msg.pro.processQA.pass,  name: 'qcResult',  inputValue: '通过'},
				               {boxLabel: Oit.msg.pro.processQA.noPass, name: 'qcResult', inputValue: '不通过'}]
					},{
						fieldLabel:Oit.msg.pro.processQA.checkEqipCode,
						name:'checkEqipCode',
						editor:'textfield'
					}]
});