package redis;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-camel-ftp2jms.xml"})
@TransactionConfiguration(defaultRollback=false)//spring-test 会自动回滚
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

}
