package com.yd.javaWeb.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author Yd on  2018-05-17
 * @description
 **/
public class BufferTest {
    // Invariants: mark <= position <= limit <= capacity
    Buffer buffer = ByteBuffer.allocate(1024);
}
