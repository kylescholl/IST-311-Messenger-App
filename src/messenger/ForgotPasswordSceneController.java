package messenger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Justin
 */
public class ForgotPasswordSceneController implements Initializable {

    @FXML
    private Button sendButton;

    /**
     * Initializes the controller class.
     */
    @FXML
    void sendButtonPress(ActionEvent event) {
        try {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Password recovery email sent");
            alert.showAndWait();

            //todo function to send email to input
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'ForgotPasswordScene.fxml'.";
        // TODO
    }

}
