CREATE OR REPLACE PROCEDURE "FINISH_WORK_ORDE"(V_WORKORDERNO IN VARCHAR2,
                                               UPDATOR       IN VARCHAR2) AS
  nextSection   VARCHAR(50) DEFAULT ''; -- 下一道工序：100说明整个生产单流程结束了
  subLength NUMBER(10) DEFAULT 0; -- 订单已经下发的长度

  CURSOR equipCodeCursor IS -- 生产单关联的设备：加工中或空闲状态的
    SELECT DISTINCT R.EQUIP_CODE AS CODE
      FROM T_PLA_ORDER_TASK R
     WHERE R.WORK_ORDER_NO = V_WORKORDERNO
       AND R.EQUIP_CODE IS NOT NULL
       AND R.STATUS IN ('TO_DO', 'IN_PROGRESS');

  CURSOR orderItemIdCursor IS -- 订单明细id遍历
    SELECT R.CUS_ORDER_ITEM_ID AS ID
      FROM T_WIP_WO_CUSORDER_RELATION R
     WHERE R.WORK_ORDER_NO = V_WORKORDERNO;

BEGIN
--0、更新设备状态：CHANGE_EQUIP_STATUS[Procedures]
--1、更新任务单T_PLA_CU_ORDER_ITEM_PRO_DEC;T_PLA_ORDER_TASK(强制完成生产单时，有任务单状态还是未完成，需要此处更新)
--2、更新生产单状态：T_WIP_WORK_ORDER
--3、更新任务状态：T_PLA_ORDER_TASK
--4、更新明细分解状态：T_PLA_CU_ORDER_ITEM_PRO_DEC
--5、更新订单分卷:T_PLA_CUSTOMER_ORDER_ITEM_DEC
--6、更新订单明细	：FINISH_ORDER_ITEM[Procedures]
--7、更新物料需求状态：T_PLA_MRP

  --0、获取生产单任务里面状态为'TO_DO', 'IN_PROGRESS'的设备，更新成IDLE
  FOR equipCode IN equipCodeCursor LOOP
    CHANGE_EQUIP_STATUS(equipCode.CODE, 'IDLE', UPDATOR);
  END LOOP;
  
  --1、更新任务单T_PLA_CU_ORDER_ITEM_PRO_DEC;T_PLA_ORDER_TASK
  -- (强制完成生产单时，有任务单状态还是未完成，需要此处更新)
  UPDATE T_PLA_CU_ORDER_ITEM_PRO_DEC
     SET STATUS           = 'FINISHED',
         MODIFY_TIME      = SYSDATE,
         MODIFY_USER_CODE = UPDATOR
   WHERE WORK_ORDER_NO = V_WORKORDERNO
     AND STATUS IN ('TO_DO', 'IN_PROGRESS');
  UPDATE T_PLA_ORDER_TASK
     SET STATUS           = 'FINISHED',
         MODIFY_TIME      = SYSDATE,
         MODIFY_USER_CODE = UPDATOR
   WHERE WORK_ORDER_NO = V_WORKORDERNO 
     AND STATUS IN ('TO_DO', 'IN_PROGRESS');

  --2、更新生产单状态
  UPDATE T_WIP_WORK_ORDER
     SET STATUS           = 'FINISHED',
         MODIFY_TIME      = SYSDATE,
         MODIFY_USER_CODE = UPDATOR,
         REAL_END_TIME    = SYSDATE
   WHERE WORK_ORDER_NO = V_WORKORDERNO;

  --3、更新任务状态
  UPDATE T_PLA_ORDER_TASK
     SET STATUS           = 'FINISHED',
         MODIFY_TIME      = SYSDATE,
         MODIFY_USER_CODE = UPDATOR
   WHERE WORK_ORDER_NO = V_WORKORDERNO;

  --4、更新明细分解状态
  UPDATE T_PLA_CU_ORDER_ITEM_PRO_DEC A
     SET STATUS           = 'FINISHED',
         MODIFY_TIME      = SYSDATE,
         MODIFY_USER_CODE = UPDATOR
   WHERE A.WORK_ORDER_NO = V_WORKORDERNO;

  -- 5、判断是否最后一道工序
  SELECT A.NEXT_SECTION
    INTO nextSection
    FROM T_WIP_WORK_ORDER A
   WHERE A.WORK_ORDER_NO = V_WORKORDERNO;

  IF nextSection = '99' THEN

    --循环生产单的关联订单明细ID，判断每一个订单明细的分卷是否全完成
    FOR orderItemId IN orderItemIdCursor LOOP

      -- 6、判断订单下发总长与订单长度比较，是否完成了。 订单关联生产单的最后一道工序的长度和 与 订单长度比较；
      SELECT ((SELECT SUM(B.ORDER_TASK_LENGTH)
          FROM T_WIP_WORK_ORDER A, T_WIP_WO_CUSORDER_RELATION B
         WHERE A.ID = B.WORK_ORDER_ID AND B.CUS_ORDER_ITEM_ID=orderItemId.ID
          AND A.PROCESS_CODE = (SELECT T.PROCESS_CODE FROM T_WIP_WORK_ORDER T WHERE T.WORK_ORDER_NO=V_WORKORDERNO)
          AND A.STATUS='FINISHED') - (SELECT SUM(I.ORDER_LENGTH) FROM T_PLA_CUSTOMER_ORDER_ITEM I WHERE I.ID=orderItemId.ID))
      INTO subLength
      FROM DUAL;

      IF subLength >= 0 THEN

        --7、更新订单分卷
        UPDATE T_PLA_CUSTOMER_ORDER_ITEM_DEC A
         SET A.STATUS         = 'FINISHED',
           MODIFY_TIME      = SYSDATE,
           MODIFY_USER_CODE = UPDATOR
        WHERE A.ORDER_ITEM_ID=orderItemId.ID;
        --8、更新订单明细
        FINISH_ORDER_ITEM(orderItemId.ID, UPDATOR);
      END IF;
    END LOOP;
  
  END IF;

  --7、更新物料需求状态
  UPDATE T_PLA_MRP
     SET STATUS = 'FINISHED'
   WHERE WORK_ORDER_ID IN
         (SELECT ID
            FROM T_WIP_WORK_ORDER
           WHERE WORK_ORDER_NO = V_WORKORDERNO);
END;
