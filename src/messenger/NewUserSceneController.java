package messenger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * NewUserSceneController Class
 *
 * @author sscho
 */
public class NewUserSceneController implements Initializable {
    
    @FXML
    private Button continueButton;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private Hyperlink loginSegueButton;
    
    @FXML
    private void continueButtonPress(ActionEvent event) {
        /**
         * TODO:
         * Verify proper data entry --> email must be in correct format
         * Verify email doesn't already exist in system
         * 
         * Send new user's data to DB
         * Notify user of success (done)
         * Close or transition back to login scene
         *  --> if unable to find a solution, just have them close it (see below)
         * 
         * PROBLEM: Old login scene will not have new user's information unless queried again
         * POSSIBLE SOLUTION: Close old window when transitioning to new scene
         */
        
        
        // This will only happen when a new user is successfully registered //
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Welcome!");
            alert.setContentText("New Account Created");
            alert.showAndWait();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
    /**
     * Return to LoginScene
     */
    @FXML
    private void loginSegue(ActionEvent event) {
        /**
         * Would simply just closing the window work?
         * Problems:
         *  Old scene may not have most current data from database
         *  Only works when returning to previous scene, wouldn't work when going to a new scene
         */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent secondRoot = null;
        try {
            secondRoot = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(NewUserSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Show Second FXML in new a window            
        Stage stage = new Stage();
        stage.setScene(new Scene(secondRoot));
        stage.setTitle("Login Window");
        stage.show();
        // loading from FXML
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
