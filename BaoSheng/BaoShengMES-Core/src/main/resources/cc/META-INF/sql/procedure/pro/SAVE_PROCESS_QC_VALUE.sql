CREATE OR REPLACE  PROCEDURE "SAVE_PROCESS_QC_VALUE"(checkItemCode IN VARCHAR2,
													 qcValue IN VARCHAR2,
													 qcResult IN VARCHAR2,
													 checkEqipCode IN VARCHAR2,
													 eqipCode IN VARCHAR2,
													 workOrderNo IN VARCHAR2,
													 creater IN VARCHAR2,
													 detectType IN VARCHAR2,
													 coilNum IN INTEGER,
													 checkItemName IN VARCHAR2)
AS
		contractNo			T_ORD_SALES_ORDER.CONTRACT_NO%TYPE;
		salesOrderNo		T_PRO_PROCESS_QC_VALUE.SALES_ORDER_NO%TYPE;
		customerOrderNo		T_PRO_PROCESS_QC_VALUE.CUSTOMER_ORDER_NO%TYPE;
		custProductType		T_PRO_PROCESS_QC_VALUE.CUST_PRODUCT_TYPE%TYPE;
		custProductSpec		T_PRO_PROCESS_QC_VALUE.CUST_PRODUCT_SPEC%TYPE;
		productCode			T_PRO_PROCESS_QC_VALUE.PRODUCT_CODE%TYPE;
		productType			T_PRO_PROCESS_QC_VALUE.PRODUCT_TYPE%TYPE;
		productSpec			T_PRO_PROCESS_QC_VALUE.PRODUCT_SPEC%TYPE;
		craftsId			T_PRO_PROCESS_QC_VALUE.CRAFTS_ID%TYPE;
		processId			T_PRO_PROCESS_QC_VALUE.PROCESS_ID%TYPE;
		processCode			T_PRO_PROCESS_QC_VALUE.PROCESS_CODE%TYPE;
		processName			T_PRO_PROCESS_QC_VALUE.PROCESS_NAME%TYPE;
		equipName		    VARCHAR2(200); -- 设备名称
	
	
		cursor mycursor is
		    SELECT
				    DISTINCT
						e.CONTRACT_NO,
						e.SALES_ORDER_NO,
						d.CUSTOMER_ORDER_NO,
						f.CUST_PRODUCT_TYPE,
						f.CUST_PRODUCT_SPEC,
						f.PRODUCT_CODE,
						f.PRODUCT_TYPE,
						f.PRODUCT_SPEC,
						a.CRAFTS_ID,
						a.PROCESS_ID,
						a.PROCESS_CODE,
						a.PROCESS_NAME
				FROM T_PLA_CU_ORDER_ITEM_PRO_DEC a
					LEFT JOIN T_PLA_CUSTOMER_ORDER_ITEM_DEC b ON a.ORDER_ITEM_DEC_ID = b.ID
					LEFT JOIN T_PLA_CUSTOMER_ORDER_ITEM c ON b.ORDER_ITEM_ID = c.Id
					LEFT JOIN T_PLA_CUSTOMER_ORDER d ON c.CUSTOMER_ORDER_ID = d.ID
					LEFT JOIN T_ORD_SALES_ORDER e ON d.SALES_ORDER_ID = e.Id
					LEFT JOIN T_ORD_SALES_ORDER_ITEM f ON c.SALES_ORDER_ITEM_ID = f.ID
				WHERE a.ID IN (
				  SELECT ORDER_ITEM_PRO_DEC_ID FROM T_PLA_ORDER_TASK WHERE WORK_ORDER_NO = workOrderNo
			    AND STATUS IN ('TO_DO','IN_PROGRESS','FINISHED')
				);

BEGIN
	
	-- 查询设备名称
	SELECT NVL(EQUIP_ALIAS, NAME) || '[' || CODE || ']' INTO equipName FROM T_FAC_EQIP_INFO WHERE CODE ＝ EQIPCODE;
	
	OPEN mycursor;
	LOOP
	FETCH mycursor into
            contractNo,
            salesOrderNo,
            customerOrderNo,
			custProductType,
			custProductSpec,
			productCode,
			productType,
			productSpec,
			craftsId,
			processId,
			processCode,
			processName;
	EXIT WHEN mycursor%NOTFOUND;
		INSERT INTO T_PRO_PROCESS_QC_VALUE(
										   ID,
										   CONTRACT_NO,
										   SALES_ORDER_NO,
										   CUSTOMER_ORDER_NO,
										   CUST_PRODUCT_TYPE,
										   CUST_PRODUCT_SPEC,
										   PRODUCT_CODE,
										   PRODUCT_TYPE,
										   PRODUCT_SPEC,
										   WORK_ORDER_NO,
										   CRAFTS_ID,
										   PROCESS_ID,
										   PROCESS_CODE,
										   PROCESS_NAME,
										   SAMPLE_BARCODE,
										   CHECK_ITEM_CODE,
										   CHECK_ITEM_NAME,
										   EQIP_CODE,
										   EQUIP_NAME,
										   QC_VALUE,
										   QC_RESULT,
										   CHECK_EQIP_CODE,
										   "TYPE",
										   COIL_NUM,
										   CREATE_USER_CODE,
										   CREATE_TIME,
										   MODIFY_USER_CODE,
										   MODIFY_TIME
									) VALUES(
										   SYS_GUID(),
										   contractNo,
										   salesOrderNo,
										   customerOrderNo,
										   custProductType,
										   custProductSpec,
										   productCode,
										   productType,
										   productSpec,
										   workOrderNo,
										   craftsId,
										   processId,
										   processCode,
										   processName,
										   '',
										   checkItemCode,
										   checkItemName,
										   eqipCode,
										   equipName,
										   qcValue,
										   qcResult,
										   checkEqipCode,
										   detectType,
										   coilNum,
										   creater,
										   sysdate,
										   creater,
										   sysdate
									);
	END LOOP;
	CLOSE mycursor;
END;