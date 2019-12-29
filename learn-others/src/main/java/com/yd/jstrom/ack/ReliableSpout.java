package com.yd.jstrom.ack;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.alibaba.jstorm.client.spout.IFailValueSpout;
import com.google.common.base.Strings;

import java.io.*;
import java.util.*;

/**
 * Created by Zeb灬D on 2018/1/31
 * Description:
 */
public class ReliableSpout extends BaseRichSpout implements IFailValueSpout {

    public static final String FILE_PATH = "d:/text.txt";
    private HashMap<String, String> waitAck = new HashMap<>();
    private SpoutOutputCollector collector;
    private BufferedReader bufferedReader;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        try {
            this.bufferedReader = new BufferedReader(new FileReader(new File(FILE_PATH)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextTuple() {
        try {
            String line = bufferedReader.readLine();
            if (!Strings.isNullOrEmpty(line)) {
                List<Object> arrayList = new ArrayList<>();
                arrayList.add(line);
                String messigeId = UUID.randomUUID().toString().replaceAll("-", "");
                waitAck.put(messigeId, line);
                collector.emit(arrayList, messigeId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line"));
    }

    @Override
    public void ack(Object msgId) {
        //super.ack(msgId);
        System.err.println("ReliableSpout处理成功:" + msgId);
        System.err.println("ReliableSpout 删除缓存中的消息");
        waitAck.remove(msgId);
    }

    @Override
    public void fail(Object msgId) {
//        super.fail(msgId);
        System.err.println("ReliableSpout处理失败：" + msgId);
        System.err.println("ReliableSpout重发失败消息");
        collector.emit(new Values(waitAck.get(msgId)), msgId);
    }

    @Override
    public void fail(Object msgId, List<Object> values) {

    }
}