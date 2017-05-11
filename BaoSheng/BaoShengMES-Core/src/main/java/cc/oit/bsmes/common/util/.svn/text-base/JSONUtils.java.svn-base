package cc.oit.bsmes.common.util;


import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JinHanyun on 2014/7/9 0009.
 */
public class JSONUtils{

    /**
     *
     * @param json
     * @param cls
     * @return
     */
    public static Object jsonToBean(JSONObject json, Class cls){
        Object obj = null;
        try{
            obj = cls.newInstance();

            // 取出Bean里面的所有方法
            Method[] methods = cls.getMethods();
            for(int i=0; i < methods.length; i++)
            {
                // 取出方法名
                String methodName = methods[i].getName();
                // 若是方法名不是以set开始的则退出本次循环
                if(methodName.indexOf("set") < 0)
                {
                    continue;
                }
                // 取出方法的类型
                Class[] clss = methods[i].getParameterTypes();
                if(clss.length != 1)
                {
                    continue;
                }

                // 类型
                String type = clss[0].getSimpleName();

                String key = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);

                // 如果map里有该key
                if(json.get(key) != null){
                    setValue(type, json.get(key), methods[i], obj);
                }
            }
        } catch (Exception ex){
        }
        return obj;
    }

    private static void setValue(String type, Object value, Method method, Object bean)
    {
        if(value != null && !"".equals(value))
        {
            try
            {
                if("String".equals(type))
                {
                    method.invoke(bean, new Object[] {value});
                } else if("int".equals(type) || "Integer".equals(type))
                {
                    method.invoke(bean, new Object[] { new Integer(""+value) });
                } else if("double".equals(type) || "Double".equals(type))
                {
                    method.invoke(bean, new Object[] { new Double(""+value) });
                } else if("float".equals(type) || "Float".equals(type))
                {
                    method.invoke(bean, new Object[] { new Float(""+value) });
                } else if("long".equals(type) || "Long".equals(type))
                {
                    method.invoke(bean, new Object[] { new Long(""+value) });
                } else if("int".equals(type) || "Integer".equals(type))
                {
                    method.invoke(bean, new Object[] { new Integer(""+value) });
                } else if("boolean".equals(type) || "Boolean".equals(type))
                {
                    method.invoke(bean, new Object[] { Boolean.valueOf(""+value) });
                } else if("BigDecimal".equals(type))
                {
                    method.invoke(bean, new Object[] { new BigDecimal(""+value) });
                } else if("Date".equals(type))
                {
                    Class dateType = method.getParameterTypes()[0];
                    if("java.util.Date".equals(dateType.getName()))
                    {
                        java.util.Date date = null;
                        if("String".equals(value.getClass().getSimpleName()))
                        {
                            String time = String.valueOf(value);
                            String format = null;
                            if(time.indexOf(":") > 0)
                            {
                                if(time.indexOf(":") == time.lastIndexOf(":"))
                                {
                                    format = "yyyy-MM-dd H:mm";
                                }
                                else
                                {
                                    format = "yyyy-MM-dd H:mm:ss";
                                }
                            }
                            else
                            {
                                format = "yyyy-MM-dd";
                            }
                            SimpleDateFormat sf = new SimpleDateFormat();
                            sf.applyPattern(format);
                            date = sf.parse(time);
                        }
                        else
                        {
                            date = (java.util.Date) value;
                        }

                        if(date != null)
                        {
                            method.invoke(bean, new Object[] { date });
                        }
                    }
                    else if("java.sql.Date".equals(dateType.getName()))
                    {
                        Date date = null;
                        if("String".equals(value.getClass().getSimpleName()))
                        {
                            String time = String.valueOf(value);
                            String format = null;
                            if(time.indexOf(":") > 0)
                            {
                                if(time.indexOf(":") == time.lastIndexOf(":"))
                                {
                                    format = "yyyy-MM-dd H:mm";
                                }
                                else
                                {
                                    format = "yyyy-MM-dd H:mm:ss";
                                }
                            }
                            else
                            {
                                format = "yyyy-MM-dd";
                            }
                            SimpleDateFormat sf = new SimpleDateFormat();
                            sf.applyPattern(format);
                            date = new Date(sf.parse(time).getTime());
                        }
                        else
                        {
                            date = (Date) value;
                        }

                        if(date != null)
                        {
                            method.invoke(bean, new Object[] { date });
                        }
                    }
                } else if("Timestamp".equals(type))
                {
                    Timestamp timestamp = null;
                    if("String".equals(value.getClass().getSimpleName()))
                    {
                        String time = String.valueOf(value);
                        String format = null;
                        if(time.indexOf(":") > 0)
                        {
                            if(time.indexOf(":") == time.lastIndexOf(":"))
                            {
                                format = "yyyy-MM-dd H:mm";
                            }
                            else
                            {
                                format = "yyyy-MM-dd H:mm:ss";
                            }
                        }
                        else
                        {
                            format = "yyyy-MM-dd";
                        }
                        SimpleDateFormat sf = new SimpleDateFormat();
                        sf.applyPattern(format);
                        timestamp = new Timestamp(sf.parse(time).getTime());
                    }
                    else
                    {
                        timestamp = (Timestamp) value;
                    }
                    if(timestamp != null)
                    {
                        method.invoke(bean, new Object[] { timestamp });
                    }
                } else if("byte[]".equals(type))
                {
                    method.invoke(bean, new Object[] { new String(""+value).getBytes() });
                }else if("String[]".equals(type)){
                    method.invoke(bean,new Object[]{String.valueOf(value).split(",")});
                }
            } catch(Exception ex){
            }
        }
    }

    public static String getValue(JSONObject queryParams, String key) {
        Object object = queryParams.get(key);
        if (object == null) {
            return "";
        }
        return String.valueOf(object);
    }
}
