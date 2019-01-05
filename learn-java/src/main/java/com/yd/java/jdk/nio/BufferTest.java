package com.yd.java.jdk.nio;

import com.yd.java.jdk.Constant;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用Buffer读写数据一般遵循以下四个步骤：
 * 写入数据到Buffer
 * 调用flip()方法
 * 从Buffer中读取数据
 * 调用clear()方法或者compact()方法
 * 当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。
 * 在读模式下，可以读取之前写入到buffer的所有数据。
 *
 * @author Yd on  2018-06-20
 * @description <p>Buffer 三个重要的属性：
 * capacity、作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。
 * position、当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
 * 当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0. 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
 * limit、在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
 * 当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）
 * position和limit的含义取决于Buffer处在读模式还是写模式。不管Buffer处在什么模式，capacity的含义总是一样的。
 **/
public class BufferTest {
    public static void main(String[] args) throws Exception {
        fileChannelTest(Constant.FILENAME, "rw");
        ConvertFile2FileChannelMap(Constant.FILENAME);
    }

    /**
     * filechannel 读取到Buffer中，通过四个步骤进行读写操作
     *
     * @param fileName
     * @param mode
     * @throws Exception
     */
    public static void fileChannelTest(String fileName, String mode) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, mode);
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int byteLength = fileChannel.read(buffer);
//        buffer.put((byte) 125);
        while (byteLength != -1) {
            buffer.flip();//从写模式切换到读模式
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
            System.out.println();
//            如果调用的是clear()方法，position将被设回0，limit被设置成 capacity的值。换句话说，Buffer 被清空了。Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。
//            如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。
//            如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用compact()方法。
//            compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。
            buffer.clear();
//            buffer.compact();
            byteLength = fileChannel.read(buffer);
        }
        randomAccessFile.close();
    }

    /**
     * 将带有目录的文件 转化成内存区域，适用于只读，大型文件， 如大文件的md5校验
     *
     * @param fileName
     */
    public static MappedByteBuffer[] ConvertFile2FileChannelMap(String fileName) {
        int BUFFER_SIZE = 1024;
        long fileLength = new File(fileName).length();
        int bufferCount = 1 + (int) (fileLength / BUFFER_SIZE);
        MappedByteBuffer[] mappedByteBuffers = new MappedByteBuffer[bufferCount];
        long remaining = fileLength;
        for (int i = 0; i < bufferCount; i++) {
            RandomAccessFile file;
            try {
                file = new RandomAccessFile(fileName, "r");
                mappedByteBuffers[i] = file.getChannel().map(FileChannel.MapMode.READ_WRITE, i * BUFFER_SIZE,
                        Math.min(remaining, BUFFER_SIZE));
                System.out.println(mappedByteBuffers);
            } catch (Exception e) {
                e.printStackTrace();
            }
            remaining -= BUFFER_SIZE;
        }

        return mappedByteBuffers;
    }
}
