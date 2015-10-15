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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter{
	
	private String name = null;
	
	//Name is the name of the plugin
	public LogFormatter(String name){
		this.name = name;
	}
	
	@Override
	public String format(LogRecord rec) {
		StringBuilder builder = new StringBuilder(1000);
		//builder.append("\n");
		builder.append("[");
		builder.append(getDate(rec.getMillis()));
		builder.append("]");
		builder.append(" ");
		builder.append("[");
		builder.append(name);
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
