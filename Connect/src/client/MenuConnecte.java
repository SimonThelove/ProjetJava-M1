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
import javafx.scene.control.Alert;

/**
 *
 * @author lamoure
 */
public class MenuConnecte extends GridPane {
    
    private final GestionProtocoleClient gp = new GestionProtocoleClient();

    public void menuConnecte(Stage fenetre_menuC, Scene rootScene, Client client) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        //Affichage au client d'un message de bienvenue avec son nom et prenom
        Text titre = new Text("Bienvenue : " + client.getNomCo() + " " + client.getPrenomCo());
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);
                
        //Creation du bouton Modification
        Button modifier = new Button("Modifier");
        HBox hbModifier = new HBox(10);
        hbModifier.setAlignment(Pos.BOTTOM_RIGHT);
        hbModifier.getChildren().add(modifier);
        this.add(hbModifier, 1, 2);
        
        //Action lors de l'appui sur le bouton Modification
        modifier.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Affichage du menu Modification
                public void handle(ActionEvent e) {
                    Modification modifier = new Modification();
                    Scene scene_modifier = new Scene(modifier);
                    modifier.modifier(fenetre_menuC,scene_modifier, client);
                    fenetre_menuC.setScene(scene_modifier);
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
                //Affichage du menu Rechercher
                public void handle(ActionEvent e) {
                    Recherche recherche = new Recherche();
                    Scene scene_recherche = new Scene(recherche);
                    recherche.rechercher(fenetre_menuC, scene_recherche, client);
                    fenetre_menuC.setScene(scene_recherche);
                }
            }
        );
        
        //Creation du bouton Message a envoyer
        Button envoyerMessage = new Button("Nouveau message");
        HBox hbEnvoiMessage = new HBox(10);
        hbEnvoiMessage.setAlignment(Pos.BOTTOM_RIGHT);
        hbEnvoiMessage.getChildren().add(envoyerMessage);
        this.add(hbEnvoiMessage, 3, 2);
        
        //Action lors de l'appui sur le bouton Nouveau message
        envoyerMessage.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Affichage du menu Message a envoyer
                public void handle(ActionEvent e) {
                    EnvoiMessage envoiMessage = new EnvoiMessage();
                    Scene scene_envoiMessage = new Scene(envoiMessage);
                    envoiMessage.envoyerMessage(fenetre_menuC, scene_envoiMessage, client);
                    fenetre_menuC.setScene(scene_envoiMessage);
                }
            }
        );
        
        //Creation du bouton Mes messages
        Button recupererMessage = new Button("Mes messages");
        HBox hbrecupererMessage = new HBox(10);
        hbrecupererMessage.setAlignment(Pos.BOTTOM_RIGHT);
        hbrecupererMessage.getChildren().add(recupererMessage);
        this.add(hbrecupererMessage, 4, 2);
        
        //Action lors de l'appui sur le bouton Mes messages
        recupererMessage.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Affichage du menu Mes messages
                public void handle(ActionEvent e) {
                    RecuperationMessage recuperationMessage = new RecuperationMessage();
                    Scene scene_recuperationMessagee = new Scene(recuperationMessage);
                    recuperationMessage.recupererMessage(fenetre_menuC, scene_recuperationMessagee, client);
                    fenetre_menuC.setScene(scene_recuperationMessagee);
                }
            }
        );
        
        //Creation du bouton Se deconnecter
        Button seDeconnecter = new Button("Se Deconnecter");
        HBox hbDeco = new HBox(10);
        hbDeco.setAlignment(Pos.BOTTOM_RIGHT);
        hbDeco.getChildren().add(seDeconnecter);
        this.add(hbDeco, 5, 2);
        
        //Action lors de l'appui sur le bouton seDeconnecter
        seDeconnecter.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Deonnexion et affichage du menu Anonyme
                public void handle(ActionEvent e) {
                    gp.requeteDeco(client);

                    // POP-UP du message resultat de deconnexion
                    final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(fenetre_menuC);
                    alert.setTitle("Connect - information");
                    alert.setHeaderText("Message");
                    alert.setContentText(client.getChaine());
                    alert.showAndWait();
                    
                    //Affichage du menu Anonyme
                    MenuAnonyme menuA = new MenuAnonyme();
                    Scene scene_menuA = new Scene(menuA);
                    menuA.menuAnonyme(fenetre_menuC, scene_menuA, client);
                    fenetre_menuC.setScene(scene_menuA);
                }
            }
        );
    }
}
