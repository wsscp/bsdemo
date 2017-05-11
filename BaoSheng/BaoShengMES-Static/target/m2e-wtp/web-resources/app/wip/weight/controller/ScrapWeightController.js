Ext.define('bsmes.controller.ScrapWeightController', {
	extend : 'Ext.app.Controller',
	weightForm : 'scrapWeightForm',
	views : [ 'ScrapWeightForm'],
	//stores : [ 'ScrapWeightStore'],
	constructor: function() {
		var me = this;
		// 初始化refs
		me.refs = me.refs || [];
		
		me.refs.push({
			ref: 'weightForm', 
			selector: me.weightForm
		});
		
		me.callParent(arguments);
	},
	init: function() {
		var me = this;
		/**
		 * @event save
		 * 点击save按钮前触发。
		 * @param {Ext.button.Button} btn 点击的button
		 */
		// 初始化工具栏
		me.control(me.weightForm + ' button[itemId=save]', {
			click: me.onSave
		});
	},onLaunch : function() {
		var me = this;
		
	},
	onSave : function(){
		var me = this;
		var form = me.getWeightForm();
		
		//4.调用提交的方法，提交该表单  
        form.submit({  
            waitMsg:"正在向服务器提交数据",  
            url:"scrapWeightSave",
            method: 'POST',
            success: function (form, action) {  
            	var valuesObj = form.getValues();
            	form.reset();
            	var record=form.getRecord();
            	if(record){
            		record.set("userCode",valuesObj.userCode);
            	}else{
            		record = Ext.create("bsmes.model.ScrapWeight");
                	record.set("userCode",valuesObj.userCode);
            	}
            	
            	form.loadRecord(record);
            	
            	Ext.MessageBox.alert("提示", "success!");  
            },  
            failure: function (form, action) {
                Ext.MessageBox.alert("提示", "failure");  
            }  
        });  
		
		
	}
});