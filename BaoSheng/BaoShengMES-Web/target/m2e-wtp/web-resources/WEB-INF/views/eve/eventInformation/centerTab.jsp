<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="sctx" value="/bsstatic" />
<style type="text/css">
.x-grid-record-red {
	color: red;
}

.x-grid-record-orange {
	color: #FF8C30;
}

.x-grid-record-blue {
	color: blue;
}
</style>
<script type="text/javascript" src="${sctx}/Sound/soundmanager2-min.js"></script>
<script type="text/javascript">
	soundManager = new SoundManager();
	// soundManager.waitForWindowLoad = true;
	soundManager.debugMode = false;
	soundManager.url = '/bsstatic/Sound/swf';
	soundManager.onload = function() {
		soundManager.createSound({
			id : 'systemSound',
			url : '/bsstatic/Sound/mp3/system.mp3',
			autoPlay : true
		// volume: 100
		});
		soundManager.createSound({
			id : 'msgSound',
			url : '/bsstatic/Sound/mp3/msg.mp3'
		// volume: 100
		});
		soundManager.createSound({
			id : 'joinSound',
			url : '/bsstatic/Sound/mp3/join.mp3'
		// volume: 100
		});
	}
</script>

<widget
	title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>"
	xtype="eventInformationList" controller="EventInformationController"
	closable="false"> </widget>
