package net.comdude2.plugins.comlibrary.encryption;

import java.io.InputStream;
import java.security.MessageDigest;

public class MD5 {
	
	public MD5(){
		
	}
	
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
