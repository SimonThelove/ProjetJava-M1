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
import socketsTCP.SocketMessenger;

/**
 *
 * @author lamoure
 */
public class LanceurClient extends Application {
    
    // Cette classe correspond au main Client
    // Elle permet d'initialiser la fenêtre (Stage)
    // Elle crée également un premier objet de la classe Client
    // Il sera implémenté au fur et à mesure de l'utilisation de l'application
    // Elle crée également un objet de la classe SocketClient
    // afin de permettre la communication avec le serveur
    
    
    private MenuAnonyme menuA;
    private final Client client = new Client();
    private final SocketClient soc = new SocketClient();
    private final SocketMessenger socMsgr = new SocketMessenger();
    
    @Override
    public void start(Stage application) {
        application.setTitle("Connect!");
        
        //Creation et affichage du menu Principal en tant qu'anonyme
        menuA = new MenuAnonyme();
        Scene sceneRoot = new Scene(menuA);
        menuA.menuAnonyme(application, sceneRoot, client, soc, socMsgr);
        application.setScene(sceneRoot);
        application.show();

    }
    
    public static void main(String[] args) {
        launch();
    }
}