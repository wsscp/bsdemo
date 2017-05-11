Ext.define('Oit.app.locale.Message',{
	override: 'Oit.locale.Message',
	statics: {
		createUserCode: '创建者',
		createTime: '创建时间',
		modifyUserCode: '更新者',
		modifyTime: '更新时间',
		job:{
			jobLog:{
				jobName:'名称',
				jobDesc:'描述',
				hostName:'主机',
				hostAddress:'地址',
				flag:'状态',
				prevStartTime:'开始执行时间',
				prevResult:'执行结果',
				prevEndTime:'执行结束时间',
				errorMessage:'错误日志'
			}
		}
	}
});

