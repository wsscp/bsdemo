<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<script type="text/javascript" src="${sctx}/Sound/soundmanager2-min.js"></script>
<style type="text/css">
.x-grid-record-yellow {
	color : Yellow
}
.x-grid-record-gree {
	color : #00CC00
}
.x-grid-record-gray {
	color : Gray
}
.x-grid-record-black {
	color : #0D0D0D
}

.gridButton{
-moz-border-radius: 3px;
-webkit-border-radius: 3px;
border-radius: 3px;
background-color: #20B2AA;
color: #fff;
font-size:12px;
border:medium none;
}
p {
margin : 0 0 5 0;
}
span .responded{
font-size : 15px;
font-weight :600;
color : #436EEE;
}
span .unresponded{
font-size : 15px;
font-weight :600;
color : #FF0000;
}
</style>
<script type="text/javascript">
	//初始化声音类soundManager
	soundManager = new SoundManager();
	// soundManager.waitForWindowLoad = true;
		// 是否打开调试模式，打开话对viewport有一定影响
	soundManager.debugMode = false;
	// 这个是soundManager提供的swf文件所在的文件夹
	soundManager.url = '/bsstatic/Sound/swf';
	soundManager.onload = function() {
	 // 这里面放入你要播放的声音
	 // 系统声音
		 soundManager.createSound({
		     id: 'systemSound',
		     url: '/bsstatic/Sound/mp3/system.mp3',
		     // autoLoad: true,//自动加载
		     // multiShot: false,//true 在同一时刻只能有一个频段的声音
		     autoPlay: true // 自动播放 这个是系统的背景音
		     // volume: 100
		 });
		 // 信息音
		 soundManager.createSound({
		     id: 'msgSound',
		     url: '/bsstatic/Sound/mp3/msg.mp3'
		     // volume: 100
		 });
		 // 加入音
		 soundManager.createSound({
		     id: 'joinSound',
		     url: '/bsstatic/Sound/mp3/join.mp3'
		     // volume: 100
		 });
	}	
</script>
<style type="text/css">
    .x-grid-record-red{
        color: red;
    }
    .x-grid-record-orange{
        color: #FF8C30;
    }
    .x-grid-record-blue{
        color: blue;
    }
</style>
<widget title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>" xtype="equipFaultManageList" controller="EquipFaultManageController" 
		closable="false"> </widget>
