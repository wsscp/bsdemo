<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  <div class="table_box box1_w"> 
   <!-- 绝缘单元操作 --> 
   <h1 style="width:180px;height:65px;margin:auto;margin-top: 20px;">护套单元操作</h1> 
   <table class="printtTable table_box" style="width:756px;"> 
    <!-- 批次号/条形码 --> 
    <tbody>
	<tr>
	<input type="checkbox" style="margin-left:16px;"><span style="font-size: 20px;color: #8A2BE2">内护</span>
	&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	<input type="checkbox"><span style="font-size: 20px;color: #8A2BE2">外护</span>
	</tr>
     <tr> 
      <td class="tdInput" style="width:275px;height:95px;text-align:left;">产品批号:</td> 
      <td style="width:485px;" rowspan="2"> 
       <table> 
        <tbody>
         <tr> 
          <td rowspan="2" style="width:60px;">
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
   <!-- END 批次号/条形码 --> 
   <div class="table_box box2_w"> 
    <!-- 原材料控制 --> 
    <h3 style="margin-left:20px;">原材料控制</h3> 
    <c:if test="${not empty HTMaterialDT}">
    <table class="printtTable table_box box3_w"> 
     <!-- 导体 --> 
     <tbody>
      <tr> 
       <td rowspan="6" class="title">
        <div class="writing_tb">
         导体
        </div></td> 
       <td rowspan="2" style="width:60px" class="small">ID</td> 
       <td class="small" rowspan="2" style="width:110px"></td> 
       <td rowspan="6" style="width:166px;">导体截面图</td> 
       <td style="width:190px" class="small">详细参数</td> 
       <td style="width:80px" class="small">标准值</td> 
       <td style="width:80px" class="small">误差范围</td> 
	   <td style="width:80px" class="small">测量值</td>
      </tr> 
      <tr> 
       <td class="small" style="width:190px">单丝外径/mm</td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td class="tdInput" style="width:80px"></td> 
      </tr> 
      <tr> 
       <td rowspan="2" style="width:60px" class="small">批次号</td> 
       <td class="tdInput" rowspan="2" style="width:110px"></td> 
       <td class="small" style="width:190px">导体外径/mm</td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td class="tdInput" style="width:80px"></td>  
      </tr> 
      <tr> 
       <td class="small" style="width:190px">单元导体质量/（kg/km）</td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td>  
      </tr> 
      <tr> 
       <td rowspan="2" style="width:60px" class="small">材料</td> 
       <td rowspan="2" style="width:110px"><input type="text" /></td> 
       <td class="small" style="width:190px">导体直流电阻/（ohm/km）</td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td> 
      </tr> 
      <tr>
       <td style="width:190px"></td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td> 
      </tr>
     </tbody>
    </table> 
    </c:if>
    <!-- END 导体 --> 
<table class="printtTable table_box box3_w"> 
     <tbody>
      <tr> 
       <td rowspan="7" class="title">
        <div class="writing_tb">
         半成品电线
        </div></td> 
       <td rowspan="2" style="width:60px" class="small">ID</td> 
       <td class="small" rowspan="2" style="width:110px">${HTSemiIn.id}</td> 
       <td rowspan="6" style="width:166px;">半成品电线截面图</td> 
       <td style="width:190px" class="small">参数</td> 
       <td style="width:80px" class="small">标准值</td> 
       <td style="width:80px" class="small">误差范围</td> 
	   <td style="width:80px" class="small">测量值</td>
      </tr> 
      <tr> 
       <td class="small" style="width:190px">半成品电线外径/mm</td> 
       <td class="small" style="width:80px">${HTSemiIn.standardDia}</td>
       <td style="width:80px"></td> 
	   <td class="tdInput" style="width:80px"></td> 
      </tr> 
      <tr> 
       <td rowspan="2" style="width:60px" class="small">批次号</td> 
       <td class="tdInput" rowspan="2" style="width:110px"></td> 
       <td class="small" style="width:190px" >表面质量</td> 
       <td class="tdInput" style="width:80px" colspan="3"><input class="needInput" type="text"></td> 
      </tr>  
      <tr>
       <td style="width:190px">&nbsp</td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td> 
      </tr>
      <tr> 
       <td rowspan="2" style="width:60px" class="small">&nbsp</td> 
       <td rowspan="2" style="width:110px"></td> 
       <td class="small" style="width:190px"></td> 
       <td style="width:80px">&nbsp</td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td> 
      </tr>
      <tr>
       <td style="width:190px">&nbsp</td> 
       <td style="width:80px"></td> 
       <td style="width:80px"></td> 
	   <td style="width:80px"></td> 
      </tr> 
     </tbody>
    </table> 
   <table class="printtTable table_box box3_w"> 
		<tbody>
			<td rowspan="4" class="title">
				<div class="writing_tb">
				电缆料
				</div></td>
			<td style="width:80px"></td> 
			<td style="width:120px">ID</td> 
			<td style="width:130px">批次号</td> 
			<td style="width:95px">材料名称</td>
			<td style="width:60px">颜色</td>
			</tr> 
			<c:forEach items="${HTMaterialJYS}" var="HTMaterialJY" varStatus="status">
				<tr> 
				<td>
					<c:choose>
						<c:when test="${status.count==1}">第一层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==2}">第二层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==3}">第三层</c:when>
					</c:choose>
				</td> 
				<td class="small">${HTMaterialJY.id}</td> 
				<td class="tdInput"></td> 
				<td class="small">${HTMaterialJY.name}</td> 
				<td class="small">${HTMaterialJY.color}</td> 
			</tr> 
			</c:forEach>
		</tbody>	
	</table>
	
 	<c:if test="${not empty HTMaterialSMS}"> 
	<table class="printtTable table_box box3_w"> 
		<tbody>
			<td rowspan="4" class="title">
				<div class="writing_tb">
				色母料
				</div></td>
			<td style="width:80px"></td> 
			<td style="width:120px">ID</td> 
			<td style="width:130px">批次号</td> 
			<td style="width:95px">颜色</td>
			<td style="width:60px">配比</td>
			</tr> 
			<c:forEach items="${HTMaterialSMS}" var="HTMaterialSM" varStatus="status">
				<tr> 
				<td>
					<c:choose>
						<c:when test="${status.count==1}">第一层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==2}">第二层</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${status.count==3}">第三层</c:when>
					</c:choose>
				</td> 
				<td class="small">${HTMaterialSM.id}</td> 
				<td class="tdInput"></td> 
				<td>${HTMaterialSM.color}</td> 
				<td></td>
				</tr> 
			</c:forEach>
		</tbody>	
	</table>
	</c:if> 
   <!-- END 电缆料 --> 
   
    <!-- END 绝缘料 --> 
   </div> 
   <!-- END 原材料控制 --> 
  </div> 
  <!-- END 绝缘单元操作 --> 
