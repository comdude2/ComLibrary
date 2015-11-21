package net.comdude2.plugins.comlibrary.net;

import java.util.LinkedList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	public static boolean send(String to, LinkedList <String> cc, String from, String smtpHost, String subject, String text) throws AddressException, MessagingException{
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		if (cc != null){
			for (String s : cc){
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(s));
			}
		}
		message.setSubject(subject);
		message.setText(text);
		Transport.send(message);
		return true;
	}
	
}
