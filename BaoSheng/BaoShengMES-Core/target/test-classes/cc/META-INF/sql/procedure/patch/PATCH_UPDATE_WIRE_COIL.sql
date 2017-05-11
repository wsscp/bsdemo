CREATE OR REPLACE PROCEDURE "PATCH_UPDATE_WIRE_COIL"(V_errorcode    OUT NUMBER,
                                                     V_errorcontent OUT VARCHAR2) AS

  -- 删除没有关联的prop
  CURSOR orderCursor IS
    SELECT WORK_ORDER_NO, PROCESS_GROUP_RESPOOL
      FROM T_WIP_WORK_ORDER
     where PROCESS_CODE = 'Steam-Line'
    --and Id in ('XSDDMX2016010800607','XSDDMX2016011200296')
    ;

  CURSOR prodecCursor(workOrderNo VARCHAR2) IS
    select *
      from T_PLA_CU_ORDER_ITEM_PRO_DEC
     where WORK_ORDER_NO = workOrderNo;

  countResult NUMBER;

BEGIN

  countResult := 0;

  FOR orderItem IN orderCursor LOOP
  
    FOR prodec IN prodecCursor(orderItem.PROCESS_GROUP_RESPOOL) LOOP
      update T_PLA_CU_ORDER_ITEM_PRO_DEC
         set WIRE_COIL       = prodec.WIRE_COIL,
             WIRE_COIL_COUNT = prodec.WIRE_COIL_COUNT
       where ORDER_ITEM_DEC_ID = prodec.ORDER_ITEM_DEC_ID
         and COLOR = prodec.COLOR
         and WORK_ORDER_NO = orderItem.WORK_ORDER_NO;
      countResult := countResult + 1;
    end loop;
  end loop;
  COMMIT;
  V_errorcode := countResult;
END;
