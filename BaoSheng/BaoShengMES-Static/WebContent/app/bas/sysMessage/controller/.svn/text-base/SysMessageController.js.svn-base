Ext.define('bsmes.controller.SysMessageController', {
	extend : 'Oit.app.controller.GridController',
	view : 'sysMessagelist',
	editview : 'sysMessageread',
	views : [ 'SysMessageList', 'SysMessageRead' ],
	stores : [ 'SysMessageStore' ],
	onLaunch : function(){
		var me = this;
		me.callParent(arguments);
		
		var grid = me.getGrid();
		grid.on('toRead', me.doRead, me);
	},
	onFormSave: function(btn) {
		var me = this;
		var form = me.getEditForm(); 
		form.updateRecord();
		if (form.isValid()) {
			var store = me.getGrid().getStore();
//			store.getProxy().url='sysMessage';
			// 同步到服务器
			store.sync();
			// 关闭窗口
			me.getEditView().close();
            parent.countDownMsgCount();
		}
	},
	doRead: function(record) {
		var me = this;
		if (me.newFormToEdit) {
			me.getEditView().show();
			record.set('hasread','true');
			me.getEditForm().loadRecord(record);
		} else {
			me.getGrid().editingPlugin.startEdit(data, 0);
		}
		
		
	}
	
});
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
