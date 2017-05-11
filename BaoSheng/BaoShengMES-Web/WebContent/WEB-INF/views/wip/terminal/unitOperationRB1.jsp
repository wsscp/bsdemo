<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="table_outter">
<div class="table_box box1_w"> 
   <!-- 绕包单元操作 --> 
   <div class="h_style1">
   <h1 style="width:220px;height:60px;margin:auto;margin-top:20px;">绕包单元操作</h1> 
   </div>
   <div class="table_">
   <table class="printtTable table_box"> 
    <!-- 批次号/条形码 --> 
    <tbody>
     <tr> 
      <td class="tdInput" style="width:275px;height:95px;text-align:left;">产品批号: ${series }</td> 
      <td style="width:485px;" rowspan="2"> 
       <table> 
        <tbody>
         <tr> 
          <td class="imgFrame" rowspan="2" style="width:60px;">
           <div class="writing_tb">
            操作简图
           </div></td> 
          <td class="imgBox">
           <div>
            <img src="/bsstatic/icons/Logo.png" />
           </div></td> 
          <td class="imgBox">
           <div>
            <img />
           </div></td> 
          <td class="imgBox">
           <div>
            <img />
           </div></td> 
          <td class="imgBox">
           <div>
            <img />
           </div></td> 
         </tr> 
         <tr> 
          <td class="center">原料输入</td> 
          <td class="center">绕包机</td> 
          <td class="center">产品输出</td> 
          <td class="center">合格检验</td> 
         </tr> 
        </tbody>
       </table> </td> 
     </tr> 
     <tr style="width:200px;height:95px;"> 
      <td>条形码粘贴处</td> 
     </tr> 
    </tbody>
   </table> 
   </div>
   <!-- END 批次号/条形码 --> 
    <div class="table_">
   <div class="table_box box2_w"> 
    <!-- 原材料控制 --> 
    <div class="h_style3">
    <h3 style="margin-left:20px;">原材料控制</h3> 
    </div>
    <c:forEach begin="1" end="${countNumMap.dtCount}" var="i">
    	<div class="table_inner">
    	<table class="printtTable table_box box3_w"> 
     <!-- 导体 --> 
     <tbody>
      <tr> 
       <td rowspan="6" class="title">
        <div class="writing_tb">
         导体
        </div></td> 
       <td rowspan="2" style="width:60px" class="small">ID</td> 
       <td class="small" rowspan="2" style="width:110px">${RBMaterialDT.id}</td> 
       <td rowspan="6" style="width:166px;">导体截面图</td> 
       <td style="width:190px" class="small">详细参数</td> 
       <td style="width:80px" class="small">标准值</td> 
       <td style="width:80px" class="small">误差范围</td> 
	   <td style="width:80px" class="small">测量值</td>
      </tr> 
      <tr> 
       <td class="small" style="width:190px">单丝外径/mm</td> 
       <td class="small">${RBQc.singleDia}</td> 
       <td></td> 
	   <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td rowspan="2" style="width:60px" class="small">批次号</td> 
       <td class="tdInput" rowspan="2" style="width:110px"></td> 
       <td class="small" style="width:190px">绞合外径/mm</td> 
       <td class="small">${RBMaterialDT.standardDia}</td> 
       <td></td> 
	   <td class="tdInput"></td>   
      </tr> 
      <tr> 
       <td class="small" style="width:190px">单元导体质量/(kg/km)</td> 
       <td></td> 
       <td></td> 
	   <td></td>  
      </tr> 
      <tr> 
       <td rowspan="2" style="width:60px" class="small" >材料</td> 
       <td class="small" rowspan="2" style="width:110px">${RBMaterialDT.name}</td> 
       <td class="small" style="width:190px">导体直流电阻/(ohm/km)</td> 
       <td class="small">${RBMaterialDT.directResistance}</td> 
       <td></td> 
	   <td></td> 
      </tr> 
      <tr height="20px">
      	<td></td>
      	<td></td>
      	<td></td>
      	<td></td>
      </tr>
     </tbody>
    </table> 
    </div>
    <!-- END 导体 --> 
    </c:forEach>
    
    <c:if test="${processCode=='wrapping'}">
    	<div class="table_inner">
    	<table class="printtTable table_box box3_w"> 
     <tbody>
      <tr> 
       <td rowspan="6" class="title">
        <div class="writing_tb">
         半成品电线
        </div></td> 
       <td rowspan="2" style="width:60px" class="small">ID</td> 
       <td rowspan="2" class="small" style="width:110px">
       <textarea cols="16" rows="2" readonly="readonly">${RBSemiIn.id}</textarea>
       </td> 
       <td rowspan="6" style="width:166px;">半成品电线截面图</td> 
       <td style="width:190px" class="small">详细参数</td> 
       <td style="width:80px" class="small">标准值</td> 
       <td style="width:80px" class="small">误差范围</td> 
	   <td style="width:80px" class="small">测量值</td>
      </tr> 
      <tr> 
       <td class="small" style="width:190px">绞合外径/mm</td> 
       <td class="small" style="width:80px">${RBSemiIn.standardDia?RBSemiIn.standardDia:RBSemiIn.afterDia}</td> 
       <td style="width:80px"></td> 
	   <td class="tdInput "style="width:80px"></td>  
      </tr> 
      <tr> 
       <td rowspan="2" style="width:60px" class="small">批次号</td> 
       <td class="tdInput" rowspan="2" style="width:110px"></td> 
       <td class="small" style="width:190px">单元电线质量/(kg/km)</td> 
       <td class="small" style="width:80px">${RBSemiIn.standardWeight}</td> 
       <td style="width:80px"></td> 
	   <td class="tdInput "style="width:80px"></td>  
      </tr> 
      <tr> 
       <td class="small" style="width:190px">&nbsp</td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td>  
      </tr> 
      <tr> 
       <td rowspan="2" style="width:60px; height:56px" class="small"></td> 
       <td rowspan="2" style="width:110px"></td> 
       <td class="small" style="width:190px"></td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td> 
      </tr> 
      <tr>
       <td></td> 
       <td></td> 
       <td></td> 
	   <td></td> 
      </tr>
     </tbody>
    </table> 
    </div>
    <!-- END 半成品 -->
    </c:if>
    
    <c:forEach items="${RBMaterialDCLists}" var="item" varStatus="status">
    	<div class="table_inner">
    	<table id="dynamicTable" class="printtTable table_box box3_w"> 
     <tbody>
      <tr>
       <td rowspan="8" class="title" style="width:60px">
        <div class="writing_tb">
        <c:choose>
        	<c:when test="${status.count==1}">包带一</c:when>
        	<c:when test="${status.count==2}">包带二</c:when>
        	<c:when test="${status.count==3}">包带三</c:when>
        	<c:when test="${status.count==4}">包带四</c:when>
        	<c:otherwise>包带五</c:otherwise>
        </c:choose>
        </div></td> 
        <td style="width:190px" class="small">ID</td>
        <td class="small" style="width:190px">${item.id}</td>
        <td rowspan="8" style="width:190px"><c:choose>
        	<c:when test="${status.count==1}">包带一</c:when>
        	<c:when test="${status.count==2}">包带二</c:when>
        	<c:when test="${status.count==3}">包带三</c:when>
        	<c:when test="${status.count==4}">包带四</c:when>
        	<c:otherwise>包带五</c:otherwise>
        </c:choose></td>
      </tr>
      <tr>
      	<td class="small">批次号</td>
        <td class="tdInput"></td>
      </tr>
      <tr>
      	<td class="small">宽度/mm</td>
        <td class="small">${item.width}</td>
      </tr>
      <tr>
      	<td class="small">厚度/mm</td>
        <td class="small">${item.thickness}</td>
      </tr>
      <tr>
      	<td class="small">颜色</td>
        <td class="small">${item.color}</td>
      </tr>
      <tr>
      	<td class="small">材料</td>
        <td class="small">${item.material}</td>
      </tr>
      <tr>
      	<td class="small">标准消耗量/m</td>
        <td class="small">
        	<fmt:formatNumber value="${item.quantity*length/1000}" pattern="#.##">
        	</fmt:formatNumber>       	
        </td>
      </tr>   
      <tr>
      	<td class="small">实际消耗量/m</td>
        <td></td>
      </tr>    
     </tbody>
    </table> 
    </div>
    </c:forEach>
     
   </div> 
   </div>
   <!-- END 原材料控制 --> 
  </div> 
  </div>
  <!-- END 绝缘单元操作 --> 
