package com.yd.java.jdk.io;

import com.yd.java.jdk.Constant;

import java.io.*;

/**
 * Reader和Writer除了基于字符之外，其他方面都与InputStream和OutputStream非常类似。他们被用于读写文本。
 *
 * @author Yd on 2018-06-22
 * 比如，你有一个程序需要处理磁盘上的大量文件，这个任务可以通过并发操作提高性能。
 * 又比如，你有一个web服务器或者聊天服务器，接收许多连接和请求，这些任务都可以通过并发获得性能的提升。
 * <p>
 * <p>
 * 如果你需要并发处理IO，这里有几个问题可能需要注意一下：
 * <p>
 * 在同一时刻不能有多个线程同时从InputStream或者Reader中读取数据，也不能同时往OutputStream或者Writer里写数据。
 * 你没有办法保证每个线程读取多少数据，以及多个线程写数据时的顺序。
 * <p>
 * 如果线程之间能够保证操作的顺序，它们可以使用同一个stream、reader、writer。
 * 比如，你有一个线程判断当前的输入流来自哪种类型的请求，然后将流数据传递给其他合适的线程做后续处理。当有序存取流、reader、writer时，这种做法是可行的。
 * 请注意，在线程之间传递流数据的代码应当是同步的。
 */
public class CharStream {

    //Reader的read()方法返回一个字符，意味着这个返回值的范围在0到65535之间(当达到流末尾时，同样返回-1)。
    // 这并不意味着Reade只会从数据源中一次读取2个字节，Reader会根据文本的编码，一次读取一个或者多个字节。
    public static void reader() throws IOException {
        Reader reader = new FileReader(Constant.FILENAME);
        int data = reader.read();
        while (data != -1) {
            char dataChar = (char) data;
            data = reader.read();
        }
    }

    public static void writer() throws IOException {
        Writer writer = new FileWriter(Constant.FILENAME);
        writer.write("Hello World Writer");
        writer.close();
        Writer writer1 = new OutputStreamWriter(new FileOutputStream(Constant.FILENAME));
    }

    //整合Reader与InputStream
    public static void composit() throws FileNotFoundException {
        Reader reader = new InputStreamReader(new FileInputStream(Constant.FILENAME));

    }

    //InputStreamReader和OutputStreamWriter
    //两个类把字节流转换成字符流，中间做了数据的转换，类似适配器模式的思想。
    public static void StreamReader() throws IOException {
        InputStream inputStream = new FileInputStream(Constant.FILENAME);
        InputStreamReader reader = new InputStreamReader(inputStream);
        int data = reader.read();//read()方法返回一个包含了读取到的字符内容的int类型变量(译者注：0~65535)。
        while (data != -1) {
            char theChar = (char) data;//这里不会造成数据丢失，因为返回的int类型变量data只有低16位有数据，高16位没有数据
            data = reader.read();
        }
        reader.close();

        //第二个参数，此时该InputStreamReader会将输入的字节流转换成UTF8字符流。
        reader = new InputStreamReader(inputStream, "UTF-8");

        OutputStream outputStream = new FileOutputStream(Constant.FILENAME);
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write("Hello World");//将该输出字节流转换成字符流
        writer.close();

    }

    //如果你想明确指定一种编码方案，利用InputStreamReader配合FileInputStream来替代FileReader(译者注：FileReader没有可以指定编码的构造函数)。
    // InputStreamReader可以让你设置编码处理从底层文件中读取的字节。
    //同样，FileWriter不能指定编码，可以通过OutputStreamWriter配合FileOutputStream替代FileWriter。
    public static void FileReaderWriter() throws IOException {
        Reader reader = new FileReader(Constant.FILENAME);
        int data = reader.read();
        while (data != -1) {
            //do something with data...
            data = reader.read();
        }
        reader.close();

        Writer writer = new FileWriter(Constant.FILENAME, true); //appends to file
    }

    //管道与字符数组相关的reader和writer，主要涉及PipedReader、PipedWriter、CharArrayReader、CharArrayWriter。
    //PipedReader能够从管道中读取字符流。与PipedInputStream类似，不同的是PipedReader读取的是字符而非字节。换句话说，PipedReader用于读取管道中的文本。
    public static void pipe() throws IOException {
        PipedWriter pipedWriter = new PipedWriter();
        Reader reader = new PipedReader(pipedWriter);
        int data = reader.read();
        while (data != -1) {
            //do something with data...
            data = reader.read();
        }
        reader.close();

        char[] chars = "my name is Yd".toCharArray(); //get char array from somewhere.
        CharArrayReader charArrayReader = new CharArrayReader(chars);
        int read = charArrayReader.read();
        while (read != -1) {
            //do something with data
            data = charArrayReader.read();
            System.out.println(data);
        }
        charArrayReader.close();
    }

    //PushbackInputStream，SequenceInputStream和PrintStream。
    public static void otherCharStream() throws IOException {
        PushbackInputStream input = new PushbackInputStream(new FileInputStream(Constant.TEMP));
        int data = input.read();
        System.out.println((char) data);//此时流往后读流
        //把读取到的字节重新推回到InputStream中，以便再次通过read()读取。
        input.unread(data);//但此时将data重新设置回去
        System.out.println((char) data + "-->" + (char) input.read());
        input = new PushbackInputStream(new FileInputStream(Constant.TEMP), 8);//设置推回缓冲区的大小

        //当读取SequenceInputStream时，会先从第一个输入流中读取，完成之后再从第二个输入流读取，以此推类。
        //read()方法会在读取到当前流末尾时，关闭流，并把当前流指向逻辑链中的下一个流，最后返回新的当前流的read()值
        InputStream input1 = new FileInputStream(Constant.FILENAME);
        InputStream input2 = new FileInputStream(Constant.TEMP);
        InputStream combined = new SequenceInputStream(input1, input2);
        System.out.println((char) combined.read());

        PrintStream output = new PrintStream(new FileOutputStream(Constant.TEMP));
        output.print(true);
        output.print((int) 123);
        output.print((float) 123.456);
        output.println();//换行
        output.printf("Text + data: %s", 123);//格式化输出
        output.close();


        //默认情况下，行号从0开始，当LineNumberReader读取到行终止符时，行号会递增(译者注：换行\n，回车\r，或者换行回车\n\r都是行终止符)。
        //LineNumberReader是记录了已读取数据行号的BufferedReader
        LineNumberReader reader = new LineNumberReader(new FileReader(Constant.TEMP));
        data = reader.read();
        while (data != -1) {
            char dataChar = (char) data;
            System.out.print(dataChar);
            data = reader.read();
            int lineNumber = reader.getLineNumber();//获取当前行号
//            System.out.println("lineNumber"+lineNumber);
            //setLineNumber()仅仅改变LineNumberReader内的记录行号的变量值，不会改变当前流的读取位置。
        }
    }

    //StreamTokenizer(译者注：请注意不是StringTokenizer)可以把输入流(译者注：InputStream和Reader。
    // 通过InputStream构造StreamTokenizer的构造函数
    //通过循环调用nextToken()可以遍历底层输入流的所有符号。在每次调用nextToken()之后，StreamTokenizer有一些变量可以帮助我们获取读取到的符号的类型和值。
    public static void StreamTokenizer() throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader("Mary had 1 little lamb..."));
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {//TT_EOF表示流末尾，TT_EOL表示行末尾。
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                System.out.println(tokenizer.sval + "-->" + tokenizer);
            } else if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                System.out.println(tokenizer.nval);
            } else if (tokenizer.ttype == StreamTokenizer.TT_EOL) {
                System.out.println();
            }
            //ttype 读取到的符号的类型(字符，数字，或者行结尾符)
            //sval 如果读取到的符号是字符串类型，该变量的值就是读取到的字符串的值
            //nval 如果读取到的符号是数字类型，该变量的值就是读取到的数字的值
        }
    }

    public static void main(String[] args) throws IOException {
        otherCharStream();
        StreamTokenizer();
    }
}
