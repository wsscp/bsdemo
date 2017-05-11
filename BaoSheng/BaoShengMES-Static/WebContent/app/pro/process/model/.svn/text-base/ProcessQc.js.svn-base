Ext.define('bsmes.model.ProcessQc',{
	extend:'Ext.data.Model',
	fields : [
	          	'processId',
	          	'checkItemCode',
	          	'checkItemName',
	          	'frequence',
	          	{
					name : 'needDa',
					type : 'boolean',
					renderer : function(value) {
						if (value == true) {
							return Oit.msg.pro.processReceipt.needDaYes;
						} else {
							return Oit.msg.pro.processReceipt.needDaNo;
						}
					}
				},
				{
					name : 'needIs',
					type : 'boolean',
					renderer : function(value) {
						if (value == true) {
							return Oit.msg.pro.processReceipt.needDaYes;
						} else {
							return Oit.msg.pro.processReceipt.needDaNo;
						}
					}
				},
	          	{
	          		name:'dataType',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == 'STRING'){
	          				return '字符';
	          			}else if(value == 'DOUBLE'){
	          				return '数字';
	          			}else if(value == 'BOOLEAN'){
	          				return '布尔';
	          			}else{
	          				return '';
	          			}
	          		}
	          	},
	          	'dataUnit',
	          	'marks',
	          	'itemTargetValue',
	          	'itemMaxValue',
	          	'itemMinValue',
	          	{
	          		name:'hasPic',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == '1'){
	          				return '是';
	          			}else if(value == '0'){
	          				return '否';
	          			}else{
	          				return '';
	          			}
	          		}
	          	},
	          	{
	          		name:'needShow',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == '1'){
	          				return '是';
	          			}else if(value == '0'){
	          				return '否';
	          			}else{
	          				return '';
	          			}
	          		}
	          	},
	          	{
	          		name:'needFirstCheck',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == '1'){
	          				return '是';
	          			}else if(value == '0'){
	          				return '否';
	          			}else{
	          				return '';
	          			}
	          		}
	          	},
	          	{
	          		name:'needMiddleCheck',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == '1'){
	          				return '是';
	          			}else if(value == '0'){
	          				return '否';
	          			}else{
	          				return '';
	          			}
	          		}
	          	},
	          	{
	          		name:'needInCheck',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == '1'){
	          				return '是';
	          			}else if(value == '0'){
	          				return '否';
	          			}else{
	          				return '';
	          			}
	          		}
	          	},
	          	{
	          		name:'needOutCheck',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == '1'){
	          				return '是';
	          			}else if(value == '0'){
	          				return '否';
	          			}else{
	          				return '';
	          			}
	          		}
	          	},
	          	{
	          		name:'needAlarm',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == '1'){
	          				return '是';
	          			}else if(value == '0'){
	          				return '否';
	          			}else{
	          				return '';
	          			}
	          		}
	          	},
	          	'valueDomain',
	          	{
	          		name:'emphShow',
	          		type:'string',
	          		renderer:function(value){
	          			if(value == '1'){
	          				return '是';
	          			}else if(value == '0'){
	          				return '否';
	          			}else{
	          				return '';
	          			}
	          		}
	          	}
	          ]
});