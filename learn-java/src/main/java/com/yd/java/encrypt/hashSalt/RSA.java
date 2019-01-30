package com.yd.java.encrypt.hashSalt;

import javax.swing.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * 这是一个加密解密的
 * 你输入 字符串，然后展示加密密文，再解密回来
 * <pre>
 *     不支持中文，因为解密回来 乱码了
 * </pre>
 * @author Yd on 2019/1/14
 */
public final class RSA {

    /**
     * Trivial test program.
     *
     * @param args
     * methodology
     */
    public static void main(String[] args) {
        RSA rsa = new RSA(1024);
        String text1 = JOptionPane.showInputDialog("Enter a message to encrypt :");

        String ciphertext = rsa.encrypt(text1);
        //加密后字符串
        JOptionPane.showMessageDialog(null, "Your encrypted message : " + ciphertext);
        //解密
        JOptionPane.showMessageDialog(null, "Your message after decrypt : " + rsa.decrypt(ciphertext));
    }

    private BigInteger modulus, privateKey, publicKey;

    /**
     *
     * @param bits
     */
    public RSA(int bits) {
        generateKeys(bits);
    }

    /**
     *
     * @param message
     * @return encrypted message
     */
    public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes(Charset.forName("utf-8")))).modPow(publicKey, modulus).toString();
    }

    /**
     *
     * @param message
     * @return encrypted message as big integer
     */
    public synchronized BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    /**
     *
     * @param encryptedMessage
     * @return plain message
     */
    public synchronized String decrypt(String encryptedMessage) {
        return new String((new BigInteger(encryptedMessage)).modPow(privateKey, modulus).toByteArray(),Charset.forName("utf-8"));
    }

    /**
     *
     * @param encryptedMessage
     * @return plain message as big integer
     */
    public synchronized BigInteger decrypt(BigInteger encryptedMessage) {
        return encryptedMessage.modPow(privateKey, modulus);
    }

    /**
     * Generate a new public and private key set.
     *
     * @param bits
     */
    public synchronized void generateKeys(int bits) {
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bits / 2, 100, r);
        BigInteger q = new BigInteger(bits / 2, 100, r);
        modulus = p.multiply(q);

        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        publicKey = new BigInteger("3");

        while (m.gcd(publicKey).intValue() > 1) {
            publicKey = publicKey.add(new BigInteger("2"));
        }

        privateKey = publicKey.modInverse(m);
    }

}