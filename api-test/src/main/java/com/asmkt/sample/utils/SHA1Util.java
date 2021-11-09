package com.asmkt.sample.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Util {
	/**
	 * 加密算法SHA1
	 */
	public static final String KEY_ALGORITHM = "SHA1";
	
	private static final char[] HEX_VOCABLE = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

	/**
	 * SHA1加密
	 */
	public static String encrypt(String content){
		if(content == null){
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(KEY_ALGORITHM);
			messageDigest.update(content.getBytes());
			return bytesToHex(messageDigest.digest());
		} catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * hex 转化为 byte[]
	 * @param hex
	 * @return
	 */
	public static byte[]  hexToBytes(String hex){
		if(hex==null || hex.equals("")){
			return null;
		}
		hex = hex.toUpperCase();
		int length = hex.length() / 2;
		char[] hexChars = hex.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] =(byte)(charToByte(hexChars[pos])<< 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	
	private static byte charToByte(char c){
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	/**
	 * bytes 转化为 hex
	 * @param bs
	 * @return
	 */
	public static String bytesToHex(byte[] bs){
		StringBuilder sb = new StringBuilder();
		for (byte b : bs) {
			int high = (b>>4) & 0x0f;
			int low = b & 0x0f;
			sb.append(HEX_VOCABLE[high]);
			sb.append(HEX_VOCABLE[low]);
		}
		return sb.toString();
	}
}
