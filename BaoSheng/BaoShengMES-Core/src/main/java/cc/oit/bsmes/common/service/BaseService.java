package cc.oit.bsmes.common.service;

import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface BaseService<T extends Base> {
	
	public List<T> getAll();
	
	public T getById(String id);

	public int count(T findParams);

    public int countQuery(List<CustomQueryParam> customQueryParams);

    public List<T> query(List<CustomQueryParam> customQueryParams);

    public List<T> query(List<CustomQueryParam> customQueryParams, Integer start, Integer limit, List<Sort> sortList);

	public List<T> find(T findParams, Integer start, Integer limit, List<Sort> sortList);
	
	public List<T> findByObj(T findParams);

	public void insert(T t) throws DataCommitException;
	
	public void insert(List<T> list) throws DataCommitException;

	public void deleteById(String id) throws DataCommitException;
	
	public void deleteById(List<String> list) throws DataCommitException;
	
	public void delete(T t) throws DataCommitException;
	
	public void delete(List<T> list) throws DataCommitException;

    public void deleteAll() throws DataCommitException;

	public void update(T t) throws DataCommitException;
	
	public void update(List<T> list) throws DataCommitException;

    public void export(OutputStream outputStream, String sheetName, JSONArray columns,JSONObject queryFilter) throws IOException, WriteException, InvocationTargetException,
            IllegalAccessException,
            NoSuchMethodException;

    public List<T> findForExport(JSONObject jsonParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    public int countForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    
    public List<T> getByObj(T findParams) ;

    public String getOrgCode();
}
