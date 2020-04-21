package messenger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginSceneController {
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Hyperlink forgotPasswordButton;

    @FXML
    private Hyperlink createAccountButton;

    @FXML
    void gotoConversationScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConversationScene.fxml"));
            Parent secondRoot = loader.load();
            
            // Show Second FXML in new a window            
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("Conversation Window");
            stage.show();
        } 
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void gotoForgotPasswordScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPasswordScene.fxml"));
            Parent secondRoot = loader.load();
            
            // Show Second FXML in new a window            
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("Forgot Password Window");
            stage.show();
        } 
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void gotoNewUserScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewUserScene.fxml"));
            Parent secondRoot = loader.load();
            
            // Show Second FXML in new a window            
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("New User Window");
            stage.show();
        } 
        catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert forgotPasswordButton != null : "fx:id=\"forgotPasswordButton\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert createAccountButton != null : "fx:id=\"createAccountButton\" was not injected: check your FXML file 'LoginScene.fxml'.";
        
        // will load data from DB here
        
        
    }
}
