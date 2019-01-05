package com.yd.javaWeb.IDGenerator;

/**
 * 利用Db 生成ID
 * 优点：
 * 1）简单，代码方便，性能可以接受。
 * 2）数字ID天然排序，对分页或者需要排序的结果很有帮助。
 * <p>
 * 缺点：
 * 1）不同数据库语法和实现不同，数据库迁移的时候或多数据库版本支持的时候需要处理。
 * 2）在单个数据库或读写分离或一主多从的情况下，只有一个主库可以生成。有单点故障的风险。
 * 3）在性能达不到要求的情况下，比较难于扩展。
 * 4）如果遇见多个系统需要合并或者涉及到数据迁移会相当痛苦。
 * 5）分表分库的时候会有麻烦。
 *
 * @author Yd on  2018-06-26
 * @description <p>优化方案：
 * 1）针对主库单点，如果有多个Master库，则每个Master库设置的起始数字不一样，步长一样，可以是Master的个数。比如：Master1 生成的是 1，4，7，10，Master2生成的是2,5,8,11 Master3生成的是 3,6,9,12。
 * 这样就可以有效生成集群中的唯一ID，也可以大大降低ID生成数据库操作的负载。
 **/
public class SqlId {
    String selectSql = "select nextval as id from dual";
    String selectFuction = "";//调用存储过程等方式
}
