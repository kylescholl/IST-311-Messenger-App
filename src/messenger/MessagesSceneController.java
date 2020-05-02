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
    List<Users> users_data;
    List<Conversation> convo_data;
    Long current_id;
    Set<Long> convo_id_set = new HashSet<>();
    Set<Long> friend_id_set = new HashSet<>();
    
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
            if (firstUserId.equals(current_id)) {
                convo_id_set.add(convoId);
                friend_id_set.add(firstUserId);
            }
            if (secondUserId.equals(current_id)) {
                convo_id_set.add(convoId);
                friend_id_set.add(secondUserId);
            }
        }
        System.out.println("convo_id_set: " + convo_id_set);
        System.out.println(users_data);
        // Load data into messagesList
        for (Long item : friend_id_set) {
            for (Users d : users_data) {
                if (item.equals(d.getId())) {
                    String name = d.getEmail();
                    String toDisplay = name + " " + convo_id_set.toString();
                    messagesList.getItems().add(name);
                }
            }
        }
    }

    @FXML
    void gotoConversationScene(MouseEvent event) {
        System.out.println("test");
        System.out.println("id: " + current_id);
        
        //messagesList.getItems().add("Item 2");
    }

    public void loadData() {
        //Query q_users_ = manager.createNamedQuery("Users.findById");
        Query q_users = manager.createNamedQuery("Users.findAll");
        users_data = q_users.getResultList();
        
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
