package com.yd.java.jdk.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * channel 相关方法
 *
 * @author Yd on  2018-06-20
 * @description
 **/
public class ChannelTest {


    public static void channelTransferFrom(String fromFileName, String toFileName) throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile(fromFileName, "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile(toFileName, "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();


        toChannel.transferFrom(fromChannel, position, count);
        //是不是发现这个例子和前面那个例子特别相似？除了调用方法的FileChannel对象不一样外，其他的都一样。
        fromChannel.transferTo(position, count, toChannel);

    }

    public static void  socketChannelTest() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("http://148.251.188.73", 80));

        while(! socketChannel.finishConnect() ){
            //wait, or do something else...

            String newData = "New String to write to file..." + System.currentTimeMillis();

            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put(newData.getBytes());

            buf.flip();

            while(buf.hasRemaining()) {
                socketChannel.write(buf);
            }
        }
    }

    //ServerSocketChannel 是一个可以监听新进来的TCP连接的通道, 就像标准IO中的ServerSocket一样。
    // ServerSocketChannel类在 java.nio.channels包中。
    public static void serverSocketChannelTest() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        while(true){
            SocketChannel socketChannel =serverSocketChannel.accept();

            //do something with socketChannel...

            if(socketChannel != null){
                //do something with socketChannel...
            }

        }
    }

    //DatagramChannel是一个能收发UDP包的通道。因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包。
    public static void datagramChannelTest() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        channel.receive(buf);

        String newData = "New String to write to file..." + System.currentTimeMillis();

        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();

        int bytesSent = channel.send(buf, new InetSocketAddress("jenkov.com", 80));

        int bytesRead = channel.read(buf);
        int bytesWritten = channel.write(buf);
    }

    //管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
    public static void pipeTest() throws IOException {
        //通过Pipe.open()方法打开管道。
        Pipe pipe = Pipe.open();
        //要向管道写数据，需要访问sink通道。
        Pipe.SinkChannel sinkChannel = pipe.sink();

        //通过调用SinkChannel的write()方法，将数据写入SinkChannel
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            sinkChannel.write(buf);
        }

        //从读取管道的数据，需要访问source通道
        Pipe.SourceChannel sourceChannel = pipe.source();
        //调用source通道的read()方法来读取数据
        int bytesRead = sourceChannel.read(buf);//read()方法返回的int值会告诉我们多少字节被读进了缓冲区。

    }

    public static void main(String[] args) throws IOException {
        socketChannelTest();
    }
}
