<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table_outter">
<div class="table_box box1_w"> 
   <!-- 流程、设备和参数 --> 
   <h1 style="width:320px;height:45px;margin-left:20px;">流程、设备和参数</h1> 
   <div class="table_">
   <div class="table_box box2_w" style="padding:30px 10px 30px 4px;"> 
    <!-- 流程 --> 
    <table class="nav_table"> 
     <tbody>
      <tr> 
       <td style="width:45px;border:none;" rowspan="2">
        <div class="writing_tb" style="font-size:20px;">
         流程
        </div></td> 
       <td class="small">原料</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">放线、收线</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">引线</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">绕包头</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">排线</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">开机</td> 
      </tr> 
      <tr> 
       <td class="small">核对确认</td> 
       <td class="small">上盘调节张力</td> 
       <td class="small">安装</td> 
       <td class="small">上料、调节各项参数</td> 
       <td class="small">调节各项参数</td> 
       <td class="small">设定各项参数、实时调节确认</td> 
      </tr> 
     </tbody>
    </table> 
   </div> 
   </div>
   <!-- 流程 --> 
   <div class="table_">
   <table class="printtTable table_box box2_w"> 
    <!-- 设备 --> 
    <tbody>
     <tr> 
      <td rowspan="2" class="title">
       <div class="writing_tb">
        设备
       </div></td> 
      <td style="width:140px">名称</td> 
      <td style="width:210px">型号</td> 
      <td style="width:180px">编号</td> 
      <td style="width:150px">开机前检验</td> 
     </tr> 
     <tr> 
      <td class="small">${equipInfo.code}</td> 
      <td class="small">${equipInfo.name}</td> 
      <td class="small">${equipInfo.equipAlias}</td> 
      <td class="tdInput"></td> 
     </tr> 
    </tbody>
   </table> 
   </div>
   <!-- END 设备 --> 
   <div class="table_">
   <table class="printtTable table_box box2_w"> 
    <!-- 放线盘 --> 
    <tbody>
     <tr> 
      <td rowspan="4" class="title">
       <div class="writing_tb">
        放线盘
       </div></td> 
      <td class="small" style="width:140px">ID</td> 
      <td style="width:140px"></td> 
      <td rowspan="4" class="title">
       <div class="writing_tb">
        收线盘
       </div></td> 
      <td class="small" style="width:140px">ID</td> 
      <td style="width:140px"></td> 
     </tr> 
     <tr> 
      <td class="small">最大重量/kg</td> 
      <td></td> 
      <td class="small">最大重量/kg</td> 
      <td></td> 
     </tr> 
     <tr> 
      <td class="small">最大长度/km</td> 
      <td></td> 
      <td class="small">最大长度/km</td> 
      <td></td> 
     </tr> 
     <tr> 
      <td class="small">张力确认</td> 
      <td class="tdInput"></td> 
      <td class="small">张力确认</td> 
      <td class="tdInput"></td> 
     </tr> 
    </tbody>
   </table> 
   </div>
   <!-- END 放线盘 --> 
   <c:forEach items="${RBMaterialDCLists}" var="item" varStatus="status">
   	 <div class="table_">
   	 <table class="printtTable table_box box2_w"> 
     <tbody>
	 <tr>
	 <td></td>
	 <td class="small" colspan="2" style="width:136px">所用包带信息</td>
	 <td class="small" style="width:86px">工作参数</td>
	 <td class="small" style="width:60px">设定值</td>
	 <td class="small" style="width:84px">误差范围</td>
	 <td class="small" colspan="5" style="width:320px">实时测量值</td>
	 </tr>
      <tr>
       <td rowspan="4" class="title">
        <div class="writing_tb">
      <c:choose>
        	<c:when test="${status.count==1}">绕包头一</c:when>
        	<c:when test="${status.count==2}">绕包头二</c:when>
        	<c:when test="${status.count==3}">绕包头三</c:when>
        	<c:when test="${status.count==4}">绕包头四</c:when>
        	<c:otherwise>绕包头五</c:otherwise>
        </c:choose>
        </div></td>
       <td class="small" style="width:38px">ID</td> 
	   <td class="small" "width:98px">${item.id}</td> 
       <td class="small" style="width:86px">绕包方向</td>
	   <td class="small" style="width:60px">${RBQc.RBDirect}</td>
	   <td style="width:84px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   </tr>
	   <tr>
       <td class="small" style="width:38px">颜色</td> 
	   <td class="small" style="width:98px">${item.color}</td> 
       <td class="small" style="width:86px">转速/rpm</td>
	   <td style="width:60px"></td>
	   <td style="width:84px"></td>
	   <td class="tdInput" style="width:64px"></td>
	   <td class="tdInput" style="width:64px"></td>
	   <td class="tdInput" style="width:64px"></td>
	   <td class="tdInput" style="width:64px"></td>
	   <td class="tdInput" style="width:64px"></td>
	   </tr>
	   <tr>
       <td class="small" style="width:38px">材料</td> 
	   <td class="small" style="width:98px">${item.material}</td> 
       <td class="small" style="width:86px">张力/N</td>
	   <td style="width:60px"></td>
	   <td style="width:84px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   </tr>
	   <tr>
       <td class="small" style="width:38px">宽度</td> 
	   <td class="small" style="width:98px">${item.width}</td> 
       <td class="small" style="width:86px">节距/mm</td>
	   <td style="width:60px"></td>
	   <td style="width:84px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   <td style="width:64px"></td>
	   </tr>
      </tr> 
     </tr>
     </tbody>
    </table> 
    </div>
   </c:forEach>
   <div class="table_"> 
   <table class="printtTable table_box box2_w"> 
    <!-- 牵引速度 --> 
    <tbody>
     <tr> 
      <td rowspan="2" class="title">
       <div class="writing_tb" style="width:74px">
        牵引速度
		/rpm
       </div></td> 
      <td class="small" style="width:56px">设定值</td> 
      <td class="small" style="width:70px">误差范围</td> 
	  <td class="small" style="width:524px" colspan="8">实时测量值</td> 
     </tr> 
     <tr> 
      <td>&nbsp</td>
	  <td></td>
	  <td class="tdInput"></td>
	  <td class="tdInput"></td>
	  <td class="tdInput"></td>
	  <td class="tdInput"></td>
	  <td class="tdInput"></td>
	  <td class="tdInput"></td>
	  <td class="tdInput"></td>
	  <td class="tdInput"></td>
     </tr> 
     </tr> 
    </tbody>
   </table> 
   </div>
   <!-- END 牵引速度 --> 
   
  </div> 
  </div>