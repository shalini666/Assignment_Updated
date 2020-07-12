package automation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

//-----------------------------------------------   Author:Shalini G  -------------------------------------------------------- //

// ----------------------------------------------  Last modified Date:11-07-2020    ------------------------------------------  //



public class Test {
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		URL TESTURL = new URL("http://192.168.43.213:4444/wd/hub");   // To set the nodeURL,user should register to set as node
		
		 DesiredCapabilities capability = DesiredCapabilities.chrome();   // To set the type of browser and OS use 
		 
         capability.setBrowserName("chrome");   //  Can set browser based on user requirement
         
         capability.setPlatform(Platform.ANY);  //  Can set platform based on user requirement
         
		System.setProperty("webdriver.chrome.driver","C:\\Drivers\\chromedriver.exe");  //  driver.exe is set for windows, based on platform drivers should be added
		
		WebDriver driver = new RemoteWebDriver(TESTURL, capability);  //  RemoteWebDriver is used to set which node (or machine) that our test will run against
		
		driver.get("https://test.kommunicate.io/");   //  To launch the application.
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//List<WebElement> iframeElements = driver.findElements(By.tagName("iframeResult"));//Find the total number of elements that have the tag “iframe”.
		//By executing a java script
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		Integer noOfFrames = Integer.parseInt(exe.executeScript("return window.length").toString());
		System.out.println("No. of iframes on the page are " + noOfFrames);  //  To print the no.of frames
		
		driver.switchTo().frame("Kommunicate widget iframe"); // Switch BY frame name
		
		WebElement ele=driver.findElement(By.xpath("//div[@id='launcher-svg-container']")); //  To inspect chat icon using id attribute and assign into Webelement
		ele.click();  //  To perform click action on chat icon
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		driver.findElement(By.xpath("//div[@class='km-kb-container vis']")).click();   //   To inspect FAQ icon using class attribute and perform click action
		
		List<WebElement> list = driver.findElements(By.xpath("//a"));    //  Identify the number of lists on webpage and assign into Webelement List 
        int FAQCount = list.size();     // Count the total no.of list of FAQ on Web Page 
        System.out.println("Total Number of FAQCount on webpage = "  + FAQCount);    //  Print the total count of list of FAQ on webpage
        
   
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		
	          	// object is created of Property file
				Properties props = new Properties();    

				props.put("mail.smtp.host", "smtp.gmail.com");   //  Host server set automatically- you can change based on your requirement 
		  
				props.put("mail.smtp.socketFactory.port", "465");  // To set the port of socket factory
		 
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  // To set socket factory
		 
				props.put("mail.smtp.auth", "true");  // To set the authentication to true
		 
				props.put("mail.smtp.port", "465");   // To set the port of SMTP server
		 
				// This will handle the complete authentication
				Session session = Session.getDefaultInstance(props,
		 
						new javax.mail.Authenticator() {
		 
							protected PasswordAuthentication getPasswordAuthentication() {
		 
							return new PasswordAuthentication("valid email", "valid password"); // User should provide valid email and valid password
		 
							}
		 
						});
				
				
				// Check for FAQ count < 20, trigger a mail to ourself with the actual FAQ count.
				
				int FAQCount1=20;   
				
				if(FAQCount > FAQCount1) // Check the condition for Total FAQCount is greater or lessthan FAQCount1(20)
				{
					System.out.println("FAQCount is morethan FAQCount1 and actual FAQCount is "+FAQCount); 
				}
		 
				else
				{
				try {
					
					Message message = new MimeMessage(session);  //To  Create object of MimeMessage class
		 
					message.setFrom(new InternetAddress("valid email"));  // To Set the from address
		 
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("valid email"));  // Set the recipient address and should be valid
		           
					message.setSubject("Testing Subject");  // Add the subject link
		 
					BodyPart messageBodyPart1 = new MimeBodyPart();  // Create object to add multimedia type content
		
					messageBodyPart1.setText("This is message body");  // To Set the body of email
		 
					MimeBodyPart messageBodyPart2 = new MimeBodyPart(); // Create another object to add another content
		 
					String filename = "F:\\a.xlsx";  // Mention the file which you want to send(should contain information of actual FAQ count)
		 
					DataSource source = new FileDataSource(filename); // Create data source and pass the filename
		 
					messageBodyPart2.setDataHandler(new DataHandler(source)); // set the handler
		 
					messageBodyPart2.setFileName(filename); //To set the file
		 
					Multipart multipart = new MimeMultipart(); // Create object of MimeMultipart class
		 
					multipart.addBodyPart(messageBodyPart2);   // add body part 1
		 
					multipart.addBodyPart(messageBodyPart1);   // add body part 2
		 
					message.setContent(multipart);  // set the content
		 
					Transport.send(message);  // finally send the email
		 
					System.out.println("=====Email Sent=====");
		 
				} catch (MessagingException e) {
		 
					throw new RuntimeException(e);
		 
				}
		
		}
       
       
 driver.quit(); // To exit the browser.
		
}

}
