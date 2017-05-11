CREATE OR REPLACE PROCEDURE "UPDATE_INV_OA_USE_LOG"(orderItemProDecId IN VARCHAR,updateUser IN VARCHAR)
AS
	oaUseLogId 			T_PLA_INV_OA_USE_LOG."ID"%TYPE;
	inventorydetailId 	T_PLA_INV_OA_USE_LOG.INVENTORY_DETAIL_ID%TYPE;
	inventoryId 		T_INV_INVENTORY.ID%TYPE;
	length				T_INV_INVENTORY_DETAIL.LENGTH%TYPE;
	lockedQuantity      T_INV_INVENTORY.LOCKED_QUANTITY%TYPE;
	
	CURSOR mycursor IS 
		SELECT ID,INVENTORY_DETAIL_ID FROM T_PLA_INV_OA_USE_LOG WHERE REF_ID = orderItemProDecId;
		
BEGIN
	OPEN mycursor;
	LOOP
	FETCH mycursor INTO oaUseLogId,inventorydetailId;
	EXIT WHEN mycursor%NOTFOUND;
		--更新日志的状态为已取消
		UPDATE T_PLA_INV_OA_USE_LOG SET STATUS = 'CANCELED' WHERE ID = oaUseLogId;
		--更新日志明细表中状态为可用
		UPDATE T_INV_INVENTORY_DETAIL SET STATUS = 'AVAILABLE',MODIFY_TIME = sysdate,MODIFY_USER_CODE =updateUser WHERE ID = inventorydetailId;
		--查询出冲抵的长度和库存ID
		SELECT INVENTORY_ID,LENGTH INTO inventoryId,length FROM T_INV_INVENTORY_DETAIL WHERE ID = inventorydetailId;
		--从库存表中查询锁定的长度
		SELECT LOCKED_QUANTITY INTO lockedQuantity FROM T_INV_INVENTORY WHERE ID = inventoryId;
		--更新库存表中锁定的长度 =  锁定长度 - 日志明细中冻结长度
		UPDATE T_INV_INVENTORY SET LOCKED_QUANTITY = lockedQuantity - length,MODIFY_TIME = sysdate,MODIFY_USER_CODE =updateUser WHERE ID = inventoryId;
	END LOOP;
  	CLOSE mycursor;
END UPDATE_INV_OA_USE_LOG;