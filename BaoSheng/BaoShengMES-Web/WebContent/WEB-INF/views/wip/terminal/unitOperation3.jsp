<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  
  <!-- 流程、设备和参数 --> 
  <div class="table_box box1_w"> 
   <!-- 产品和检验 --> 
   <h1 style="width:270px;height:45px;margin-left:20px;">产品和检验</h1> 
   <table class="printtTable table_box box2_w" cellPadding="0"> 
    <!-- 编织成品 --> 
    <tbody>
     <tr> 
      <td rowspan="6" class="title">
       <div class="writing_tb">
        编织半成品
       </div></td> 
      <td>信息</td> 
      <td width="200px">参数</td> 
      <td rowspan="6" style="width:130px;">合格成品视图</td> 
      <td rowspan="6" style="width:130px;">不合格成品视图1</td> 
      <td rowspan="6" style="width:130px;">不合格成品视图2</td> 
     </tr> 
     <tr> 
      <td class="small">ID</td> 
      <td class="small">${BZSemiOutProductProps}</td> 
     </tr> 
     <tr> 
      <td class="small">批次号</td> 
      <td class="tdInput"></td> 
     </tr> 
     <tr> 
      <td class="small">计量长度/km</td> 
      <td class="tdInput"></td> 
     </tr> 
     <tr> 
      <td class="small">单元重量kg/hm</td> 
      <td><input type="text" /></td> 
     </tr> 
     <tr> 
      <td class="small">表面质量</td> 
      <td class="tdInput"></td> 
     </tr> 
    </tbody>
   </table> 
   <!-- END 编织成品 --> 
   <table class="printtTable table_box box2_w" cellPadding="0"> 
    <!-- 测量检验 --> 
    <tbody>
     <tr> 
      <td rowspan="5" class="title">
       <div class="writing_tb">
        测量检验
       </div></td> 
      <td height="16px"></td> 
      <td width="100px">标准值</td> 
      <td>误差范围</td> 
      <td colspan="5">测量值</td> 
      <td>平均值</td> 
      <td style="width:68px;">合格签字</td> 
     </tr> 
     <tr> 
      <td class="small" height="16px">编织成品外径/mm</td> 
      <td class="small">${BZQcParam.afterBzDia}</td> 
      <td><input type="text" style="width:58px;" /></td> 
      <td class="tdInput" width="50px"></td> 
      <td class="tdInput" width="50px"></td>  
      <td class="tdInput" width="50px"></td> 
      <td class="tdInput" width="50px"></td>  
      <td class="tdInput" width="50px"></td> 
      <td class="tdInput" width="50px"></td> 
      <td class="tdInput" rowspan="4"></td> 
     </tr> 
     <tr> 
      <td class="small" height="16px">编织节距/mm</td> 
      <td class="small">${BZQcParam.bzStep}</td> 
      <td><input type="text" style="width:58px;" /></td> 
 	  <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>   
     </tr> 
     <tr> 
      <td class="small" height="16px">编织密度</td> 
      <td class="small">${BZQcParam.bzDensity}</td> 
      <td></td> 
	  <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
     </tr> 
     <tr> 
      <td class="small" height="16px">圆整度</td> 
      <td class="small">${BZQcParam.afterBzroundness}</td> 
      <td></td> 
	  <td class="tdInput" ></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
      <td class="tdInput"></td> 
      <td class="tdInput"></td>  
     </tr> 
    </tbody>
   </table> 
   <!-- END 编织机主机 --> 
   <div class="table_box box2_w" style="border:none;">
     注:
    <br /> &nbsp;&nbsp;①“异常备注”是指在生产过程中，工人根据以往生产经验，发现的可能会影响产品质量单在单元表格中又没有数据规定或说明的情况。
    <br /> &nbsp;&nbsp;②“生产确认”是指工人在完成相关操作后，对所有的相关参数与标准值或设定值进行比较，确认是否合格
   </div> 
  </div> 
  <!-- 产品和检验 -->  
