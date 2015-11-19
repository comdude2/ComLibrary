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

package net.comdude2.plugins.comlibrary.encryption;

import java.io.InputStream;
import java.security.MessageDigest;

public class MD5 {
	
	/*
	 * NOTE You can parse a string to this using the following code: 
	 * InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
	*/
	
	public static byte[] createChecksum(InputStream is, int bufferSize) throws Exception {
		byte[] buffer = new byte [bufferSize];
	    MessageDigest complete = MessageDigest.getInstance("MD5");
	    int numRead;
	    do {
	        numRead = is.read(buffer);
	        if (numRead > 0) {
	            complete.update(buffer, 0, numRead);
	        }
	    } while (numRead != -1);
	    is.close();
	    return complete.digest();
	}
	
	public static String getMD5Checksum(InputStream is, int bufferSize) throws Exception {
	   byte[] b = createChecksum(is, bufferSize);
	   String result = "";
	   for (int i=0; i < b.length; i++) {
	       result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	   }
	   return result;
	}
	
}
