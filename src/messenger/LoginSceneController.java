package messenger;

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
        System.out.println("test");
        
        Main m = new Main();
        Parent p = m.root;
        
//        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Scene scene = new Scene(p);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void gotoForgotPasswordScene(ActionEvent event) {

    }

    @FXML
    void gotoNewUserScene(ActionEvent event) {

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
