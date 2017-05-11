<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="/bsmes"/>
<c:set var="sctx" value="/bsstatic" />
<html>
    <head>
        <script type="text/javascript" src="${sctx}/app/include-ext.js"></script>
        <script type="text/javascript" src="${sctx}/index.js"></script>
        <script type="text/javascript" src="${sctx}/oitjs/ext/util/message.js"></script>
        <script src="${sctx}/app/message.js"></script>
        <title><fmt:message key="bsmes.title"/></title>
        <script type="text/javascript">
            function showMessage() {
                openTab('系统消息管理', 'bas/sysMessage.action');
                sysMessage.Ext.ComponentQuery.query('sysMessagelist')[0].getStore().loadPage(1);
            }
        </script>
        <style type="text/css">
            #msg-div {
                left: 50%;
                margin-left: -200px;
                position: absolute;
                top: 200px;
                width: 400px;
                z-index: 20000;
            }

            #msg-div .msg {
                background: none repeat scroll 0 0 #f6f6f6;
                border: 2px solid #ccc;
                border-radius: 8px;
                color: #555;
                margin-top: 2px;
                padding: 10px 15px;
            }
            #msg-div a:visited {
                color: #1024ee;
            }
            #userInfo a:visited {
                color: #ffffff;
            }
            #userInfo a:visited {
                color: #ffffff;
            }
        </style>
        <style type="text/css">
            .title-style{
                color: white;
                font-family: helvetica,arial,verdana,sans-serif;
                font-size: 20px;
                font-weight: bold;
                line-height: 16px;
                position:absolute;
                top:10px;
                width: 210px;
                left:53px;
            }

            .x-btn-default-toolbar-small {
                background-image:url(${sctx}/icons/Nav_ori.png);
                background-color:#2d5259;
                border-color: #2d5259;
                padding: 0 0 0 0;
            }

            .x-btn-default-toolbar-small-menu-active, .x-btn-default-toolbar-small-pressed {
                background-image: url(${sctx}/icons/Nav_over.png);
            }

            .x-btn-default-toolbar-small .x-btn-inner {
                color: #ffffff;
                font-family: helvetica,arial,verdana,sans-serif;
                font-size: 12.5px;
                font-weight: bold;
                padding: 0 5px;
            }

            .x-btn-default-toolbar-small .x-btn-arrow-right{
                padding-right:3px;
            }

            .x-btn-default-toolbar-small .x-btn-arrow{
                background-image: url("");
            }

            .x-menu-body {
                background: none repeat scroll 0 0 #566069;
                padding: 0;
            }
            .x-menu {
                border-color: #566069;
                border-style: solid;
                border-width: 1px;
            }

            .x-menu-item-text {
                color: white;
                cursor: pointer;
                font-size: 13px;
                margin-right: 16px;
            }

            .x-menu-item-active{
                background: none repeat scroll 0 0 #6FB34C;
            }

            .top_r_msg {
                color: #ffffff;
                cursor: pointer;
                display: inline-block;
                font-size: 12px;
                height: 24px;
                outline: medium none;
                text-decoration: none;
                padding: 10px;
            }

            .m-btn-downarrow {
                background: url(${sctx}/icons/menu_downarrow_white.png) no-repeat scroll 4px center transparent;
                display: inline-block;
                line-height: 14px;
                width: 12px;
            }

            .l-btn-text {
                display: inline-block;
                height: 16px;
                line-height: 16px;
                padding: 0;
            }

            .menu {
                background: url(${sctx}/icons/menu.gif) repeat-y scroll 0 0 #f0f0f0;
                border: 1px solid #ccc;
                margin: 0;
                overflow: hidden;
                padding: 2px;
                position: absolute;
            }

            .menu-item {
                border: 1px solid transparent;
                cursor: pointer;
                font-size: 12px;
                height: 22px;
                line-height: 20px;
                margin: 0;
                overflow: hidden;
                padding: 0;
                position: relative;
            }

            .menu-text {
                left: 28px;
                position: absolute;
                top: 0;
            }

            .menu-sep{
                margin:3px 0px 3px 24px;
                line-height:2px;
                font-size:2px;
                background:url(${sctx}/icons/menu_sep.png) repeat-x;
            }

            #userMenu>div:hover{
                background:#6FB34C;
            }

            .x-tab-bar-default {
                background-color: #287874;
            }
            .x-tab-default-top {
                background-color: #30918d;
            }


            .x-tab-default-active {
                background-color: #8fc5c3;
            }

            .x-tab-default-active .x-tab-inner {
                color: #FFFFFF;
            }

            .x-tab-bar {
                position: relative;
            }
            
            .x-tab-bar-strip-default {
                border-style: solid;
                border-color: #8fc5c3;
                background-color: #8fc5c3;
            }
        </style>
    </head>
    <body>
        <iframe name="falseAjaxTarget" style="display: none" src="about:blank"></iframe>
        <div id='menu' class='x-hide-display' items='${resources}' userCode='${userSessionKey.userCode}' ctx='${ctx}' sctx='${sctx}' exitText='<fmt:message key="menu.exit.text"/>'>
            <div id='firstTab' iframeName='handSchedule' title='${resources1111.name}' href='${resources1111.uri}' ></div>
            <div id="bsmesInfo">
                <img id="titleImg" src="${sctx}/icons/Logo.png"/>
		        <span id="titleHtml" class="title-style">
                    宝胜特缆制造执行系统
		        </span>
            </div>
            <div id="userInfo">
                <a class="top_r_msg" href="#" onclick="javascript:showMsg();">
                    <span class="l-btn-text">
                        <font color="white">${userSessionKey.name}</font>
                        <span class="m-btn-downarrow">&nbsp;</span>
                    </span>
                </a>
                <img onclick="javascript:showMessage();" src="${sctx}/icons/mail.png" style="margin-bottom: -5px" class="x-action-col-icon x-action-col-2" alt="">
                <font color="white" style="font-size: 11px">
                    (<span id="msgCount">0</span>)
                </font>
            </div>
        </div>
        <div id="userMenu" class="menu" style="width: 100px;display: none;top: 42px; z-index: 110005;">
            <div class="menu-item" onclick="javascript:openUserInfo('${userSessionKey.userCode}');" name="" href="" style="height: 20px;">
                <div class="menu-text">个人资料</div>
            </div>
            <div class="menu-sep"> </div>
            <div class="menu-item" onclick="window.location.href='${ctx}/logout.action'" name="" href="" style="height: 20px;">
                <div class="menu-text">退出</div>
            </div>
        </div>
    </body>
</html>