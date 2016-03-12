/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package client;

import gestionProtocole.GestionProtocoleClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import socketsTCP.SocketClient;
import socketsTCP.SocketEcouteMsgr;

/**
 *
 * @author lamoure
 */
public class Messenger extends GridPane {
    
    // Cette classe accessible à tous les utilisateurs permet d'utiliser les fonctions Peer-to-Peer
    // Elle permet en passant par le serveur au départ, de créer une conversation directe avec un autre client
    // La seule condition est que ce client soit connecté au Messenger au moment de la conversation
    
    private GestionProtocoleClient gp, gpMsgr;
    private ObservableList<String> connectes;
    
    public Messenger() {
        this.connectes = FXCollections.observableArrayList();
    }
    
    public void dialoguer (Stage fenetre_menu, Scene rootScene, Client client, SocketClient socket, SocketEcouteMsgr ecoute) {

        // Dimensions de la fenêtre de dialogue
        this.setPrefSize(600, 600);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement Messenger.java");
        
        this.gp = new GestionProtocoleClient(socket);
        this.gpMsgr = new GestionProtocoleClient(ecoute);
        
        // Zone d'affichage des messages
        TextArea messages = new TextArea();
        messages.setPrefSize(400, 550);
        messages.setEditable(false);
        this.add(messages, 0, 0, 2, 1);
        
        // Zone de saisie du message
        TextField saisie_msg = new TextField();
        saisie_msg.setPrefWidth(250);
        this.add(saisie_msg, 0, 1);
        
System.out.println("Creation liste des clients connectés...");
        
        // Creation de la liste des clients connectes
        ListView<String> liste_clients = new ListView<>(connectes);
        
        // Recuperation des clients connectes sur le serveur (anonymes ou non)
        gp.requeteP2P(client);
        connectes = gp.getClients_co();
        
        
        // Ajout des noms a la liste
        liste_clients.setItems(connectes);
        liste_clients.setPrefSize(200, 550);
        this.add(liste_clients, 2, 0, 3, 1);

System.out.println("Creation liste OK...");
                        
        // Actualisation de la liste des clients connectés
        Button actualiser = new Button("Actualiser");
        HBox hbActualise = new HBox(10);
        hbActualise.setAlignment(Pos.BOTTOM_RIGHT);
        hbActualise.getChildren().add(actualiser);
        this.add(hbActualise, 3, 1);
        
        actualiser.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event)
            {
System.out.println("Actualisation de la liste des clients connectés...");
                
                gp.requeteP2P(client);
                connectes = null;
                connectes = gp.getClients_co();

            }
        
    });
                
        // Attente de la connexion de l'autre client
        Button contacter = new Button("Contacter");
        HBox hbContact = new HBox(10);
        hbContact.setAlignment(Pos.BOTTOM_RIGHT);
        hbContact.getChildren().add(contacter);
        this.add(hbContact, 2, 1);
        
        contacter.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event)
            {
System.out.println("Ouverture socket d'ecoute P2P...");
                ecoute.socket(messages);
                // Mise à jour de l'affichage
                messages.setText(gpMsgr.getConversation().getText());
            } 
    });
        
        // Bouton d'envoi du message
        Button envoyer = new Button("Envoyer");
        HBox hbEnvoi = new HBox(10);
        hbEnvoi.setAlignment(Pos.BOTTOM_RIGHT);
        hbEnvoi.getChildren().add(envoyer);
        this.add(hbEnvoi, 1, 1);
        
        envoyer.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event)
            {
                
                // Envoi du message au client sélectionné +  affichage de la conversation
System.out.println("Initialisation de la conversation P2P...");                
                ecoute.initEnvoiP2P();
System.out.println("Envoi du message P2P...");                 
                gpMsgr.echangerP2P(saisie_msg.getText());
System.out.println("Affichage de la conversation P2P...");
                messages.setText(messages.getText() + " >> " + saisie_msg.getText() + System.lineSeparator());
System.out.println("Vidage de la zone de saisie...");                 
                saisie_msg.clear();
                if (client.getChaine().equalsIgnoreCase("P2PN|ok")) {
System.out.println("Attente d'une réponse...");
                    ecoute.socket(messages);
                }
            }
        
    });
        
        // Bouton de retour au menu précédent
        Button retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 4, 1);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle (ActionEvent e) {
System.out.println(">> Fermeture Messenger.java (Retour)"); 

                    //NOTIFIER L'AUTRE CLIENT QUE L'ON QUITTE LA CONVERSATION ??
                    
                    //Si le client est connecte, on le renvoit au menuConnecte
                    if(client.getMail()!=null)
                    {
                        MenuConnecte menuC = new MenuConnecte();
                        Scene scene_menuC = new Scene(menuC);
                        menuC.menuConnecte(fenetre_menu, scene_menuC, client, gp, socket);
                        fenetre_menu.setScene(scene_menuC);
                    }
                    //Sinon on le renvoit au menuAnonyme
                    else
                    {
                        MenuAnonyme menuA = new MenuAnonyme();
                        Scene scene_menuA = new Scene(menuA);
                        menuA.menuAnonyme(fenetre_menu, scene_menuA, client, socket);
                        fenetre_menu.setScene(scene_menuA);
                    }
                }
            }
        );
    }
}