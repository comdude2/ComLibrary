package net.comdude2.plugins.comlibrary.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class WebReader {
	
	public WebReader(){
		
	}
	
	public static LinkedList <String> readData(String address) throws MalformedURLException{
		LinkedList <String> lines = new LinkedList <String> ();
		try {
			URL url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			conn.connect();
			InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
			BufferedReader buff = new BufferedReader(in);
			String line = "";
			do{
				line = null;
				line = buff.readLine();
				lines.add(line);
			}while (line != null);
			return lines;
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
}
