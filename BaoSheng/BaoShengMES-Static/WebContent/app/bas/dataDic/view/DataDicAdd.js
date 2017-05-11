Ext.define('bsmes.view.DataDicAdd', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.dataDicAdd',
	title: Oit.msg.bas.dic.addForm.title,
	/*iconCls: 'feed-add',*/
	formItems: [{
        fieldLabel: Oit.msg.bas.dic.termsCode,
        name: 'termsCode',
        xtype:'combobox',
        queryMode: 'local',
        allowBlank:false,
        displayField:'termsName',
        valueField: 'termsCode',
        store:new Ext.data.Store({
        	fields:['termsCode','termsName'],
        	autoLoad:true,
        	proxy:{
        		type: 'rest',
        		url:'dataDic/getTermsCode'
        	}
        })
    },{
        fieldLabel: Oit.msg.bas.dic.code,
        name: 'code',
        allowBlank:false
    },{
        fieldLabel: Oit.msg.bas.dic.name,
        name: 'name',
        allowBlank:false
    },{
        fieldLabel: Oit.msg.bas.dic.seq,
        name: 'seq',
        allowBlank:false
    }, {
        fieldLabel: Oit.msg.bas.dic.extatt,
        name: 'extatt'
    },{
        fieldLabel: Oit.msg.bas.dic.marks,
        name: 'marks'
    },{
        fieldLabel: Oit.msg.bas.dic.status,
        xtype:'radiogroup',
        columns:4,
        vertical:true,
        items:[{boxLabel: Oit.msg.bas.dic.normal, name: 'status', inputValue: '1',checked:true},
               {boxLabel: Oit.msg.bas.dic.freeze, name: 'status', inputValue: '0'}]
    }] 
});
