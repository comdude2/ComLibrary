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
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import net.comdude2.plugins.comlibrary.util.ObjectManager;

import org.apache.commons.codec.binary.Base64;
	
public class AES {
	
	private String password = "test";
	private String salt;
	private static final int pswdIterations = 65536 ;
	private int keySize = 256;
	private byte[] ivBytes;
	private SecretKey secret = null;
	
	public AES(int keySize, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException{
		this.keySize = keySize;
		this.password = password;
		this.generateSalt();
		this.generateKey();
	}
	
	public void generateKey() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] saltBytes = salt.getBytes("UTF-8");
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(
				password.toCharArray(), 
				saltBytes, 
				pswdIterations, 
				keySize
				);
	
		SecretKey secretKey = factory.generateSecret(spec);
		secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
	}
	
	public String encrypt(String plainText) throws Exception { 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new Base64().encodeAsString(encryptedTextBytes);
	}
	
	@SuppressWarnings("static-access")
	public String decrypt(String encryptedText) throws Exception {
		byte[] encryptedTextBytes = new Base64().decodeBase64(encryptedText);
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
	
	public void setSalt(String salt){
		this.salt = salt;
	}
	
	public String getSalt(){
		return this.salt;
	}
	
	public byte[] encryptBytes(byte[] plainText) throws Exception { 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText);
		return encryptedTextBytes;
	}
	
	public byte[] decryptBytes(byte[] encryptedTextBytes) throws Exception {
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
	
	public void setPassword(String pass){
		this.password = pass;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void saveKeyToFile(File f) throws IOException{
		ObjectManager.writeObject(f, secret);
	}
	
	public void loadKeyFromFile(File f) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream ois = ObjectManager.readObject(f);
		SecretKey key = null;
		key = (SecretKey) ois.readObject();
		ois.close();
		secret = key;
	}
	
}
