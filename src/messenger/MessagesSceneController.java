package messenger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * MessagesSceneController class
 *
 * For when a user is looking at all of their messages.
 *
 * @author sscho
 */
public class MessagesSceneController {

    @FXML
    private ListView<String> messagesList;

    @FXML
    private Button createConversationButton;

    @FXML
    private TextField newEmailField;
    
    // JDBC driver name, database URL and credentials
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://ist311db.cahpdmpysbav.us-east-2.rds.amazonaws.com:3306/db8?zeroDateTimeBehavior=CONVERT_TO_NULL";
    //  Database credentials
    static final String USER = "db8_user";
    static final String PASS = "db8_user_ist311_rpz";

    EntityManager manager;
    List<Users> users_data;
    List<Conversation> convo_data;
    Long current_id;
    Long other_id;
    Set<Long> convo_id_set = new HashSet<>();
    Set<Long> friend_id_set = new HashSet<>();
    Map<Long, Long> friend_id_map = new HashMap<>();

    Stage old_stage;
    
    @FXML
    void createConversation(ActionEvent event) {
        String s = newEmailField.getText();
        other_id = getIdByEmail(s);
        neww();
    }
    
    Long getIdByEmail(String email) {
        Connection connection = null;
        Statement statement = null;
        Long id_value = -1L;

        String value = "\'" + email + "\'";
        
        String query 
                = "SELECT * FROM Users\n"
                + "WHERE email = " + value + ";";
        System.out.println(query);

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            statement = connection.createStatement();

            String sql = query;
            ResultSet rs = statement.executeQuery(sql);

            //Get data from result set
            while (rs.next()) {
                id_value = rs.getLong("user_id");
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {

            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
            }
        }
        return id_value;
    }
    
    void neww() {
        Connection connection = null;
        Statement statement = null;

        String values = current_id + "," + other_id;
        
        String query = "INSERT INTO Conversation (first_user_id, second_user_id) "
                + "VALUES (" + values + ");";
        System.out.println(query);

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            statement = connection.createStatement();

            String sql = query;
            //ResultSet rs = statement.executeQuery(sql);
            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException se) {
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {

            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
            }
        }
    }

    // Receive users_data from previous controller
    void initData(Stage s, Long id) {
        old_stage = s;
        current_id = id;
        System.out.println("convo_data: " + convo_data);

        for (Conversation c : convo_data) {
            Long convoId = c.getId();
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
                System.out.println("\nconvo_id being passed: " + convo_id);
                controller.initData(convo_id, current_id);

                // Show Second FXML in new a window            
                Stage stage = new Stage();
                stage.setScene(new Scene(secondRoot));
                stage.setTitle("Messages Window");

                old_stage.close();

                stage.show();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    @FXML
    void gotoConversationScene(MouseEvent event) { //REMOVE

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
        assert createConversationButton != null : "fx:id=\"createConversationButton\" was not injected: check your FXML file 'MessagesScene.fxml'.";
        assert newEmailField != null : "fx:id=\"newEmailField\" was not injected: check your FXML file 'MessagesScene.fxml'.";

        manager = (EntityManager) Persistence.createEntityManagerFactory("IST-311-messenger-app-JavaFXPU").createEntityManager();

        loadData();
        setupListener();
    }
}
