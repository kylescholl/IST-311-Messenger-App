package messenger;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    Query q_maxID;
    List<Users> data;
    
    // TESTING //
    public void cancelLogin() {
        System.out.println("GGGGGGGGGGGG: " + loginSegueButton.getScene().getWindow());
        loginSegueButton.getScene().getWindow().hide();
    }
    // TESTING //
    
    /**
     * @param email
     * @return
     */
    public boolean checkEmail(String email) {
        //Check if in email format
        if (isValidEmail(email)) {
            for (Users d : data) {
                //Check if email already exists
                if (!d.getEmail().equals(email)) {
                    return true;
                } else {
                    System.out.println("Email already in system");
                }
            }
        } else {
            System.out.println("Not valid email");
        }
        return false;
    }
    
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    public Long getMaxID() {
        return ((Long) q_maxID.getResultList().get(0));
    }
    
    public boolean checkPassword() {
        if (!passwordField.getText().equals(null)) {
            if (!passwordField.getText().contains(" ")) {
                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                    return true;
                }
            } else {
                System.out.println("pw contains spaces");
            }
        } else {
            System.out.println("No pw entered");
        }
        return false;
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
        Long maxID = getMaxID();
        String email_input = emailField.getText();
        String password_input = passwordField.getText();
        // Check if email is already in system
        if (checkEmail(email_input)) {
            // Confirm passwords match
            System.out.println("test");
            if (checkPassword()) {
                System.out.println("maxID: " + maxID);
                if (maxID != null)) {
                    // Add user to DB
                    Users user = new Users();
                    user.setId(maxID + 1L);
                    user.setEmail(email_input);
                    user.setPassword(password_input);

                    manager.getTransaction().begin();
                    manager.persist(user);
                    manager.getTransaction().commit();

                    try {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("Welcome!");
                        alert.setContentText("New Account Created");
                        alert.showAndWait();
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                    // Transition back to login page
                } else {
                    System.out.println("maxID: " + maxID);
                    throw new IllegalArgumentException("id cannot be null");
                }
            } else {
                System.out.println("Passwords do not match");
                /**
                 * TODO: Make a new label in scene builder when this runs,
                 * display an error message.
                 */
            }
        } else {
            
            /**
             * TODO: Make a new label in scene builder when this runs, display
             * an error message to user stating email already exists in system.
             */
        }
    }
    
    /**
     * Return to LoginScene
     */
    @FXML
    private void loginSegue(ActionEvent event) {
        /**
         * Would simply just closing the window work? Problem: Old scene may not
         * have most current data from database Only works when returning to
         * previous scene, wouldn't work when going to a new scene
         */
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent secondRoot = null;
        try {
            secondRoot = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(NewUserSceneController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        // Show Second FXML in new a window            
        Stage stage = new Stage();
        stage.setScene(new Scene(secondRoot));
        stage.setTitle("Login Window");
        stage.show();
    }

    public void loadData() {
        q_maxID = manager.createNamedQuery("Users.getMaxID");
        Query q_all = manager.createNamedQuery("Users.findAll");
        data = q_all.getResultList();
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
