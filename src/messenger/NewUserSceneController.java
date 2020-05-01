package messenger;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * NewUserSceneController Class
 *
 * @author sscho
 */
public class NewUserSceneController {

    @FXML
    private Button continueButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private Hyperlink loginSegueButton;

    EntityManager manager;
    List<Users> data;
    //Long id = -1;
//    Long id;

    /**
     *
     * @param email
     * @return
     */
    public boolean checkEmail(String email) {
        for (Users d : data) {
            if (d.getEmail().equals(email)) {
                //id = d.getId();
                return true;
            }
        }
        return false;
    }

    public Long getMaxID() {
        Query q_maxID = manager.createNamedQuery("Users.getMaxID");
        return ((Long) q_maxID.getResultList().get(0));
    }

    public void insertUser(Long id, String email, String pw) {

    }

    @FXML
    private void continueButtonPress(ActionEvent event) {
        /**
         * TODO: Verify proper data entry --> email must be in correct format
         * Verify email doesn't already exist in system
         *
         * Send new user's data to DB Notify user of success (done) Close or
         * transition back to login scene --> if unable to find a solution, just
         * have them close it (see below)
         *
         * PROBLEM: Old login scene will not have new user's information unless
         * queried again POSSIBLE SOLUTION: Close old window when transitioning
         * to new scene
         */

        // Confirm valid email format
        // Confirm passwords match
        // Check if email is already in system
        Long maxID = getMaxID();
        String email_input = emailField.getText();
        String password_input = passwordField.getText();

        if (!checkEmail(email_input)) {
            // Add user to DB
            if (!maxID.equals(null)) {
                System.out.println("");
                System.out.println(maxID);
                System.out.println(email_input);
                System.out.println(password_input);
                System.out.println("");

                Users user = new Users();
                user.setId(maxID + 1L);
                user.setEmail(email_input);
                user.setPassword(password_input);
                
                manager.getTransaction().begin();
                manager.persist(user);
                manager.getTransaction().commit();

                System.out.println("test");
            } else {
                throw new IllegalArgumentException("id cannot be null");
            }
        }

        // This will only happen when a new user is successfully registered //
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Welcome!");
            alert.setContentText("New Account Created");
            //alert.showAndWait();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Return to LoginScene
     */
    @FXML
    private void loginSegue(ActionEvent event) {
        /**
         * Would simply just closing the window work? Problems: Old scene may
         * not have most current data from database Only works when returning to
         * previous scene, wouldn't work when going to a new scene
         */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent secondRoot = null;
        try {
            secondRoot = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(NewUserSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Show Second FXML in new a window            
        Stage stage = new Stage();
        stage.setScene(new Scene(secondRoot));
        stage.setTitle("Login Window");
        stage.show();
        // loading from FXML
    }

    public void loadData() {
        System.out.println("\nMaxID");
        Query q_maxID = manager.createNamedQuery("Users.getMaxID");
        Long maxID = (Long) q_maxID.getResultList().get(0);

        Query q_all = manager.createNamedQuery("Users.findAll");
        Query q_email = manager.createNamedQuery("Users.findByEmail");
        System.out.println("q_email: " + q_email);
        // "q_email.toString()" does nothing --> same output as "q_email"

        //List<Users> data = q_all.getResultList();
        data = q_all.getResultList();
        System.out.println("data: " + data);
        // "data.toString()" does nothing --> same output as "data"

        ObservableList<Users> odata = FXCollections.observableArrayList();

        System.out.println("\nEmails: ");
        for (Users d : data) {
            System.out.println(d.getEmail());
            odata.add(d);
        }
        System.out.println("odata: " + odata);
        System.out.println("odata.toString(): " + odata.toString());
        //modelTable.setItems(odata);
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        assert continueButton != null : "fx:id=\"continueButton\" was not injected: check your FXML file 'NewUserScene.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'NewUserScene.fxml'.";
        assert confirmPasswordField != null : "fx:id=\"confirmPasswordField\" was not injected: check your FXML file 'NewUserScene.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'NewUserScene.fxml'.";
        assert loginSegueButton != null : "fx:id=\"loginSegueButton\" was not injected: check your FXML file 'NewUserScene.fxml'.";

        //load data from database
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST-311-messenger-app-JavaFXPU").createEntityManager();
        //load data
        loadData();
    }
}
