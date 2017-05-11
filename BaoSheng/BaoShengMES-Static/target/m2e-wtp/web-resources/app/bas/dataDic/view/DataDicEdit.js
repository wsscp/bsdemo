Ext.define('bsmes.view.DataDicEdit', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.dataDicEdit',
	title: Oit.msg.bas.dic.editForm.title,
	/*iconCls: 'feed-add',*/
	formItems: [{
        fieldLabel: Oit.msg.bas.dic.termsCode,
        name: 'termsCode',
        xtype: 'displayfield'
    },
	{
        fieldLabel: Oit.msg.bas.dic.termsName,
        name: 'termsName',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.bas.dic.code,
        name: 'code',
        xtype: 'displayfield'
    },{
        fieldLabel: Oit.msg.bas.dic.name,
        name: 'name'
    },{
        fieldLabel: Oit.msg.bas.dic.seq,
        name: 'seq'
    } ,{
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
        items:[{boxLabel: Oit.msg.bas.dic.normal, name: 'status', inputValue: '1',id:'status-2000'},
               {boxLabel: Oit.msg.bas.dic.freeze, name: 'status', inputValue: '0',id:'status-2001'}]
    }] 
});

