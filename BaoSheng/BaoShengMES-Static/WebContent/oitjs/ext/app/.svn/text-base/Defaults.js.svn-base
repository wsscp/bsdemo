// 日期相关
Ext.define('Oit.data.Field', {
	override : 'Ext.data.Field',
	dateFormat : 'time'
});

// RowExpander
Ext.define('Oit.grid.plugin.RowExpander', {
	override : 'Ext.grid.plugin.RowExpander',
	rowBodyTpl : [ '<div id="{id}">', '</div>' ]
});

// RowExpander
Ext.define('Oit.data.validations', {
	override : 'Ext.data.validations',
    number: function(config, value) {
        var valid = true;
        if (config.min) {
            valid = valid && value >= config.min;
        }
        if (config.max) {
            valid = valid && value <= config.max;
        }
        return valid;
    }
});

// RowEditor
Ext.define('Oit.grid.RowEditor', {
	override : 'Ext.grid.RowEditor',
    saveBtnText  : '更新',
    cancelBtnText: '取消'
});

// RowEditor
Ext.define('Oit.window.Window', {
	override : 'Ext.window.Window',
    initComponent : function() {
        // 限制高度
        this.height = document.body.clientHeight < this.height ? document.body.clientHeight : this.height;

        this.callParent(arguments);
    }
});

// url
Ext.define('Oit.url', {
    statics : {
        urlParam: function(name) {
            var s = window.location.search;
            var re = new RegExp('(?:^|[&?])' + name + '(?:[=]([^&]*))?(?:$|[&])', 'i');
            var m = re.exec(s);

            return m ? m[1] : "";
        },
        urlParams : function() {
            var s = window.location.search.substring(1);
            var urlParams = {};
            var params = s.split("&");
            for (var i in params) {
                var param = params[i];
                var splits = param.split("=");
                urlParams[splits[0]] = splits[1];
            }
            return urlParams;
        }
    }
});

//ajax
Ext.define('Oit.Ajax', {
    override : 'Ext.Ajax', 
    request : function(options) {
        var success = options.success;
        options.success = function(response, options) {
            var result = Ext.JSON.decode(response.responseText, true);
            if (result != null && (result.success == false || result.success == 'false')) {
                if (result.message) {
                    Ext.Msg.alert(Oit.msg.WARN, result.message);
                }
                Ext.callback(options.failure, options.scope, [response, options]);
            } else {
                Ext.callback(success, options.scope, [response, options]);
            }
        }
        try{
          this.callParent(arguments);
        }
        catch(err)
        { 
        	if(err.code==101||err.name=='NS_ERROR_FAILURE')
        		{
        	     Ext.Msg.alert("错误提示","网络出现故障，请联系管理员");
        		}else
        		{
        			// Ext.Msg.alert("错误提示","出现未知错误，请联系管理员");	
        			}
        	 
        	
        	  Ext.callback(options.failure, options.scope);
        	
        }
    }
   
});
