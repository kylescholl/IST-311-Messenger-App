package messenger;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * ConversationSceneController class
 *
 * @author sscho
 */
public class ConversationSceneController {

    @FXML
    private Button messagesButton;

    @FXML
    private ListView<String> conversationList;

    @FXML
    private Button refreshButton;
    
        @FXML
    private Button sendMessageButton;

    @FXML
    private TextArea newMessageField;

    EntityManager manager;
    List<Users> users_data;
    List<Conversation> convo_data;
    Long convo_id;
//    Set<Long> convo_id_set = new HashSet<>();
//    Set<Long> friend_id_set = new HashSet<>();
//    Map<Long, Long> friend_id_map = new HashMap<>();
    
    // WIP
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
    
    @FXML
    void refreshData(ActionEvent event) {
        /**
         * This function will refresh data
         *
         * Can prob just call loadData() again
         *
         * --> Logic will prob be in initData(), so either call that or make a
         * new method will similar functionality.
         */
    }
    
    @FXML
    void sendMessage(ActionEvent event) {
        //send message to DB
        //clear text in textField
        //call refreshData()
    }

    void setupListener() {
        conversationList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                    } else {
                        setText("");
                    }
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    System.out.println("You clicked on " + cell.getItem());
                    //segue(cell.getItem());
                    e.consume();
                }
            });
            return cell;
        });
    }

    void initData(Long id) {
        convo_id = id;
        
        

    }

    public void loadData() {
        Query q_users = manager.createNamedQuery("Users.findAll");
        users_data = q_users.getResultList();

        Query q = manager.createNamedQuery("Conversation.findAll");
        convo_data = q.getResultList();
    }

    @FXML
    void initialize() {
        assert messagesButton != null : "fx:id=\"messagesButton\" was not injected: check your FXML file 'ConversationScene.fxml'.";
        assert conversationList != null : "fx:id=\"conversationList\" was not injected: check your FXML file 'ConversationScene.fxml'.";
        assert refreshButton != null : "fx:id=\"refreshButton\" was not injected: check your FXML file 'ConversationScene.fxml'.";
        assert sendMessageButton != null : "fx:id=\"sendMessageButton\" was not injected: check your FXML file 'ConversationScene.fxml'.";
        assert newMessageField != null : "fx:id=\"newMessageField\" was not injected: check your FXML file 'ConversationScene.fxml'.";
        
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST-311-messenger-app-JavaFXPU").createEntityManager();

        loadData();
        setupListener();
    }
}
