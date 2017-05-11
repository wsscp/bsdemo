Ext.define('Oit.locale.Message',{
	statics: {
		ERROR: '错误',
		WARN: '警告',
        PROMPT:'提示',
        LOADING:'正在处理...',
        createUserCode: '创建者',
        createTime: '创建时间',
        modifyUserCode: '更新者',
        modifyTime: '更新时间',
        orgCode: '所属组织',
        exit : '退出',
        pass:'通过',
        noPass:'不通过',
		error: {
			noRowSelect: '请选择一条数据！',
			handled: '已处理！',
			network: '网络错误，请检查后重试！'
		},
        boolean:{
            YES:'是',
            NO:'否'
        },
		button: {
			search: '查找',
			detail: '详细',
			back:'返回',
			save: '保存',
			add: '添加',
			remove: '删除',
			edit: '编辑',
			ok: '确定',
			cancel: '取消',
			close:'关闭',
			reset:'重置',
			export:'导出为xls',
            back : '返回',
            print:'打印'
        },
        dataType:{
        	boolean:'布尔',
        	string:'字符',
        	number:'数值'
        },
        unitType:{
        	ton:'吨',
        	kg:'千克',
        	km:'千米',
        	m:'米'
        }
	}
});

Oit.msg = Oit.locale.Message;
Oit.error = Oit.locale.Message.error;
Oit.btn = Oit.locale.Message.button;
Oit.dataType = Oit.locale.Message.dataType;
Oit.unitType = Oit.locale.Message.unitType;
Oit.boolean = Oit.locale.Message.boolean;