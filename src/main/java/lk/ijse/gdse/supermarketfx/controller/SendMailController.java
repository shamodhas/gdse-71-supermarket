package lk.ijse.gdse.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/24/2024 8:56 AM
 * Project: supermarketfx-71
 * --------------------------------------------
 **/

public class SendMailController {
    public TextField txtEmail;
    public TextField txtSubject;
    public TextArea txtBody;

    @Setter
    private String customerEmail;

    public void sendMailOnAction(ActionEvent actionEvent) {
        try{
//            String toEmail = txtEmail.getText();
            String subject = txtSubject.getText();
            String body = txtBody.getText();

            // Gmail - need 2FA
//            sendMailUsingGmail(toEmail,subject,body);

//            or
            // Sendgrid
            sendEmailUsingSendgrid(customerEmail,subject,body);

            new Alert(Alert.AlertType.INFORMATION,"Sent...!").show();
        } catch (MessagingException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to send mail...!").show();
        }
    }

    private void sendEmailUsingSendgrid(String toEmail, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.sendgrid.net"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        props.put("mail.smtp.ssl.trust", "smtp.sendgrid.net");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("apikey", "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");
            }
        };
        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress("shamodha7@gmail.com"));

        msg.setSubject(subject);

        msg.setText(body);

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

        System.out.println("Message is ready");
        Transport.send(msg);
    }

    private void sendMailUsingGmail(String toEmail,String subject,String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("shamodha7@gmail.com", "mbaw gobn buvi apzt");
            }
        };
        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress("shamodha7@gmail.com"));

        msg.setSubject(subject);

        msg.setText(body);

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

        System.out.println("Message is ready");
        Transport.send(msg);
    }
}
