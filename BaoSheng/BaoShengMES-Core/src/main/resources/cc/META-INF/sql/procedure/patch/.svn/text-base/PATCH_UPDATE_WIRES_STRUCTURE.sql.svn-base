CREATE OR REPLACE PROCEDURE "PATCH_UPDATE_WIRES_STRUCTURE"(V_errorcode    OUT NUMBER,
                                                           V_errorcontent OUT VARCHAR2) AS

  -- 删除没有关联的prop
  CURSOR salesOrderItemCursor IS
    SELECT ID, PRODUCT_CODE, PRODUCT_TYPE, PRODUCT_SPEC, WIRES_STRUCTURE
      FROM T_ORD_SALES_ORDER_ITEM
     where CREATE_TIME < SYSDATE - 3
    --   and id = 'XSDDMX2016011200303'
    --and Id in ('XSDDMX2016010800607','XSDDMX2016011200296')
    ;

  wiresStructureStr VARCHAR(2000) := '
         SELECT NVL((SELECT DECODE(NVL(SUBSTR(IMP.PROP_TARGET_VALUE, 0, 1), 1),
                 1,
                 ''A'',
                 2,
                 ''B'',
                 5,
                 ''C'',
                 6,
                 ''C'',
                 ''A'') AS WIRES_STRUCTURE
     FROM T_PRO_PRODUCT_CRAFTS  PPC,
          T_PRO_PRODUCT_PROCESS PPP,
          T_PRO_PROCESS_IN_OUT  PPIO,
          T_INV_MAT             IM,
          T_INV_TEMPLET         IT,
          T_INV_TEMPLET_DETAIL  ITD,
          T_INV_MAT_PROP        IMP
    WHERE PPC.ID = PPP.PRODUCT_CRAFTS_ID
      AND PPP.ID = PPIO.PRODUCT_PROCESS_ID
      AND PPIO.MAT_CODE = IM.MAT_CODE
      AND IM.TEMPLET_ID = IT.ID
      AND IT.ID = ITD.TEMPLET_ID
      AND IM.ID = IMP.MAT_ID
      AND ITD.ID = IMP.TEMPLET_DETAIL_ID
      AND PPP.PROCESS_CODE = ''Extrusion-Single''
      AND ITD.PROP_NAME = ''导体类别''
      AND (PPC.PRODUCT_CODE = :productCode OR PPC.ID = :productCode)
      AND ROWNUM = 1),NULL) FROM DUAL
                ';

  productCode VARCHAR(100);

  wiresStructure VARCHAR(10);

  countResult NUMBER;

BEGIN

  countResult := 0;

  FOR salesOrderItem IN salesOrderItemCursor LOOP
    wiresStructure := 'A';
  
    --dbms_output.put_line(salesOrderItem.PRODUCT_CODE);
    -- 1、根据订单固有的产品编码查询
    EXECUTE IMMEDIATE wiresStructureStr
      INTO wiresStructure
      USING salesOrderItem.PRODUCT_CODE, salesOrderItem.PRODUCT_CODE;
  
    -- 2、没有则：用型号规格到产品表查询产品编码
    if wiresStructure is null or wiresStructure = '' then
      SELECT NVL((select PRODUCT_CODE
                   from T_PLA_PRODUCT
                  where PRODUCT_TYPE =
                        replace(salesOrderItem.PRODUCT_TYPE, '(临时)', '')
                    and PRODUCT_SPEC = salesOrderItem.PRODUCT_SPEC
                    and rownum = 1),
                 NULL)
        into productCode
        FROM DUAL;
    
      -- 3、根据新的产品编码查询
      if productCode is not null then
        -- dbms_output.put_line('--' || productCode);
        EXECUTE IMMEDIATE wiresStructureStr
          INTO wiresStructure
          USING productCode, productCode;
      end if;
    end if;
  
    -- 4、任然没有：设置默认wiresStructure
    if wiresStructure is null or wiresStructure = '' then
      wiresStructure := 'A';
    end if;
  
    -- 5、跟新
  
    if wiresStructure is not null and
       wiresStructure <> salesOrderItem.WIRES_STRUCTURE then
    
      --dbms_output.put_line(salesOrderItem.PRODUCT_CODE || '----' || salesOrderItem.WIRES_STRUCTURE || '----' || wiresStructure);
    
      -- 更新wiresStructure
      update T_ORD_SALES_ORDER_ITEM
         set WIRES_STRUCTURE = wiresStructure
       where ID = salesOrderItem.ID;
      countResult := countResult + 1;
    end if;
  
  end loop;

  COMMIT;
  V_errorcode := countResult;
END;
