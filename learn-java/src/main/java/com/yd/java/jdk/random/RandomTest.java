package com.yd.java.jdk.random;

import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * https://juejin.im/post/5c510433e51d4547254c7b44
 * @author Yd on 2019-01-31
 * @description
 */
public class RandomTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //该类的实例用于生成 伪随机数。该类使用48位种子，使用线性同余公式进行修改。
        //如果使用相同的种子创建两个Random实例，并且对每个实例都进行相同的方法调用，则它们将生成相同的随机数字。
        new Random().nextInt();

        org.apache.commons.lang3.RandomUtils.nextInt(1, 100);

        ThreadLocalRandom.current().nextInt();

        java.security.SecureRandom.getInstance("SHA1PRNG").generateSeed(12);
    }
}
