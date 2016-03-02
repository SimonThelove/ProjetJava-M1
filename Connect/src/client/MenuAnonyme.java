/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package client;

import javafx.application.Platform;
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

import socketsTCP.SocketClient;
import socketsTCP.SocketEcouteMsgr;

/**
 *
 * @author lamoure
 */
public class MenuAnonyme extends GridPane {

    public void menuAnonyme (Stage fenetre_menu, Scene rootScene, Client client, SocketClient soc) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
System.out.println(">>>> Lancement MenuAnonyme.java");        
        
        Text titre = new Text("Menu Principal");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);
        
        //Creation du bouton Inscription
        Button sInscrire = new Button("Inscription");
        HBox hbInscription = new HBox(10);
        hbInscription.setAlignment(Pos.BOTTOM_RIGHT);
        hbInscription.getChildren().add(sInscrire);
        this.add(hbInscription, 0, 2);
        
        //Action lors de l'appui sur le bouton Inscription
        sInscrire.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Affichge du menu Inscription
                public void handle(ActionEvent e) {
System.out.println(">> Fermeture MenuAnonyme.java (Inscription)");
                    
                    Inscription inscription = new Inscription();
                    Scene scene_insciption = new Scene(inscription);
                    inscription.sInscrire(fenetre_menu, scene_insciption, client, soc);
                    fenetre_menu.setScene(scene_insciption);
                }
            }
        );
        
        //Creation du bouton Connexion
        Button seConnecter = new Button("Connexion");
        HBox hbSeConnecter=  new HBox(10);
        hbSeConnecter.setAlignment(Pos.BOTTOM_RIGHT);
        hbSeConnecter.getChildren().add(seConnecter);
        this.add(hbSeConnecter, 1, 2);
        
        //Action lors de l'appui sur le bouton Connexion
        seConnecter.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Affichge du menu Connexion
                public void handle(ActionEvent e) {
System.out.println(">> Fermeture MenuAnonyme.java (Connexion)");
                    
                    Connexion connexion = new Connexion();
                    Scene scene_connexion = new Scene(connexion);
                    connexion.seConnecter(fenetre_menu, scene_connexion, client, soc);
                    fenetre_menu.setScene(scene_connexion);
                }
            }
        );

        //Creation du bouton Rechercher
        Button rechercher = new Button("Rechercher");
        HBox hbRech = new HBox(10);
        hbRech.setAlignment(Pos.BOTTOM_RIGHT);
        hbRech.getChildren().add(rechercher);
        this.add(hbRech, 2, 2);
        
        //Action lors de l'appui sur le bouton Rechercher
        rechercher.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Affichge du menu Rechercher
                public void handle(ActionEvent e) {
System.out.println(">> Fermeture MenuAnonyme.java (Rechercher)");
                    
                    Recherche recherche = new Recherche();
                    Scene scene_recherche = new Scene(recherche);
                    recherche.rechercher(fenetre_menu, scene_recherche, client, soc);
                    fenetre_menu.setScene(scene_recherche);
                }
            }
        );
        
        //Creation du bouton Messenger
        Button messenger = new Button("Messenger");
        HBox hbMsgr = new HBox(10);
        hbMsgr.setAlignment(Pos.BOTTOM_RIGHT);
        hbMsgr.getChildren().add(messenger);
        this.add(hbMsgr, 3, 2);
        
        //Action lors de l'appui sur le bouton Messenger
        messenger.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Affichge du menu Messenger
                public void handle(ActionEvent e) {
System.out.println(">> Fermeture MenuAnonyme.java (Messenger)");
                    
                    // Creation d'un socket d'ecoute pour connexion P2P
                    SocketEcouteMsgr ecoute = new SocketEcouteMsgr();
                    
                    // Ouverture de la scene messenger
                    Messenger discuter = new Messenger();
                    Scene scene_discuter = new Scene(discuter);
                    discuter.dialoguer(fenetre_menu, scene_discuter, client, soc, ecoute);
                    fenetre_menu.setScene(scene_discuter);
                    
                   
                }
            }
        );
        
        //Creation du bouton Quitter
        Button quitter = new Button("Quitter");
        HBox hbQuit = new HBox(10);
        hbQuit.setAlignment(Pos.BOTTOM_RIGHT);
        hbQuit.getChildren().add(quitter);
        this.add(hbQuit, 4, 2);
        
        //Action lors de l'appui sur le bouton Quitter
        quitter.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override
                //Fermer le socket et quitter l'application
                public void handle(ActionEvent e) {
                    
                    if (!soc.getSocket().isClosed()){
System.out.println("Fermeture Socket client anonyme...");                        
                        soc.quit();
                    }
System.out.println(">> Fermeture MenuAnonyme.java (Quitter)");
                   Platform.exit();
                }
            }
        );
    }
}
