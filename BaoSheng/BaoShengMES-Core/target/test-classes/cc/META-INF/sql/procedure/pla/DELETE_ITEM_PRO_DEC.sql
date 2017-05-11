--删除计算OA上次分解的信息
CREATE OR REPLACE PROCEDURE "DELETE_ITEM_PRO_DEC"(V_ORDERITEMID IN VARCHAR,
                                                  V_UPDATEUSER  IN VARCHAR) AS
  -- 1、订单分卷游标
  CURSOR orderItemDecCursor IS
    SELECT *
      FROM T_PLA_CUSTOMER_ORDER_ITEM_DEC
     WHERE ORDER_ITEM_ID = V_ORDERITEMID;

  -- 2、订单工序分解明细游标
  CURSOR oitemProDecOaCursor(itemDecId VARCHAR2) IS
    SELECT PDO.ID
      FROM T_PLA_CU_ORDER_ITEM_PRO_DEC_OA PDO
     WHERE PDO.ORDER_ITEM_DEC_ID = itemDecId;

BEGIN

  FOR orderItemDec IN orderItemDecCursor LOOP
    FOR oitemProDecOa IN oitemProDecOaCursor(orderItemDec.ID) LOOP
      -- 1、删除PRODEC对应的设备占用
      DELETE FROM T_FAC_WORK_TASKS
       WHERE ORDER_ITEM_PRO_DEC_ID = oitemProDecOa.ID;
      -- 2、删除PRODEC
      DELETE FROM T_PLA_CU_ORDER_ITEM_PRO_DEC_OA
       WHERE ID = oitemProDecOa.ID;
    END LOOP;
  END LOOP;
  -- 3、删除订单产品对应的OA物料需求
  DELETE FROM T_PLA_MRP_OA WHERE ORDER_ITEM_ID = V_ORDERITEMID;

END DELETE_ITEM_PRO_DEC;
