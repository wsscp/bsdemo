<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
       <td class="small">原料</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">放线、收线</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">引线</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">挤出机</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">排线</td> 
       <td class="arrow-right" rowspan="2"><i class="fa fa-arrow-right fa-lg"></i></td> 
       <td class="small">开机</td> 
      </tr> 
      <tr> 
       <td class="small">核对确认</td> 
       <td class="small">上盘调节张力</td> 
       <td class="small"></td> 
       <td class="small">安装、调节张力</td> 
       <td class="small">调节各项参数</td> 
       <td class="small">设定各项参数，实时调节确认</td> 
      </tr> 
     </tbody>
    </table> 
   </div> 
   <!-- 流程 --> 
   <table class="printtTable table_box box2_w"> 
    <!-- 设备 --> 
    <tbody>
     <tr> 
      <td rowspan="2" class="title">
       <div class="writing_tb">
        设备
       </div></td> 
      <td>名称</td> 
      <td>型号</td> 
      <td>编号</td> 
      <td>开机检验</td> 
     </tr> 
     <tr> 
      <td class="small">${equipInfo.code}</td> 
      <td class="small">${equipInfo.name}</td> 
      <td class="small">${equipInfo.equipAlias}</td> 
      <td class="tdInput"><input class="needInput" type="text" /></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 设备 --> 
   <table class="printtTable table_box box2_w"> 
    <!-- 放线盘 --> 
    <tbody>
     <tr> 
      <td rowspan="5" class="title">
       <div class="writing_tb">
        放线盘
       </div></td> 
      <td>ID</td> 
      <td><input type="text" /></td> 
      <td rowspan="5" class="title">
       <div class="writing_tb">
        收线盘
       </div></td> 
      <td>ID</td> 
      <td><input type="text" /></td> 
     </tr> 
     <tr> 
      <td>盘径/mm</td> 
      <td><input type="text" /></td> 
      <td>盘径/mm</td> 
      <td><input type="text" /></td> 
     </tr> 
     <tr> 
      <td>最大重量/kg</td> 
      <td><input type="text" /></td> 
      <td>最大重量/kg</td> 
      <td><input type="text" /></td> 
     </tr> 
     <tr> 
      <td>最大长度/km</td> 
      <td><input type="text" /></td> 
      <td>最大长度/km</td> 
      <td><input type="text" /></td> 
     </tr> 
     <tr> 
      <td>张力确认</td> 
      <td class="tdInput"></td> 
      <td>张力确认</td> 
      <td class="tdInput"></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 放线盘 --> 
   <table class="printtTable table_box box2_w"> 
     <!-- 挤出机 --> 
     <tbody>
      <tr> 
       <td rowspan="11" class="title">
        <div class="writing_tb">
         挤出机	
        </div></td>
       <td style="width:270px" colspan="2">挤塑机参数</td> 
       <td style="width:100px">挤塑机温度</td>
	   <td style="width:62px">设定值</td>
	   <td style="width:76px">误差范围</td>
	   <td colspan="5" style="width:274px">实时测量值</td>
      </tr> 
      <tr>
       <td style="width:135px">ID</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px">一区</td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
      </tr> 
      <tr> 
       <td style="width:135px">机头DSCP</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px">二区</td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
      </tr> 
      <tr> 
       <td style="width:135px">螺杆DSCP</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px">三区</td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
      </tr> 
      <tr> 
       <td style="width:135px">模芯DSCP</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px">四区</td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
      </tr>
	  <tr> 
       <td style="width:135px">模芯尺寸</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px">五区</td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
      </tr>
	  <tr> 
       <td style="width:135px">模套DSCP</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px">六区</td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
      </tr>
	  <tr> 
       <td style="width:135px">模套尺寸</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px">七区</td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
      </tr>
	  <tr> 
       <td style="width:135px">螺杆额定转速</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px">八区</td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
	   <td class="tdInput" style="width:50px"><input class="needInput" type="text"></td>
      </tr>
	  <tr> 
       <td style="width:135px">熔融温度</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px"></td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td style="width:50px"></td>
	   <td style="width:50px"></td>
	   <td style="width:50px"></td>
	   <td style="width:50px"></td>
	   <td style="width:50px"></td>
      </tr>
	  <tr> 
       <td style="width:135px">熔融压力</td> 
	   <td style="width:135px"></td> 
	   <td style="width:100px"></td>
	   <td style="width:62px"></td> 
	   <td style="width:76px"></td> 
	   <td style="width:50px"></td>
	   <td style="width:50px"></td>
	   <td style="width:50px"></td>
	   <td style="width:50px"></td>
	   <td style="width:50px"></td>
      </tr>
     </tbody>
    </table> 
    <!-- END 挤出机 --> 
   <table class="printtTable table_box box2_w"> 
    <!-- 牵引速度 --> 
    <tbody>
     <tr> 
      <td rowspan="2" class="title">
       <div class="writing_tb" style="width:50px">
        牵引速度
		/rpm
       </div></td> 
      <td style="width:110px">设定值</td> 
      <td style="width:112px">误差范围</td> 
	  <td colspan="8">实时测量值</td> 
     </tr> 
     <tr> 
      <td></td>
	  <td></td>
	  <td class="tdInput"><input class="needInput" type="text" style="width:65px"></td>
	  <td class="tdInput"><input class="needInput" type="text" style="width:65px"></td>
	  <td class="tdInput"><input class="needInput" type="text" style="width:65px"></td>
	  <td class="tdInput"><input class="needInput" type="text" style="width:65px"></td>
	  <td class="tdInput"><input class="needInput" type="text" style="width:65px"></td>
	  <td class="tdInput"><input class="needInput" type="text" style="width:65px"></td>
	  <td class="tdInput"><input class="needInput" type="text" style="width:65px"></td>
	  <td class="tdInput"><input class="needInput" type="text" style="width:65px"></td>
     </tr> 
     </tr> 
    </tbody>
   </table> 
   
   <!-- END 牵引速度 --> 
   <table class="printtTable table_box box2_w"> 
    <!-- 异常备注 --> 
    <tbody>
     <tr> 
      <td rowspan="2" class="title">
       <div class="writing_tb">
        异常备注
       </div></td> 
      <td rowspan="2" style="text-align:center;width: 550px; height:100px"></td> 
      <td class="tdInput">生产确认</td> 
     </tr> 
     <tr> 
      <td><input type="text" /></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 异常备注 --> 
 </div>