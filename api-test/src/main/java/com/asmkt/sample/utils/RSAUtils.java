package com.asmkt.sample.utils;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtils {
	private static final char[] HEX_VOCABLE = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";
	
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 53;
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 64;
	/**
	 * 加密
	 */
	public static String encryptByPublicKey(String context,String publicKey){
		
		try {
			byte[] publicKeyBytes = RSAUtils.hexToBytes(publicKey);
			byte[] contextBytes = context.getBytes();
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publick = keyFactory.generatePublic(x509KeySpec);
			//对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publick);
			int inputLen = contextBytes.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			//对数据分段解密
			while(inputLen - offSet > 0 ){
				if(inputLen - offSet > MAX_ENCRYPT_BLOCK){
					cache = cipher.doFinal(contextBytes,offSet,MAX_ENCRYPT_BLOCK);
				}else{
					cache = cipher.doFinal(contextBytes,offSet,inputLen - offSet);
				}
				out.write(cache,0,cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			String encryptedContext = RSAUtils.bytesToHex(encryptedData);
			out.close();
			return encryptedContext;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解密
	 * @param context
	 * @param privateKey
	 * @return
	 */
	public static String decryptByPrivateKey(String context,String privateKey){
	
		try {
			byte[] privateKeyBytes = RSAUtils.hexToBytes(privateKey);
			byte[] contextBytes = RSAUtils.hexToBytes(context);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = contextBytes.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			//对数据分段解密
			while(inputLen - offSet > 0){
				if(inputLen - offSet > MAX_DECRYPT_BLOCK){
					cache = cipher.doFinal(contextBytes,offSet,MAX_DECRYPT_BLOCK);
					System.out.println("111");
				}else{
					cache = cipher.doFinal(contextBytes,offSet,inputLen - offSet);
				}
				out.write(cache,0,cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			String decryptedContext = new String(decryptedData);
			out.close();
			return decryptedContext;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
