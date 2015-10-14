package net.comdude2.plugins.comlibrary.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter{

	@Override
	public String format(LogRecord rec) {
		StringBuilder builder = new StringBuilder(1000);
		//builder.append("\n");
		builder.append("[");
		builder.append(getDate(rec.getMillis()));
		builder.append("]");
		builder.append(" ");
		builder.append("[");
		builder.append("CaravanManager");
		builder.append("]");
		builder.append(" ");
		builder.append("[");
		builder.append(rec.getLevel());
		builder.append("]");
		builder.append(" - ");
		builder.append(formatMessage(rec));
		builder.append(System.lineSeparator());
		return builder.toString();
	}
	
	public String getDate(long millisecs){
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date resultDate = new Date(millisecs);
		return date_format.format(resultDate);
	}
	
	public String getHead(Handler h){
		return super.getHead(h);
	}
	
	public String getTail(Handler h){
		return super.getTail(h);
	}
	
}
