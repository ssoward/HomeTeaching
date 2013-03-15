package com.soward.util;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import com.soward.object.Member;

import java.io.UnsupportedEncodingException;

public class SendEmail {
    public SendEmail() {
    }
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "scott.soward@gmail.com";
    private static final String SMTP_AUTH_PWD  = "Amorvivir@82";
    private static final String fromName = "nilsynils@gmail.com";
    
    public static void main( String args[] ) {
        SendEmail se = new SendEmail();
        // = "8018280383@vtext.com";//
        // "sean treseder";
        // "scott soward";
        // = "8017034195@vtext.com";//
        // = "8017035030@tmomail.com";
        // se.sendMail("8018280383@vtext.com", "message", "toName", "fromName");
        // se.sendMail("scott.soward@gmail.com", "message", "toName",
        // "fromName");
    }

    public static void sendMail(ArrayList<Member> recipient, String mess, String from,
            String subj) throws Exception{
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
        // props.put("mail.smtps.quitwait", "false");

        Session mailSession = Session.getDefaultInstance(props);
        //mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subj);
        message.setContent(mess, "text/plain");
        message.setFrom( new InternetAddress( fromName, from ) );
        
        System.out.print("sending mail to: ");
        for ( Member rec : recipient ) {
            System.out.print(" "+rec.getFirstName()+"_"+rec.getLastName());
            message.addRecipient( Message.RecipientType.TO, new InternetAddress( rec.getEmail(), rec.getFirstName() ) );
        } 
        System.out.println("");
        System.out.println(mess);

        transport.connect
          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
