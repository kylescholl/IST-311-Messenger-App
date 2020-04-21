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
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MessagesSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<?> messagesList;

    @FXML
    private Button settingsButton;

    @FXML
    private Button tempButton;

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
    void initialize() {
        assert messagesList != null : "fx:id=\"messagesList\" was not injected: check your FXML file 'MessagesScene.fxml'.";
        assert settingsButton != null : "fx:id=\"settingsButton\" was not injected: check your FXML file 'MessagesScene.fxml'.";
        assert tempButton != null : "fx:id=\"tempButton\" was not injected: check your FXML file 'MessagesScene.fxml'.";

    }
}
