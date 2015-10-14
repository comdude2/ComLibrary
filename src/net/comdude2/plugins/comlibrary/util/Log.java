package net.comdude2.plugins.comlibrary.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
//import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Log {
	
	private Logger logger = null;
	private boolean debug = false;
	private LogFormatter formatterTxt = null;
	private FileHandler fileTxt = null;
	
	public Log(String name, boolean debug){
		logger = Logger.getLogger(name);
		logger.setUseParentHandlers(false);
		//ConsoleHandler handler = new ConsoleHandler();
		//handler.setFormatter(new LogFormatter());
		//logger.addHandler(handler);
		this.debug = debug;
		formatterTxt = new LogFormatter();
		try {
			File file = new File("");
			String path = file.getAbsolutePath() + "/";
			file = new File(path + "logs/");
			if (!file.exists()){
				file.mkdir();
			}
			file = new File(path + "logs/latest.txt");
			if (file.exists()){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
				String logName = sdf.format(file.lastModified()) + ".txt";
				//System.out.println(path + "logs/" + logName);
				File f = new File(path + "logs/" + logName);
				//f.createNewFile();
				boolean worked = file.renameTo(f);
				if (worked){
					//System.out.println("Managed to rename log");
				}else{
					//System.out.println("Failed to rename log");
				}
			}
			fileTxt = new FileHandler(path + "logs/latest.txt");
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
