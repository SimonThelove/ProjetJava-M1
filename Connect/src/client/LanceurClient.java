/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import socketsTCP.SocketClient;

/**
 *
 * @author lamoure
 */
public class LanceurClient extends Application {
    
    private MenuAnonyme menuA;
    private final Client client = new Client();
    private final SocketClient soc = new SocketClient();
    
    @Override
    public void start(Stage application) {
        application.setTitle("Connect!");
        
        //Creation et affichage du menu Principal en tant qu'anonyme
        menuA = new MenuAnonyme();
        Scene sceneRoot = new Scene(menuA);
        menuA.menuAnonyme(application, sceneRoot, client, soc);
        application.setScene(sceneRoot);
        application.show();

    }
    
    public static void main(String[] args) {
        launch();
    }
}