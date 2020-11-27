package flexcom.checkMailPackage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
	public static void main(String[] args) throws IOException {
		String params = "C:\\EmailChecker\\Email.properties";
		FileReader properties = new FileReader(params);

		Properties prop = new Properties();
		prop.load(properties);

		CheckMails checkMails = new CheckMails();

		String host = prop.getProperty("host");// change accordingly
		String mailStoreType = prop.getProperty("mailStoreType");
		String username = prop.getProperty("username");// change accordingly
		String password = prop.getProperty("password");// change accordingly
		String port = prop.getProperty("mail.imap.port");// change accordingly
		String starttls = prop.getProperty("mail.imap.starttls.enable");
		String sharedbox = prop.getProperty("sharedbox");
		checkMails.check(host, mailStoreType, username+sharedbox, password,port,starttls);

	}
}
