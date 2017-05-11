CREATE OR REPLACE PROCEDURE "WORKORDER_REPORT"(vo_reporttype  IN VARCHAR2,
                                               vo_querydate   IN VARCHAR2,
                                               V_errorcode    OUT NUMBER,
                                               V_errorcontent OUT VARCHAR2) AS
  -- vo_reporttype[类型]:为空默认为所有操； EMEU:JS[挤塑]、HHPT[火花配套]、BZPB[编制屏蔽]、CLPB[成缆屏蔽]。
  -- vo_querydate[查询日期]:必填，当天。
  -- V_errorcode[错误编码]:返回0:正常
  -- V_errorcontent[错误信息]:返回normal:正常

  -- 1、*****定义机构游标*****
  CURSOR orgInfoCursor IS
    SELECT * FROM T_BAS_ORG;
  -- 2、*****定义报表类型*****
  TYPE arry_var IS VARRAY(10) of VARCHAR2(32); -- 定义一个字符串数组
  reportTypeArray arry_var := arry_var('js', 'hhpt', 'bzpb', 'clpb'); -- 默认4种类型:JS[挤塑]、HHPT[火花配套]、BZPB[编制屏蔽]、CLPB[成缆屏蔽]。
  -- 3、*****定义查询日期[为空便取当前日期]，星期几，班次游标：用于循环班次用*****
  queryDate VARCHAR2(50) := NVL(vo_querydate,
                                TO_CHAR(SYSTIMESTAMP, 'yyyy-mm-dd'));
  weekNo    NUMBER; -- 星期几：1/2/3/4/5/6/7
  -- 查询星期几的SQL:{参数:queryDate[查询日期]}
  weekNoStr VARCHAR(200) := 'SELECT DECODE(WEEK_NO,0,7,WEEK_NO) FROM (SELECT TO_NUMBER(TO_CHAR(TO_DATE(:queryDate, ''yyyy-mm-dd''), ''D'')) - 1 AS WEEK_NO FROM DUAL)';
  -- 定义订单游标：获取班次列表
  CURSOR dayShiftCursor(weekNoTmp NUMBER, orgCodeTmp VARCHAR2) IS
    SELECT WS.ID,
           WC.WEEK_NO,
           WCS.WORK_SHIFT_ID,
           WS.SHIFT_NAME,
           WS.SHIFT_START_TIME,
           WS.SHIFT_END_TIME
      FROM T_BAS_WEEK_CALENDAR_SHIFT WCS,
           T_BAS_WEEK_CALENDAR       WC,
           T_BAS_WORK_SHIFT          WS
     WHERE WCS.WEEK_CALENDAR_ID = WC.ID
       AND WCS.WORK_SHIFT_ID = WS.ID
       AND WCS.ORG_CODE = orgCodeTmp
       AND WC.ORG_CODE = orgCodeTmp
       AND WC.WEEK_NO = weekNoTmp
       AND WCS.STATUS = '1'
       AND WS.STATUS = '1';
  -- 定义报工记录游标 : 获取该时间段所报工的所有产品型号规格及长度:{参数:shiftStartTime[班次开始时间]、shiftEndTime[班次结束时间]、workOrderNo[生产单号]}
  CURSOR reportCursor(shiftStartTime TIMESTAMP,
                      shiftEndTime   TIMESTAMP,
                      workOrderNo    VARCHAR2) IS
    SELECT D.PRODUCT_TYPE,
           D.CUST_PRODUCT_TYPE,
           D.PRODUCT_SPEC,
           SUM(B.REPORT_LENGTH) AS REPORT_LENGTH,
           G.WIRES_STRUCTURE,
           G.NUMBER_OF_WIRES,
           F.CRAFTS_ID,
           MAX(A.EQUIP_CODE) AS EQUIP_CODE,
           D.CONTRACT_NO,
           D.OPERATOR,
           GETCOLORSTR(wm_concat(D.COLOR)) AS COLOR
      FROM T_WIP_REPORT                  A,
           T_WIP_REPORT_TASK             B,
           T_PLA_ORDER_TASK              C,
           T_PLA_CU_ORDER_ITEM_PRO_DEC   D,
           T_PLA_CUSTOMER_ORDER_ITEM_DEC E,
           T_PLA_CUSTOMER_ORDER_ITEM     F,
           T_ORD_SALES_ORDER_ITEM        G
     WHERE A.REPORT_TIME >= shiftStartTime
       AND A.REPORT_TIME < shiftEndTime
       AND A.WORK_ORDER_NO = workOrderNo
       AND A.ID = B.REPORT_ID
       AND B.ORDER_TASK_ID = C.ID
       AND C.ORDER_ITEM_PRO_DEC_ID = D.ID
       AND D.ORDER_ITEM_DEC_ID = E.ID
       AND E.ORDER_ITEM_ID = F.ID
       AND F.SALES_ORDER_ITEM_ID = G.ID
     GROUP BY D.PRODUCT_TYPE,
              D.CUST_PRODUCT_TYPE,
              D.PRODUCT_SPEC,
              G.WIRES_STRUCTURE,
              G.NUMBER_OF_WIRES,
              F.CRAFTS_ID,
              D.CONTRACT_NO,
              D.OPERATOR;
  shiftStartTime TIMESTAMP(6); -- 班次开始时间
  shiftEndTime   TIMESTAMP(6); -- 班次结束时间
  -- 4、*****定义T_WIP_WORK_ORDER游标，查询sql串，用于查询订单结果的循环*****
  TYPE ref_cursor IS REF CURSOR; -- 定义游标
  orderCursor ref_cursor; -- 定义订单游标
  orderItem   T_WIP_WORK_ORDER%rowtype;
  -- 5、*****定义sql串*****
  -- 5.1、*****生产单查询sql:{参数:shiftStartTime[班次开始时间]、shiftEndTime[班次结束时间]、orgCode[组织代码]、inSqlStr[设备编码inSql]}
  -- 拼接查询sql
  -- 1、一个班次内完成的（班次结束时间>=REAL_START_TIME>=班次开始时间）
  -- 2、跳班次完成的（REAL_START_TIME<班次开始时间 & REAL_END_TIME>班次开始时间）
  -- 1、此班次开始的（班次结束时间>=REAL_START_TIME>=班次开始时间）
  -- 2、上班次开始的（REAL_START_TIME<班次开始时间）
  orderSqlStrTmp VARCHAR(2000); -- 中间变量
  orderSqlStr    VARCHAR(2000) := '
          SELECT *
            FROM (SELECT *
                FROM T_WIP_WORK_ORDER
               WHERE REAL_START_TIME >= :shiftStartTime
                 AND REAL_START_TIME <= :shiftEndTime
                 AND STATUS = ''FINISHED''
              UNION ALL
              SELECT *
                FROM T_WIP_WORK_ORDER
               WHERE REAL_START_TIME < :shiftStartTime
                 AND REAL_END_TIME > :shiftStartTime
                 AND STATUS = ''FINISHED''
              UNION ALL
              SELECT *
                FROM T_WIP_WORK_ORDER
               WHERE REAL_START_TIME >= :shiftStartTime
                 AND REAL_START_TIME <= :shiftEndTime
                 AND STATUS = ''IN_PROGRESS''
              UNION ALL
              SELECT *
                FROM T_WIP_WORK_ORDER
               WHERE REAL_START_TIME < :shiftStartTime
                 AND STATUS = ''IN_PROGRESS'') TMP
           WHERE TMP.ORG_CODE = :orgCode
             AND TMP.PROCESS_CODE IN
                ';

  -- 5.2、*****挡班/附挡班/辅助工:{参数:equipCode[设备编码]、shiftStartTime[班次开始时间]、shiftEndTime[班次结束时间] 、certificate[人员资质]、orgCode[组织代码]}
  -- 只要在这期间刷卡的就计算进去
  -- 定义操作工游标，获取当时操作人员的工号、姓名、资质
  workerStr VARCHAR(2000) := '
    SELECT NVL((
	    SELECT WMSYS.WM_CONCAT(DISTINCT E.NAME)
	      FROM T_WIP_REPORT       A,
	           T_WIP_REPORT_USER  B,
	           T_WIP_WORK_ORDER   C,
	           T_WIP_ONOFF_RECORD D,
	           T_BAS_EMPLOYEE     E
	     WHERE A.ID = B.REPORT_ID
	       AND B.ONOFF_ID = D.ID
	       AND C.WORK_ORDER_NO = A.WORK_ORDER_NO
	       AND D.USER_CODE = E.USER_CODE
	       AND C.ID = :workOrderId
	       AND E.CERTIFICATE = :certificate
	       AND A.CREATE_TIME >= :shiftStartTime
	       AND A.CREATE_TIME < :shiftEndTime
	       AND C.ORG_CODE = :orgCode), NULL) FROM DUAL';
  -- 5.3、*****从T_PRO_PROCESS_QC_VALUE中获取的属性:{参数:workOrderNo[生产单号], contractNo[合同号], productType[产品型号] productSpec[产品规格]、 checkItemName[属性名称]}
  qcValueStr VARCHAR(1000) := 'SELECT NVL((select null from dual where  ''aa'' = :workOrderNo AND ''aa'' = :contractNo AND ''aa'' = :productType AND ''aa'' = :productSpec AND ''aa'' = :checkItemName),NULL) FROM DUAL';
  /*
              SELECT NVL((SELECT QV.QC_VALUE
                       FROM T_PRO_PROCESS_QC_VALUE QV, t_pro_process_qc qc
                      WHERE QV.check_item_code = QC.check_item_code
                      AND QV.WORK_ORDER_NO = :workOrderNo
                      AND QV.CONTRACT_NO = :contractNo
                      AND QV.PRODUCT_TYPE = :productType
                      AND QV.PRODUCT_SPEC = :productSpec
                      AND QC.CHECK_ITEM_NAME = :checkItemName
                      AND ROWNUM = 1),
                     NULL)
                FROM DUAL
              ';
              */
  -- 5.4、*****从T_FAC_EQIP_INFO中获取的设备名称:{参数:equipCode[设备编码], orgCode[组织机构]}
  equipInfoStr VARCHAR(1000) := '
                SELECT NVL((SELECT EQUIP.EQUIP_ALIAS || ''-'' || LINE.NAME || ''['' || LINE.CODE || '']'' AS NAME
                       FROM T_FAC_EQIP_INFO    LINE,
                          T_FAC_PRODUCT_EQIP PE,
                          T_FAC_EQIP_INFO    EQUIP
                      WHERE LINE.ID = PE.PRODUCT_LINE_ID
                        AND PE.EQUIP_ID = EQUIP.ID
                        AND LINE.TYPE = ''PRODUCT_LINE''
                        AND LINE.CODE = :equipCode
                        AND LINE.ORG_CODE = :orgCode
                        AND EQUIP.ORG_CODE = :orgCode
                        AND ROWNUM = 1),
                       NULL)
                  FROM DUAL
                ';
  -- 5.6、*****从T_PRO_PROCESS_RECEIPT中获取的属性:{参数:equipCode[设备编码]、 processId[工序ID]、 receiptCode[属性编码]}
  -- recepitValueStr VARCHAR(500) := 'SELECT NVL((SELECT RECEIPT.RECEIPT_TARGET_VALUE FROM T_PRO_PROCESS_RECEIPT RECEIPT, T_PRO_EQIP_LIST LIST, T_PRO_PRODUCT_PROCESS PROCESS WHERE RECEIPT.EQIP_LIST_ID=LIST.ID AND LIST.PROCESS_ID=PROCESS.ID AND LIST.EQUIP_CODE=:equipCode AND PROCESS.ID=:processId AND RECEIPT.RECEIPT_CODE=:receiptCode AND ROWNUM = 1), NULL) FROM DUAL';

  -- 6、*****表字段定义*****
  -- 临时变量
  quota          NUMBER; -- 定额
  coefficient    NUMBER; -- 系数
  dbCoefficient  NUMBER; -- 当班系数
  fdbCoefficient NUMBER; -- 副当班系数
  fzgCoefficient NUMBER; -- 辅助工系数
  employNumber   NUMBER; -- 定员数
  processCode    VARCHAR(50); -- 工序编码
  processName    VARCHAR(50); -- 工序名称

  -- 公共信息
  contractNo      VARCHAR(50); -- 订单号/合同号
  operator        VARCHAR(50); -- 经办人
  workOrderNo     VARCHAR(50); -- 生产单号
  productType     VARCHAR(100); -- 型号
  productSpec     VARCHAR(100); -- 规格
  workHours       NUMBER; -- 工时:暂时先用生产单实际结束时间-生产单实际开始时间
  dbWorker        VARCHAR(200); -- 挡班
  fdbWorker       VARCHAR(200); -- 附挡班
  fzgWorker       VARCHAR(200); -- 辅助工
  equipCode       VARCHAR(200); -- 设备编码
  equipName       VARCHAR(400); -- 设备名称

  -- 特有信息
  colorOrWord             VARCHAR(500); -- 色别或字码
  jsThicknessMin          NUMBER; -- 挤塑厚度/min
  jsThicknessMax          NUMBER; -- 挤塑厚度/max
  jsFrontOuterdiameterMin NUMBER; -- 挤塑前外径/min
  jsFrontOuterdiameterMax NUMBER; -- 挤塑前外径/max
  jsBackOuterdiameterMin  NUMBER; -- 挤塑后外径/min
  jsBackOuterdiameterMax  NUMBER; -- 挤塑后外径/max

  testVoltage NUMBER; -- 试验电压
  punctureNum NUMBER; -- 击穿次数
  lineSpeed   NUMBER; -- 线速度m/s

  outerPosition VARCHAR(50); -- 外层方向
  beltPosition  VARCHAR(50); -- 包带方向
  cuCoverLevel  NUMBER; -- 铜丝覆盖率或铜带重叠率
  planLength    NUMBER; -- 计划长度
  realLength    NUMBER; -- 实际长度
  testing       VARCHAR(50); -- 检验
  quality       VARCHAR(50); -- 质量
  kind          VARCHAR(50); -- 种类
  preLeave      VARCHAR(50); -- 上班盘存
  thisTake      VARCHAR(50); -- 本班领用
  thisBack      VARCHAR(50); -- 本班退用

  clOuterdiameterUp   NUMBER; -- 成缆外径mm/上
  clOuterdiameterDown NUMBER; -- 成缆外径mm/下
  pbOuterdiameterUp   NUMBER; -- 屏蔽外径mm/上
  pbOuterdiameterDown NUMBER; -- 屏蔽外径mm/下
  piceRange           NUMBER; -- 节距
  pbMat               VARCHAR(50); -- 屏蔽材料
  rbQuality           VARCHAR(50); -- 绕包质量
  coverLevel          NUMBER; -- 搭盖率%
  ownerTesting        VARCHAR(50); -- 产品自检
  finishedLength      NUMBER; -- 长度

BEGIN
  -- 1、*****获取当天为星期几*****
  EXECUTE IMMEDIATE weekNoStr
    INTO weekNo
    USING queryDate;

  -- 2、*****首先遍历机构：orgCursor*****
  FOR orgInfo IN orgInfoCursor LOOP
  
    -- 3、*****删除旧记录[where:queryDate,orgCode]*****
    DELETE FROM T_WIP_WORK_ORDER_REPORT
     WHERE REPORT_DATE = queryDate
       AND ORG_CODE = orgInfo.ORG_CODE;
  
    -- 4、*****再循环报表类型[reportTypeArray]*****
    FOR i IN 1 .. reportTypeArray.count LOOP
      -- 判断报表类型：为空全部类型都执行，否则执行当前传入类型
      IF vo_reporttype IS NULL OR vo_reporttype = reportTypeArray(i) THEN
      
        -- 4.1、*****根据reportTypeArray获取设备编码*****
        IF reportTypeArray(i) = 'js' THEN
          -- 挤出双层/挤出单层
          orderSqlStrTmp := orderSqlStr || '(''Extrusion-Single'')';
        ELSIF reportTypeArray(i) = 'hhpt' THEN
          -- 配套
          orderSqlStrTmp := orderSqlStr || '(''Respool'')';
        ELSIF reportTypeArray(i) = 'bzpb' THEN
          -- 编织/屏蔽
          orderSqlStrTmp := orderSqlStr || '(''Braiding'')';
        ELSIF reportTypeArray(i) = 'clpb' THEN
          -- 成缆/屏蔽
          orderSqlStrTmp := orderSqlStr || '(''Cabling'', ''wrapping'')';
        ELSE
          orderSqlStrTmp := orderSqlStr || '('''')';
        END IF;
      
        -- 5、*****然后循环当天班次：dayShiftCursor*****
        FOR dayShift IN dayShiftCursor(weekNo, orgInfo.ORG_CODE) LOOP
          -- 首先赋值班次开始和结束时间：因为包含有24:00结束，数据库为00:00-23:59,需要转换
          shiftStartTime := to_date(queryDate, 'yyyy-mm-dd') +
                            1 / 24 / 60 / 60 *
                            (substr(dayShift.SHIFT_START_TIME, 0, 2) * 3600 +
                             substr(dayShift.SHIFT_START_TIME, 3, 2) * 60);
          shiftEndTime   := to_date(queryDate, 'yyyy-mm-dd') +
                            1 / 24 / 60 / 60 *
                            (substr(dayShift.SHIFT_END_TIME, 0, 2) * 3600 +
                             substr(dayShift.SHIFT_END_TIME, 3, 2) * 60);
          -- 结束时间小于开始说明第二天了：2346  0745
          IF shiftStartTime > shiftEndTime THEN
            shiftStartTime := shiftStartTime - 1;
          END IF;
          -- 如果当天的班次未开始，不统计
          IF shiftStartTime > SYSDATE THEN
            GOTO CONTINUE_SHIFT;
          END IF;
        
          -- 5.1、*****再拼接并循环查询出的生产单：orderCursor*****
          -- 打开游标循环结果生产单
          OPEN orderCursor FOR orderSqlStrTmp
            USING shiftStartTime, shiftEndTime, shiftStartTime, shiftStartTime, shiftStartTime, shiftEndTime, shiftStartTime, orgInfo.ORG_CODE;
          LOOP
            FETCH orderCursor
              INTO orderItem;
            Exit WHEN orderCursor%NOTFOUND;
            
            -- 赋值生产单号
		    workOrderNo    := orderItem.WORK_ORDER_NO; -- 生产单号
          
            -- 循环获取当班次该生产单报工的产品型号和长度
            FOR reportItem IN reportCursor(shiftStartTime,
                                           shiftEndTime,
                                           workOrderNo) LOOP
		     
              -- （3） 合同号、经办人、规格、型号、颜色/字码、计划长度、实际长度、制造长度、设备编码、设备名称
              contractNo     := reportItem.CONTRACT_NO; -- 合同号
              operator       := reportItem.OPERATOR; -- 经办人
              productType    := reportItem.PRODUCT_TYPE; -- 型号
              productSpec    := reportItem.PRODUCT_SPEC; -- 规格
              processCode    := orderItem.PROCESS_CODE; -- 工序编码
              processName    := orderItem.PROCESS_NAME; -- 工序名称
              colorOrWord    := reportItem.COLOR; -- 颜色/字码
              planLength     := reportItem.REPORT_LENGTH; -- 计划长度
              realLength     := reportItem.REPORT_LENGTH; -- 实际长度
              finishedLength := reportItem.REPORT_LENGTH; -- 制造长度 长度为零跳过
              IF finishedLength = 0 THEN
                GOTO CONTINUE_REPORT;
              END IF;
              equipCode := reportItem.EQUIP_CODE; -- 设备编码
              EXECUTE IMMEDIATE equipInfoStr
                INTO equipName
                USING reportItem.EQUIP_CODE, orgInfo.ORG_CODE, orgInfo.ORG_CODE; -- 设备名称
            
              -- （2）根据reportTypeArray获取特定的参数[T_PRO_PROCESS_QC_VALUE]
              IF reportTypeArray(i) = 'js' THEN
                -- 挤出双层/挤出单层:参数获取{色别或字码, 挤塑厚度/min, 挤塑厚度/max, 挤塑前外径/min, 挤塑前外径/max, 挤塑后外径/min, 挤塑后外径/max, 制造长度,  检验, 质量, 种类, 上班盘存, 本班领用, 本班退用}
                EXECUTE IMMEDIATE qcValueStr
                  INTO jsThicknessMin
                  USING workOrderNo, contractNo, productType, productSpec, '绝缘厚度'; -- 挤塑厚度/min
                EXECUTE IMMEDIATE qcValueStr
                  INTO jsThicknessMax
                  USING workOrderNo, contractNo, productType, productSpec, '绝缘厚度'; -- 挤塑厚度/max
                EXECUTE IMMEDIATE qcValueStr
                  INTO jsFrontOuterdiameterMin
                  USING workOrderNo, contractNo, productType, productSpec, '绝缘前外径'; -- 挤塑前外径/min
                EXECUTE IMMEDIATE qcValueStr
                  INTO jsFrontOuterdiameterMax
                  USING workOrderNo, contractNo, productType, productSpec, '绝缘前外径'; -- 挤塑前外径/max
                EXECUTE IMMEDIATE qcValueStr
                  INTO jsBackOuterdiameterMin
                  USING workOrderNo, contractNo, productType, productSpec, '绝缘后外径'; -- 挤塑后外径/min
                EXECUTE IMMEDIATE qcValueStr
                  INTO jsBackOuterdiameterMax
                  USING workOrderNo, contractNo, productType, productSpec, '绝缘后外径'; -- 挤塑后外径/max
                EXECUTE IMMEDIATE qcValueStr
                  INTO testing
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 检验
                EXECUTE IMMEDIATE qcValueStr
                  INTO quality
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 质量
                EXECUTE IMMEDIATE qcValueStr
                  INTO kind
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 种类
                EXECUTE IMMEDIATE qcValueStr
                  INTO preLeave
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 上班盘存
                EXECUTE IMMEDIATE qcValueStr
                  INTO thisTake
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 本班领用
                EXECUTE IMMEDIATE qcValueStr
                  INTO thisBack
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 本班退用
              ELSIF reportTypeArray(i) = 'hhpt' THEN
                -- 配套:参数获取{色别或字码, 试验电压, 击穿次数, 线速度m/s, 计划长度, 实际长度, 检验, 质量}
                EXECUTE IMMEDIATE qcValueStr
                  INTO testing
                  USING workOrderNo, contractNo, productType, productSpec, '火花试验电压'; -- 试验电压
                SELECT COUNT(*)
                  INTO punctureNum
                  FROM T_WIP_SPARK_REPAIR
                 WHERE WORK_ORDER_NO = workOrderNo
                   AND CREATE_TIME >= shiftStartTime
                   AND CREATE_TIME <= shiftEndTime; -- 击穿次数
                EXECUTE IMMEDIATE qcValueStr
                  INTO testing
                  USING workOrderNo, contractNo, productType, productSpec, '线速度'; -- 线速度m/s
                EXECUTE IMMEDIATE qcValueStr
                  INTO testing
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 检验
                EXECUTE IMMEDIATE qcValueStr
                  INTO quality
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 质量
              ELSIF reportTypeArray(i) = 'bzpb' THEN
                -- 编织/屏蔽:参数获取{外层方向, 包带方向, 铜丝覆盖率或铜带重叠率, 检验, 质量, 种类, 上班盘存, 本班领用, 本班退用}
                EXECUTE IMMEDIATE qcValueStr
                  INTO outerPosition
                  USING workOrderNo, contractNo, productType, productSpec, '绕包方向'; -- 外层方向
                EXECUTE IMMEDIATE qcValueStr
                  INTO beltPosition
                  USING workOrderNo, contractNo, productType, productSpec, '绕包方向'; -- 包带方向
                EXECUTE IMMEDIATE qcValueStr
                  INTO cuCoverLevel
                  USING workOrderNo, contractNo, productType, productSpec, '覆盖率(%)'; -- 铜丝覆盖率或铜带重叠率
                EXECUTE IMMEDIATE qcValueStr
                  INTO testing
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 检验
                EXECUTE IMMEDIATE qcValueStr
                  INTO quality
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 质量
                EXECUTE IMMEDIATE qcValueStr
                  INTO kind
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 种类
                EXECUTE IMMEDIATE qcValueStr
                  INTO preLeave
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 上班盘存
                EXECUTE IMMEDIATE qcValueStr
                  INTO thisTake
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 本班领用
                EXECUTE IMMEDIATE qcValueStr
                  INTO thisBack
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 本班领用
              ELSIF reportTypeArray(i) = 'clpb' THEN
                -- 成缆/屏蔽:参数获取{成缆外径mm/上, 成缆外径mm/下, 屏蔽外径mm/上, 屏蔽外径mm/下, 节距, 屏蔽材料, 绕包质量, 搭盖率%, 产品自检, 长度}
                EXECUTE IMMEDIATE qcValueStr
                  INTO clOuterdiameterUp
                  USING workOrderNo, contractNo, productType, productSpec, '成缆前外径'; -- 成缆外径mm/上
                EXECUTE IMMEDIATE qcValueStr
                  INTO clOuterdiameterDown
                  USING workOrderNo, contractNo, productType, productSpec, '成缆后外径'; -- 成缆外径mm/下
                EXECUTE IMMEDIATE qcValueStr
                  INTO pbOuterdiameterUp
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 屏蔽外径mm/上
                EXECUTE IMMEDIATE qcValueStr
                  INTO pbOuterdiameterDown
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 屏蔽外径mm/下
                EXECUTE IMMEDIATE qcValueStr
                  INTO piceRange
                  USING workOrderNo, contractNo, productType, productSpec, '节距'; -- 节距
                EXECUTE IMMEDIATE qcValueStr
                  INTO pbMat
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 屏蔽材料
                EXECUTE IMMEDIATE qcValueStr
                  INTO rbQuality
                  USING workOrderNo, contractNo, productType, productSpec, '半成品表面质量'; -- 绕包质量
                EXECUTE IMMEDIATE qcValueStr
                  INTO coverLevel
                  USING workOrderNo, contractNo, productType, productSpec, '包带搭盖率'; -- 搭盖率% 包带搭盖率
                EXECUTE IMMEDIATE qcValueStr
                  INTO ownerTesting
                  USING workOrderNo, contractNo, productType, productSpec, ''; -- 产品自检
              END IF;
            
              
            
              -- 获取当班员工列表，并添加员工记录表，获取当班、副挡板、辅助工
              EXECUTE IMMEDIATE workerStr
                  INTO dbWorker
                  USING orderItem.ID, 'DB', shiftStartTime, shiftEndTime, orgInfo.ORG_CODE; -- 当班
              EXECUTE IMMEDIATE workerStr
                  INTO fdbWorker
                  USING orderItem.ID, 'FDB', shiftStartTime, shiftEndTime, orgInfo.ORG_CODE; -- 副挡板
              EXECUTE IMMEDIATE workerStr
                  INTO fzgWorker
                  USING orderItem.ID, 'FZG', shiftStartTime, shiftEndTime, orgInfo.ORG_CODE; -- 辅助工
            
              -- （end）插入统计表[T_WIP_WORK_ORDER_REPORT]
              INSERT INTO T_WIP_WORK_ORDER_REPORT
                (ID,
                 CONTRACT_NO,
                 OPERATOR,
                 WORK_ORDER_NO,
                 EQUIP_CODE,
                 EQUIP_NAME,
                 SHIFT_ID,
                 SHIFT_NAME,
                 PRODUCT_TYPE,
                 PRODUCT_SPEC,
                 WORK_HOURS,
                 DB_WORKER,
                 FDB_WORKER,
                 FZG_WORKER,
                 CREATE_USER_CODE,
                 CREATE_TIME,
                 REPORT_DATE,
                 REPORT_TYPE,
                 FINISHED_LENGTH,
                 COLOR_OR_WORD,
                 JS_THICKNESS_MIN,
                 JS_THICKNESS_MAX,
                 JS_FRONT_OUTERDIAMETER_MIN,
                 JS_FRONT_OUTERDIAMETER_MAX,
                 JS_BACK_OUTERDIAMETER_MIN,
                 JS_BACK_OUTERDIAMETER_MAX,
                 TEST_VOLTAGE,
                 PUNCTURE_NUM,
                 LINE_SPEED,
                 OUTER_POSITION,
                 BELT_POSITION,
                 CU_COVER_LEVEL,
                 PLAN_LENGTH,
                 REAL_LENGTH,
                 TESTING,
                 QUALITY,
                 KIND,
                 PRE_LEAVE,
                 THIS_TAKE,
                 THIS_BACK,
                 CL_OUTERDIAMETER_UP,
                 CL_OUTERDIAMETER_DOWN,
                 PB_OUTERDIAMETER_UP,
                 PB_OUTERDIAMETER_DOWN,
                 PICE_RANGE,
                 PB_MAT,
                 RB_QUALITY,
                 COVER_LEVEL,
                 OWNER_TESTING,
                 ORG_CODE)
              VALUES
                (SYS_GUID(),
                 contractNo,
                 operator,
                 workOrderNo,
                 equipCode,
                 equipName,
                 dayShift.ID,
                 dayShift.SHIFT_NAME,
                 productType,
                 productSpec,
                 workHours,
                 dbWorker,
                 fdbWorker,
                 fzgWorker,
                 'job',
                 SYSTIMESTAMP,
                 queryDate,
                 reportTypeArray(i),
                 finishedLength,
                 colorOrWord,
                 jsThicknessMin,
                 jsThicknessMax,
                 jsFrontOuterdiameterMin,
                 jsFrontOuterdiameterMax,
                 jsBackOuterdiameterMin,
                 jsBackOuterdiameterMax,
                 testVoltage,
                 punctureNum,
                 lineSpeed,
                 outerPosition,
                 beltPosition,
                 cuCoverLevel,
                 planLength,
                 realLength,
                 testing,
                 quality,
                 kind,
                 preLeave,
                 thisTake,
                 thisBack,
                 clOuterdiameterUp,
                 clOuterdiameterDown,
                 pbOuterdiameterUp,
                 pbOuterdiameterDown,
                 piceRange,
                 pbMat,
                 rbQuality,
                 coverLevel,
                 ownerTesting,
                 orgInfo.ORG_CODE);
            
              <<CONTINUE_REPORT>>
              NULL;
            
            END LOOP; -- 遍历生产单[reportCursor]结束
          
          END LOOP; -- 遍历生产单[orderCursor]结束
          CLOSE orderCursor; -- 关闭游标
        
          <<CONTINUE_SHIFT>>
          NULL;
        
        END LOOP; -- 遍历班次信息[dayShiftCursor]结束
      
      END IF;
    
    END LOOP; -- 遍历报表类型[reportTypeArray]结束
  
  END LOOP; -- 遍历机构[orgInfoCursor]结束

  COMMIT;

  -- 设置出口参数
  V_errorcode    := 0;
  V_errorcontent := 'normal';

END;
