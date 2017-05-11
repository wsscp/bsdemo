package cc.oit.bsmes.redis;

import cc.oit.bsmes.common.model.Base;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIN on 2015/5/27.
 */
@Service
public class RedisServiceImpl<K,V,T extends Base> implements RedisService<K,V,T> {

    @Resource
    private RedisTemplate<K,V> redisTemplate;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public long add(final String key, final T value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = getSerializer();
                    byte[] keys = serializer.serialize(key);
                    byte[] values = serializer.serialize(gson.toJson(value));
                    return connection.rPush(keys, values);
                }
        });
    }


    @Override
    public long add(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                byte[] keys = serializer.serialize(key);
                byte[] values = serializer.serialize(value);
                return connection.rPush(keys, values);
            }
        });
    }

    @Override
    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                byte[] keys = serializer.serialize(key);
                byte[] result = connection.get(keys);
                if(result == null){
                    return "";
                }
                connection.close();
                return serializer.deserialize(result);
            }
        });
    }

    @Override
    public T get(final String key, final Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                byte[] keys = serializer.serialize(key);
                byte[] result = redisConnection.get(keys);
                if(result == null){
                    return null;
                }
                return gson.fromJson(serializer.deserialize(result),clazz);
            }
        });
    }

    @Override
    public List<T> lRange(final String key, final Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                byte[] keys = serializer.serialize(key);
                List<byte[]> list = redisConnection.lRange(keys,0,-1);
                List<T> result = new ArrayList<T>();
                for (byte[] bytes : list) {
                    result.add(gson.fromJson(serializer.deserialize(bytes),clazz));
                }
                return result;
            }
        });
    }

    @Override
    public T lRange(final String key, final Class<T> clazz, final long start) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                byte[] keys = serializer.serialize(key);
                List<byte[]> list = redisConnection.lRange(keys,start,1);
                if(list != null && list.size() > 0){
                    return gson.fromJson(serializer.deserialize(list.get(0)),clazz);
                }else{
                    return null;
                }
            }
        });
    }

    @Override
    public Long remove(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                return connection.del(serializer.serialize(key));
            }
        });
    }

    @Override
    public Long remove(final String[] keyArr) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                long result = 0;
                RedisSerializer<String> serializer = getSerializer();
                for (String key : keyArr) {
                    result+=redisConnection.del(serializer.serialize(key));
                }
                return result;
            }
        });
    }

    @Override
    public long addList(final String key, final T value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                byte[] keys = serializer.serialize(key);
                byte[] result = serializer.serialize(gson.toJson(value));
                return redisConnection.rPush(keys, result);
            }
        });
    }

    @Override
    public long addList(final String key, final List<T> list) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                byte[][] array = new byte[list.size()][];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = serializer.serialize(gson.toJson(list.get(i)));
                }
                return redisConnection.rPush(serializer.serialize(key), array);
            }
        });

    }

    @Override
    public Long lLen(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getSerializer();
                byte[] keys = serializer.serialize(key);
                return redisConnection.lLen(keys);
            }
        });
    }

    private RedisSerializer<String> getSerializer(){
        return redisTemplate.getStringSerializer();
    }
}
