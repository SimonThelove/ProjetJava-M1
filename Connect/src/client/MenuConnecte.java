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
    
    private Client client = new Client();
    
    private final GestionProtocoleClient gp = new GestionProtocoleClient();

    public void menuConnecte(Stage fenetre_menuC, Scene rootScene, Client client) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
System.out.println("MENU C - infos : " + client.getNom() + " " + client.getPrenom());        
        Text titre = new Text("Bienvenue : " + client.getNomCo() + " " + client.getPrenomCo());
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);
        
        Button modifier = new Button("Modification");
        HBox hbModifier = new HBox(10);
        hbModifier.setAlignment(Pos.BOTTOM_RIGHT);
        hbModifier.getChildren().add(modifier);
        this.add(hbModifier, 1, 2);
        
        
        modifier.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Modification modifier = new Modification();
                Scene scene_modifier = new Scene(modifier);
                modifier.modifier(fenetre_menuC,scene_modifier, client);
                fenetre_menuC.setScene(scene_modifier);
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
                recherche.rechercher(fenetre_menuC, scene_recherche, client);
                fenetre_menuC.setScene(scene_recherche);
            }
        });
        
        Button seDeconnecter = new Button("Se Déconnecter");
        HBox hbDeco = new HBox(10);
        hbDeco.setAlignment(Pos.BOTTOM_RIGHT);
        hbDeco.getChildren().add(seDeconnecter);
        this.add(hbDeco, 3, 2);
        
        seDeconnecter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                gp.requeteDeco(client);
                //client = gp.getClient();
                
                // POP-UP DU MESSAGE DE RESULTAT
                final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(fenetre_menuC);
                alert.setTitle("Connect - information");
                alert.setHeaderText("Message");
                alert.setContentText(client.getChaine());
                alert.showAndWait();
                
                MenuAnonyme menuA = new MenuAnonyme();
                Scene scene_menuA = new Scene(menuA);
                menuA.menuAnonyme(fenetre_menuC, scene_menuA, client);
                fenetre_menuC.setScene(scene_menuA);
            }
        });

    }
}
