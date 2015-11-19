package net.comdude2.plugins.comlibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class ObjectManager {
	
	public static void writeObject(File f, Object o) throws IOException{
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(o);
		out.close();
		fos.close();
	}
	
	//NOTE WARNING This returns a stream and therefore the stream will need to be closed to prevent leaks.
	public static ObjectInputStream readObject(File f) throws FileNotFoundException,IOException{
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream in = new ObjectInputStream(fis);
		return in;
	}
	
}
