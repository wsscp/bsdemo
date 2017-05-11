CREATE OR REPLACE FUNCTION VALIDATEPRODUCTCRAFTS(V_CRAFTSID IN VARCHAR2)
  RETURN VARCHAR IS

  ISPASS VARCHAR(500) := ''; -- 返回结果：默认通过

  DATA_COUNT NUMBER; -- 计数

  -- 工序游标
  CURSOR PROCESSCURSOR IS
    SELECT *
      FROM T_PRO_PRODUCT_PROCESS_WIP
     WHERE PRODUCT_CRAFTS_ID = V_CRAFTSID;

BEGIN
  -- DESCRIBE：工艺是否有效
  -- 1、包括工序是否连贯，
  -- 2、是否没道工序都有投入产出，
  -- 3、产出投入是否连贯，
  -- 4、产出是否有且只有一个，
  -- 5、是否都有生产线

  -- 是否最后一到只有一个，同时是否工序存在
  SELECT COUNT(*)
    INTO DATA_COUNT
    FROM T_PRO_PRODUCT_PROCESS_WIP
   WHERE PRODUCT_CRAFTS_ID = V_CRAFTSID
     AND NEXT_PROCESS_ID = '-1';
  IF DATA_COUNT = 0 THEN
    ISPASS := '工艺不存在';
  ELSIF DATA_COUNT > 1 THEN
    ISPASS := '工艺序号错误';
  ELSE
    FOR PROCESS IN PROCESSCURSOR LOOP
      -- 是否有生产线，生产线取值变更，无需校验
      -- SELECT COUNT(*) INTO DATA_COUNT FROM T_PRO_EQIP_LIST WHERE PROCESS_ID = PROCESS.ID;
      -- IF DATA_COUNT = 0 THEN ISPASS := 0; GOTO FUN_OVER; END IF;
      -- 是否投入不少于一个
      SELECT COUNT(*)
        INTO DATA_COUNT
        FROM T_PRO_PROCESS_IN_OUT_WIP
       WHERE IN_OR_OUT = 'IN'
         AND PRODUCT_PROCESS_ID = PROCESS.ID;
      IF DATA_COUNT = 0 THEN
        ISPASS := ISPASS || ';' || PROCESS.PROCESS_NAME || '-' ||
                  PROCESS.SEQ || '工序没有投入';
        --GOTO FUN_OVER;
      END IF;
      -- 是否产出有且只有一个
      SELECT 
		CASE PROCESS.PROCESS_CODE
             WHEN 'Irradition' THEN 1
             ELSE COUNT(*) END
        INTO DATA_COUNT
        FROM T_PRO_PROCESS_IN_OUT_WIP
       WHERE IN_OR_OUT = 'OUT'
         AND PRODUCT_PROCESS_ID = PROCESS.ID;
      IF DATA_COUNT = 0 THEN
        ISPASS := ISPASS || ';' || PROCESS.PROCESS_NAME || '-' ||
                  PROCESS.SEQ || '工序没有产出';
        --  GOTO FUN_OVER;
      ELSIF DATA_COUNT > 1 THEN
        ISPASS := ISPASS || ';' || PROCESS.PROCESS_NAME || '-' ||
                  PROCESS.SEQ || '工序有多个产出';
        --  GOTO FUN_OVER;
      END IF;

    END LOOP;
  END IF;

  <<FUN_OVER>>
  RETURN(ISPASS);
END VALIDATEPRODUCTCRAFTS;
