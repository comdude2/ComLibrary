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

package net.comdude2.plugins.comlibrary.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import net.comdude2.plugins.comlibrary.encryption.MD5;
import net.comdude2.plugins.comlibrary.util.Log;

public class WebDownloader {
	
	private Log log = null;
	
	public WebDownloader(Log log){
		this.log = log;
	}
	
	public boolean downloadFile(String address, String fileLocation) throws MalformedURLException, Exception{
		URL source = new URL(address);
		FileOutputStream fos = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) source.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			conn.connect();
			ReadableByteChannel rbc = Channels.newChannel(conn.getInputStream());
			fos = new FileOutputStream(fileLocation);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			try{
				conn.disconnect();
			}catch (Exception e){
				
			}
			try{
				rbc.close();
			}catch (Exception e){
				
			}
			fos = null;
			log.info("Download complete.");
		} catch (IOException e) {
			log.info("Download failed.");
			log.debug("IO Error: " + e.getMessage());
			log.debug(e.getMessage(), e);
			log.info("Retrying download...");
		}finally{
			try{
				if (fos != null){
					fos.close();
				}
				
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean downloadFileWithMD5(String address, String fileLocation, String MD5) throws MalformedURLException{
		URL source = new URL(address);
		FileOutputStream fos = null;
		int tryNo = 1;
		do{
			try {
				HttpURLConnection conn = (HttpURLConnection) source.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				conn.connect();
				ReadableByteChannel rbc = Channels.newChannel(conn.getInputStream());
				fos = new FileOutputStream(fileLocation);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				try{
					conn.disconnect();
				}catch (Exception e){
					
				}
				try{
					rbc.close();
				}catch (Exception e){
					
				}
				fos = null;
				if (checkMD5File(MD5, fileLocation)){
					log.info("Download complete.");
					return true;
				}
			} catch (IOException e) {
				log.info("Download try " + tryNo + " failed.");
				log.debug("IO Error: " + e.getMessage());
				log.debug(e.getMessage(), e);
				log.info("Retrying download...");
			}finally{
				try{
					if (fos != null){
						fos.close();
					}
					
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			tryNo++;
			try{
				File f = new File(fileLocation);
				if (f.exists()){
					f.delete();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}while (tryNo < 5);
		log.warning("Failed to download, too many retries.");
		return false;
	}
	
	public boolean checkMD5File(String original, String fileLocation){
		log.debug("Checking MD5's...");
		try {
			FileInputStream fis = new FileInputStream(fileLocation);
			String downloaded = MD5.getMD5Checksum(fis, 4096);
			try{
				fis.close();
			}catch (Exception e){
				
			}
			log.debug("Target MD5: " + original);
			log.debug("Downloaded: " + downloaded);
			if (downloaded.equalsIgnoreCase(original)){
				return true;
			}else{
				return false;
			}
		} catch (FileNotFoundException e) {
			System.out.println("File location given wasn't found: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error while getting MD5 of file: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
}
