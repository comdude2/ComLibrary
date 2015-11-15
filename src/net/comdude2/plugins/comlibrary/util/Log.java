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
import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Log {
	
	private Logger logger = null;
	private boolean debug = false;
	private LogFormatter formatterTxt = null;
	private FileHandler fileTxt = null;
	
	public Log(String name, File f, boolean debug){
		logger = Logger.getLogger(name);
		logger.setUseParentHandlers(false);
		//ConsoleHandler handler = new ConsoleHandler();
		//handler.setFormatter(new LogFormatter());
		//logger.addHandler(handler);
		this.debug = debug;
		formatterTxt = new LogFormatter(name);
		try {
			fileTxt = new FileHandler(f.getAbsoluteFile().toString());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (fileTxt != null){
			fileTxt.setFormatter(formatterTxt);
	    	logger.addHandler(fileTxt);
		}else{
			this.error("Couldn't initialise logger file", null);
		}
	}
	
	public void info(String message){
		logger.info(message);
	}
	
	public void warning(String message){
		logger.warning(message);
	}
	
	public void severe(String message){
		logger.severe(message);
	}
	
	public void error(String message, Exception e){
		logger.log(MyLevel.Error, "##############################");
		logger.log(MyLevel.Error, message);
		logger.log(MyLevel.Error, "");
		logger.log(MyLevel.Error, "Stack Trace:");
		e.printStackTrace();
		logger.log(MyLevel.Error, "Stack trace for exception: " + e.getMessage() + " CAUSE: " + e.getCause(), e);
		logger.log(MyLevel.Error, "##############################");
	}
	
	public void debug(String message){
		if (debug){
			logger.log(MyLevel.Debug, message);
		}
	}
	
	public void debug(String message, Exception e){
		if (debug){
			logger.log(MyLevel.Debug, "##############################");
			logger.log(MyLevel.Debug, message);
			logger.log(MyLevel.Debug, "");
			logger.log(MyLevel.Debug, "Stack Trace:");
			e.printStackTrace();
			logger.log(MyLevel.Debug, "Stack trace for exception: " + e.getMessage() + " CAUSE: " + e.getCause(), e);
			logger.log(MyLevel.Debug, "##############################");
		}
	}
	
	public void setDebug(boolean debug){
		this.debug = debug;
	}
	
}
