package messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class
 * @author sscho
 */
public class Main extends Application {
    
    Stage window;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        
        // loading from FXML
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Scene scene = new Scene(root);
        window.setScene(scene);
//        MessagesSceneController controller = root.<LoginSceneController>getController();
//        controller.initData(window);
        window.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}