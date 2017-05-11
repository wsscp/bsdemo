Ext.define('bsmes.controller.EquipInformationController', {
	extend : 'Oit.app.controller.GridController',
	view : 'equipInformationList',
	editview:'equipInformationEdit',
	addview:'equipInformationAdd',
	importEqipImageWindow : 'importEqipImageWindow',
    refs : [{
        ref: 'maintainerEditWindow',
        selector: 'maintainerEditWindow',
        autoCreate: true,
        xtype: 'maintainerEditWindow'
    },{
    	ref: 'importEqipImageWindow',
        selector: 'importEqipImageWindow',
        autoCreate: true,
        xtype: 'importEqipImageWindow'
    },{
    	ref: 'equipTransferHisEdit',
        selector: 'equipTransferHisEdit',
        autoCreate: true,
        xtype: 'equipTransferHisEdit'
    }],
	views : [ 'EquipInformationList','EquipInformationEdit','EquipInformationAdd','MaintainerEditWindow','ImportEqipImageWindow','EquipEventCaseList','EquipTransferHisEdit'],
	stores : [ 'EquipInformationStore','EquipEventCaseStore','SparePartStore'],
    exportUrl:'equipInformation/export/设备信息',
    onLaunch : function() {
        var me = this;
        var grid = me.getGrid();
        grid.on('toEditMaintainer', me.toEditMaintainer, me);

        me.control('maintainerEditWindow button[itemId=checkAndAdd]', {
            click: me.addMaintainer
        });
        me.control(me.view + ' button[itemId=importImage]', {
          click: function(){
          	var view = Ext.ComponentQuery.query('equipInformationList')[0];
  			var selection = view.getSelectionModel().getSelection();
  			if(selection && selection!=''){
  				var win = me.getImportEqipImageWindow();
  				win.show();
  			}else{
  				Ext.Msg.alert(Oit.msg.PROMPT, '请选择一条数据！');
  			}
          }
      });
        me.control('maintainerEditWindow button[itemId=ok]', {
            click: function() {
                var store = Ext.ComponentQuery.query('maintainerEditWindow grid')[0].getStore();
                var data = [];
                store.each(function(record) {
                    var maintainer = record.get('maintainer');
                    var repeat = false;
                    Ext.each(data, function(item) {
                        if (item == maintainer) {
                            repeat = true;
                        }
                    });
                    if (!repeat) {
                        data.push(maintainer);
                    }
                });
                me.currentRow.set('maintainer', data.join(','));
                me.getGrid().getStore().sync();

                me.getMaintainerEditWindow().close();
            }
        });
        me.control('maintainerEditWindow button[itemId=cancel]', {
            click: function() {
                me.getMaintainerEditWindow().close();
            }
        });
        me.control('equipEventCaseList button[itemId=check]', {
            click: function() {
            	var grid = Ext.ComponentQuery.query('equipEventCaseList')[0];
            	var selections = grid.getSelectionModel().getSelection();
            	if (selections.length > 0) {
            		var row = selections[0];
            		var id = row.get('id');
            		Ext.Ajax.request({
            		    url: 'equipFaultManage/getCheckData',
            		    params: {
            		        id: id
            		    },
            		    success: function(response){
            		        var records = Ext.decode(response.responseText).eve;
            		        if(records.length == 0){
            		        	Ext.Msg.alert(Oit.msg.PROMPT, '事件未处理或处理未登记!');
            		        	return;
            		        }
            		        var win = Ext.create('Ext.window.Window', {
            		            title: '设备维修记录',
            		            width: 650,
            		        	maxHeight : 850,
            		        	layout:'fit',
            		        	height : document.body.scrollHeight-100,
            		            items: {  
            		                xtype: 'tabpanel',
            		                enableTabScroll: true, 
            		                defaults: { autoScroll: true },
            		                animScroll : true, // 使用动画滚动效果
            		                items : []
            		            }
            		        })
            		        Ext.Array.each(records, function(json, index, countriesItSelf) {
            		            var tab = win.down('tabpanel');
            		            var store = Ext.create('bsmes.store.SparePartStore');
            		            var panel = me.openRepairedRecord(json, store);
            		            tab.add(panel);
            		            if(index == 0){
            		            	tab.setActiveTab(panel);
            		            }
            		        });
            		        win.doLayout();
            		        win.show();
            		    }
            		});
            	}else{
            		Ext.Msg.alert(Oit.msg.WARN, Oit.error.noRowSelect);
    				return;
            	}
            }
        });
        
        me.control(me.view + ' button[itemId=equipTransfer]', {
            click: function() {
                me.getEquipTransferHisEdit().show();
            }
        });
        me.callParent(arguments);
    },
    toEditMaintainer : function(row) {
        this.currentRow = row;
        var data = [];
        Ext.each(row.get('maintainer').split(','), function(maintainer) {
            if (maintainer.length == 0) {
                return;
            }
            data.push(Ext.create('bsmes.model.Maintainer', {maintainer : maintainer}));
        });
        var window = this.getMaintainerEditWindow();
        Ext.ComponentQuery.query('maintainerEditWindow grid')[0].getStore().loadData(data);
        window.show();
    },
    addMaintainer : function() {
        var code = Ext.ComponentQuery.query('#maintainerField')[0].value;
        Ext.Ajax.request({
            url:'/bsmes/bas/user/checkUserCodeExists/'+ code+'/',
            method:'GET',
            async: false,
            success: function(response) {
                var exists = response.responseText == 'true';
                if (exists) {
                    var newRecord = Ext.create('bsmes.model.Maintainer', {maintainer : code});
                    Ext.ComponentQuery.query('maintainerEditWindow grid')[0].getStore().add(newRecord);
                } else {
                    Ext.Msg.alert(Oit.msg.WARN,Oit.msg.fac.equipInformation.error.checkExists);
                }
            }
        });
    },
    onFormSave: function(btn) {
        var me = this;
        var form = me.getEditForm();
        form.updateRecord();
        var record = form.getRecord();
        if (record.get('isNeedMaintain')) {
            var maintainDate = record.get('maintainDate');
            if (!maintainDate || maintainDate < 1 || maintainDate > 31) {
                Ext.Msg.alert(Oit.msg.ERROR, Oit.msg.fac.equipInformation.error.mainDateOutOfRange);
                return;
            }
            var tmplMap = record.getTmplMap();
            if (tmplMap.FIRST_CLASS && tmplMap.FIRST_CLASS.triggerType == 'NATURE') {
                if (!record.get('maintainDateFirst') || record.get('maintainDateFirst') < 1 || record.get('maintainDateFirst') > 31) {
                    Ext.Msg.alert(Oit.msg.ERROR, Oit.msg.fac.equipInformation.error.mainDateOutOfRange);
                    return;
                }
            }
            if (tmplMap.SECOND_CLASS && tmplMap.SECOND_CLASS.triggerType == 'NATURE') {
                if (!record.get('maintainDateSecond') || record.get('maintainDateSecond') < 1 || record.get('maintainDateSecond') > 31) {
                    Ext.Msg.alert(Oit.msg.ERROR, Oit.msg.fac.equipInformation.error.mainDateOutOfRange);
                    return;
                }
            }
            if (tmplMap.OVERHAUL && tmplMap.OVERHAUL.triggerType == 'NATURE') {
                if (!record.get('maintainDateOverhaul') || record.get('maintainDateOverhaul') < 1 || record.get('maintainDateOverhaul') > 31) {
                    Ext.Msg.alert(Oit.msg.ERROR, Oit.msg.fac.equipInformation.error.mainDateOutOfRange);
                    return;
                }
            }
        }
        if (form.isValid()) {
            var store = me.getGrid().getStore();
            // 同步到服务器
            store.sync();
            // 关闭窗口
            me.getEditView().close();
        }
    },
    openRepairedRecord : function(json, store, index){
    	var win = Ext.create("bsmes.view.CheckEventFlowPanel",{store : store});
    	var seq = json.seq+1;
    	win.title = '第'+seq +'次维修记录';
        var form = win.down('form').getForm();
    	var equipName;
        if(json.equipAlias != ''){
        	equipName = json.equipAlias+ '-' +json.equipName + '['+json.equipCode+']';
        }else{
        	equipName = json.equipName + '['+json.equipCode+']';
        }
        form.setValues({
        	equipName : equipName,
        	createTime : new Date(json.createTime),
        	equipModelStandard : json.equipName,
        	protectMan : json.userName,
        	startRepairTime : new Date(json.startRepairTime),
        	finishRepairTime : new Date(json.finishRepairTime),
        	repairMan : json.repairMan,
        	failureModel : json.failureModel,
        	equipTroubleDescribetion : json.equipTroubleDescribetion,
        	equipTroubleAnalyze : json.equipTroubleAnalyze,
        	repairMeasures : json.repairMeasures
        });
        var grid = win.down('grid');
        grid.getStore().load({
        	params : {
    			recordId : json.id
    		}
        })
        return win;
    }
});
