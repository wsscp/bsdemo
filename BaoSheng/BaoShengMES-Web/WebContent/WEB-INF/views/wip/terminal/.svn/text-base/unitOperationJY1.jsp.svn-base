<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table_outter">
<div class="table_box box1_w"> 
   <!-- 绝缘单元操作 --> 
   <div class="h_style1">
   <h1 style="width:220px;height:60px;margin:auto;margin-top:20px;">绝缘单元操作</h1> 
   </div>
   <div class="table_">
   <table class="printtTable table_box"> 
    <!-- 批次号/条形码 --> 
    <tbody>
     <tr> 
      <td class="tdInput" style="width:275px;height:95px;text-align:left;">产品批号:${series}</td> 
      <td style="width:485px;"rowspan="2"> 
       <table> 
        <tbody>
         <tr> 
          <td class="imgFrame" rowspan="2" style="width:60px;">
           <div class="writing_tb">
            操作简图
           </div>
           </td> 
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
          <td class="center">挤出机</td> 
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
    <c:if test="${!empty JYMaterialDT}">
    <div class="table_inner">
    <table class="printtTable table_box box3_w"> 
     <!-- 半成品线芯 --> 
     <tbody>
      <tr> 
       <td rowspan="6" class="title">
        <div class="writing_tb">
         导体
        </div></td> 
       <td class="small" rowspan="2" style="width:60px">ID</td> 
       <td class="small" rowspan="2" style="width:110px">${JYMaterialDT.id}</td> 
       <td rowspan="6" style="width:166px;">导体截面图</td> 
       <td class="small" style="width:190px">详细参数</td> 
       <td class="small" style="width:80px">标准值</td> 
       <td class="small" style="width:80px">误差范围</td> 
	   <td class="small" style="width:80px">测量值</td>
      </tr> 
      <tr> 
       <td class="small" style="width:190px">单丝直径/mm</td> 
       <td class="small" style="width:80px">${JYQc.singleDia}</td> 
       <td style="width:80px"></td> 
	   <td class="tdInput" style="width:80px"></td> 
      </tr> 
      <tr> 
       <td class="small" rowspan="2">批次号</td> 
       <td class="tdInput" rowspan="2" ></td> 
       <td class="small">导体外径/mm</td> 
       <td class="small" style="width:80px">${JYMaterialDT.standardDia}</td> 
       <td style="width:80px"></td> 
	   <td class="tdInput" style="width:80px"></td>  
      </tr> 
      <tr> 
       <td class="small">单元导体质量/(kg/km)</td> 
       <td class="small" style="width:80px">${JYMaterialDT.quantity}</td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td>  
      </tr> 
      <tr> 
       <td class="small" rowspan="2">材料</td> 
       <td class="small" rowspan="2">${JYMaterialDT.name }</td> 
       <td class="small">导体直流电阻/(ohm/km)</td> 
       <td class="small" style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td> 
      </tr> 
      <tr height="20px">
       <td style="width:190px"></td>
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td>  
      </tr>
     </tbody>
    </table> 
    </div>
    </c:if>
    <!-- END 导体 --> 
    
	<c:if test="${not empty JYSemiIn}">
	<div class="table_inner">
    <table class="printtTable table_box box3_w"> 
     <tbody>
      <tr> 
       <td rowspan="6" class="title">
        <div class="writing_tb">
         半成品
        </div></td> 
       <td class="small" rowspan="2" style="width:110px">ID</td> 
       <td class="small" rowspan="2" style="width:130px">${JYSemiIn.id}</td> 
       <td rowspan="6" style="width:166px;">导体截面图</td> 
       <td class="small" style="width:120px">详细参数</td> 
       <td class="small" style="width:80px">标准值</td> 
       <td class="small" style="width:80px">误差范围</td> 
	   <td class="small" style="width:80px">测量值</td>
      </tr> 
      <tr> 
       <td class="small" style="width:190px">搭盖率</td> 
       <td class="small" style="width:80px">${JYSemiIn.coverRate}</td> 
       <td style="width:80px"></td> 
	   <td class="tdInput" style="width:80px"></td> 
      </tr> 
      <tr> 
       <td class="small" rowspan="2">批次号</td> 
       <td class="tdInput" rowspan="2" ></td> 
       <td class="small">导体外径/mm</td> 
       <td class="small" style="width:80px">${JYSemiIn.standardDia}</td> 
       <td style="width:80px"></td> 
	   <td class="tdInput" style="width:80px"></td>  
      </tr> 
      <tr> 
       <td class="small">表面质量</td> 
       <td class="small" colspan="3">
       	(<input type="checkbox" >有&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp无<input type="checkbox">)&nbsp&nbsp&nbsp翘边、分层
       </td> 
      </tr> 
      <tr> 
       <td class="small" height="48px" rowspan="2">绕包材料</td> 
       <td class="small" rowspan="2">${JYSemiIn.material}</td> 
       <td class="small"></td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td> 
      </tr> 
      <tr>
       <td style="width:160px"></td>
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td>  
      </tr>
     </tbody>
    </table> 
    </div>
    </c:if>
    <!-- END 半成品 --> 
	
	<div class="table_inner">
	<table class="printtTable table_box box3_w"> 
		<tbody>
			<tr>
			<td rowspan="6" class="title">
				<div class="writing_tb">
				电缆料
				</div></td>
			<td style="width:100px"></td> 
			<td class="small" style="width:140px">ID</td> 
			<td class="small" style="width:130px">批次号</td> 
			<td style="width:140px">材料名称</td>
			<td class="small" style="width:60px">颜色</td>
			</tr> 
			<c:forEach items="${JYMaterialJYS}" var="JYMaterialJY" varStatus="status">
				<tr> 
				<td class="small">
					<c:choose>
						<c:when test="${status.count==1}">第一层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==2}">第二层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==3}">第三层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==4}">第四层</c:when>
					</c:choose>
				</td> 
				<td class="small">${JYMaterialJY.id}</td> 
				<td class="tdInput"></td> 
				<td class="small">${JYMaterialJY.name}</td> 
				<td class="small">${JYMaterialJY.color}</td> 
			</tr> 
			</c:forEach>
		</tbody>	
	</table>
	</div>
	
 	<c:if test="${!empty JYMaterialSMS}"> 
 	<div class="table_inner">
	<table class="printtTable table_box box3_w"> 
		<tbody>
			<tr>
			<td rowspan="6" class="title">
				<div class="writing_tb">
				色母料
				</div></td>
			<td style="width:100px"></td> 
			<td class="small" style="width:130px">ID</td> 
			<td class="small" style="width:140px">批次号</td> 
			<td class="small" style="width:140px">颜色</td>
			<td class="small" style="width:60px">配比</td>
			</tr> 
			<c:forEach items="${JYMaterialSMS}" var="JYMaterialSM" varStatus="status">
				<tr> 
				<td class="small">
					<c:choose>
						<c:when test="${status.count==1}">第一层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==2}">第二层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==3}">第三层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==4}">第四层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==5}">第五层</c:when>
					</c:choose>
				</td> 
				<td class="small">${JYMaterialSM.id}</td> 
				<td class="tdInput"></td> 
				<td class="small">${JYMaterialSM.color}</td> 
				<td></td>
				</tr> 
			</c:forEach>
		</tbody>	
	</table>
	</div>
	</c:if> 
    <!-- END 绝缘料 --> 
   </div> 
   </div>
   <!-- END 原材料控制 --> 
  </div> 
  </div>