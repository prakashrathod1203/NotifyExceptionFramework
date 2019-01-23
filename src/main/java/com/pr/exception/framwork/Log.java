package com.pr.exception.framwork;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Log {

	public static void emailNotify(String subject,String smtpHost, String smtpPort, String smtpUserName, String smtpPass, String smtpToMail, Exception e) {
		Email email = new Email();
		email.setHost(smtpHost);
		email.setPort(smtpPort);
		email.setFrom(smtpUserName);
		email.setPassword(smtpPass);
		email.setTo(smtpToMail);
		email.setSubject(subject);
		
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");
		builder.append("<b>Date/Time :</b> ").append(dateFormat.format(new Date())).append("<br/>");
		builder.append("<b>Class :</b> ").append(e.getStackTrace()[0].getClassName()).append("<br/>");
		builder.append("<b>Method :</b> ").append(e.getStackTrace()[0].getMethodName()).append("<br/>");
		builder.append("<b>Line :</b> ").append(e.getStackTrace()[0].getLineNumber()).append("<br/>");
		builder.append("<b>Message :</b> ").append("<pre>").append(e.getMessage()).append("</pre>").append("<br/>");
		email.setBody(builder.toString());
		Mailer.send(email);
		
	}
	public static void main(String[] s) {
		try{
			int a=0;
			a+=10/a;
		}catch(ArithmeticException e){
			try {
				emailNotify("<Project-Name>","<host-name>","<port>","<user-name>","<password>","<devloper-email-address>",e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
