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
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

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
    List<Messages> messages_data;
    List<Messages> test_data;

    LinkedList<LinkedHashMap<String, String>> messages = new LinkedList<>();
    LinkedHashMap<Long, String> messagesList;

    Long convo_id;
    Long user_id;
    boolean isInitial = true;

    // JDBC driver name, database URL and credentials
    //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://ist311db.cahpdmpysbav.us-east-2.rds.amazonaws.com:3306/db8?zeroDateTimeBehavior=CONVERT_TO_NULL";
    //  Database credentials
    static final String USER = "db8_user";
    static final String PASS = "db8_user_ist311_rpz";

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
        refresh(false);
    }

    void refresh(boolean check) {
        if (check) {
            isInitial = false;
            System.out.println("isInitial");
            
        }
        conversationList.getItems().removeAll(messages);

        getMessagesFromDB();

        for (Map<String, String> message : messages) {
            if (message.get("id").equals(convo_id.toString())) {
                String toDisplay = message.get("body");
                conversationList.getItems().add(toDisplay);
            }
        }
    }

    @FXML
    void sendMessage(ActionEvent event) {
        //send message to DB
        //clear text in textField
        //call refreshData()

        if (!newMessageField.getText().equals("")) {
            sendMessageToDB();
            refresh(true);
            newMessageField.clear();
        }
    }

    void setupListener() {
        conversationList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<String>() {
                protected void updateItem(String item, boolean empty) {
                    System.out.println("L_?");
                    super.updateItem(item, empty);
                    if (item != null && isInitial) {
                        setText(item);
                    } else {
                        setText("");
                    }
                }
            };
            // prob dont need this since no selecting
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

    void initData(Long id, Long userId) {
        convo_id = id;
        user_id = userId;

        refresh(false);
    }

    void sendMessageToDB() {
        Connection connection = null;
        Statement statement = null;

        String values = convo_id + "," + user_id + ",\'" + newMessageField.getText() + "\'";
        System.out.println(values);

        String query = "INSERT INTO Messages (conversation_id, sender_id, body) "
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

    void getMessagesFromDB() {
        Connection connection = null;
        Statement statement = null;

        String query
                = "SELECT c.id, m.message_id, m.conversation_id, m.sender_id, m.body, m.time_stamp\n"
                + "FROM Conversation c\n"
                + "INNER JOIN Messages m ON c.id = m.conversation_id;";

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            statement = connection.createStatement();

            String sql = query;
            ResultSet rs = statement.executeQuery(sql);

            //Get data from result set
            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                Long c_id = rs.getLong("id");
                Long m_message_id = rs.getLong("m.message_id");
                Long m_sender_id = rs.getLong("m.sender_id");
                String m_body = rs.getString("m.body");
                String m_time_stamp = rs.getString("m.time_stamp");

                map.put("id", c_id.toString());
                map.put("message_id", m_message_id.toString());
                map.put("sender_id", m_sender_id.toString());
                map.put("body", m_body);
                map.put("time_stamp", m_time_stamp);

                messages.add(map);
            }
            rs.close();
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

    public void loadData() {
        Query q_users = manager.createNamedQuery("Users.findAll");
        users_data = q_users.getResultList();

        Query q = manager.createNamedQuery("Messages.findAll");
        messages_data = q.getResultList();
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
