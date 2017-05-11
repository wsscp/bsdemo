/**
 * Created by jijy on 2014/11/5.
 */
Ext.onReady(function() {
//    getNewMessage();
//    setInterval(getNewMessage, 120000);
});

function getNewMessage() {
	console.log('--------------------')
    Ext.Ajax.request({
        url: 'bas/sysMessage/getNewMessage',
        async:false,
        success: function(response){
            var text = response.responseText;
            // process server response here
            var messageObj = Ext.JSON.decode(text);
            var size = messageObj.size;
            if (!isNaN(messageObj.unreadSize)) {
                msgCount = parseInt(messageObj.unreadSize);
                refreshMsgCount();
            }

            if (size == "0") {
                return;
            }
            var msg = messageObj.message;
            msg += '</p><p align="right"><a href="#" onclick="readMessage(this, \'' + messageObj.messageId + '\')">设为已读</a>';
            if (size != '1') {
                msg += '&nbsp;<a href="#" onclick="moreMessages(this)">更多新消息</a>';
            }
            msg += '&nbsp;<a href="#" onclick="closeMessage(this)">关闭</a>';

            Ext.util.message.msg(messageObj.title, msg);
        }
    });
    //Ext.util.message.msg('Done', 'Your fake data was saved!');
}

function readMessage(button, messageId) {
    Ext.Ajax.request({
        url: 'bas/sysMessage/readMessage/' + messageId
    });
    countDownMsgCount();
    closeMessage(button);
}

function moreMessages(button) {
    openTab('系统消息管理', 'bas/sysMessage.action');
    sysMessage.Ext.ComponentQuery.query('sysMessagelist')[0].getStore().loadPage(1);
    closeMessage(button);
}

function closeMessage(button) {
    Ext.fly(button).parent('div.msg').remove();
}

var msgCount = 0;
function countDownMsgCount() {
    msgCount--;
    refreshMsgCount();
}

function refreshMsgCount() {
    Ext.fly('msgCount').setHTML(msgCount);
}