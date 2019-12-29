## api-common

这里存放通用api操作，其中攘括了:

- 通用Util，如二位码生成、中文转拼音、spring-bean方式格式化输出；
- 除了Util外，tfidf为 中文分词 个人实现，可以说仿照Lucense 思想实现，可能在性能上有所欠缺；
- dbAccess 是自己 采用ETL 方式进行不同数据库DB 进行表迁移操作，目前支持Oracle Mysql 之间相迁移；
- httpClient 是个人基于HttpURLConnection 实现http 请求；
- Json  主要仿照 开源JSON；
- 当然common也会存放一些其他模块使用的JavaBean、annotation等；

## api-gateway

这功能貌似比较新颖，采用servlet+spring 方式进行 将 Spring-Bean 进行Http 请求方法级别，只是Http版的RPC;

将spring bean 通过http 暴露出来



## learn-java

这里学习的东西可多了，比如java版算法、并发编程 书学习、加密与解密、java8新特性、javaWeb、及jdk源码学习位运算、集合、泛化、instrument（统计与计算）、io、net 网络编程、nio、proxy、reflect等操作；

属于个人堆积经验的地方:

包括java方向:

- algorithm
- concurrency
- encrypt
- generics
- guava
- interview
- java8
- jdk
- jmh
- observer
- rpc
- jvm监控

javaWeb方向:

- IDGenerator
- io
- nio

disruptor：

- Publisher
- EventHandler



## learn-others

基于各种框架方面学习，现在以各种框架名称为目录

目前有：

- com.yd.akka
- com.yd.camel
- com.yd.feign
- com.yd.groovy
- com.yd.guava.cache
- com.yd.javassist
- com.yd.mq.activemq
- com.yd.poi
- com.yd.spring
- com.yd.springmvc
- com.yd.jstrom
- com.yd.lua
- com.yd.metrics
- com.yd.objectPool
- com.yd.reactor
- com.yd.zk



