Ext.define('bsmes.view.CheckResume', {
	extend: 'Oit.app.view.form.EditForm',
	alias: 'widget.checkResume',
	title: '简历查看',
	formItems: [{
		xtype : 'fieldset',
		title: '员工信息',
		collapsible: true,
		layout: 'form',
	    items : [{
	    	xtype : 'hiddenfield',
	    	name : 'userCode'
	    },{
	    	layout : 'column',
	    	items : [{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '姓名',
	    		name : 'userName'
	    	},{
	    		columnWidth: 0.3,
	    		xtype : 'displayfield',
	    		fieldLabel: '性别',
	    		name : 'gender'
	    	},{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '婚姻状况',
	    		name : 'maritalStatus'
	    	}]
	    },{
	    	layout : 'column',
	    	items : [{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '籍贯',
	    		name : 'originPlace'
	    	},{
	    		columnWidth: 0.3,
	    		xtype : 'displayfield',
	    		fieldLabel: '出生地',
	    		name : 'birthPlace'
	    	},{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '户口性质',
	    		name : 'accountProperties'
	    	}]
	    },{
	    	layout : 'column',
	    	items : [{
	    		columnWidth: 0.65,
	    		xtype : 'displayfield',
	    		fieldLabel: '家庭住址',
	    		name : 'homeAddress'
	    	},{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '政治面貌',
	    		name : 'politicalClimate'
	    	}]
	    },{
	    	layout : 'column',
	    	items : [{
	    		columnWidth: 0.65,
	    		xtype : 'displayfield',
	    		fieldLabel: '身份证号',
	    		name : 'idNumbers'
	    	},{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '出生日期',
	    		name : 'birthDate'
	    	}]
	    },{
	    	layout : 'column',
	    	items : [{
	    		columnWidth: 0.65,
	    		xtype : 'displayfield',
	    		fieldLabel: '手机号码',
	    		name : 'phoneNumber'
	    	},{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '入职日期',
	    		name : 'entryDate'
	    	}]
	    },{
	    	layout : 'column',
	    	items : [{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '入学时间',
	    		name : 'admissionDate'
	    	},{
	    		columnWidth: 0.3,
	    		xtype : 'displayfield',
	    		fieldLabel: '毕业时间',
	    		name : 'graduationDate'
	    	},{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '学历',
	    		name : 'education'
	    	}]
	    },{
	    	layout : 'column',
	    	items : [{
	    		columnWidth: 0.65,
	    		xtype : 'displayfield',
	    		fieldLabel: '毕业院校',
	    		name : 'school'
	    	},{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '学位',
	    		name : 'degree'
	    	}]
	    },{
	    	layout : 'column',
	    	items : [{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield',
	    		fieldLabel: '学习方式',
	    		name : 'studyWay'
	    	},{
	    		columnWidth: 0.3,
	    		xtype : 'displayfield',
	    		fieldLabel: '是否航空院校',
	    		name : 'isAviationCollege'
	    	},{
	    		columnWidth: 0.35,
	    		xtype : 'displayfield'
	    	}]
	    },{
	    	layout : 'form',
	    	items : [{
	    		xtype : 'textareafield',
	    		fieldLabel: '备注',
	    		name : 'remarks',
		        labelWidth : 60,
		        grow      : true,
		        readOnly : true,
		        autoWidth: true,
		        labelAlign : 'top'
	    	}]
	    }]
	},{
		xtype : 'fieldset',
		title: '员工履历',
		collapsible: true,
		defaultType: 'displayfield',
		layout: 'fit',
		items :[{
			xtype : 'grid',
			store : new Ext.data.Store({
				fields : [ {name: 'recordDate', type: 'string'},
					         {name: 'recordDetail',  type: 'string'},
					         {name : 'id',type : 'string'}],
				autoLoad : false,
				proxy : {
					type : 'rest',
					url : 'user/getUserResumeInfo'
				}
			}),
			columns : [{
				text : '时间',
				flex : 2,
				dataIndex : 'recordDate'
			},{
				text : '履历明细',
				flex : 8,
				dataIndex : 'recordDetail'
			}],
			listeners : {
				'itemdblclick' : function(cop,record,item,index,e,eOpts ){
					var win = Ext.create('bsmes.view.AddEmployeeResume',{
						title: '查看履历'
					});
					win.down('form').getForm().setValues({
						recordDate : record.get('recordDate'),
						recordDetail : record.get('recordDetail')
					});
					win.show();
				}
			},
			tbar : [ {
				itemId : 'add',
				text : '添加',
				iconCls : 'icon_add',
				handler : function(){
					var me = this;
					var userCode = me.up('form').getForm().findField('userCode').getValue();
					var win = Ext.create('bsmes.view.AddEmployeeResume',{
						title: '添加履历'
					});
					win.down('form').getForm().setValues({
						userCode : userCode
					});
					win.show();
				}
			},{
				itemId : 'remove',
				text : '删除',
				iconCls : 'icon_remove',
				handler : function(){
					var grid = this.up('grid');
					var selection = grid.getSelectionModel().getSelection();
					if(selection && selection != ''){
						var id = selection[0].get('id');
						Ext.Ajax.request({
						    url: 'user/deleteEmployeeResume',
						    params: {
						    	id: id
						    },
						    success: function(response){
						    	var result = Ext.decode(response.responseText);
						    	grid.getStore().reload();
								Ext.Msg.alert(Oit.msg.WARN, result.message);
						        
						    }
						});
					}else{
						Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条数据！');
					}
				}
			}]
		}]
	},{
		xtype : 'fieldset',
		title: '家庭信息',
		collapsible: true,
		defaultType: 'displayfield',
		layout: 'fit',
	    items : [{
	    	xtype : 'grid',
	    	store : new Ext.data.Store({
				fields : [ {name: 'relationShip', type: 'string'},
					         {name: 'name',  type: 'string'},
					         {name: 'gender',       type: 'string'},
					         {name: 'workUnit',  type: 'string'},
					         {name: 'birthDate', type: 'string'},
					         {name: 'phoneNumber',  type: 'string'}],
				autoLoad : false,
				proxy : {
					type : 'rest',
					url : 'user/getFamilyInfo'
				}
			}),
	    	columns : [{
	    		text : '与本人关系',
	    		dataIndex : 'relationShip'
	    	},{
	    		text : '姓名',
	    		dataIndex : 'name'
	    	},{
	    		text : '出生年月',
	    		dataIndex : 'birthDate'
	    	},{
	    		text : '工作单位',
	    		dataIndex : 'workUnit'
	    	},{
	    		text : '联系方式',
	    		dataIndex : 'phoneNumber'
	    	},{
	    		text : '性别',
	    		dataIndex : 'gender'
	    	}]
	    }]
	}] 
});