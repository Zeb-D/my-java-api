package com.yd.java.encrypt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

	private final static String IVKEY = "9greqde7banhkxfv";
	
	public static byte[] encrypt(String data, String key) {
		try {
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			int blockSize = cipher.getBlockSize();
			SecretKeySpec keyspec = new SecretKeySpec(fullZore(key, blockSize), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(fullZore(IVKEY, blockSize));
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(data.getBytes("utf8"));
			return encrypted;
		} catch (Exception e) {
		    logger.warn(e.getMessage());
			return null;
		}
	}
	
	public static byte[] encrypt(byte[] bt, String key) {
		try {
			String data = new String(bt, "utf8");
			return encrypt(data, key);
		} catch (Exception e) {
		    logger.warn(e.getMessage());
			return null;
		}
	}
	
	public static String encryptHex(String data, String key) {
		try {
			byte[] bt = encrypt(data, key);
			return parseByte2HexStr(bt);
		} catch (Exception e) {
		    logger.warn(e.getMessage());
			return null;
		}
	}

	public static byte[] decrypt(byte[] data, String key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			int blockSize = cipher.getBlockSize();
			SecretKeySpec keyspec = new SecretKeySpec(fullZore(key, blockSize), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(fullZore(IVKEY, blockSize));
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] decrypted = cipher.doFinal(data);
			return decrypted;
		} catch (Exception e) {
		    logger.warn(e.getMessage());
			return null;
		}
	}
	
	public static String decryptByHex(String hexStr, String key) {
		try {
			byte[] bt = decrypt(parseHexStr2Byte(hexStr), key);
			return new String(bt, "utf8");
		} catch (Exception e) {
		    logger.warn(e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	
	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	

	public static byte[] fullZore(String data, int blockSize) throws Exception {
		byte[] dataBytes = data.getBytes("utf8");
		int plaintextLength = dataBytes.length;
		if (plaintextLength % blockSize != 0) {
			plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));

		}
		byte[] plaintext = new byte[plaintextLength];
		System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
		return plaintext;
	}

}
