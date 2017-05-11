Ext.define('Oit.app.locale.Message', {
			override : 'Oit.locale.Message',
			creator : '创建者',
			createTime : '创建时间',
			updator : '更新者',
			updateTime : '更新时间',
			statics : {
				fac : {
					equipInformation : {
						name : '设备名称',
						code : '设备编号',
						equipAlias : '设备别名',
						maintainer : '维修负责人',
						userCode : '员工号',
						nextMaintainDate : '月检下次维修时间',
						maintainDate : '月检固定维修日',
						nextMaintainDateFirst : '一级保养下次维修时间',
						maintainDateFirst : '一级保养固定维修日',
						nextMaintainDateSecond : '二级保养下次维修时间',
						maintainDateSecond : '二级保养固定维修日',
						nextMaintainDateOverhaul : '大修下次维修时间',
						maintainDateOverhaul : '大修固定维修日',
						type : '设备类型',
						editTitle : '设备修改',
						addTitle : '设备添加',
						orgCode : '所属组织',
						model : '设备型号',
						belongLine : '所属生产线',
						subType : '子类型',
						btn : {
							maintainerEdit : '编辑维修负责人',
							repair : '维修'
						},
						error : {
							checkExists : '员工号不存在！',
							mainDateOutOfRange : '设备维修日必须在1和31之间！'
						}
					},
					eventInformation : {
						eventTitle : '事件标题',
						eventContent : '事件内容 ',
						eventStatus : '事件状态',
						eventReason : '事件原因',
						eventResult : '事件结果',
						equipCode : '机台',
						orgCode : '组织机构',
						userName : '报修人',
						batchNo : '批次号',
						completed : '已完成',
						uncompleted : '未处理',
						responded : '已响应',
						incompleted : '处理中',
						pending : '待确认',
						zero : '一般',
						one : '急',
						two : '很急',
						three : '特急',
						productCode : '产品名称',
						pendingProcessing : '是否拦截在制品',
						name : '类型名称',
						processType : '处理方式',
						createTime : '事件发生时间',
						responseTime : '事件响应时间',
						completeTime : '事件完成时间',
						search : '查看',
						view : '事件信息查看',
						edit : '事件信息编辑',
						processSeq : '事件紧急度',
						responsible : '责任人'
					},
					maintainItem : {
						tempId : '点检模版ID',
						describe : '项目说明',
						addForm : {
							title : '添加项目'
						},
						editForm : {
							title : '编辑项目'
						}
					},
					maintainTemplate : {
						model : '设备型号',
						orgCode : '数据所属组织',
						triggerCycle : '触发周期',
						triggerCycle_m : '触发周期(月)',
						triggerCycle_h : '触发周期(小时)',
						triggerType : '触发类型',
						type : '模版类型',
						unit : '单位',
						describe : '点检说明',
						addForm : {
							title : '添加模版'
						},
						editForm : {
							title : '编辑模版'
						},
						btn : {
							editItem : '编辑维修项目'
						}
					},
					maintainRecord : {
						createUserCode : '检修人员',
						startTime : '维修开始时间',
						finishTime : '维修完成时间',
						equipCode : '设备编码',
						status : '状态',
						addForm : {
							title : 'TODO'
						},
						editForm : {
							title : 'TODO'
						},
						btn : {
							complete : '完成',
							add : '检修',
							dailyAdd : '点检',
							firstClassAdd : '一级保养',
							secondClassAdd : '二级保养',
							overhaulAdd : '大修',
							monthlyAdd : '月检'
						}
					},
					maintainRecordItem : {
						isPassed : '是否通过检查',
						recordId : '点检表',
						value : '测量值',
						remarks : '备注',
						describe : '项目说明',
						itemId : '点检项目',
						addForm : {
							title : 'TODO'
						},
						editForm : {
							title : '编辑值'
						}
					},
					equipMaintenance : {
						equipCode : '设备代码',
						equipName : '设备名称',
						eventContent : '事件描述',
						createTime : '事件创建时间',
						responseTime : '响应时间',
						responsed : '响应人',
						responseTimes : '响应时长(分钟)',
						completeTime : '完成时间',
						complete : '完成人',
						status : '事件状态',
						completeTimes : '完成时长(分钟)',
						type : {
							completed : '已完成',
							uncompleted : '未完成',
							responded : '已响应',
							all : '全部'
						}
					},
					shunDownAnalysis : {
						productName : '产品',
						equipCode : '生产线代码',
						equipName : '生产线名称',
						reason : '停机原因',
						startTime : '停机开始时间',
						endTime : '停机结束时间',
						title : '停机原因修改',
						isCompleted : '是否已完成',
						timeBet : '停机时间间隔(分钟)',
						all : '所有',
						yes : '是',
						no : '否'
					},
					shunDownStatistics : {
						equipCode : '生产线',
						startTime : '开始时间',
						endTime : '结束时间',
						resert : '重置'
					},
					dailyCheck : {
						equipCode : '设备编码',
						startTime : '维修开始时间',
						finishTime : '维修完成时间',
						describe : '项目说明',
						value : '测量值',
						isPassed : '是否通过检查',
						remarks : '备注',
						status : '维护状态',
						inProgress : '未完成',
						finished : '已完成'
					}
				}
			}
		});
