/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin
 */
public class NewUserSceneController implements Initializable {

    @FXML
    private Button continueButton;
    @FXML
    private Hyperlink signInLink;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void continueButtonPress(ActionEvent event) {
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

    @FXML
    private void signInAction(ActionEvent event) {
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
}
