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

/**
 *
 * @author lamoure
 */
public class MenuAnonyme extends GridPane {

    public void menuAnonyme (Stage fenetre_menu, Scene rootScene) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        Text titre = new Text("Menu Principal");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);
        
        Button sInscrire = new Button("Inscription");
        HBox hbInscription = new HBox(10);
        hbInscription.setAlignment(Pos.BOTTOM_RIGHT);
        hbInscription.getChildren().add(sInscrire);
        this.add(hbInscription, 0, 2);
        
        
        sInscrire.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                Inscription inscription = new Inscription();
                Scene scene_insciption = new Scene(inscription);
                inscription.sInscrire(fenetre_menu, scene_insciption);
                fenetre_menu.setScene(scene_insciption);
            }
        });
        
        Button seConnecter = new Button("Connexion");
        HBox hbSeConnecter=  new HBox(10);
        hbSeConnecter.setAlignment(Pos.BOTTOM_RIGHT);
        hbSeConnecter.getChildren().add(seConnecter);
        this.add(hbSeConnecter, 1, 2);
        
        
        seConnecter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Connexion connexion = new Connexion();
                Scene scene_connexion = new Scene(connexion);
                connexion.seConnecter(fenetre_menu, scene_connexion);
                fenetre_menu.setScene(scene_connexion);
            }
        });

        Button rechercher = new Button("Rechercher");
        HBox hbRech = new HBox(10);
        hbRech.setAlignment(Pos.BOTTOM_RIGHT);
        hbRech.getChildren().add(rechercher);
        this.add(hbRech, 2, 2);
        
        
        rechercher.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Recherche recherche = new Recherche();
                Scene scene_recherche = new Scene(recherche);
                recherche.rechercher(fenetre_menu, scene_recherche);
                fenetre_menu.setScene(scene_recherche);
            }
        });
        
        Button quitter = new Button("Quitter");
        HBox hbQuit = new HBox(10);
        hbQuit.setAlignment(Pos.BOTTOM_RIGHT);
        hbQuit.getChildren().add(quitter);
        this.add(hbQuit, 3, 2);
        
        
        quitter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
            
        });
        
    }
}
