package com.yd.jstrom;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

/**
 * Created by Zeb灬D on 2017/10/5.
 */
public class WordCountTopologyMain {
    public static void main(String[] args) throws InterruptedException {
        //定义一个Topology
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReader());

        builder.setBolt("word-normalizer", new WordNormalizer(), 1)
                .shuffleGrouping("word-reader");

        builder.setBolt("word-counter", new WordCounter(), 1)
                .fieldsGrouping("word-normalizer", new Fields("word"));
        //配置
        Config conf = new Config();
        conf.put("wordsFile", "application.properties");
        //conf.setDebug(true);
        //提交Topology
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        //创建一个本地模式cluster
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Toplogie", conf, builder.createTopology());
        Thread.sleep(10000);
        cluster.shutdown();
    }
}