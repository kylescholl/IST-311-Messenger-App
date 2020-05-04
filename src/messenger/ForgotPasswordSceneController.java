//package messenger;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//import java.net.URL;
//import java.util.Properties;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
///**
// * FXML Controller class
// *
// * @author Justin
// */
//public class ForgotPasswordSceneController implements Initializable {
//
//    @FXML
//    private Button sendButton;
//    @FXML
//    private TextField emailLabel;
//
//    /**
//     * Initializes the controller class. TODO add function to send the recovery
//     * email
//     */
//    @FXML
//    void sendButtonPress(ActionEvent event) {
//        try {
//            // add email function here userEmail the address
//            // Recipient's email ID needs userEmail be mentioned.
//            String userEmail = emailLabel.getText();
//
//            // Sender's email ID needs userEmail be mentioned
//            String from = "web@gmail.com";
//
//            // Assuming you are sending email from localhost
//            String host = "localhost";
//
//            // Get system properties
//            Properties properties = System.getProperties();
//
//            // Setup mail server
//            properties.setProperty("mail.smtp.host", host);
//
//            // Get the default Session object
//            Session session = Session.getDefaultInstance(properties);
//
//            try {
//                // Create a default MimeMessage object.
//                MimeMessage message = new MimeMessage(session);
//
//                // Set From: header field of the header.
//                message.setFrom(new InternetAddress(from));
//
//                // Set To: header field of the header.
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
//
//                // Set Subject: header field
//                message.setSubject("Messenger Application Password Recovery");
//
//                // Now set the actual message
//                message.setText("Forgot your password?");
//
//                // Send message
//                Transport.send(message);
//                System.out.println("Sent message successfully....");
//            } catch (MessagingException mex) {
//                mex.printStackTrace();
//            }
//
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Information Dialog");
//            alert.setHeaderText(null);
//            alert.setContentText("Password recovery email sent");
//            alert.showAndWait();
//
//        } catch (Exception ex) {
//            System.err.println(ex);
//        }
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'ForgotPasswordScene.fxml'.";
//        // TODO
//    }
//}
