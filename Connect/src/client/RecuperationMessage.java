/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gestionProtocole.GestionProtocoleClient;
import javafx.scene.control.TextArea;

import socketsTCP.SocketClient;

/**
 *
 * @author Yohann
 */
public class RecuperationMessage extends GridPane {

    private GestionProtocoleClient gp;
    private Text titre;
    private Button retour;
    
    public void recupererMessage (Stage fenetre_menu, Scene rootScene, Client client, SocketClient socket) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement RecuperationMessage.java"); 
        
        this.gp = new GestionProtocoleClient(socket);
        
System.out.println("Creation de la requete MSG..."); 
        
        //Creation de la requete a envoyer puis execution de la requete
        gp.requeteRecupMsg(client);
        
System.out.println("Messages récupérés..."); 
        
        titre = new Text("Messages reçus : " + " messages");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        //Affichage des messages reçus
        
        
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 1, 6);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Renvoit le client au menu connecte
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture de ReceptionMessage.java (Retour)"); 
                    
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC, client, gp, socket);
                    fenetre_menu.setScene(scene_menuC);
                }
            }
        ); 
    }
}