<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="sctx" value="/bsstatic" />
<style type="text/css">
.change_equip {
	background-image: url("${sctx}/icons/arrow-continue-090.png");
}

.split_order {
	background-image: url("${sctx}/icons/arrow-split.png");
}

.group_order {
	background-image: url("${sctx}/icons/arrow-continue-090.png");
}
.x-grid-record-red-color {
	color: red !important;
}
.x-grid-record-orange-color {
	color: #FF8C30 !important;
}
.x-grid-record-blue-color {
	color: #3880C9 !important;
}
.x-grid-record-green-color {
	color: #008000 !important;
}
.x-grid-record-purple-color {
	color: #9264E8 !important;
}
.x-grid-record-cyan-color {
	color: #6666FF !important;
}
.x-grid-record-grey-color {
	color: grey !important;
}

.ux-notification-window .x-window-body {
	text-align: center;
	padding: 5px 5px 5px 5px;
}

ul {
	overflow: hidden;
	padding: 0px;
	margin: 0px;
}

ul li,ul li {
	float: left;
	list-style: none;
}

.mesFieldSet {
	border: 1px solid #C0C0C0;
}

.mesFieldSet>textarea {
	border: none;
}

table.mesTableLayout,textarea,b {
	font-size: 13px;
	padding: 0px;
}

span.label {
	padding-left: 10px;
	width: 125px;
	display: -moz-inline-box;
	display: inline-block;
}

table.mesTable {
	border-collapse: collapse;
}

table.mesTable>thead>tr>th,table.mesTable>tbody>tr>td {
	border: 1px solid #EDEDED;
	font-size: 13px;
	padding: 5px;
}

table.mesTable>thead>tr>th {
	border: 1px solid #C0C0C0;
	background-color: #F5F5F5;
	color: #666666;
}

table.mesTable>tbody>tr>td.small {
	font-size: 12px;
}
table.mesTable>tbody>tr>td.edit {
	color: blue;
	text-decoration: underline;
	cursor: pointer;
}

table.mesTable>tbody>tr>td.title {
	width: 57px;
	background-color: #93CDDD;
}

#noticeFieldset>b {
	color: black;
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

.btn-primary.big {
	display: none;
	margin-right: 4px;
	font-size: 18px;
	font-weight: 700;
	padding: 8px;
}

.btn-nocss {
	border: 1px solid #ccd4db;
	-moz-border-radius: 33px; -webkit-border-radius : 33px;
	border-radius: 33px;
	-webkit-border-radius: 33px; border-radius : 33px;
	
}
/**
background-color: #C0C0C0;
*/

.btn-nocss:hover {
	background-color: #ccd4db;
}

textarea {
	width: 100%;
}
</style>
<widget
	title="<fmt:message key="${moduleName}.${submoduleName}.centerTitle"/>"
	xtype="handSchedulePanel" controller="HandScheduleController"
	closable="false"> </widget>
<div id="roleList" roleList = '${roleList}'/>
<p id="userSessionKeyName" userName="${userSessionKey.name}"
	userCode="${userSessionKey.userCode}"></p>
	
<div id="unit"></div>
