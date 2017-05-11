CREATE OR REPLACE PROCEDURE "PATCH_DELETE_OLD_MAT_PROP"(V_errorcode    OUT NUMBER,
                                                  V_errorcontent OUT VARCHAR2) AS

  -- 删除没有关联的prop
  CURSOR matTempleCursor2 IS

    select p.id
      from t_inv_mat_prop p
      left join t_inv_mat m
        on p.mat_id = m.id
     where m.id is null;

  -- 删除无效的mat_code信息
  CURSOR matTempleCursor3 IS
    select id, mat_code, to_char(create_time, 'yyyy-mm-dd hh24:mi:ss')
      from t_inv_mat t
     where t.mat_type <> 'MATERIALS'
       and t.mat_code not in
           (select distinct mat_code from t_pro_process_in_out)
     order by t.create_time desc;

  -- 删除旧的无用数据
  CURSOR matTempleCursor1 IS
    select *
      from (select MAT_ID, TEMPLET_DETAIL_ID
              from T_INV_MAT_PROP
             group by MAT_ID, TEMPLET_DETAIL_ID
            having count(1) > 1) t;

  CURSOR propCursor(matId VARCHAR2, templetDetailId VARCHAR2) IS
    SELECT *
      FROM T_INV_MAT_PROP
     where MAT_ID = matId
       and TEMPLET_DETAIL_ID = templetDetailId
     order by CREATE_TIME desc;

  countProp NUMBER;

  countResult NUMBER;

BEGIN

  countResult := 0;

  FOR prop IN matTempleCursor2 LOOP
    delete from T_INV_MAT_PROP where id = prop.id;
    countResult := countResult + 1;
  end loop;

  FOR mat IN matTempleCursor3 LOOP
    delete from T_INV_MAT_PROP where mat_id = mat.id;
    delete from t_inv_mat where id = mat.id;
    countResult := countResult + 1;
  end loop;

  FOR matTemple IN matTempleCursor1 LOOP
    countProp := 1;
    FOR prop IN propCursor(matTemple.MAT_ID, matTemple.TEMPLET_DETAIL_ID) LOOP
      if countProp > 1 then
        delete from T_INV_MAT_PROP where id = prop.id;
        countResult := countResult + 1;
      end if;
      countProp := countProp + 1;
    end loop;
  end loop;
  COMMIT;
  V_errorcode := countResult;
END;
