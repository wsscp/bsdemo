<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

  <div class="table_box box1_w"> 
   <!-- 编织单元操作 --> 
   <h1 style="width:180px;height:65px;margin:auto;margin-top: 20px;">编织单元操作</h1> 
   <table class="printtTable table_box" style="width:756px;"> 
    <!-- 批次号/条形码 --> 
    <tbody>
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
          <td class="center">编织机</td> 
          <td class="center">产品输出</td> 
          <td class="center">合格检验</td> 
         </tr> 
        </tbody>
       </table> </td> 
     </tr> 
     <tr style="width:275px;height:95px;"> 
      <td>条形码粘贴处</td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 批次号/条形码 --> 
   <div class="table_box box2_w"> 
    <!-- 原材料控制 --> 
    <h3 style="margin-left:20px;">原材料控制</h3> 
    <table class="printtTable table_box box3_w" cellPadding="0"> 
     <!-- 半成品线芯 --> 
     <tbody>
      <tr> 
       <td rowspan="6" class="title">
        <div class="writing_tb">
         半成品线芯
        </div></td> 
       <td rowspan="2" style="width:73px;">ID</td> 
       <td rowspan="2" style="width:132px;">
       <textarea cols="16" rows="2" readonly="readonly">${BZSemiParm.matCode}</textarea></td> 
       <td rowspan="6" style="width:148px;">线缆界面图</td> 
       <td style="width:135px;">参数</td> 
       <td style="width:90px;">标准值</td> 
       <td style="width:90px;">测量值</td> 
      </tr> 
      <tr> 
       <td class="small" height="20px">线缆最小外径/mm</td> 
       <td class="small">${BZSemiParm.minDia}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td rowspan="2">批次号</td> 
       <td rowspan="2" class="tdInput"></td> 
       <td class="small">线缆最大外径/mm</td> 
       <td class="small">${BZSemiParm.maxDia}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td class="small">圆整度</td> 
       <td class="small">${BZSemiParm.roundness}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td rowspan="2">规格</td> 
       <td class="small" rowspan="2">${productSpec}</td> 
       <td class="small">实际长度/m</td> 
       <td class="small">${length}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td class="small"></td> 
       <td class="small">${BZSemiParm.unitWeight}</td> 
       <td></td> 
      </tr> 
     </tbody>
    </table> 
    <!-- END 半成品线芯 --> 
    <table class="printtTable table_box box3_w"> 
     <!-- 屏蔽材料 --> 
     <tbody>
      <tr> 
       <td rowspan="5" class="title">
        <div class="writing_tb">
         屏蔽材料
        </div></td> 
       <td rowspan="2" style="width:73px;">ID</td> 
       <td class="small" rowspan="2" style="width:132px;">${BZMaterialPB.matCode}</td> 
       <td rowspan="5" style="width:148px;">屏蔽材料界面图</td> 
       <td style="width:135px;">参数</td> 
       <td style="width:90px;">标准值</td> 
       <td style="width:90px;">测量值</td> 
      </tr> 
      <tr> 
       <td class="small">直径/mm</td> 
       <td class="small">${BZQcParam.singleDia}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td rowspan="2" >批次号</td> 
       <td rowspan="2" class="tdInput"></td> 
       <td class="small">根数</td> 
       <td class="small">${BZQcParam.wireCount}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td class="small">消耗量/kg</td> 
       <td class="small">
       	<fmt:formatNumber value="${BZMaterialPB.quantity*length/1000}" pattern="#.##">
       	</fmt:formatNumber>      	
       </td> 
       <td></td> 
      </tr> 
      <tr> 
       <td rowspan="1">材料</td> 
       <td class="small" rowspan="1">${BZQcParam.PBMaterial}</td> 
       <td class="small"></td> 
       <td></td> 
       <td></td> 
      </tr> 
     </tbody>
    </table> 
    <!-- END 屏蔽材料 --> 
    <c:forEach begin="1" end="${countRB}">
    	<table class="printtTable table_box box3_w"> 
     <!-- 绕包材料 --> 
     <tbody>
      <tr> 
       <td rowspan="6" class="title">
        <div class="writing_tb">
         绕包材料
        </div></td> 
       <td rowspan="2" style="width:73px;">ID</td> 
       <td class="small" rowspan="2" style="width:132px;">${BZMaterialRB.matCode}</td> 
       <td rowspan="6" style="width:148px;">线缆界面图</td> 
       <td style="width:135px;">参数</td> 
       <td style="width:90px;">标准值</td> 
       <td style="width:90px;">测量值</td> 
      </tr> 
      <tr> 
       <td class="small">宽度/mm</td> 
       <td class="small">${BZMaterialRB.width}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td rowspan="2">批次号</td> 
       <td rowspan="2" class="tdInput"></td> 
       <td class="small">厚度/mm</td> 
       <td class="small">${BZMaterialRB.thickness}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td class="small">层数</td> 
       <td class="small">${BZMaterialRB.level}</td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr> 
       <td rowspan="2">材料</td> 
       <td class="small" rowspan="2">${BZMaterialRB.material}</td> 
       <td class="small">消耗量/kg</td> 
       <td class="small">    		
       		<fmt:formatNumber value="${BZMaterialRB.RB*length/1000}" pattern="#.##">
       		</fmt:formatNumber>
       </td> 
       <td class="tdInput"></td> 
      </tr> 
      <tr height="25px"> 
       <td class="small"></td> 
       <td></td> 
       <td></td> 
      </tr> 
     </tbody>
    </table>
    </c:forEach>
   </div> 
   <!-- END 原材料控制 --> 
  </div> 
  <!-- END 编织单元操作 --> 
