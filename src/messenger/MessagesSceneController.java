package messenger;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MessagesSceneController {

    @FXML
    private ListView<String> messagesList;

    EntityManager manager;
    Query q_maxID;
    List<Users> users_data;
    List<Conversation> convo_data;
    Long current_id;
    Set<Long> convo_id_set = new HashSet<>();
    //Set<Long> convo_id_set = Collections.<Long>emptySet();
    
        // Receive users_data from previous controller
    void initData(Long id) {
        current_id = id;
        System.out.println("convo_data: " + convo_data);
        
        for (Conversation c : convo_data) {
            Long convoId = c.getConversationId();
            Long firstUserId = c.getFirstUserId().getId();
            Long secondUserId = c.getSecondUserId().getId();
            System.out.println("\nc_id.getId: " + c.getFirstUserId().getId());
            System.out.println("current_id: " + current_id);
            if (firstUserId.equals(current_id) || secondUserId.equals(current_id)) {
                System.out.println("conversation found");
                System.out.println("convoId: " + convoId);
                System.out.println("set: " + convo_id_set);
                System.out.println(convoId.getClass());
                convo_id_set.add(convoId);
                System.out.println("test");
            }
        }
        System.out.println("convo_id_set: " + convo_id_set);
    }

    @FXML
    void gotoConversationScene(MouseEvent event) {
        System.out.println("test");
        System.out.println("id: " + current_id);
        
        messagesList.getItems().add("Item 2");
    }

    public void loadData() {
        q_maxID = manager.createNamedQuery("Users.getMaxID");
        Query q_all = manager.createNamedQuery("Users.findAll");
        users_data = q_all.getResultList();

        Query q = manager.createNamedQuery("Conversation.findAll");
        convo_data = q.getResultList();

        
        
        messagesList.getItems().add("Item 1");
    }

    void setupListener() {
        messagesList.setCellFactory(lv -> {
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
                    e.consume();
                }
            });
            return cell;
        });
    }

    @FXML
    void initialize() {
        assert messagesList != null : "fx:id=\"messagesList\" was not injected: check your FXML file 'MessagesScene.fxml'.";

        //load users_data from database
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST-311-messenger-app-JavaFXPU").createEntityManager();
        //load users_data
        loadData();
        setupListener();
    }
}
