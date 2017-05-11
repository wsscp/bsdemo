package cc.oit.bsmes.redis;

import cc.oit.bsmes.common.model.Base;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JIN on 2015/5/27.
 */
public interface RedisService<K,V,T extends Base> {

    /**
     * 缓存数据
     * @param key
     * @param value
     * @return
     */
    public long add(final String key, final T value);

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public long add(final String key, final String value);

    /**
     * 查询数据
     * @param key
     * @return
     */
    public String get(final String key);

    /**
     * 查询一个对象集
     * @param key
     * @param clazz
     * @return
     */
    public List<T> lRange(final String key,final Class<T> clazz);

    /**
     * 从集合中查询一个对象出来
     * @param key
     * @param clazz
     * @param start
     * @return
     */
    public T lRange(final String key,final Class<T> clazz,long start);

    /**
     * 查询一个对象
     * @param key
     * @param clazz
     * @return
     */
    public T get(final String key,final Class<T> clazz);

    /**
     * 删除缓存数据
     * @param key
     * @return
     */
    public Long remove(final String key);

    /**
     * 批量删除缓存数据
     * @param keyArr
     * @return
     */
    public Long remove(final String[] keyArr);

    public long addList(String key, T value);

    public long addList(String key, List<T> list);

    public Long lLen(String key);
}
