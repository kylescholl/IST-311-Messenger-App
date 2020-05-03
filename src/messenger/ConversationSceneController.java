package messenger;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * ConversationSceneController class
 *
 * @author sscho
 */
public class ConversationSceneController {

    @FXML
    private Button messagesButton;

    @FXML
    private ListView<?> conversationList;

    @FXML
    private Button refreshButton;

    @FXML
    void refreshData(ActionEvent event) {
        // this function will refresh data
        // can prob just call loadData() again
    }

    @FXML
    void gotoMessagesScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MessagesScene.fxml"));
            Parent secondRoot = loader.load();

            // Show Second FXML in new a window            
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("Messages Window");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    void initData(Long id) {

    }

    @FXML
    void initialize() {
        assert messagesButton != null : "fx:id=\"messagesButton\" was not injected: check your FXML file 'ConversationScene.fxml'.";
        assert conversationList != null : "fx:id=\"conversationList\" was not injected: check your FXML file 'ConversationScene.fxml'.";
        assert refreshButton != null : "fx:id=\"refreshButton\" was not injected: check your FXML file 'ConversationScene.fxml'.";

    }
}
