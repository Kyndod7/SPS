package edu.ucf.thesis.server.push;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.ucf.thesis.server.experiment.SPSTrafficSimulator;
import edu.ucf.thesis.server.util.ServerProperties;

public class SMSSender {

	private final static Logger LOGGER = Logger.getLogger(SPSTrafficSimulator.class.getName());
	private static SMSSender mSingleton;
	private Session mSession; 
    
	public static SMSSender getInstace() {
		if (mSingleton == null) {
			mSingleton = new SMSSender();
		}
		return mSingleton;
	}
	
    private SMSSender() {
    	initialize();
    }
    
    private void initialize() {
		ServerProperties serverProperties = ServerProperties.getInstance();
		final String username = serverProperties.getProxyEmailUsername();
		final String password = serverProperties.getProxyEmailPassword();
    	
    	Properties emailProperties = new Properties();
    	emailProperties.put("mail.smtp.starttls.enable", "true");
    	emailProperties.put("mail.smtp.auth", "true");
    	emailProperties.put("mail.smtp.host", "smtp.gmail.com");
    	emailProperties.put("mail.smtp.port", "587");
    	
        mSession = Session.getInstance(emailProperties, new javax.mail.Authenticator() {
        	
        	@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
            
        });
    }
    
    public void send(String textMessage, String recipient) {

        try {
            Message message = new MimeMessage(mSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            //message.setFrom(new InternetAddress(USERNAME));
            //message.setSubject("Subject");
            message.setText(textMessage);
            Transport.send(message);
        } catch (MessagingException e) {
			LOGGER.warning(e.getMessage());
        }
    }
    
}