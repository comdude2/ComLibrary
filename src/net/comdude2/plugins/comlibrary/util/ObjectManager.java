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

package net.comdude2.plugins.comlibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class ObjectManager {
	
	/**
	 * Write a serialised object to a file.
	 * @param File f
	 * @param Object o
	 * @throws IOException
	 */
	public static void writeObject(File f, Object o) throws IOException{
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(o);
		out.close();
		fos.close();
	}
	
	//NOTE WARNING This returns a stream and therefore the stream will need to be closed to prevent leaks.
	/**
	 * Read a serialised object from a file.
	 * @param File f
	 * @return ObjectInputStream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ObjectInputStream readObject(File f) throws FileNotFoundException,IOException{
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream in = new ObjectInputStream(fis);
		return in;
	}
	
}
