package redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisSimpleTest {
  
    private Jedis jedis;
      
    /** 
     * 初始化连接 
     * <br>------------------------------<br> 
     */  
    @Before
    public void beforeClass() {  
        jedis = new Jedis("192.168.1.58",8092);
//        jedis.auth("");
        System.out.println(jedis);
        String abc = jedis.ping();//要打印出pong
        System.out.println(abc);
    }

      
    /** 
     * set 新增 
     * <br>------------------------------<br> 
     */  
    @Test
    public void testSet() {  
        jedis.set("blog", "java2000_wl");  
    }

    @Test
    public void testSetId() {
        jedis.set("id", "0");
    }
    @Test
    public void testGetId() {
        System.out.println(jedis.incr("id"));
    }

    /** 
     *  获取 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testGet() {  
        System.out.println(jedis.get("blog"));  
    }  
      
    /** 
     * 修改key 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testRenameKey() {  
        jedis.rename("blog", "blog_new");  
    }  
      
    /** 
     * 按key删除 
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testDel() {  
        jedis.del("blog_new");  
    }  
      
    /** 
     * 获取所有的key  
     * <br>------------------------------<br> 
     */  
    @Test  
    public void testKeys() {  
        System.out.println(jedis.keys("*"));  
    }

    @After
    public void destroy(){
        jedis.close();
    }
}  