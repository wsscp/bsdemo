/**
 *@Project: 超级奶爸之宝宝喂养记
 *@Author: LionGIS@163.com
 *@Date: 2014-06-20
 *@Copyright: 代码开源，欢迎转载，但请保留版本信息.
 */

Ext.define('MES.view.Login', {
    extend: 'Ext.form.Panel',
    requires: [   'Ext.form.FieldSet',
                  'Ext.field.Password',
                  'Ext.Button'],
    alias: 'widget.login',
    config: {
    	id: 'loginView',
    	 fullscreen: true,
         
        items: [
                {
                	 xtype: 'fieldset',
                     
                     margin: '',
                     padding: 2,
                     right: '',
                     ui: 'light',
                     width: '',
                     modal: false,
                     title: '用户登录',
                    items: [
                        {
                        	 xtype: 'textfield',
                             border: '',
                             height: '',
                             width: '',
                             clearIcon: false,
                             label: '用户名：',
                             labelWidth: '20%',
                             name: 'username',
                             id:'username',
                             required: true,
                             autoCapitalize: false,
                             placeHolder: '请输入用户名',
                             ui: 'button'
                        },
                        {
                        	 xtype: 'passwordfield',
                             label: '密&nbsp;&nbsp; 码：',
                             labelWidth: '20%',
                             name: 'password',
                             required: true,
                             id:'password',
                             placeHolder: '请输入密码'
                        }
                    ]
                },
                {
                    xtype: 'button',
                    
                    itemId: 'loginButton',
                    margin: 20,
                    padding: 8,
                    ui: 'action',
                    
                    iconCls: 'user',
                    iconMask: true,
                    text: ' 登 录 系 统 ',
                    handler: function () {
						var usernameProxy = Ext.getCmp('username').getValue();
                        var passwordProxy = Ext.getCmp('password').getValue();
                        if(usernameProxy == '') {
                            Ext.Msg.alert("错误信息", "用户名不能为空.");
                            return;
                        } else if(passwordProxy == '') {
                            Ext.Msg.alert("错误信息", "密码不能为空.");
                            return;
                        }
                        
                        if(usernameProxy=="admin" && passwordProxy=="admin") {
	                        Ext.getCmp('loginView').setHidden(true);
	                    	Ext.Viewport.add(Ext.create('WeiYang.view.Main'));
                        }
                    }
                }
        ]
    }
});