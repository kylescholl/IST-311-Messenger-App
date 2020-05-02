package messenger;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
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
    private ListView<?> messagesList;

    @FXML
    void gotoConversationScene(MouseEvent event) {
        System.out.println("test");
    }
    
    @FXML
    void initialize() {
        assert messagesList != null : "fx:id=\"messagesList\" was not injected: check your FXML file 'MessagesScene.fxml'.";

    }
}
