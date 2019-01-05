package com.yd.java.jdk.nio;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。
 *
 * @author Yd on  2018-06-20
 * @description
 * IO                NIO
 * 面向流            面向缓冲
 * 阻塞IO            非阻塞IO
 * 无                选择器
 * 无论您选择IO或NIO工具箱，可能会影响您应用程序设计的以下几个方面：
 * 对NIO或IO类的API调用。
 * 数据处理。
 * 用来处理数据的线程数。
 **/
public class SelectorTest {

    //Selector 创建
    public static Selector selectorCreated() throws Exception {
        return Selector.open();
    }

    //与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。
    //注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件：
    //Connect Accept Read Write
    public static SelectionKey channelRegisterSelector(SelectableChannel channel, Selector selector) throws IOException {
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        return key;
    }

    public static int selectInterest(SelectionKey selectionKey) {
        int interestSet = selectionKey.interestOps();
        boolean isInterestedInAccept = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
        boolean isInterestedInConnect = (interestSet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
        System.out.println(interestSet);
        System.out.println(isInterestedInAccept);
        System.out.print(isInterestedInConnect);
        selectionKey.isAcceptable();
        selectionKey.isConnectable();
        selectionKey.attach("Name is Yd");
        Channel channel = selectionKey.channel();
        Selector selector = selectionKey.selector();

        return interestSet;
    }

    public static void selector(SelectableChannel channel) throws IOException {
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) continue;
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = (SelectionKey) keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        System.out.println(SelectionKey.OP_READ);
        System.out.println(SelectionKey.OP_WRITE);
        System.out.println(interestSet);
        Selector selection = SelectorProvider.provider().openSelector();
    }
}
