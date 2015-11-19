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
	
	/**
	 * Construct a new instance of AES.
	 * NOTE: keySize should be between 128 and 256
	 * @param int keySize
	 * @param String password
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public AES(int keySize, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException{
		this.keySize = keySize;
		this.password = password;
		this.generateSalt();
		this.generateKey();
	}
	
	/**
	 * Generate a SecretKey, this is used to encrypt and decrypt data.
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
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
	
	/**
	 * Encrypts a string.
	 * @param String plainText
	 * @return String
	 * @throws Exception
	 */
	public String encrypt(String plainText) throws Exception { 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new Base64().encodeAsString(encryptedTextBytes);
	}
	
	/**
	 * Decrypts a string.
	 * @param String encryptedText
	 * @return String
	 * @throws Exception
	 */
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
	
	/**
	 * Generates random salt for the SecretKey to be derived from.
	 */
	public void generateSalt(){
		salt = new String(generateSaltMethod());
	}
	
	/**
	 * Generates random byte array for use as salt.
	 * @return byte[]
	 */
	public byte[] generateSaltMethod() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return bytes;
	}
	
	/**
	 * Set the salt the SecretKey is derived from.
	 * @param String salt
	 */
	public void setSalt(String salt){
		this.salt = salt;
	}
	
	/**
	 * Get the salt the SecretKey was derived from.
	 * @return String
	 */
	public String getSalt(){
		return this.salt;
	}
	
	/**
	 * Encrypts a byte array.
	 * @param byte[] plain
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] encryptBytes(byte[] plain) throws Exception { 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plain);
		return encryptedTextBytes;
	}
	
	/**
	 * Decrypts a byte array.
	 * @param byte[] encryptedTextBytes
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * Set the password used to derive the SecretKey.
	 * @param String pass
	 */
	public void setPassword(String pass){
		this.password = pass;
	}
	
	/**
	 * Get the password used to derive the SecretKey.
	 * @return String
	 */
	public String getPassword(){
		return this.password;
	}
	
	/**
	 * Get the SecretKey.
	 * @return SecretKey
	 */
	public SecretKey getKey(){
		return secret;
	}
	
	/**
	 * Set the SecretKey.
	 * @param SecretKey key
	 */
	public void setKey(SecretKey key){
		secret = key;
	}
	
	/**
	 * Saves the SecretKey to a file of your choosing.
	 * @param File f
	 * @throws IOException
	 */
	public void saveKeyToFile(File f) throws IOException{
		ObjectManager.writeObject(f, secret);
	}
	
	/**
	 * Loads a SecretKey from a file of your choosing.
	 * @param File f
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void loadKeyFromFile(File f) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream ois = ObjectManager.readObject(f);
		SecretKey key = null;
		key = (SecretKey) ois.readObject();
		ois.close();
		secret = key;
	}
	
}
