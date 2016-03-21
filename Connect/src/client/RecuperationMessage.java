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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gestionProtocole.GestionProtocoleClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import socketsTCP.SocketClient;
import socketsTCP.SocketMessenger;

/**
 *
 * @author Yohann
 */
public class RecuperationMessage extends GridPane {
    
    // Cette classe permet la gestion de la boite de recepetion des messages de type mail

    private GestionProtocoleClient gp;
    private Text titre;
    private Button retour;
    private ObservableList<String> messages;

    public RecuperationMessage() {
        this.messages = FXCollections.observableArrayList();
    }
    
    public void recupererMessage (Stage fenetre_menu, Scene rootScene, GestionProtocoleClient gp, Client client, SocketClient socket, SocketMessenger socMsgr) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement RecuperationMessage.java"); 
        
        this.gp = gp;
        
        titre = new Text("Messages reçus : " + gp.getNbMessages() + " messages");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        //Affichage des messages reçus
        ListView<String> liste_messages = new ListView<>(messages);
        this.messages = gp.getMessagerie();
        liste_messages.setItems(messages);
        this.add(liste_messages, 0, 1, 2, 1);
        
        //Bouton de sélection du message reçu
        Button afficherMessage = new Button("Afficher");
        HBox hbAfficher = new HBox(10);
        hbAfficher.setAlignment(Pos.BOTTOM_RIGHT);
        hbAfficher.getChildren().add(afficherMessage);
        this.add(hbAfficher, 0, 2);
        
        //Action lors de l'appui sur le bouton afficher
        afficherMessage.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Affichage du message sélectionné
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture de ReceptionMessage.java (AfficherMessage)"); 

                    AffichageMessage afficher = new AffichageMessage();
                    Scene scene_afficher = new Scene(afficher);
                    afficher.afficherMessage(fenetre_menu, scene_afficher, gp, client, socket, liste_messages.getSelectionModel().getSelectedIndex() + 1, socMsgr);
                    fenetre_menu.setScene(scene_afficher);
                }
            }
        ); 
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 1, 2);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Renvoit le client au menu connecte
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture de ReceptionMessage.java (Retour)"); 
                    
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC, client, gp, socket, socMsgr);
                    fenetre_menu.setScene(scene_menuC);
                }
            }
        ); 
    }
}