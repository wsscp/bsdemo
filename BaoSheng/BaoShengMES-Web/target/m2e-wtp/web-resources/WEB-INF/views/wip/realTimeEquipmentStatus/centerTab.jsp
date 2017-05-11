<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="sctx" value="/bsstatic" />
<style>
fieldset {
	border: 1px solid silver !important;
	margin: 0 2px !important;
	padding: 0.35em 0.625em 0.75em !important;
}

legend {
	border-width: 0;
	margin-bottom: 0px;
	padding: 0 5px 0 5px;
	width: auto;
}

html {
	overflow-x: hidden;
	overflow-y: hidden;
}

.btn-primary {
	background-color: #70B9B2;
	border-color: #287874;
	color: #fff;
}

.btn-primary:hover {
	background-color: #348480;
	border-color: #287874;
	color: #fff;
}
</style>

<div class="container" style="width: 100%; padding: 0;" sroll="no">
</div>
<script type="text/javascript"
	src="${sctx}/app/wip/realTimeEquipmentStatus/EquipStatusComponent.js"></script>
