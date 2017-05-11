Ext.define('Oit.app.locale.Message',{
	override: 'Oit.locale.Message',
	statics: {
		createUserCode: '创建者',
		createTime: '创建时间',
		modifyUserCode: '更新者',
		modifyTime: '更新时间',
		bas:{
			user: {
				name: '姓名',
				tel: '手机',
				userCode: '员工号',
				password: '密码',
				status: '状态',
				freeze: '冻结',
				normal: '正常',
				blankText: '该字段不能为空'
			},
			role:{
				name: '角色名称',
				description: '描述',
				orgCode: '部门编号'
			},
			employee:{
				addForm:{
					title:"添加员工"
				},
				name: '姓名',
				tel: '联系电话',
				userCode:"员工工号",
				email:"电子邮件",
				orgCode:"所属部门",
				topOrgCode:'所属顶层机构'
			},
			resources:{
				addForm:{
					title:"新建资源"
				},
				name:'资源名称',
				uri:'资源URI',
				type:'资源类型',
				description:'资源描述'
			},
			param:{
				addForm:{
					title:"添加参数"
				},
				editForm:{
					title:"修改参数"
				},
				code:'参数号',
				name: '参数名',
				value:'参数值',
				type:'参数类型',
				description:'参数描述',
				status:'状态',
				normal: '正常',
				freeze: '冻结',
				orgCode:'数据所属组织'
			},
			dic:{
				addForm:{
					title:"添加数据"
				},
				editForm:{
					title:"编辑数据"
				},
				termsCode:'词条类型编号',
				code: '词条编号',
				name:'词条名称',
				seq:'顺序号',
				lan:'语言',
				extatt:'扩展属性',
				marks:'备注',
				status:'数据状态',
				normal: '正常',
				freeze: '冻结'
			},
			prop:{
				addForm:{
					title:"添加属性"
				},
				editForm:{
					title:"添加属性"
				},
				keyK:'键',
				valueV: '值',
				description:'描述',
				status:'状态',
				normal: '正常',
				freeze: '冻结'
			},
			mesClient: {
				addForm:{
					title:"添加系统终端"
				},
				editForm:{
					title:'修改系统终端'
				},
				clientIp: '终端IP',
				clientMac: '终端MAC',
				clientName: '终端名',
				orgCode: '数据所属组织'
			},
			mesClientManEqip: {
				addForm:{
					title:"添加信息"
				},
				editForm:{
					title:'修改信息'
				},
				mesClientId: 'MES终端',
				eqipId: '设备'
			},
			sysMessage: {
				readForm:{
					title:'查看信息'
				},
				messageTitle: '消息标题',
				messageContent: '消息内容',
				hasread: '是否已阅',
				messageReceiver: '消息接收人',
				receiveTime: '接收时间',
				readTime: '阅读时间',
				orgCode: '数据所属组织',
				yes: '是',
				no: '否'
			},
			weekCalendarShift: {
				addForm:{
					title:"添加班次"
				},
				editForm:{
					title:'修改班次'
				},
				weekCalendarId: '周工作日历',
				workShiftId: '班次',
				shiftStartTime: '班次开始时间',
				shiftEndTime: '班次结束时间',
				status: '状态',
				orgCode: '数据所属组织'
			},
			eqipCalendarShift: {
				addForm:{
					title:"添加班次"
				},
				editForm:{
					title:'修改班次'
				},
				equipCode: '设备编码',
				name: '设备名称',
				dateOfWork: '日期',
				workShiftId: '班次信息'
			}
		}
	}
});

