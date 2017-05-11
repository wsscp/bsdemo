Ext.define("bsmes.view.SupplementMaterialWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.supplementMaterialWindow',
	layout:'fit',
	width:document.body.scrollWidth-300,
    height:document.body.scrollHeight-100,
    modal : true,
	plain : true,
	title: '补料清单',
	materialMap: new Ext.util.HashMap(),
	workOrderNo : null,
	userCode : null,
	userName : null,
	groupMap : null,
	initComponent: function() {
		var me = this;
		var rowItems = [];
		me.materialMap.clear();
		Ext.Array.each(me.groupMap, function(record, i){
			console.log(record.matCode)
			me.materialMap.add(record.matCode,record.name);
			var json = Ext.decode(record.inAttrDesc);
			var matDesc = '';
			var a = false;
			if (json.color) { // 宽度
				matDesc += '<font color="red">颜色:' + json.color + '</font><br/>'
				a = true;
			}
			if (json.kuandu) { // 宽度
				matDesc += '宽度:' + json.kuandu + '<br/>';
				a = true;
			}
			if (json.houdu) { // 厚度
				matDesc += '厚度:' + json.houdu + '<br/>';
				a = true;
			}
			if (json.caizhi) { // 材质
				matDesc += '材质:' + json.caizhi + '<br/>';
				a = true;
			}
			if (json.chicun) { // 尺寸
				matDesc += '尺寸:' + json.chicun + '<br/>';
				a = true;
			}
			if (json.guige) { // 规格
				matDesc += '规格:' + json.guige + '<br/>';
				a = true;
			}
			if (json.dansizhijing) { // 单丝直径
				matDesc += '单丝直径:' + json.dansizhijing + '<br/>';
				a = true;
			}
			if(a){
				var name = record.name + '(' + matDesc + ')' + '(KG)';
			}else{
				var name = record.name+ '(KG)';
			}
			var matItems = {
					xtype : 'textfield',
					fieldLabel : name,
					id: record.matCode,
					name : record.matCode,
					labelWidth : 200,
					height : 30,
				    readOnly:false,
					margin: '5 0 5 10',
	        		plugins:{
	                    ptype:'virtualKeyBoard'
	                }
			};
			rowItems.push(matItems);
		});
		me.items = [{
			xtype : 'form',
			autoScroll:true, 
			width : '100%',
			layout : 'vbox',
			buttonAlign : 'left',
			labelAlign : 'right',
			bodyPadding : 5,
			defaults : {
				xtype : 'panel',
				width : '100%',
				layout : 'column',
				defaults : {
					width : 450,
					padding : 1,
					labelAlign : 'right',
					align: 'middle'
				}
			},
			items : [{
				items: [{
			    	fieldLabel:'<font size="4.5px">生产单号</font>',
					xtype:'displayfield',
					labelWidth : 80,
					margin: '10 0 0 0',
					name:'workOrderNo',
					value : me.workOrderNo
			    },{
			    	fieldLabel:'<font size="4.5px">补料人</font>',
					xtype:'displayfield',
					labelWidth : 60,
					margin: '10 0 0 0',
					name:me.userCode,
					value : me.userName
			    }]
			},{
				title:'<font size="2px">补料信息</font>',
		    	xtype : 'fieldset',
		    	width: '99%',
 		        margin: '10 0 0 0',
 		        padding:'0 0 5 0',
 		        layout:{
 		        	type: 'table',
 		        	columns: 2
 		        },
	            items: rowItems
			}]
		}];
		
		Ext.apply(me, {
			buttons: [{
				itemId: 'ok',
				text:Oit.btn.ok,
				handler: function() {
					var form = this.up('window').down('form').getForm();
					var isAllNull = true;
					for(var i in form.getValues()){
						if(form.findField(i).getValue() != ''){
							isAllNull = false;
						}
					}
					if(isAllNull){
						Ext.Msg.alert(Oit.msg.PROMPT, '补料量均为空，请填写补料量！');
						return;
					}
					var matMap = me.materialMap.map;
					var data=[];
					for(var key in matMap){
						console.log('key:'+key)
						var inputValue = form.findField(key).getValue();
						if(inputValue == ''){
							continue;
						}
						data.push({
							buLiaoQuantity : inputValue,
							workOrderNo : me.workOrderNo,
							equipCode : Ext.fly('equipInfo').getAttribute('code'),
							userCode : me.userCode,
							matCode : key
						});
						
					}
					form.submit({
                    	url : 'updateSuppMaterialInfo',
                    	params : {
                    		buLiaoInfo : Ext.encode(data),
                    		workOrderNo : me.workOrderNo
                    	},
                        success: function(form, action) {
                        	Ext.Msg.alert(Oit.msg.PROMPT, '补料成功');
                        	me.close();
                        	Ext.ComponentQuery.query('#recentOrderGrid')[0].getStore().reload();
                        },
                        failure : function(form, action) {
                        	Ext.Msg.alert(Oit.msg.PROMPT, '补料失败');
            			}
                    });
				}		
			},'->', {
				itemId: 'cancel',
				text:Oit.btn.cancel,
				scope: me,
				handler: me.close
			}]
		});
		this.callParent(arguments);
		
	}
});

