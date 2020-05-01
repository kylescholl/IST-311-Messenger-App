package messenger;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class LoginSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void gotoForgotPasswordScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPasswordScene.fxml"));
            Parent secondRoot = loader.load();

            // Show Second FXML in new a window            
            Stage stage = new Stage();
            stage.setScene(new Scene(secondRoot));
            stage.setTitle("Forgot Password Window");
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
        // Using UsersModel Table
//        Query q_all = manager.createNamedQuery("UsersModel.findAll");
//        Query q_email = manager.createNamedQuery("UsersModel.findByEmail");
//        System.out.println("q_email: " + q_email);

        // Using Users Table
        Query q_all = manager.createNamedQuery("Users.findAll");
        Query q_email = manager.createNamedQuery("Users.findByEmail");
        System.out.println("q_email: " + q_email);
        
        

        List<UsersModel> data = q_all.getResultList();
        System.out.println("data: " + data);

        ObservableList<UsersModel> odata = FXCollections.observableArrayList();
        System.out.println("odata: " + odata);

        for (UsersModel d : data) {
            //...   
            System.out.println(d.getId());
            odata.add(d);
        }

        //modelTable.setItems(odata);
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
