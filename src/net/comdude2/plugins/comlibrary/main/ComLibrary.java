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

package net.comdude2.plugins.comlibrary.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.comdude2.plugins.comlibrary.database.DatabaseConnector;
import net.comdude2.plugins.comlibrary.encryption.AES;

import org.bukkit.plugin.java.JavaPlugin;

public class ComLibrary extends JavaPlugin{
	
	private static boolean encryptionTestPassed = false;
	private static int keySize = 256;
	public static ComLibrary inst = null;
	
	public ComLibrary(){
		inst = this;
	}
	
	/**
	 * Returns this instance of ComLibrary, will be null if this plugin isn't loaded.
	 * @return ComLibrary
	 */
	public static ComLibrary getInstance(){
		return inst;
	}
	
	/*
	 * NOTE
	 * 
	 * It is highly recommended that you use the keySize variable in this class to set your keySize in AES
	 * As when this library loads it ensures that it's usable, be sure to make sure the encryption test passed too.
	*/
	
	@Override
	public void onEnable(){
		
		//Load database driver
		try{DatabaseConnector.loadJdbcDriver();this.getLogger().info("Loaded MySQL driver.");}catch(Exception e){this.getLogger().warning("Failed to load MySQL driver!");}
		
		File path = new File("");
		File f = new File(path.getAbsolutePath() + "ComLibrary_Licence.txt");
		if (!f.exists()){
			try{
				exportResource("/LICENSE.txt", f);
			}catch(Exception e){
				e.printStackTrace();
				this.getServer().getPluginManager().disablePlugin(this);
			}
		}
		this.getLogger().info("Performing encryption test...");
		try{
			AES aes = new AES(keySize, "encryptionTest");
			String plain = "Pie is nice";
			String encrypted = aes.encrypt(plain);
			String decrypted = aes.decrypt(encrypted);
			if (decrypted.equals(plain)){
				this.getLogger().info("Encryption test passed.");
				encryptionTestPassed = true;
			}else{
				this.getLogger().info("Encryption test failed!");
				encryptionTestPassed = false;
			}
		}catch (java.security.InvalidKeyException e){
			keySize = 128;
			try{
				AES aes = new AES(keySize, "encryptionTest");
				String plain = "Pie is nice";
				String encrypted = aes.encrypt(plain);
				String decrypted = aes.decrypt(encrypted);
				if (decrypted.equals(plain)){
					this.getLogger().info("Encryption test passed.");
					encryptionTestPassed = true;
				}else{
					this.getLogger().info("Encryption test failed!");
					encryptionTestPassed = false;
				}
			}catch (Exception e1){
				e.printStackTrace();
				System.out.println("Encryption test failed!");
				encryptionTestPassed = false;
			}
		}catch (Exception e){
			e.printStackTrace();
			this.getLogger().info("Encryption test failed!");
			encryptionTestPassed = false;
		}finally{
			this.getLogger().info("Encryption test complete.");
		}
		this.getLogger().info(this.getDescription().getName() + " V" + this.getDescription().getVersion() + " is now Enabled!");
	}
	
	@Override
	public void onDisable(){
		this.getLogger().info(this.getDescription().getName() + " V" + this.getDescription().getVersion() + " is now Disabled!");
	}
	
	/**
	 * Get whether the encryption test performed onEnable passed or not.
	 * @return boolean
	 */
	public static boolean getEncryptionTestPassed(){
		return encryptionTestPassed;
	}
	
	/**
	 * Get the current AES key size.
	 * @return int
	 */
	public static int getKeySize(){
		return keySize;
	}
	
	private void exportResource(String resourceName, File destination) throws Exception {
		InputStream stream = null;
        OutputStream resStreamOut = null;
        try {
        	stream = this.getClass().getResourceAsStream(resourceName);
            if(stream == null) {
            	throw new Exception("Can't get resource '" + resourceName + "' from Jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(destination);
            while ((readBytes = stream.read(buffer)) > 0) {
            	resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
        	throw ex;
        } finally {
        	try{stream.close();}catch(Exception e){}
        	try{resStreamOut.close();}catch(Exception e){}
        }
	}
	
}
