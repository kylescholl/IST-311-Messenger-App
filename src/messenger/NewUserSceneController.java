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
 * NewUserSceneController class
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

    /**
     * @param email
     * @return
     */
    public boolean checkEmail(String email) {
        //Check if in email format
        if (isValidEmail(email)) {
            for (Users d : data) {
                System.out.println("GGGGGGG: " + d.getEmail());
                //Check if email already exists
                if (d.getEmail().equals(email)) {
                    System.out.println("Email already in system");
                    return false;
                }
            }
        } else {
            System.out.println("Not valid email");
        }
        return true;
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

    public boolean checkPassword() {
        if (!passwordField.getText().equals("")) {
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
         * Send new user's data to DB
         * 
         * Close or transition back to login scene --> if unable to find a solution, 
         * just have them close it (see below)
         * 
         * PROBLEM: Old login scene will not have new user's information unless
         * queried again POSSIBLE SOLUTION: Close old window when transitioning
         * to new scene
         */

        // Confirm valid email format
        String email_input = emailField.getText();
        String password_input = passwordField.getText();
        // Check if email is already in system
        if (!checkEmail(email_input)) {
            // Confirm passwords match
            if (checkPassword()) {
                // Add user to DB
                Users user = new Users();
                user.setEmail(email_input);
                user.setPassword(password_input);
                

                manager.getTransaction().begin();
                manager.persist(user);
                manager.getTransaction().commit();

                System.out.println("[New user successfully registered]");

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
            Logger.getLogger(NewUserSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Show Second FXML in new a window            
        Stage stage = new Stage();
        stage.setScene(new Scene(secondRoot));
        stage.setTitle("Login Window");
        stage.show();
    }

    public void loadData() {
        Query q_all = manager.createNamedQuery("Users.findAll");
        data = q_all.getResultList();
        System.out.println("data: " + data);
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
