Ext.define('bsmes.model.SysMessage', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'messageTitle',
		type : 'string'
	}, {
		name : 'messageContent',
		type : 'string'
	}, {
		name : 'hasread',
		type : 'string',
		renderer : function(value) {
			if (value == 'true') {
				return Oit.msg.bas.sysMessage.yes;
			} else if (value == 'false') {
				return Oit.msg.bas.sysMessage.no;
			} else {
				return "";
			}
		}
	}, {
		name : 'messageReceiver',
		type : 'string'
		
	}, {
		name : 'receiveTime',
		type : 'date'
			
	}, {
		name : 'readTime',
		type : 'date'
	}, {
		name : 'orgCode',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date'
	} ]
});
