/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package serveur;

/**
 *
 * @author Yohann
 */
import javafx.application.Application;
import javafx.stage.Stage;
import socketsTCP.SocketServeur;

public class LanceurServeur extends Application {
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        SocketServeur soc = new SocketServeur();
        soc.socket();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
