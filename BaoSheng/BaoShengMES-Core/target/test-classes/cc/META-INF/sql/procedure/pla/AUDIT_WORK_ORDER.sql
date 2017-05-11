CREATE OR REPLACE
PROCEDURE "AUDIT_WORK_ORDER" (woId IN VARCHAR2,
                              updator IN VARCHAR2,
                              isLock IN VARCHAR2,
                              reqDate IN TIMESTAMP,
                              processCode IN VARCHAR2,
                              orgCode IN VARCHAR2,
                              processId IN VARCHAR2)
AS
    orderTaskId VARCHAR2(50);
    proDecId VARCHAR2(50);

    CURSOR myCursor IS
      SELECT
        OT.ID,
        OT.ORDER_ITEM_PRO_DEC_ID
      FROM T_PLA_ORDER_TASK OT LEFT JOIN T_WIP_WORK_ORDER WO ON OT.WORK_ORDER_NO = WO.WORK_ORDER_NO
      WHERE WO.ID = woId;
BEGIN
    INSERT INTO T_PLA_TOOLES_RP("ID",
                                WORK_ORDER_ID,
                                TOOLES,
                                QUANYITY,
                                PLAN_DATE,
                                STATUS,
                                PROCESS_CODE,
                                ORG_CODE,
                                CREATE_USER_CODE,
                                CREATE_TIME,
                                MODIFY_USER_CODE,
                                MODIFY_TIME)
                          SELECT SYS_GUID(),
                                  woId,
                                  EQUIP_CODE,
                                  1,
                                  reqDate,
                                  'AUDITED',
                                  processCode,
                                  orgCode,
                                  updator,
                                  SYSDATE,
                                  updator,
                                  SYSDATE
                            FROM T_PRO_EQIP_LIST
                            WHERE PROCESS_ID = processId AND TYPE = 'TOOLS';
    OPEN myCursor;
    LOOP
    FETCH myCursor INTO orderTaskId,proDecId;
    EXIT WHEN mycursor%NOTFOUND;
        IF isLock = '1' THEN
          UPDATE T_PLA_CU_ORDER_ITEM_PRO_DEC SET IS_LOCKED = '1',MODIFY_USER_CODE = updator,MODIFY_TIME = SYSDATE WHERE ID = proDecId;
        END IF;

        UPDATE T_PLA_ORDER_TASK SET IS_LOCKED = isLock,STATUS = 'TO_DO',MODIFY_USER_CODE = updator,MODIFY_TIME = SYSDATE WHERE ID = orderTaskId;
    END LOOP;
    CLOSE myCursor;
END;