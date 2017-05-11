/**
 * Created by chanedi on 14-3-3.
 */
Ext.define("bsmes.view.SignView",{
    extend : 'Ext.window.Window',
    alias : 'widget.signView',
    title: Oit.msg.wip.terminal.btn.sign,
    width: 600,
    height:300+Ext.fly('maxClient').getAttribute("value")*30,
    modal: true,
    defaults:{
        style:"font-size:20px;"
    },
    padding:'5 5 5 5',
	initComponent: function () {
	    	var me = this;
	    	var workShiftItems = [];

            var comBoxItem = [];
            var maxClient = Ext.fly('maxClient').getAttribute("value");
            for(var i=0;i<maxClient;i++){
                comBoxItem.push({
                    boxLabel : Ext.get('equip'+i).getAttribute('equipAlias')+'['+Ext.get('equip'+i).getAttribute('equipCode')+']',
                    inputValue:Ext.get('equip'+i).getAttribute('equipCode'),
                    name : 'equipCodes',
                    margin:'0 0 10 0'
                });
            }

	    	//加载班次
	    	Ext.Ajax.request({
	    		url:'terminal/loadTodayWorkShifts',
				method:'GET',
				async: false,
				success: function(response) {
					var workShifts = Ext.decode(response.responseText);
					var checkOne = true;
					Ext.Array.each(workShifts,function(workShift,i){
                        workShiftItems.push({
			    			boxLabel: workShift.shiftStartTime+'~'+workShift.shiftEndTime, 
			    			name: 'shiftId', 
			    			inputValue: workShift.id,
			    			checked:checkOne
			    		});
			    		checkOne = false;
			    	});
				}
	    	});

	    	me.items = [{
                xtype:'form',
                items:[{
                    xtype:'label',
                    text:Oit.msg.wip.terminal.userCode,
                    padding:'20 20 0 20'
                },{
                    xtype:'textfield',
                    name:'userCode',
                    itemId:'employeeCode',
                    width:450,
                    height:30,
                    padding:'10 20 0 20',
                    allowBlank:false,
                    blankText:Oit.msg.wip.terminal.error.userCodeIsNotNull,
                    enableKeyEvents:true,
                    validateOnChange:false,
                    plugins:{
                        ptype:'virtualKeyBoard'
                    },
                    listeners:{
                        specialKey : function(field, e) {
                            if (e.getKey() == Ext.EventObject.ENTER) {//响应回车
                                //Ext.getCmp('virtualKeyBoardPanel').hide();
                                Ext.ComponentQuery.query('#virtualKeyBoardPanel')[0].setValue('');
                            }
                        },
                        'change':function( me, newValue, oldValue, eOpts ){
                            if(newValue == ''){
                                return;
                            }
                            if(newValue.length <4){
                                return;
                            }
                            var exceptionTypeObject = Ext.ComponentQuery.query('#exceptionType')[0];
                            var  signEquipCodes = Ext.ComponentQuery.query('#signEquipCodes')[0];
                            var  signEquipLabel = Ext.ComponentQuery.query('#signEquipLabel')[0];
                            var workShiftObject = Ext.ComponentQuery.query('#shiftId')[0];
                            Ext.Ajax.request({
                                url:'terminal/loadUserSingViewData/'+newValue+'/',
                                method:'GET',
                                success: function(response) {
                                    var data = Ext.decode(response.responseText);
                                    var checkOne = true;
                                    var recordExists = data.recordExists;
                                    if(!recordExists){
                                        signEquipCodes.show();
                                        signEquipCodes.allowBlank = false;
                                        signEquipLabel.show();
                                    }else{
                                        signEquipCodes.hide();
                                        signEquipLabel.hide();
                                        signEquipCodes.allowBlank = true;
                                    }
                                    var workShift = data.workShift;
                                    if(recordExists && workShift != null){
                                            workShiftObject.removeAll();
                                            workShiftObject.add([{
                                                boxLabel: workShift.shiftStartTime+'~'+workShift.shiftEndTime,
                                                name: 'shiftId',
                                                inputValue: workShift.id,
                                                checked:checkOne
                                            }]);
                                    }else{
                                        workShiftObject.removeAll();
                                        workShiftObject.add(workShiftItems);
                                    }

                                    var creditCardTypes = data.creditCardTypes;
                                    var typeArray = new Array();
                                    Ext.Array.each(creditCardTypes,function(type,i){
                                        if(type.code != 'ON_WORK'){
                                            if(recordExists){
                                                typeArray.push({
                                                    boxLabel: type.name,
                                                    name: 'exceptionType',
                                                    inputValue: type.code,
                                                    checked:checkOne
                                                });
                                                checkOne = false;
                                            }
                                        }else{
                                        	if(!recordExists){
                                    			typeArray.push({
                                    				boxLabel: type.name,
                                    				name: 'exceptionType',
                                    				inputValue: type.code,
                                    				checked:checkOne
                                    			});
                                    		}
                                        }
                                    });
                                    exceptionTypeObject.removeAll();
                                    exceptionTypeObject.add(typeArray);
                                    me.validateOnChange = true;
                                    Ext.getCmp('signViewOkBtn').show();
                                },
                                failure:function(response){
                                    var data = Ext.decode(response.responseText);
                                    workShiftObject.removeAll();
                                    exceptionTypeObject.removeAll();
                                    me.validateOnChange = false;
                                    Ext.getCmp('signViewOkBtn').hide();
                                    if(data.msg){
                                        Ext.Msg.alert(Oit.msg.WARN, data.msg);
                                    }
                                }
                            });
                        }
                    }
                },{
                    xtype:'label',
                    text:Oit.msg.wip.terminal.cardType,
                    padding:'20 20 0 20'
                },{
                    xtype:'radiogroup',
                    id:'exceptionType',
                    vertical:true,
                    padding:'10 20 0 20',
                    columns:5,
                    width:450
                },{
                    xtype:'label',
                    text:'班次',
                    padding:'20 20 0 20'
                },{
                    xtype:'radiogroup',
                    id:'shiftId',
                    columns:3,
                    padding:'10 20 0 20'
                    //items:workShiftItems
                },{
                    xtype:'label',
                    text:'设备',
                    padding:'20 20 0 20',
                    id:'signEquipLabel'
                },{
                    xtype: 'checkboxgroup',
                    width: 500,
                    columns: 2,
                    padding:'10 20 0 20',
                    id:'signEquipCodes',
                    vertical: true,
                    items: comBoxItem
                }]
            }];
	    	me.callParent(arguments); // ------------call父类--------------
	},
    buttons:[{
    	itemId:'ok',
        xtype:'button',
        id:'signViewOkBtn',
        text:Oit.btn.ok,
        hidden:true
    },'->',{
        xtype:'button',
        text:Oit.btn.close,
        handler:function(){
            this.up('window').close();

        }
    }]
});