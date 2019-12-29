package com.yd.reactor;

/**
 * Created by Zeb灬D on 2018/3/6
 * Description:
 */
public class Server {

    public static void main(String[] args) {
        Selector selector = new Selector();
        Dispatcher eventLooper = new Dispatcher(selector);
        Acceptor acceptor;
        acceptor = new Acceptor(selector, 11);

        eventLooper.registEventHandler(EventType.ACCEPT, new AcceptEventHandler(selector));
        new Thread(acceptor, "Acceptor-" + acceptor.getPort()).start();
        eventLooper.handleEvents();
    }

}