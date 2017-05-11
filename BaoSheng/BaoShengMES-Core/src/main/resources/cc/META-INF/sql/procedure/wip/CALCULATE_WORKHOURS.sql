-- 计算工时
CREATE OR REPLACE PROCEDURE "CALCULATE_WORKHOURS"(V_CERTIFICATE       IN VARCHAR2,
                                                  V_REPROTTYPE        IN VARCHAR2,
                                                  V_PROD_LENGTH       IN NUMBER,
                                                  V_EQUIP_CODE        IN VARCHAR2,
                                                  V_CUST_PRODUCT_TYPE IN VARCHAR2,
                                                  V_PRODUCT_TYPE      IN VARCHAR2,
                                                  V_PRODUCT_SPEC      IN VARCHAR2,
                                                  V_WIRES_STRUCTURE   IN VARCHAR2,
                                                  V_NUMBER_OF_WIRES   IN NUMBER,
                                                  V_CRAFTS_ID         IN VARCHAR2,
                                                  V_PROCESS_NAME      IN VARCHAR2,
                                                  V_WORK_HOURS        OUT NUMBER,
                                                  V_QUOTA             OUT NUMBER,
                                                  V_COEFFICIENT       OUT NUMBER,
                                                  V_errorcode         OUT NUMBER,
                                                  V_errorcontent      OUT VARCHAR2) AS

  -- 1、分工序计算：绝缘/成缆/编织/护套等等
  -- 2、查找匹配到的定额计算规则
  -- 3、计算匹配规则

  -- 返回参数
  WORK_HOURS      NUMBER := 0; -- 工时
  QUOTA           NUMBER := 0; -- 定额
  DB_COEFFICIENT  NUMBER := 0; -- 当班系数
  FDB_COEFFICIENT NUMBER := 0; -- 副当班系数
  FZG_COEFFICIENT NUMBER := 0; -- 辅助工系数
  EMPLOY_NUMBER   NUMBER := 0; -- 定员数(暂时没用)

  -- 通用
  --ROWCOUNT NUMBER; -- 规则匹配记录数

  QUOTA_ROLE_ID VARCHAR2(50); -- 定额规则ID

  HOURROLECOUNT NUMBER; -- 查找到的计算公式的规则

  -- 绝缘
  PRODUCTSPEC VARCHAR2(50); -- 规格匹配出来的截面：2.5
  HAS_NL_MAT  NUMBER; -- 是否存在尼龙材料：0：不存在，>0 存在

  -- 成缆
  CLASSIFYNAME VARCHAR2(50); -- 分类名称
  CUDIA        NUMBER; -- 铜绞线单丝直径
  STEELWIDTH   NUMBER; -- 钢丝宽度

  -- 护套
  RADIUS NUMBER; -- 半制品外径
BEGIN
  -- 初始化
  PRODUCTSPEC := V_PRODUCT_SPEC;
  -- 获取产品分类名称
  SELECT WM_CONCAT(T.NAME)
    INTO CLASSIFYNAME
    FROM T_PLA_PRODUCT_CLASSIFY T
   START WITH T.ID = (SELECT CLASSIFY_ID
                        FROM T_PLA_PRODUCT P
                       WHERE P.PRODUCT_TYPE = V_PRODUCT_TYPE
                         AND P.PRODUCT_SPEC = V_PRODUCT_SPEC
                         AND ROWNUM = 1)
  CONNECT BY T.ID = PRIOR T.PID;

  -- 绝缘计算工时，按照设备、型号和规格匹配的规则计算
  IF V_REPROTTYPE = 'Extrusion-Single' THEN
    -- 获取规格：*号后面的数字
    PRODUCTSPEC := SUBSTR(V_PRODUCT_SPEC, INSTR(V_PRODUCT_SPEC, '*') + 1);
  
    -- 根据设备和规格判断是否有匹配的定额计算规则
    SELECT COUNT(*)
      INTO HOURROLECOUNT
      FROM T_BAS_WORK_HOURS
     WHERE EQUIP_CODE = V_EQUIP_CODE
       AND PRODUCT_SPEC = PRODUCTSPEC;
  
    IF HOURROLECOUNT > 0 THEN
      -- 判断 是否存在尼龙材料：0：不存在，>0 存在
      SELECT COUNT(*)
        INTO HAS_NL_MAT
        FROM T_PRO_PRODUCT_PROCESS A, T_PRO_PROCESS_IN_OUT B, T_INV_MAT C
       WHERE A.ID = B.PRODUCT_PROCESS_ID
         AND B.MAT_CODE = C.MAT_CODE
         AND A.PROCESS_CODE = 'Extrusion-Single'
         AND C.MAT_NAME LIKE '%尼龙%'
         AND A.PRODUCT_CRAFTS_ID = V_CRAFTS_ID;
      -- 定额获取：耐火
      IF INSTR(V_CUST_PRODUCT_TYPE, 'NH-') > 0 THEN
        SELECT QUOTA_NH,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          INTO QUOTA,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          FROM T_BAS_WORK_HOURS
         WHERE EQUIP_CODE = V_EQUIP_CODE
           AND PRODUCT_SPEC = PRODUCTSPEC;
        -- 定额获取：SYV
      ELSIF INSTR(V_CUST_PRODUCT_TYPE, 'SYV') > 0 THEN
        SELECT QUOTA_SYV,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          INTO QUOTA,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          FROM T_BAS_WORK_HOURS
         WHERE EQUIP_CODE = V_EQUIP_CODE
           AND PRODUCT_SPEC = PRODUCTSPEC;
        -- 定额获取：无卤机车线
      ELSIF INSTR(V_CUST_PRODUCT_TYPE, 'WDZ-DCYJ') > 0 THEN
        SELECT QUOTA_WLJ,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          INTO QUOTA,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          FROM T_BAS_WORK_HOURS
         WHERE EQUIP_CODE = V_EQUIP_CODE
           AND PRODUCT_SPEC = PRODUCTSPEC;
        -- 定额获取：尼龙
      ELSIF HAS_NL_MAT > 0 THEN
        SELECT QUOTA_NL,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          INTO QUOTA,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          FROM T_BAS_WORK_HOURS
         WHERE EQUIP_CODE = V_EQUIP_CODE
           AND PRODUCT_SPEC = PRODUCTSPEC;
        -- 定额获取：单
      ELSIF V_WIRES_STRUCTURE = 'A' THEN
        SELECT QUOTA_SINGLE,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          INTO QUOTA,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          FROM T_BAS_WORK_HOURS
         WHERE EQUIP_CODE = V_EQUIP_CODE
           AND PRODUCT_SPEC = PRODUCTSPEC;
        -- 定额获取：束
      ELSIF V_WIRES_STRUCTURE = 'B' OR V_WIRES_STRUCTURE = 'C' THEN
        SELECT QUOTA_BUNDLE,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          INTO QUOTA,
               DB_COEFFICIENT,
               FDB_COEFFICIENT,
               FZG_COEFFICIENT,
               EMPLOY_NUMBER
          FROM T_BAS_WORK_HOURS
         WHERE EQUIP_CODE = V_EQUIP_CODE
           AND PRODUCT_SPEC = PRODUCTSPEC;
      END IF;
    END IF;
    -- 火花工段工时定额    公里数/70*8/（2人一组）   开短头线盘数/10/1个辅助工时/（2人一组）
  ELSIF V_REPROTTYPE = 'Respool' THEN
    QUOTA           := 70;
    DB_COEFFICIENT  := 1.9; -- 当班系数
    FDB_COEFFICIENT := 0; -- 副当班系数
    FZG_COEFFICIENT := 1.4; -- 辅助工系数
    EMPLOY_NUMBER   := 2; -- 定员人数
    -- 成缆计算工时，按照设备、产品分类、型号和规格匹配的规则计算
  ELSIF V_REPROTTYPE = 'Cabling' or V_REPROTTYPE = 'wrapping' THEN
  
    -- 获取铜绞线单丝直径：wrapping  绕包[54B]
    SELECT NVL((SELECT P.PROP_TARGET_VALUE
                 FROM T_INV_MAT             M,
                      T_INV_MAT_PROP        P,
                      T_INV_TEMPLET_DETAIL  D,
                      T_PRO_PROCESS_IN_OUT  IO,
                      T_PRO_PRODUCT_PROCESS PPP
                WHERE P.MAT_ID = M.ID
                  AND P.TEMPLET_DETAIL_ID = D.ID
                  AND M.MAT_CODE = IO.MAT_CODE
                  AND IO.PRODUCT_PROCESS_ID = PPP.ID
                  AND PPP.PROCESS_CODE = 'wrapping'
                  AND D.PROP_NAME = '单丝直径'
                  AND M.MAT_NAME = '裸铜绞线'
                  AND PPP.PRODUCT_CRAFTS_ID = V_CRAFTS_ID
                  AND ROWNUM = 1),
               0)
      INTO CUDIA
      FROM DUAL;
  
    -- 获取钢带宽度：shield  铠装[54B]
  
    SELECT NVL((SELECT (CASE
                        WHEN REGEXP_LIKE(REPLACE(P.PROP_TARGET_VALUE,
                                                 'm',
                                                 ''),
                                         '(^[+-]?\d{0,}\.?\d{0,}$)') THEN
                         TO_NUMBER(REPLACE(P.PROP_TARGET_VALUE, 'm', ''))
                        ELSE
                         0
                      END)
                 FROM T_INV_MAT             M,
                      T_INV_MAT_PROP        P,
                      T_INV_TEMPLET_DETAIL  D,
                      T_PRO_PROCESS_IN_OUT  IO,
                      T_PRO_PRODUCT_PROCESS PPP
                WHERE P.MAT_ID = M.ID
                  AND P.TEMPLET_DETAIL_ID = D.ID
                  AND M.MAT_CODE = IO.MAT_CODE
                  AND IO.PRODUCT_PROCESS_ID = PPP.ID
                  AND PPP.PROCESS_CODE = 'shield'
                  AND D.PROP_NAME = '宽度'
                  AND M.MAT_NAME = '铠装电缆用镀锌钢带'
                  AND PPP.PRODUCT_CRAFTS_ID = V_CRAFTS_ID),
               0)
      INTO STEELWIDTH
      FROM DUAL;
  
    -- 1、成缆规则说明：1.6米成缆定额/42B成缆定额 设备按照[控缆/力缆/计算机电缆]分类来进行计算
    -- 2、其余按照先芯数后规格匹配
  
    -- 1.6米成缆定额/42B成缆定额 IF V_EQUIP_CODE = '444-70' OR V_EQUIP_CODE = '444-74' OR V_EQUIP_CODE = '444-73' THEN
    -- 计算机电缆
    IF INSTR(CLASSIFYNAME, '计算机电缆') > 0 THEN
      CLASSIFYNAME := '计算机电缆';
      -- 控缆
    ELSIF INSTR(CLASSIFYNAME, '控制电缆') > 0 THEN
      CLASSIFYNAME := '控制电缆';
      -- 力缆
    ELSIF INSTR(CLASSIFYNAME, '电力电缆') > 0 THEN
      CLASSIFYNAME := '电力电缆';
      -- 风能电缆
    ELSIF INSTR(CLASSIFYNAME, '风能电缆') > 0 THEN
      CLASSIFYNAME := '风能电缆';
    ELSE
      CLASSIFYNAME := '';
    END IF;
  
    -- A、用[设备、规格、产品分类]匹配
    SELECT NVL((SELECT ID
                 FROM T_BAS_WORK_HOURS
                WHERE EQUIP_CODE = V_EQUIP_CODE
                  AND PRODUCT_SPEC = PRODUCTSPEC
                  AND PRODUCT_CLASSIFY = CLASSIFYNAME
                  AND ROWNUM = 1),
               '')
      INTO QUOTA_ROLE_ID
      FROM DUAL;
  
    -- B、用[设备、芯数、产品分类]匹配
    IF QUOTA_ROLE_ID IS NULL THEN
      SELECT NVL((SELECT ID
                   FROM T_BAS_WORK_HOURS
                  WHERE EQUIP_CODE = V_EQUIP_CODE
                    AND WIRES_NUM_MAX >= V_NUMBER_OF_WIRES
                    AND WIRES_NUM_MIN <= V_NUMBER_OF_WIRES
                    AND PRODUCT_CLASSIFY = CLASSIFYNAME
                    AND ROWNUM = 1),
                 0)
        INTO QUOTA_ROLE_ID
        FROM DUAL;
    END IF;
  
    -- C、用[设备、芯数、长度]匹配
    IF QUOTA_ROLE_ID IS NULL THEN
      SELECT NVL((SELECT ID
                   FROM T_BAS_WORK_HOURS
                  WHERE EQUIP_CODE = V_EQUIP_CODE
                    AND WIRES_NUM_MAX >= V_NUMBER_OF_WIRES
                    AND WIRES_NUM_MIN <= V_NUMBER_OF_WIRES
                    AND MAX_LENGTH >= V_PROD_LENGTH
                    AND MIN_LENGTH <= V_PROD_LENGTH
                    AND ROWNUM = 1),
                 0)
        INTO QUOTA_ROLE_ID
        FROM DUAL;
    END IF;
  
    -- D、用[设备、芯数]匹配
    IF QUOTA_ROLE_ID IS NULL THEN
      SELECT NVL((SELECT ID
                   FROM T_BAS_WORK_HOURS
                  WHERE EQUIP_CODE = V_EQUIP_CODE
                    AND WIRES_NUM_MAX >= V_NUMBER_OF_WIRES
                    AND WIRES_NUM_MIN <= V_NUMBER_OF_WIRES
                    AND ROWNUM = 1),
                 0)
        INTO QUOTA_ROLE_ID
        FROM DUAL;
    END IF;
  
    -- E、用[设备、产品分类]匹配
    IF QUOTA_ROLE_ID IS NULL THEN
      SELECT NVL((SELECT ID
                   FROM T_BAS_WORK_HOURS
                  WHERE EQUIP_CODE = V_EQUIP_CODE
                    AND PRODUCT_CLASSIFY = CLASSIFYNAME
                    AND ROWNUM = 1),
                 0)
        INTO QUOTA_ROLE_ID
        FROM DUAL;
    END IF;
  
    -- F、用[设备、铜绞线直径]匹配
    IF QUOTA_ROLE_ID IS NULL THEN
      SELECT NVL((SELECT ID
                   FROM T_BAS_WORK_HOURS
                  WHERE EQUIP_CODE = V_EQUIP_CODE
                    AND CU_DIAMETER_MAX >= CUDIA
                    AND CU_DIAMETER_MIN <= CUDIA
                    AND ROWNUM = 1),
                 0)
        INTO QUOTA_ROLE_ID
        FROM DUAL;
    END IF;
  
    -- G、用[设备]匹配
    IF QUOTA_ROLE_ID IS NULL THEN
      SELECT NVL((SELECT ID
                   FROM T_BAS_WORK_HOURS
                  WHERE EQUIP_CODE = V_EQUIP_CODE
                    AND ROWNUM = 1),
                 0)
        INTO QUOTA_ROLE_ID
        FROM DUAL;
    END IF;
  
    -- 设置返回参数值
    IF QUOTA_ROLE_ID IS NOT NULL THEN
      SELECT QUOTA,
             DB_COEFFICIENT,
             FDB_COEFFICIENT,
             FZG_COEFFICIENT,
             EMPLOY_NUMBER
        INTO QUOTA,
             DB_COEFFICIENT,
             FDB_COEFFICIENT,
             FZG_COEFFICIENT,
             EMPLOY_NUMBER
        FROM T_BAS_WORK_HOURS
       WHERE ID = QUOTA_ROLE_ID;
    END IF;
  
    -- 编织  计算工时，按照设备、产品分类、型号和规格匹配的规则计算
  ELSIF V_REPROTTYPE = 'Braiding' THEN
    QUOTA := 0;
  
    -- 护套  计算工时，按照设备、产品分类、型号和规格匹配的规则计算
  ELSIF V_REPROTTYPE = 'Jacket-Extrusion' OR V_REPROTTYPE = 'shield' THEN
    -- 产品分类：核电电缆
    IF INSTR(CLASSIFYNAME, '核电电缆') > 0 THEN
      CLASSIFYNAME := '核电电缆';
      -- 风能电缆
    ELSIF INSTR(CLASSIFYNAME, '风能电缆') > 0 THEN
      CLASSIFYNAME := '风能电缆';
    ELSE
      CLASSIFYNAME := '';
    END IF;
  
    -- 用[分类]匹配，取特殊定额
    SELECT NVL((SELECT QUOTA
                 FROM T_BAS_WORK_HOURS
                WHERE PRODUCT_CLASSIFY = CLASSIFYNAME
                  AND PROCESS_SECTION = 'Jacket-Extrusion'
                  AND ROWNUM = 1),
               0)
      INTO QUOTA
      FROM DUAL;
  
    IF QUOTA = 0 THEN
      -- 内护
      IF INSTR(V_PROCESS_NAME, '内') > 0 THEN
      
        SELECT NVL((SELECT P.PROP_TARGET_VALUE
                     FROM T_INV_MAT             M,
                          T_INV_MAT_PROP        P,
                          T_INV_TEMPLET_DETAIL  D,
                          T_PRO_PROCESS_IN_OUT  IO,
                          T_PRO_PRODUCT_PROCESS PPP
                    WHERE P.MAT_ID = M.ID
                      AND P.TEMPLET_DETAIL_ID = D.ID
                      AND M.MAT_CODE = IO.MAT_CODE
                      AND IO.PRODUCT_PROCESS_ID = PPP.ID
                      AND PPP.PROCESS_CODE = 'Jacket-Extrusion'
                      and m.mat_name = '内护套半成品'
                      and d.prop_name = '外径'
                      and ppp.product_crafts_id = V_CRAFTS_ID
                      AND ROWNUM = 1),
                   0)
          INTO RADIUS
          FROM DUAL;
      
        -- A、用[外径、分类]匹配
        SELECT NVL((SELECT QUOTA_IN
                     FROM T_BAS_WORK_HOURS
                    WHERE RADIUS_MAX >= RADIUS
                      AND RADIUS_MIN <= RADIUS
                      AND PRODUCT_CLASSIFY = CLASSIFYNAME
                      AND PROCESS_SECTION = 'Jacket-Extrusion'
                      AND ROWNUM = 1),
                   0)
          INTO QUOTA
          FROM DUAL;
      
        IF QUOTA = 0 THEN
          -- B、用[外径]匹配
          SELECT NVL((SELECT QUOTA_IN
                       FROM T_BAS_WORK_HOURS
                      WHERE RADIUS_MAX >= RADIUS
                        AND RADIUS_MIN <= RADIUS
                        AND PROCESS_SECTION = 'Jacket-Extrusion'
                        AND ROWNUM = 1),
                     0)
            INTO QUOTA
            FROM DUAL;
        END IF;
      
        -- 外护
      ELSIF INSTR(V_PROCESS_NAME, '外') > 0 THEN
      
        SELECT NVL((SELECT P.PROP_TARGET_VALUE
                     FROM T_INV_MAT             M,
                          T_INV_MAT_PROP        P,
                          T_INV_TEMPLET_DETAIL  D,
                          T_PRO_PROCESS_IN_OUT  IO,
                          T_PRO_PRODUCT_PROCESS PPP
                    WHERE P.MAT_ID = M.ID
                      AND P.TEMPLET_DETAIL_ID = D.ID
                      AND M.MAT_CODE = IO.MAT_CODE
                      AND IO.PRODUCT_PROCESS_ID = PPP.ID
                      AND PPP.PROCESS_CODE = 'Jacket-Extrusion'
                      and m.mat_name = '外护套半成品'
                      and d.prop_name = '外径'
                      and ppp.product_crafts_id = V_CRAFTS_ID
                      AND ROWNUM = 1),
                   0)
          INTO RADIUS
          FROM DUAL;
      
        -- A、用[外径、分类]匹配
        SELECT NVL((SELECT QUOTA_OUT
                     FROM T_BAS_WORK_HOURS
                    WHERE RADIUS_MAX >= RADIUS
                      AND RADIUS_MIN <= RADIUS
                      AND PRODUCT_CLASSIFY = CLASSIFYNAME
                      AND PROCESS_SECTION = 'Jacket-Extrusion'
                      AND ROWNUM = 1),
                   0)
          INTO QUOTA
          FROM DUAL;
      
        IF QUOTA = 0 THEN
          -- B、用[外径]匹配
          SELECT NVL((SELECT QUOTA_OUT
                       FROM T_BAS_WORK_HOURS
                      WHERE RADIUS_MAX >= RADIUS
                        AND RADIUS_MIN <= RADIUS
                        AND PROCESS_SECTION = 'Jacket-Extrusion'
                        AND ROWNUM = 1),
                     0)
            INTO QUOTA
            FROM DUAL;
        END IF;
      END IF;
    END IF;
  END IF;

  WORK_HOURS := 0;

  IF QUOTA > 0 THEN
    WORK_HOURS := V_PROD_LENGTH / (QUOTA * 1000) * 8;
  END IF;
  
  IF V_CERTIFICATE = 'DB' THEN
    V_COEFFICIENT  := DB_COEFFICIENT;
  ELSIF V_CERTIFICATE = 'FDB' THEN
    V_COEFFICIENT  := FDB_COEFFICIENT;
  ELSIF V_CERTIFICATE = 'FZG' THEN
    V_COEFFICIENT  := FZG_COEFFICIENT;
  END IF;

  -- 设置出口参数
  V_WORK_HOURS      := WORK_HOURS;
  V_QUOTA           := QUOTA;
  V_errorcode       := 0;
  v_errorcontent    := 'normal';

  -- 未找到任何数据
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    V_WORK_HOURS      := 0;
    V_QUOTA           := 0;
    V_COEFFICIENT  := 0;
    V_errorcode       := 1;
    v_errorcontent    := '未找到任何数据';
  
END;
