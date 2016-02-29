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
import javafx.scene.control.Alert;

/**
 *
 * @author lamoure
 */
public class Modification extends GridPane {
        
    private final GestionProtocoleClient gp = new GestionProtocoleClient();
    private Text titre;
    private Label nom, prenom, mail, diplome, annee, competences;
    private TextField saisie_nom, saisie_prenom, saisie_mail, saisie_diplome, saisie_annee, saisie_competences;
    private Button modifier, retour;
    
    public void modifier (Stage fenetre_menu, Scene rootScene, Client client) {
        
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        
        titre = new Text("Modification");
        titre.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        this.add(titre, 0, 0, 2, 1);

        //Creation des differents labels et TextField
        nom = new Label("Nom :");
        this.add(nom, 0, 1);

        saisie_nom = new TextField();
        this.add(saisie_nom, 1, 1);

        prenom = new Label("Prenom :");
        this.add(prenom, 0, 2);

        saisie_prenom = new TextField();
        this.add(saisie_prenom, 1, 2);
        
        mail = new Label("E-mail :");
        this.add(mail, 0, 3);

        saisie_mail = new TextField();
        saisie_mail.setEditable(false);
        saisie_mail.setText(client.getMail());
        this.add(saisie_mail, 1, 3);
        
        diplome = new Label("Diplome :");
        this.add(diplome, 0, 4);

        saisie_diplome = new TextField();
        this.add(saisie_diplome, 1, 4);
        
        annee = new Label("Année :");
        this.add(annee, 0, 5);

        saisie_annee = new TextField();
        this.add(saisie_annee, 1, 5);
        
        competences = new Label("Compétences :");
        this.add(competences, 0, 6);

        saisie_competences = new TextField();
        this.add(saisie_competences, 1, 6);
        
        //Creation du bouton Valider
        modifier = new Button("Valider");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(modifier);
        this.add(hbBtn, 1, 8);
        
        //Action lors de l'appui sur le bouton Valider
        modifier.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent e) {
                    //Recuperation des informations des champs
                    client.setNom(saisie_nom.getText());
                    client.setPrenom(saisie_prenom.getText());
                    client.setMail(saisie_mail.getText());
                    client.setDiplome(saisie_diplome.getText());
                    client.setAnnee(saisie_annee.getText());
                    client.setCompetences(saisie_competences.getText());
                    
                    //Creation de la requete a envoyer puis execution de la requete Modification
                    gp.setMessage(client);
                    gp.requeteModi(client);

                    // POP-UP de message de resultat
                    final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(fenetre_menu);
                    alert.setTitle("Connect - information");
                    alert.setHeaderText("Message");
                    alert.setContentText(client.getChaine());
                    alert.showAndWait();

                    // Retour au menu connecte
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC, client);
                    fenetre_menu.setScene(scene_menuC);
                }
            }
        );
        
        //Creation du bouton retour
        retour = new Button("Retour");
        HBox hbRetour = new HBox(10);
        hbRetour.setAlignment(Pos.BOTTOM_RIGHT);
        hbRetour.getChildren().add(retour);
        this.add(hbRetour, 1, 9);
        
        //Action lors de l'appui sur le bouton retour
        retour.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                //Renvoit le client au menu connecte
                public void handle (ActionEvent e) {
                    MenuConnecte menuC = new MenuConnecte();
                    Scene scene_menuC = new Scene(menuC);
                    menuC.menuConnecte(fenetre_menu, scene_menuC, client);
                    fenetre_menu.setScene(scene_menuC);
                }
            }
        ); 
    }
}
