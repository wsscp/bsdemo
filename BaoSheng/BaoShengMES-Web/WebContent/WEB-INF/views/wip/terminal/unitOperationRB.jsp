<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
table > tbody > tr > td > input{
	text-align: center;
	}
textarea {
	height: 45px;
	weight: 132px;
	padding:5px;
	resize: none;
	text-align: center;
	border-bottom: 0px solid;
	border-left: 0px solid;
	border-right: 0px solid;
	border-top: 0px solid;
	font-size: 13px;
	}
</style>
<div id="UnitOperationPrintArea">
<c:import url="/WEB-INF/views/wip/terminal/unitOperationRB1.jsp"></c:import>
<c:import url="/WEB-INF/views/wip/terminal/unitOperationRB2.jsp"></c:import>
<c:import url="/WEB-INF/views/wip/terminal/unitOperationRB3.jsp"></c:import>
</div>