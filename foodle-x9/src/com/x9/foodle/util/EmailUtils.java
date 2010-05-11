package com.x9.foodle.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

/**
 * Bla bla
 * 
 * @author tgwizard
 * 
 */
public class EmailUtils {
	public static void sendEmail(ServletContext servletContext, String to,
			String subject, String msg) {
		String from = "foodlex9@gmail.com";
		String host = "smtp.gmail.com";
		String username = "foodlex9@gmail.com";
		String password = servletContext.getInitParameter("foodlex9password");
		Properties props = new Properties();
		props.put("mail.smtps.auth", "true");
		Session session = Session.getInstance(props, null);
		Message email = new MimeMessage(session);

		System.out.println("----- Email -----");
		System.out.println("To: " + to);
		System.out.println("Subject: " + subject);
		System.out.println("Message: " + msg);
		System.out.println("------ End ------");

		try {
			email.setFrom(new InternetAddress(from));
			email.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to, false));
			email.setSubject(subject);
			email.setText(msg);

			Transport t = session.getTransport("smtps");
			try {
				t.connect(host, username, password);
				t.sendMessage(email, email.getAllRecipients());
			} finally {
				t.close();
			}
		} catch (AddressException e) {
			// throw new RuntimeException(e);
			System.err.println("Email error");
			e.printStackTrace();
		} catch (MessagingException e) {
			// throw new RuntimeException(e);
			System.err.println("Email error");
			e.printStackTrace();
		}
	}
}
