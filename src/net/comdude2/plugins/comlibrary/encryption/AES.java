/*
ComLibrary - A library plugin for Minecraft
Copyright (C) 2015  comdude2 (Matt Armer)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Contact: admin@mcviral.net
*/

/*	IMPORTANT
 *  Before using this class it would be good practice to get the boolean 'encryptionTestPassed'
 *  from ComLibrary class, this is to check wether the class passed a basic test, if it didn't
 *  then using it might not be a good idea.
*/

package net.comdude2.plugins.comlibrary.encryption;
	
import java.security.AlgorithmParameters;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
	
public class AES {
	
	private static final String password = "test";
	private String salt;
	private static final int pswdIterations = 65536 ;
	private int keySize = 256;
	private byte[] ivBytes;
	private  byte[] globalSaltBytes = null;
	
	public AES(int keySize){
		this.keySize = keySize;
	}
	
	public String encrypt(String plainText) throws Exception { 
		//get salt 
		byte[] saltBytes = salt.getBytes("UTF-8");
		
		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(
			password.toCharArray(), 
			saltBytes, 
			pswdIterations, 
			keySize
			);
		
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		
		//encrypt the message
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new Base64().encodeAsString(encryptedTextBytes);
	}
	
	@SuppressWarnings("static-access")
	public String decrypt(String encryptedText) throws Exception {
		
		byte[] saltBytes = salt.getBytes("UTF-8");
		byte[] encryptedTextBytes = new Base64().decodeBase64(encryptedText);
		
		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(
				password.toCharArray(), 
				saltBytes, 
				pswdIterations, 
				keySize
				);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
	
		// Decrypt the message
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
		byte[] decryptedTextBytes = null;
		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return new String(decryptedTextBytes);
	}
	
	public void generateSalt(){
		salt = new String(generateSaltMethod());
	}
	
	public byte[] generateSaltMethod() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return bytes;
	}
	
	public void generateNewGlobalSaltBytes() throws Exception{
		byte[] saltBytes = generateSaltMethod();
		globalSaltBytes = saltBytes;
	}
	
	public void setSalt(String salt){
		this.salt = salt;
	}
	
	public void setGlobalSaltBytes(byte[] bytes){
		this.globalSaltBytes = bytes;
	}
	
	public String getSalt(){
		return this.salt;
	}
	
	public byte[] getGlobalSaltBytes(){
		return this.globalSaltBytes;
	}
	
	public byte[] encryptBytes(byte[] plainText) throws Exception { 
		
		//get salt
		byte[] saltBytes;
		if (globalSaltBytes == null){
			generateNewGlobalSaltBytes();
			saltBytes = globalSaltBytes;
		}else{
			saltBytes = globalSaltBytes;
		}
		
		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(
				password.toCharArray(), 
				saltBytes, 
				pswdIterations, 
				keySize
				);
	
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
	
		//encrypt the message
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText);
		return encryptedTextBytes;
	}
	
	public byte[] decryptBytes(byte[] encryptedText) throws Exception {
		
		//get salt
		byte[] saltBytes;
		if (globalSaltBytes == null){
			generateNewGlobalSaltBytes();
			saltBytes = globalSaltBytes;
		}else{
			saltBytes = globalSaltBytes;
		}
		//byte[] encryptedTextBytes = new Base64().decodeBase64(encryptedText);
		byte[] encryptedTextBytes = encryptedText;
		
		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(
				password.toCharArray(), 
				saltBytes, 
				pswdIterations, 
				keySize
				);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
	
		// Decrypt the message
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
		byte[] decryptedTextBytes = null;
		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return decryptedTextBytes;
	}
	
}