package com.yd.java.jdk.io;

import com.yd.java.jdk.Constant;

import java.io.*;

/**
 * Java IO流是既可以从中读取，也可以写入到其中的数据流。流通常会与数据源、数据流向目的地相关联，比如文件、网络等等。
 * 流和数组不一样，不能通过索引读写数据。在流中，你也不能像数组那样前后移动读取数据，除非使用RandomAccessFile 处理文件。流仅仅只是一个连续的数据流。
 * <p>
 * <p>
 * 某些类似PushbackInputStream 流的实现允许你将数据重新推回到流中，以便重新读取。
 * 然而你只能把有限的数据推回流中，并且你不能像操作数组那样随意读取数据。流中的数据只能够顺序访问。
 * <p>
 * Java IO流通常是基于字节或者基于字符的。字节流通常以“stream”命名，比如InputStream和OutputStream。
 * 除了DataInputStream 和DataOutputStream 还能够读写int, long, float和double类型的值以外，其他流在一个操作时间内只能读取或者写入一个原始字节。
 *
 * @author Yd on 2018-06-22
 */
public class ByteStream {

    public static void main(String[] args) {

    }

    /**
     * Java IO中的管道为运行在同一个JVM中的两个线程提供了通信的能力。所以管道也可以作为数据源以及目标媒介。
     * 你不能利用管道与不同的JVM中的线程通信(不同的进程)。在概念上，Java的管道不同于Unix/Linux系统中的管道。
     * 在Unix/Linux中，运行在不同地址空间的两个进程可以通过管道通信。在Java中，通信的双方应该是运行在同一进程中的不同线程。
     * 一个PipedInputStream流应该和一个PipedOutputStream流相关联。
     * 一个线程通过PipedOutputStream写入的数据可以被另一个线程通过相关联的PipedInputStream读取出来。
     *
     * @throws IOException
     */
    public static void pipe() {
        //当使用两个相关联的管道流时，务必将它们分配给不同的线程。
        // read()方法和write()方法调用时会导致流阻塞，这意味着如果你尝试在一个线程中同时进行读和写，可能会导致线程死锁。
        try (
                PipedOutputStream pipedOutputStream = new PipedOutputStream();
                PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream)
        ) {
            new Thread(() -> {
                try {
                    pipedOutputStream.write("hello pipe!".getBytes());
                } catch (IOException e) {

                }
            }).start();
            new Thread(() -> {
                try {
                    int data = pipedInputStream.read();
                    while (data != -1) {
                        System.out.println((char) data);
                        data = pipedInputStream.read();
                    }
                } catch (IOException e) {
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void input() throws IOException {
        InputStream input = new FileInputStream(Constant.FILENAME);
        //read()方法返回一个整数，代表了读取到的字节的内容(译者注：0 ~ 255)。当达到流末尾没有更多数据可以读取的时候，read()方法返回-1。
        int data = input.read();
        while (data != -1) {
            data = input.read();
        }
    }

    public static void output() throws IOException {
        OutputStream output = new FileOutputStream(Constant.FILENAME);
        output.write("Hello World".getBytes());
        output.close();
    }

    //你可以将流整合起来以便实现更高级的输入和输出操作。
    // 比如，一次读取一个字节是很慢的，所以可以从磁盘中一次读取一大块数据，然后从读到的数据块中获取字节。
    // 为了实现缓冲，可以把InputStream包装到BufferedInputStream中。
    //缓冲只是通过流整合实现的其中一个效果。你可以把InputStream包装到PushbackInputStream中，之后可以将读取过的数据推回到流中重新读取，在解析过程中有时候这样做很方便。
    // 或者，你可以将两个InputStream整合成一个SequenceInputStream。
    public static void composit() throws FileNotFoundException {//组合流
        InputStream input = new BufferedInputStream(new FileInputStream(Constant.FILENAME));
    }

    public static void inputstream() throws IOException {
        try (InputStream inputstream = new FileInputStream(Constant.FILENAME)) {
            int data = inputstream.read();//InputStream的子类可能会包含read()方法的替代方法。比如，DataInputStream允许你利用readBoolean()，readDouble()等方法读取Java基本类型变量int，long，float，double和boolean。
            while (data != -1) {
                System.out.print((char) data);
                data = inputstream.read();
            }

        }
    }

    //往FileOutputStream里写数据的时候，这些数据有可能会缓存在内存中。
    //在之后的某个时间，比如，每次都只有X份数据可写，或者FileOutputStream关闭的时候，才会真正地写入磁盘。
    // 当FileOutputStream没被关闭，而你又想确保写入到FileOutputStream中的数据写入到磁盘中，
    // 可以调用flush()方法，该方法可以保证所有写入到FileOutputStream的数据全部写入到磁盘中。
    public static void outputStream() throws IOException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(Constant.FILENAME, true);//appends to file
//            while (hasMoreData()) {
//                int data = getMoreData();
            output.write("".getBytes());
            output.flush();
//            }
        } finally {
            if (output != null) {
                output.close();
            }

        }
    }

    //字节数组与过滤器的输入输出流 ByteArrayInputStream，ByteArrayOutputStream，FilterInputStream，FilterOutputStream。
    public static void ByteArryFilter() throws FileNotFoundException {
        byte[] bytes ="".getBytes(); //get byte array from somewhere.
        InputStream input = new ByteArrayInputStream(bytes);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(bytes,0,bytes.length);
        //write data to output stream
        bytes = output.toByteArray();

        //FilterInputStream是实现自定义过滤输入流的基类，基本上它仅仅只是覆盖了InputStream中的所有方法。
        //就我自己而言，我没发现这个类明显的用途。除了构造函数取一个InputStream变量作为参数之外，我没看到FilterInputStream任何对InputStream新增或者修改的地方。如果你选择继承FilterInputStream实现自定义的类，同样也可以直接继承自InputStream从而避免额外的类层级结构。
        //FilterInputStream filterInputStream = new FilterInputStream(new FileInputStream(Constant.FILENAME));
    }

    //Buffered和data的输入输出流，BufferedInputStream，BufferedOutputStream，DataInputStream，DataOutputStream
    public static void BufferedData() throws IOException {
        //可以为流提供缓冲区
        //你可以给BufferedInputStream的构造函数传递一个值，设置内部使用的缓冲区设置大小(译者注：默认缓冲区大小8 * 1024B)
        InputStream inputStream = new BufferedInputStream(new FileInputStream(Constant.FILENAME), 8 * 1024);

        //DataInputStream可以使你从输入流中读取Java基本类型数据，而不必每次读取字节数据。你可以把InputStream包装到DataInputStream中，然后就可以从此输入流中读取基本类型数据了
        DataInputStream input = new DataInputStream(new FileInputStream("binary.data"));
        int aByte = input.read();
        int anInt = input.readInt();
        float aFloat = input.readFloat();
        double aDouble = input.readDouble();//etc.
        input.close();

        //可以往输出流中写入Java基本类型数据
        DataOutputStream output = new DataOutputStream(new FileOutputStream("binary.data"));
        output.write(45);
        //byte data output.writeInt(4545);
        //int data output.writeDouble(109.123);
        //double data  output.close();
    }

    //ObjectOutputStream能够让你把对象写入到输出流中，而不需要每次写入一个字节。
    // 你可以把OutputStream包装到ObjectOutputStream中，然后就可以把对象写入到该输出流中了。
    //在你序列化和反序列化一个对象之前，该对象的类必须实现了java.io.Serializable接口。
    public static void ObjectStream() throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(Constant.FILENAME));
        output.writeObject(new Constant()); //etc.
        output.close();
    }
}
