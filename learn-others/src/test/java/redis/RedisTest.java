package redis;

import com.yd.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yd on  2018-03-13
 * @description
 **/
public class RedisTest extends BaseTest {
    @Autowired
    protected RedisTemplate redisTemplate;

    @Test
    public void testAdd() {
        User user = new User();
        user.setId(123);
        user.setName("Yd");

        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize(user.getId().toString());
                byte[] name = serializer.serialize(user.getName());
                return connection.setNX(key, name);
            }
        });

        System.out.println("redis add boolean :" + result);
    }

    @Test
    public void testBatchAdd() {
        List<User> users = new ArrayList<User>();
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                for (User user : users) {
                    byte[] key = serializer.serialize(user.getId().toString());
                    byte[] name = serializer.serialize(user.getName());
                    connection.setNX(key, name);
                }
                return true;
            }
        }, false, true);

    }

    @Test
    public void testDelete(){
        List<String> keys = new ArrayList<String>();
        redisTemplate.delete(keys);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(123);
        user.setName("Yd123");

        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize(user.getId().toString());
                byte[] name = serializer.serialize(user.getName());
                connection.set(key, name);
                return true;
            }
        });
    }

    @Test
    public void testGet(){
        Integer id = new Integer(123);

        User result = (User) redisTemplate.execute(new RedisCallback<User>() {
            public User doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize(id.toString());
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                String name = serializer.deserialize(value);
                return new User(id, name, null);
            }
        });
    }


}
