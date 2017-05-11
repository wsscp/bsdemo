package cc.oit.bsmes.common.service.impl;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.ReflectUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Transactional(rollbackFor = { Exception.class }, readOnly = true)
public abstract class BaseServiceImpl<T extends Base> implements BaseService<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaseDAO<T> baseDAO;

	@Override
	public List<T> getAll() {
		return baseDAO.getAll();
	}

	@Override
	public T getById(String id) {
		return baseDAO.getById(id);
	}

	@Override
	public int count(T findParams) {
		return baseDAO.count(findParams);
	}

    @Override
    public int countQuery(List<CustomQueryParam> customQueryParams) {
        return baseDAO.countQuery(customQueryParams);
    }

    @Override
    public List<T> query(List<CustomQueryParam> customQueryParams) {
        return baseDAO.query(customQueryParams, null);
    }

    @Override
    public List<T> query(List<CustomQueryParam> customQueryParams, Integer start, Integer limit, List<Sort> sortList) {
        if (start != null && limit != null) {
		    SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        }
        return baseDAO.query(customQueryParams, sortList);
    }

    @Override
	public List<T> find(T findParams, Integer start, Integer limit, List<Sort> sortList) {
        if (start != null && limit != null) {
            SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        }
		return baseDAO.find(findParams);
	}
	
	@Override
	public List<T> findByObj(T findParams) {
		return baseDAO.find(findParams);
	}
	
	@Override
	public List<T> getByObj(T findParams) {
		return baseDAO.get(findParams);
	}

	@Override
	@Transactional(readOnly = false)
	public void insert(T t) throws DataCommitException {
		if (baseDAO.insert(t) != 1) {
			throw new DataCommitException();
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void insert(List<T> list) throws DataCommitException {
		for (T t : list) {
            insert(t);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(String id) throws DataCommitException {
		if (baseDAO.delete(id) != 1) {
			throw new DataCommitException();
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(List<String> list) throws DataCommitException {
		for (String id : list) {
            deleteById(id);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(T t) throws DataCommitException {
		if (baseDAO.delete(t.getId()) != 1) {
			throw new DataCommitException();
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(List<T> list) throws DataCommitException {
		for (T t : list) {
			delete(t);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void update(T t) throws DataCommitException {
		if (baseDAO.update(t) != 1) {
			throw new DataCommitException();
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void update(List<T> list) throws DataCommitException {
		for (T t : list) {
			update(t);
		}
	}

    @Override
    public void export(OutputStream outputStream, String sheetName, JSONArray columns,JSONObject queryFilter) throws IOException, WriteException, InvocationTargetException,
            IllegalAccessException,
            NoSuchMethodException {
        WritableWorkbook wwb = Workbook.createWorkbook(outputStream);
        WritableSheet sheet = wwb.createSheet(sheetName, 0);
        for (int i = 0; i < columns.size(); i++) {
            JSONObject jsonObject = (JSONObject) columns.get(i);
            sheet.addCell(new Label(i, 0, jsonObject.getString("text")));
        }

        List<T> list = findForExport(queryFilter);
        if (list.size() == 0) {
            wwb.write();
            wwb.close();
            return;
        }

        Class<?> modelClass = list.get(0).getClass();
        List<Method> readMethods = new ArrayList<Method>();
        for (int i = 0; i < columns.size(); i++) {
            JSONObject jsonObject = (JSONObject) columns.get(i);
            String prop = (String) jsonObject.get("dataIndex");
            try {
                Method readMethod = ReflectUtils.getBeanGetter(modelClass, prop);
                readMethods.add(readMethod);
            } catch (NoSuchMethodException e) {
                readMethods.add(null);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < readMethods.size(); j++) {
                T obj = list.get(i);

                Method readMethod = readMethods.get(j);
                String valueStr = null;
                if (readMethod != null) {
                    Object value = readMethod.invoke(obj);
                    valueStr = value == null ? "" : value.toString();

                    if (value instanceof Date) {
                        DateTimeFormat dateTimeFormat = readMethod.getAnnotation(DateTimeFormat.class);
                        DateFormat df;
                        if (dateTimeFormat == null) {
                            try {
                                Field field = ReflectUtils.getFieldByGetter(modelClass, readMethod.getName());
                                dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
                            } catch (NoSuchFieldException e) {
                            }
                        }
                        if (dateTimeFormat == null) {
                            df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
                        } else {
                            df = new SimpleDateFormat(dateTimeFormat.pattern());
                        }
                        valueStr = df.format(value);
                    }
                }
                sheet.addCell(new Label(j, i + 1, valueStr));
            }
        }

        wwb.write();
        wwb.close();
    }

    @Override
    public List<T> findForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        return baseDAO.getAll();
    }

    @Override
    public int countForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        return 0;
    }

    @Override
    public void deleteAll() throws DataCommitException {
        baseDAO.deleteAll();
    }

    /**
     * 对查询的对象，如果类型是String 加上%value%
     * @param findParams
     * @param cls
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public void addLike(T findParams, Class cls) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String methodName = "";
        Method[] methods = cls.getMethods();
        for(Method method:methods){
            methodName = method.getName();
            if(StringUtils.isNotBlank(methodName)){
                if(methodName.indexOf("get") < 0){
                    continue;
                }
                if(method.getReturnType() != String.class){
                    continue;
                }
                Object value  = method.invoke(findParams,null);
                if(value == null || StringUtils.isBlank(String.valueOf(value))){
                    continue;
                }

                Method setMethod = cls.getMethod(methodName.replace("get","set"),String.class);
                if(setMethod == null){
                    continue;
                }
                setMethod.invoke(findParams,"%"+value+"%");
            }
        }
    }

    public void addLike(JSONObject findParamsMap){
        if(findParamsMap == null)
        {
            return;
        }
        Iterator<String> it = findParamsMap.keySet().iterator();
        String key = "";
        while(it.hasNext()){
            key = it.next();
            if(findParamsMap.get(key) != null){
                Object value = findParamsMap.get(key);
                if("java.lang.String".equals(value.getClass().getName()) &&
                        StringUtils.isNotBlank(findParamsMap.getString(key))){
                    findParamsMap.put(key,"%"+findParamsMap.getString(key)+"%");
                }
            }
        }
    }

    public String getOrgCode(){
        User user  = SessionUtils.getUser();
        if(user == null) return "";
        return user.getOrgCode();
    }

}
