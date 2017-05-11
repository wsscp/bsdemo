Ext.define("bsmes.view.RepairTypeWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.repairTypeWindow',
	layout:'fit',
	width:document.body.scrollWidth-200,
    height:document.body.scrollHeight-200,
    modal : true,
	plain : true,
	title: '修复方式',
	initComponent: function() {
		var me = this;
		
		me.items = [{
			xtype: 'form',
			bodyPadding: '5 50',
			autoScroll:true, 
			fieldDefaults: {  // 设置field的样式
	            msgTarget: 'side', // 错误信息提示位置
	            labelWidth: 150,  // 设置label的宽度
	            labelStyle: 'font-size:30px; margin-top:20px;'
	        },
		    url: '../sparkRepair/updateSparkRepair',
		    items: [{
				xtype: 'hiddenfield',
				itemId: 'id',
			    name: 'id'
			},{
		    	fieldLabel: '修复方式',
		        itemId: 'repairTypeComb',
		        name: 'repairType',
		        xtype: 'radiogroup',  
                columns: 2, // 一行显示几个
                allowBlank: false,
                layout: { autoFlex: true }, // 设置自动间距样式为false
                items: [],  
                listeners:{  
                    render:function(view, opt){  
                        me.initTypeComb(me, this, view, opt);
                    }
                },
				margin: '30 30 30 30'
			}]
		}];
		
		Ext.apply(me, {
			buttons: [{
				itemId: 'ok',
				text:Oit.btn.ok
			},'->', {
				itemId: 'cancel',
				text:Oit.btn.cancel,
				scope: me,
				handler: me.close
			}]
		});
		
		this.callParent(arguments);
		
	},
	
	/**
	 * 初始radioGroup组件
	 * @param me 整个弹出框
	 * @param group radioGroup组件组
	 * @param view radio组件
	 * @param opt radio组件check值
	 * 
	 * */
	initTypeComb: function(me, group, view, opt){
		Ext.Ajax.request({  
            url:'../sparkRepair/repairType',//请求路径，需要手动输入指定  
            success: function(response){  
            	var result = Ext.decode(response.responseText);
            	var items = [];
                for(var j=0;j<result.length;j++){  
                	items[j] = new Ext.form.Radio({  
                        boxLabel: result[j].name,   
                        inputValue: result[j].code, 
                        name: 'repairType', //这个是后台接收的表单域，这里的checkboxgroup都使用同一个name 
                        boxLabelCls: 'x-boxlabel-size-30 x-boxlabel-left-10',
                        fieldStyle: 'margin-top:5px;'
                    });  
                }  
                group.items.addAll(items);
                group.doLayout(); 
            }  
        })
	}
	
});

