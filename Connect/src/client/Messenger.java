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
    
    private GestionProtocoleClient gp;
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
        
        this.gp = new GestionProtocoleClient(socket);
        
        // Zone d'affichage des messages
        TextArea messages = new TextArea();
        messages.setPrefSize(450, 550);
        messages.setEditable(false);
        this.add(messages, 0, 0);
        
        // Zone de saisie du message
        TextField saisie_msg = new TextField();
        this.add(saisie_msg, 0, 1);
        
        // Creation de la liste des clients connectes
        ListView<String> liste_clients = new ListView<>(connectes);
        
        // Recuperation des clients connectes sur le serveur (anonymes ou non)
        gp.requeteP2P(client);
        connectes = gp.getClients_co();
        
        
        // Ajout des noms a la liste
        liste_clients.setItems(connectes);
        liste_clients.setPrefSize(100, 550);
        this.add(liste_clients, 1, 0);
        
        // Connexion avec client sélectionné dans la ListView
        //gp.connecterP2P(portEcouteP2P);
                // >> Demande au serveur une connexion avec le client sélectionné
                // LE SERVEUR VA TRANSMETTRE LA DEMANDE A L'AUTRE CLIENT
                
        // Attente de la connexion de l'autre client
        ecoute.socket();
        
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
                //gp.echangerP2P(saisie_msg.getText()); >> Envoi du message directement au client
                
                messages.setText(messages.getText() + saisie_msg.getText() + System.lineSeparator());
                saisie_msg.clear();
            }
        
    });
        
        // Bouton de retour au menu précédent
        // DEVELOPPEMENT EN COURS ...
        
    }
}