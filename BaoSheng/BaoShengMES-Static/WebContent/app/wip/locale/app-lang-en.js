Ext.define('Oit.app.locale.Message',{
	override: 'Oit.locale.Message',
	statics: {
		creator: '创建者',
		createTime: '创建时间',
		updator: '更新者',
		updateTime: '更新时间',
		wip :{
			workOrder:{
				workOrderNO:'生产单号',
				oaDate:'订单最迟完工日期',
				latestStartDate:'OA开始时间',
				latestFinishDate:'OA结束时间',
				processName:'工序',
				orgName:'单位',
				customerContractNO:'客户合同号',
				productSpec:'产品规格',
				productType:'产品型号',
				preStartTime:'预计开始日期',
				preEndTime:'预计结束日期',
				shift:'班次',
				preStartTimeFrom:'预计开始日期  从',
				preStartTimeTo:'到',
				equipName:'机台',
				status:'生产状态',
				orderLength:'总任务数',
				cancelLength:'取消数量',
				auditTime:'审核日期',
				color:'颜色',
				percent:'完成进度',
				reqTec:'技术要求',
				remarks:'备注',
				addForm:{
					title:'新增生产单'
				}
			},
			btn:{
				audit:'审核通过',
				cancel:'任务取消',
				adjustOrder:'调整加工顺序',
				printOrder:'打印任务单',
				resourceGantt:'资甘特图',
				logisticsRequest:'后勤需求'
			},
			msg:{
				auditError:'已经审核通过不需要再审核!'
			}
		}
	}
});

