package messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
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
        window.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}



//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//
// DAO
//public class Main {
//	public static void main(String[] args) { 
//		// Gets an entity manager and a transaction
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyUnitName"); 
//		EntityManager em = emf.createEntityManager(); 
//		// Persists the entity to the database 
//		EntityTransactiontx = em.getTransaction(); 	
//		try{
//			tx.begin(); 
//			em.persist(entity); 
//			tx.commit(); 
//		} catch (Exception e) {
//			...
//			tx.rollback();
//		} finally {
//			em.close(); 
//			emf.close();
//		}
//	}
//}