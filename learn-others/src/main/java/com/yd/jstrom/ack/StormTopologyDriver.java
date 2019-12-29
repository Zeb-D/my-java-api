package com.yd.jstrom.ack;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

/**
 * Created by Zeb灬D on 2018/1/31
 * Description:
 */
public class StormTopologyDriver {
    public static void main(String[] args) throws InterruptedException {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("mySpout", new ReliableSpout(), 1);
        topologyBuilder.setBolt("bolt1", new MySplitBolt(), 1).shuffleGrouping("mySpout");
        topologyBuilder.setBolt("bolt2", new MyWordCountAndPrintBolt(), 1).shuffleGrouping("bolt1");

        Config config = new Config();
        config.setNumWorkers(2);
        StormTopology stormTopology = topologyBuilder.createTopology();

        config.setDebug(false);

        config.setNumAckers(2);
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("wordcount", config, stormTopology);

        Thread.sleep(5000);
        localCluster.shutdown();
    }
}