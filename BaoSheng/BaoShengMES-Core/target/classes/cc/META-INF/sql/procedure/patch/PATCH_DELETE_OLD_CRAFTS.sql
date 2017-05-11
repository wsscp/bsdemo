-- 删除就工艺
CREATE OR REPLACE 
PROCEDURE "DELETE_OLD_CRAFTS" (V_errorcode OUT NUMBER, V_errorcontent OUT VARCHAR2)
AS

 -- 重复产品的游标
 CURSOR productCodeCursor IS
	select t.product_code
	  from t_pro_product_crafts t
	 group by t.product_code
	having count(*) > 1;
	
 -- 工艺游标
 CURSOR craftsCursor(productCode VARCHAR2) IS
    SELECT * FROM T_PRO_PRODUCT_CRAFTS where PRODUCT_CODE = productCode
    order by CREATE_TIME desc;
    
 -- 工序游标
 CURSOR processCursor(craftsId VARCHAR2) IS
    SELECT * FROM T_PRO_PRODUCT_PROCESS where PRODUCT_CRAFTS_ID = craftsId;
    
 -- 工序游标
 CURSOR qcCursor(processId VARCHAR2) IS
    SELECT * FROM T_PRO_PROCESS_QC where PROCESS_ID = processId;
    
 -- 工序游标
 CURSOR equipListCursor(processId VARCHAR2) IS
    SELECT * FROM T_PRO_EQIP_LIST where PROCESS_ID = processId;
    
 countCrafts NUMBER;
 usedCrafts NUMBER;
 usedCraftsId VARCHAR(50);
 countResult NUMBER;
BEGIN	
	--删除的表
	--t_pro_eqip_list
	--t_pro_process_in_out
	--t_pro_process_qc
	--t_pro_process_qc_eqip
	--t_pro_process_qc_value
	--t_pro_process_receipt
	--t_pro_product_crafts
	--t_pro_product_process
	countResult := 0;
	
	FOR productCode IN productCodeCursor LOOP
	  countCrafts := 1;
	  FOR crafts IN craftsCursor(productCode.PRODUCT_CODE) LOOP
	    if countCrafts = 1 then
          usedCraftsId := crafts.ID;
        end if;
	    if countCrafts > 1 then
	      --select count(*) into usedCrafts from t_pla_customer_order_item where crafts_id = crafts.ID;
	      --if usedCrafts = 0 then
	        update t_pla_customer_order_item set crafts_id=usedCraftsId where crafts_id = crafts.ID;
            update t_pla_cu_order_item_pro_dec set crafts_id=usedCraftsId where crafts_id = crafts.ID;
	        --dbms_output.put_line(crafts.ID);
	        --goto CONTINUE_TEST;
		    FOR process IN processCursor(crafts.ID) LOOP
		      FOR qc IN qcCursor(process.ID) LOOP
		        delete from t_pro_process_qc_eqip where qc_id=qc.ID;
			  end loop;
			  FOR equipList IN equipListCursor(process.ID) LOOP
			    delete from t_pro_process_receipt where eqip_list_id=equipList.ID;
			  end loop;
	
		    delete from t_pro_process_qc where process_id = process.ID;
		    delete from t_pro_process_qc_value where process_id = process.ID;
		    delete from t_pro_eqip_list where process_id = process.ID;
		    delete from t_pro_process_in_out where product_process_id = process.ID;
		    delete from t_pro_product_process where id = process.ID;
		    end loop;
		    delete from t_pro_product_crafts where id = crafts.ID;
		    --<<CONTINUE_TEST>> null;
		  --end if;
		  countResult := countResult + 1;
		end if;
		countCrafts := countCrafts+1;
	  end loop;
	end loop;
	V_errorcode := countResult;
END;
