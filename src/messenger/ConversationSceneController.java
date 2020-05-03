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
    Long convo_id;
//    Set<Long> convo_id_set = new HashSet<>();
//    Set<Long> friend_id_set = new HashSet<>();
//    Map<Long, Long> friend_id_map = new HashMap<>();

    // JDBC driver name and database URL
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

    void initData(Long id) {
        convo_id = id;
        
        
        
        
        
        
        
        
        
        // OLD //

        System.out.println("aaaa: " + messages_data);
        
        Conversation c = manager.find(Conversation.class, id);
        System.out.println("c: " + c);
        Messages messages = new Messages();
        //Long l = messages.getConversationId();

        System.out.println("    " + convo_id);
        for (Messages m : messages_data) {

            String toDisplay = m.getBody();
            conversationList.getItems().add(toDisplay);
        }

        // load messages into listView
//        for (Messages m : messages_data) {
//            Long convoId = m.getConversationId();
//            Long firstUserId = m.getFirstUserId().getId();
//            Long secondUserId = m.getSecondUserId().getId();
//            System.out.println("\nc_id.getId: " + m.getFirstUserId().getId());
//            System.out.println("current_id: " + current_id);
//            if (firstUserId.equals(current_id)) {
//                convo_id_set.add(convoId);
//                friend_id_set.add(secondUserId);
//                friend_id_map.put(secondUserId, convoId);
//            }
//            if (secondUserId.equals(current_id)) {
//                convo_id_set.add(convoId);
//                friend_id_set.add(firstUserId);
//                friend_id_map.put(firstUserId, convoId);
//            }
//        }
    }

    public void loadData() {
        Query q_users = manager.createNamedQuery("Users.findAll");
        users_data = q_users.getResultList();

        Query q = manager.createNamedQuery("Messages.findAll");
        messages_data = q.getResultList();

        // TESTING //
        //test();
        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        String s 
                = "SELECT c.id, m.message_id, m.conversation_id, m.sender_id, m.body, m.time_stamp\n"
                + "FROM Conversation c\n"
                + "INNER JOIN Messages m ON c.id = m.conversation_id;";
        getMessagesForConvoId(s, 1L);
        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
    }

    //consider making this a general method and just passing in the desired sql query
    LinkedHashMap<String,String> getMessagesForConvoId(String query, Long id) {
        Connection connection = null;
        Statement statement = null;
        
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            statement = connection.createStatement();
            
            String sql = query;
            ResultSet rs = statement.executeQuery(sql);
            
            //Get data from result set
            while (rs.next()) {
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
                
                // TODO need to add these to a list as it iterates through
                // use an if statement to match GLOBAL convo_id to sql id
                // add message_id, sender_id, body, and time_stamp to lists
                
                System.out.println("map.current: " + map);
                
                //System.out.println("\n    map: " + map);
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
        return map;
    }

    void test() {
        Connection conn = null;
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
//            String sql = "UPDATE Employees set age=30 WHERE id=103";
//
//            // Let us check if it returns a true Result Set or not.
//            Boolean ret = stmt.execute(sql);
//            System.out.println("Return value is : " + ret.toString());
//
//            // Let us update age of the record with ID = 103;
//            int rows = stmt.executeUpdate(sql);
//            System.out.println("Rows impacted : " + rows);
            String sql = "SELECT * FROM Users";
            ResultSet rs = stmt.executeQuery(sql);

            //Get data from result set
            while (rs.next()) {
                Long test_id = rs.getLong("user_id");
                String test_username = rs.getString("username");
                String test_email = rs.getString("email");
                String test_password = rs.getString("password");

                //Display values
                System.out.print("id: " + test_id);
                System.out.print(", username: " + test_username);
                System.out.print(", email: " + test_email);
                System.out.println(", password: " + test_password);
            }
            //Clean-up
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
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
