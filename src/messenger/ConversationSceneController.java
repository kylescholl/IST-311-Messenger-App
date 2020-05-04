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
    
    LinkedList<LinkedHashMap<String,String>> messages = new LinkedList<>();
    //LinkedHashMap<String,String> message;
    
    LinkedHashMap<Long,String> messagesList;
    
    Long convo_id;

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
        System.out.println("\n\nconvo_id assigneds");
        
        String query 
                = "SELECT c.id, m.message_id, m.conversation_id, m.sender_id, m.body, m.time_stamp\n"
                + "FROM Conversation c\n"
                + "INNER JOIN Messages m ON c.id = m.conversation_id;";
        //getMessagesForConvoId(convo_id);
        
        makeQuery(query, convo_id);
        System.out.println("messages: " + messages);
        
        for (Map<String,String> message : messages) {
            System.out.println("\nid: " + message.get("id"));
            System.out.println("convo_id: " + convo_id);
            
            if (message.get("id") == convo_id.toString()) {
                System.out.println("L_?");
                String toDisplay = message.get("body");
                conversationList.getItems().add(toDisplay);
            }
        }
    }
    
    void makeQuery(String query, Long id) {
        Connection connection = null;
        Statement statement = null;
        
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            
            statement = connection.createStatement();
            
            String sql = query;
            ResultSet rs = statement.executeQuery(sql);
            
            //Get data from result set
            while (rs.next()) {
                LinkedHashMap<String,String> map = new LinkedHashMap<>();
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
                
                System.out.println("DEBUG");
                System.out.println("map: " + map);
                
                messages.add(map);
                
                // TODO need to add these to a list as it iterates through
                // use an if statement to match GLOBAL convo_id to sql id
                // add message_id, sender_id, body, and time_stamp to lists
                System.out.println("zzzzzzzzzz");
                System.out.println(c_id);
                System.out.println(id);
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
    
    void getMessagesForConvoId(Long id) {
        String s 
                = "SELECT c.id, m.message_id, m.conversation_id, m.sender_id, m.body, m.time_stamp\n"
                + "FROM Conversation c\n"
                + "INNER JOIN Messages m ON c.id = m.conversation_id;";
        
        String query 
                = "SELECT conversation_id, ";
        
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
                
                // TODO need to add these to a list as it iterates through
                // use an if statement to match GLOBAL convo_id to sql id
                // add message_id, sender_id, body, and time_stamp to lists
                System.out.println("zzzzzzzzzz");
                System.out.println(c_id);
                System.out.println(id);
                
                if (c_id.equals(id)) {
                    messagesList.put(m_message_id, m_body);
                    System.out.println("ggggggggg");
                }
                System.out.println("messagesList.current: " + messagesList);
                
                
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
    }

    public void loadData() {
        Query q_users = manager.createNamedQuery("Users.findAll");
        users_data = q_users.getResultList();

        Query q = manager.createNamedQuery("Messages.findAll");
        messages_data = q.getResultList();
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
