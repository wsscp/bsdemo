CREATE OR REPLACE FUNCTION GETCOLORSTR(V_COLOR IN VARCHAR2) RETURN VARCHAR2 IS

  -- 返回的颜色
  R_COLOR VARCHAR2(1000);

  TMP_COLOR VARCHAR2(1000) := replace(replace(replace(V_COLOR, '双色', ''), '本色', ''), '色', '');

  -- 定义游标：将颜色转换成一个数组表
  CURSOR colorCursor IS
    select distinct (CASE
                      WHEN A.COLOR IS NULL THEN
                       NULL
                      WHEN instr(A.COLOR, '-') > 0 THEN
                       NULL
                      WHEN instr(A.COLOR, '#') = 0 THEN
                       NULL
                      ELSE
                       to_number(SUBSTR(A.COLOR,
                                        0,
                                        INSTR(A.COLOR, '#', 1, 1) - 1))
                    END) AS COLORNUM,
                    (CASE
                      WHEN A.COLOR IS NULL THEN
                       A.COLOR
                      WHEN instr(A.COLOR, '-') > 0 THEN
                       A.COLOR
                      WHEN instr(A.COLOR, '#') = 0 THEN
                       A.COLOR
                      ELSE
                       SUBSTR(A.COLOR,
                              INSTR(A.COLOR, '#') + 1,
                              LENGTH(A.COLOR))
                    END) AS COLORV,
                    A.COLOR
      from (SELECT REGEXP_SUBSTR(TMP_COLOR, '[^,]+', 1, LEVEL) AS COLOR
              FROM DUAL
            CONNECT BY LEVEL <= REGEXP_COUNT(TMP_COLOR, ',') + 1) A
     order by COLORV, COLORNUM;

  -- 起始位置
  firstNum NUMBER;
  -- 上一个位置
  lastNum NUMBER;
  -- 上一个颜色
  lastColor VARCHAR2(20);

  -- 总行数
  totalCount NUMBER;
  -- 行数下标
  i NUMBER;
  -- 连续次数
  continueCont NUMBER;
BEGIN
  -- 初始化所有参数： firstNum、lastNum、totalCount、i、continueCont
  firstNum := 0;
  lastNum  := 0;
  select count(*)
    into totalCount
    from (SELECT distinct REGEXP_SUBSTR(TMP_COLOR, '[^,]+', 1, LEVEL) AS COLOR
            FROM DUAL
          CONNECT BY LEVEL <= REGEXP_COUNT(TMP_COLOR, ',') + 1);
  i            := 1;
  continueCont := 1;

  -- 遍历游标处理每一此颜色
  FOR color IN colorCursor LOOP

    -- 判断是否是连续的颜色
    if (color.COLORNUM is not null and color.COLORNUM - lastNum = 1 and
       color.COLORV = lastColor) then
      lastNum      := color.COLORNUM;
      lastColor    := color.COLORV;
      continueCont := continueCont + 1;

    else
      -- 处理上次颜色是1#红、2#绿、3#黄
      if firstNum is not null and firstNum > 0 then
        if R_COLOR is not null then
          R_COLOR := R_COLOR || ',';
        end if;
        if continueCont <= 1 then
          R_COLOR := R_COLOR || firstNum || '#' || lastColor;
        else
          R_COLOR := R_COLOR || firstNum || '#-' || lastNum || '#/' ||
                     lastColor;
        end if;
      end if;

      -- 当颜色是红、绿、黄，直接添加
      if color.COLORNUM is null then
        if R_COLOR is not null then
          R_COLOR := R_COLOR || ',';
        end if;
        R_COLOR := R_COLOR || color.COLOR;
      end if;

      -- 重新初始化
      lastNum      := color.COLORNUM;
      firstNum     := color.COLORNUM;
      lastColor    := color.COLORV;
      continueCont := 1;
    end if;

    -- 当递归到最后一项时，所做的处理
    if i = totalCount and firstNum is not null then
      if R_COLOR is not null then
        R_COLOR := R_COLOR || ',';
      end if;

      if continueCont <= 1 then
        R_COLOR := R_COLOR || color.COLORNUM || '#' || color.COLORV;
      else
        R_COLOR := R_COLOR || firstNum || '#-' || lastNum || '#/' ||
                   lastColor;
      end if;
    end if;

    -- 行数加1
    i := i + 1;
  END LOOP;

  RETURN(R_COLOR);
END GETCOLORSTR;
