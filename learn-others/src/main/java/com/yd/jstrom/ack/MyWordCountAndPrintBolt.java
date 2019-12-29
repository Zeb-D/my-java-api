package com.yd.jstrom.ack;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by Zeb灬D on 2018/1/31
 * Description:
 */
public class MyWordCountAndPrintBolt extends BaseRichBolt {

    private Map<String, Integer> counters = Maps.newHashMap();

    private OutputCollector collector;

    private boolean flag = true;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {

        String word = input.getStringByField("word");

        if ("been".equals(word) && flag) {
            flag = false;
            this.collector.fail(input);
        } else {
            int num = input.getIntegerByField("num");
            if (counters.containsKey(word)) {
                counters.put(word, counters.get(word) + num);
            } else {
                counters.put(word, 1);
            }
            this.collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public void cleanup() {
        System.err.println("result:");
        for (Map.Entry entry : counters.entrySet()) {
            System.err.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}