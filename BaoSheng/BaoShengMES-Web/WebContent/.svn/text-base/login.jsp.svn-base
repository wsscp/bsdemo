<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="sctx" value="/bsstatic" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="errorMsg" value="${errorMsg}" />
 
<!DOCTYPE html>
<html lang="zh-cn">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>宝胜特缆制造执行系统登录</title>
 
<link type="text/css" rel="stylesheet" href="${sctx}/assets/css/font-awesome.css" />
 
<style type="text/css">
  
  /**
  * body样式
  */
  .login_body{
    background: none repeat scroll 0 0 #fff;
    color: #666;
    font: 12px/150% Arial,Verdana,"宋体";
    margin: 0;
    height:100%; 
    position: relative; 
    min-height:600px;
    _height:600px;
    min-width: 800px;
    width: expression_r( document.body.clientWidth < 801 ? "800px" : "auto" );	
  }
  
  /**
  * 中心块
  */
  .center{
    margin: auto;
  }
  
  /**
  * 主区域样式
  */
  .login_body .main{
    width: 100%;
	height:90%;
	min-height:540px;
	_height:540px;
	background:url(${sctx}/icons/login_back.jpg) no-repeat;
	background-size:100% 100%;
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,src='${sctx}/icons/login_back.jpg' ,sizingMethod='scale');
  }
  
  /**
  * 主区域中心块样式
  */
  .login_body .main .center{
    width: 800px;
	height: 540px;
	background:url(${sctx}/icons/login_drop.png) no-repeat;
	background-size:100% 100%;
	position: relative;
  }
  
  
  /**
  * 页脚区域样式
  */
  .login_body .footer{
    width: 100%;
	height:10%;
	min-height:60px;
	_height:60px;
	background-color: #1C2B2E;
	position:absolute; 
	bottom: 0px;
  }
  
  /**
  * 页脚区域中心块样式
  */
  .login_body .footer .center{
    width:800px;
	height:100%; 
	overflow:hidden;
  }
  
  .footer span{
    color: #A6AAAB;
	display: block;
	font-size: 12px;
	margin: auto;
  }
  
  
  
  /**
  * 表单块样式
  */
  .login-form{
    position: absolute;
	top: 50%;
	left: 50%;
	width:344px;
	height:200px;
	margin-left: -30px;
	margin-top: -10px;
	filter:progid:DXImageTransform.Microsoft.gradient(enabled='true',startColorstr='#44000000',endColorstr='#44000000');
	background:rgba(0,0,0,.3);

  }
  
  /**
  * 输入框样式
  */
  .input-group{
    width:290px;
	height:50px;
	padding-top: 10px;
	padding-left: 30px;
	position:relative;
  }
  
  .form-text-icon{
    position:absolute;
	left:42px;
	top:22px;
  }
  
  .form-text{
    color: #333;
	border: 1px solid #FFFFFF;
	height: 20px;
	line-height: 1.42857;
	padding: 6px 12px;
	width: 240px;
    padding-left:30px; 
	font-family: "georgia","Helvetica Neue",Helvetica,Arial,"Hiragino Sans GB","Hiragino Sans GB W3","Microsoft YaHei UI","Microsoft YaHei","WenQuanYi Micro Hei",sans-serif;
    font-size: 16px;
	font-weight: bold; 
  }
  
  .form-text:focus,
  .form-text:hover{
	  border: 1px solid #5AB2AD;
  }
  
  /**
  * 按钮样式
  */
  .btn-group{
    width:280px;
	height:50px;
	padding-top: 10px;
	padding-left: 34px;
  }

  .btn{
    border: 1px solid transparent;
    cursor: pointer;
    display: inline-block;
    font-size: 14px;
    line-height: 1.42857;
    padding: 3px 12px;
    text-align: center;
  }
   
  .btn-primary {
    color: #ffffff;
    background-color: #5AB2AD;
    border-color: #5AB2AD;
  }
  
  .btn-primary:hover,
  .btn-primary:focus,
  .btn-primary:active,
  .btn-primary.active{
    color: #ffffff;
    background-color: #287875;
  }
  
  /**
  * 弹出层样式
  */
  .modal {
    position: absolute;
	top: 50%;
	left: 50%;
    width: 300px;
	height: 120px;
    margin-top: -150px;
	margin-left: -140px;
	background-color: #F1F1F1;
    z-index: 1050;
  }
  
  .modal-content{
    font-size: 24px;
	text-align: center;
	color: inherit;
	font-family: inherit;
	font-weight: 500;
  }

  .modal-backdrop {
    background-color: #000;
    bottom: 0;
    left: 0;
    position: fixed;
    right: 0;
    top: 0;
    z-index: 1040;
	opacity: 0;
    transition: opacity 0.15s linear 0s;
	opacity: 0.5;
  }
    
</style>
 
</head>
<body class="login_body" onload="loginLoad();">
	<div class="main">
	    <div id="centerBox" class="center">
		    <form action="${ctx}/login.action" accept-charset="utf-8" method="post" class="login-form">	
				<div class="input-group" style="margin-top: 20px;">
					<i class="fa fa-user form-text-icon"></i>
					<input type="text" class="form-text" name="loginName" placeholder="登录名">
				</div>
				<div class="input-group" >
					<i class="fa fa-lock form-text-icon"></i>
					<input type="password" class="form-text" id="password" placeholder="密码">
					<input type="hidden" name="password">
					<input type="hidden" name="url" value="${url}">
				</div>
				<div class="btn-group">
					<button id='checkReValid' class="btn btn-primary" type="button" style="float: right;" onclick="validateFrom(this.form)"><i class="fa fa-key"></i>登录</button>
				</div>			
			</form>
		</div>
			
	</div>
	<div class="footer">
	    <div class="center">
		    <span style="width: 228px;margin-top: 15px;">上海东方申信科技发展有限公司&nbsp;&nbsp;版权所有</span>
		    <span style="width: 342px;">Copyright © 2013 Orientech Info.Tech. All Rights Reserved</span>
		</div>	
	</div>
	
    
</body>
<script type="text/javascript" src="${sctx}/jQuery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${sctx}/jQuery/jquery.md5.js"></script>
<script type="text/javascript" >
var checkResult=false;	    
function loginLoad(){
  selfAdaptionHeight();
}

window.onresize = selfAdaptionHeight;
document.onkeydown=function(event){
	if(!checkResult){
		var e = event || window.event || arguments.callee.caller.arguments[0];
	    if(e && e.keyCode==13){
	    	document.getElementById('checkReValid').click();
	    }
	}
}
/**
* 自适应高度
*/
function selfAdaptionHeight(){
  var bodyHeight = document.body.clientHeight;
  var winHeight = document.documentElement.clientHeight;
  if(bodyHeight<winHeight || bodyHeight>600){
    document.body.style.height=winHeight+'px'; 
    bodyHeight=winHeight;
    if(bodyHeight>600){
      var top = (bodyHeight-600)/2;
      document.getElementById('centerBox').style.top=top+'px';
    }
  }  
}
	
/**
* 提交表单验证
*/
function validateFrom(form){
	if(!checkResult){
		var loginName = $("input[name='loginName']").val();
		var password = $("#password").val();
		if(loginName == '' || password == ''){
		  alertMsg({content: '请输入用户名和密码！'})
		}else{
			$("input[name='password']").val($.md5($("#password").val()));
			$.ajax({
				url: $(form).attr("action"),
	  		    type: $(form).method||'POST',
	  		    data: $(form).serializeArray(),
	 	        success: function(msg){
	 	        	msg = eval("("+msg+")");
	 	        	if(msg.success){
	 	        		window.location = ($("input[name='url']").val() == '' ? 'index.action' : $("input[name='url']").val());
	 	        		// 'index.action';
	 	        	}else{
	 	        		alertMsg({content: decodeURIComponentEx(msg.message)})
	 	        	}
		        }
	 	    });
		}
	}
}

/**
* 弹出消息
*/
function alertMsg(config){
  this.config = config;

  var modalTemplate = '<div style="padding: 0px 15px;">' +
					    '<h3 class="modal-content">@{content}</h3>' +
					  '</div>' +
					  '<div style="padding: 0px 120px;">' +
					    '<button class="btn btn-primary ConfirmBtn" type="button">确定</button>' +
					  '</div>';
  var backdrop = '<div class="modal-backdrop"></div>'

  this.createModal = function(){
    var modalContent = this.template(modalTemplate, config);
    // 创建弹出层
    var modalBox = document.createElement('div');
    modalBox.setAttribute('class', 'modal');
    modalBox.innerHTML = modalContent;
    document.body.appendChild(modalBox);
    // 创建遮罩层
    var backdropBox = document.createElement('div');
    backdropBox.setAttribute('class', 'modal-backdrop');
    document.body.appendChild(backdropBox);
    //绑定事件
    var but = modalBox.getElementsByClassName('ConfirmBtn')[0].onclick = function(e){
      closeModal(modalBox, backdropBox, e);
    };
    modalBox.style.marginTop=(getScrollTop()-150)+'px';
  }

  /** 关闭弹出窗口
  * @param modalBox 弹出层
  * @param backdropBox 遮罩层
  * @param event 事件
  */
  this.closeModal = function(modalBox, backdropBox, event){
    modalBox.parentNode.removeChild(modalBox);
    backdropBox.parentNode.removeChild(backdropBox);
    checkResult=false;
  }

  /** 渲染简单模板
  * @param template 模板
  * @param data 数据
  */
  this.template = function(template,data){
    var content='';
    if(data instanceof Array){
      for(var i=0;i<data.length;i++){
        content+=renderTemplate(template,data[i]);
	  }
    }else{
	  var t=template;
	  for(var attr in data){
	    var value=data[attr];
	    if(value==null){
	      value='';
	    }
	    t=t.replace(new RegExp("@{"+attr+"}","gm"),value);
	  }
	  content+=t;
    }
    return content;
  }
  
  //创建弹出层
  createModal();
}

/**
 * 获取滚动条高度
 */
function getScrollTop(){
  var scrollTop=0;
  if(document.documentElement&&document.documentElement.scrollTop){
    scrollTop=document.documentElement.scrollTop;
  }else if(document.body){
    scrollTop=document.body.scrollTop;
  }
  return scrollTop;
}

/** 
 * url字符编码解码 
 *
 * @param {String} 
 * @return {String} 
 */  
function decodeURIComponentEx(uriComponent){  
	 checkResult=true;
 if(!uriComponent){  
     return  uriComponent;  
 }  
 var ret;  
 try{  
     ret = decodeURIComponent(uriComponent);  
 }catch(ex){  
     ret = unescape(uriComponent);  
 }  
 return ret;  
}; 
</script>
</html>