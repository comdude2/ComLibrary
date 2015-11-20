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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class WebReader {
	
	/**
	 * Read data from a web source.
	 * @param String address
	 * @return boolean
	 */
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
