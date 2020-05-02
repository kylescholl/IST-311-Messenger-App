package messenger;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
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
    Map<Long, Long> friend_id_map = new HashMap<>();

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
                friend_id_set.add(secondUserId);
                friend_id_map.put(secondUserId, convoId);
            }
            if (secondUserId.equals(current_id)) {
                convo_id_set.add(convoId);
                friend_id_set.add(firstUserId);
                friend_id_map.put(firstUserId, convoId);
            }
        }
        // Load data into messagesList
        for (Long friend : friend_id_set) {
            for (Users user : users_data) {
                if (friend.equals(user.getId())) {
                    String toDisplay = user.getEmail();
                    messagesList.getItems().add(toDisplay);
                }
            }
        }
    }

    Long findIdByEmail(String email) {
        for (Users user : users_data) {
            if (email.equals(user.getEmail())) {
                Long user_id = user.getId();
                System.out.println("MATCH");
                System.out.println("friend_id_map: " + friend_id_map);
                System.out.println("user_id: " + user_id);
                return friend_id_map.get(user_id);
            }
        }
        return -1L;
    }

    void segue(String cell) {
        // loop through sets to figure out matching conversation id
        Long convo_id = findIdByEmail(cell);
        System.out.println("cell: " + cell);
        System.out.println("convo_id: " + convo_id);

        if (convo_id.equals(-1L)) {
            System.out.println("Error: convo_id is -1L");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ConversationScene.fxml"));
                Parent secondRoot = loader.load();

                // Pass data to new controller
                ConversationSceneController controller = loader.<ConversationSceneController>getController();
                controller.initData(convo_id);

                // Show Second FXML in new a window            
                Stage stage = new Stage();
                stage.setScene(new Scene(secondRoot));
                stage.setTitle("Messages Window");
                stage.show();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    @FXML
    void gotoConversationScene(MouseEvent event) {

        System.out.println("id: " + current_id);

        //messagesList.getItems().add("Item 2");
    }

    public void loadData() {
        Query q_users = manager.createNamedQuery("Users.findAll");
        users_data = q_users.getResultList();

        Query q = manager.createNamedQuery("Conversation.findAll");
        convo_data = q.getResultList();
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
                    segue(cell.getItem());
                    e.consume();
                }
            });
            return cell;
        });
    }

    @FXML
    void initialize() {
        assert messagesList != null : "fx:id=\"messagesList\" was not injected: check your FXML file 'MessagesScene.fxml'.";

        manager = (EntityManager) Persistence.createEntityManagerFactory("IST-311-messenger-app-JavaFXPU").createEntityManager();

        loadData();
        setupListener();
    }
}
