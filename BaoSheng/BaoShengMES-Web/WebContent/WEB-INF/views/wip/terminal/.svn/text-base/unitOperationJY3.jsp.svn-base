<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="table_outter">
<div class="table_box box1_w"> 
   <!-- 产品和检验 --> 
   <h1 style="width:270px;height:45px;margin-left:20px;">产品和检验</h1> 
    <div class="table_">
   <table class="printtTable table_box box2_w"> 
    <!-- 编织成品 --> 
    <tbody>
     <tr> 
      <td rowspan="6" class="title">
       <div class="writing_tb">
        成品电线
       </div></td> 
	  <td class="small" style="width:134px"></td>
      <td class="small" style="width:106px">标准值</td>
	  <td class="small" style="width:106px">实际值</td> 	  
      <td style="width:138px" rowspan="6">成品电线视图</td> 
      <td style="width:138px" rowspan="6">合格绝缘视图</td> 
	  <td style="width:138px" rowspan="6">不合格绝缘视图</td>
     </tr>
	 <tr>
	  <td class="small" style="width:134px">ID</td>
      <td class="small" colspan="2">${JYSemiOut.id}</td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">批次号</td>
      <td class="tdInput" style="width:106px" colspan="2"></td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">长度/km</td>
      <td class="small" style="width:106px">${length/1000}</td> 
	  <td style="width:106px" class="tdInput"></td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">颜色</td>
      <td class="small" colspan="2" style="width:106px">
      	${colors}
      </td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">电缆料消耗量/kg</td>
      <td class="small" tyle="width:106px">
      	<c:set var="sum" value="0"></c:set>
      	<c:forEach items="${JYMaterialJYS}" var="item"> 
      		<c:set var="sum" value="${sum+item.quantity}"></c:set>	
      	</c:forEach>
      	<fmt:formatNumber value="${sum*(length/1000)}" pattern="#.##"/>
      			
      </td> 
	  <td style="width:106px" class="tdInput"></td> 
	 </tr>
    </tbody>
   </table>  
   </div>
   <div class="table_">
   <table class="printtTable table_box box2_w" cellPadding="0"> 
    <!-- 测量检验 --> 
    <tbody>
     <tr> 
      <td rowspan="6" class="title">
       <div class="writing_tb">
       成品检验
       </div></td> 
      <td width="134px"></td> 
      <td class="small" width="100px">标准值</td> 
      <td class="small" width="100px">误差范围</td> 
      <td class="small" colspan="5">测量值</td> 
      <td class="small">平均值</td> 
      <td class="small" style="width:72px;">合格签字</td> 
     </tr> 
     <tr> 
      <td class="small">最薄点厚度/mm</td> 
      <td class="small">${JYQc.minThickness}</td> 
      <td></td> 
      <td class="tdInput" width="50px"></td> 
      <td class="tdInput" width="50px"></td>  
      <td class="tdInput" width="50px"></td> 
      <td class="tdInput" width="50px"></td>  
      <td class="tdInput" width="50px"></td> 
      <td class="tdInput" width="50px"></td> 
      <td class="tdInput" rowspan="5"></td> 
     </tr> 
     <tr> 
      <td class="small">绝缘厚度/mm</td> 
      <td class="small">${JYQc.standardThickness}</td> 
      <td></td> 
 	  <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>   
     </tr> 
     <tr> 
      <td class="small">成品电线外径</td> 
      <td class="small">${JYQc.standardDia}</td> 
      <td></td> 
	  <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
     </tr> 
     <tr> 
      <td class="small">同心度计算</td> 
      <td class="small">${JYQc.eccentricity}</td> 
      <td></td> 
	  <td class="tdInput" ></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
     </tr> 
     <tr>
      <td class="small" >绝缘表面质量</td> 
      <td class="tdInput" colspan="8"></td> 
     </tr>
    </tbody>
   </table> 
</div>	
  <!-- 产品和检验 -->    
  <div class="text" style="border:none;margin-bottom:5px;">
     注:
    <br /> &nbsp;&nbsp;①"异常备注 "是指在生产过程中，工人根据以往生产经验，发现的可能会影响产品质量单在单元表格中又没有数据规定或说明的情况。
    <br />&nbsp;&nbsp;②"生产确认 "是指工人在完成相关操作后，对所有的相关参数与标准值或设定值进行比较，确认是否合格 
   </div> 
   
   </div>
</div>
