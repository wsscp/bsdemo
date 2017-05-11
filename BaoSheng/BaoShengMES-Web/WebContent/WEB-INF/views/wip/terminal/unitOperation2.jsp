<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
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

  <div class="table_box box1_w"> 
   <!-- 流程、设备和参数 --> 
   <h1 style="width:320px;height:45px;margin-left:20px;">流程、设备和参数</h1> 
   <div class="table_box box2_w" style="padding:30px 10px 30px 4px;"> 
    <!-- 流程 --> 
    <table class="nav_table"> 
     <tbody>
      <tr> 
       <td style="width:45px;border:none;" rowspan="2">
        <div class="writing_tb" style="font-size:20px;">
         流程
        </div></td> 
       <td class="small">设备</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">原材料、线芯</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">齿轮和模具</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">线锭</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">收放线盘</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">开机</td> 
      </tr> 
      <tr> 
       <td class="small">空转检查</td> 
       <td class="small">核对检查</td> 
       <td class="small">安装</td> 
       <td class="small">安装、调节张力</td> 
       <td class="small">安装、调节张力</td> 
       <td class="small">调节参数</td> 
      </tr> 
     </tbody>
    </table> 
   </div> 
   <!-- 流程 --> 
   <table class="printtTable table_box box2_w"> 
    <!-- 设备 --> 
    <tbody>
     <tr> 
      <td rowspan="2" class="title" cellPadding="0">
       <div class="writing_tb">
        设备
       </div></td> 
      <td height="20px">ID</td> 
      <td>名称</td> 
      <td>设备别名</td> 
      <td>开机检验</td> 
     </tr> 
     <tr> 
      <td class="small" height="20px">${equipInfo.code}</td> 
      <td class="small">${equipInfo.name}</td> 
      <td class="small">${equipInfo.equipAlias}</td> 
      <td class="tdInput"></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 设备 --> 
   <table class="printtTable table_box box2_w" cellPadding="0"> 
    <!-- 放线盘 --> 
    <tbody>
     <tr> 
      <td rowspan="5" class="title">
       <div class="writing_tb">
        放线盘
       </div></td> 
      <td>ID</td> 
      <td width="120px">&nbsp</td> 
      <td rowspan="5" class="title">
       <div class="writing_tb">
        收线盘
       </div></td> 
      <td>ID</td> 
      <td width="120px">&nbsp</td> 
     </tr> 
     <tr> 
      <td>盘径/mm</td> 
      <td></td> 
      <td>盘径/mm</td> 
      <td></td> 
     </tr> 
     <tr> 
      <td>最大承重量/kg</td> 
      <td></td> 
      <td>最大承重量/kg</td> 
      <td></td> 
     </tr> 
     <tr> 
      <td>最大长度/km</td> 
      <td></td> 
      <td>最大长度/km</td> 
      <td></td> 
     </tr> 
     <tr> 
      <td height="20px">张力确认</td> 
      <td class="tdInput"></td> 
      <td>张力确认</td> 
      <td class="tdInput"></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 放线盘 --> 
   <table class="printtTable table_box box2_w" cellPadding="0"> 
    <!-- 编织机主机 --> 
    <tbody>
     <tr> 
      <td rowspan="8" class="title">
       <div class="writing_tb">
        编织机主机
       </div></td> 
      <td colspan="2" style="text-align:center;">信息</td> 
      <td>工作参数</td> 
      <td>设定值</td> 
      <td width="100px">误差范围</td> 
      <td colspan="5" style="text-align:center; width: 250px">实时测量值</td> 
     </tr> 
     <tr> 
      <td rowspan="2" class="small">ID</td> 
      <td rowspan="2" width="58px"></td> 
      <td class="small">锭数</td> 
      <td class="small" width="100px">${BZQcParam.spindle}</td>
      <td colspan="6"></td> 
     </tr> 
     <tr>
     	<td class="small">旋转速度/rpm</td>
     	<td></td> 
      	<td width="100px"></td> 
      	<td class="tdInut" width="50px"></td> 
      	<td class="tdInut" width="50px"></td> 
      	<td class="tdInut" width="50px"></td> 
      	<td class="tdInut" width="50px"></td> 
        <td class="tdInut" width="50px"></td> 
     </tr>
     <tr> 
      <td rowspan="3" class="small">类型</td>       
      <td rowspan="3"></td> 
      <td class="small">牵引速度(m/min)</td> 
      <td></td> 
      <td></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
     </tr> 
     <tr>
      <td class="small">编织外径</td> 
      <td class="small"></td> 
      <td></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
     </tr>
     <tr> 
      <td class="small">编织节距</td> 
      <td class="small">${BZQcParam.bzStep}</td> 
      <td></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
     </tr> 
     <tr> 
      <td rowspan="2" class="small"></td> 
      <td rowspan="2"></td> 
      <td class="small">编织角</td> 
      <td class="small">${BZQcParam.bzAngle}</td> 
      <td class="small"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
     </tr> 
     <tr> 
      <td class="small">编织密度</td> 
      <td class="small">${BZQcParam.bzDensity}</td> 
      <td></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
      <td class="tdInut" width="50px"></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 编织机主机 --> 
   <table class="printtTable table_box box2_w"> 
    <!-- 模具 --> 
    <tbody>
     <tr> 
      <td rowspan="2" class="title">
       <div class="writing_tb">
        模具
       </div></td> 
      <td height="20px">ID</td> 
      <td>名称</td> 
      <td>规格</td> 
      <td>开机检验</td> 
     </tr> 
     <tr> 
      <td height="20px"></td> 
      <td></td> 
      <td></td> 
      <td class="tdInput"></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 模具 --> 
   <table class="printtTable table_box box2_w" cellPadding="0"> 
    <!-- 异常备注 --> 
    <tbody>
     <tr> 
      <td rowspan="2" class="title">
       <div class="writing_tb">
        备注
       </div></td> 
      <td class="tdInput" rowspan="2"></td> 
      <td width="100px">生产确认</td> 
      </tr>
     <tr> 
      <td class="tdInput" height="25px"></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 异常备注 --> 
  </div> 