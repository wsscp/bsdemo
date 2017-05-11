CREATE OR REPLACE PROCEDURE "WORKHOURS_REPORT"(vo_querydate   IN VARCHAR2,
                                               V_errorcode    OUT NUMBER,
                                               V_errorcontent OUT VARCHAR2) AS
  -- vo_reporttype[类型]:为空默认为所有操； EMEU:JS[挤塑]、HHPT[火花配套]、BZPB[编制屏蔽]、CLPB[成缆屏蔽]。
  -- vo_querydate[查询日期]:必填，当天。
  -- V_errorcode[错误编码]:返回0:正常
  -- V_errorcontent[错误信息]:返回normal:正常

  -- 1、设置统计时间
  queryDate VARCHAR2(50) := NVL(vo_querydate,
                                TO_CHAR(SYSTIMESTAMP, 'yyyy-mm-dd'));
  startTime TIMESTAMP(6) := TO_DATE(queryDate || ' 00:00:00', 'yyyy-mm-dd hh24:mi:ss'); -- 查询开始时间
  endTime   TIMESTAMP(6) := TO_DATE(queryDate || ' 23:59:59', 'yyyy-mm-dd hh24:mi:ss'); -- 查询结束时间
    
  -- 2、获取报工人员信息和班次
  CURSOR reportUserCursor(startTime TIMESTAMP,
                      endTime   TIMESTAMP) IS
    SELECT A.ID,
           D.NAME,
           D.USER_CODE,
           D.CERTIFICATE,
           C.SHIFT_ID,
           E.SHIFT_NAME
      FROM T_WIP_REPORT                  A,
           T_WIP_REPORT_USER             B,
           T_WIP_ONOFF_RECORD            C,
           T_BAS_EMPLOYEE                D,
           T_BAS_WORK_SHIFT              E
     WHERE A.ID = B.REPORT_ID
       AND B.ONOFF_ID = C.ID
       AND C.USER_CODE = D.USER_CODE
       AND C.SHIFT_ID = E.ID
       AND A.REPORT_TIME >= startTime
       AND A.REPORT_TIME < endTime;                           
                                
                           
  -- 3、根据报工ID获取报工订单信息和报工长度
  CURSOR reportCursor(reportId VARCHAR2) IS
    SELECT A.ID,
           A.EQUIP_CODE,
           H.EQUIP_ALIAS || '-' || H.NAME || '[' || H.CODE || ']' AS EQUIP_NAME,
           SUM(B.REPORT_LENGTH) AS REPORT_LENGTH,
           D.WORK_ORDER_NO,
           D.CUST_PRODUCT_TYPE,
           D.CUST_PRODUCT_SPEC,
           D.PRODUCT_TYPE,
           D.PRODUCT_SPEC,
           D.PROCESS_CODE,
           D.PROCESS_NAME,
           D.CONTRACT_NO,
           D.OPERATOR,
           GETCOLORSTR(WMSYS.WM_CONCAT(D.COLOR)) AS COLOR,
           G.WIRES_STRUCTURE,
           G.NUMBER_OF_WIRES,
           G.ORG_CODE,
           F.CRAFTS_ID,
           (SELECT COUNT(*) FROM T_WIP_REPORT_USER WHERE REPORT_ID = A.ID) AS EMPLOY_NUMBER
      FROM T_WIP_REPORT                  A,
           T_WIP_REPORT_TASK             B,
           T_PLA_ORDER_TASK              C,
           T_PLA_CU_ORDER_ITEM_PRO_DEC   D,
           T_PLA_CUSTOMER_ORDER_ITEM_DEC E,
           T_PLA_CUSTOMER_ORDER_ITEM     F,
           T_ORD_SALES_ORDER_ITEM        G,
           T_FAC_EQIP_INFO               H
     WHERE A.ID = B.REPORT_ID
       AND A.EQUIP_CODE = H.CODE
       AND B.ORDER_TASK_ID = C.ID
       AND C.ORDER_ITEM_PRO_DEC_ID = D.ID
       AND D.ORDER_ITEM_DEC_ID = E.ID
       AND E.ORDER_ITEM_ID = F.ID
       AND F.SALES_ORDER_ITEM_ID = G.ID
       AND A.ID = reportId
     GROUP BY A.ID,
              A.EQUIP_CODE,
              D.WORK_ORDER_NO,
              D.CUST_PRODUCT_TYPE,
              D.CUST_PRODUCT_SPEC,
              D.PRODUCT_TYPE,
              D.PRODUCT_SPEC,
              D.PROCESS_CODE,
              D.PROCESS_NAME,
              G.WIRES_STRUCTURE,
              G.NUMBER_OF_WIRES,
              G.ORG_CODE,
              F.CRAFTS_ID,
              D.CONTRACT_NO,
              D.OPERATOR,
              H.EQUIP_ALIAS,
              H.NAME,
              H.CODE;
              
  
  -- 4、*****表字段定义*****
  quota          NUMBER; -- 定额
  coefficient    NUMBER; -- 系数
  workHours       NUMBER; -- 工时

BEGIN
  
  
  
    -- 1、*****删除旧记录[where:queryDate]*****
    DELETE FROM T_WIP_USER_WORK_HOURS WHERE REPORT_DATE = queryDate;
  
    -- 2、*****循环报工人[reportUserCursor]*****
    FOR reportUser IN reportUserCursor(startTime, endTime) LOOP
      -- 3、*****循环报工订单信息和报工长度[reportUserCursor]*****
      FOR report IN reportCursor(reportUser.ID) LOOP
        -- 4、*****调用存储过程：获得[工时、定额、人员系数、定员人数]*****
        -- 参数{V_REPROTTYPE:, V_PROD_LENGTH:加工长度, V_EQUIP_CODE:设备编码, V_CUST_PRODUCT_TYPE:客户型号, V_PRODUCT_TYPE:型号, V_PRODUCT_SPEC:规格, V_WIRES_STRUCTURE:线芯结构, V_NUMBER_OF_WIRES:线芯数, V_CRAFTS_ID:工艺ID, V_PROCESS_NAME:工艺名称}
        CALCULATE_WORKHOURS(reportUser.CERTIFICATE,
          report.PROCESS_CODE,  
          report.REPORT_LENGTH/report.EMPLOY_NUMBER,
          report.EQUIP_CODE,
          report.CUST_PRODUCT_TYPE,
          report.PRODUCT_TYPE,
          report.PRODUCT_SPEC,
          report.WIRES_STRUCTURE,
          report.NUMBER_OF_WIRES,
          report.CRAFTS_ID,
          report.EMPLOY_NUMBER,
          workHours,
          quota,
          coefficient,
          V_errorcode,
          V_errorcontent);
        
        -- 5、*****新增工时表*****
        INSERT INTO T_WIP_USER_WORK_HOURS
          (ID,
           USER_CODE,
           USER_NAME,
           CONTRACT_NO,
           OPERATOR,
           WORK_ORDER_NO,
           EQUIP_CODE,
           EQUIP_NAME,
           SHIFT_ID,
           SHIFT_NAME,
           PROCESS_CODE,
           PROCESS_NAME,
           PRODUCT_TYPE,
           PRODUCT_SPEC,
           CUST_PRODUCT_TYPE,
           CUST_PRODUCT_SPEC,
           COLOR,
           QUOTA,
           PRODUCT_WORK_HOURS,
           PRODUCT_SUPPORT_HOURS,
           OVERTIME_HOURS,
           SUPPORT_HOURS,
           FINISHED_LENGTH,
           COEFFICIENT,
           REPORT_DATE,
           REPORT_ID,
           ORG_CODE,
           CREATE_USER_CODE,
           CREATE_TIME,
           MODIFY_USER_CODE,
           MODIFY_TIME)
        VALUES
          (SYS_GUID(),
           reportUser.USER_CODE,
           reportUser.NAME,
           report.CONTRACT_NO,
           report.OPERATOR,
           report.WORK_ORDER_NO,
           report.EQUIP_CODE,
           report.EQUIP_NAME,
           reportUser.SHIFT_ID,
           reportUser.SHIFT_NAME,
           report.PROCESS_CODE,
           report.PROCESS_NAME,
           report.PRODUCT_TYPE,
           report.PRODUCT_SPEC,
           report.CUST_PRODUCT_TYPE,
           report.CUST_PRODUCT_SPEC,
           report.COLOR,
           quota,
           workHours,
           0, -- product_support_hours
           0, -- overtime_hours
           0, -- support_hours
           report.REPORT_LENGTH,
           coefficient,
           queryDate,
           report.ID,
           report.ORG_CODE,
           'JOB',
           SYSTIMESTAMP,
           'JOB',
           SYSTIMESTAMP);  
      END LOOP; -- 循环报工订单信息和报工长度[reportUserCursor]结束
    END LOOP; -- 循环报工人[reportUserCursor]结束

  COMMIT;

  -- 设置出口参数
  V_errorcode    := 0;
  V_errorcontent := 'normal';

END;
