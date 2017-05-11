CREATE OR REPLACE PROCEDURE "SP_SET_RELATION_SEQ" (WORK_ORDER_NO IN VARCHAR2) AS
-----在下发生产单时更新生产线列表顺序
v_count number;
BEGIN
     for y in (select * from table(splitstr(WORK_ORDER_NO,','))) loop
         for x in (select * from T_WIP_WO_EQUIP_RELATION w where w.work_order_id = (select id from t_wip_work_order where work_order_no =y.column_value) ) loop
          v_count := 0;
          begin
             select nvl(max(w.seq),0) into v_count from T_WIP_WO_EQUIP_RELATION w where w.seq is not null and w.equip_code = x.equip_code;
            exception
            when others then
            v_count := 0;
            end;
              
            update T_WIP_WO_EQUIP_RELATION k set k.seq = ( select kk from (
                   select rownum + v_count as kk ,b.* from  (
                   select w.* from T_WIP_WO_EQUIP_RELATION w
                   where w.equip_code = x.equip_code
                   and w.seq is null
                   order by w.create_time) b ) c where c.id = k.id)
            where k.equip_code = x.equip_code and k.seq is null;
         end loop;
     end loop;


END SP_SET_RELATION_SEQ;
