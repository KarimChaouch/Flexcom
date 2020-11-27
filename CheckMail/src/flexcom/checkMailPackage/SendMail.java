package flexcom.checkMailPackage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	public void send() throws IOException {
		
		InputStream inputStream;
		String params="C:\\EmailChecker\\Sender.properties";
//		if(args.length==2) {
//		//Properties file	
//		}
//		else {
//			System.out.println("Please specify the properties file");
//		}

           FileReader properties=new FileReader(params);

           Properties prop = new Properties();
            prop.load(properties);

            // set the properties value
    		// Recipient's email ID needs to be mentioned.
    		String to = prop.getProperty("MailTo");

    		// Sender's email ID needs to be mentioned
    		String from = prop.getProperty("MailFrom");

    	    final String username = prop.getProperty("username");// change accordingly
    		final String password = prop.getProperty("password");// change accordingly

    		// Assuming you are sending email through relay.jangosmtp.net
    		String host = prop.getProperty("host");
    		String auth =prop.getProperty("mail.smtp.auth");
    		String starttls=prop.getProperty("mail.smtp.starttls.enable");
    		String port = prop.getProperty("mail.smtp.port");
    		String file = "C:\\EmailChecker\\EmailList.xls";
            // save properties to project root folder


		


		Properties props = new Properties();
		props.put("mail.smtp.auth",auth);
		props.put("mail.smtp.starttls.enable",  starttls);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			
			System.out.println("Preparing session...");
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("ALERT : List of unreceived Mails");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText("");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = file;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("EmailList.xls");
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}