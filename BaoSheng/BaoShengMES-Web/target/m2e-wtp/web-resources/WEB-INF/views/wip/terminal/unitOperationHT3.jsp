<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="table_box box1_w"> 
   <!-- 产品和检验 --> 
   <h1 style="width:270px;height:45px;margin-left:20px;">产品和检验</h1> 
   <table class="printtTable table_box box2_w"> 
    <!-- 编织成品 --> 
    <tbody>
     <tr> 
      <td rowspan="7" class="title">
       <div class="writing_tb">
        成品电缆
       </div></td> 
	  <td class="small" style="width:134px"></td>
      <td style="width:106px">标准值</td>
	  <td style="width:106px">实际值</td> 	  
      <td style="width:138px" rowspan="7">合格绕包线示例图</td> 
      <td style="width:138px" rowspan="7">漏包线示例图</td> 
	  <td style="width:138px" rowspan="7">松散折皱示例图</td>
     </tr>
	 <tr>
	  <td class="small" style="width:134px">ID</td>
      <td class="small" style="width:106px" colspan="2">${HTSemiOut.id}</td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">批次号</td>
      <td class="tdInput" style="width:106px" colspan="2"></td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">表面质量</td>
      <td class="tdInput" style="width:106px" colspan="2"><input class="needInput" type="text"></td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">计量长度</td>
      <td class="small" style="width:106px">${length }</td> 
	  <td style="width:106px" class="tdInput"><input class="needInput" type="text"></td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">颜色</td>
      <td class="small" style="width:106px">${HTSemiOut.color}</td> 
	  <td style="width:106px" class="tdInput"><input class="needInput" type="text"></td> 
	 </tr>
	 <tr>
	  <td class="small" style="width:134px">电缆料消耗量/kg</td>
      <td class="small" style="width:106px">
      	<c:set var="sum" value="0"></c:set>
      	<c:forEach items="${HTMaterialJYS}" var="item"> 
      		<c:set var="sum" value="${sum+item.quantity}"></c:set>	
      	</c:forEach>
      	<fmt:formatNumber value="${sum*(length/1000)}" pattern="#.##"/>
      </td> 
	  <td style="width:106px" class="tdInput"><input class="needInput" type="text"></td> 
	 </tr>
    </tbody>
   </table> 
   <!-- END 编织成品 --> 
  <table class="printtTable table_box box2_w"> 
    <!-- 测量检验 --> 
    <tbody>
     <tr> 
      <td rowspan="6" class="title">
       <div class="writing_tb">
        成品检验
       </div></td> 
      <td></td> 
      <td>标准值</td> 
      <td>误差范围</td> 
      <td colspan="5">测量值</td>
      <td style="width:100px;">合格签字</td> 
     </tr> 
     <tr> 
      <td class="small">最薄点厚度/mm</td> 
      <td class="small" style="width:58px;">${HTQc.minThickness}</td> 
      <td style="width:70px;"></td> 
      <td class="tdInput" style="width:58px;" ></td>  
      <td class="tdInput" style="width:58px;" ></td> 
      <td class="tdInput" style="width:58px;" ></td>  
      <td class="tdInput" style="width:58px;" ></td>
      <td class="tdInput" style="width:58px;" ></td>
      <td class="tdInput" rowspan="5" ></td> 
     </tr> 
     <tr> 
      <td class="small">护套厚度/mm</td> 
      <td class="small" style="width:58px;">${HTQc.standardThickness}</td> 
      <td style="width:70px;"></td> 
      <td class="tdInput" style="width:58px;" ></td>  
      <td class="tdInput" style="width:58px;" ></td> 
      <td class="tdInput" style="width:58px;" ></td>  
      <td class="tdInput" style="width:58px;" ></td>
      <td class="tdInput" style="width:58px;" ></td>
     </tr> 
     <tr> 
      <td class="small">成品电缆外径/mm</td> 
      <td class="small" style="width:58px;">${HTQc.standardDia}</td> 
      <td style="width:70px;"></td> 
      <td class="tdInput" style="width:58px;" ></td>  
      <td class="tdInput" style="width:58px;" ></td> 
      <td class="tdInput" style="width:58px;" ></td>  
      <td class="tdInput" style="width:58px;" ></td>
      <td class="tdInput" style="width:58px;" ></td>
     </tr>
     <tr> 
      <td class="small">同心度计算</td> 
      <td class="small" style="width:58px;">${HTQc.eccentricity}</td> 
      <td style="width:70px;"></td> 
      <td class="tdInput" style="width:58px;" ></td>  
      <td class="tdInput" style="width:58px;" ></td> 
      <td class="tdInput" style="width:58px;" ></td>  
      <td class="tdInput" style="width:58px;" ></td>
      <td class="tdInput" style="width:58px;" ></td>
     </tr>
    </tbody>
   </table> 
  <!-- 产品和检验 -->  
  <div class="table_box box2_w" style="border:none;">
     注:
    <br /> &nbsp;&nbsp;①“异常备注”是指在生产过程中，工人根据以往生产经验，发现的可能会影响产品质量单在单元表格中又没有数据规定或说明的情况。
    <br /> &nbsp;&nbsp;②“生产确认”是指工人在完成相关操作后，对所有的相关参数与标准值或设定值进行比较，确认是否合格 
   </div> 
</div>
