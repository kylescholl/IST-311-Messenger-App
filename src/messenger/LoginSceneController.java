package messenger;

import java.io.IOException;
import java.util.List;
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
 * LoginSceneController class
 *
 * @author sscho
 */
public class LoginSceneController {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Hyperlink forgotPasswordButton;

    @FXML
    private Hyperlink createAccountButton;

    Long id;
    Stage old_stage;

    List<Users> data;

    public boolean validateUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // checks if the email is valid
        for (Users d : data) {
            //Check if email already exists
            System.out.println("GGGGGGG: " + d.getEmail());
            if (d.getEmail().equals(username)) {
                
                return true;
            } else {
                System.out.println("Email not in system, please create an account");
                return false;
            }
        }
        return true;
    }

    @FXML
    void gotoMessagesScene(ActionEvent event) {
        /**
         * TODO:
         *
         * Add user login authentication.
         *
         * refresh data when hitting continue to get newly created user
         */
        boolean validEmail = validateUser();

        if (validEmail == true) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MessagesScene.fxml"));
                Parent secondRoot = loader.load();

                // Pass data to new controller
                MessagesSceneController controller = loader.<MessagesSceneController>getController();

                // Show Second FXML in new a window            
                Stage stage = new Stage();
                stage.setScene(new Scene(secondRoot));
                stage.setTitle("Messages Window");

                //controller.initData(id);
                controller.initData(stage, 2L);

                stage.show();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Try Again");
            alert.setHeaderText("Invalid Username/Password");
            alert.setContentText("Please try logging in again.");
            alert.showAndWait();
        }
    }

    @FXML
    void gotoForgotPasswordScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPasswordScene.fxml"));
            Parent secondRoot = loader.load();

            // Show Second FXML in new a window            
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("Forgot Password Window");
            old_stage.close();
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void gotoNewUserScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewUserScene.fxml"));
            Parent secondRoot = loader.load();

            // Show Second FXML in new a window            
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("New User Window");
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    EntityManager manager;

    public void loadData() {
        /**
         * TODO: Need to confirm that entered email and password match up can be
         * done a few ways --> most likely method by using identifying user_id
         * of a valid email, then comparing entered password with DB password
         */

        Query q_all = manager.createNamedQuery("Users.findAll");
        
        data = q_all.getResultList();

        // is this needed? --> oLists are usually used in tableViews
        //ObservableList<Users> odata = FXCollections.observableArrayList();
        //modelTable.setItems(odata);
    }

    void initData(Stage s) {
        old_stage = s;
    }

    @FXML
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert forgotPasswordButton != null : "fx:id=\"forgotPasswordButton\" was not injected: check your FXML file 'LoginScene.fxml'.";
        assert createAccountButton != null : "fx:id=\"createAccountButton\" was not injected: check your FXML file 'LoginScene.fxml'.";

        // will load data from DB here
        // loading data from database
        //database reference: "IST-311-messenger-app-JavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("IST-311-messenger-app-JavaFXPU").createEntityManager();

        //set up the columns in the table
//        modelColumnID.setCellValueFactory(new PropertyValueFactory<>("Id")); //should match with attribute Id (e.g., getId/setId methods) in SimpleModel
//        modelColumnValue.setCellValueFactory(new PropertyValueFactory<>("Value")); //should match with attribute Value (e.g., getValue/setValue methods) in SimpleModel
        //loading data
        loadData();
    }
}