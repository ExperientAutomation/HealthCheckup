package Utils;

import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import LanuchApplication.EventXL_Apps;

public class emailReport_RD_Execution extends EventXL_Apps {
	
	String html;

	public void sendEmail(String testName, String sheetName) {

		final String username = config.LoginCredentails("USER_NAME");
		String Screenshotpath = "N:/QA/LiveWorkSpace/Data/ScreenShots/";

		Properties props = new Properties();
		props.put("mail.smtp.auth", false);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp2.expoexchange.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,
								config.LoginCredentails("PASSWORD"));
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));			

			message.setSubject("Automation Report for RD Apps HealthCheck ");

			BodyPart messageBodyPart = new MimeBodyPart();
			BodyPart attachmentPart = new MimeBodyPart();

			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			String file = config.getexcelpathRDONLY();
			String fileName = "RD App HealthCheck.xlsx";
			DataSource source = new FileDataSource(file);
			attachmentPart.setDataHandler(new DataHandler(source));
			attachmentPart.setFileName(fileName);

			// Get the count of Failures
			XlsUtil xls = new XlsUtil(config.getexcelpathRDONLY());

			int count = 0;
			int j =0, i =0;
			String result = null;
			for (j = 2; j <= 3; j++) {

				for (i = 3; i <= xls.getRowCount(sheetName); i++) {
					
					result = xls.getCellData(sheetName, j, i);					
					if (result.equalsIgnoreCase("Fail")) {						
						count++;
					}
				}
			}

			if (count == 0) {
				
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Chandrasekhar.Kulandasamy@experient-inc.com, sreejak@infinite.com"));
				System.out.println("All pass");
				html = "<p>Hi,</p><p>PFA the Automation Test report for RD Applications.</p><p>Note: All are Passed :-)</p><p>Thanks,</p><p>Chandra</p>";	
			} else if (count == 1) {

				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Chandrasekhar.Kulandasamy@experient-inc.com,sreejak@infinite.com,julie.racster@experient-inc.com"));
				System.out.println("One fail");
				html = "<p>Hi,</p><p> One RD Application got failed!. </p> <p> Please check the failed applications in the attachment and please open the below folder to view the failed applications Screenshot. </p><p>"+Screenshotpath+"</p><p>Thanks,</p><p>Chandra</p>";
			} else {
				
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Chandrasekhar.Kulandasamy@experient-inc.com,sreejak@infinite.com,julie.racster@experient-inc.com"));
				System.out.println("There are "+count+" failures");
				html = "<p>Hi,</p><p> There are "+count+" failures in the RD Application. </p> <p> Please check the failed applications in the attachment and please open the below folder to view the failed applications Screenshot. </p><p>"+Screenshotpath+"</p><p>Thanks,</p><p>Chandra</p>";
			}			

			messageBodyPart.setContent(html, "text/html");
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentPart);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Email Sent");

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}