CREATE OR REPLACE FUNCTION EQUIP_WW_TARGETVALUE(V_TARGETCODE IN VARCHAR2,
                                                V_EQUIPCODE  IN VARCHAR2,
                                                V_ORGCODE    IN VARCHAR2)
  RETURN VARCHAR IS

  TARGETVALUE VARCHAR(500) := ''; -- 返回结果：

  counts NUMBER; -- 计数

  V_CUST_PRODUCT_TYPE VARCHAR(50) := ''; -- 当前加工的型号
  PROCESS_ID          VARCHAR(50) := ''; -- 当前加工的工序ID
  SECTION             VARCHAR(50) := ''; -- 当前加工的工段
  PARM_NAME           VARCHAR(50) := ''; -- 当前采集的标签名
  OUT_SIDE_VALUE      VARCHAR(50) := ''; -- 当前采集的外径
  FLOAT_NUMBER        NUMBER := 0; -- 变动量

  MATNAME_GROUP VARCHAR(500); -- 绝缘投入物料原材料名称

BEGIN
  -- DESCRIBE：获取当前设备标签的警戒线值
  -- 1、获取当前设备生产产品的绝缘工序的物料名称；
  -- 2、根据名称判断材料，并从表中获取警戒线值。

  -- 1、获取当前加工的工艺ID和工序ID
  select count(*)
    into counts
    FROM (SELECT DISTINCT PD.process_ID AS process_ID
            FROM T_PLA_ORDER_TASK T, T_PLA_CU_ORDER_ITEM_PRO_DEC PD
           WHERE T.ORDER_ITEM_PRO_DEC_ID = PD.ID
             AND T.STATUS = 'IN_PROGRESS'
             AND PD.STATUS = 'IN_PROGRESS'
             AND T.EQUIP_CODE = V_EQUIPCODE
             AND T.ORG_CODE = V_ORGCODE
             AND PD.ORG_CODE = V_ORGCODE) TEMP;

  if counts = 0 then
    GOTO FUN_OVER;
  end if;

  SELECT TEMP.PROCESS_ID, CUST_PRODUCT_TYPE
    INTO PROCESS_ID, V_CUST_PRODUCT_TYPE
    FROM (SELECT DISTINCT PD.process_ID     AS process_ID,
                          CUST_PRODUCT_TYPE AS CUST_PRODUCT_TYPE
            FROM T_PLA_ORDER_TASK T, T_PLA_CU_ORDER_ITEM_PRO_DEC PD
           WHERE T.ORDER_ITEM_PRO_DEC_ID = PD.ID
             AND T.STATUS = 'IN_PROGRESS'
             AND PD.STATUS = 'IN_PROGRESS'
             AND T.EQUIP_CODE = V_EQUIPCODE
             AND T.ORG_CODE = V_ORGCODE
             AND PD.ORG_CODE = V_ORGCODE) TEMP
   WHERE ROWNUM = 1;


  -- 2、获取当前设备上加工的产品的工艺的绝缘投入料名
  SELECT WMSYS.WM_CONCAT(TMP.MAT_NAME)
    INTO MATNAME_GROUP
    FROM (SELECT DISTINCT M.MAT_CODE, M.MAT_NAME
            FROM T_PRO_PROCESS_IN_OUT_wip  IO,
                 T_INV_MAT_wip             M
           WHERE IO.MAT_CODE = M.MAT_CODE
             AND IO.IN_OR_OUT = 'IN'
             AND M.MAT_TYPE = 'MATERIALS'
             AND IO.PRODUCT_PROCESS_ID = PROCESS_ID) TMP;

  -- 3、获取当前加工的工段
  SELECT PI.SECTION
    INTO SECTION
    FROM T_PRO_PRODUCT_PROCESS_wip PP, T_PRO_PROCESS_INFO PI
   WHERE PP.PROCESS_CODE = PI.CODE
     AND PP.ID = PROCESS_ID;

  -- 4、当前采集的标签名
  SELECT M.PARM_NAME
    INTO PARM_NAME
    FROM T_INT_EQUIP_MES_WW_MAPPING M
   WHERE M.EQUIP_CODE = CONCAT(V_EQUIPCODE, '_EQUIP')
     AND M.PARM_CODE = V_TARGETCODE;

  -- 5、判断标签是否取外径
  IF INSTR(PARM_NAME, '冷') > 0 AND INSTR(PARM_NAME, '径') > 0 THEN
    if SECTION = '绝缘' then
      SELECT COUNT(*)
        INTO COUNTS
        FROM (SELECT DISTINCT QC.ITEM_TARGET_VALUE
                FROM T_PRO_PROCESS_QC_wip QC
               WHERE QC.PROCESS_ID = PROCESS_ID
                 AND QC.ITEM_TARGET_VALUE IS NOT NULL
                 AND QC.CHECK_ITEM_NAME IN ('绝缘最大外径', '绝缘最小外径')) temp;
    
      IF COUNTS < 2 THEN
        GOTO FUN_OVER;
      END IF;
    
      SELECT ((select ITEM_TARGET_VALUE
                 from (SELECT DISTINCT QC.ITEM_TARGET_VALUE
                         FROM T_PRO_PROCESS_QC_wip      QC
                        WHERE QC.PROCESS_ID = PROCESS_ID
                          AND QC.ITEM_TARGET_VALUE IS NOT NULL
                          AND QC.CHECK_ITEM_NAME = '绝缘最小外径' ) TEMP
                where ROWNUM = 1) || '-' ||
             (select ITEM_TARGET_VALUE
                 from (SELECT DISTINCT QC.ITEM_TARGET_VALUE
                         FROM T_PRO_PROCESS_QC_wip      QC
                        WHERE QC.PROCESS_ID = PROCESS_ID
                          AND QC.ITEM_TARGET_VALUE IS NOT NULL
                          AND QC.CHECK_ITEM_NAME = '绝缘最大外径' ) TEMP
                where ROWNUM = 1))
        INTO OUT_SIDE_VALUE
        FROM dual;
    
      TARGETVALUE := OUT_SIDE_VALUE;
    END if;
  ELSIF INSTR(PARM_NAME, '热') > 0 AND INSTR(PARM_NAME, '径') > 0 THEN
    if SECTION = '绝缘' then
      SELECT COUNT(*)
        INTO COUNTS
        FROM (SELECT DISTINCT QC.ITEM_TARGET_VALUE
                FROM T_PRO_PROCESS_QC_wip QC
               WHERE QC.PROCESS_ID = PROCESS_ID
                 AND QC.ITEM_TARGET_VALUE IS NOT NULL
                 AND QC.CHECK_ITEM_NAME IN ('绝缘最大外径', '绝缘最小外径')) temp;
    
      IF COUNTS < 2 THEN
        GOTO FUN_OVER;
      END IF;
      
      -- PVC-聚氯乙烯(绝缘最小外径+0.1)
      IF INSTR(MATNAME_GROUP, '聚氯乙烯') > 0 OR INSTR(MATNAME_GROUP, '聚乙烯') > 0 THEN
      	FLOAT_NUMBER := 0.1;
      -- XLPE-交联聚乙烯(绝缘最大外径+0.2)
      ELSIF INSTR(MATNAME_GROUP, '交联聚乙烯') > 0 OR INSTR(MATNAME_GROUP, '交联乙烯') > 0 THEN
        FLOAT_NUMBER := 0.2;
      -- 无卤(绝缘最大外径+0.15)
      ELSIF INSTR(V_CUST_PRODUCT_TYPE, 'WDZ') > 0 THEN 
        FLOAT_NUMBER := 0.15;
      ELSE
    	FLOAT_NUMBER := 0;
      END IF;
    
      SELECT ((TO_NUMBER((select ITEM_TARGET_VALUE
                 from (SELECT DISTINCT QC.ITEM_TARGET_VALUE
                         FROM T_PRO_PROCESS_QC_wip      QC
                        WHERE QC.PROCESS_ID = PROCESS_ID
                          AND QC.ITEM_TARGET_VALUE IS NOT NULL
                          AND QC.CHECK_ITEM_NAME = '绝缘最小外径' ) TEMP
                where ROWNUM = 1))+FLOAT_NUMBER) || '-' ||
             (TO_NUMBER((select ITEM_TARGET_VALUE
                 from (SELECT DISTINCT QC.ITEM_TARGET_VALUE
                         FROM T_PRO_PROCESS_QC_wip      QC
                        WHERE QC.PROCESS_ID = PROCESS_ID
                          AND QC.ITEM_TARGET_VALUE IS NOT NULL
                          AND QC.CHECK_ITEM_NAME = '绝缘最大外径' ) TEMP
                where ROWNUM = 1))+FLOAT_NUMBER))
        INTO OUT_SIDE_VALUE
        FROM dual;
    
      TARGETVALUE := OUT_SIDE_VALUE;
    END if;
  ELSIF INSTR(PARM_NAME, '外径') > 0 OR INSTR(PARM_NAME, '线径') > 0 THEN
    if SECTION = '绝缘' then
      SELECT COUNT(*)
        INTO COUNTS
        FROM (SELECT DISTINCT QC.ITEM_TARGET_VALUE
                FROM T_PRO_PROCESS_QC_wip QC
               WHERE QC.PROCESS_ID = PROCESS_ID
                 AND QC.ITEM_TARGET_VALUE IS NOT NULL
                 AND QC.CHECK_ITEM_NAME = '绝缘前外径') temp;
    
      IF COUNTS = 0 THEN
        GOTO FUN_OVER;
      END IF;
    
      SELECT TEMP.ITEM_TARGET_VALUE
        INTO OUT_SIDE_VALUE
        FROM (SELECT DISTINCT QC.ITEM_TARGET_VALUE
                FROM T_PRO_PROCESS_QC_wip QC
               WHERE QC.PROCESS_ID = PROCESS_ID
                 AND QC.ITEM_TARGET_VALUE IS NOT NULL
                 AND QC.CHECK_ITEM_NAME = '绝缘前外径') TEMP
       WHERE ROWNUM = 1;
    
      TARGETVALUE := (TO_NUMBER(OUT_SIDE_VALUE) - 0.1) || '-' ||
                     (TO_NUMBER(OUT_SIDE_VALUE) + 0.1);
    
    ELSIF SECTION = '成缆' THEN
    
      SELECT COUNT(*)
        INTO COUNTS
        FROM (SELECT ITEM_TARGET_VALUE
                FROM T_PRO_PROCESS_QC_wip QC
               WHERE QC.CHECK_ITEM_NAME IN
                     ('铠装后外径', '成缆后外径', '绕包后外径', '绞合外径')
                 AND QC.PROCESS_ID = PROCESS_ID) temp;
    
      IF COUNTS = 0 THEN
        GOTO FUN_OVER;
      END IF;
    
      SELECT TEMP.ITEM_TARGET_VALUE
        INTO OUT_SIDE_VALUE
        FROM (SELECT ITEM_TARGET_VALUE
                FROM T_PRO_PROCESS_QC_wip QC
               WHERE QC.CHECK_ITEM_NAME IN
                     ('铠装后外径', '成缆后外径', '绕包后外径', '绞合外径')
                 and QC.PROCESS_ID = PROCESS_ID) TEMP
       WHERE ROWNUM = 1;
    
      TARGETVALUE := (TO_NUMBER(OUT_SIDE_VALUE) - 0.5) || '-' ||
                     (TO_NUMBER(OUT_SIDE_VALUE) + 0.5);
    
    ELSIF SECTION = '护套' THEN
    
      SELECT COUNT(*)
        INTO COUNTS
        FROM (SELECT DISTINCT P.PROP_TARGET_VALUE
                FROM T_INV_MAT_WIP        M,
                     T_INV_MAT_PROP_WIP   P,
                     T_INV_TEMPLET_DETAIL D
               WHERE P.MAT_ID = M.ID
                 AND P.TEMPLET_DETAIL_ID = D.ID
                 and m.PROCESS_WIP_ID = PROCESS_ID
                 AND D.PROP_NAME = '成品电线标准外径'
                 AND P.PROP_TARGET_VALUE IS NOT NULL) temp;
    
      IF COUNTS = 0 THEN
        GOTO FUN_OVER;
      END IF;
    
      SELECT TEMP.PROP_TARGET_VALUE
        INTO OUT_SIDE_VALUE
        FROM (SELECT DISTINCT P.PROP_TARGET_VALUE
                FROM T_INV_MAT_WIP        M,
                     T_INV_MAT_PROP_WIP   P,
                     T_INV_TEMPLET_DETAIL D
               WHERE P.MAT_ID = M.ID
                 AND P.TEMPLET_DETAIL_ID = D.ID
                 and m.PROCESS_WIP_ID = PROCESS_ID
                 AND D.PROP_NAME = '成品电线标准外径'
                 AND P.PROP_TARGET_VALUE IS NOT NULL) TEMP
       WHERE ROWNUM = 1;
    
      TARGETVALUE := (TO_NUMBER(OUT_SIDE_VALUE) - 0.5) || '-' ||
                     (TO_NUMBER(OUT_SIDE_VALUE) + 0.5);
    END IF;
  
  ELSE
  
    -- 2、PVC:聚氯乙烯\PE:聚乙烯\XLPE:交联聚乙烯\NL:尼龙
    IF INSTR(MATNAME_GROUP, '聚氯乙烯') > 0 THEN
      SELECT NVL((SELECT PVC
                   FROM T_INT_EQUIP_WW_PRODUCT_VALUE
                  WHERE EQUIP_CODE = V_EQUIPCODE
                    AND TARGET_CODE = V_TARGETCODE
                    AND ROWNUM = 1),
                 NULL)
        INTO TARGETVALUE
        FROM DUAL;
    ELSIF INSTR(MATNAME_GROUP, '聚乙烯') > 0 THEN
      SELECT NVL((SELECT PE
                   FROM T_INT_EQUIP_WW_PRODUCT_VALUE
                  WHERE EQUIP_CODE = V_EQUIPCODE
                    AND TARGET_CODE = V_TARGETCODE
                    AND ROWNUM = 1),
                 NULL)
        INTO TARGETVALUE
        FROM DUAL;
    ELSIF INSTR(MATNAME_GROUP, '交联聚乙烯') > 0 OR
          INSTR(MATNAME_GROUP, '交联乙烯') > 0 THEN
      SELECT NVL((SELECT XLPE
                   FROM T_INT_EQUIP_WW_PRODUCT_VALUE
                  WHERE EQUIP_CODE = V_EQUIPCODE
                    AND TARGET_CODE = V_TARGETCODE
                    AND ROWNUM = 1),
                 NULL)
        INTO TARGETVALUE
        FROM DUAL;
    ELSIF INSTR(MATNAME_GROUP, '尼龙') > 0 THEN
      SELECT NVL((SELECT NL
                   FROM T_INT_EQUIP_WW_PRODUCT_VALUE
                  WHERE EQUIP_CODE = V_EQUIPCODE
                    AND TARGET_CODE = V_TARGETCODE
                    AND ROWNUM = 1),
                 NULL)
        INTO TARGETVALUE
        FROM DUAL;
    END IF;
  
  END IF;

  <<FUN_OVER>>
  RETURN(TARGETVALUE);
END EQUIP_WW_TARGETVALUE;
