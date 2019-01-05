package com.yd.javaWeb.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Buffer、Channel、selector
 *
 * @author Yd on  2018-05-15
 * @description
 **/
public class NIOTest {


    //服务端处理方式
    public void selector() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);//设置非阻塞方式
            ssc.socket().bind(new InetSocketAddress(8080));
            ssc.register(selector, SelectionKey.OP_ACCEPT);//注册监听事件
            while (true) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();//取得所有key集合
                Iterator<SelectionKey> it = selectedKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssChannel.accept();//接受到服务端的请求
                        sc.configureBlocking(false);
                        sc.register(selector,SelectionKey.OP_READ);
                        it.remove();
                    }else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
                        SocketChannel sc = (SocketChannel) key.channel();
                        while (true){
                            buffer.clear();
                            int n =sc.read(buffer);
                            if (n<=0){
                                break;
                            }
                            buffer.flip();
                        }
                        it.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(){
        try{
            SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));
            sc.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(new  byte[1024]);
            while (buffer.hasRemaining()){
                sc.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
