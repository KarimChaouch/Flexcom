package flexcom.checkMailPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import flexcom.checkMailPackage.*;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class CheckMails {

	public void check(String host, String storeType, String user, String password, String port, String starttls)
			throws IOException {
		String params = "C:\\EmailChecker\\Email.properties";
		String list = "C:\\EmailChecker\\ListOfMails.txt";
		FileReader propert = new FileReader(params);

		Properties prop = new Properties();
		prop.load(propert);
		String file = "C:\\EmailChecker\\EmailList.xls";

		int rowNumber = 0;

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("MailListSheet");
		HSSFRow rowhead = sheet.createRow((short) rowNumber);
		rowhead.createCell(0).setCellValue("Email");
		rowhead.createCell(1).setCellValue("Reception");
		rowhead.createCell(2).setCellValue("Subject");
		// rowhead.createCell(3).setCellValue("Threshold");

		// Font styles creation
		HSSFFont font1 = workbook.createFont();
		HSSFFont font2 = workbook.createFont();
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font2.setColor(IndexedColors.RED.getIndex());
		font1.setFontName("Yu Gothic UI Semibold");
		font2.setFontName("Yu Gothic UI Semibold");
		HSSFCellStyle style1 = workbook.createCellStyle();
		HSSFCellStyle style2 = workbook.createCellStyle();
		style1.setFont(font1);
		style2.setFont(font2);
		for (int j = 0; j <= 2; j++) {
			rowhead.getCell(j).setCellStyle(style1);
		}

		try {

			// create properties field
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.starttls.enable", starttls);
			properties.setProperty("mail.imaps.auth.plain.disable", "true");
			properties.setProperty("mail.imaps.auth.ntlm.disable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("imaps");

			store.connect(host, user, password);

			// create the folder object and open it
			Folder inboxFolder = store.getFolder("INBOX");
			inboxFolder.open(Folder.READ_WRITE);
			Folder alertesFolder = inboxFolder.getFolder("ALERTES");
			alertesFolder.open(Folder.READ_WRITE);

			// retrieve the last 50 messages from the folder in an array and print it
			int length = inboxFolder.getMessageCount();
			Message[] messages = null;
			if (length > 100) {
				messages = inboxFolder.getMessages(length - 100, length);
			} else {
				messages = inboxFolder.getMessages();
			}
			System.out.println("messages.length---" + messages.length);

			// Get Date - 1 list of messages
			Calendar calendar = Calendar.getInstance();

			calendar.add(Calendar.DATE, -1);
			Date date = new Date(calendar.getTimeInMillis());

			// Set of emails not received

			// Set of emails to track
			Map<String, String[]> emailsSet = new HashMap<String, String[]>();
			
			BufferedReader mailReader = new BufferedReader(new FileReader(list));
			String line;
			while ((line = mailReader.readLine()) != null) {
			emailsSet.put(line, new String[] { "Not received", "" });
			}
			mailReader.close();

			// Email for testing
//			emailsSet.put("pierre.azzopardi@flexcomlabs.com", new String[] { "Not received", "" });
			// List of message to move

			// Check emails and Append Csv file
			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				
				if (message.getSentDate().after(new Date(System.currentTimeMillis() - 14 * 60 * 60 * 1000))) {
					String sender = message.getFrom()[0].toString();
					
					
					if (sender.contains("<") && sender.contains(">")) {

						sender = sender.substring(sender.indexOf("<") + 1);
						sender = sender.substring(0, sender.indexOf(">"));
					}
					
					System.out.println(sender);

					if (emailsSet.containsKey(sender)) {
						List<Message> tempList = new ArrayList<>();

						tempList.add(message);
						Message[] tempMessageArray = tempList.toArray(new Message[tempList.size()]);
						
						emailsSet.put(sender, new String[] { "Received", message.getSubject().toString() });

						try {
							message.setFlag(Flags.Flag.SEEN, true);
							
							message.saveChanges();
						} catch (MessagingException e) {

						}
//						try {
//
//							System.out.println("Moving email");						
//							inboxFolder.copyMessages(tempMessageArray, alertesFolder);
//
//
//							message.saveChanges();
//							
//						} catch (MessagingException e) {
//
//						}
//						
//						try {
//		
//							System.out.println("Deleting message from : " + message.getFrom()[0].toString());
//							message.setFlag(Flags.Flag.DELETED, true);
//							message.saveChanges();
//							
//						} catch (MessagingException e) {
//
//						}
					}
//					System.out.println("---------------------------------");
//					System.out.println("Email Number " + (i + 1));
//					System.out.println("Subject: " + message.getSubject());
//					System.out.println("From: " + message.getFrom()[0]);
//					System.out.println("Text: " + message.getContent().toString());

				}

			}
			// move messages

			boolean problemFound = false;
			// Append CSV with information
			for (Map.Entry<String, String[]> entry : emailsSet.entrySet()) {

				HSSFRow row = sheet.createRow((short) ++rowNumber);
				row.createCell(0).setCellValue(entry.getKey());
				row.createCell(1).setCellValue(entry.getValue()[0]);
				if (entry.getValue()[0].equals("Not received")) {
					row.getCell(1).setCellStyle(style2);
				}

				row.createCell(2).setCellValue(entry.getValue()[1]);

				if (entry.getValue()[0].equals("Not received")) {
					problemFound = true;
				}
				// row.createCell(3).setCellValue("");
			}

			// Resize column
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			// Write output file with style
			File mailFile = new File(file);
			mailFile.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(mailFile, false);
			workbook.write(fileOut);
			fileOut.close();

			// Send the file by email
			if (true) {
				SendMail sendMail = new SendMail();
				sendMail.send();
			}
			// close the store and folder objects
			inboxFolder.close(true);
			alertesFolder.close(true);

			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}