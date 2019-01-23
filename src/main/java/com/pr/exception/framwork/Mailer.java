package com.pr.exception.framwork;

import java.util.Properties;

import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Mailer {

	private static final Log log = LogFactory.getLog(Mailer.class);

	public static void send(Email email) {
		try {
			MimeMessage message = new MimeMessage(getSession(email));

			InternetAddress from = new InternetAddress(email.getFrom(), null);
			InternetAddress[] fromAddresses = { from };
			message.setFrom(from);
			message.setReplyTo(fromAddresses);

			message.setSubject(email.getSubject() + " Exception");
			message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(email.getTo(), false));

			Multipart multiPart = new MimeMultipart();
			MimeBodyPart messageText = new MimeBodyPart();
			messageText.setContent(email.getBody(), "text/html");
			multiPart.addBodyPart(messageText);

			message.setContent(multiPart);
			/**
			 * do the actual sending here
			 *
			 */
			log.info("before transport set");
			Transport.send(message);
			log.info("Email message sent");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	protected static Session getSession(Email email) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");// Outgoing server requires
											// authentication
		props.put("mail.smtp.starttls.enable", "true");// TLS must be activated
		props.put("mail.smtp.host", email.getHost()); // Outgoing server (SMTP)
														// - change it to your
														// SMTP server
		props.put("mail.smtp.port", email.getPort());// Outgoing port
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email.getFrom(), email.getPassword());
			}
		});
		return session;
	}
}
