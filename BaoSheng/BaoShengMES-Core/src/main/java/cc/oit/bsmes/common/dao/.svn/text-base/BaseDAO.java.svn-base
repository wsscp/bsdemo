package cc.oit.bsmes.common.dao;

import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.mybatis.BaseProvider;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BaseDAO<T extends Base> {

	@SelectProvider(type = BaseProvider.class, method = "getAll")
	@ResultMap("getMap")
	public List<T> getAll();

	@SelectProvider(type = BaseProvider.class, method = "getById")
	@ResultMap("getMap")
	public T getById(String id);

	@SelectProvider(type = BaseProvider.class, method = "count")
	public int count(T findParams);

	@SelectProvider(type = BaseProvider.class, method = "countQuery")
	public int countQuery(@Param("queryParams") List<CustomQueryParam> customQueryParams);

	@SelectProvider(type = BaseProvider.class, method = "get")
	@ResultMap("getMap")
	public T getOne(T findParams);
	
	@SelectProvider(type = BaseProvider.class, method = "query")
	@ResultMap("getMap")
	public List<T> query(@Param("queryParams") List<CustomQueryParam> customQueryParams, @Param("sortList") List<Sort> sortList);

	@SelectProvider(type = BaseProvider.class, method = "get")
	@ResultMap("getMap")
	public List<T> get(T findParams);

	@SelectProvider(type = BaseProvider.class, method = "find")
	@ResultMap("getMap")
	public List<T> find(T findParams);

	@InsertProvider(type = BaseProvider.class, method = "insert")
	@Options(keyProperty = "id")
	public int insert(T t);
	
	@DeleteProvider(type = BaseProvider.class, method = "delete")
	public int delete(String id);

	@UpdateProvider(type = BaseProvider.class, method = "update")
	public int update(T t);

    @DeleteProvider(type = BaseProvider.class,method = "deleteAll")
    public int deleteAll();
}
