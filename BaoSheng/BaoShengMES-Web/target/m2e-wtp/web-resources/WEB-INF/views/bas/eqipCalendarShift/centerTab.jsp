<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<widget title="<fmt:message key="${moduleName}.${submoduleName}.title"/>" xtype="eqipCalendarShiftView" controller="EqipCalendarShiftController" 
		closable="false"> </widget>
<div id="workShift" workShifts = '${workShifts}'/>