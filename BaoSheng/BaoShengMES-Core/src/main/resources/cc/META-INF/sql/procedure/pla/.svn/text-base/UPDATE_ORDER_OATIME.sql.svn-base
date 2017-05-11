CREATE OR REPLACE PROCEDURE "UPDATE_ORDER_OATIME"(V_ORG_CODE IN VARCHAR2) AS

CURSOR oaUptempCursor IS
    SELECT * FROM T_PLA_OA_UPTEMP
      WHERE ORG_CODE = V_ORG_CODE;
      
CURSOR tashCursor IS
    SELECT * FROM t_fac_work_tasks
      WHERE finishwork = '0';
      
BEGIN
  
  FOR oaUptemp IN oaUptempCursor LOOP
      IF oaUptemp.TABLE_NAME = 'T_PLA_CUSTOMER_ORDER_ITEM' THEN 
          UPDATE T_PLA_CUSTOMER_ORDER_ITEM SET 
           PLAN_START_DATE = oaUptemp.PLAN_START_DATE, 
           PLAN_FINISH_DATE = oaUptemp.PLAN_FINISH_DATE,
           LAST_OA = oaUptemp.LAST_OA
          WHERE
           ID = oaUptemp.TABLE_UID;
      END IF;
      
      IF oaUptemp.TABLE_NAME = 'T_PLA_CUSTOMER_ORDER' THEN 
          UPDATE T_PLA_CUSTOMER_ORDER SET 
           PLAN_START_DATE = oaUptemp.PLAN_START_DATE, 
           PLAN_FINISH_DATE = oaUptemp.PLAN_FINISH_DATE,
           LAST_OA = oaUptemp.LAST_OA
          WHERE
           ID = oaUptemp.TABLE_UID;
      END IF;
  
  END LOOP;
  
  FOR tash IN tashCursor LOOP
      UPDATE t_pla_cu_order_item_pro_dec_oa SET 
       LATEST_START_DATE = tash.WORK_START_TIME, 
       LATEST_FINISH_DATE = tash.WORK_END_TIME,
       EQUIP_CODE = tash.EQUIP_CODE
      WHERE
       ID = tash.ORDER_ITEM_PRO_DEC_ID;
  END LOOP;
  
  COMMIT;
END;
